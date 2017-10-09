package th.co.nttdata.tki.bean;

public class MReportType {

	private Byte typeCode;
	private String report;
	private String typeName;
	private String[] reportCategory;

	public Byte getTypeCode() {
		return typeCode;
	}
	public MReportType setTypeCode(Byte typeCode) {
		this.typeCode = typeCode;
		return this;
	}
	public String getReport() {
		return report;
	}
	public MReportType setReport(String report) {
		this.report = report;
		return this;
	}
	public String getTypeName() {
		return typeName;
	}
	public MReportType setTypeName(String typeName) {
		this.typeName = typeName;
		return this;
	}
	public String[] getReportCategory() {
		return reportCategory;
	}
	public MReportType setReportCategory(String[] reportCategory) {
		this.reportCategory = reportCategory;
		return this;
	}

}