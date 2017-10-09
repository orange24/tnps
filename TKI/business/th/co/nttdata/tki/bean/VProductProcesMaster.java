package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class VProductProcesMaster extends AbstractBaseBean {
	
	private Date 	reportDateFr;
	private Date 	reportDateTo;
	private Integer maxRecord;
	private Integer nMachineId;
	private Integer nCavQty;
	private Integer nPartId;
	private Integer nWIPSeq;
	private Integer snp;
	private String 	dUpdatedDate;
	private String 	dUpdatedTime;
	private String 	keyPart;
	private String 	keyWip;
    private String 	nMaterialCost;
	private String 	sCareerSheetNo;
	private String 	sCategory;
    private String 	sLineMachineCD;
	private String 	sMachine;
    private String 	sMaterial;
    private String 	sMoldName;
	private String 	sMoldNo;
	private String 	sPartName;
	private String 	sPartNo;
	private String 	sProcessName;
	private String 	sWIP;
	private String 	wip;
	private List<MMold> moldList;
	private List<VProductProcesMaster> vList;
	
	public Date getReportDateFr() {
		return reportDateFr;
	}
	public void setReportDateFr(Date reportDateFr) {
		this.reportDateFr = reportDateFr;
	}
	public Date getReportDateTo() {
		return reportDateTo;
	}
	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}
	public Integer getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}
	public Integer getnMachineId() {
		return nMachineId;
	}
	public void setnMachineId(Integer nMachineId) {
		this.nMachineId = nMachineId;
	}
	public Integer getnWIPSeq() {
		return nWIPSeq;
	}
	public void setnWIPSeq(Integer nWIPSeq) {
		this.nWIPSeq = nWIPSeq;
	}
	public Integer getSnp() {
		return snp;
	}
	public void setSnp(Integer snp) {
		this.snp = snp;
	}
	public String getdUpdatedTime() {
		return dUpdatedTime;
	}
	public void setdUpdatedTime(String dUpdatedTime) {
		this.dUpdatedTime = dUpdatedTime;
	}
	public String getsCareerSheetNo() {
		return sCareerSheetNo;
	}
	public void setsCareerSheetNo(String sCareerSheetNo) {
		this.sCareerSheetNo = sCareerSheetNo;
	}
	public String getsCategory() {
		return sCategory;
	}
	public void setsCategory(String sCategory) {
		this.sCategory = sCategory;
	}
	public String getsLineMachineCD() {
		return sLineMachineCD;
	}
	public void setsLineMachineCD(String sLineMachineCD) {
		this.sLineMachineCD = sLineMachineCD;
	}
	public String getsMachine() {
		return sMachine;
	}
	public void setsMachine(String sMachine) {
		this.sMachine = sMachine;
	}
	public String getsMaterial() {
		return sMaterial;
	}
	public void setsMaterial(String sMaterial) {
		this.sMaterial = sMaterial;
	}
	public String getsPartName() {
		return sPartName;
	}
	public void setsPartName(String sPartName) {
		this.sPartName = sPartName;
	}
	public String getsPartNo() {
		return sPartNo;
	}
	public void setsPartNo(String sPartNo) {
		this.sPartNo = sPartNo;
	}
	public String getsProcessName() {
		return sProcessName;
	}
	public void setsProcessName(String sProcessName) {
		this.sProcessName = sProcessName;
	}
	public String getsWIP() {
		return sWIP;
	}
	public void setsWIP(String sWIP) {
		this.sWIP = sWIP;
	}
	public List<VProductProcesMaster> getvList() {
		return vList;
	}
	public void setvList(List<VProductProcesMaster> vList) {
		this.vList = vList;
	}
	public void setdUpdatedDate(String dUpdatedDate) {
		this.dUpdatedDate = dUpdatedDate;
	}
	public String getdUpdatedDate() {
		return dUpdatedDate;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public Integer getnPartId() {
		return nPartId;
	}
	public void setnPartId(Integer nPartId) {
		this.nPartId = nPartId;
	}
	public String getnMaterialCost() {
		return nMaterialCost;
	}
	public void setnMaterialCost(String nMaterialCost) {
		this.nMaterialCost = nMaterialCost;
	}
	public String getKeyPart() {
		return keyPart;
	}
	public void setKeyPart(String keyPart) {
		this.keyPart = keyPart;
	}
	public String getKeyWip() {
		return keyWip;
	}
	public void setKeyWip(String keyWip) {
		this.keyWip = keyWip;
	}
	public String getsMoldName() {
		return sMoldName;
	}
	public void setsMoldName(String sMoldName) {
		this.sMoldName = sMoldName;
	}
	public String getsMoldNo() {
		return sMoldNo;
	}
	public void setsMoldNo(String sMoldNo) {
		this.sMoldNo = sMoldNo;
	}
	public Integer getnCavQty() {
		return nCavQty;
	}
	public void setnCavQty(Integer nCavQty) {
		this.nCavQty = nCavQty;
	}
	public List<MMold> getMoldList() {
		return moldList;
	}
	public void setMoldList(List<MMold> moldList) {
		this.moldList = moldList;
	}
}
