package th.co.nttdata.tki.blogic.fng.multilot;

import static org.junit.Assert.*;
import static th.co.nttdata.tki.blogic.fng.multilot.MultiLotRules.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MultiLotRulesTest {

	private static Candidate c(String seq) { return new Candidate(seq, 10, 1, 5, "MC-8", "N", false, null); }
	private static Candidate cFg(String seq, int fgId) { return new Candidate(seq, 10, 1, fgId, "MC-8", "N", false, null); }
	private static List<Candidate> list(Candidate... cs) { return new ArrayList<Candidate>(Arrays.asList(cs)); }
	private static List<SeqEdit> list(SeqEdit... es) { return new ArrayList<SeqEdit>(Arrays.asList(es)); }
	private static List<Candidate> five() { return list(c("A-1001"), c("B-1001"), c("C-1001"), c("D-1001"), c("E-1001")); }
	private static List<Candidate> empty() { return new ArrayList<Candidate>(); }
	private static Date date(int y, int m, int d) { Calendar cal = Calendar.getInstance(); cal.clear(); cal.set(y, m - 1, d); return cal.getTime(); }
	private static Map<String, Object[]> ml(String seq, String refNo, int qty) {
		Map<String, Object[]> m = new HashMap<String, Object[]>();
		m.put(seq, new Object[] { refNo, qty });
		return m;
	}
	private static SeqEdit edit(String seq, int qty, boolean deleted, boolean flagPrint) { return new SeqEdit(seq, qty, deleted, flagPrint); }

	@Test public void fourSameGroupPasses() { assertTrue(validateSelection(list(c("A-1001"), c("B-1001"), c("C-1001"), c("D-1001"))).isEmpty()); }
	@Test public void fiveRejected() { assertEquals("err.fng.013", validateSelection(five()).get(0)[0]); }
	@Test public void emptyRejected() { assertEquals("err.fng.013", validateSelection(empty()).get(0)[0]); }
	@Test public void otherFgRejected() { assertArrayEquals(new String[] { "err.fng.014", "B-1001" }, validateSelection(list(c("A-1001"), cFg("B-1001", 99))).get(0)); }
	@Test public void otherWipRejected() {
		Candidate b = new Candidate("B-1001", 10, 1, 5, "FL-1", "N", false, null);
		assertArrayEquals(new String[] { "err.fng.014", "B-1001" }, validateSelection(list(c("A-1001"), b)).get(0));
	}
	@Test public void specialLabelRejected() {
		Candidate a = new Candidate("A-1001", 10, 1, 5, "MC-8", "S", false, null);
		assertArrayEquals(new String[] { "err.fng.015", "A-1001" }, validateSelection(list(a)).get(0));
	}
	@Test public void scannedInRejected() {
		Candidate a = new Candidate("A-1001", 10, 1, 5, "MC-8", "N", true, null);
		assertArrayEquals(new String[] { "err.fng.016", "A-1001" }, validateSelection(list(a)).get(0));
	}
	@Test public void onActiveMlRejected() {
		Candidate a = new Candidate("A-1001", 10, 1, 5, "MC-8", "N", false, "ML2611130001");
		assertArrayEquals(new String[] { "err.fng.017", "A-1001", "ML2611130001" }, validateSelection(list(a)).get(0));
	}
	@Test public void refNoFormat() { assertEquals("ML2611130007", formatRefNo(date(2026, 11, 13), 7)); }
	@Test public void tagEditDeleteBlocked() { assertEquals("ML2611130001", checkTagEdit(ml("A-1001", "ML2611130001", 10), list(edit("A-1001", 10, true, false)))); }
	@Test public void tagEditQtyChangeBlocked() { assertEquals("ML2611130001", checkTagEdit(ml("A-1001", "ML2611130001", 10), list(edit("A-1001", 8, false, false)))); }
	@Test public void tagEditMissingSeqBlocked() { assertEquals("ML2611130001", checkTagEdit(ml("A-1001", "ML2611130001", 10), list(edit("A-1002", 10, false, false)))); }
	@Test public void tagEditSinglePrintBlocked() { assertEquals("ML2611130001", checkTagEdit(ml("A-1001", "ML2611130001", 10), list(edit("A-1001", 10, false, true)))); }
	@Test public void tagEditUntouchedPasses() { assertNull(checkTagEdit(ml("A-1001", "ML2611130001", 10), list(edit("A-1001", 10, false, false), edit("A-1002", 5, false, true)))); }
}
