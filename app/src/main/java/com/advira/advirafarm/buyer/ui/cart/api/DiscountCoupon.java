package com.advira.advirafarm.buyer.ui.cart.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountCoupon {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("discount_coupon_name")
    @Expose
    private String discountCouponName;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("discount_details")
    @Expose
    private String discountDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscountCouponName() {
        return discountCouponName;
    }

    public void setDiscountCouponName(String discountCouponName) {
        this.discountCouponName = discountCouponName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(String discountDetails) {
        this.discountDetails = discountDetails;
    }

}