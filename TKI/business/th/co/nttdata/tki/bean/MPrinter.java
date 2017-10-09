package th.co.nttdata.tki.bean;

public class MPrinter extends AbstractBaseBean {

	private Integer printerId;
	private Integer isEnable;
	private String printerName;
	private String displayName;

	public MPrinter() {
	}

	public MPrinter(Integer printerId, String displayName) {
		this.printerId = printerId;
		this.displayName = displayName;
	}

	public MPrinter(String printerName, String displayName) {
		this.printerName = printerName;
		this.displayName = displayName;
	}

	public Integer getPrinterId() {
		return printerId;
	}

	public void setPrinterId(Integer printerId) {
		this.printerId = printerId;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
