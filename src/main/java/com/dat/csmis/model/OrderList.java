package com.dat.csmis.model;

import java.util.ArrayList;
import java.util.List;

import com.dat.csmis.entity.WeeklyOrderEntity;

public class OrderList {
	private List<WeeklyOrderEntity> orderList = new ArrayList<WeeklyOrderEntity>();

	private Integer resId;

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public List<WeeklyOrderEntity> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<WeeklyOrderEntity> orderList) {
		this.orderList = orderList;
	}

}
