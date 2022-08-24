package com.advira.advirafarm.buyer.ui.wallet.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletPaymentRequest {

    @SerializedName("order_init_id")
    @Expose
    private String orderInitId;
    @SerializedName("razpay_payment_id")
    @Expose
    private String razpayPaymentId;
    @SerializedName("order_pay_through")
    @Expose
    private String orderPayThrough;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_ref")
    @Expose
    private String paymentRef;

    public String getOrderInitId() {
        return orderInitId;
    }

    public void setOrderInitId(String orderInitId) {
        this.orderInitId = orderInitId;
    }

    public String getRazpayPaymentId() {
        return razpayPaymentId;
    }

    public void setRazpayPaymentId(String razpayPaymentId) {
        this.razpayPaymentId = razpayPaymentId;
    }

    public String getOrderPayThrough() {
        return orderPayThrough;
    }

    public void setOrderPayThrough(String orderPayThrough) {
        this.orderPayThrough = orderPayThrough;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }
}
