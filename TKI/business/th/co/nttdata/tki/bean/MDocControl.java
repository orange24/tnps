package th.co.nttdata.tki.bean;

public class MDocControl extends AbstractBaseBean {
	
	private String docNo;
	private String revDocNo;
	private String docNoR2;
	private String revDocNoR2;
	
	public MDocControl() {
		super();
	}
	
	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getRevDocNo() {
		return revDocNo;
	}

	public void setRevDocNo(String revDocNo) {
		this.revDocNo = revDocNo;
	}

	public String getDocNoR2() {
		return docNoR2;
	}

	public void setDocNoR2(String docNoR2) {
		this.docNoR2 = docNoR2;
	}

	public String getRevDocNoR2() {
		return revDocNoR2;
	}

	public void setRevDocNoR2(String revDocNoR2) {
		this.revDocNoR2 = revDocNoR2;
	}
	
}
