package th.co.nttdata.tki.bean;

public class MCurrency extends AbstractBaseBean {

	private String currency;
	private String currencyDesc;

	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}