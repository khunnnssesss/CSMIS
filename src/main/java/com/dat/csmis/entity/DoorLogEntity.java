package com.dat.csmis.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DoorLogEntity {
	@EmbeddedId
	private DoorlogComposite key;
	@Column(nullable = false)
	private String name;
	@Column
	private String staffId;
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Column(nullable = false)
	private String dept;

	

	public DoorlogComposite getKey() {
		return key;
	}

	public void setKey(DoorlogComposite key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	
	
	
	
	
	

}
