package com.advira.advirafarm.buyer.ui.discount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CouponValidationResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("discount_rule")
    @Expose
    private String discountRule;
    @SerializedName("discounted_amount")
    @Expose
    private Double discountedAmount;

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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDiscountRule() {
        return discountRule;
    }

    public void setDiscountRule(String discountRule) {
        this.discountRule = discountRule;
    }

    public Double getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(Double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

}