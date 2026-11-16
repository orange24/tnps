# Multi-Lot QR Tag (เมนู 6.05) และหน้าสแกน Handheld — Design

สถานะ: ฉบับสมบูรณ์ รออนุมัติ · เริ่มคุย 2026-11-11 · เขียน 2026-11-13

## 1. ที่มาและเหตุผล

ปัจจุบันเมนู 6.04 (FNG_S06, Print Tag Label) พิมพ์ sticker **1 ใบต่อ 1 lot seq** ด้วย barcode Code128 ของ lot seq no.
หน้างานต้องการรวมของหลาย lot seq (FG และลูกค้าเดียวกัน) ไว้ในกล่องเดียว จึงต้องการ sticker ใบเดียวที่ระบุได้ว่าในกล่องมี seq อะไรบ้าง จำนวนเท่าไร
แล้วใช้ handheld สแกน sticker นั้นเพื่อทำ FG IN / FG OUT แทนการสแกนทีละ seq ในเมนู 6.01

ระยะแรกใช้เครื่องพิมพ์ Zebra เดิม (GK420t / ZD421) ภายหลังอาจซื้อเครื่องใหม่สำหรับ QR โดยเฉพาะ

## 2. เป้าหมาย / ไม่ใช่เป้าหมาย

**เป้าหมาย**
- เมนูใหม่ 6.05 พิมพ์ sticker 10×5 cm ที่รวมได้ 1–4 lot seq พร้อม QR code
- หน้าสแกนบน handheld: สแกน QR หลายกล่อง แตกเป็นรายแถวต่อ seq แล้วบันทึก FG IN หรือ FG OUT ทีเดียว
- บันทึกลงที่เดียวกับ 6.01 (`t_fg_detail`) เพื่อให้รายงาน FG stock เดิมทุกตัวใช้ได้ทันทีโดยไม่ต้องแก้

**ไม่ใช่เป้าหมาย**
- ไม่แก้พฤติกรรมของ 6.04 และ 6.01 เดิม (ยังใช้งานคู่กันได้)
- ไม่ทำแอป native บน handheld (เป็นหน้าเว็บใน TNPS)
- ไม่รวม seq ข้ามลูกค้าหรือข้าม FG ใน sticker เดียว

**สิ่งที่ถือว่าล้มเหลวแม้ระบบจะทำงานได้**
- ยอด FG stock ผิด เช่น seq เดียวถูกนับ IN ซ้ำจากการสแกน sticker รวมกับสแกน barcode เดี่ยว
- ข้อความบน sticker ล้นช่อง/อ่านไม่ออกเมื่อชื่อลูกค้าหรือ part name ยาว
- บันทึกผ่านแบบเงียบ ๆ แต่บางแถวไม่ถูกบันทึก (ดูข้อ 6.4)

## 3. ข้อจำกัด

- Stack เดิม: Spring 3.0.5 MVC + iBatis 2.3.4, Java 8, JSP + jQuery, build ด้วย Ant (ดู `CLAUDE.md`)
- DB: SQL Server 2012 Standard บน TNPS-SVR (เครื่องเดียวรวมทุก env) — ต้องมี DB script แยก PROD/TEST และ deploy คู่กับ WAR
- 6.04 พิมพ์จาก **ฝั่ง server**: `FNG_S06LogicImpl.printLabel` อ่าน template จาก `WebContent/print_template/` แทนค่า แล้วส่ง raw bytes ผ่าน `javax.print` ไปยัง printer ตามชื่อที่ติดตั้งบน app server (TKITNPS) — เครื่องพิมพ์ทุกเครื่องที่จะใช้ต้องติดตั้งบน app server ด้วย
- template เดิมเป็น EPL2 แต่ sticker ใหม่ใช้ **ZPL II** (ต้องการ QR และฟอนต์ที่ย่อขนาดได้)
- Claude เตรียมโค้ดและสคริปต์; Watchara เป็นคน build, deploy, รัน DDL บน PROD และ git push

## 4. สิ่งที่ตัดสินใจแล้ว (โดยผู้ใช้)

### 4.1 หน้าพิมพ์ 6.05
- ขั้นตอนเหมือน 6.04: ใส่ Lot No. → กด Preview → ระบบแบ่ง seq ตาม Qty/SNP
- ต่างจาก 6.04 ตรงที่ตารางด้านล่าง **ไม่ล้าง** เมื่อ Preview lot ใหม่ — สะสมได้หลาย lot โดยแสดงเป็นกลุ่มต่อ lot
- lot ที่เพิ่มเข้ามาต้องเป็น **ลูกค้าเดียวกันและ FG เดียวกัน** กับที่อยู่ในตาราง ไม่เช่นนั้นเตือนและไม่เพิ่ม (Customer/FG ถูกล็อกหลัง lot แรก)
- ติ๊กเลือก seq ได้ **สูงสุด 4** แล้วกด **"Print รวมในใบเดียว"** → ได้ sticker 1 ใบ
- seq ที่เหลือพิมพ์แยกใบแบบเดิมได้ ("Save And Print (แยกใบ)") หรือเก็บไว้รวมกับกล่องอื่น
- mockup: `.superpowers/brainstorm/49896-1794410824/content/print-screen-v2.html`

### 4.2 Sticker
- ขนาด 10×5 cm, ภาษา ZPL II, เลย์เอาต์ **แบบ B** + ย่อตัวอักษรอัตโนมัติ (mockup: `label-b-final.html` ในโฟลเดอร์เดียวกัน)
- ช่อง: KIKUWA GROUP · WIP/Process · CUSTOMER NAME · FG. NO. · PART NAME · LOT. NO. (สูงสุด 4 แถว: qty + lot seq no.) · QTY. รวม · QR code + เลขอ้างอิงใต้ QR
- ข้อความใน QR (จาก mockup):
  `ML2611110001;LOT1:A016825DX-2052-1001,Q5,DC2026-11-09;LOT2:...;`
  - `ML` + yyMMdd + running 4 หลัก = เลขอ้างอิงของ sticker
  - แต่ละ seq: lot seq no., `Q` จำนวน, `DC` วันที่ (date code)

### 4.3 เครื่องพิมพ์
- ~~เครื่องใหม่ TSC MB241T~~ → ลูกค้าใช้ **เครื่อง Zebra เดิม** (GK420t / ZD421) ไปก่อน (2026-11-13)

### 4.4 หน้าสแกน Handheld
- login ด้วย **user TNPS ของตัวเอง** (ระบบบันทึกผู้สแกนลง createby/updateby)
- ใช้ได้ทั้ง **FG IN และ FG OUT**
- สแกนหลายกล่องแล้วกด **Save ทีเดียว**
- QR 1 ใบ **แตกเป็นรายแถวต่อ seq** เหมือนแถวในเมนู 6.01 และ **บันทึกลง `t_fg_detail` ที่เดียวกับ 6.01**
- จำนวนขึ้นให้อัตโนมัติจาก sticker แต่ **แก้ไขได้ทีละ seq** (กรณีของเสีย) — ห้ามเกินจำนวนของ seq นั้น และแถวที่ถูกลดจำนวนต้อง **ไฮไลต์**

## 5. ข้อมูลอ้างอิงจากโค้ดปัจจุบัน

| เรื่อง | ที่อยู่ |
|---|---|
| 6.01 FG In/Out หน้าจอ | `TKI/WebContent/page/FNG/FNG_S01.jsp/.js` — header: Report Date, IN/OUT (radio, ล็อกหลังกด OK), Type (`reportType`) |
| 6.01 save | `FNG_S01Controller.checkLotNo` (`/FNG_S01_CheckLotNoAndSave`) → `clearDuplicateLotSeqNo`, `skipBlankRow`, `checkLotNo` (err.fng.006 ซ้ำ), `checkNotExist` → `FNG_S01LogicImpl.save` (err.fng.012 OUT ที่ยังไม่ IN) |
| SQL บันทึก | `t_fg_detail.xml` → `insertFgDetail` (MERGE ต่อแถว; ข้ามแถวที่ยอดรวมเกิน `lotseqqty` หรือติดลบ **โดยไม่แจ้ง**) |
| 6.04 พิมพ์ | `FNG_S06Controller` (`/FNG_S06_preview`, `/FNG_S06_printLabel`), `FNG_S06LogicImpl.printLabel` — insert/update `t_taglabel`, ลบแล้ว insert `t_lot_sequence` ตาม tagId, `updatePrintStatus` ต่อ seq |
| template เดิม | `WebContent/print_template/normal.txt`, `special.txt` (EPL2) — path อยู่ใน context-param `normal_label_template`/`special_label_template` |

## 6. การออกแบบ (ส่วนที่ผู้ใช้ไม่ได้กำหนด — เป็นการตัดสินใจของ Claude)

### 6.1 ข้อมูล
ตารางใหม่ 2 ตาราง เพื่อเก็บว่า sticker รวมแต่ละใบมี seq อะไร *(Claude's call)*
- `t_multilot_tag` — `refno` (PK, `MLyyMMddNNNN`), customerid, fgid, wip, printername, createby/createdate, updateby/updatedate
- `t_multilot_tag_detail` — `refno`, `lineno` (1–4), `lotseqno`, `qty`, `datecode`

running number ออกใน transaction เดียวกับการ insert (ใช้ `MAX+1` ภายใต้ `UPDLOCK, HOLDLOCK` ตามวัน) *(Claude's call)*

seq ที่ถูกเลือกพิมพ์รวมยังต้องมี record ใน `t_taglabel`/`t_lot_sequence` เหมือน 6.04 (6.05 เรียกใช้ขั้นตอนบันทึก seq แบบเดียวกับ `printLabel`) และอัปเดต print status ของ seq นั้นเมื่อพิมพ์สำเร็จ

### 6.2 การพิมพ์
- template ใหม่ `WebContent/print_template/multilot.zpl` + context-param ใหม่ ใช้กลไก `javax.print` raw เดิม *(Claude's call)*
- การย่อตัวอักษร: คำนวณความกว้างโดยประมาณฝั่ง Java แล้วเลือกขนาดฟอนต์ ZPL (`^A0` scalable) ให้พอดีช่อง ต่ำสุดที่กำหนดไว้ ถ้ายังไม่พอให้ตัดท้ายด้วย `…`
- เลือกเครื่องพิมพ์จาก dropdown เหมือน 6.04 และจำไว้ใน session

### 6.3 การสแกน QR — เพิ่มเข้าไปในหน้า 6.01 (`FNG_S01`) เดิม ไม่สร้างหน้าใหม่

*(ปรับตอนเขียนแผน 2026-11-13, Claude's call: ผู้ใช้กำหนดว่า "เงื่อนไข IN/OUT ทำตามของเดิมทุกอย่าง แค่เพิ่มการสแกน QR" และให้ใช้ layout ปกติเหมือน 6.01 — การเพิ่มการรับ QR ในช่อง Lot Seq. No. ของ 6.01 ได้ header, การบันทึก และ validation เดิมครบโดยไม่ต้องทำซ้ำ; หน้า 6.05 สำหรับพิมพ์ใช้ชื่อ `FNG_S08`)*

- **layout แบบหน้าปกติของ TNPS เหมือน 6.01** ไม่ทำ layout พิเศษสำหรับ tablet (ผู้ใช้ตัดสินใจ 2026-11-13) · รับข้อมูลจากตัวสแกน 2D แบบ keyboard (Newland NLS-HR2081-BT และ Schlongen SLG-3878 แบบมีแท่นชาร์จ) (Enter ต่อท้าย) ใช้ได้ทั้งบน PC และ tablet
- header: ใช้ของ 6.01 ตามเดิม (Report Date, IN/OUT, Type แล้วกด OK)
- สแกน QR → ส่ง `refno` ไป server → ได้รายการ seq จาก `t_multilot_tag_detail` (ไม่เชื่อข้อมูลใน QR อย่างเดียว) → เพิ่มแถวต่อ seq
- รับ barcode เดี่ยว 19 หลักแบบ 6.01 ด้วย เพื่อให้กล่องเก่าใช้หน้าเดียวกันได้ *(Claude's call)*
- ตรวจทันทีตอนสแกน (เฉพาะฝั่งหน้าจอ): refno ไม่พบ · สแกนกล่องเดิมซ้ำในรายการ · seq ซ้ำในรายการ
- **เงื่อนไข IN/OUT และการบันทึกเหมือน 6.01 ทุกอย่าง** (ผู้ใช้ตัดสินใจ 2026-11-13: "ทำตามของเดิมทุกอย่าง แค่เพิ่มการสแกน QR") — Save ส่งรายการแถวเข้าทางบันทึกเดียวกับ `/FNG_S01_CheckLotNoAndSave` (err.fng.006, err.fng.012, `insertFgDetail`) ไม่เพิ่มกฎใหม่
- ข้อสังเกตที่ผู้ใช้รับทราบ: `insertFgDetail` ข้ามแถวที่ยอดเกิน `lotseqqty` โดยไม่แจ้ง เป็นพฤติกรรมเดิมของ 6.01 คงไว้ตามเดิม

## 7. ทดสอบ
- sticker: พิมพ์จริงกับ GK420t และ ZD421 ด้วย 3 กรณีใน mockup (ปกติ / ชื่อยาวสุด + 4 lot / lot เดียว) และสแกน QR ด้วย handheld
- หน้าสแกน: IN ครบ, IN แก้จำนวน (ไฮไลต์), IN ซ้ำ, OUT ก่อน IN (ผลต้องเหมือน 6.01), สแกน sticker ผิด FG/refno ไม่มี, ผสม QR + barcode เดี่ยว
- ตรวจรายงาน FG stock เดิมว่ายอดตรงหลังบันทึกจากหน้าใหม่
- ทดสอบบน TEST DB ก่อน (DB script แยก `_TEST.sql`)

### 6.5 ข้อสรุปเพิ่มเติมจาก builder check *(Claude's call)*
- **ไม่ต้องรองรับภาษาไทยบน sticker:** ตรวจ PROD เมื่อ 2026-11-13 แล้ว `m_customer.customername` และ `m_fg.fgname` (2,918 แถว) ไม่มีตัวอักษรไทยเลย จึงใช้ฟอนต์ `^A0` ได้ ถ้าวันหน้ามีชื่อไทย ระบบจะแทนตัวที่พิมพ์ไม่ได้ด้วย `?` และเขียน log ไว้
- **DPI:** วาง ZPL สำหรับ 203 dpi (เครื่องเดิม GK420t และ ZD421 เป็น 203 dpi) และเก็บค่า dpi ไว้เป็นค่าคงที่ตัวเดียว เปลี่ยนรุ่นภายหลังได้
- **date code:** ไม่มีใน `t_lot_sequence` → ดึงจาก `t_lotno.printdate` ตอนพิมพ์ แล้วเก็บลง `t_multilot_tag_detail.datecode` (ดูข้อ 8.4)

## 8. คำตอบของคำถามที่เคยเปิด (ตอบครบแล้ว 2026-11-13)
1. ~~ยอด IN/OUT~~ → เหมือน 6.01 ทุกอย่าง (ดู 6.3)
2. ~~seq บนหลาย sticker~~ → (ผู้ใช้เห็นด้วย 2026-11-13) seq หนึ่งอยู่บน sticker QR ที่ยังไม่ยกเลิกได้ **ใบเดียว**; 6.05 ไม่ให้เลือก seq ที่พิมพ์เดี่ยวแล้ว (print status = 1) หรือที่อยู่บน ML อื่น; **แก้ 6.04 เล็กน้อย**: ห้ามลบ/แบ่งใหม่/พิมพ์เดี่ยว seq ที่อยู่บน ML ที่ยังไม่ยกเลิก (แจ้ง error พร้อมเลข ML)
3. ~~พิมพ์ซ้ำ/ยกเลิก~~ → (ผู้ใช้เห็นด้วย) ใน 6.05 ค้นด้วยเลข ML ได้: **พิมพ์ซ้ำ** ใช้ refno เดิม (เก็บ printcount); **ยกเลิก** ได้เฉพาะ ML ที่ seq ใดยังไม่เคยมี FG IN — เพิ่มคอลัมน์ `status` (A=ใช้งาน, C=ยกเลิก), cancelby/canceldate ใน `t_multilot_tag`; หน้าสแกนปฏิเสธ ML ที่ยกเลิกแล้ว
4. ~~ที่มาของ DC~~ → (ผู้ใช้ตอบ 2026-11-13) **`t_lotno.printdate`** = วันที่พิมพ์ lot card (3.03) ของแต่ละ lot, รูปแบบ `yyyy-MM-dd`; ถ้า printdate ว่าง (เคยเกิดกับ lot ที่ re-generate, ดู commit d605f48) ให้พิมพ์ว่างและใส่ `DC` เปล่าใน QR *(Claude's call)*
5. ~~WIP/special label~~ → (ผู้ใช้เห็นด้วย) WIP ต้องตรงกันทุก lot (ล็อกเหมือนลูกค้า/FG); lot ที่ labeltype = 'S' (special) ไม่รองรับใน 6.05 รอบแรก — แจ้งเตือนและไม่เพิ่ม
6. ~~รุ่น handheld~~ → **Urovo P8100P** (ผู้ใช้ตอบ 2026-11-13): rugged tablet จอ 10.1" 1200×1920 Android 13 · หัวสแกน 2D เป็น option (บาง SKU ไม่มี) → ไม่ทำ layout พิเศษ ใช้หน้าแบบปกติ; **ตัวสแกนคือ Newland NLS-HR2081-BT** (2D ไร้สาย Bluetooth 5.0 + 2.4G dongle) จับคู่กับ tablet แบบ Bluetooth HID ใช้งานเหมือนคีย์บอร์ด จึงไม่ต้องใช้กล้องหรือ HTTPS · ต้องตั้งค่า: (ก) ตั้ง layout คีย์บอร์ดจริงบน Android เป็น English (US) (ข) ตัวสแกนส่ง Enter (CR) ต่อท้าย (ค) ถ้าตัวอักษรของ QR ยาวหล่นหาย ให้เพิ่ม keystroke delay ของตัวสแกน (ง) เปิด "แสดงแป้นพิมพ์เสมือน" ไว้ ถ้าต้องพิมพ์แก้จำนวนบนจอ · หน้าเว็บต้อง focus ช่องสแกนอัตโนมัติหลังสแกนและหลังแก้จำนวนเสร็จ *(Claude's call)*
7. ~~เครื่องพิมพ์~~ → ใช้เครื่องเดิมไปก่อน: **Zebra GK420t** (192.168.3.13, 4.26, 5.33) และ **Zebra ZD421 203dpi** (192.168.3.10, 4.24, 5.35) — ทั้งสองรุ่นพิมพ์ ZPL II และ QR (`^BQ`) ได้ ที่ 203 dpi; template เดิมบอกว่าม้วนปัจจุบันกว้าง ~10 cm สูง ~4.5 cm (`Q810`, `Q360,40` ที่ 203 dpi) → ถ้าใช้ม้วนเดิม ปรับแบบ B เป็น 10×4.5 cm
