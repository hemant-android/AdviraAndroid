package com.advira.advirafarm.buyer.ui.discount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CouponValidationRequest {

    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("coupon_id")
    @Expose
    private String couponId;

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

}