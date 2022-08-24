package com.advira.advirafarm.buyer.ui.discount.api;

import com.advira.advirafarm.buyer.ui.cart.api.DiscountCoupon;
import com.advira.advirafarm.buyer.ui.login.api.DefaultAddress;
import com.advira.advirafarm.buyer.ui.login.api.LoginData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscountListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("discount_coupon")
    @Expose
    private List<DiscountCoupon> discountCoupon = null;

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

    public List<DiscountCoupon> getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(List<DiscountCoupon> discountCoupon) {
        this.discountCoupon = discountCoupon;
    }

}