package th.co.nttdata.tki.bean;

public class Message {
	private String code;
	private String[] arguments;

	public Message(String code, String[] arguments) {
		super();
		this.code = code;
		this.arguments = arguments;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String[] getArguments() {
		return arguments;
	}
	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
}
