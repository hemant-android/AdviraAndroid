package com.advira.advirafarm.buyer.ui.cart.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscountResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("credit_details")
    @Expose
    private List<CreditDetail> creditDetails = null;
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

    public List<CreditDetail> getCreditDetails() {
        return creditDetails;
    }

    public void setCreditDetails(List<CreditDetail> creditDetails) {
        this.creditDetails = creditDetails;
    }

    public List<DiscountCoupon> getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(List<DiscountCoupon> discountCoupon) {
        this.discountCoupon = discountCoupon;
    }

}