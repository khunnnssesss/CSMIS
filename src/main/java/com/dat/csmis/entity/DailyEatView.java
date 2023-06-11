package com.dat.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "daily_eat_detail")
public class DailyEatView {

    @Id
    @Column(name = "door_log")
    private String doorlog;
    @Column(name = "date")
    private String date;
    @Column(name = "Eat")
    private String eat;
    @Column(name = "status")
    private String status;
    @Column(name = "name")
    private String name;
    @Column(name = "staff_id")
    private String staffId;
    @Column(name = "res_id")
    private Integer resId;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
    @Column(name = "dat_price")
    private Double datPrice;
    @Column(name = "emp_price")
    private Double empPrice;
    
    
    public DailyEatView() {
    }

    @Override
    public String toString() {
        return "DailyEatView [doorlog=" + doorlog + ", date=" + date + ", eat=" + eat + ", status=" + status + ", name="
                + name + ", staffId=" + staffId + ", resId=" + resId + ", startDate=" + startDate + ", endDate="
                + endDate + ", datPrice=" + datPrice + ", empPrice=" + empPrice + "]";
    }

    public DailyEatView(String doorlog, String date, String eat, String status, String name, String staffId,
            Integer resId, String startDate, String endDate, Double datPrice, Double empPrice) {
        this.doorlog = doorlog;
        this.date = date;
        this.eat = eat;
        this.status = status;
        this.name = name;
        this.staffId = staffId;
        this.resId = resId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.datPrice = datPrice;
        this.empPrice = empPrice;
    }
    public String getDoorlog() {
        return doorlog;
    }
    public void setDoorlog(String doorlog) {
        this.doorlog = doorlog;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStaffId() {
        return staffId;
    }
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public Integer getResId() {
        return resId;
    }
    public void setResId(Integer resId) {
        this.resId = resId;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public Double getDatPrice() {
        return datPrice;
    }
    public void setDatPrice(Double datPrice) {
        this.datPrice = datPrice;
    }
    public Double getEmpPrice() {
        return empPrice;
    }
    public void setEmpPrice(Double empPrice) {
        this.empPrice = empPrice;
    }

    
}
