package com.floki.entity;

public class Summary {

    private Float capital;
    private Float benefit;
    private Float extras;
    private Float totalOrder;

    public Summary(Float capital, Float benefit, Float extras, Float totalOrder) {
        this.capital = capital;
        this.benefit = benefit;
        this.extras = extras;
        this.totalOrder = totalOrder;
    }

    public Float getCapital() {
        return capital;
    }

    public void setCapital(Float capital) {
        this.capital = capital;
    }

    public Float getBenefit() {
        return benefit;
    }

    public void setBenefit(Float benefit) {
        this.benefit = benefit;
    }

    public Float getExtras() {
        return extras;
    }

    public void setExtras(Float extras) {
        this.extras = extras;
    }

    public Float getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Float totalOrder) {
        this.totalOrder = totalOrder;
    }
}
