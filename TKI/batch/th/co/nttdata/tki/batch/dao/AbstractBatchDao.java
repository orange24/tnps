package th.co.nttdata.tki.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Base class สำหรับ Batch DAO ทุกตัว
 * รวม isBatchRunning และ upsertBatchControl ที่ทุก batch ใช้เหมือนกัน
 */
public abstract class AbstractBatchDao {

    /** Logger ใช้ชื่อ subclass จริงๆ เช่น WipStockBatchDao, FGStockBatchDao */
    protected final Logger log;
    protected final DataSource dataSource;

    protected AbstractBatchDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.log = Logger.getLogger(getClass());
    }

    // =========================================================================
    //  Batch Control (shared)
    // =========================================================================

    /**
     * true = batchstatus=1 (RUNNING) — ห้าม run ซ้ำ
     */
    public boolean isBatchRunning(String batchCode) {
        String sql = "SELECT batchstatus FROM m_batch_control WHERE batchcode = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, batchCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("batchstatus") == 1;
            }
        } catch (SQLException e) {
            log.warn("[" + batchCode + "] isBatchRunning check failed — assuming not running: "
                    + e.getMessage());
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
          + "  UPDATE SET batchstatus=?, runby=?, "
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
                ps.setString(3,  runBy);
                ps.setInt   (4,  status);
                ps.setInt   (5,  status);
                ps.setString(6,  batchCode);
                ps.setString(7,  batchName);
                ps.setInt   (8,  status);
                ps.setString(9,  runBy);
                ps.setInt   (10, status);
                ps.setInt   (11, status);
                ps.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                "[" + batchCode + "] upsertBatchControl failed (status=" + status + ")", e);
        }
    }
}
