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
	static final int PAD = 6;
	/** the 4 header rows are kept short so the lot part (and its QR column) gets the height */
	private static final int ROW = 38;
	private static final int LOT_TOP = 4 * ROW;
	private static final int LOT_ROW = (LABEL_HEIGHT_DOTS - LOT_TOP) / 4;
	/** row headings (CUSTOMER NAME, LOT. NO. ...) */
	public static final int LABEL_FONT = 22;
	/** wide enough for "CUSTOMER NAME" at LABEL_FONT */
	static final int HEAD_W = 180;
	private static final int WIP_X = 600;
	private static final int QTY_W = 74;
	private static final int QR_X = 580;
	/** biggest QR side in dots that fits the column above the QR Tag No. */
	public static final int QR_MAX = 206;
	/** ^BQ prints about 10 dots below its ^FO */
	private static final int QR_TOP_SHIFT = 10;
	private static final int REF_FONT = 18;
	/** ECC L byte-mode capacity of QR versions 1-10 */
	private static final int[] QR_BYTES_L = { 17, 32, 53, 78, 106, 134, 154, 192, 230, 271 };

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

	/** Modules per side of the smallest ECC L version holding the text in byte mode. */
	public static int qrModules(String qr) {
		int v = 1;
		while (v < QR_BYTES_L.length && qr.length() > QR_BYTES_L[v - 1]) {
			v++;
		}
		return 17 + 4 * v;
	}

	/** Biggest dots per module (1-10) that keeps the QR within QR_MAX; fewer lots get a bigger module. */
	public static int qrMagnification(String qr) {
		return Math.max(1, Math.min(10, QR_MAX / qrModules(qr)));
	}

	public static String build(LabelData d) {
		int h = LABEL_HEIGHT_DOTS;
		StringBuilder z = new StringBuilder();
		z.append("^XA\n^CI0\n^PW").append(LABEL_WIDTH_DOTS).append("\n^LL").append(h).append("\n^LH0,0\n");

		// frame and grid
		box(z, 0, 0, LABEL_WIDTH_DOTS, h, 3);
		for (int y = ROW; y <= LOT_TOP; y += ROW) {
			hline(z, 0, y, LABEL_WIDTH_DOTS);
		}
		vline(z, WIP_X, 0, ROW);
		vline(z, HEAD_W, ROW, h - ROW);
		hline(z, 0, LOT_TOP + LOT_ROW, HEAD_W);
		hline(z, 0, LOT_TOP + 2 * LOT_ROW, HEAD_W);
		for (int i = 1; i < 4; i++) {
			hline(z, HEAD_W, LOT_TOP + i * LOT_ROW, QR_X - HEAD_W);
		}
		vline(z, HEAD_W + QTY_W, LOT_TOP, h - LOT_TOP);
		vline(z, QR_X, LOT_TOP, h - LOT_TOP);

		// header rows
		text(z, PAD, 0, WIP_X - 2 * PAD, ROW, "KIKUWA GROUP", true);
		text(z, WIP_X + PAD, 0, LABEL_WIDTH_DOTS - WIP_X - 2 * PAD, ROW, d.wip, true);
		String[][] rows = { { "CUSTOMER NAME", d.customerName }, { "FG. NO.", d.fgNo }, { "PART NAME", d.partName } };
		for (int i = 0; i < rows.length; i++) {
			int y = ROW * (i + 1);
			label(z, PAD, y, ROW, rows[i][0]);
			text(z, HEAD_W + PAD, y, LABEL_WIDTH_DOTS - HEAD_W - 2 * PAD, ROW, rows[i][1], true);
		}

		// lower part: LOT. NO. / QTY. / total, 4 lot lines, QR
		label(z, PAD, LOT_TOP, LOT_ROW, "LOT. NO.");
		label(z, PAD, LOT_TOP + LOT_ROW, LOT_ROW, "QTY.");
		int total = 0;
		for (LabelLine l : d.lines) {
			total += l.qty;
		}
		int bigH = 60;
		String totalText = String.valueOf(total);
		int totalTop = LOT_TOP + 2 * LOT_ROW;
		z.append("^FO0,").append(totalTop + (h - totalTop - bigH) / 2)
				.append("^A0N,").append(bigH).append(",0^FB").append(HEAD_W).append(",1,0,C,0^FD")
				.append(totalText).append("^FS\n");
		for (int i = 0; i < 4 && i < d.lines.size(); i++) {
			LabelLine l = d.lines.get(i);
			int y = LOT_TOP + i * LOT_ROW;
			text(z, HEAD_W + PAD, y, QTY_W - 2 * PAD, LOT_ROW, String.valueOf(l.qty), false);
			text(z, HEAD_W + QTY_W + PAD, y, QR_X - HEAD_W - QTY_W - 2 * PAD, LOT_ROW, l.lotSeqNo, true);
		}
		// centred in the column between the grid line and the QR Tag No.
		String qr = qrText(d);
		int mag = qrMagnification(qr);
		int size = qrModules(qr) * mag;
		int refY = h - REF_FONT - 4;
		int qrY = LOT_TOP + 2 + (refY - LOT_TOP - 2 - size) / 2;
		z.append("^FO").append(QR_X + 2 + (LABEL_WIDTH_DOTS - 3 - QR_X - 2 - size) / 2).append(',')
				.append(qrY - QR_TOP_SHIFT)
				.append("^BQN,2,").append(mag).append("^FDLA,").append(qr).append("^FS\n");
		z.append("^FO").append(QR_X).append(',').append(refY)
				.append("^A0N,").append(REF_FONT).append(",0^FB").append(LABEL_WIDTH_DOTS - QR_X).append(",1,0,C,0^FD")
				.append(asciiOnly(d.refNo)).append("^FS\n");

		z.append("^XZ\n");
		return z.toString();
	}

	private static void label(StringBuilder z, int x, int rowY, int rowH, String s) {
		z.append("^FO").append(x).append(',').append(rowY + (rowH - LABEL_FONT) / 2)
				.append("^A0N,").append(LABEL_FONT).append(",0^FD").append(s).append("^FS\n");
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
