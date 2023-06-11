package com.dat.csmis.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "doorlogallviewentity")
public class DoorLogAllViewEntity {
	@EmbeddedId
    private YourEntityKey key;

    @Column(name = "Eat")
    private String eat;

    @Column(name = "status")
    private String status;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "staff_id")
    private String staffId;
    
    @Column(name = "is_delete")
    private String isDelete;
    

	

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public YourEntityKey getKey() {
		return key;
	}

	public void setKey(YourEntityKey key) {
		this.key = key;
	}

	public String getEat() {
		return eat;
	}

	public void setEat(String eat) {
		this.eat = eat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
}
