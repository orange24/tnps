package th.co.nttdata.tki.blogic.fng.multilot;

import java.text.SimpleDateFormat;

/**
 * QR text and ZPL II for the 10x5 cm multi-lot sticker (Zebra GK420t / ZD421, 203 dpi).
 * Layout follows label-b-final.html at 2 dots per mockup px.
 */
public final class MultiLotZpl {

	public static final int DPI = 203;
	public static final int LABEL_WIDTH_DOTS = 800;
	/** 5 cm roll; set to 360 if the roll measures 4.5 cm */
	public static final int LABEL_HEIGHT_DOTS = 400;
	/** dot heights, big to small */
	public static final int[] FONT_STEPS = { 30, 26, 22, 20, 18, 16 };

	private static final int MIN_FONT = 16;
	private static final int PAD = 6;
	private static final int ROW = 50;
	private static final int HEAD_W = 150;
	private static final int WIP_X = 600;
	private static final int QTY_W = 80;
	private static final int QR_X = 620;

	private MultiLotZpl() {
	}

	/** {@code <refno>;LOT1:<lotseqno>,Q<qty>,DC<yyyy-MM-dd>;LOT2:...;} */
	public static String qrText(LabelData d) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder(d.refNo).append(';');
		for (int i = 0; i < d.lines.size(); i++) {
			LabelLine l = d.lines.get(i);
			sb.append("LOT").append(i + 1).append(':').append(l.lotSeqNo)
					.append(",Q").append(l.qty)
					.append(",DC").append(l.dateCode == null ? "" : f.format(l.dateCode))
					.append(';');
		}
		return sb.toString();
	}

	/** Outside 0x20-0x7E becomes '?'; '^' and '~' become ' ' so they cannot start a ZPL command. */
	public static String asciiOnly(String s) {
		if (s == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder(s.length());
		for (char c : s.toCharArray()) {
			if (c == '^' || c == '~') {
				sb.append(' ');
			} else if (c < 0x20 || c > 0x7E) {
				sb.append('?');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static int estimateWidth(String text, int fontHeight) {
		return (int) Math.ceil(text.length() * fontHeight * 0.55);
	}

	/** First height in FONT_STEPS whose estimated width fits the box; 16 if none does. */
	public static int fitFontHeight(String text, int boxWidthDots) {
		for (int h : FONT_STEPS) {
			if (estimateWidth(text, h) <= boxWidthDots) {
				return h;
			}
		}
		return MIN_FONT;
	}

	/** The text unchanged if it fits at 16 dots, otherwise cut and ended with "...". */
	public static String fitText(String text, int boxWidthDots) {
		if (estimateWidth(text, MIN_FONT) <= boxWidthDots) {
			return text;
		}
		String t = text;
		while (t.length() > 0 && estimateWidth(t + "...", MIN_FONT) > boxWidthDots) {
			t = t.substring(0, t.length() - 1);
		}
		return t + "...";
	}

	public static String build(LabelData d) {
		int h = LABEL_HEIGHT_DOTS;
		StringBuilder z = new StringBuilder();
		z.append("^XA\n^CI0\n^PW").append(LABEL_WIDTH_DOTS).append("\n^LL").append(h).append("\n^LH0,0\n");

		// frame and grid
		box(z, 0, 0, LABEL_WIDTH_DOTS, h, 3);
		for (int y = ROW; y <= 4 * ROW; y += ROW) {
			hline(z, 0, y, LABEL_WIDTH_DOTS);
		}
		vline(z, WIP_X, 0, ROW);
		vline(z, HEAD_W, ROW, h - ROW);
		hline(z, 0, 5 * ROW, HEAD_W);
		hline(z, 0, 6 * ROW, HEAD_W);
		for (int y = 5 * ROW; y < h; y += ROW) {
			hline(z, HEAD_W, y, QR_X - HEAD_W);
		}
		vline(z, HEAD_W + QTY_W, 4 * ROW, h - 4 * ROW);
		vline(z, QR_X, 4 * ROW, h - 4 * ROW);

		// header rows
		text(z, PAD, 0, WIP_X - 2 * PAD, ROW, "KIKUWA GROUP", true);
		text(z, WIP_X + PAD, 0, LABEL_WIDTH_DOTS - WIP_X - 2 * PAD, ROW, d.wip, true);
		String[][] rows = { { "CUSTOMER NAME", d.customerName }, { "FG. NO.", d.fgNo }, { "PART NAME", d.partName } };
		for (int i = 0; i < rows.length; i++) {
			int y = ROW * (i + 1);
			label(z, PAD, y, rows[i][0]);
			text(z, HEAD_W + PAD, y, LABEL_WIDTH_DOTS - HEAD_W - 2 * PAD, ROW, rows[i][1], true);
		}

		// lower part: LOT. NO. / QTY. / total, 4 lot lines, QR
		label(z, PAD, 4 * ROW, "LOT. NO.");
		label(z, PAD, 5 * ROW, "QTY.");
		int total = 0;
		for (LabelLine l : d.lines) {
			total += l.qty;
		}
		int bigH = 60;
		String totalText = String.valueOf(total);
		z.append("^FO0,").append(6 * ROW + (h - 6 * ROW - bigH) / 2)
				.append("^A0N,").append(bigH).append(",0^FB").append(HEAD_W).append(",1,0,C,0^FD")
				.append(totalText).append("^FS\n");
		for (int i = 0; i < 4 && i < d.lines.size(); i++) {
			LabelLine l = d.lines.get(i);
			int y = 4 * ROW + i * ROW;
			text(z, HEAD_W + PAD, y, QTY_W - 2 * PAD, ROW, String.valueOf(l.qty), false);
			text(z, HEAD_W + QTY_W + PAD, y, QR_X - HEAD_W - QTY_W - 2 * PAD, ROW, l.lotSeqNo, true);
		}
		// ECC L: up to 192 bytes stays at version 8 (49 modules = 147 dots), centred in the column
		z.append("^FO").append(QR_X + 16).append(',').append(4 * ROW + 8)
				.append("^BQN,2,3^FDLA,").append(qrText(d)).append("^FS\n");
		int refH = 18;
		z.append("^FO").append(QR_X).append(',').append(h - refH - 4)
				.append("^A0N,").append(refH).append(",0^FB").append(LABEL_WIDTH_DOTS - QR_X).append(",1,0,C,0^FD")
				.append(asciiOnly(d.refNo)).append("^FS\n");

		z.append("^XZ\n");
		return z.toString();
	}

	private static void label(StringBuilder z, int x, int rowY, String s) {
		int lh = 18;
		z.append("^FO").append(x).append(',').append(rowY + (ROW - lh) / 2)
				.append("^A0N,").append(lh).append(",0^FD").append(s).append("^FS\n");
	}

	/** Text shrunk (and cut if needed) to fit boxW, vertically centred in a row of rowH. */
	private static void text(StringBuilder z, int x, int y, int boxW, int rowH, String raw, boolean fit) {
		String s = fitText(asciiOnly(raw), boxW);
		int fh = fit ? fitFontHeight(s, boxW) : FONT_STEPS[0];
		z.append("^FO").append(x).append(',').append(y + (rowH - fh) / 2)
				.append("^A0N,").append(fh).append(",0^FD").append(s).append("^FS\n");
	}

	private static void box(StringBuilder z, int x, int y, int w, int h, int t) {
		z.append("^FO").append(x).append(',').append(y).append("^GB").append(w).append(',').append(h)
				.append(',').append(t).append("^FS\n");
	}

	private static void hline(StringBuilder z, int x, int y, int w) {
		box(z, x, y, w, 0, 2);
	}

	private static void vline(StringBuilder z, int x, int y, int h) {
		box(z, x, y, 0, h, 2);
	}
}
