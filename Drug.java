package com.liyuting.drug.domain;

public class Drug {
	Integer drug_id;
	String drug_name;
	Double purchase_price;
	Integer purchase_number;
	String purchase_date;
	String provider; 
	String producer;
	String specification;
	Integer inventory;
	Double price;
	String description;
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	

	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	
	public Integer getDrug_id() {
		return drug_id;
	}
	public void setDrug_id(Integer drug_id) {
		this.drug_id = drug_id;
	}
	public String getDrug_name() {
		return drug_name;
	}
	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
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
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
