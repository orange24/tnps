package th.co.nttdata.tki.bean;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;

public class TTaglabelHistory extends AbstractBaseBean {

	private Integer tagHistoryId;
	private String lotNo;
	private Integer custFrom;
	private Integer custTo;
	private Integer fgFrom;
	private Integer fgTo;
	private Integer snpFrom;
	private Integer snpTo;
	private Integer snp;
	private Integer qty;
	private Date changeDate;
	private String changeBy = getUsername();

	public TTaglabelHistory(String lotNo, Integer custFrom, Integer custTo,
			Integer fgFrom, Integer fgTo, Integer snpFrom, Integer snpTo,
			Integer qty) {
		super();
		this.lotNo = lotNo;
		this.custFrom = custFrom;
		this.custTo = custTo;
		this.fgFrom = fgFrom;
		this.fgTo = fgTo;
		this.snpFrom = snpFrom;
		this.snpTo = snpTo;
		this.qty = qty;
	}

	private static final String getUsername() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "TEST";
		}
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public Integer getTagHistoryId() {
		return tagHistoryId;
	}

	public void setTagHistoryId(Integer tagHistoryId) {
		this.tagHistoryId = tagHistoryId;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public Integer getCustFrom() {
		return custFrom;
	}

	public void setCustFrom(Integer custFrom) {
		this.custFrom = custFrom;
	}

	public Integer getCustTo() {
		return custTo;
	}

	public void setCustTo(Integer custTo) {
		this.custTo = custTo;
	}

	public Integer getFgFrom() {
		return fgFrom;
	}

	public void setFgFrom(Integer fgFrom) {
		this.fgFrom = fgFrom;
	}

	public Integer getFgTo() {
		return fgTo;
	}

	public void setFgTo(Integer fgTo) {
		this.fgTo = fgTo;
	}

	public Integer getSnp() {
		return snp;
	}

	public void setSnp(Integer snp) {
		this.snp = snp;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeBy() {
		return changeBy;
	}

	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

	public Integer getSnpFrom() {
		return snpFrom;
	}

	public void setSnpFrom(Integer snpFrom) {
		this.snpFrom = snpFrom;
	}

	public Integer getSnpTo() {
		return snpTo;
	}

	public void setSnpTo(Integer snpTo) {
		this.snpTo = snpTo;
	}

}