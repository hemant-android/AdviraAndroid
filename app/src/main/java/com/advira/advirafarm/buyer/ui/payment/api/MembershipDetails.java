package com.advira.advirafarm.buyer.ui.payment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipDetails {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("membership_details")
    @Expose
    private String membershipDetails;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("membership_start_date")
    @Expose
    private String membershipStartDate;
    @SerializedName("membership_exp_date")
    @Expose
    private String membershipExpDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMembershipDetails() {
        return membershipDetails;
    }

    public void setMembershipDetails(String membershipDetails) {
        this.membershipDetails = membershipDetails;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
