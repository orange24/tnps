package th.co.nttdata.tki.bean;

import java.util.Date;

public class Batch extends AbstractBaseBean {
	private Date 	finishDate;
	private Date 	startDate;
	private Date 	executeDate;
	private Integer batchId;
	private Integer batchStatus;
	private Integer	isenable;
	private Integer	result;
	private String  batchCode;
	private String 	batchName;
	private String 	runBy;
	private String 	spName;
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
 
	public Date getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public Integer getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}
	public Integer getIsenable() {
		return isenable;
	}
	public void setIsenable(Integer isenable) {
		this.isenable = isenable;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getRunBy() {
		return runBy;
	}
	public void setRunBy(String runBy) {
		this.runBy = runBy;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}	
}
