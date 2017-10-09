package th.co.nttdata.tki.bean;

import java.util.Date;

public class TLotNo extends AbstractBaseBean {

	private String lotNo;
	private Integer dcPlanId;
	private String workOrderNo;
	private String printStatus;
	private Date printDate;
	private String printBy;

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public Integer getDcPlanId() {
		return dcPlanId;
	}

	public void setDcPlanId(Integer dcPlanId) {
		this.dcPlanId = dcPlanId;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public String getPrintBy() {
		return printBy;
	}

	public void setPrintBy(String printBy) {
		this.printBy = printBy;
	}

}