package com.dat.csmis.model;

public class AvoidMeatBean {
	private String date;
	private String name;
	private String count;
	

	public AvoidMeatBean(String date, String name, String count) {
		super();
		this.date = date;
		this.name = name;
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
