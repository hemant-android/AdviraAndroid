package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCancelRequest {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("cancel_reason_id")
    @Expose
    private String cancelReasonId;
    @SerializedName("cancel_reason_other")
    @Expose
    private String cancelReasonOther;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCancelReasonOther() {
        return cancelReasonOther;
    }

    public void setCancelReasonOther(String cancelReasonOther) {
        this.cancelReasonOther = cancelReasonOther;
    }

}