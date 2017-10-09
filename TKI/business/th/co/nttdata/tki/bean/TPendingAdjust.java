package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

@SuppressWarnings("unchecked")
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
	//P3 for v_09_pendingList
	private Date operationDate;
	private Integer qty;
	private Integer nDiffQty;
	
	private List<TPendingRework> reworkList = 
		LazyList.decorate(new ArrayList<TPendingRework>(), FactoryUtils.instantiateFactory(TPendingRework.class));

	private List<Map<String, Object>> wipList;
	
	public Integer getPdAdjustId() {
		return pdAdjustId;
	}
	public void setPdAdjustId(Integer pdAdjustId) {
		this.pdAdjustId = pdAdjustId;
	}
	public Integer getPdId() {
		return pdId;
	}
	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public Integer getPdQty() {
		return pdQty;
	}
	public void setPdQty(Integer pdQty) {
		this.pdQty = pdQty;
	}
	public Integer getOk() {
		return ok;
	}
	public void setOk(Integer ok) {
		this.ok = ok;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public List<TPendingRework> getReworkList() {
		return reworkList;
	}
	public void setReworkList(List<TPendingRework> reworkList) {
		this.reworkList = reworkList;
	}
	public String getWorkorderNo() {
		return workorderNo;
	}
	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public Integer getPdAdjustQty() {
		return pdAdjustQty;
	}
	public void setPdAdjustQty(Integer pdAdjustQty) {
		this.pdAdjustQty = pdAdjustQty;
	}
	public List<Map<String, Object>> getWipList() {
		return wipList;
	}
	public void setWipList(List<Map<String, Object>> wipList) {
		this.wipList = wipList;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getnDiffQty() {
		return nDiffQty;
	}
	public void setnDiffQty(Integer nDiffQty) {
		this.nDiffQty = nDiffQty;
	}
}
