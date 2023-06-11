package com.dat.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


	
	@Entity
	@Table(name = "user_view")
	public class UserViewEntity {
	    @Id
	    
	    @Column(name = "staff_id")
	    private String staffId;
	    
	    @Column(name = "name")
	    private String name;
	    
	    @Column(name = "door_log")
	    private String doorLogNo;
	    
	    @Column(name = "status")
	    private String status;
	    
	    @Column(name = "dates")
	    private String dates;
	    
	    @Column(name = "dates_count")
	    private String datesCount;

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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		

		public String getDates() {
			return dates;
		}

		public void setDates(String dates) {
			this.dates = dates;
		}

		public String getDatesCount() {
			return datesCount;
		}

		public void setDatesCount(String datesCount) {
			this.datesCount = datesCount;
		}
	    
	    
}
