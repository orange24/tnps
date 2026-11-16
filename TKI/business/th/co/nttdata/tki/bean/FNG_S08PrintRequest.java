package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

/** 6.05: the lots shown on screen; the seqs to put on one QR tag are the ones with flagPrint == 1. */
public class FNG_S08PrintRequest extends AbstractBaseBean {
	private String printerId;
	private String printerName;
	private List<TTagLabelDetail> lots = new ArrayList<TTagLabelDetail>();

	public String getPrinterId() {
		return printerId;
	}

	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public List<TTagLabelDetail> getLots() {
		return lots;
	}

	public void setLots(List<TTagLabelDetail> lots) {
		this.lots = lots;
	}
}
