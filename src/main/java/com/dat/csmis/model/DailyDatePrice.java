package com.dat.csmis.model;


public class DailyDatePrice {

    
    private String date;
    private String status;
    private String eat;

    public DailyDatePrice(){

    }
    
    public DailyDatePrice(String date, String status, String eat, Double price) {
        this.date = date;
        this.status = status;
        this.eat = eat;
        this.price = price;
    }
    private Double price;


    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getEat() {
        return eat;
    }
    public void setEat(String eat) {
        this.eat = eat;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DailyDatePrice [date=" + date + ", status=" + status + ", eat=" + eat + ", price=" + price + "]";
    }

    

    
}
