package com.advira.advirafarm.buyer.ui.order.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPayment {

    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("order_amount_due")
    @Expose
    private String orderAmountDue;
    @SerializedName("amount_paid")
    @Expose
    private Integer amountPaid;
    @SerializedName("amount_payable")
    @Expose
    private String amountPayable;
    @SerializedName("minimum_payment")
    @Expose
    private Integer minimumPayment;
    @SerializedName("maximum_payment")
    @Expose
    private String maximumPayment;

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderAmountDue() {
        return orderAmountDue;
    }

    public void setOrderAmountDue(String orderAmountDue) {
        this.orderAmountDue = orderAmountDue;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(String amountPayable) {
        this.amountPayable = amountPayable;
    }

    public Integer getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(Integer minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    public String getMaximumPayment() {
        return maximumPayment;
    }

    public void setMaximumPayment(String maximumPayment) {
        this.maximumPayment = maximumPayment;
    }

}