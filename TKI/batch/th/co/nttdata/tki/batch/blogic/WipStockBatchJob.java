package th.co.nttdata.tki.batch.blogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import th.co.nttdata.tki.batch.dao.WipStockBatchDao;
import th.co.nttdata.tki.batch.dao.WipStockBatchDao.AdjAgg;
import th.co.nttdata.tki.batch.dao.WipStockBatchDao.PendingAdjResult;
import th.co.nttdata.tki.batch.dao.WipStockBatchDao.SummaryAgg;
import th.co.nttdata.tki.batch.dao.WipStockBatchDao.WipDateKey;
import th.co.nttdata.tki.batch.dao.WipStockBatchDao.WipKey;
import th.co.nttdata.tki.batch.dao.WipStockBatchDao.WipStockDto;

/**
 * WIP Stock Batch Job — แปลง SP_BATCH_WIP_STOCK_PROCESS เป็น Java
 *
 * รองรับ run ย้อนหลังหลายวัน:
 *   - รับ startDate → loop วันที่ตามลำดับจาก startDate จนถึงวันนี้
 *   - วันที่ต้องประมวลผลตามลำดับ เพราะ prevStock ของวัน D+1 ขึ้นกับ currentStock วัน D
 *   - ข้อมูล static (m_part, m_part_wip, m_fg_part) fetch ครั้งเดียวนอก loop
 *   - prevStockMap carry forward ใน memory ข้ามวัน (ไม่ query DB ซ้ำ)
 *
 * Threading (ภายในแต่ละวัน):
 *   Main thread  : fetch ข้อมูล date-specific → build maps → คำนวณ nextWipQty/currentStock
 *   Worker threads: delete + insert + update แบ่งตาม partId partition
 */
public class WipStockBatchJob extends TimerTask {

    private static final Logger log = Logger.getLogger(WipStockBatchJob.class);

    private static final String BATCH_CODE = "WIP_B01";
    private static final String BATCH_NAME = "WIP Stock Calculate";

    /** จำนวน partId ต่อ 1 worker thread */
    private static final int PARTITION_SIZE = 100;
    private static final int THREAD_COUNT   = 4;

    private final DataSource dataSource;
    private final Date       startDate;  // วันแรกที่ต้องการ recalculate
    private final String excutedBy;

    /**
     * @param dataSource Spring bean "dataSource" (main TKI DB)
     * @param startDate  วันแรกที่ต้องการ recalculate — จะ run ต่อเนื่องถึงวันนี้
     */
    public WipStockBatchJob(DataSource dataSource, Date startDate, String excutedBy) {
        this.dataSource = dataSource;
        this.startDate  = stripTime(startDate);
        this.excutedBy  = excutedBy;
    }

    // =========================================================================
    //  Main flow
    // =========================================================================

    @Override
    public void run() {
        Date today = stripTime(new Date());
        log.info("[WIP_B01] Start — startDate=" + startDate + " → today=" + today);

        WipStockBatchDao dao  = new WipStockBatchDao(dataSource);
        ExecutorService  exec = Executors.newFixedThreadPool(THREAD_COUNT);

        // ── Batch Control: ป้องกัน run ซ้ำ ───────────────────────────────────
        // status 0 = ไม่มีการ run / status 1 = กำลัง run อยู่ → abort
        if (dao.isBatchRunning(BATCH_CODE)) {
            log.warn("[WIP_B01] Batch '" + BATCH_CODE + "' status=1 กำลัง run อยู่ — abort เพื่อป้องกันซ้ำ");
            exec.shutdown();
            return;
        }
        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, 1, this.excutedBy); // status 1 = RUNNING
        log.info("[WIP_B01] Batch Control set RUNNING (status=1)");

        String  errorMsg = null;
        boolean success  = false;
        try {
            // ── [1] Static data ───────────────────────────────────────────────
            dao.insertBatchLog(BATCH_CODE, "getAllPartIds", 1, today, "start");
            List<Integer> allPartIds = dao.getAllPartIds();
            dao.insertBatchLog(BATCH_CODE, "getAllPartIds", 0, today, "parts=" + allPartIds.size());

            if (allPartIds.isEmpty()) {
                log.warn("[WIP_B01] m_part ไม่มีข้อมูล — skipped");
                success = true;
                return;
            }

            dao.insertBatchLog(BATCH_CODE, "getCalcWipMap", 1, today, "start");
            Map<Integer, NavigableMap<Integer, String>> calcWipMap =
                    dao.getCalcOrderedWipMap(allPartIds);
            dao.insertBatchLog(BATCH_CODE, "getCalcWipMap", 0, today, "done");

            dao.insertBatchLog(BATCH_CODE, "getPartToFgMap", 1, today, "start");
            Map<Integer, Integer> partToFgMap = dao.getPartToFgMap(allPartIds);
            Map<Integer, List<Integer>> fgToPartsMap =
                    dao.getFgToPartsMap(new HashSet<>(partToFgMap.values()));
            dao.insertBatchLog(BATCH_CODE, "getPartToFgMap", 0, today, "done");

            Set<WipKey> adjKeySet = dao.getAdjustKeySet(allPartIds);

            Map<WipKey, Integer> prevStockMap =
                    dao.getPrevStockMap(allPartIds, addDays(startDate, -1));
            dao.insertBatchLog(BATCH_CODE, "staticData", 0, today,
                    "parts=" + allPartIds.size() + " adjKeys=" + adjKeySet.size());

            // ── [2] Loop วันที่ ───────────────────────────────────────────────
            int totalDays = 0;
            Date current = startDate;
            while (!current.after(today)) {
                dao.insertBatchLog(BATCH_CODE, "processDate", 1, current,
                        "date=" + current + " day=" + (totalDays + 1));

                prevStockMap = processOneDate(current, dao, exec,
                        allPartIds, calcWipMap, partToFgMap, fgToPartsMap,
                        adjKeySet, prevStockMap);
                totalDays++;

                dao.insertBatchLog(BATCH_CODE, "processDate", 0, current,
                        "date=" + current + " done");
                current = addDays(current, 1);
            }

            dao.insertBatchLog(BATCH_CODE, "WIP_B01 complete", 0, today,
                    "totalDays=" + totalDays);
            success = true;

        } catch (Exception e) {
            errorMsg = causeChain(e);
            log.error("[WIP_B01] Failed: " + errorMsg, e);
            dao.insertBatchLog(BATCH_CODE, "WIP_B01 ERROR", 2, today, errorMsg);
            throw new RuntimeException("[WIP_B01] Batch failed", e);
        } finally {
            exec.shutdown();
            int finalStatus = success ? 0 : 2;
            dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, finalStatus, this.excutedBy,
                                   success ? null : errorMsg);
            log.warn("[WIP_B01] Batch Control set " + (success ? "SUCCESS (0)" : "FAILED (2)"));
        }
    }

    // =========================================================================
    //  Process หนึ่งวัน → คืน prevStockMap สำหรับวันถัดไป
    // =========================================================================

    /**
     * ประมวลผล WIP stock สำหรับ reportDate เดียว
     *
     * @param prevStockMap  ผลลัพธ์ currentstock ของวันก่อนหน้า (carry forward จาก memory)
     * @return              prevStockMap สำหรับวัน reportDate+1
     *                      (= ISNULL(adjuststock, currentstock) ของวันนี้)
     */
    private Map<WipKey, Integer> processOneDate(
            Date                                        reportDate,
            WipStockBatchDao                            dao,
            ExecutorService                             exec,
            List<Integer>                               allPartIds,
            Map<Integer, NavigableMap<Integer, String>> calcWipMap,
            Map<Integer, Integer>                       partToFgMap,
            Map<Integer, List<Integer>>                 fgToPartsMap,
            Set<WipKey>                                 adjKeySet,
            Map<WipKey, Integer>                        prevStockMap)
            throws InterruptedException, ExecutionException {

        // ── Fetch date-specific data ──────────────────────────────────────────
        Map<WipDateKey, SummaryAgg> dailySummaryMap =
                dao.getDailySummaryMap(allPartIds, reportDate);

        Map<WipDateKey, Integer> adjustStockMap =
                dao.getLatestAdjustStockMap(allPartIds, reportDate);

        PendingAdjResult pendingResult =
                dao.fetchPendingAdjData(allPartIds, reportDate);
        Map<WipDateKey, AdjAgg> pdAdjMap = pendingResult.pdAdjMap;
        // buildReworkAdjMap: query จาก t_rework_adjust.reportdate โดยตรง (วันที่ user กรอก rework)
        // แทนการ chain จาก pdAdjustId ซึ่งกรองด้วยวัน pending เดิม → rework ข้ามวันหายได้
        Map<WipDateKey, AdjAgg> rwAdjMap =
                dao.buildReworkAdjMap(allPartIds, reportDate);

        // fgStockMap ขึ้นกับวัน (fg stock ผลิตได้ในวันนั้น)
        Map<Integer, Integer> fgStockMap =
                dao.getFgStockMap(new HashSet<>(partToFgMap.values()), reportDate);

        // ── Build part base rows (Source A ∪ Source B ∪ Source C) ──────────────
        Set<WipDateKey> partRows = new LinkedHashSet<>();

        // Source A (แก้ bug จาก SP): parts ที่มี WIP stock เมื่อวาน → carry forward วันนี้
        // SP เดิม: JOIN condition (d.reportDate = s.reportdate) ขัดแย้งกับ WHERE (s.reportdate = d.reportDate-1)
        // ทำให้ return 0 rows เสมอ — แก้โดยใช้ key ของ prevStockMap ที่มีอยู่แล้ว ไม่ต้อง query DB เพิ่ม
        // ผลลัพธ์: part ที่หยุด production ชั่วคราว จะยัง carry prevStock ต่อไปได้ถูกต้อง
        for (WipKey k : prevStockMap.keySet()) {
            partRows.add(new WipDateKey(reportDate, k.wip, k.partId));         // Source A (fixed)
        }

        partRows.addAll(dailySummaryMap.keySet());                             // Source B
        for (WipKey k : adjKeySet) {
            partRows.add(new WipDateKey(reportDate, k.wip, k.partId));         // Source C
        }

        // ── Build WipStockDto list ใน memory ─────────────────────────────────
        List<WipStockDto> wipStocks = new ArrayList<>(partRows.size());
        for (WipDateKey key : partRows) {
            SummaryAgg daily = dailySummaryMap.getOrDefault(key, SummaryAgg.ZERO);
            AdjAgg     pdAdj = pdAdjMap.getOrDefault(key, AdjAgg.ZERO);
            AdjAgg     rwAdj = rwAdjMap.getOrDefault(key, AdjAgg.ZERO);
            // prevStock มาจาก memory (carry forward จากวันก่อน) — ไม่ต้อง query DB
            Integer    prev  = prevStockMap.getOrDefault(new WipKey(key.wip, key.partId), 0);

            WipStockDto dto  = new WipStockDto();
            dto.reportDate   = key.reportDate;
            dto.wip          = key.wip;
            dto.partId       = key.partId;
            dto.ok           = nvl(daily.ok) + nvl(pdAdj.ok) + nvl(rwAdj.ok);
            dto.ng           = nvl(daily.ng) + nvl(pdAdj.ng) + nvl(rwAdj.ng);
            dto.pd           = nvl(daily.pd);
            dto.qty          = nvl(daily.qty) + nvl(pdAdj.qty) + nvl(rwAdj.qty);
            dto.prevStock    = nvl(prev);
            dto.adjustStock  = adjustStockMap.get(key);
            dto.nextWipQty   = 0;
            dto.currentStock = 0;
            wipStocks.add(dto);
        }

        dao.insertBatchLog("WIP_B01", "WIP_B01 Count proc.", 0, reportDate,
                           String.valueOf(wipStocks.size()));

        // ── Delete: single transaction (ป้องกัน deadlock จาก parallel DELETE) ──
        dao.deleteForDate(allPartIds, reportDate);

        // ── Insert: parallel (new rows ไม่ชนกัน) ─────────────────────────────
        List<List<WipStockDto>> partitions = partitionByPartId(wipStocks, PARTITION_SIZE);
        runParallelInsert(exec, partitions, dao);

        dao.insertBatchLog("WIP_B01", "WIP_B01 tracking", 0, reportDate,
                           "Finish insert WIP Stock");

        // ── คำนวณ nextWipQty + currentStock ใน memory (single thread) ─────────
        Map<WipDateKey, WipStockDto> wipStockMap = new HashMap<>(wipStocks.size() * 2);
        for (WipStockDto w : wipStocks) {
            wipStockMap.put(new WipDateKey(w.reportDate, w.wip, w.partId), w);
        }
        calculateNextWipAndCurrentStock(wipStocks, wipStockMap, calcWipMap,
                pdAdjMap, rwAdjMap, partToFgMap, fgToPartsMap, fgStockMap);

        // ── Update: single transaction (ป้องกัน deadlock จาก parallel UPDATE) ──
        dao.batchUpdateAll(wipStocks);

        dao.insertBatchLog("WIP_B01", "WIP_B01 tracking", 0, reportDate,
                           "Finish update WIP Stock");

        log.info("[WIP_B01] date=" + reportDate + " done — " + wipStocks.size() + " records");

        // ── Build prevStockMap สำหรับวันถัดไป (carry forward ใน memory) ───────
        // = ISNULL(adjuststock, currentstock) ของวันนี้
        // ใช้ค่าที่คำนวณแล้วใน memory — ไม่ต้อง query DB รอบใหม่
        Map<WipKey, Integer> nextPrevStockMap = new HashMap<>(wipStocks.size() * 2);
        for (WipStockDto w : wipStocks) {
            int stockForTomorrow = (w.adjustStock != null) ? w.adjustStock : w.currentStock;
            nextPrevStockMap.put(new WipKey(w.wip, w.partId), stockForTomorrow);
        }
        return nextPrevStockMap;
    }

    // =========================================================================
    //  คำนวณ nextWipQty + currentStock (translate จาก RS1→RS2→RS3→RS4 ใน SP)
    // =========================================================================

    private void calculateNextWipAndCurrentStock(
            List<WipStockDto>                          wipStocks,
            Map<WipDateKey, WipStockDto>               wipStockMap,
            Map<Integer, NavigableMap<Integer, String>> calcWipMap,
            Map<WipDateKey, AdjAgg>                    pdAdjMap,
            Map<WipDateKey, AdjAgg>                    rwAdjMap,
            Map<Integer, Integer>                      partToFgMap,
            Map<Integer, List<Integer>>                fgToPartsMap,
            Map<Integer, Integer>                      fgStockMap) {

        for (WipStockDto ws : wipStocks) {
            NavigableMap<Integer, String> orderedWip = calcWipMap.get(ws.partId);

            // sumIscalc check: ถ้า partId ไม่มี iscalc=1 wip เลย → skip
            if (orderedWip == null || orderedWip.isEmpty()) continue;

            // หา wipOrder ของ record นี้
            Integer wipOrder = null;
            for (Map.Entry<Integer, String> e : orderedWip.entrySet()) {
                if (e.getValue().equals(ws.wip)) { wipOrder = e.getKey(); break; }
            }
            if (wipOrder == null) continue; // wip นี้ไม่ได้เป็น iscalc=1 → skip

            int maxOrder = orderedWip.lastKey();
            Map.Entry<Integer, String> nextEntry = orderedWip.higherEntry(wipOrder);

            int nextWipQty;
            if (nextEntry != null) {
                // Case 1: มี next WIP stage ในตัว part เดียวกัน
                String      nextWip = nextEntry.getValue();
                WipDateKey  nextKey = new WipDateKey(ws.reportDate, nextWip, ws.partId);
                WipStockDto nextWs  = wipStockMap.get(nextKey);
                nextWipQty = nextWs != null
                           ? nextWs.ok + nextWs.ng + nextWs.pd
                           : 0;

            } else if (wipOrder == maxOrder) {
                // Case 2: WIP stage สุดท้าย → consume เข้า FG stock
                Integer fgId = partToFgMap.get(ws.partId);
                nextWipQty = fgId != null ? nvl(fgStockMap.get(fgId)) : 0;

            } else {
                // Case 3: assy case → qty ของ WIP แรกของ assy part
                Integer fgId       = partToFgMap.get(ws.partId);
                Integer assyPartId = findAssyPart(fgId, ws.partId, fgToPartsMap);
                if (assyPartId != null) {
                    NavigableMap<Integer, String> assyWips = calcWipMap.get(assyPartId);
                    if (assyWips != null && !assyWips.isEmpty()) {
                        String      assyFirstWip = assyWips.firstEntry().getValue();
                        WipStockDto assyWs       = wipStockMap.get(
                                new WipDateKey(ws.reportDate, assyFirstWip, assyPartId));
                        nextWipQty = assyWs != null ? assyWs.qty : 0;
                    } else {
                        nextWipQty = 0;
                    }
                } else {
                    nextWipQty = 0;
                }
            }

            ws.nextWipQty   = nextWipQty;
            ws.currentStock = ws.prevStock + ws.ok - nextWipQty;
        }
    }

    // =========================================================================
    //  Utility methods
    // =========================================================================

    /**
     * Group WipStockDto ตาม partId แล้ว partition ทีละ partIdsPerChunk partIds
     * ทุก WIP ของ partId เดียวกันอยู่ใน chunk เดียวกัน → safe DELETE แบบ parallel
     */
    private List<List<WipStockDto>> partitionByPartId(List<WipStockDto> wipStocks,
                                                       int partIdsPerChunk) {
        Map<Integer, List<WipStockDto>> byPartId = new LinkedHashMap<Integer, List<WipStockDto>>();
        for (WipStockDto w : wipStocks) {
            List<WipStockDto> list = byPartId.get(w.partId);
            if (list == null) {
                list = new ArrayList<WipStockDto>();
                byPartId.put(w.partId, list);
            }
            list.add(w);
        }
        List<Integer> partIdList = new ArrayList<>(byPartId.keySet());

        List<List<WipStockDto>> result = new ArrayList<>();
        for (int i = 0; i < partIdList.size(); i += partIdsPerChunk) {
            List<WipStockDto> chunk = new ArrayList<>();
            for (int j = i; j < Math.min(i + partIdsPerChunk, partIdList.size()); j++) {
                chunk.addAll(byPartId.get(partIdList.get(j)));
            }
            result.add(chunk);
        }
        return result;
    }

    private Integer findAssyPart(Integer fgId, int currentPartId,
                                  Map<Integer, List<Integer>> fgToPartsMap) {
        if (fgId == null) return null;
        List<Integer> parts = fgToPartsMap.get(fgId);
        if (parts == null) return null;
        for (Integer p : parts) { if (p != currentPartId) return p; }
        return null;
    }

    private void runParallelInsert(ExecutorService exec,
            List<List<WipStockDto>> partitions,
            final WipStockBatchDao dao)
            throws InterruptedException, ExecutionException {
        List<Future<?>> futures = new ArrayList<Future<?>>(partitions.size());
        for (int i = 0; i < partitions.size(); i++) {
            final List<WipStockDto> part = partitions.get(i);
            futures.add(exec.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    dao.insertBatch(part);
                    return null;
                }
            }));
        }
        for (Future<?> f : futures) f.get();
    }

    /** รวม message ของทุก cause ใน exception chain เพื่อให้เห็น root cause */
    private static String causeChain(Throwable t) {
        StringBuilder sb = new StringBuilder();
        while (t != null) {
            if (sb.length() > 0) sb.append(" → ");
            sb.append(t.getClass().getSimpleName()).append(": ").append(t.getMessage());
            t = t.getCause();
        }
        String msg = sb.toString();
        return msg.length() > 500 ? msg.substring(0, 500) : msg;
    }

    private int nvl(Integer v) { return v != null ? v : 0; }

    private Date stripTime(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);      c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private Date addDays(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

}
