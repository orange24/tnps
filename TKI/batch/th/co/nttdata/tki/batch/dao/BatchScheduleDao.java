package th.co.nttdata.tki.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * DAO สำหรับ m_batch_schedule
 * ใช้ใน BatchSchedulerService เพื่อดึง batch ที่ต้องรัน ณ เวลานั้น
 */
public class BatchScheduleDao extends AbstractBatchDao {

    public BatchScheduleDao(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * คืน batchcode ทั้งหมดที่ enabled=1 และ scheduletime ตรงกับเวลาปัจจุบัน (รูปแบบ "HH:mm")
     */
    public List<String> queryScheduledBatches(String currentTime) {
        String sql =
            "SELECT batchcode FROM m_batch_schedule "
          + "WHERE enabled = 1 AND scheduletime = ?";
        List<String> result = new ArrayList<String>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, currentTime);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("batchcode"));
                }
            }
        } catch (SQLException e) {
            log.error("[Scheduler] queryScheduledBatches failed for time=" + currentTime
                    + ": " + e.getMessage(), e);
        }
        return result;
    }
}
