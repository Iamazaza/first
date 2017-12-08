package com.liyuting.drug.domain;

public class EnterDrug {

	private Integer  bills_id;
	private Integer  drug_id;
	private Double   purchase_price;
	private Integer  purchase_number;
	private String  purchase_date;
	private String  provider;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getBills_id() {
		return bills_id;
	}
	public void setBills_id(Integer bills_id) {
		this.bills_id = bills_id;
	}
	public Integer getDrug_id() {
		return drug_id;
	}
	public void setDrug_id(Integer drug_id) {
		this.drug_id = drug_id;
	}
	public Double getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(Double purchase_price) {
		this.purchase_price = purchase_price;
	}
	public Integer getPurchase_number() {
		return purchase_number;
	}
	public void setPurchase_number(Integer purchase_number) {
		this.purchase_number = purchase_number;
	}
	public String getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	
                               
}
