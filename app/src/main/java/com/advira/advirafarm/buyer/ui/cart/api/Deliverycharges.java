package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deliverycharges {
    @SerializedName("less_than")
    @Expose
    private String lessThan;
    @SerializedName("greater_than")
    @Expose
    private String greaterThan;
    @SerializedName("charges")
    @Expose
    private String charges;

    public String getLessThan() {
        return lessThan;
    }

    public void setLessThan(String lessThan) {
        this.lessThan = lessThan;
    }

    public String getGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan(String greaterThan) {
        this.greaterThan = greaterThan;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }
}
