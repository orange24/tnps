package th.co.nttdata.tki.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * DAO สำหรับ WipStockBatchJob
 *
 * แนวทาง fetch:
 *   - fetch แต่ละ table แยกกันด้วย simple WHERE/GROUP BY
 *   - IN clause แบ่งเป็น chunk ≤500 (ป้องกัน SQL Server slowdown)
 *   - ไม่มี multi-table JOIN ใน query → DB CPU ต่ำ, Java ทำ join แทน
 *
 * แนวทาง write:
 *   - DELETE ด้วย partid IN (...)
 *   - INSERT / UPDATE ด้วย PreparedStatement.executeBatch()
 *
 * Java 7 compatible: ไม่ใช้ lambda, stream, computeIfAbsent, merge
 */
public class WipStockBatchDao {

    private static final Logger log   = Logger.getLogger(WipStockBatchDao.class);
    private static final int    CHUNK = 500;

    private final DataSource dataSource;

    public WipStockBatchDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // =========================================================================
    //  Fetch methods
    // =========================================================================

    public List<Integer> getAllPartIds() {
        List<Integer> ids = new ArrayList<Integer>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT partid FROM m_part");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ids.add(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("getAllPartIds failed", e);
        }
        return ids;
    }

    /** partId → TreeMap<wipOrder, wip>  (iscalc=1 เท่านั้น เรียงตาม wipOrder) */
    public Map<Integer, NavigableMap<Integer, String>> getCalcOrderedWipMap(List<Integer> partIds) {
        Map<Integer, NavigableMap<Integer, String>> result =
                new HashMap<Integer, NavigableMap<Integer, String>>();
        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT partid, wip, wiporder FROM m_part_wip "
                       + "WHERE iscalc = 1 AND partid IN (" + ph(chunk.size()) + ")";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                setInts(ps, 1, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int    partId   = rs.getInt("partid");
                        String wip      = rs.getString("wip");
                        int    wipOrder = rs.getInt("wiporder");
                        NavigableMap<Integer, String> wipMap = result.get(partId);
                        if (wipMap == null) {
                            wipMap = new TreeMap<Integer, String>();
                            result.put(partId, wipMap);
                        }
                        wipMap.put(wipOrder, wip);
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("getCalcOrderedWipMap failed", e); }
        }
        return result;
    }

    /** (reportDate, wip, partId) → SummaryAgg จาก t_daily_summary */
    public Map<WipDateKey, SummaryAgg> getDailySummaryMap(List<Integer> partIds, Date reportDate) {
        Map<WipDateKey, SummaryAgg> result = new HashMap<WipDateKey, SummaryAgg>();
        java.sql.Date sqlDate = toSqlDate(reportDate);
        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT ds.reportdate, ds.wip, ds.partid,"
                       + "  SUM(ds.ok) AS ok, SUM(ds.ng) AS ng, SUM(ds.pd) AS pd, SUM(ds.qty) AS qty "
                       + "FROM t_daily_summary ds "
                       + "INNER JOIN m_part_wip w ON ds.wip = w.wip AND ds.partid = w.partid "
                       + "WHERE ds.reportdate = ? AND ds.reporttype IN (1,2) AND w.iscalc = 1 "
                       + "  AND ds.partid IN (" + ph(chunk.size()) + ") "
                       + "GROUP BY ds.reportdate, ds.wip, ds.partid";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                setInts(ps, 2, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        WipDateKey key = new WipDateKey(rs.getDate("reportdate"),
                                                        rs.getString("wip"), rs.getInt("partid"));
                        SummaryAgg agg = new SummaryAgg();
                        agg.ok  = rs.getInt("ok");
                        agg.ng  = rs.getInt("ng");
                        agg.pd  = rs.getInt("pd");
                        agg.qty = rs.getInt("qty");
                        result.put(key, agg);
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("getDailySummaryMap failed", e); }
        }
        return result;
    }

    /** (wip, partId) → ISNULL(adjuststock, currentstock) ของเมื่อวาน */
    public Map<WipKey, Integer> getPrevStockMap(List<Integer> partIds, Date prevDate) {
        Map<WipKey, Integer> result = new HashMap<WipKey, Integer>();
        java.sql.Date sqlDate = toSqlDate(prevDate);
        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT wip, partid, ISNULL(adjuststock, currentstock) AS stock "
                       + "FROM t_wip_stock "
                       + "WHERE reportdate = ? AND partid IN (" + ph(chunk.size()) + ")";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                setInts(ps, 2, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.put(new WipKey(rs.getString("wip"), rs.getInt("partid")),
                                   rs.getInt("stock"));
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("getPrevStockMap failed", e); }
        }
        return result;
    }

    /** (reportDate, wip, partId) → adjuststock ล่าสุด (MAX wipstockadjustid) */
    public Map<WipDateKey, Integer> getLatestAdjustStockMap(List<Integer> partIds, Date reportDate) {
        Map<WipDateKey, Integer> result = new HashMap<WipDateKey, Integer>();
        java.sql.Date sqlDate = toSqlDate(reportDate);
        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT wip, partid, reportdate, adjuststock "
                       + "FROM t_wip_stockadjust "
                       + "WHERE reportdate = ? AND partid IN (" + ph(chunk.size()) + ") "
                       + "ORDER BY wipstockadjustid DESC";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                setInts(ps, 2, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        WipDateKey key = new WipDateKey(rs.getDate("reportdate"),
                                                        rs.getString("wip"), rs.getInt("partid"));
                        // putIfAbsent equivalent: keep first (= highest id because ORDER BY DESC)
                        if (!result.containsKey(key)) {
                            result.put(key, rs.getInt("adjuststock"));
                        }
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("getLatestAdjustStockMap failed", e); }
        }
        return result;
    }

    /**
     * Pending adjust: (reportDate, p.wip, p.partid) → AdjAgg
     * คืน PendingAdjResult เพื่อให้ buildReworkAdjMap ต่อยอดได้
     */
    public PendingAdjResult fetchPendingAdjData(List<Integer> partIds, Date reportDate) {
        Map<WipDateKey, AdjAgg> pdAdjMap        = new HashMap<WipDateKey, AdjAgg>();
        Map<Integer, Integer>   pdAdjIdToPartId = new HashMap<Integer, Integer>();
        java.sql.Date sqlDate = toSqlDate(reportDate);

        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT p.wip, p.partid, pa.pdadjustid, pa.ok, pa.ng "
                       + "FROM t_pending p "
                       + "INNER JOIN m_part_wip w ON p.wip = w.wip AND p.partid = w.partid AND w.iscalc = 1 "
                       + "INNER JOIN t_pending_adjust pa ON p.pdid = pa.pdid AND pa.reportdate = ? "
                       + "WHERE p.reporttype IN (1,2) AND p.partid IN (" + ph(chunk.size()) + ")";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                setInts(ps, 2, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int    partId     = rs.getInt("partid");
                        String wip        = rs.getString("wip");
                        int    pdAdjustId = rs.getInt("pdadjustid");
                        int    ok         = rs.getInt("ok");
                        int    ng         = rs.getInt("ng");

                        WipDateKey key = new WipDateKey(sqlDate, wip, partId);
                        AdjAgg existing = pdAdjMap.get(key);
                        if (existing == null) {
                            pdAdjMap.put(key, new AdjAgg(ok, ng, ok + ng));
                        } else {
                            pdAdjMap.put(key, AdjAgg.add(existing, new AdjAgg(ok, ng, ok + ng)));
                        }
                        pdAdjIdToPartId.put(pdAdjustId, partId);
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("fetchPendingAdjData failed", e); }
        }
        return new PendingAdjResult(pdAdjMap, pdAdjIdToPartId);
    }

    /**
     * Rework adjust: (reportDate, pr.wip, p.partid) → AdjAgg
     * ต่อยอดจาก pdAdjIdToPartId ของ fetchPendingAdjData
     */
    public Map<WipDateKey, AdjAgg> buildReworkAdjMap(Map<Integer, Integer> pdAdjIdToPartId,
                                                      Date reportDate) {
        Map<WipDateKey, AdjAgg> result = new HashMap<WipDateKey, AdjAgg>();
        if (pdAdjIdToPartId.isEmpty()) return result;

        List<Integer> pdAdjIds = new ArrayList<Integer>(pdAdjIdToPartId.keySet());
        java.sql.Date sqlDate  = toSqlDate(reportDate);

        // Step 1: t_pending_rework → pdreworkid → (pdadjustid, pr.wip)
        Map<Integer, Integer> reworkIdToAdjId = new HashMap<Integer, Integer>();
        Map<Integer, String>  reworkIdToWip   = new HashMap<Integer, String>();

        for (List<Integer> chunk : toChunks(pdAdjIds)) {
            String sql = "SELECT pdreworkid, pdadjustid, wip FROM t_pending_rework "
                       + "WHERE pdadjustid IN (" + ph(chunk.size()) + ")";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                setInts(ps, 1, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        reworkIdToAdjId.put(rs.getInt("pdreworkid"), rs.getInt("pdadjustid"));
                        reworkIdToWip.put(rs.getInt("pdreworkid"), rs.getString("wip"));
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("buildReworkAdjMap step1 failed", e); }
        }

        if (reworkIdToAdjId.isEmpty()) return result;

        // Step 2: t_rework_adjust → aggregate
        List<Integer> pdreworkIds = new ArrayList<Integer>(reworkIdToAdjId.keySet());
        for (List<Integer> chunk : toChunks(pdreworkIds)) {
            String sql = "SELECT pdreworkid, ok, ng FROM t_rework_adjust "
                       + "WHERE pdreworkid IN (" + ph(chunk.size()) + ")";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                setInts(ps, 1, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int pdreworkid = rs.getInt("pdreworkid");
                        int ok         = rs.getInt("ok");
                        int ng         = rs.getInt("ng");

                        Integer adjId  = reworkIdToAdjId.get(pdreworkid);
                        String  prWip  = reworkIdToWip.get(pdreworkid);
                        if (adjId == null || prWip == null) continue;

                        Integer partId = pdAdjIdToPartId.get(adjId);
                        if (partId == null) continue;

                        WipDateKey key    = new WipDateKey(sqlDate, prWip, partId);
                        AdjAgg     existing = result.get(key);
                        if (existing == null) {
                            result.put(key, new AdjAgg(ok, ng, ok + ng));
                        } else {
                            result.put(key, AdjAgg.add(existing, new AdjAgg(ok, ng, ok + ng)));
                        }
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("buildReworkAdjMap step2 failed", e); }
        }
        return result;
    }

    /** partId → fgId  (MIN fgid, isactive=1) */
    public Map<Integer, Integer> getPartToFgMap(List<Integer> partIds) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT partid, MIN(fgid) AS fgid FROM m_fg_part "
                       + "WHERE isactive = 1 AND partid IN (" + ph(chunk.size()) + ") "
                       + "GROUP BY partid";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                setInts(ps, 1, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) result.put(rs.getInt("partid"), rs.getInt("fgid"));
                }
            } catch (SQLException e) { throw new RuntimeException("getPartToFgMap failed", e); }
        }
        return result;
    }

    /** fgId → List<partId>  (ใช้หา assy part) */
    public Map<Integer, List<Integer>> getFgToPartsMap(Set<Integer> fgIds) {
        Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
        if (fgIds.isEmpty()) return result;
        for (List<Integer> chunk : toChunks(new ArrayList<Integer>(fgIds))) {
            String sql = "SELECT fgid, partid FROM m_fg_part "
                       + "WHERE isactive = 1 AND fgid IN (" + ph(chunk.size()) + ") "
                       + "ORDER BY fgid, partid";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                setInts(ps, 1, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int fgId   = rs.getInt("fgid");
                        int partId = rs.getInt("partid");
                        List<Integer> parts = result.get(fgId);
                        if (parts == null) {
                            parts = new ArrayList<Integer>();
                            result.put(fgId, parts);
                        }
                        parts.add(partId);
                    }
                }
            } catch (SQLException e) { throw new RuntimeException("getFgToPartsMap failed", e); }
        }
        return result;
    }

    /** fgId → SUM(fgin) */
    public Map<Integer, Integer> getFgStockMap(Set<Integer> fgIds, Date reportDate) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        if (fgIds.isEmpty()) return result;
        java.sql.Date sqlDate = toSqlDate(reportDate);
        for (List<Integer> chunk : toChunks(new ArrayList<Integer>(fgIds))) {
            String sql = "SELECT fgid, ISNULL(SUM(fgin), 0) AS totalfgin "
                       + "FROM t_fg_stock WHERE reportdate = ? AND fgid IN (" + ph(chunk.size()) + ") "
                       + "GROUP BY fgid";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate(1, sqlDate);
                setInts(ps, 2, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) result.put(rs.getInt("fgid"), rs.getInt("totalfgin"));
                }
            } catch (SQLException e) { throw new RuntimeException("getFgStockMap failed", e); }
        }
        return result;
    }

    /** (wip, partId) ที่เคยมีใน t_wip_stockadjust (Source C ของ part base rows) */
    public Set<WipKey> getAdjustKeySet(List<Integer> partIds) {
        Set<WipKey> result = new HashSet<WipKey>();
        for (List<Integer> chunk : toChunks(partIds)) {
            String sql = "SELECT DISTINCT wip, partid FROM t_wip_stockadjust "
                       + "WHERE partid IN (" + ph(chunk.size()) + ")";
            try (Connection c = dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                setInts(ps, 1, chunk);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        result.add(new WipKey(rs.getString("wip"), rs.getInt("partid")));
                }
            } catch (SQLException e) { throw new RuntimeException("getAdjustKeySet failed", e); }
        }
        return result;
    }

    // =========================================================================
    //  Batch Control methods  (m_batch_control)
    // =========================================================================

    public boolean isBatchRunning(String batchCode) {
        String sql = "SELECT batchstatus FROM m_batch_control WHERE batchcode = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, batchCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("batchstatus") == 1;
            }
        } catch (SQLException e) {
            log.warn("isBatchRunning check failed — assuming not running: " + e.getMessage());
        }
        return false;
    }

    /**
     * UPSERT สถานะลง m_batch_control
     * status: 0=SUCCESS, 1=RUNNING, 2=FAILED
     */
    public void upsertBatchControl(String batchCode, String batchName, int status, String runBy) {
        String sql = "MERGE m_batch_control AS t "
                   + "USING (VALUES (?)) AS s(batchcode) ON t.batchcode = s.batchcode "
                   + "WHEN MATCHED THEN "
                   + "  UPDATE SET batchstatus = ?, batchname = ?, runby = ?, "
                   + "             startdate  = CASE WHEN ? = 1 THEN GETDATE() ELSE t.startdate END, "
                   + "             finishdate = CASE WHEN ? <> 1 THEN GETDATE() ELSE t.finishdate END "
                   + "WHEN NOT MATCHED THEN "
                   + "  INSERT (batchcode, batchname, batchstatus, startdate, finishdate, runby) "
                   + "  VALUES (?, ?, ?, GETDATE(), NULL, ?);";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, batchCode);
            ps.setInt   (2, status);
            ps.setString(3, batchName);
            ps.setString(4, runBy);
            ps.setInt   (5, status);
            ps.setInt   (6, status);
            ps.setString(7, batchCode);
            ps.setString(8, batchName);
            ps.setInt   (9, status);
            ps.setString(10, runBy);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("upsertBatchControl failed (status=" + status + ")", e);
        }
    }

    // =========================================================================
    //  Write methods
    // =========================================================================

    /**
     * DELETE records ของ chunk แล้ว batch INSERT ใหม่
     * แต่ละ chunk มี partId ไม่ซ้ำกัน (safe parallel DELETE)
     */
    public void deleteAndInsert(List<WipStockDto> chunk, Date reportDate) {
        if (chunk.isEmpty()) return;

        Set<Integer> chunkPartIds = new HashSet<Integer>();
        for (WipStockDto w : chunk) chunkPartIds.add(w.partId);
        List<Integer> pidList = new ArrayList<Integer>(chunkPartIds);
        java.sql.Date sqlDate = toSqlDate(reportDate);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String delSql = "DELETE FROM t_wip_stock "
                              + "WHERE reportdate = ? AND partid IN (" + ph(pidList.size()) + ")";
                try (PreparedStatement ps = conn.prepareStatement(delSql)) {
                    ps.setDate(1, sqlDate);
                    setInts(ps, 2, pidList);
                    ps.executeUpdate();
                }

                String insSql = "INSERT INTO t_wip_stock"
                              + "(reportdate, wip, partid, ok, ng, pd, qty,"
                              + " prevstock, adjuststock, nextwipqty, currentstock) "
                              + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(insSql)) {
                    for (WipStockDto w : chunk) {
                        ps.setDate  (1,  toSqlDate(w.reportDate));
                        ps.setString(2,  w.wip);
                        ps.setInt   (3,  w.partId);
                        ps.setInt   (4,  w.ok);
                        ps.setInt   (5,  w.ng);
                        ps.setInt   (6,  w.pd);
                        ps.setInt   (7,  w.qty);
                        ps.setInt   (8,  w.prevStock);
                        if (w.adjustStock != null) ps.setInt (9, w.adjustStock);
                        else                       ps.setNull(9, Types.INTEGER);
                        ps.setInt   (10, w.nextWipQty);
                        ps.setInt   (11, w.currentStock);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("deleteAndInsert failed", e);
        }
    }

    /** batch UPDATE nextwipqty + currentstock */
    public void batchUpdate(List<WipStockDto> chunk) {
        if (chunk.isEmpty()) return;
        String sql = "UPDATE t_wip_stock SET nextwipqty = ?, currentstock = ? "
                   + "WHERE reportdate = ? AND wip = ? AND partid = ?";
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (WipStockDto w : chunk) {
                    ps.setInt   (1, w.nextWipQty);
                    ps.setInt   (2, w.currentStock);
                    ps.setDate  (3, toSqlDate(w.reportDate));
                    ps.setString(4, w.wip);
                    ps.setInt   (5, w.partId);
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("batchUpdate failed", e);
        }
    }

    /** Insert monitoring log ลง t_batch_log */
    public void insertBatchLog(String code, String name, int status,
                               Date reportDate, String runBy) {
        String sql = "INSERT INTO t_batch_log(batchcode, batchname, batchstatus,"
                   + " startdate, finishdate, runby, createdate) "
                   + "VALUES (?,?,?,?,?,?,GETDATE())";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            java.sql.Date d = toSqlDate(reportDate);
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setInt   (3, status);
            ps.setDate  (4, d);
            ps.setDate  (5, d);
            ps.setString(6, runBy != null ? runBy : "WIP_B01");
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("insertBatchLog failed (non-fatal): " + e.getMessage());
        }
    }

    // =========================================================================
    //  JDBC utilities
    // =========================================================================

    /** แบ่ง ids เป็น List ของ chunk ขนาด ≤ CHUNK */
    private List<List<Integer>> toChunks(List<Integer> ids) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < ids.size(); i += CHUNK) {
            result.add(new ArrayList<Integer>(ids.subList(i, Math.min(i + CHUNK, ids.size()))));
        }
        return result;
    }

    private String ph(int n) {
        StringBuilder sb = new StringBuilder(n * 2);
        for (int i = 0; i < n; i++) { if (i > 0) sb.append(','); sb.append('?'); }
        return sb.toString();
    }

    private void setInts(PreparedStatement ps, int offset, List<Integer> ids) throws SQLException {
        for (int i = 0; i < ids.size(); i++) ps.setInt(offset + i, ids.get(i));
    }

    private java.sql.Date toSqlDate(Date d) {
        return d != null ? new java.sql.Date(d.getTime()) : null;
    }

    // =========================================================================
    //  DTOs / Keys
    // =========================================================================

    public static class WipKey {
        public final String wip;
        public final int    partId;
        public WipKey(String wip, int partId) { this.wip = wip; this.partId = partId; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WipKey)) return false;
            WipKey k = (WipKey) o;
            return partId == k.partId && Objects.equals(wip, k.wip);
        }
        @Override public int hashCode() { return Objects.hash(wip, partId); }
    }

    public static class WipDateKey extends WipKey {
        public final java.sql.Date reportDate;
        public WipDateKey(Date reportDate, String wip, int partId) {
            super(wip, partId);
            this.reportDate = (reportDate instanceof java.sql.Date)
                ? (java.sql.Date) reportDate
                : new java.sql.Date(reportDate.getTime());
        }
        @Override public boolean equals(Object o) {
            if (!super.equals(o)) return false;
            if (!(o instanceof WipDateKey)) return false;
            return Objects.equals(reportDate, ((WipDateKey) o).reportDate);
        }
        @Override public int hashCode() { return Objects.hash(reportDate, wip, partId); }
    }

    public static class SummaryAgg {
        public Integer ok, ng, pd, qty;
        public static final SummaryAgg ZERO = new SummaryAgg();
    }

    public static class AdjAgg {
        public Integer ok, ng, qty;
        public AdjAgg() {}
        public AdjAgg(int ok, int ng, int qty) { this.ok = ok; this.ng = ng; this.qty = qty; }
        public static AdjAgg add(AdjAgg a, AdjAgg b) {
            int ao = a.ok != null ? a.ok : 0, bo = b.ok != null ? b.ok : 0;
            int an = a.ng != null ? a.ng : 0, bn = b.ng != null ? b.ng : 0;
            int aq = a.qty != null ? a.qty : 0, bq = b.qty != null ? b.qty : 0;
            return new AdjAgg(ao + bo, an + bn, aq + bq);
        }
        public static final AdjAgg ZERO = new AdjAgg(0, 0, 0);
    }

    public static class WipStockDto {
        public Date    reportDate;
        public String  wip;
        public int     partId;
        public int     ok, ng, pd, qty;
        public int     prevStock;
        public Integer adjustStock;
        public int     nextWipQty;
        public int     currentStock;
    }

    public static class PendingAdjResult {
        public final Map<WipDateKey, AdjAgg> pdAdjMap;
        public final Map<Integer, Integer>   pdAdjustIdToPartId;
        public PendingAdjResult(Map<WipDateKey, AdjAgg> m, Map<Integer, Integer> p) {
            this.pdAdjMap           = m;
            this.pdAdjustIdToPartId = p;
        }
    }
}
