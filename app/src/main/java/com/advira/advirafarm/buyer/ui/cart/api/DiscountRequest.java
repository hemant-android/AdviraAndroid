package com.advira.advirafarm.buyer.ui.cart.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountRequest {
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;

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

}