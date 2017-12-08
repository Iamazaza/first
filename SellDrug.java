package com.liyuting.drug.domain;

public class SellDrug {
	private Integer customer_id    ;
	private Integer drug_id        ;
	private Integer market_number  ;
	private Integer market_date    ;
	private Integer price          ;
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getDrug_id() {
		return drug_id;
	}
	public void setDrug_id(Integer drug_id) {
		this.drug_id = drug_id;
	}
	public Integer getMarket_number() {
		return market_number;
	}
	public void setMarket_number(Integer market_number) {
		this.market_number = market_number;
	}
	public Integer getMarket_date() {
		return market_date;
	}
	public void setMarket_date(Integer market_date) {
		this.market_date = market_date;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
