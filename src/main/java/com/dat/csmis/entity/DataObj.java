package com.dat.csmis.entity;

public class DataObj {
    
    private String doorlog;
    private String startdate;
    private String enddate;
    
    @Override
    public String toString() {
        return "DataObj [doorlog=" + doorlog + ", startdate=" + startdate + ", enddate=" + enddate + "]";
    }
    public String getDoorlog() {
        return doorlog;
    }
    public void setDoorlog(String doorlog) {
        this.doorlog = doorlog;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return enddate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }


}
