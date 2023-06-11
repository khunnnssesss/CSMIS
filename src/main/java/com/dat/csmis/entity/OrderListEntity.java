package com.dat.csmis.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class OrderListEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String startDate;
	private String endDate;
	private String cashier;
	private String receivedBy;
	private String approvedManager;

	private Integer totalPeople;

	private Integer price;
private Integer datPrice;
private Integer empPrice;
	private Integer totalPrice;
	public Integer getDatPrice() {
		return datPrice;
	}

	public void setDatPrice(Integer datPrice) {
		this.datPrice = datPrice;
	}

	public Integer getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(Integer empPrice) {
		this.empPrice = empPrice;
	}

	private String paymentDate;
	private String status;
	private String paymentMethod;
	private String createdAt;
	private String isDelete;

	@OneToMany(targetEntity = WeeklyOrderEntity.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "orderListId", referencedColumnName = "id")
	private List<WeeklyOrderEntity> weeklyOrderList = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "resId")
	private RestaurantEntity restaurantEntity;
	
	

	public RestaurantEntity getRestaurantEntity() {
		return restaurantEntity;
	}

	public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
		this.restaurantEntity = restaurantEntity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getApprovedManager() {
		return approvedManager;
	}

	public void setApprovedManager(String approvedManager) {
		this.approvedManager = approvedManager;
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

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<WeeklyOrderEntity> getWeeklyOrderList() {
		return weeklyOrderList;
	}

	public void setWeeklyOrderList(List<WeeklyOrderEntity> weeklyOrderList) {
		this.weeklyOrderList = weeklyOrderList;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "OrderListEntity [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", cashier="
				+ cashier + ", receivedBy=" + receivedBy + ", approvedManager=" + approvedManager + ", totalPeople="
				+ totalPeople + ", price=" + price + ", totalPrice=" + totalPrice + ", paymentDate=" + paymentDate
				+ ", status=" + status + ", paymentMethod=" + paymentMethod + ", createdAt=" + createdAt + ", isDelete="
				+ isDelete + ", weeklyOrderList=" + weeklyOrderList + ", restaurantEntity=" + restaurantEntity + "]";
	}
	

}
