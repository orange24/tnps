package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

public class TPendingAdjust extends AbstractBaseBean {
	private Integer pdAdjustId;
	private Integer pdId;
	private String customerCode;
	private Integer partId;
	private String partNo;
	private String partName;
	private String workorderNo;
	private String lotNo;
	private String wip;
	private String wipName;
	private Integer pdQty;
	private Integer pdAdjustQty;
	private Integer ok;
	private Integer ng;
	private String wipRework;
	private String adjustRemark;
	private String ngReason;
	private Date operationDate;
	private Integer qty;
	private Integer nDiffQty;
	private String remark;

	private List<MReason> reasonList = LazyList.decorate(new ArrayList(),
			FactoryUtils.instantiateFactory(MReason.class));
	private List<TPendingRework> reworkList = LazyList.decorate(new ArrayList(),
			FactoryUtils.instantiateFactory(TPendingRework.class));
	private List<Map<String, Object>> wipList;
	
	public String toString() {
		String result = "{";
		result += "pdAdjustId = " + pdAdjustId + ",";
		result += "pdId = " + pdId + ",";
		result += "customerCode = " + customerCode + ",";
		result += "partId = " + partId + ",";
		result += "partNo = " + partNo + ",";
		result += "partName = " + partName + ",";
		result += "workorderNo = " + workorderNo + ",";
		result += "lotNo = " + lotNo + ",";
		result += "wip = " + wip + ",";
		result += "wipName = " + wipName + ",";
		result += "pdQty = " + pdQty + ",";
		result += "pdAdjustQty = " + pdAdjustQty + ",";
		result += "ok = " + ok + ",";
		result += "ng = " + ng + ",";
		result += "wipRework = " + wipRework + ",";
		result += "adjustRemark = " + adjustRemark + ",";
		result += "ngReason = " + ngReason + ",";
		result += "operationDate = " + operationDate + ",";
		result += "qty = " + qty + ",";
		result += "nDiffQty = " + nDiffQty + ",";
		result += "reasonList = " + reasonList + ",";
		result += "reworkList = " + reworkList + ",";
		result += "wipList = " + wipList + ",";
		result += "createBy = " + super.getCreateBy();
		result += "}";
		return result;
	}

	public Integer getPdAdjustId() {
		return this.pdAdjustId;
	}

	public void setPdAdjustId(Integer pdAdjustId) {
		this.pdAdjustId = pdAdjustId;
	}

	public Integer getPdId() {
		return this.pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Integer getPartId() {
		return this.partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getWip() {
		return this.wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getWipName() {
		return this.wipName;
	}

	public void setWipName(String wipName) {
		this.wipName = wipName;
	}

	public Integer getPdQty() {
		return this.pdQty;
	}

	public void setPdQty(Integer pdQty) {
		this.pdQty = pdQty;
	}

	public Integer getOk() {
		return this.ok;
	}

	public void setOk(Integer ok) {
		this.ok = ok;
	}

	public Integer getNg() {
		return this.ng;
	}

	public void setNg(Integer ng) {
		this.ng = ng;
	}

	public Date getOperationDate() {
		return this.operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public List<TPendingRework> getReworkList() {
		return this.reworkList;
	}

	public void setReworkList(List<TPendingRework> reworkList) {
		this.reworkList = reworkList;
	}

	public String getWorkorderNo() {
		return this.workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public String getLotNo() {
		return this.lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public Integer getPdAdjustQty() {
		return this.pdAdjustQty;
	}

	public void setPdAdjustQty(Integer pdAdjustQty) {
		this.pdAdjustQty = pdAdjustQty;
	}

	public List<Map<String, Object>> getWipList() {
		return this.wipList;
	}

	public void setWipList(List<Map<String, Object>> wipList) {
		this.wipList = wipList;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getnDiffQty() {
		return this.nDiffQty;
	}

	public void setnDiffQty(Integer nDiffQty) {
		this.nDiffQty = nDiffQty;
	}

	public String getWipRework() {
		return this.wipRework;
	}

	public void setWipRework(String wipRework) {
		this.wipRework = wipRework;
	}

	public List<MReason> getReasonList() {
		return this.reasonList;
	}

	public void setReasonList(List<MReason> reasonList) {
		this.reasonList = reasonList;
	}

	public String getNgReason() {
		return this.ngReason;
	}

	public void setNgReason(String ngReason) {
		this.ngReason = ngReason;
	}

	public String getAdjustRemark() {
		return this.adjustRemark;
	}

	public void setAdjustRemark(String adjustRemark) {
		this.adjustRemark = adjustRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
