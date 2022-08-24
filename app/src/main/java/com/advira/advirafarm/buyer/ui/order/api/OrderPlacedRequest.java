package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPlacedRequest {

    @SerializedName("action_type")
    @Expose
    private String actionType;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("grand_total_amount")
    @Expose
    private String grandTotalAmount;
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
    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("delivery_charges")
    @Expose
    private String deliverycharges;
    @SerializedName("delivery_slot_date")
    @Expose
    private String deliverySlotDate;
    @SerializedName("delivery_slot_time")
    @Expose
    private String deliverySlotTime;


    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public String getDeliverycharges() {
        return deliverycharges;
    }

    public void setDeliverycharges(String deliverycharges) {
        this.deliverycharges = deliverycharges;
    }

    public void setGrandTotalAmount(String grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
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

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDeliverySlotDate() {
        return deliverySlotDate;
    }

    public void setDeliverySlotDate(String deliverySlotDate) {
        this.deliverySlotDate = deliverySlotDate;
    }

    public String getDeliverySlotTime() {
        return deliverySlotTime;
    }

    public void setDeliverySlotTime(String deliverySlotTime) {
        this.deliverySlotTime = deliverySlotTime;
    }
}