package com.advira.advirafarm.buyer.ui.payment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MpaymentreceivedRequest {

    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("pay_through")
    @Expose
    private String payThrough;
    @SerializedName("membership_price")
    @Expose
    private String membershipPrice;
    @SerializedName("membership_duration")
    @Expose
    private String membershipDuration;
    @SerializedName("other_pay_details")
    @Expose
    private String otherPayDetails;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayThrough() {
        return payThrough;
    }

    public void setPayThrough(String payThrough) {
        this.payThrough = payThrough;
    }

    public String getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(String membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public String getMembershipDuration() {
        return membershipDuration;
    }

    public void setMembershipDuration(String membershipDuration) {
        this.membershipDuration = membershipDuration;
    }

    public String getOtherPayDetails() {
        return otherPayDetails;
    }

    public void setOtherPayDetails(String otherPayDetails) {
        this.otherPayDetails = otherPayDetails;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
