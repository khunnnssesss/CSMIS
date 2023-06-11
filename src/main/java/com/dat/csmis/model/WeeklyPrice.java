package com.dat.csmis.model;

public class WeeklyPrice {
    
    private Integer resId;
    private Integer resPrice;
    private double datPrice;
    private double empPrice;

    
    public WeeklyPrice() {
    }
    
    public WeeklyPrice(Integer resId, Integer resPrice, double datPrice, double empPrice) {
        this.resId = resId;
        this.resPrice = resPrice;
        this.datPrice = datPrice;
        this.empPrice = empPrice;
    }
    @Override
    public String toString() {
        return "WeeklyPrice [resId=" + resId + ", resPrice=" + resPrice + ", datPrice=" + datPrice + ", empPrice="
                + empPrice + "]";
    }
    public Integer getResId() {
        return resId;
    }
    public void setResId(Integer resId) {
        this.resId = resId;
    }
    public Integer getResPrice() {
        return resPrice;
    }
    public void setResPrice(Integer resPrice) {
        this.resPrice = resPrice;
    }
    public double getDatPrice() {
        return datPrice;
    }
    public void setDatPrice(double datPrice) {
        this.datPrice = datPrice;
    }
    public double getEmpPrice() {
        return empPrice;
    }
    public void setEmpPrice(double empPrice) {
        this.empPrice = empPrice;
    }

    
}
