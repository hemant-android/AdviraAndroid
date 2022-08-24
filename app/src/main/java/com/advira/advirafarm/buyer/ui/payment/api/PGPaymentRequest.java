package com.advira.advirafarm.buyer.ui.payment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PGPaymentRequest {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("payment_amount")
    @Expose
    private String paymentAmount;
    @SerializedName("payment_due_amount")
    @Expose
    private String paymentDueAmount;
    @SerializedName("payment_ref")
    @Expose
    private String paymentRef;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}