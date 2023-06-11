package com.dat.csmis.entity;

public class EmpDoorlog {
    
    private String date;
    private String eat;
    private String status;
    
    
   
    public EmpDoorlog(String date, String eat, String status) {
        this.date = date;
        this.eat = eat;
        this.status = status;
    }
    @Override
    public String toString() {
        return "EmpDoorlog [date=" + date + ", eat=" + eat + ", status=" + status + "]";
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
