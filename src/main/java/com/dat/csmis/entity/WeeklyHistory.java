package com.dat.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "weekly_history")
public class WeeklyHistory {

    @Id
    @Column(name = "restaurantID")
    private Integer resId;
    @Column(name = "Priceper")
    private Integer price;
    @Column(name = "datprice")
    private double datp;
    @Column(name = "empprice")
    private double empp;
    @Column(name = "weekstart")
    private String weekst;
    @Column(name = "weekend")
    private String weeken;

    
    public WeeklyHistory() {
    }
    
    public WeeklyHistory(Integer resId, Integer price, double datp, double empp, String weekst, String weeken) {
        this.resId = resId;
        this.price = price;
        this.datp = datp;
        this.empp = empp;
        this.weekst = weekst;
        this.weeken = weeken;
    }
    @Override
    public String toString() {
        return "WeeklyHistory [resId=" + resId + ", price=" + price + ", datp=" + datp + ", empp=" + empp + ", weekst="
                + weekst + ", weeken=" + weeken + "]";
    }
    public Integer getResId() {
        return resId;
    }
    public void setResId(Integer resId) {
        this.resId = resId;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public double getDatp() {
        return datp;
    }
    public void setDatp(double datp) {
        this.datp = datp;
    }
    public double getEmpp() {
        return empp;
    }
    public void setEmpp(double empp) {
        this.empp = empp;
    }
    public String getWeekst() {
        return weekst;
    }
    public void setWeekst(String weekst) {
        this.weekst = weekst;
    }
    public String getWeeken() {
        return weeken;
    }
    public void setWeeken(String weeken) {
        this.weeken = weeken;
    }

    
    
}
