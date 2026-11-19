package th.co.nttdata.tki.blogic.fng.multilot;

import static org.junit.Assert.*;
import static th.co.nttdata.tki.blogic.fng.multilot.MultiLotZpl.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

public class MultiLotZplTest {

	private static Date d(String s) {
		if (s == null) return null;
		try { return new SimpleDateFormat("yyyy-MM-dd").parse(s); } catch (ParseException e) { throw new RuntimeException(e); }
	}

	private static LabelData label(String ref, String wip, String cust, String fg, String part, Object[]... lots) {
		LabelData l = new LabelData();
		l.refNo = ref; l.wip = wip; l.customerName = cust; l.fgNo = fg; l.partName = part;
		l.lines = new ArrayList<LabelLine>();
		for (Object[] o : lots) {
			LabelLine ln = new LabelLine();
			ln.lotSeqNo = (String) o[0]; ln.qty = (Integer) o[1]; ln.dateCode = d((String) o[2]);
			l.lines.add(ln);
		}
		return l;
	}

	static LabelData sample3() {
		return label("ML2611110001", "MC-8", "NSK Steering Systems Morocco", "AL-EXAPENSSM-0003", "HOUSING EG41Q0227",
				new Object[] { "A016825DX-2052-1001", 5, "2026-11-09" },
				new Object[] { "A016826DX-2052-1001", 10, "2026-11-09" },
				new Object[] { "A016827DX-1011-1001", 10, "2026-11-10" });
	}

	static LabelData sample4() {
		return label("ML2611110002", "FL-1", "Mitsubishi Electric Siam Compressor Industry Co., Ltd.", "AL-L APASHIMORI-0006",
				"J03A (IPM4) LED HL (HG) BRACKET A RH 10047-8N01H-50/P",
				new Object[] { "A016825DX-2052-1001", 5, "2026-11-08" },
				new Object[] { "A016826DX-2052-1001", 10, "2026-11-09" },
				new Object[] { "A016827DX-1011-1001", 10, "2026-11-09" },
				new Object[] { "A016828DX-1011-1001", 10, "2026-11-10" });
	}

	static LabelData sample1() {
		return label("ML2611110003", "MC-8", "THAI STANLEY ELECTRIC PUBLIC COMPANY LIMITED", "AL-L APKOITO-0038",
				"HEAT SINK RH RG01 LED HL (HG) 10062-8R006 -00",
				new Object[] { "A016830DX-1001-1001", 20, "2026-11-10" });
	}

	static LabelData sampleNullDate() {
		return label("ML2611110004", "MC-8", "X", "Y", "Z", new Object[] { "A016830DX-1001-1001", 20, null });
	}

	@Test public void qrTextThreeLots() {
		assertEquals("ML2611110001;LOT1:A016825DX-2052-1001,Q5,DC2026-11-09;LOT2:A016826DX-2052-1001,Q10,DC2026-11-09;LOT3:A016827DX-1011-1001,Q10,DC2026-11-10;", qrText(sample3()));
	}
	@Test public void qrTextNullDate() { assertTrue(qrText(sampleNullDate()).contains(",Q20,DC;")); }
	@Test public void shortTextKeepsBiggestFont() { assertEquals(30, fitFontHeight("MC-8", 200)); }
	@Test public void longestRealCustomerShrinks() {
		int h = fitFontHeight("Mitsubishi Electric Siam Compressor Industry Co., Ltd.", 640);
		assertTrue(h < 30 && estimateWidth("Mitsubishi Electric Siam Compressor Industry Co., Ltd.", h) <= 640);
	}
	@Test public void tooLongIsTruncated() {
		String t = fitText("J03A (IPM4) LED HL (HG) BRACKET A RH 10047-8N01H-50/P EXTRA EXTRA EXTRA", 300);
		assertTrue(t.endsWith("...") && estimateWidth(t, 16) <= 300);
	}
	@Test public void asciiOnlyReplaces() { assertEquals("AB?C D", asciiOnly("ABกC^D")); }
	@Test public void buildHasSizeAndQr() {
		String z = build(sample4());
		assertTrue(z.startsWith("^XA") && z.trim().endsWith("^XZ"));
		assertTrue(z.contains("^PW800") && z.contains("^LL400") && z.contains("^BQN,2,4^FDLA," + qrText(sample4()) + "^FS"));
		assertTrue(z.contains("ML2611110002") && z.contains("A016828DX-1011-1001"));
	}
	/** every lot count fills the QR column: printed square within the column, clear of the grid line and the QR Tag No. */
	@Test public void qrFitsRightColumnForEveryLotCount() {
		LabelData four = sample4();
		for (LabelLine l : four.lines) l.qty = 1000;
		assertTrue(qrText(four).length() <= 192);
		for (LabelData d : new LabelData[] { four, sample3(), sample1(), sampleNullDate() }) {
			String z = build(d);
			java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\^FO(\\d+),(\\d+)\\^BQN,2,(\\d+)").matcher(z);
			assertTrue(m.find());
			int x = Integer.parseInt(m.group(1)), y = Integer.parseInt(m.group(2)) + 10, mag = Integer.parseInt(m.group(3));
			int size = qrModules(qrText(d)) * mag;
			assertEquals(qrMagnification(qrText(d)), mag);
			assertTrue("QR " + size + " dots, at least x4", size <= QR_MAX && mag >= 4);
			assertTrue("QR left " + x, x >= 586);
			assertTrue("QR right " + (x + size), x + size <= 793);
			assertTrue("QR top " + y + " must clear the grid line at 152", y >= 156);
			java.util.regex.Matcher r = java.util.regex.Pattern.compile("\\^FO\\d+,(\\d+)\\^A0N,18,0\\^FB\\d+,1,0,C,0\\^FDML").matcher(z);
			assertTrue(r.find());
			assertTrue("QR bottom " + (y + size) + " must clear the QR Tag No.", y + size + 3 <= Integer.parseInt(r.group(1)));
		}
	}
	@Test public void qrVersionFromLength() {
		assertEquals(49, qrModules(qrText(sample4())));
		assertEquals(6, qrMagnification(qrText(sample1()))); // 55 bytes: version 4, 33 x 6 = 198 dots
	}
	/** headings keep LABEL_FONT and the longest lot seq no. keeps the biggest font */
	@Test public void headingsAndLotSeqFitTheirColumns() {
		assertTrue(estimateWidth("CUSTOMER NAME", LABEL_FONT) <= HEAD_W - 2 * PAD);
		assertTrue(build(sample4()).contains("^A0N," + LABEL_FONT + ",0^FDCUSTOMER NAME^FS"));
		assertTrue(build(sample4()).contains("^A0N,30,0^FDA016828DX-1011-1001^FS"));
	}
	@Test public void buildTotalQty() { assertTrue(build(sample4()).contains("^FD35^FS")); } // 5+10+10+10

	/** Writes sample ZPL files for a real print test: java ... MultiLotZplTest <outDir> */
	public static void main(String[] args) throws Exception {
		File dir = new File(args[0]);
		dir.mkdirs();
		LabelData[] s = { sample3(), sample4(), sample1() };
		for (int i = 0; i < s.length; i++) {
			FileOutputStream out = new FileOutputStream(new File(dir, "sample-" + (i + 1) + ".zpl"));
			out.write(build(s[i]).getBytes("US-ASCII"));
			out.close();
		}
	}
}
