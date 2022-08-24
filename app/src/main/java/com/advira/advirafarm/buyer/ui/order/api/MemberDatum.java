package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDatum {

    @SerializedName("membership_details")
    @Expose
    private String membershipDetails;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("membership_start_date")
    @Expose
    private String membershipStartDate;
    @SerializedName("membership_exp_date")
    @Expose
    private String membershipExpDate;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getMembershipDetails() {
        return membershipDetails;
    }

    public void setMembershipDetails(String membershipDetails) {
        this.membershipDetails = membershipDetails;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(String membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public String getMembershipExpDate() {
        return membershipExpDate;
    }

    public void setMembershipExpDate(String membershipExpDate) {
        this.membershipExpDate = membershipExpDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
