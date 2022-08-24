package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDatum_v2 {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;
    @SerializedName("grand_total_amount")
    @Expose
    private String grandTotalAmount;
    @SerializedName("totalcart_count")
    @Expose
    private String totalcartCount;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("cancel_reason_id")
    @Expose
    private String cancelReasonId;
    @SerializedName("cancel_reason_other")
    @Expose
    private String cancelReasonOther;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("max_cancellation_hours")
    @Expose
    private String maxCancellationHours;
    @SerializedName("max_retry_hours")
    @Expose
    private String maxretryHours;
    @SerializedName("max_retry_hours_time")
    @Expose
    private String maxretryhoursTime;
    @SerializedName("payment")
    @Expose
    private List<Payment_v2> payment = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public void setGrandTotalAmount(String grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    public String getTotalcartCount() {
        return totalcartCount;
    }

    public void setTotalcartCount(String totalcartCount) {
        this.totalcartCount = totalcartCount;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMaxCancellationHours() {
        return maxCancellationHours;
    }

    public void setMaxCancellationHours(String maxCancellationHours) {
        this.maxCancellationHours = maxCancellationHours;
    }

    public String getMaxretryHours() {
        return maxretryHours;
    }

    public void setMaxretryHours(String maxretryHours) {
        this.maxretryHours = maxretryHours;
    }

    public String getMaxretryhoursTime() {
        return maxretryhoursTime;
    }

    public void setMaxretryhoursTime(String maxretryhoursTime) {
        this.maxretryhoursTime = maxretryhoursTime;
    }

    public List<Payment_v2> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment_v2> payment) {
        this.payment = payment;
    }
}
