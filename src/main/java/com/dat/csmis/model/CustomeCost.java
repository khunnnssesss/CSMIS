package com.dat.csmis.model;

public class CustomeCost {
	private String date;
	private int totalPeople;
	private double price;
	private double datPrice;
	private double empPrice;
	private String resName;
	private int resId;
	private int empCostCount;
	private int eaten;
	
	
	
	public int getEmpCostCount() {
		return empCostCount;
	}
	public void setEmpCostCount(int empCostCount) {
		this.empCostCount = empCostCount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotalPeople() {
		return totalPeople;
	}
	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDatPrice() {
		return datPrice;
	}
	public void setDatPrice(double datPrice) {
		this.datPrice = datPrice;
	}
	public double getEmpPrice() {
		return empPrice;
	}
	public void setEmpPrice(double empPrice) {
		this.empPrice = empPrice;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public int getEaten() {
		return eaten;
	}
	public void setEaten(int eaten) {
		this.eaten = eaten;
	}
	
}
