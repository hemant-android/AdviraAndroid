package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("new_payment")
    @Expose
    //private NewPayment newPayment;
    private List<NewPayment> newPayment = null;

    @SerializedName("payment_details")
    @Expose
    private List<PaymentDetail> paymentDetails = null;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;

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

    public List<NewPayment> getNewPayment() {
        return newPayment;
    }

    public void setNewPayment(List<NewPayment> newPayment) {
        this.newPayment = newPayment;
    }

    public List<PaymentDetail> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<com.advira.advirafarm.buyer.ui.order.api.OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}