package com.dat.csmis.model;

public class WeeklyLists {
    
    private String date;
    private String day;

    public WeeklyLists(){

    }
    
    public WeeklyLists(String date, String day) {
        this.date = date;
        this.day = day;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    
}
