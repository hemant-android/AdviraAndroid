package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payment_data")
    @Expose
    private com.advira.advirafarm.buyer.ui.order.api.PaymentData paymentData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.advira.advirafarm.buyer.ui.order.api.PaymentData getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(com.advira.advirafarm.buyer.ui.order.api.PaymentData paymentData) {
        this.paymentData = paymentData;
    }

}