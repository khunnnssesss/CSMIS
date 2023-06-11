package com.dat.csmis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeatList {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String meat;
	    private String isDelete;
	    
	    public Integer getId() {
	        return id;
	    }
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    public String getMeat() {
	        return meat;
	    }
	    public void setMeat(String meat) {
	        this.meat = meat;
	    }
		public String getIsDelete() {
			return isDelete;
		}
		public void setIsDelete(String isDelete) {
			this.isDelete = isDelete;
		}

    
}
