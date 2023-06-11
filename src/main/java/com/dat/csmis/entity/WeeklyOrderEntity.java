package com.dat.csmis.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class WeeklyOrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String date;

	private Integer totalPeople;

	private Integer price;

	private Integer totalPrice;

	@OneToMany(targetEntity = AvoidMeat.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "weeklyOrderId", referencedColumnName = "id")
	private List<AvoidMeat> avoidMeatList = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "orderListId")
	private OrderListEntity orderList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(Integer totalPeople) {
		this.totalPeople = totalPeople;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<AvoidMeat> getAvoidMeatList() {
		return avoidMeatList;
	}

	public void setAvoidMeatList(List<AvoidMeat> avoidMeatList) {
		this.avoidMeatList = avoidMeatList;
	}
	

	public OrderListEntity getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderListEntity orderList) {
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "WeeklyOrderEntity [id=" + id + ", date=" + date + ", totalPeople=" + totalPeople + ", price=" + price
				+ ", totalPrice=" + totalPrice + ", avoidMeatList=" + avoidMeatList + "]";
	}

}
