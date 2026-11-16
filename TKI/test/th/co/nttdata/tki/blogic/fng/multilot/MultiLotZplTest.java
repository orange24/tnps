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
		assertTrue(z.contains("^PW800") && z.contains("^LL400") && z.contains("^BQN,2,3^FDLA," + qrText(sample4()) + "^FS"));
		assertTrue(z.contains("ML2611110002") && z.contains("A016828DX-1011-1001"));
	}
	/** ECC L keeps a 4-lot tag with 4-digit qtys at QR version 8 (49 modules, <=192 bytes): 147 dots wide. */
	@Test public void qrFitsRightColumnBelowGrid() {
		LabelData d = sample4();
		for (LabelLine l : d.lines) l.qty = 1000;
		assertTrue(qrText(d).length() <= 192);
		java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\^FO(\\d+),(\\d+)\\^BQN").matcher(build(d));
		assertTrue(m.find());
		int x = Integer.parseInt(m.group(1)), y = Integer.parseInt(m.group(2));
		assertTrue("QR right edge " + (x + 147), x >= 625 && x + 147 <= 790);
		assertTrue("QR top " + y + " must clear the grid line at 200", y >= 205);
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
