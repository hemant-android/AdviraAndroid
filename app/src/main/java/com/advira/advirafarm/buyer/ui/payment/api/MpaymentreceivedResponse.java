package com.advira.advirafarm.buyer.ui.payment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MpaymentreceivedResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("membership_details")
    @Expose
    private MembershipDetails membershipDetails;

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

    public MembershipDetails getMembershipDetails() {
        return membershipDetails;
    }

    public void setMembershipDetails(MembershipDetails membershipDetails) {
        this.membershipDetails = membershipDetails;
    }
}
