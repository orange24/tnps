package th.co.nttdata.tki.batch.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import th.co.nttdata.tki.batch.bean.MLeadtime;

/**
 * JDBC DAO สำหรับ Leadtime Batch (LDT_B01)
 */
public class LeadtimeBatchDao extends AbstractBatchDao {

    public LeadtimeBatchDao(DataSource dataSource) {
        super(dataSource);
    }

    // =========================================================================
    //  Query
    // =========================================================================

    /**
     * ดึง m_leadtime ทุก row ที่มี stFromDate4, 5 หรือ 6 อย่างน้อย 1 ช่อง
     */
    public List<MLeadtime> queryLeadtime() {
        String sql =
            "SELECT leadTimeId, wip, partId, "
          + "       stFromDate4, stToDate4, "
          + "       stFromDate5, stToDate5, "
          + "       stFromDate6, stToDate6 "
          + "FROM m_leadtime "
          + "WHERE stFromDate4 IS NOT NULL OR stFromDate5 IS NOT NULL OR stFromDate6 IS NOT NULL";
        List<MLeadtime> result = new ArrayList<MLeadtime>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MLeadtime lt = new MLeadtime();
                lt.setLeadTimeId(rs.getInt("leadTimeId"));
                lt.setWip(rs.getString("wip"));
                lt.setPartId(rs.getInt("partId"));
                lt.setStFromDate4(rs.getTimestamp("stFromDate4"));
                lt.setStToDate4(rs.getTimestamp("stToDate4"));
                lt.setStFromDate5(rs.getTimestamp("stFromDate5"));
                lt.setStToDate5(rs.getTimestamp("stToDate5"));
                lt.setStFromDate6(rs.getTimestamp("stFromDate6"));
                lt.setStToDate6(rs.getTimestamp("stToDate6"));
                result.add(lt);
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryLeadtime failed", e);
        }
        return result;
    }

    /**
     * AVG(time/qty) จาก t_dailymcwk และ t_dailywk สำหรับ wip+partId ในช่วงวันที่
     * ผลลัพธ์เป็น List<BigDecimal> (0-2 ค่า จาก UNION)
     */
    public List<BigDecimal> queryAvg(String wip, int partId, Date fromDate, Date toDate) {
        String sql =
            "SELECT av.average "
          + "FROM ( "
          + "    SELECT AVG(mc) AS average "
          + "    FROM ( "
          + "        SELECT (mcd.timeused*60 / (ISNULL(mcd.ok,0) + ISNULL(mcd.ng,0))) AS mc "
          + "        FROM t_dailymcwk mc "
          + "        INNER JOIN t_dailymcwk_detail mcd ON mc.dailymcwkid = mcd.dailymcwkid "
          + "        WHERE (mcd.timeused IS NOT NULL AND mcd.timeused > 0) "
          + "          AND (ISNULL(mcd.ok,0) + ISNULL(mcd.ng,0)) > 0 "
          + "          AND mc.reporttype = 1 "
          + "          AND mc.wip = ? AND mcd.partid = ? "
          + "          AND mc.reportdate BETWEEN ? AND ? "
          + "    ) m HAVING AVG(mc) > 0 "
          + "    UNION "
          + "    SELECT AVG(wk) AS average "
          + "    FROM ( "
          + "        SELECT (wkd.timeused*60 / (ISNULL(wkd.ok,0) + ISNULL(wkd.ng,0))) AS wk "
          + "        FROM t_dailywk wk "
          + "        INNER JOIN t_dailywk_detail wkd ON wk.dailywkid = wkd.dailywkid "
          + "        WHERE (wkd.timeused IS NOT NULL AND wkd.timeused > 0) "
          + "          AND (ISNULL(wkd.ok,0) + ISNULL(wkd.ng,0)) > 0 "
          + "          AND wk.reporttype = 1 "
          + "          AND wk.wip = ? AND wkd.partid = ? "
          + "          AND wk.reportdate BETWEEN ? AND ? "
          + "    ) w HAVING AVG(wk) > 0 "
          + ") av";
        java.sql.Timestamp from = new java.sql.Timestamp(fromDate.getTime());
        java.sql.Timestamp to   = new java.sql.Timestamp(toDate.getTime());
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString   (1, wip);
            ps.setInt      (2, partId);
            ps.setTimestamp(3, from);
            ps.setTimestamp(4, to);
            ps.setString   (5, wip);
            ps.setInt      (6, partId);
            ps.setTimestamp(7, from);
            ps.setTimestamp(8, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal v = rs.getBigDecimal("average");
                    if (v != null) result.add(v);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryAvg failed wip=" + wip + " partId=" + partId, e);
        }
        return result;
    }

    /**
     * SUM(ok) จาก t_dailymc สำหรับ wip+partId ในช่วงวันที่
     * (reporttime IN 25,26 = Day+Night shift)
     */
    public int queryDaily(String wip, int partId, Date fromDate, Date toDate) {
        String sql =
            "SELECT ISNULL(SUM(ISNULL(dcd.ok,0)),0) AS total "
          + "FROM t_dailymc dc "
          + "INNER JOIN t_dailymc_detail dcd ON dc.dailymcid = dcd.dailymcid "
          + "WHERE (dcd.qty IS NOT NULL AND dcd.qty > 0) "
          + "  AND dcd.reporttime IN (25,26) "
          + "  AND dc.reporttype = 1 "
          + "  AND dc.wip = ? AND dc.partid = ? "
          + "  AND dc.reportdate BETWEEN ? AND ?";
        return queryIntSingle(sql, wip, partId, fromDate, toDate, "queryDaily");
    }

    /**
     * COUNT(*) ของ t_dailymc records สำหรับ wip+partId ในช่วงวันที่
     */
    public int queryDailySize(String wip, int partId, Date fromDate, Date toDate) {
        String sql =
            "SELECT count(*) AS total "
          + "FROM t_dailymc dc "
          + "INNER JOIN t_dailymc_detail dcd ON dc.dailymcid = dcd.dailymcid "
          + "WHERE (dcd.qty IS NOT NULL AND dcd.qty > 0) "
          + "  AND dcd.reporttime IN (25,26) "
          + "  AND dc.reporttype = 1 "
          + "  AND dc.wip = ? AND dc.partid = ? "
          + "  AND dc.reportdate BETWEEN ? AND ?";
        return queryIntSingle(sql, wip, partId, fromDate, toDate, "queryDailySize");
    }

    /**
     * SUM(stopminute*60) จาก t_dailymc_stopmc สำหรับ wip+partId ในช่วงวันที่
     * (ผลลัพธ์เป็น seconds)
     */
    public int queryStopMC(String wip, int partId, Date fromDate, Date toDate) {
        String sql =
            "SELECT ISNULL(SUM(ISNULL(stopminute,0)*60),0) AS total "
          + "FROM t_dailymc_stopmc sc "
          + "WHERE stopminute IS NOT NULL "
          + "  AND dailymcid IN ( "
          + "      SELECT dc.dailymcId "
          + "      FROM t_dailymc dc "
          + "      INNER JOIN t_dailymc_detail dcd ON dc.dailymcid = dcd.dailymcid "
          + "      WHERE (dcd.qty IS NOT NULL AND dcd.qty > 0) "
          + "        AND dcd.reporttime IN (25,26) "
          + "        AND dc.reporttype = 1 "
          + "        AND dc.wip = ? AND dc.partid = ? "
          + "        AND dc.reportdate BETWEEN ? AND ? "
          + "  )";
        return queryIntSingle(sql, wip, partId, fromDate, toDate, "queryStopMC");
    }

    // =========================================================================
    //  Update
    // =========================================================================

    /**
     * Batch UPDATE m_leadtime SET stResult4/5/6 ทุก row ในรอบเดียว
     */
    public void batchUpdateLeadtime(List<MLeadtime> list) {
        String sql =
            "UPDATE m_leadtime "
          + "SET stResult4 = ?, stResult5 = ?, stResult6 = ? "
          + "WHERE leadTimeId = ?";
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (MLeadtime lt : list) {
                    ps.setBigDecimal(1, lt.getStResult4());
                    ps.setBigDecimal(2, lt.getStResult5());
                    ps.setBigDecimal(3, lt.getStResult6());
                    ps.setInt       (4, lt.getLeadTimeId());
                    ps.addBatch();
                }
                ps.executeBatch();
                c.commit();
                log.info("[LDT_B01] batchUpdateLeadtime done — " + list.size() + " rows");
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("batchUpdateLeadtime failed", e);
        }
    }

    // =========================================================================
    //  Helper
    // =========================================================================

    private int queryIntSingle(String sql, String wip, int partId,
                               Date fromDate, Date toDate, String label) {
        java.sql.Timestamp from = new java.sql.Timestamp(fromDate.getTime());
        java.sql.Timestamp to   = new java.sql.Timestamp(toDate.getTime());
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString   (1, wip);
            ps.setInt      (2, partId);
            ps.setTimestamp(3, from);
            ps.setTimestamp(4, to);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(label + " failed wip=" + wip + " partId=" + partId, e);
        }
        return 0;
    }
}
