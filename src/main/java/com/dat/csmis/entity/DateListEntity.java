package com.dat.csmis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class DateListEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateListId;
    private String date;
	
	public int getDateListId() {
		return dateListId;
	}
	public void setDateListId(int dateListId) {
		this.dateListId = dateListId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DateListEntity [dateListId=" + dateListId + ", date=" + date + "]";
	}
	
    
    
}
