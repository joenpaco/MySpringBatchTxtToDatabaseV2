package models;

public class Src_sapfisaldos {

	String bukrs;
	String ktopl;
	String saknr;
	String bilkt;
	String fisc_year;
	String fis_period;
	String debits_per;
	String credit_per;
	String per_sales;
	String balance;
	String currency;

	public Src_sapfisaldos() {
	}

	public Src_sapfisaldos(String bukrs, String ktopl, String saknr, String bilkt, String fisc_year, String fis_period,
			String debits_per, String credit_per, String per_sales, String balance, String currency) {

		this.bukrs = bukrs;
		this.ktopl = ktopl;
		this.saknr = saknr;
		this.bilkt = bilkt;
		this.fisc_year = fisc_year;
		this.fis_period = fis_period;
		this.debits_per = debits_per;
		this.credit_per = credit_per;
		this.per_sales = per_sales;
		this.balance = balance;
		this.currency = currency;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getKtopl() {
		return ktopl;
	}

	public void setKtopl(String ktopl) {
		this.ktopl = ktopl;
	}

	public String getSaknr() {
		return saknr;
	}

	public void setSaknr(String saknr) {
		this.saknr = saknr;
	}

	public String getBilkt() {
		return bilkt;
	}

	public void setBilkt(String bilkt) {
		this.bilkt = bilkt;
	}

	public String getFisc_year() {
		return fisc_year;
	}

	public void setFisc_year(String fisc_year) {
		this.fisc_year = fisc_year;
	}

	public String getFis_period() {
		return fis_period;
	}

	public void setFis_period(String fis_period) {
		this.fis_period = fis_period;
	}

	public String getDebits_per() {
		return debits_per;
	}

	public void setDebits_per(String debits_per) {
		this.debits_per = debits_per;
	}

	public String getCredit_per() {
		return credit_per;
	}

	public void setCredit_per(String credit_per) {
		this.credit_per = credit_per;
	}

	public String getPer_sales() {
		return per_sales;
	}

	public void setPer_sales(String per_sales) {
		this.per_sales = per_sales;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
