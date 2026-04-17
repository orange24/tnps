package th.co.nttdata.tki.batch.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import th.co.nttdata.tki.batch.bean.TWIPDeadline;
import th.co.nttdata.tki.batch.bean.TWIPDeadlinedDate;

/**
 * JDBC DAO สำหรับ WIP Deadline Batch
 * รองรับ: batch control, prepare tables, fetch, bulk insert, finalize
 */
public class WipDeadlineBatchDao extends AbstractBatchDao {

    public WipDeadlineBatchDao(DataSource dataSource) {
        super(dataSource);
    }

    // =========================================================================
    //  Prepare tables  (sequential, ก่อน parallel calc)
    // =========================================================================

    /**
     * ลำดับการเตรียม table:
     * 1. deleteDeadlineDate  — ลบ date นอก window (executeDate-10 ถึง executeDate)
     * 2. deleteDeadlineTmp   — clear tmp
     * 3. copyDeadlineHeader  — snapshot header ไป tmp
     * 4. copyDeadlineDate    — snapshot date ไป tmp
     * 5. deleteDeadline      — clear ตาราง main ทั้งหมด (จะ rebuild ใหม่)
     */
    public void prepareDeadlineTables(Date executeDate) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. ลบ date นอก window
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM t_wip_deadline_date "
                      + "WHERE reportdate < CONVERT(DATE, CAST(? AS DATETIME) - 10) "
                      + "   OR reportdate >= CONVERT(DATE, ?)")) {
                    java.sql.Date d = new java.sql.Date(executeDate.getTime());
                    ps.setDate(1, d);
                    ps.setDate(2, d);
                    ps.executeUpdate();
                }
                // 2. clear tmp
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("DELETE FROM t_wip_deadline_header_tmp");
                    st.executeUpdate("DELETE FROM t_wip_deadline_tmp");
                }
                // 3. snapshot header → tmp
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate(
                        "INSERT INTO t_wip_deadline_header_tmp (partid,wip,wiporder,iswip,capacity,stock) "
                      + "SELECT partid,wip,wiporder,iswip,capacity,stock FROM t_wip_deadline");
                }
                // 4. snapshot date → tmp
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate(
                        "INSERT INTO t_wip_deadline_tmp (reportdate,partid,wip,wiporder,deadlineqty,shiftid,colorid) "
                      + "SELECT reportdate,partid,wip,wiporder,deadlineqty,shiftid,colorid FROM t_wip_deadline_date");
                }
                // 5. clear main tables (rebuild ทั้งหมด)
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("DELETE FROM t_wip_deadline_date");
                    st.executeUpdate("DELETE FROM t_wip_deadline");
                }
                conn.commit();
                log.info("[WIP_B02] prepareDeadlineTables done");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("prepareDeadlineTables failed", e);
        }
    }

    // =========================================================================
    //  Fetch
    // =========================================================================

    /**
     * Query delivery plan — group by partId ใน Java
     * (iBatis เดิมใช้ groupBy="partId", เรา handle เองใน loop)
     * ผลลัพธ์: List<TWIPDeadline> แต่ละตัว = 1 partId, wipDeadlinedDateList = delivery dates DESC
     */
    public List<TWIPDeadline> queryDeliveryPlan() {
        String sql =
            "WITH LatestFgStock AS ( "
            + "    SELECT  "
            + "        fgid, "
            + "        fgbalance, "
            + "        fgadjust, "
            + "        ROW_NUMBER() OVER (PARTITION BY fgId ORDER BY reportDate DESC) AS ranking "
            + "    FROM  "
            + "        dbo.t_fg_stock "
            + "), "
            + " MaxRevision AS ( "
            + "    SELECT  "
            + "        d1.customerid,  "
            + "        d1.yearmonth,  "
            + "        MAX(d1.revision) AS max_revision "
            + "    FROM  "
            + "        t_deliveryplan d1 "
            + "        INNER JOIN t_deliveryplan_date dd ON dd.deliveryplanid = d1.deliveryplanid "
            + "    WHERE  "
            + "        yearmonth = CAST(CONCAT(DATEPART(year, GETDATE()), RIGHT('00' + CONVERT(varchar, DATEPART(month, GETDATE())), 2)) AS int) "
            + "    GROUP BY  "
            + "        d1.customerid,  "
            + "        d1.yearmonth "
            + ") "
            + " SELECT  "
            + "    mfg.partid,  "
            + "    dp2.deliverydate, "
            + "    COALESCE(fs.fgadjust, fs.fgbalance, 0) AS fgStock, "
            + "    dp2.productionQty "
            + " FROM  "
            + "    t_deliveryplan dp1 "
            + "    INNER JOIN t_deliveryplan_date dp2 ON dp1.deliveryplanid = dp2.deliveryplanid "
            + "    INNER JOIN m_fg_part AS mfg ON dp2.fgid = mfg.fgid "
            + "    LEFT OUTER JOIN LatestFgStock fs ON dp2.fgid = fs.fgid AND fs.ranking = 1 "
            + "    INNER JOIN MaxRevision mr ON dp1.customerid = mr.customerid AND dp1.yearmonth = mr.yearmonth AND dp1.revision = mr.max_revision "
            + " WHERE  "
            + "    dp2.productionQty IS NOT NULL  "
            + "    AND dp2.productionqty > 0 "
            + "    AND dp2.deliverydate BETWEEN GETDATE() AND DATEADD(day, 29, GETDATE()) "
            + "    AND dp1.yearmonth = CAST(CONCAT(DATEPART(year, GETDATE()), RIGHT('00' + CONVERT(varchar, DATEPART(month, GETDATE())), 2)) AS int) "
            + " ORDER BY  "
            + "    mfg.partid,  "
            + "    dp2.deliverydate DESC";

        List<TWIPDeadline> result = new ArrayList<TWIPDeadline>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            TWIPDeadline current = null;
            while (rs.next()) {
                int partId = rs.getInt("partid");
                // group by partId (SQL ordered by partid)
                if (current == null || current.getPartId() != partId) {
                    current = new TWIPDeadline();
                    current.setPartId(partId);
                    current.setStock(rs.getInt("fgStock"));
                    current.setWipDeadlinedDateList(new ArrayList<TWIPDeadlinedDate>());
                    result.add(current);
                }
                TWIPDeadlinedDate date = new TWIPDeadlinedDate();
                date.setReportDate(rs.getDate("deliverydate"));
                date.setDeadlineQty(rs.getInt("productionQty"));
                current.getWipDeadlinedDateList().add(date);
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryDeliveryPlan failed", e);
        }
        log.info("[WIP_B02] queryDeliveryPlan — parts=" + result.size());
        return result;
    }

    /**
     * Query WIP capacity + current stock ของ partId หนึ่ง
     * ผลลัพธ์ ordered by wipOrder ASC
     */
    public List<TWIPDeadline> queryCapStockWIP(int partId) {
        String sql =
            "WITH LatestWipStock AS ("
            + "    SELECT "
            + "        partid, "
            + "        wip, "
            + "        adjuststock, "
            + "        currentStock,"
            + "        ROW_NUMBER() OVER (PARTITION BY partid, wip ORDER BY reportdate DESC) AS ranking "
            + "    FROM t_wip_stock "
            + "    WHERE partid = ? "
            + ")"
            + " SELECT "
            + "    ISNULL(CASE lt.stUseNo"
            + "              WHEN '1' THEN 3600 / NULLIF(lt.stresult1, 0) "
            + "              WHEN '2' THEN 3600 / NULLIF(lt.stresult2, 0) "
            + "              WHEN '3' THEN 3600 / NULLIF(lt.stresult3, 0) "
            + "              WHEN '4' THEN 3600 / NULLIF(lt.stresult4, 0) "
            + "              WHEN '5' THEN 3600 / NULLIF(lt.stresult5, 0) "
            + "              ELSE          3600 / NULLIF(lt.stresult6, 0) "
            + "           END, 0) AS capacity, "
            + "    pw.wip, "
            + "    pw.wipOrder, "
            + "    pw.partId,"
            + "    COALESCE(ws.adjuststock, ws.currentStock, 0) AS stock, "
            + "    1 AS isWIP "
            + " FROM "
            + "    m_part_wip pw "
            + " LEFT JOIN "
            + "    m_leadtime lt ON lt.partid = pw.partid AND lt.wip = pw.wip "
            + " LEFT JOIN "
            + "    LatestWipStock ws ON pw.wip = ws.wip AND pw.partid = ws.partid AND ws.ranking = 1 "
            + " WHERE "
            + "    pw.partid = ? "
            + " ORDER BY pw.wiporder ";

        List<TWIPDeadline> result = new ArrayList<TWIPDeadline>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, partId); // CTE WHERE partid = ?
            ps.setInt(2, partId); // main WHERE pw.partid = ?
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TWIPDeadline wip = new TWIPDeadline();
                    wip.setPartId(rs.getInt("partid"));
                    wip.setWip(rs.getString("wip"));
                    wip.setWipOrder(rs.getInt("wipOrder"));
                    BigDecimal cap = rs.getBigDecimal("capacity");
                    wip.setCapacity(cap != null ? cap : BigDecimal.ZERO);
                    wip.setStock(rs.getInt("stock"));
                    wip.setIsWIP(rs.getInt("isWIP"));
                    result.add(wip);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryCapStockWIP failed for partId=" + partId, e);
        }
        return result;
    }

    // =========================================================================
    //  Write
    // =========================================================================

    /**
     * Bulk INSERT deadline headers + dates ในครั้งเดียว (executeBatch)
     * แต่ละ TWIPDeadline = 1 header + N date rows
     */
    public void bulkInsertDeadline(List<TWIPDeadline> deadlineList) {
        if (deadlineList.isEmpty()) return;

        String hdrSql  = "INSERT INTO t_wip_deadline (partid,wip,wiporder,iswip,capacity,stock) "
                       + "VALUES (?,?,?,?,?,?)";
        String dateSql = "INSERT INTO t_wip_deadline_date "
                       + "(reportdate,partid,wip,wiporder,deadlineqty,shiftid,colorid) "
                       + "VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // batch INSERT headers
                try (PreparedStatement ps = conn.prepareStatement(hdrSql)) {
                    for (TWIPDeadline d : deadlineList) {
                        ps.setInt      (1, d.getPartId());
                        ps.setString   (2, d.getWip());
                        ps.setInt      (3, d.getWipOrder() != null ? d.getWipOrder() : 0);
                        ps.setInt      (4, d.getIsWIP()    != null ? d.getIsWIP()    : 0);
                        ps.setBigDecimal(5, d.getCapacity() != null ? d.getCapacity() : BigDecimal.ZERO);
                        if (d.getStock() != null) ps.setInt (6, d.getStock());
                        else                      ps.setNull(6, Types.INTEGER);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                // batch INSERT dates
                try (PreparedStatement ps = conn.prepareStatement(dateSql)) {
                    for (TWIPDeadline d : deadlineList) {
                        if (d.getWipDeadlinedDateList() == null) continue;
                        for (TWIPDeadlinedDate dt : d.getWipDeadlinedDateList()) {
                            ps.setDate  (1, new java.sql.Date(dt.getReportDate().getTime()));
                            ps.setInt   (2, d.getPartId());
                            ps.setString(3, d.getWip());
                            ps.setInt   (4, d.getWipOrder() != null ? d.getWipOrder() : 0);
                            if (dt.getDeadlineQty() != null) ps.setInt (5, dt.getDeadlineQty());
                            else                             ps.setNull(5, Types.INTEGER);
                            ps.setInt   (6, dt.getShiftId() != null ? dt.getShiftId() : 1);
                            if (dt.getColorId() != null) ps.setInt (7, dt.getColorId());
                            else                         ps.setNull(7, Types.INTEGER);
                            ps.addBatch();
                        }
                    }
                    ps.executeBatch();
                }
                conn.commit();
                log.info("[WIP_B02] bulkInsertDeadline done — headers=" + deadlineList.size());
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("bulkInsertDeadline failed", e);
        }
    }

    /**
     * หลัง insert เสร็จ:
     * 1. MERGE headers จาก tmp กลับ (restore parts ที่ไม่ได้ recalculate รอบนี้)
     * 2. INSERT dates จาก tmp ที่มี header ตรงกัน
     */
    public void finalizeDeadline() {
        String mergeSql =
            "MERGE INTO t_wip_deadline dl "
          + "USING (SELECT partid,wip,wiporder,iswip,capacity,stock "
          + "       FROM t_wip_deadline_header_tmp) tmp "
          + "ON (dl.partid=tmp.partid AND dl.wip=tmp.wip AND dl.wiporder=tmp.wiporder) "
          + "WHEN NOT MATCHED THEN "
          + "  INSERT(partid,wip,wiporder,iswip,capacity,stock) "
          + "  VALUES(tmp.partid,tmp.wip,tmp.wiporder,tmp.iswip,tmp.capacity,tmp.stock);";

        String insertLastSql =
            "INSERT INTO t_wip_deadline_date (reportdate,partid,wip,wiporder,deadlineqty,shiftid,colorid) "
          + "SELECT reportdate,partid,wip,wiporder,deadlineqty,shiftid,colorid "
          + "FROM   t_wip_deadline_tmp t "
          + "WHERE  EXISTS (SELECT 1 FROM t_wip_deadline d "
          + "               WHERE d.partid=t.partid AND d.wip=t.wip AND d.wiporder=t.wiporder)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate(mergeSql);
                st.executeUpdate(insertLastSql);
                conn.commit();
                log.info("[WIP_B02] finalizeDeadline done");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("finalizeDeadline failed", e);
        }
    }
}
