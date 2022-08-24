package com.advira.advirafarm.buyer.ui.payment.razorpay.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankEMIRequest {
    @SerializedName("bank_code")
    @Expose
    private String bankCode;
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

}