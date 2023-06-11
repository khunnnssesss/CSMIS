package com.dat.csmis.entity;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doorlog_view")
public class DoorLogViewEntity {
	@Id
    @Column(name = "door_log")
    private String doorLogNo;

    @Column(name = "date")
    private String date;

    @Column(name = "Eat")
    private String eat;

    @Column(name = "status")
    private String status;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "staff_id")
    private String staffId;
    
    

    

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoorLogNo() {
    return doorLogNo;
  }

  public void setDoorLogNo(String doorLogNo) {
    this.doorLogNo = doorLogNo;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
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

 

}




