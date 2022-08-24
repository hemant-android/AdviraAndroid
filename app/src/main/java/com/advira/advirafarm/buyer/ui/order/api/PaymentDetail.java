package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("order_RegNo")
    @Expose
    private String orderRegNo;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_amount")
    @Expose
    private String paymentAmount;
    @SerializedName("payment_due_amount")
    @Expose
    private String paymentDueAmount;
    @SerializedName("payment_ref")
    @Expose
    private String paymentRef;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderRegNo() {
        return orderRegNo;
    }

    public void setOrderRegNo(String orderRegNo) {
        this.orderRegNo = orderRegNo;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDueAmount() {
        return paymentDueAmount;
    }

    public void setPaymentDueAmount(String paymentDueAmount) {
        this.paymentDueAmount = paymentDueAmount;
    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}