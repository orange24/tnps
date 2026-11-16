package th.co.nttdata.tki.blogic.fng.multilot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Rules of 6.05 Print Multi-Lot Tag. No Spring, no DB. */
public final class MultiLotRules {

	public static final int MAX_SEQ = 4;

	private MultiLotRules() {
	}

	/**
	 * @return error code + args per problem (codes are keys in errorcodes.properties); empty = OK.
	 *         The first seq is the reference for customer / FG / WIP.
	 */
	public static List<String[]> validateSelection(List<Candidate> selected) {
		List<String[]> errors = new ArrayList<String[]>();
		if (selected == null || selected.isEmpty() || selected.size() > MAX_SEQ) {
			errors.add(new String[] { "err.fng.013" });
			return errors;
		}
		Candidate first = selected.get(0);
		for (Candidate c : selected) {
			if (c.customerId != first.customerId || c.fgId != first.fgId
					|| !String.valueOf(c.wip).equals(String.valueOf(first.wip))) {
				errors.add(new String[] { "err.fng.014", c.lotSeqNo });
			}
			if ("S".equals(c.labelType)) {
				errors.add(new String[] { "err.fng.015", c.lotSeqNo });
			}
			if (c.scannedIn) {
				errors.add(new String[] { "err.fng.016", c.lotSeqNo });
			}
			if (c.activeRefNo != null) {
				errors.add(new String[] { "err.fng.017", c.lotSeqNo, c.activeRefNo });
			}
		}
		return errors;
	}

	/** "ML" + yyMMdd + 4-digit running number. */
	public static String formatRefNo(Date printDate, int running) {
		return "ML" + new SimpleDateFormat("yyMMdd").format(printDate)
				+ String.format("%04d", running);
	}

	/**
	 * 6.04 guard: a seq that is on an active ML may not be deleted, change qty, disappear from the
	 * list, or be printed on its own.
	 *
	 * @param mlSeqs lotSeqNo -> {refNo, qty} of seqs already on an active ML
	 * @return refNo of the first ML hit, null if the edit leaves those seqs untouched
	 */
	public static String checkTagEdit(Map<String, Object[]> mlSeqs, List<SeqEdit> incoming) {
		if (mlSeqs == null || mlSeqs.isEmpty()) {
			return null;
		}
		Map<String, SeqEdit> byNo = new HashMap<String, SeqEdit>();
		if (incoming != null) {
			for (SeqEdit e : incoming) {
				byNo.put(e.lotSeqNo, e);
			}
		}
		for (Map.Entry<String, Object[]> ml : mlSeqs.entrySet()) {
			String refNo = (String) ml.getValue()[0];
			int qty = ((Number) ml.getValue()[1]).intValue();
			SeqEdit e = byNo.get(ml.getKey());
			if (e == null || e.deleted || e.flagPrint || e.qty != qty) {
				return refNo;
			}
		}
		return null;
	}
}
