package th.co.nttdata.tki.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import th.co.nttdata.tki.batch.bean.MMoldShot;

/**
 * JDBC DAO สำหรับ Mold Shot Batch (MLD_B01)
 */
public class MoldShotBatchDao {

    private static final Logger log = Logger.getLogger(MoldShotBatchDao.class);
    private final DataSource dataSource;

    public MoldShotBatchDao(DataSource dataSource) {
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
            log.warn("[MLD_B01] isBatchRunning check failed — assuming not running: " + e.getMessage());
        }
        return false;
    }

    /**
     * UPSERT m_batch_control (INSERT ถ้าไม่มี row, UPDATE ถ้ามีแล้ว)
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
    //  Query min dates
    // =========================================================================

    /**
     * หา min(reportdate) ต่อ (moldId, moldNo) จาก t_dailymc ที่ update ตั้งแต่ executeDate
     * ผลลัพธ์: list ของ MMoldShot แต่ละตัวมี reportDate, moldId, moldNo
     */
    public List<MMoldShot> queryMinDatesDC(Date executeDate) {
        String sql =
            "SELECT MIN(reportdate) AS reportDate, moldId, moldNo "
          + "FROM t_dailymc "
          + "WHERE lastupdate >= CONVERT(DATE, ?) "
          + "GROUP BY moldid, moldno";
        java.sql.Date sqlDate = new java.sql.Date(executeDate.getTime());
        List<MMoldShot> result = new ArrayList<MMoldShot>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, sqlDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MMoldShot m = new MMoldShot();
                    java.sql.Date d = rs.getDate("reportDate");
                    m.setReportDate(d != null ? new Date(d.getTime()) : null);
                    m.setMoldId(rs.getInt("moldId"));
                    m.setMoldNo(rs.getString("moldNo"));
                    result.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryMinDatesDC failed", e);
        }
        return result;
    }

    /**
     * หา min(reportdate) จาก t_fg_detail ที่ update ตั้งแต่ executeDate
     * คืนค่า null ถ้าไม่มีข้อมูล
     */
    public Date queryMinDateFG(Date executeDate) {
        String sql =
            "SELECT MIN(reportdate) AS mindate "
          + "FROM t_fg_detail "
          + "WHERE lastupdate >= CONVERT(DATE, ?)";
        java.sql.Date sqlDate = new java.sql.Date(executeDate.getTime());
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, sqlDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date d = rs.getDate("mindate");
                    return d != null ? new Date(d.getTime()) : null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryMinDateFG failed", e);
        }
        return null;
    }

    // =========================================================================
    //  DC History
    // =========================================================================

    /**
     * ลบ t_mold_dc_history ตั้งแต่ minDate สำหรับ moldId+moldNo นั้น
     */
    public void deleteDCHistory(int moldId, String moldNo, Date minDate) {
        String sql =
            "DELETE FROM t_mold_dc_history "
          + "WHERE reportdate >= ? AND moldid = ? AND moldno = ?";
        java.sql.Date sqlDate = new java.sql.Date(minDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate  (1, sqlDate);
                ps.setInt   (2, moldId);
                ps.setString(3, moldNo);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                "deleteDCHistory failed moldId=" + moldId + " moldNo=" + moldNo, e);
        }
    }

    /**
     * INSERT INTO t_mold_dc_history ทั้งหมดของวันที่ reportDate สำหรับ mold นั้น
     * รวม selectDailyMC + insertMoldHistoryDC เป็น INSERT...SELECT เดียว
     * (ถ้าไม่มีข้อมูลวันนั้น ก็ไม่ insert แถวใดเลย — ไม่ error)
     */
    public void insertDCHistoryForDate(int moldId, String moldNo, Date reportDate) {
        String sql =
            "INSERT INTO t_mold_dc_history (reportdate, moldid, moldno, totaldcshot) "
          + "SELECT reportdate, moldid, moldno, SUM(totalDCShot) AS totaldcshot "
          + "FROM ( "
          + "    SELECT ISNULL(CEILING(CAST(SUM(td.qty) AS float) / NULLIF(tm.cavused,0)), 0) AS totalDCShot, "
          + "           tm.reportdate, tm.moldid, tm.moldno "
          + "    FROM t_dailymc_detail AS td "
          + "    INNER JOIN t_dailymc AS tm ON td.dailymcid = tm.dailymcid "
          + "    WHERE td.reporttime IN (25, 26) "
          + "      AND tm.reportdate = ? "
          + "      AND tm.moldid = ? "
          + "      AND tm.moldno = ? "
          + "    GROUP BY tm.reportdate, tm.moldid, tm.moldno, tm.cavused "
          + ") AS dc "
          + "GROUP BY reportdate, moldid, moldno";
        java.sql.Date sqlDate = new java.sql.Date(reportDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate  (1, sqlDate);
                ps.setInt   (2, moldId);
                ps.setString(3, moldNo);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                "insertDCHistoryForDate failed moldId=" + moldId + " moldNo=" + moldNo
                + " date=" + reportDate, e);
        }
    }

    // =========================================================================
    //  FG History
    // =========================================================================

    /**
     * ลบ t_mold_fg_history ตั้งแต่ minDate ทุก mold
     */
    public void deleteFGHistory(Date minDate) {
        String sql = "DELETE FROM t_mold_fg_history WHERE reportdate >= ?";
        java.sql.Date sqlDate = new java.sql.Date(minDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                int deleted = ps.executeUpdate();
                c.commit();
                log.info("[MLD_B01] deleteFGHistory deleted=" + deleted + " rows (>= " + sqlDate + ")");
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("deleteFGHistory failed", e);
        }
    }

    /**
     * INSERT INTO t_mold_fg_history จาก t_fg_detail สำหรับวันที่ reportDate
     */
    public void insertFGHistoryForDate(Date reportDate) {
        String sql =
            "INSERT INTO t_mold_fg_history (reportdate, moldid, moldno, totalfgsold) "
          + "SELECT fgout.reportdate AS reportDate, mc.moldid AS moldId, md.moldno AS moldNo, "
          + "       SUM(fgout.fgout) AS totalFGSold "
          + "FROM ( "
          + "    SELECT reportdate, fgid, customerid, SUBSTRING(lotno,1,11) AS lotno, "
          + "           SUM(fgin) AS fgin, SUM(fgout) AS fgout "
          + "    FROM t_fg_detail "
          + "    WHERE reportdate = ? AND reporttype = 1 AND fgout IS NOT NULL "
          + "    GROUP BY reportdate, fgid, customerid, SUBSTRING(lotno,1,11) "
          + ") fgout "
          + "INNER JOIN t_dailymc_lot mclot ON fgout.lotno = mclot.lotno "
          + "INNER JOIN t_dailymc mc ON mc.dailymcid = mclot.dailymcid "
          + "INNER JOIN m_mold_part mp ON mp.partid = mc.partid AND mp.moldid = mc.moldid "
          + "INNER JOIN m_mold_detail md ON mp.moldid = md.moldid "
          + "WHERE fgout.reportdate BETWEEN md.startdate AND ISNULL(md.enddate,'9999-12-31') "
          + "GROUP BY fgout.reportdate, mc.moldid, md.moldno";
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
            throw new RuntimeException("insertFGHistoryForDate failed date=" + reportDate, e);
        }
    }

    // =========================================================================
    //  Update m_mold_detail (FG stats)
    // =========================================================================

    /**
     * MERGE m_mold_detail: คำนวณ totalfgsold + fgstatus จาก t_mold_fg_history ทั้งหมด
     * เรียกครั้งเดียวหลังจาก FG history insert เสร็จทั้งหมด
     */
    public void updateMoldDetailFG() {
        String sql =
            "MERGE INTO m_mold_detail md "
          + "USING ( "
          + "    SELECT ms.moldid, ms.moldno, (ms2.initialshot + ms.totalfg) AS totalfg, "
          + "           CASE "
          + "               WHEN ms2.initialshot + ms.totalfg <= alertshot THEN 1 "
          + "               WHEN ms2.initialshot + ms.totalfg >  alertshot "
          + "                    AND ms2.initialshot + ms.totalfg <= guaranteeshot THEN 2 "
          + "               WHEN ms2.initialshot + ms.totalfg >  guaranteeshot THEN 3 "
          + "               ELSE 0 "
          + "           END AS fgstatus "
          + "    FROM ( "
          + "        SELECT moldid, moldno, ISNULL(SUM(totalfgsold),0) AS totalfg "
          + "        FROM t_mold_fg_history "
          + "        GROUP BY moldid, moldno "
          + "    ) ms "
          + "    INNER JOIN m_mold_detail ms2 ON ms.moldid = ms2.moldid AND ms.moldno = ms2.moldno "
          + ") us "
          + "ON (md.moldid = us.moldid AND md.moldno = us.moldno) "
          + "WHEN MATCHED THEN "
          + "  UPDATE SET md.totalfgsold = us.totalfg, md.fgstatus = us.fgstatus;";
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (Statement st = c.createStatement()) {
                st.executeUpdate(sql);
                c.commit();
                log.info("[MLD_B01] updateMoldDetailFG done");
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("updateMoldDetailFG failed", e);
        }
    }
}
