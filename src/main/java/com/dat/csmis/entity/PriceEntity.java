package com.dat.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class PriceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private double datPrice;
	@Column(nullable = false)
	private double empPrice;
	
	
	@OneToOne(mappedBy = "price")
	private RestaurantEntity restaurant;
	
	public RestaurantEntity getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
}
