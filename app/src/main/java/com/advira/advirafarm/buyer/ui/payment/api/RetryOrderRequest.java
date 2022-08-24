package com.advira.advirafarm.buyer.ui.payment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetryOrderRequest {

    @SerializedName("order_id")
    @Expose
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}