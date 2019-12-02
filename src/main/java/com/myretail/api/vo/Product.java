package com.myretail.api.vo;

public class Product {
	
	
	private int id;
	private String name;
	private Price price;
	
	public Product(){
	}

	public Product(int id,String name,Price prodPrice){
		this.id=id;
		this.name=name;
		this.price=prodPrice;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price productPrice) {
		this.price = productPrice;
	}

	@Override
	public String toString() {
		return "ProductDetails {"
				+ "id=" + id + ","
				+ "name=" + name +","
				+ price + 
				"}";
	}
}
