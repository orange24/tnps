package th.co.nttdata.tki.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * JDBC DAO สำหรับ Daily Summary Batch (DAL_B01)
 */
public class DailySummaryBatchDao {

    private static final Logger log = Logger.getLogger(DailySummaryBatchDao.class);
    private final DataSource dataSource;

    public DailySummaryBatchDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // =========================================================================
    //  Batch Control
    // =========================================================================

    /** true = status 1 (RUNNING) — ห้าม run ซ้ำ */
    public boolean isBatchRunning(String batchCode) {
        String sql = "SELECT batchstatus FROM m_batch_control WHERE batchcode = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, batchCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("batchstatus") == 1;
            }
        } catch (SQLException e) {
            log.warn("[DAL_B01] isBatchRunning check failed — assuming not running: " + e.getMessage());
        }
        return false;
    }

    /**
     * UPSERT m_batch_control  (INSERT ถ้าไม่มี row, UPDATE ถ้ามีแล้ว)
     * status: 0=SUCCESS  1=RUNNING  2=FAILED
     */
    public void upsertBatchControl(String batchCode, String batchName, int status, String runBy) {
        String sql =
            "MERGE m_batch_control AS t "
          + "USING (VALUES (?)) AS s(batchcode) ON t.batchcode = s.batchcode "
          + "WHEN MATCHED THEN "
          + "  UPDATE SET batchstatus=?, batchname=?, runby=?, "
          + "             startdate =CASE WHEN ?=1 THEN GETDATE() ELSE t.startdate  END, "
          + "             finishdate=CASE WHEN ?<>1 THEN GETDATE() ELSE t.finishdate END "
          + "WHEN NOT MATCHED THEN "
          + "  INSERT (batchcode,batchname,batchstatus,runby,startdate,finishdate) "
          + "  VALUES (?,?,?,?, "
          + "          CASE WHEN ?=1  THEN GETDATE() ELSE NULL END, "
          + "          CASE WHEN ?<>1 THEN GETDATE() ELSE NULL END);";
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1,  batchCode);
                ps.setInt   (2,  status);
                ps.setString(3,  batchName);
                ps.setString(4,  runBy);
                ps.setInt   (5,  status);
                ps.setInt   (6,  status);
                ps.setString(7,  batchCode);
                ps.setString(8,  batchName);
                ps.setInt   (9,  status);
                ps.setString(10, runBy);
                ps.setInt   (11, status);
                ps.setInt   (12, status);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("upsertBatchControl failed (status=" + status + ")", e);
        }
    }

    // =========================================================================
    //  Prepare
    // =========================================================================

    /**
     * หา minDate จาก t_dailymc, t_dailywk, t_dailymcwk ที่มีการ update ตั้งแต่ executeDate-1
     * ถ้าไม่มีข้อมูล ให้ใช้ executeDate เอง
     */
    public Date queryMinDate(Date executeDate) {
        String sql =
            "SELECT ISNULL(MIN(d.reportdate), CONVERT(DATE, ?)) AS mindate "
          + "FROM ( "
          + "    SELECT mc.reportdate FROM t_dailymc mc "
          + "    WHERE CONVERT(DATE, mc.lastupdate) >= CONVERT(DATE, DATEADD(day, -1, ?)) "
          + "    UNION ALL "
          + "    SELECT wk.reportdate FROM t_dailywk wk "
          + "    WHERE CONVERT(DATE, wk.lastupdate) >= CONVERT(DATE, DATEADD(day, -1, ?)) "
          + "    UNION ALL "
          + "    SELECT mcwk.reportdate FROM t_dailymcwk mcwk "
          + "    WHERE CONVERT(DATE, mcwk.lastupdate) >= CONVERT(DATE, DATEADD(day, -1, ?)) "
          + ") d";
        java.sql.Date sqlDate = new java.sql.Date(executeDate.getTime());
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);
            ps.setDate(3, sqlDate);
            ps.setDate(4, sqlDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date d = rs.getDate("mindate");
                    return d != null ? new Date(d.getTime()) : executeDate;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryMinDate failed", e);
        }
        return executeDate;
    }

    /**
     * ลบ t_daily_summary ตั้งแต่ minDate เป็นต้นไป (เพื่อ recalculate ใหม่)
     */
    public void deleteDailySummary(Date minDate) {
        String sql = "DELETE FROM t_daily_summary WHERE reportdate >= ?";
        java.sql.Date sqlDate = new java.sql.Date(minDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                int deleted = ps.executeUpdate();
                c.commit();
                log.info("[DAL_B01] deleteDailySummary deleted=" + deleted + " rows (>= " + sqlDate + ")");
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("deleteDailySummary failed", e);
        }
    }

    // =========================================================================
    //  Per-date inserts: t_daily_summary
    // =========================================================================

    /** INSERT Day shift summary สำหรับวันที่ reportDate */
    public void insertDailySummaryDay(Date reportDate) {
        String sql =
            "INSERT INTO t_daily_summary (reportdate,wip,partid,reporttype,shift,ok,ng,pd,qty) "
          + "SELECT d.reportDate, d.wip, d.partId, d.reportType, "
          + "       'D' AS shift, "
          + "       SUM(ISNULL(d.ok,0)) AS ok, "
          + "       SUM(ISNULL(d.ng,0)) AS ng, "
          + "       SUM(ISNULL(d.pd,0)) AS pd, "
          + "       SUM(ISNULL(d.qty,0)) AS qty "
          + "FROM ( "
          + "    SELECT mc.reportdate, mc.wip, mc.partid, mc.reporttype, "
          + "           SUM(ISNULL(d.ok,0)) AS ok, SUM(ISNULL(d.ng,0)) AS ng, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, SUM(ISNULL(d.qty,0)) AS qty "
          + "    FROM t_dailymc mc "
          + "    INNER JOIN t_dailymc_detail d ON mc.dailymcid = d.dailymcid "
          + "    LEFT JOIN t_dailymc_lot l ON d.dailymcid = l.dailymcid "
          + "    WHERE mc.reportdate = ? AND d.reporttime = 25 "
          + "    GROUP BY mc.reportdate, mc.wip, mc.partid, mc.reporttype "
          + "    UNION ALL "
          + "    SELECT wk.reportdate, wk.wip, d.partid, wk.reporttype, "
          + "           SUM(ISNULL(d.ok,0)) AS ok, SUM(ISNULL(d.ng,0)) AS ng, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, SUM(ISNULL(d.qty,0)) AS qty "
          + "    FROM t_dailywk wk "
          + "    INNER JOIN t_dailywk_detail d ON wk.dailywkid = d.dailywkid "
          + "    WHERE wk.reportdate = ? AND wk.shift = 'D' "
          + "    GROUP BY wk.reportdate, wk.wip, d.partid, wk.reporttype "
          + "    UNION ALL "
          + "    SELECT mcwk.reportdate, mcwk.wip, d.partid, mcwk.reporttype, "
          + "           SUM(ISNULL(d.ok,0)) AS ok, SUM(ISNULL(d.ng,0)) AS ng, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, SUM(ISNULL(d.qty,0)) AS qty "
          + "    FROM t_dailymcwk mcwk "
          + "    INNER JOIN t_dailymcwk_detail d ON mcwk.dailymcwkid = d.dailymcwkid "
          + "    WHERE mcwk.reportdate = ? AND mcwk.shift = 'D' "
          + "    GROUP BY mcwk.reportdate, mcwk.wip, d.partid, mcwk.reporttype "
          + ") d "
          + "GROUP BY d.reportdate, d.wip, d.partid, d.reporttype";
        java.sql.Date sqlDate = new java.sql.Date(reportDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                ps.setDate(2, sqlDate);
                ps.setDate(3, sqlDate);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("insertDailySummaryDay failed for " + reportDate, e);
        }
    }

    /** INSERT Night shift summary สำหรับวันที่ reportDate */
    public void insertDailySummaryNight(Date reportDate) {
        String sql =
            "INSERT INTO t_daily_summary (reportdate,wip,partid,reporttype,shift,ok,ng,pd,qty) "
          + "SELECT d.reportdate, d.wip, d.partid, d.reporttype, "
          + "       'N' AS shift, "
          + "       SUM(ISNULL(d.ok,0)) AS ok, "
          + "       SUM(ISNULL(d.ng,0)) AS ng, "
          + "       SUM(ISNULL(d.pd,0)) AS pd, "
          + "       SUM(ISNULL(d.qty,0)) AS qty "
          + "FROM ( "
          + "    SELECT mc.reportdate, mc.wip, mc.partid, mc.reporttype, "
          + "           SUM(ISNULL(d.ok,0)) AS ok, SUM(ISNULL(d.ng,0)) AS ng, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, SUM(ISNULL(d.qty,0)) AS qty "
          + "    FROM t_dailymc mc "
          + "    INNER JOIN t_dailymc_detail d ON mc.dailymcid = d.dailymcid "
          + "    LEFT JOIN t_dailymc_lot l ON d.dailymcid = l.dailymcid "
          + "    WHERE mc.reportdate = ? AND d.reporttime = 26 "
          + "          AND (l.lotno IS NULL OR (l.lotno IS NOT NULL AND l.lotno NOT LIKE '%RE%')) "
          + "    GROUP BY mc.reportdate, mc.wip, mc.partid, mc.reporttype "
          + "    UNION ALL "
          + "    SELECT wk.reportdate, wk.wip, d.partid, wk.reporttype, "
          + "           SUM(ISNULL(d.ok,0)) AS ok, SUM(ISNULL(d.ng,0)) AS ng, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, SUM(ISNULL(d.qty,0)) AS qty "
          + "    FROM t_dailywk wk "
          + "    INNER JOIN t_dailywk_detail d ON wk.dailywkid = d.dailywkid "
          + "    WHERE wk.reportdate = ? AND wk.shift = 'N' "
          + "          AND (d.workorderno IS NULL OR (d.workorderno IS NOT NULL AND d.workorderno NOT LIKE '%RE%')) "
          + "    GROUP BY wk.reportdate, wk.wip, d.partid, wk.reporttype "
          + "    UNION ALL "
          + "    SELECT mcwk.reportdate, mcwk.wip, d.partid, mcwk.reporttype, "
          + "           SUM(ISNULL(d.ok,0)) AS ok, SUM(ISNULL(d.ng,0)) AS ng, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, SUM(ISNULL(d.qty,0)) AS qty "
          + "    FROM t_dailymcwk mcwk "
          + "    INNER JOIN t_dailymcwk_detail d ON mcwk.dailymcwkid = d.dailymcwkid "
          + "    WHERE mcwk.reportdate = ? AND mcwk.shift = 'N' "
          + "          AND (d.workorderno IS NULL OR (d.workorderno IS NOT NULL AND d.workorderno NOT LIKE '%RE%')) "
          + "    GROUP BY mcwk.reportdate, mcwk.wip, d.partid, mcwk.reporttype "
          + ") d "
          + "GROUP BY d.reportdate, d.wip, d.partid, d.reporttype";
        java.sql.Date sqlDate = new java.sql.Date(reportDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                ps.setDate(2, sqlDate);
                ps.setDate(3, sqlDate);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("insertDailySummaryNight failed for " + reportDate, e);
        }
    }

    // =========================================================================
    //  Per-date MERGE: t_pending
    // =========================================================================

    /** MERGE t_pending จาก t_dailymc สำหรับวันที่ reportDate */
    public void mergePendingMC(Date reportDate) {
        String sql =
            "MERGE INTO t_pending pd "
          + "USING ( "
          + "    SELECT mc.reportdate, mc.reporttype, mc.wip, mc.partid, mc.customerid, "
          + "           SUM(ISNULL(d.pd,0)) AS pd, "
          + "           (SELECT TOP 1 t.lotno FROM t_dailymc_lot t "
          + "            INNER JOIN t_dailymc m ON m.dailymcid = t.dailymcid "
          + "            WHERE m.reportdate = mc.reportdate AND m.reporttype = mc.reporttype "
          + "              AND m.wip = mc.wip AND m.customerid = mc.customerid) AS workorderno "
          + "    FROM t_dailymc mc "
          + "    INNER JOIN t_dailymc_detail d ON mc.dailymcid = d.dailymcid "
          + "    WHERE mc.reportdate = ? AND d.reporttime IN (25,26) "
          + "    GROUP BY mc.reportdate, mc.reporttype, mc.wip, mc.partid, mc.customerid "
          + "    HAVING SUM(ISNULL(d.pd,0)) > 0 "
          + ") dmc "
          + "ON (pd.reportdate = dmc.reportdate AND pd.reporttype = dmc.reporttype "
          + "    AND pd.wip = dmc.wip AND pd.partid = dmc.partid "
          + "    AND pd.customerid = dmc.customerid AND pd.workorderno = dmc.workorderno) "
          + "WHEN NOT MATCHED THEN "
          + "  INSERT(reportdate,reporttype,wip,partid,customerid,pd,workorderno) "
          + "  VALUES(dmc.reportdate,dmc.reporttype,dmc.wip,dmc.partid,dmc.customerid,dmc.pd,dmc.workorderno) "
          + "WHEN MATCHED THEN "
          + "  UPDATE SET pd.pd = dmc.pd;";
        java.sql.Date sqlDate = new java.sql.Date(reportDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("mergePendingMC failed for " + reportDate, e);
        }
    }

    /** MERGE t_pending จาก t_dailywk สำหรับวันที่ reportDate */
    public void mergePendingWK(Date reportDate) {
        String sql =
            "MERGE INTO t_pending pd "
          + "USING ( "
          + "    SELECT wk.reportdate, wk.reporttype, wk.wip, wkd.partid, wkd.customerid, "
          + "           wkd.workorderno, wkd.lotno, SUM(ISNULL(wkd.pd,0)) AS pd "
          + "    FROM t_dailywk wk "
          + "    INNER JOIN t_dailywk_detail wkd ON wk.dailywkid = wkd.dailywkid "
          + "    WHERE wk.reportdate = ? "
          + "    GROUP BY wk.reportdate, wk.reporttype, wk.wip, wkd.partid, wkd.customerid, wkd.workorderno, wkd.lotno "
          + "    HAVING SUM(ISNULL(wkd.pd,0)) > 0 "
          + ") dwk "
          + "ON (pd.reportdate = dwk.reportdate AND pd.reporttype = dwk.reporttype "
          + "    AND pd.wip = dwk.wip AND pd.partid = dwk.partid "
          + "    AND pd.customerid = dwk.customerid AND pd.workorderno = dwk.workorderno AND pd.lotno = dwk.lotno) "
          + "WHEN NOT MATCHED THEN "
          + "  INSERT(reportdate,reporttype,wip,partid,customerid,workorderno,lotno,pd) "
          + "  VALUES(dwk.reportdate,dwk.reporttype,dwk.wip,dwk.partid,dwk.customerid,dwk.workorderno,dwk.lotno,dwk.pd) "
          + "WHEN MATCHED THEN "
          + "  UPDATE SET pd.pd = dwk.pd;";
        java.sql.Date sqlDate = new java.sql.Date(reportDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("mergePendingWK failed for " + reportDate, e);
        }
    }

    /** MERGE t_pending จาก t_dailymcwk สำหรับวันที่ reportDate */
    public void mergePendingMCWK(Date reportDate) {
        String sql =
            "MERGE INTO t_pending pd "
          + "USING ( "
          + "    SELECT mcwk.reportdate, mcwk.reporttype, mcwk.wip, mcwkd.partid, mcwkd.customerid, "
          + "           mcwkd.workorderno, mcwkd.lotno, SUM(ISNULL(mcwkd.pd,0)) AS pd "
          + "    FROM t_dailymcwk mcwk "
          + "    INNER JOIN t_dailymcwk_detail mcwkd ON mcwk.dailymcwkid = mcwkd.dailymcwkid "
          + "    WHERE mcwk.reportdate = ? "
          + "    GROUP BY mcwk.reportdate, mcwk.reporttype, mcwk.wip, mcwkd.partid, mcwkd.customerid, mcwkd.workorderno, mcwkd.lotno "
          + "    HAVING SUM(ISNULL(mcwkd.pd,0)) > 0 "
          + ") dwkmc "
          + "ON (pd.reportdate = dwkmc.reportdate AND pd.reporttype = dwkmc.reporttype "
          + "    AND pd.wip = dwkmc.wip AND pd.partid = dwkmc.partid "
          + "    AND pd.customerid = dwkmc.customerid AND pd.workorderno = dwkmc.workorderno AND pd.lotno = dwkmc.lotno) "
          + "WHEN NOT MATCHED THEN "
          + "  INSERT(reportdate,reporttype,wip,partid,customerid,workorderno,lotno,pd) "
          + "  VALUES(dwkmc.reportdate,dwkmc.reporttype,dwkmc.wip,dwkmc.partid,dwkmc.customerid,dwkmc.workorderno,dwkmc.lotno,dwkmc.pd) "
          + "WHEN MATCHED THEN "
          + "  UPDATE SET pd.pd = dwkmc.pd;";
        java.sql.Date sqlDate = new java.sql.Date(reportDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("mergePendingMCWK failed for " + reportDate, e);
        }
    }
}
