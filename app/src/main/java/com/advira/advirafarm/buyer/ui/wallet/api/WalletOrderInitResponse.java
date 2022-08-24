package com.advira.advirafarm.buyer.ui.wallet.api;

import com.advira.advirafarm.buyer.ui.payment.api.OrderInit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletOrderInitResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order_init")
    @Expose
    private OrderInit orderInit;

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

    public OrderInit getOrderInit() {
        return orderInit;
    }

    public void setOrderInit(OrderInit orderInit) {
        this.orderInit = orderInit;
    }
}
