package com.dat.csmis.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
@Entity
public class RegisterInfo {
	 @Id
	    private String empId;
	    private boolean emailNoti;
	    @Column(name="door_log")
	    private String doorlog;

	    public String getDoorlog() {
			return doorlog;
		}

		public void setDoorlog(String doorlog) {
			this.doorlog = doorlog;
		}

		@OneToMany(targetEntity = AvoidMeatEntity.class, cascade = CascadeType.ALL,orphanRemoval = true)
	    @JoinColumn(name = "employeeId", referencedColumnName = "empId")
	private List<AvoidMeatEntity> avoidList;

	@OneToMany(targetEntity = DateListEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
	    @JoinColumn(name = "employeeId", referencedColumnName = "empId")
	private List<DateListEntity> dateList;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public boolean isEmailNoti() {
		return emailNoti;
	}

	public void setEmailNoti(boolean emailNoti) {
		this.emailNoti = emailNoti;
	}

	public List<AvoidMeatEntity> getAvoidList() {
		return avoidList;
	}

	public void setAvoidList(List<AvoidMeatEntity> avoidList) {
		this.avoidList = avoidList;
	}

	public List<DateListEntity> getDateList() {
		return dateList;
	}

	public void setDateList(List<DateListEntity> dateList) {
		this.dateList = dateList;
	}

	@Override
	public String toString() {
		return "RegisterInfo [empId=" + empId + ", emailNoti=" + emailNoti + ", avoidList=" + avoidList + ", dateList="
				+ dateList + "]";
	}

}
