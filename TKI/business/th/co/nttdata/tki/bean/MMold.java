package th.co.nttdata.tki.bean;

import java.util.Date;

public class MMold extends AbstractBaseBean {

	private Date endDate;
	private Date startDate;
	private Integer cav;
	private Integer maxGuarantee;
	private Integer minGuarantee;
	private Integer moldId;
	private Integer partId;
	private Integer totalDCShot;
	private Integer totalFGSold;
	private Integer dcStatus;
	private Integer fgStatus;
	private String moldNo;
	private String moldName;
	private String cavNo;

	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getCav() {
		return cav;
	}
	public void setCav(Integer cav) {
		this.cav = cav;
	}
	public Integer getMaxGuarantee() {
		return maxGuarantee;
	}
	public void setMaxGuarantee(Integer maxGuarantee) {
		this.maxGuarantee = maxGuarantee;
	}
	public Integer getMinGuarantee() {
		return minGuarantee;
	}
	public void setMinGuarantee(Integer minGuarantee) {
		this.minGuarantee = minGuarantee;
	}
	public Integer getMoldId() {
		return moldId;
	}
	public void setMoldId(Integer moldId) {
		this.moldId = moldId;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getTotalDCShot() {
		return totalDCShot;
	}
	public void setTotalDCShot(Integer totalDCShot) {
		this.totalDCShot = totalDCShot;
	}
	public Integer getTotalFGSold() {
		return totalFGSold;
	}
	public void setTotalFGSold(Integer totalFGSold) {
		this.totalFGSold = totalFGSold;
	}
	public Integer getDcStatus() {
		return dcStatus;
	}
	public void setDcStatus(Integer dcStatus) {
		this.dcStatus = dcStatus;
	}
	public Integer getFgStatus() {
		return fgStatus;
	}
	public void setFgStatus(Integer fgStatus) {
		this.fgStatus = fgStatus;
	}
	public String getMoldNo() {
		return moldNo;
	}
	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
	public void setMoldName(String moldName) {
		this.moldName = moldName;
	}
	public String getMoldName() {
		return moldName;
	}
	public String getCavNo() {
		return cavNo;
	}
	public void setCavNo(String cavNo) {
		this.cavNo = cavNo;
	}
	
	
}