package com.dat.csmis.model;

public class DailyRegister {
    
    private String date;
    private String eat;
    private String status;
    private Double datprice;
    private Double empprice;

    
    public DailyRegister() {
    }

    
    public DailyRegister(String date, String eat, String status, Double datPrice, Double empPrice) {
        this.date = date;
        this.eat = eat;
        this.status = status;
        this.datprice = datPrice;
        this.empprice = empPrice;
    }


    @Override
    public String toString() {
        return "DailyRegister [date=" + date + ", eat=" + eat + ", status=" + status + ", datPrice=" + datprice
                + ", empPrice=" + empprice + "]";
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
    public Double getDatPrice() {
        return datprice;
    }
    public void setDatPrice(Double datPrice) {
        this.datprice = datPrice;
    }
    public Double getEmpPrice() {
        return empprice;
    }
    public void setEmpPrice(Double empPrice) {
        this.empprice = empPrice;
    }

    
}
