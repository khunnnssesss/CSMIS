package com.dat.csmis.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class DoorlogComposite implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String doorLog;
	    private String date;
		public String getDoorLog() {
			return doorLog;
		}
		public void setDoorLog(String doorLog) {
			this.doorLog = doorLog;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return Objects.hash(doorLog, date);
			
		}
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        DoorlogComposite that = (DoorlogComposite) o;
	        return Objects.equals(doorLog, that.doorLog) &&
	                Objects.equals(date, that.date);
			
		}
	    
}
