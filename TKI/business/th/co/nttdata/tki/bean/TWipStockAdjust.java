package th.co.nttdata.tki.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class TWipStockAdjust extends AbstractBaseBean implements Serializable {

	private static final long serialVersionUID = 4021322354754910601L;
	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private String customerCode;
	private Integer partId;
	private String wip;
	private String wipName;
	private String partNo;
	private String partName;
	private Integer currentStock;
	private Integer adjustStock;
	private String adjustReason;
	private List<TWipStockAdjust> adjustList;

	//WIP_S03
	private transient MultipartFile fileImport;

	//Criteria WIP_S04 adjust history
	private Date createDateFr;
	private Date createDateTo;

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getReportDate() {
		return reportDate;
	}

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
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

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public List<TWipStockAdjust> getAdjustList() {
		return adjustList;
	}

	public void setAdjustList(List<TWipStockAdjust> adjustList) {
		this.adjustList = adjustList;
	}

	public String getWipName() {
		return wipName;
	}

	public void setWipName(String wipName) {
		this.wipName = wipName;
	}

	public Integer getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	public Integer getAdjustStock() {
		return adjustStock;
	}

	public void setAdjustStock(Integer adjustStockQty) {
		this.adjustStock = adjustStockQty;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@JsonIgnore
	public MultipartFile getFileImport() {
		return fileImport;
	}

	public void setFileImport(MultipartFile fileImport) {
		this.fileImport = fileImport;
	}

	public Date getCreateDateFr() {
		return createDateFr;
	}

	public void setCreateDateFr(Date createDateFr) {
		this.createDateFr = createDateFr;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}
}