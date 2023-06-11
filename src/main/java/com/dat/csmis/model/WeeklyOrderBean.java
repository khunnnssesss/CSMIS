package com.dat.csmis.model;

import java.util.ArrayList;
import java.util.List;

public class WeeklyOrderBean {
	private String date;
	private Integer noOfPeople;
	private List<AvoidMeatBean> avoidList = new ArrayList<>();
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getNoOfPeople() {
		return noOfPeople;
	}
	public void setNoOfPeople(Integer noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	public List<AvoidMeatBean> getAvoidList() {
		return avoidList;
	}
	public void setAvoidList(List<AvoidMeatBean> avoidList) {
		this.avoidList = avoidList;
	}

}
