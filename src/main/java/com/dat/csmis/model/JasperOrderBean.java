package com.dat.csmis.model;





public class JasperOrderBean {
	
	private int id;

	private String start_date;
	private String end_date;
	private String cashier;
	private String received_by;
	private String approved_manager;

	private Integer total_people;

	private Integer price;

	private Integer total_price;
	private String payment_date;
	private String status;
	private String payment_method;
	private String created_at;
	private String name;
	private String phone;
	private String address;
	private int totalPriceSum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	
	public String getApproved_manager() {
		return approved_manager;
	}
	public void setApproved_manager(String approved_manager) {
		this.approved_manager = approved_manager;
	}
	public Integer getTotal_people() {
		return total_people;
	}
	public void setTotal_people(Integer total_people) {
		this.total_people = total_people;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReceived_by() {
		return received_by;
	}
	public void setReceived_by(String received_by) {
		this.received_by = received_by;
	}
	public int getTotalPriceSum() {
		return totalPriceSum;
	}
	public void setTotalPriceSum(int totalPriceSum) {
		this.totalPriceSum = totalPriceSum;
	}
	
	
	
	
}
