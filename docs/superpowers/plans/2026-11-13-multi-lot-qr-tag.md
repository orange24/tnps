# Multi-Lot QR Tag (6.05) + QR Scan in 6.01 — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** เมนูใหม่ 6.05 พิมพ์ sticker 10×5 cm ที่รวม 1–4 lot seq พร้อม QR (ZPL II) และให้หน้า 6.01 รับการสแกน QR นั้นแล้วแตกเป็นรายแถวต่อ seq

**Architecture:** ตารางใหม่ `t_multilot_tag` / `t_multilot_tag_detail` เก็บว่า sticker เลข ML แต่ละใบมี seq อะไร
- หน้า 6.05 (`FNG_S08`) ใช้ขั้นตอนค้นหา lot และ Preview ของ 6.04 (endpoint `FNG_S06_*` เดิม) แต่สะสม lot ไว้ในตารางเดียว
- ฝั่ง server บันทึก tag/seq ด้วยโค้ดที่แยกออกมาจาก `FNG_S06LogicImpl.printLabel` แล้วสร้าง ML, สร้าง ZPL และส่ง raw ไปที่เครื่องพิมพ์ด้วยกลไก `javax.print` เดิม
- หน้า 6.01 เพิ่มเฉพาะการรับข้อความ QR ในช่อง Lot Seq. No. ส่วน header, validation และการบันทึกใช้ของเดิมทั้งหมด

**Tech Stack:** Java 8, Spring 3.0.5 MVC, iBatis 2.3.4, SQL Server 2012, JSP + jQuery, ZPL II, JUnit 4.12 (เฉพาะคลาส pure Java)

**Spec:** `docs/superpowers/specs/2026-11-11-multi-lot-qr-tag-design.md`

## Global Constraints

- **การแบ่งงาน:** Claude เตรียมโค้ดและสคริปต์ ส่วน Watchara เป็นคน build (`ant`), deploy, รัน DDL ทั้งบน TEST และ PROD และ git push
- **git:** ห้าม `git add -A` ให้ add เฉพาะไฟล์ของ task นั้น (working tree มีงานอื่นค้างอยู่) และ commit เฉพาะเมื่อ Watchara สั่ง
- **ไฟล์ที่ต้องแก้:** SQL map ให้แก้ใน `TKI/config/th/co/nttdata/tki/persistence/` ไม่ใช่สำเนาใน `WebContent/WEB-INF/classes`
- **DB script:** ทำ 2 ไฟล์คู่กัน (`.sql` สำหรับ PROD และ `_TEST.sql` สำหรับ TEST) ตามกฎการเขียนสคริปต์ SQL Server (ไม่ใช้ตัวแปรใน PRINT, ไม่ใช้ subquery ใน VALUES)
- **Deploy:** ต้อง deploy พร้อม WAR เสมอ
- **QR text:**
  - รูปแบบ `<refno>;LOT1:<lotseqno>,Q<qty>,DC<yyyy-MM-dd>;LOT2:...;` (มี `;` ปิดท้ายเสมอ)
  - ถ้า printdate ว่าง ใส่ `DC` เปล่า
- **refno:** `ML` + `yyMMdd` + เลขลำดับ 4 หลักต่อวัน เช่น `ML2611130001`
- **วันที่ DC:** มาจาก `t_lotno.printdate` ของแต่ละ lot (วันที่พิมพ์ lot card ในเมนู 3.03)
- **กฎของ 6.05:**
  - เลือกได้ 1–4 seq ทุก seq ต้องเป็นลูกค้าเดียวกัน FG เดียวกัน และ WIP เดียวกัน
  - ไม่รองรับ lot ที่ `labeltype = 'S'` (special label)
  - ห้ามเลือก seq ที่ `flagprint = 1` หรือที่อยู่บน ML อื่นที่ยังมี `status = 'A'`
- **พิมพ์ซ้ำ:** ใช้ refno เดิม และเพิ่ม `printcount` ทีละ 1
- **ยกเลิก:** ทำได้เฉพาะ ML ที่ยังไม่มี seq ใดถูก FG IN มาก่อน (`SUM(fgin) = 0` ใน `t_fg_detail`) ยกเลิกแล้ว `status = 'C'` และบันทึก cancelby/canceldate
- **เครื่องพิมพ์:** Zebra GK420t และ ZD421 ที่ 203 dpi
  - กำหนดความกว้างพื้นที่พิมพ์ `^PW800`
  - ความสูง `^LL` เก็บเป็นค่าคงที่ `LABEL_HEIGHT_DOTS = 400` (5 cm) ถ้าวัดม้วนจริงได้ 4.5 cm ให้เปลี่ยนเป็น 360
- **ฟอนต์:** `^A0N` เท่านั้น เพราะชื่อลูกค้าและชื่อ FG ไม่มีภาษาไทย ถ้าเจออักขระที่อยู่นอกช่วง ASCII 0x20–0x7E ให้แทนด้วย `?`
- **ไม่แก้พฤติกรรมเดิม** ของ 6.04 และ 6.01 ยกเว้นตัวกันใน 6.04 ที่ระบุไว้ใน Task 6
- **คำสั่งเช็คว่า compile ผ่าน** (รันจาก `TKI/`):
  ```bash
  S=/private/tmp/claude-502/-Volumes-Work-TNPS/086cf71e-fbd0-493d-aad4-6b4c6b96ce31/scratchpad
  CP=$(find WebContent/WEB-INF/lib ext-lib -name "*.jar" | tr '\n' ':')
  find batch business config src -name "*.java" > $S/sources.txt
  /opt/homebrew/opt/openjdk/bin/javac --release 8 -Xlint:-options -encoding UTF-8 -nowarn -d $S/cc -cp "$CP" @$S/sources.txt
  ```
  - ค่าตั้งต้น (baseline) คือ error 1 ตัวที่มีอยู่แล้ว: `CommonController.java:168: package sun.awt does not exist`
  - ถ้ามี error อื่นนอกจากตัวนี้ แปลว่าเป็นของ task ที่ทำอยู่
- **คำสั่งรัน unit test** (รันจาก `TKI/`):
  ```bash
  J=/opt/homebrew/opt/openjdk/bin; M=~/.m2/repository
  JU=$M/junit/junit/4.12/junit-4.12.jar:$M/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
  rm -rf $S/ut && mkdir -p $S/ut
  $J/javac --release 8 -Xlint:-options -encoding UTF-8 -d $S/ut -cp "$JU:WebContent/WEB-INF/lib/commons-lang-2.6.jar" \
    business/th/co/nttdata/tki/blogic/fng/multilot/*.java test/th/co/nttdata/tki/blogic/fng/multilot/*.java
  $J/java -cp "$S/ut:$JU:WebContent/WEB-INF/lib/commons-lang-2.6.jar" org.junit.runner.JUnitCore <TestClass...>
  ```
  - ใช้กับแพ็กเกจ `multilot` ที่ไม่พึ่ง Spring หรือ DB จึงทดสอบแยกได้

## Review Focus

1. **QR ที่สแกนเข้ามาไม่ครบ:** ถ้าตัวสแกนส่งตัวอักษรไม่ครบ หรือคีย์บอร์ดอยู่ภาษาไทย ข้อความจะไม่ตรงรูปแบบ ต้องขึ้นข้อความว่าสแกนไม่สำเร็จและล้างช่อง ห้ามเพิ่มแถวผิดๆ เข้าไป (Task 8, ตรวจด้วย regex เต็มรูปแบบ)
2. **กดพิมพ์ซ้ำ หรือสองคนพิมพ์พร้อมกัน:** เลข ML ต้องไม่ซ้ำ และ seq เดียวกันต้องไม่ไปอยู่บน ML 2 ใบ (Task 5 ใช้ `UPDLOCK, HOLDLOCK` และตรวจซ้ำใน transaction เดียวกัน, Task 1 ใส่ unique index)
3. **ส่งงานพิมพ์ล้มเหลว:** เช่น เครื่องปิด หรือไม่เจอชื่อเครื่องบน server ต้องไม่มีการสร้าง ML และไม่ตั้ง flagprint ทิ้งไว้ (Task 5 ต้อง throw `RuntimeException` เพื่อให้ rollback เพราะ tx advice จะ rollback เฉพาะ RuntimeException)
4. **ชื่อยาวที่สุดจริง + ครบ 4 lot:** ข้อความต้องไม่ล้นช่อง และต้องตัดท้ายด้วย `...` อย่างถูกต้อง (Task 4 มีเทสต์ด้วยชื่อจริงที่ยาวที่สุด)
5. **6.04 แก้ seq ที่อยู่บน ML:** เช่น ลบ แบ่งใหม่ด้วย SNP อื่น หรือพิมพ์เดี่ยว ต้องถูกปฏิเสธพร้อมแสดงเลข ML (Task 3 `MultiLotRules.checkTagEdit` และ Task 6)

---

## File Structure

| ไฟล์ | หน้าที่ |
|---|---|
| `FNG_MULTILOT_QR.sql`, `FNG_MULTILOT_QR_TEST.sql` (root) | สร้างตาราง ML, เมนู 605 และสิทธิ์ของเมนู |
| `TKI/business/.../bean/TMultiLotTag.java`, `TMultiLotTagDetail.java` | bean ของ ML header และรายการ seq |
| `TKI/config/.../persistence/t_multilot_tag.xml` | SQL ทั้งหมดของ ML |
| `TKI/business/.../dao/TMultiLotTagDao.java`, `dao/impl/TMultiLotTagDaoImpl.java` | DAO ของ ML |
| `TKI/business/.../blogic/fng/multilot/MultiLotRules.java` | กฎแบบ pure Java: เลือก seq, เลข refno, ตัวกันของ 6.04 |
| `TKI/business/.../blogic/fng/multilot/MultiLotZpl.java` | สร้างข้อความ QR และ ZPL รวมถึงการย่อตัวอักษร (pure Java) |
| `TKI/business/.../blogic/fng/FNG_S08Logic.java`, `impl/FNG_S08LogicImpl.java` | logic ของ 6.05: พิมพ์รวม, ค้นหา, พิมพ์ซ้ำ, ยกเลิก |
| `TKI/business/.../blogic/fng/impl/FNG_S06LogicImpl.java` | แยก `saveTagAndSequences` และ `sendRaw` ออกมาเพื่อใช้ร่วมกัน, เพิ่มตัวกัน ML |
| `TKI/src/.../controller/fng/FNG_S08Controller.java` | endpoint ของ 6.05 |
| `TKI/WebContent/page/FNG/FNG_S08.jsp`, `FNG_S08.js` | หน้าจอ 6.05 |
| `TKI/WebContent/page/FNG/FNG_S01.js`, `FNG_S01Controller.java`, `FNG_S01Logic(.Impl)` | รับการสแกน QR ใน 6.01 |
| `TKI/config/th/co/nttdata/errorcodes*.properties`, `messages.properties` | ข้อความและชื่อเมนู |
| `TKI/test/th/co/nttdata/tki/blogic/fng/multilot/*Test.java` | unit test |

---

### Task 1: DB script — ตาราง ML, เมนู 6.05 และสิทธิ์

**Files:**
- Create: `/Volumes/Work/TNPS/FNG_MULTILOT_QR.sql` (`USE TKIDB_PROD`)
- Create: `/Volumes/Work/TNPS/FNG_MULTILOT_QR_TEST.sql` (`USE TKIDB_TEST`) เนื้อหาเหมือนไฟล์ PROD ต่างกันแค่ชื่อ DB

**Interfaces:**
- Produces:
  - `t_multilot_tag`
    - `refno varchar(12) PK`
    - `customerid int NOT NULL`, `fgid int NOT NULL`, `wip varchar(10) NOT NULL`
    - `status char(1) NOT NULL DEFAULT 'A'`, `printcount int NOT NULL DEFAULT 1`
    - `printername nvarchar(200) NULL`
    - `createby varchar(50)`, `createdate datetime DEFAULT GETDATE()`, `updateby varchar(50)`, `updatedate datetime`
    - `cancelby varchar(50) NULL`, `canceldate datetime NULL`
  - `t_multilot_tag_detail`
    - `refno varchar(12)`, `lineno int` (PK ประกอบจาก refno + lineno)
    - `lotseqno varchar(20) NOT NULL`, `qty int NOT NULL`, `datecode date NULL`
    - FK `refno` → `t_multilot_tag`
    - non-unique index บน `lotseqno` (ห้ามซ้ำเฉพาะใน ML ที่ใช้งานอยู่ ซึ่งตรวจในโค้ด)
  - `m_menu` แถว `(605, '6.05. Print Multi-Lot Tag', 'FNG_S08.html', 600, 1)`
  - `m_role_menu`: copy ทุก roleid ที่มีสิทธิ์ menuid 604 ไปเป็นสิทธิ์ของ 605
    - ปัจจุบันบน PROD มี 4 role: 1, 13, 28, 32

- [ ] **Step 1: เช็คชนิดคอลัมน์จริงก่อนเขียน DDL** (อ่านอย่างเดียว ด้วย Diag runner)
  - ดูชนิดและความยาวของ `t_lot_sequence.lotseqno`, `t_taglabel.wip`, `t_fg_detail.createby` จาก `INFORMATION_SCHEMA.COLUMNS` แล้วใช้ชนิดเดียวกันในตารางใหม่ (ค่าที่เขียนไว้ด้านบนเป็นค่าเดา)
- [ ] **Step 2: เขียนสคริปต์ให้รันซ้ำได้**
  - ครอบทุก `CREATE TABLE` ด้วย `IF OBJECT_ID(...) IS NULL`
  - เมนูใช้ `INSERT ... SELECT ... WHERE NOT EXISTS`
  - สิทธิ์ใช้ `INSERT INTO m_role_menu (roleid, menuid) SELECT roleid, 605 FROM m_role_menu WHERE menuid = 604 AND roleid NOT IN (SELECT roleid FROM m_role_menu WHERE menuid = 605)`
  - ท้ายสคริปต์ให้ `SELECT` ผลออกมาตรวจ: แถวเมนู 605, จำนวน role ของ 605 และคอลัมน์ของตารางใหม่
- [ ] **Step 3: ส่งให้ Watchara รัน `_TEST.sql` บน TKIDB_TEST**
  - ผลที่ต้องได้: เมนู 605 มี 1 แถว, จำนวน role ของ 605 เท่ากับของ 604, รันซ้ำครั้งที่ 2 แล้วไม่มี error และไม่มีแถวเพิ่ม
- [ ] **Step 4: Claude ยืนยันด้วย query อ่านอย่างเดียวบน TKIDB_TEST**
  - ใช้ `INFORMATION_SCHEMA.COLUMNS` และ `SELECT * FROM m_menu WHERE menuid = 605`
- [ ] **Step 5: Commit** (เมื่อ Watchara สั่ง): `git add FNG_MULTILOT_QR.sql FNG_MULTILOT_QR_TEST.sql`

---

### Task 2: Beans, SQL map และ DAO ของ ML

**Files:**
- Create: `TKI/business/th/co/nttdata/tki/bean/TMultiLotTag.java`, `TMultiLotTagDetail.java` (extends `AbstractBaseBean` มี getter/setter แบบเดียวกับ `TLotSequence`)
- Create: `TKI/config/th/co/nttdata/tki/persistence/t_multilot_tag.xml` (namespace `t_multilot_tag`)
- Modify: `TKI/config/th/co/nttdata/tki/persistence/SqlMapConfig.xml` เพิ่ม `<sqlMap resource=".../t_multilot_tag.xml" />` ต่อจาก `t_taglabel_history.xml` (บรรทัด 71)
- Create: `TKI/business/th/co/nttdata/tki/dao/TMultiLotTagDao.java`, `dao/impl/TMultiLotTagDaoImpl.java` (extends `AbstractBaseDao` และใส่ `@Repository` แบบเดียวกับ DAO อื่น)

**Interfaces:**
- Produces (bean fields):
  - `TMultiLotTag`
    - `refNo`, `customerId`, `fgId`, `wip`, `status`, `printCount`, `printerName`
    - `customerName`, `fgNo`, `fgName`, `wipName` (มาจากการ join)
    - `List<TMultiLotTagDetail> details`
  - `TMultiLotTagDetail`
    - `refNo`, `lineNo`, `lotSeqNo`, `qty`, `dateCode` (`java.util.Date`)
    - field สำหรับ 6.01: `customerId`, `customerCode`, `fgId`, `fgNo`, `fgName`, `lotSeqQty`
- Produces (DAO):
  ```java
  int nextRunning(String yyMMdd);                 // select_next_running: ISNULL(MAX(CAST(RIGHT(refno,4) AS int)),0)+1 ... WITH (UPDLOCK, HOLDLOCK) WHERE refno LIKE 'ML'+#value#+'%'
  void insertHeader(TMultiLotTag tag);            // insert_header
  void insertDetails(TMultiLotTag tag);           // insert_details (<iterate property="details">)
  TMultiLotTag findByRefNo(String refNo);         // select_by_refno: header + customer/fg/wip names
  List<TMultiLotTagDetail> findDetails(String refNo); // select_details ORDER BY lineno
  List<TMultiLotTagDetail> findScanRows(String refNo); // select_scan_rows: details JOIN t_lot_sequence/t_taglabel/m_customer/m_fg (columns เดียวกับ t_lot_sequence.select_lotseq)
  List<String> findActiveRefNosForSeqs(List<String> lotSeqNos); // select_active_by_seq: refno ของ ML ที่ status='A' และมี seq ใดในรายการ (คืน "refno:lotseqno")
  Date findLotPrintDate(String lotNo);            // select_lot_printdate: SELECT printdate FROM t_lotno WHERE lotno=#value#
  int sumFgInForRefNo(String refNo);              // select_sum_fgin: SUM(fgin) ของทุก seq ใน ML จาก t_fg_detail (เทียบ lotno=LEFT(lotseqno,14) AND lot_seqno=RIGHT(lotseqno,4) — t_fg_detail เก็บ lot_seqno 4 หลัก เช่น '1001' ตามที่ 6.01 ส่ง substring(15,19); อย่าลอก RIGHT(...,3) จาก select_lotseq_existing)
  void updateReprint(TMultiLotTag tag);           // update_reprint: printcount=printcount+1, printername, updateby/updatedate
  void updateCancel(TMultiLotTag tag);            // update_cancel: status='C', cancelby, canceldate=GETDATE()
  ```

- [ ] **Step 1: เขียน bean, SQL map และ DAO ตาม Interfaces**
  - ทุก statement ใช้ชื่อคอลัมน์จาก Task 1
  - `createby`/`updateby` ใช้ `#updateBy#` แบบเดียวกับ `t_fg_detail.insertFgDetail`
- [ ] **Step 2: เช็คว่า XML ถูกรูปแบบ**
  - Run: `xmllint --noout TKI/config/th/co/nttdata/tki/persistence/t_multilot_tag.xml TKI/config/th/co/nttdata/tki/persistence/SqlMapConfig.xml`
  - ผลที่ต้องได้: ไม่มี output
- [ ] **Step 3: เช็คว่า SQL ใช้ได้จริงบน TEST**
  - copy SQL ของ statement ประเภท select แต่ละตัวไปรันด้วย Diag runner บน TKIDB_TEST แทนพารามิเตอร์ด้วยค่าจริง เช่น `refno 'ML0000000000'`
  - ผลที่ต้องได้: ไม่มี "Invalid column name" ทุกตัว
- [ ] **Step 4: compile check** ผลที่ต้องได้: มีแค่ error baseline 1 ตัว
- [ ] **Step 5: Commit** (เมื่อ Watchara สั่ง) โดย add เฉพาะ 7 ไฟล์ข้างบน

---

### Task 3: `MultiLotRules` — กฎแบบ pure Java

**Files:**
- Create: `TKI/business/th/co/nttdata/tki/blogic/fng/multilot/MultiLotRules.java`
- Create: `TKI/business/th/co/nttdata/tki/blogic/fng/multilot/Candidate.java`
  - field แบบ public final: `String lotSeqNo; int qty; int customerId; int fgId; String wip; String labelType; boolean printed; String activeRefNo /* null ถ้าไม่ได้อยู่บน ML */`
- Test: `TKI/test/th/co/nttdata/tki/blogic/fng/multilot/MultiLotRulesTest.java`

**Interfaces:**
- Produces:
  ```java
  /** คืน list ของ error code + args; ว่าง = ผ่าน. code เป็น key ใน errorcodes.properties (Task 7) */
  static List<String[]> validateSelection(List<Candidate> selected);
  static String formatRefNo(Date printDate, int running);   // "ML" + yyMMdd + %04d
  /** ตัวกัน 6.04: seq ที่อยู่บน ML (map lotSeqNo->refNo, qty) แต่คำขอจะลบ/เปลี่ยน qty/หายไป/flagPrint=1 → คืน refNo แรกที่ชน, ไม่ชน = null */
  static String checkTagEdit(Map<String, Object[]> mlSeqs /* lotSeqNo -> {refNo, qty} */, List<SeqEdit> incoming);
  ```
  - `SeqEdit` (ไฟล์ `SeqEdit.java` ในแพ็กเกจเดียวกัน): `String lotSeqNo; int qty; boolean deleted; boolean flagPrint`
  - error code ที่ใช้:
    - `err.fng.013` เลือก 0 หรือเกิน 4 seq
    - `err.fng.014` ลูกค้า/FG/WIP ไม่ตรงกัน, args = `{lotSeqNo}`
    - `err.fng.015` special label, args = `{lotSeqNo}`
    - `err.fng.016` seq พิมพ์ไปแล้ว, args = `{lotSeqNo}`
    - `err.fng.017` seq อยู่บน ML อื่น, args = `{lotSeqNo, refNo}`

- [ ] **Step 1: เขียน test ที่ต้อง fail ก่อน**
  ```java
  @Test public void fourSameGroupPasses()        { assertTrue(validateSelection(list(c("A-1001"),c("B-1001"),c("C-1001"),c("D-1001"))).isEmpty()); }
  @Test public void fiveRejected()               { assertEquals("err.fng.013", validateSelection(five()).get(0)[0]); }
  @Test public void emptyRejected()              { assertEquals("err.fng.013", validateSelection(empty()).get(0)[0]); }
  @Test public void otherFgRejected()            { assertArrayEquals(new String[]{"err.fng.014","B-1001"}, validateSelection(list(c("A-1001"), cFg("B-1001", 99))).get(0)); }
  @Test public void otherWipRejected()           { /* wip "MC-8" vs "FL-1" → err.fng.014 */ }
  @Test public void specialLabelRejected()       { /* labelType "S" → err.fng.015 */ }
  @Test public void printedRejected()            { /* printed=true → err.fng.016 */ }
  @Test public void onActiveMlRejected()         { assertArrayEquals(new String[]{"err.fng.017","A-1001","ML2611130001"}, ...); }
  @Test public void refNoFormat()                { assertEquals("ML2611130007", formatRefNo(date(2026,11,13), 7)); }
  @Test public void tagEditDeleteBlocked()       { assertEquals("ML2611130001", checkTagEdit(ml("A-1001","ML2611130001",10), list(edit("A-1001",10,true,false)))); }
  @Test public void tagEditQtyChangeBlocked()    { /* qty 10 → 8 → refNo */ }
  @Test public void tagEditMissingSeqBlocked()   { /* seq ไม่อยู่ใน incoming → refNo */ }
  @Test public void tagEditSinglePrintBlocked()  { /* flagPrint=true → refNo */ }
  @Test public void tagEditUntouchedPasses()     { assertNull(checkTagEdit(ml("A-1001","ML2611130001",10), list(edit("A-1001",10,false,false), edit("A-1002",5,false,true)))); }
  ```
- [ ] **Step 2: รัน test (ใช้คำสั่งใน Global Constraints) ต้อง FAIL** เพราะยังไม่มีคลาส
- [ ] **Step 3: เขียนโค้ดให้ test ผ่าน**
  - ใน `validateSelection` ใช้ seq แรกเป็นตัวอ้างอิงของลูกค้า/FG/WIP
  - ใส่ error ทุกตัวที่เจอ ไม่หยุดที่ตัวแรก ยกเว้น `err.fng.013` ที่ให้คืนทันที
- [ ] **Step 4: รัน `MultiLotRulesTest` ต้อง PASS ครบ 14 ตัว**
- [ ] **Step 5: Commit** (เมื่อ Watchara สั่ง)

---

### Task 4: `MultiLotZpl` — ข้อความ QR, การย่อตัวอักษร และ ZPL

**Files:**
- Create: `TKI/business/th/co/nttdata/tki/blogic/fng/multilot/MultiLotZpl.java`
- Create: `TKI/business/th/co/nttdata/tki/blogic/fng/multilot/LabelData.java`
  - `String refNo, wip, customerName, fgNo, partName; List<LabelLine> lines`
  - `LabelLine`: `String lotSeqNo; int qty; Date dateCode`
- Test: `TKI/test/th/co/nttdata/tki/blogic/fng/multilot/MultiLotZplTest.java`

**Interfaces:**
- Produces:
  ```java
  static final int DPI = 203, LABEL_WIDTH_DOTS = 800, LABEL_HEIGHT_DOTS = 400;
  static final int[] FONT_STEPS = {30, 26, 22, 20, 18, 16};   // dot heights, ใหญ่ → เล็ก
  static String qrText(LabelData d);
  static String asciiOnly(String s);                           // นอก 0x20–0x7E → '?', '^' และ '~' → ' ' (กันชนคำสั่ง ZPL)
  static int fitFontHeight(String text, int boxWidthDots);     // ตัวแรกใน FONT_STEPS ที่ estimateWidth ≤ box, ไม่มีก็คืน 16
  static String fitText(String text, int boxWidthDots);        // ถ้าที่ 16 ยังล้น ตัดท้าย + "..."
  static int estimateWidth(String text, int fontHeight);       // ceil(text.length() * fontHeight * 0.55)
  static String build(LabelData d);                            // ZPL เต็มใบ
  ```
  - เลย์เอาต์ยึดตาม mockup `.superpowers/brainstorm/49896-1794410824/content/label-b-final.html`
    - แถวบน: `KIKUWA GROUP` | WIP
    - ตามด้วยแถว CUSTOMER NAME / FG. NO. / PART NAME (คอลัมน์หัวข้อกว้าง 150 dots)
    - ส่วนล่าง: `LOT. NO.` และ `QTY.` อยู่ซ้าย, ยอดรวมตัวใหญ่ใต้ QTY., รายการ lot 4 แถว (qty + lotseqno), QR อยู่ขวา และเลข refno อยู่ใต้ QR
    - พิกัดละเอียดให้ผู้ทำเลือกเอง แล้วตรวจด้วยการพิมพ์จริงใน Task 9
  - QR: `^FO<x>,<y>^BQN,2,3^FDMA,<qrText>^FS` (error correction M, ขยาย 3 เท่า)
  - ช่องข้อความใช้ `^A0N,<h>,0` และค่าที่จะพิมพ์ต้องผ่าน `asciiOnly` ทุกตัว

- [ ] **Step 1: เขียน test ที่ต้อง fail ก่อน** (ใช้ข้อมูลจริงจาก mockup)
  ```java
  @Test public void qrTextThreeLots() {
    assertEquals("ML2611110001;LOT1:A016825DX-2052-1001,Q5,DC2026-11-09;LOT2:A016826DX-2052-1001,Q10,DC2026-11-09;LOT3:A016827DX-1011-1001,Q10,DC2026-11-10;", qrText(sample3()));
  }
  @Test public void qrTextNullDate()    { assertTrue(qrText(sampleNullDate()).contains(",Q20,DC;")); }
  @Test public void shortTextKeepsBiggestFont() { assertEquals(30, fitFontHeight("MC-8", 200)); }
  @Test public void longestRealCustomerShrinks() {
    int h = fitFontHeight("Mitsubishi Electric Siam Compressor Industry Co., Ltd.", 640);
    assertTrue(h < 30 && estimateWidth("Mitsubishi Electric Siam Compressor Industry Co., Ltd.", h) <= 640);
  }
  @Test public void tooLongIsTruncated() {
    String t = fitText("J03A (IPM4) LED HL (HG) BRACKET A RH 10047-8N01H-50/P EXTRA EXTRA EXTRA", 300);
    assertTrue(t.endsWith("...") && estimateWidth(t, 16) <= 300);
  }
  @Test public void asciiOnlyReplaces()  { assertEquals("AB?C D", asciiOnly("ABกC^D")); }
  @Test public void buildHasSizeAndQr()  {
    String z = build(sample4());
    assertTrue(z.startsWith("^XA") && z.trim().endsWith("^XZ"));
    assertTrue(z.contains("^PW800") && z.contains("^LL400") && z.contains("^BQN,2,3^FDMA," + qrText(sample4()) + "^FS"));
    assertTrue(z.contains("ML2611110002") && z.contains("A016828DX-1011-1001"));
  }
  @Test public void buildTotalQty()      { assertTrue(build(sample4()).contains("^FD35^FS")); } // 5+10+10+10
  ```
- [ ] **Step 2: รัน test ต้อง FAIL**
- [ ] **Step 3: เขียนโค้ดให้ test ผ่าน** (วันที่ใช้ `SimpleDateFormat("yyyy-MM-dd")`)
- [ ] **Step 4: รัน `MultiLotZplTest MultiLotRulesTest` ต้อง PASS ทั้งหมด**
- [ ] **Step 5: เขียน ZPL ตัวอย่าง 3 ไฟล์ไว้ทดสอบพิมพ์จริง**
  - ตัวอย่างที่ใช้: ชื่อปกติ / ชื่อยาวที่สุด + 4 lot / lot เดียว
  - ไฟล์ปลายทาง: `$S/zpl/sample-{1,2,3}.zpl` ผ่าน main method ชั่วคราวใน test
  - ดูผลก่อนพิมพ์จริงได้ที่ labelary viewer (Watchara เป็นคนเปิด เพราะเป็นบริการภายนอก)
- [ ] **Step 6: Commit** (เมื่อ Watchara สั่ง)

---

### Task 5: logic ของ 6.05 — `FNG_S08Logic`

**Files:**
- Modify: `TKI/business/th/co/nttdata/tki/blogic/fng/impl/FNG_S06LogicImpl.java`
  - แยกบรรทัด 150–213 (ช่วงที่บันทึก/อัปเดต taglabel, ลบแล้ว insert lot seq) ออกเป็นเมธอด public ไม่เปลี่ยนพฤติกรรม
  - แยกช่วงที่ส่งงานพิมพ์ (บรรทัด 299–318) ออกเป็น `sendRaw`
  - `printLabel` ต้องเรียกใช้ทั้ง 2 เมธอดนี้แทนโค้ดเดิม
- Modify: `TKI/business/th/co/nttdata/tki/blogic/fng/FNG_S06Logic.java` ประกาศ 2 เมธอดใหม่
- Create: `TKI/business/th/co/nttdata/tki/blogic/fng/FNG_S08Logic.java`, `impl/FNG_S08LogicImpl.java` (`@Service`)

**Interfaces:**
- Consumes: DAO จาก Task 2, `MultiLotRules` จาก Task 3, `MultiLotZpl` จาก Task 4
- Produces:
  ```java
  // FNG_S06Logic
  Integer saveTagAndSequences(TTagLabelDetail tagLabel);       // คืน tagId; เหมือนโค้ดเดิมใน printLabel ทุกอย่าง
  boolean sendRaw(String printerName, byte[] data) throws Exception; // false = ไม่พบเครื่องตามชื่อ
  // FNG_S08Logic
  TMultiLotTag printMultiLot(FNG_S08PrintRequest req);   // req: printerName, List<TTagLabelDetail> lots (แต่ละ lot มี lotSequenceList; seq ที่เลือกคือ flagPrint==1)
  TMultiLotTag findByRefNo(String refNo);                 // header + details; ไม่พบ → errors err.fng.018
  TMultiLotTag reprint(String refNo, String printerName);
  TMultiLotTag cancel(String refNo);                      // fgin>0 → err.fng.019; ยกเลิกแล้ว → err.fng.020
  ```
  - `FNG_S08PrintRequest` เป็น bean ใหม่อยู่ที่ `TKI/business/th/co/nttdata/tki/bean/`
- ลำดับการทำงานของ `printMultiLot` (ทั้งหมดอยู่ใน transaction เดียว ตาม pointcut `blogic.*.*Logic.*`):
  1. เรียก `saveTagAndSequences` ทีละ lot
  2. สร้าง `Candidate` ของ seq ที่เลือก จากข้อมูลใน DB หลังบันทึก ได้แก่ customer/fg/wip/labeltype ของ tag, flagprint ของ seq และ refno จาก `findActiveRefNosForSeqs`
  3. เรียก `validateSelection` ถ้ามี error ให้ throw `MultiLotValidationException` (เป็น RuntimeException และพก error ไปด้วย) เพื่อให้ rollback
  4. ออกเลข refno ด้วย `formatRefNo(new Date(), dao.nextRunning(yyMMdd))` แล้วบันทึก header และรายการ seq
     - `datecode` ใช้ `findLotPrintDate(LEFT(lotSeqNo,14))`
  5. สร้าง ZPL ด้วย `MultiLotZpl.build` แล้วส่งด้วย `sendRaw`
     - ถ้าส่งไม่สำเร็จ ให้ throw `RuntimeException("Printer not found: " + name)`
  6. ตั้ง flagprint ของ seq ที่เลือกด้วย `tLotSequenceDao.updatePrintStatus`
- Controller เป็นคนจับ `MultiLotValidationException` แล้วใส่ error ลง bean เมื่อ rollback แล้ว ส่วน exception อื่นใช้ `setSystemError`

- [ ] **Step 1: refactor `FNG_S06LogicImpl`** โดยไม่เปลี่ยนพฤติกรรม ดู diff แล้วต้องเป็นการย้ายโค้ดเท่านั้น
- [ ] **Step 2: เขียน `FNG_S08Logic` และ impl ตามลำดับข้างบน**
  - `reprint`: build ZPL จากข้อมูลใน DB → `sendRaw` → `updateReprint`
  - `cancel`: ตรวจ `sumFgInForRefNo == 0` และ `status == 'A'` ก่อน แล้วจึง `updateCancel`
- [ ] **Step 3: compile check** ผลที่ต้องได้: มีแค่ error baseline
- [ ] **Step 4: รัน unit test ของ Task 3–4 ซ้ำ ต้อง PASS**
- [ ] **Step 5: Commit** (เมื่อ Watchara สั่ง)

---

### Task 6: ตัวกันใน 6.04

**Files:**
- Modify: `TKI/business/th/co/nttdata/tki/blogic/fng/impl/FNG_S06LogicImpl.java` — ตอนต้นของ **`saveTagAndSequences`** (ไม่ใช่ `printLabel`) เพื่อให้กันได้ทั้งทาง 6.04 และทาง 6.05 ซึ่งบันทึก lot ซ้ำได้ (เช่นผู้ใช้เปลี่ยน SNP ของ lot ที่มี seq อยู่บน ML อื่น)
- Modify: `TKI/business/th/co/nttdata/tki/dao/TMultiLotTagDao.java` + impl + XML เพิ่ม `List<TMultiLotTagDetail> findActiveDetailsByLotNo(String lotNo)` (statement `select_active_by_lotno`: รายการ seq ของ ML ที่ status='A' และ `LEFT(lotseqno,14) = #value#`)

**Interfaces:**
- Consumes: `MultiLotRules.checkTagEdit` จาก Task 3
- Produces: ถ้าชน → throw `MultiLotValidationException` (จาก Task 5) ที่มี error `err.fng.021` args `{refNo}`; `printLabel` จับ exception นี้แล้วคืน `TTagLabelDetail` ที่มี error นั้น (ไม่บันทึก ไม่พิมพ์ เพราะ rollback); `FNG_S08Controller` จับแบบเดียวกับ error ของ Task 5

- [ ] **Step 1: เพิ่ม statement, DAO และการเรียกใช้**
  - แปลง `tagLabel.getLotSequenceList()` เป็น `SeqEdit` โดย `deleted = isDelete()` และ `flagPrint = flagPrint == 1`
  - **ข้อยกเว้นสำหรับ 6.05:** seq ที่ 6.05 กำลังจะใส่ลง ML ใหม่ยังไม่อยู่บน ML ที่ใช้งานอยู่ จึงไม่ชน (`findActiveDetailsByLotNo` คืนเฉพาะ ML ที่ status='A' ที่มีอยู่แล้ว)
- [ ] **Step 2: เทสต์ของกฎนี้อยู่ใน Task 3 แล้ว** (`tagEdit*`) ให้รันซ้ำ ต้อง PASS
- [ ] **Step 3: compile check และ xmllint**
- [ ] **Step 4: Commit** (เมื่อ Watchara สั่ง)

---

### Task 7: หน้าจอ 6.05 (`FNG_S08`) และข้อความ

**Files:**
- Create: `TKI/src/th/co/nttdata/tki/controller/fng/FNG_S08Controller.java` (`PATH_URI = "FNG/FNG_S08"`; จำเครื่องพิมพ์ใน session ด้วย key `FNG_S08printerName` แบบเดียวกับ FNG_S06)
- Create: `TKI/WebContent/page/FNG/FNG_S08.jsp`, `FNG_S08.js` (หัวหน้าจอ ฟอร์ม และ dialog เลือกเครื่องพิมพ์ ให้ยกมาจาก `FNG_S06.jsp` และ `FNG_S06.js` แล้วตัดส่วนที่ไม่ใช้ออก)
- Modify: `TKI/config/th/co/nttdata/messages.properties` เพิ่ม `menu.PrintMultiLotTag = 6.05. Print Multi-Lot Tag` ต่อจากบรรทัด 49
- Modify: `TKI/config/th/co/nttdata/errorcodes.properties`, `errorcodes_th_TH.properties`, `errorcodes_ja_JP.properties` เพิ่ม `err.fng.013`–`err.fng.023` (ข้อความภาษาอังกฤษเหมือนกันทุกไฟล์ ตามแบบของ err.fng.012)

**Interfaces:**
- Endpoints:
  - `GET /FNG_S08`
  - `POST /FNG_S08_print` (`@RequestBody FNG_S08PrintRequest`) → `TMultiLotTag`
  - `GET /FNG_S08_find?refNo=` → `TMultiLotTag`
  - `POST /FNG_S08_reprint` (refNo, printerName)
  - `POST /FNG_S08_cancel` (refNo)
- ใช้ endpoint ของ 6.04 เดิมใน JS: `FNG_S06_getLotNo` (autocomplete), `FNG_S06_getDetailByLotNo`, `FNG_S06_preview`, `FNG_S06_printLabel` (สำหรับปุ่ม "Save And Print (แยกใบ)" ทีละ lot) และ `boxPrinterName`
- ข้อความ error ที่เพิ่ม:
  - `err.fng.013` Please select 1 to 4 Lot Seq. to print together.
  - `err.fng.014` Lot Seq. {0} has a different Customer, FG or WIP.
  - `err.fng.015` Lot Seq. {0} uses a special label and cannot be printed together.
  - `err.fng.016` Lot Seq. {0} was already printed.
  - `err.fng.017` Lot Seq. {0} is already on QR tag {1}.
  - `err.fng.018` QR tag {0} is not found.
  - `err.fng.019` QR tag {0} cannot be cancelled because it was already scanned IN.
  - `err.fng.020` QR tag {0} was cancelled.
  - `err.fng.021` Cannot change Lot Seq. on QR tag {0}. Cancel the QR tag first.
  - `err.fng.022` QR tag {0} was already scanned in this list.
  - `err.fng.023` Cannot read QR. Please scan again.

- [ ] **Step 1: เขียน controller, JSP และ JS ตาม mockup** (`.superpowers/brainstorm/49896-1794410824/content/print-screen-v2.html`)
  - กด Preview แล้วเพิ่มกลุ่ม lot ลงตาราง (ไม่ล้างตาราง)
  - lot ที่ 2 เป็นต้นไปต้องมี customerId/fgId/wip ตรงกับ lot แรก และ labelType ต้องไม่ใช่ 'S' ไม่เช่นนั้น `message.setError` แล้วไม่เพิ่ม
  - ช่องติ๊กของ seq ที่ `printStatus == 1` ให้ disable
  - ตัวนับ "เลือกแล้ว n/4 seq · รวม x ชิ้น" และห้ามติ๊กเกิน 4
  - ส่วนค้นหาด้วยเลข ML มีปุ่ม Reprint และ Cancel
- [ ] **Step 2: compile check และ xmllint ทุกไฟล์ที่แก้**
- [ ] **Step 3: Commit** (เมื่อ Watchara สั่ง)

---

### Task 8: สแกน QR ใน 6.01

**Files:**
- Modify: `TKI/src/th/co/nttdata/tki/controller/fng/FNG_S01Controller.java` เพิ่ม `GET /FNG_S01_getMultiLotTag?refNo=` → `TMultiLotTag` (details มาจาก `findScanRows`)
- Modify: `TKI/business/th/co/nttdata/tki/blogic/fng/FNG_S01Logic.java` + `impl/FNG_S01LogicImpl.java` เพิ่ม `TMultiLotTag getMultiLotTagForScan(String refNo)`
  - ไม่พบ → `err.fng.018`, status='C' → `err.fng.020`
- Modify: `TKI/WebContent/page/FNG/FNG_S01.js`
- Modify: `TKI/WebContent/page/FNG/FNG_S01.jsp` เพิ่ม CSS `.qty-reduced { background:#fff6cc; }`

**Interfaces:**
- Consumes: `TMultiLotTagDetail` fields จาก Task 2
- JS:
  ```js
  var QR_RE = /^(ML\d{10});(LOT[1-4]:[A-Za-z0-9-]{19},Q\d+,DC(\d{4}-\d{2}-\d{2})?;){1,4}$/;
  var scannedRefNos = [];
  function applyLotSeqDetail(tr, d)   // ย้ายส่วนที่ตั้งค่า field ใน getLotSeqDetail (บรรทัด 431–449) มาไว้ที่นี่ ให้ getLotSeqDetail เรียกใช้
  function handleQrScan(input)        // เรียกเมื่อค่าในช่องขึ้นต้นด้วย "ML" และ (กด Enter หรือค่าลงท้ายด้วย ';' และไม่มีการพิมพ์เพิ่มใน 300 ms)
  ```
- ลำดับการทำงานของ `handleQrScan`:
  1. ถ้าค่าไม่ตรง `QR_RE` → `err.fng.023` แล้วล้างช่องและ focus กลับ
  2. ถ้า refNo อยู่ใน `scannedRefNos` แล้ว → `err.fng.022`
  3. `getJSON("FNG_S01_getMultiLotTag", {refNo})` ถ้ามี error ให้แสดงแล้วจบ
  4. วนตาม details:
     - ถ้า lotSeqNo มีอยู่ในตารางแล้ว ให้ข้ามและแจ้ง `err.cmm.020` แบบเดียวกับที่ 6.01 ใช้กับรายการซ้ำ
     - ไม่ซ้ำ ให้ใช้แถวว่างสุดท้าย (หรือ `btnAdd.click()`) ตั้งค่า `#lotSeqNo` เป็น d.lotSeqNo แล้ว `applyLotSeqDetail(tr, d)`
     - ตั้งช่องจำนวน (fgIn หรือ fgOut ตามที่เลือก) = `d.qty` และเก็บ `data-tagqty = d.qty` ไว้ที่ input
  5. เพิ่ม refNo ลง `scannedRefNos` แล้วเพิ่มแถวว่างใหม่ให้ cursor อยู่ที่ช่อง Lot Seq.
- เมื่อช่องจำนวนเปลี่ยน: ถ้า `data-tagqty` มีค่าและจำนวนใหม่น้อยกว่า ให้ใส่ class `qty-reduced` ไม่เช่นนั้นเอาออก
  - การห้ามเกินจำนวนของ seq ใช้ `checkInput` เดิม (`err.fng.001`)
- กด Enter ในช่อง `#lotSeqNo` ให้ `preventDefault` เพื่อไม่ให้ฟอร์ม submit
- autocomplete เดิมยังทำงานเฉพาะค่ายาว 19 ตัว ค่าที่ขึ้นต้นด้วย "ML" จึงไม่ไปกระตุ้น autocomplete
- การบันทึกไม่ต้องแก้: `save()` ส่งทุกแถวเข้า `/FNG_S01_CheckLotNoAndSave` เหมือนเดิม

- [ ] **Step 1: เพิ่ม endpoint และ logic** แล้ว compile check
- [ ] **Step 2: แก้ JS และ JSP ตาม Interfaces**
  - ทดสอบ `QR_RE` กับข้อความตัวอย่าง 3 แบบจาก Task 4 ใน `node -e` ต้อง match ทั้ง 3
  - ข้อความที่ตัดท้ายหายไป 1 ตัว ต้องไม่ match
- [ ] **Step 3: Commit** (เมื่อ Watchara สั่ง)

---

### Task 9: ทดสอบครบวงจรบน TEST (Watchara build/deploy, ทดสอบร่วมกัน)

**Files:** ไม่มีโค้ดใหม่ Claude เตรียม checklist และ query ตรวจผล (อ่านอย่างเดียว) ไว้ที่ `$S/diag/mlcheck.sql`

- [ ] **Step 1: เตรียม TEST**
  - Watchara รัน `FNG_MULTILOT_QR_TEST.sql`, `ant build-TEST` แล้ว deploy
  - วัดความสูงม้วน sticker ถ้าได้ 4.5 cm ให้แก้ `LABEL_HEIGHT_DOTS = 360` แล้วรันเทสต์ Task 4 ใหม่ (ต้องแก้ assertion `^LL400` ตามด้วย)
- [ ] **Step 2: ทดสอบพิมพ์** บนเครื่อง GK420t 1 เครื่องและ ZD421 1 เครื่อง ใช้ 3 กรณีจาก Task 4
  - ข้อความต้องอยู่ในช่องทั้งหมด
  - สแกน QR ด้วย Newland และ Schlongen แล้วข้อความต้องตรงกับที่ query `t_multilot_tag_detail` ได้
- [ ] **Step 3: ทดสอบกฎของ 6.05**
  - เลือก 5 seq / FG ต่างกัน / WIP ต่างกัน / special label / seq ที่พิมพ์แล้ว / seq ที่อยู่บน ML อื่น ทุกกรณีต้องขึ้น error ตามรหัส
  - ปิดเครื่องพิมพ์แล้วกดพิมพ์ ต้องไม่มี ML ถูกสร้าง และ flagprint ต้องไม่เปลี่ยน
  - พิมพ์ซ้ำ: refno เดิม และ printcount เพิ่มขึ้น 1
  - ยกเลิกก่อน IN ต้องสำเร็จ ยกเลิกหลัง IN ต้องขึ้น `err.fng.019`
- [ ] **Step 4: ทดสอบ 6.04**
  - ลบ seq / เปลี่ยน SNP / พิมพ์เดี่ยว seq ที่อยู่บน ML ต้องขึ้น `err.fng.021`
  - lot ที่ไม่เกี่ยวกับ ML ต้องทำงานเหมือนเดิม
- [ ] **Step 5: ทดสอบ 6.01**
  - IN จาก QR 2 กล่อง แล้ว Save ต้องได้แถวใน `t_fg_detail` ครบทุก seq โดย createby เป็น user ที่ login
  - ลดจำนวน 1 แถว: แถวต้องเป็นสีไฮไลต์ และยอดที่บันทึกต้องเป็นตามที่แก้
  - สแกนกล่องเดิมซ้ำ ต้องขึ้น `err.fng.022`
  - ผสม QR กับ barcode เดี่ยวในรายการเดียวกัน
  - OUT ก่อน IN ต้องขึ้น `err.fng.012` เหมือนเดิม
  - สแกนตอนคีย์บอร์ดเป็นภาษาไทย ต้องขึ้น `err.fng.023`
  - ทั้งหมดต้องทดสอบบน PC และบน tablet P8100P
- [ ] **Step 6: ตรวจรายงาน FG stock (6.02)** ยอดต้องตรงกับสิ่งที่สแกน
- [ ] **Step 7: ส่งมอบให้ Watchara**
  - deploy PROD: `FNG_MULTILOT_QR.sql` + `ant build-prod` พร้อมกัน
  - ติดตั้งเครื่องพิมพ์ที่จะใช้บน TKITNPS
  - ตั้งค่าตัวสแกนให้ส่ง Enter ต่อท้าย และตั้งคีย์บอร์ดเป็นภาษาอังกฤษ
