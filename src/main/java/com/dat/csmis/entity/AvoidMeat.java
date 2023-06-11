package com.dat.csmis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class AvoidMeat {
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private String count;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AvoidMeat [id=" + id + ", name=" + name + ", count=" + count + "]";
	}
	
}
