package th.co.nttdata.tki.bean;


import java.util.List;


public class PrintTagChangedHistory extends AbstractBaseBean {

	private String lotNo;					
	private String customerFrom;					
	private String customerTo;			
	private String fgNoFrom;				
	private String fgNoTo;				
	private String fgNameFrom;					
	private String fgNameTo;
	private Integer snpFrom;												
	private Integer snpTo;												
	private Integer qty;																								 
	private List<PrintTagChangedHistory> details;
					
	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getCustomerFrom() {
		return customerFrom;
	}

	public void setCustomerFrom(String customerFrom) {
		this.customerFrom = customerFrom;
	}

	public String getCustomerTo() {
		return customerTo;
	}

	public void setCustomerTo(String customerTo) {
		this.customerTo = customerTo;
	}

	public String getFgNoFrom() {
		return fgNoFrom;
	}

	public void setFgNoFrom(String fgNoFrom) {
		this.fgNoFrom = fgNoFrom;
	}

	public String getFgNoTo() {
		return fgNoTo;
	}

	public void setFgNoTo(String fgNoTo) {
		this.fgNoTo = fgNoTo;
	}

	public String getFgNameFrom() {
		return fgNameFrom;
	}

	public void setFgNameFrom(String fgNameFrom) {
		this.fgNameFrom = fgNameFrom;
	}

	public String getFgNameTo() {
		return fgNameTo;
	}

	public void setFgNameTo(String fgNameTo) {
		this.fgNameTo = fgNameTo;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	

	public List<PrintTagChangedHistory> getDetails() {
		return details;
	}

	public void setDetails(List<PrintTagChangedHistory> details) {
		this.details = details;
	}
	
}