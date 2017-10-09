package th.co.nttdata.tki.bean;

public class MultiChoice {
	private Integer id;
	private String label;
	private Boolean choose;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Boolean getChoose() {
		return choose;
	}
	public void setChoose(Boolean choose) {
		this.choose = choose;
	}
}
