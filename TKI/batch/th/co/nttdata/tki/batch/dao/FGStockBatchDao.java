package th.co.nttdata.tki.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

/**
 * JDBC DAO สำหรับ FG Stock Batch (FNG_B01)
 *
 * Note: insertFGStock loop ต้องเป็น sequential เพราะแต่ละวัน
 * อ่านจาก t_fg_stock ของวันก่อนหน้า (cumulative calculation)
 */
public class FGStockBatchDao extends AbstractBatchDao {

    // PreparedStatement SQL สำหรับ insert — prepare ครั้งเดียว ใช้ซ้ำใน loop
    private static final String INSERT_FG_STOCK_SQL =
        "INSERT INTO t_fg_stock(reportdate, fgid, customerid, fgstock, fgin, fgout, fgbalance, fgadjust) "
      + "SELECT ? AS reportDate, fg.fgId, fg.customerId, "
      + "       ISNULL(ISNULL(yesterday.fgadjust,yesterday.fgbalance),0) AS fgStock, "
      + "       ISNULL(today.fgin,0) AS fgIn, "
      + "       ISNULL(today.fgout,0) AS fgOut, "
      + "       ISNULL(ISNULL(yesterday.fgadjust,yesterday.fgbalance),0) "
      + "           + ISNULL(today.fgin,0) - ISNULL(today.fgout,0) AS fgBalance, "
      + "       adjust.fgadjust_qty AS fgAdjust "
      + "FROM ( "
      + "    SELECT DISTINCT fgs.fgid, fgs.customerid "
      + "    FROM t_fg_stock fgs "
      + "    WHERE fgs.reportdate = DATEADD(d,-1,?) "
      + "    UNION "
      + "    SELECT DISTINCT fgd.fgid, fgd.customerid "
      + "    FROM t_fg_detail fgd "
      + "    WHERE fgd.reportdate = ? "
      + "    UNION "
      + "    SELECT fga.fgid, fga.customerid "
      + "    FROM t_fg_stock_adjust fga "
      + "    WHERE fga.reportdate = ? "
      + ") fg "
      + "LEFT OUTER JOIN ( "
      + "    SELECT fgs.fgid, fgs.customerid, fgs.fgbalance, fgs.fgadjust "
      + "    FROM t_fg_stock fgs "
      + "    WHERE fgs.reportdate = DATEADD(d,-1,?) "
      + ") yesterday ON fg.fgid = yesterday.fgid AND fg.customerid = yesterday.customerid "
      + "LEFT OUTER JOIN ( "
      + "    SELECT fg.fgid, fg.customerid, "
      + "           SUM(ISNULL(fg.fgin,0)) AS fgin, SUM(ISNULL(fg.fgout,0)) AS fgout "
      + "    FROM t_fg_detail fg "
      + "    WHERE fg.reportdate = ? "
      + "    GROUP BY fg.fgid, fg.customerid "
      + ") today ON fg.fgid = today.fgid AND fg.customerid = today.customerid "
      + "LEFT OUTER JOIN ( "
      + "    SELECT fgid, customerid, fgadjust_qty "
      + "    FROM dbo.t_fg_stock_adjust fgadj "
      + "    WHERE EXISTS ( "
      + "        SELECT 1 "
      + "        FROM ( "
      + "            SELECT MAX(fgadjustid) AS adjid "
      + "            FROM dbo.t_fg_stock_adjust "
      + "            WHERE reportdate = ? "
      + "            GROUP BY customerid, fgid "
      + "        ) x "
      + "        WHERE fgadj.fgadjustid = x.adjid "
      + "    ) "
      + ") adjust ON fg.fgid = adjust.fgid AND fg.customerid = adjust.customerid";

    public FGStockBatchDao(DataSource dataSource) {
        super(dataSource);
    }

    // =========================================================================
    //  Query dates
    // =========================================================================

    /**
     * หา minDate จาก t_fg_detail และ t_fg_stock_adjust ที่ update ตั้งแต่ executeDate-1
     * ถ้าไม่มี ใช้ executeDate-1 เป็น default (เหมือน ISNULL ใน original SQL)
     */
    public Date queryMinDate(Date executeDate) {
        String sql =
            "SELECT ISNULL(MIN(d.reportdate), CONVERT(DATETIME, CONVERT(DATE, DATEADD(day,-1,?)))) AS mindate "
          + "FROM ( "
          + "    SELECT reportdate FROM t_fg_detail "
          + "    WHERE CONVERT(DATE,lastupdate) >= CONVERT(DATE, DATEADD(day,-1,?)) "
          + "    UNION ALL "
          + "    SELECT reportdate FROM t_fg_stock_adjust "
          + "    WHERE CONVERT(DATE,lastupdate) >= CONVERT(DATE, DATEADD(day,-1,?)) "
          + ") d";
        java.sql.Date sqlDate = new java.sql.Date(executeDate.getTime());
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);
            ps.setDate(3, sqlDate);
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
     * maxDate = วันนี้ (CONVERT(DATETIME, CONVERT(DATE, GETDATE())))
     */
    public Date queryMaxDate() {
        String sql = "SELECT CONVERT(DATETIME, CONVERT(DATE, GETDATE())) AS maxdate";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                java.sql.Date d = rs.getDate("maxdate");
                return d != null ? new Date(d.getTime()) : new Date();
            }
        } catch (SQLException e) {
            throw new RuntimeException("queryMaxDate failed", e);
        }
        return new Date();
    }

    // =========================================================================
    //  Delete + Insert loop (single connection for performance)
    // =========================================================================

    /**
     * ลบ t_fg_stock ตั้งแต่ minDate เป็นต้นไป
     */
    public void deleteFGStock(Date minDate) {
        String sql = "DELETE FROM t_fg_stock WHERE reportdate >= ?";
        java.sql.Date sqlDate = new java.sql.Date(minDate.getTime());
        try (Connection c = dataSource.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                int deleted = ps.executeUpdate();
                c.commit();
                log.info("[FNG_B01] deleteFGStock deleted=" + deleted + " rows (>= " + sqlDate + ")");
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("deleteFGStock failed", e);
        }
    }

    /**
     * Insert FG Stock ทีละวันตั้งแต่ minDate ถึง maxDate (inclusive) โดยใช้
     * connection เดียวและ PreparedStatement เดียวตลอด loop เพื่อ performance
     *
     * Commit ทีละวัน เพราะ INSERT วันถัดไปต้องอ่าน t_fg_stock ของวันก่อนหน้า
     * ที่ committed แล้ว (cumulative calculation)
     *
     * @return จำนวนวันที่ insert สำเร็จ
     */
    public int insertFGStockLoop(Date minDate, Date maxDate, ProgressCallback callback) {
        List<Date> dates = buildDateList(minDate, maxDate);
        int done = 0;
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_FG_STOCK_SQL)) {
            c.setAutoCommit(false);
            for (Date date : dates) {
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                // 7 parameters — ทั้งหมด = reportDate
                ps.setDate(1, sqlDate); // SELECT ? AS reportDate
                ps.setDate(2, sqlDate); // fg DATEADD(d,-1,?)
                ps.setDate(3, sqlDate); // fg_detail reportdate = ?
                ps.setDate(4, sqlDate); // fg_stock_adjust reportdate = ?
                ps.setDate(5, sqlDate); // yesterday DATEADD(d,-1,?)
                ps.setDate(6, sqlDate); // today reportdate = ?
                ps.setDate(7, sqlDate); // adjust reportdate = ?
                ps.executeUpdate();
                c.commit();
                done++;
                if (callback != null) callback.onDate(date, done, dates.size());
            }
        } catch (SQLException e) {
            throw new RuntimeException("insertFGStockLoop failed at day " + (done + 1), e);
        }
        return done;
    }

    /** Callback สำหรับ log progress ทีละวัน */
    public interface ProgressCallback {
        void onDate(Date date, int done, int total);
    }

    // =========================================================================
    //  Helpers
    // =========================================================================

    private static List<Date> buildDateList(Date minDate, Date maxDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Calendar max = Calendar.getInstance();
        max.setTime(maxDate);
        max.set(Calendar.HOUR_OF_DAY, 23);
        max.set(Calendar.MINUTE, 59);
        max.set(Calendar.SECOND, 59);

        while (!cal.getTime().after(max.getTime())) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
