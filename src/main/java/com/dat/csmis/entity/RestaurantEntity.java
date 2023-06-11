package com.dat.csmis.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class RestaurantEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(length = 50, nullable = false)
	private String address;
	@Column(unique = true, nullable = false)
	private String phone;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private Integer totalPrice;
	@Column(nullable = false)
	private String receiveBy;
	@Column(columnDefinition = "VARCHAR(10) DEFAULT 'Active'")
	private String status;
	@OneToMany(targetEntity = OrderListEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "resId", referencedColumnName = "id")
	private List<OrderListEntity> orderListRes;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_price")
    private PriceEntity price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PriceEntity getPrice() {
		return price;
	}
	public void setPrice(PriceEntity price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReceiveBy() {
		return receiveBy;
	}
	public void setReceiveBy(String receiveBy) {
		this.receiveBy = receiveBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<OrderListEntity> getOrderListRes() {
		return orderListRes;
	}
	public void setOrderListRes(List<OrderListEntity> orderListRes) {
		this.orderListRes = orderListRes;
	}
	
	
	
}
