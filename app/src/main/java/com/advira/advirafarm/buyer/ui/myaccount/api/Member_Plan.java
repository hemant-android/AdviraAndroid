package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Member_Plan {

    @SerializedName("membershipId")
    @Expose
    private String membershipId;
    @SerializedName("membership_name")
    @Expose
    private String membershipName;
    @SerializedName("membership_details")
    @Expose
    private String membershipDetails;
    @SerializedName("membership_benefits")
    @Expose
    private String membershipBenefits;
    @SerializedName("membership_prices")
    @Expose
    private List<MembershipPrice> membershipPrices = null;
    @SerializedName("membership_faq")
    @Expose
    private List<MembershipFaq> membershipFaq = null;

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getMembershipDetails() {
        return membershipDetails;
    }

    public void setMembershipDetails(String membershipDetails) {
        this.membershipDetails = membershipDetails;
    }

    public String getMembershipBenefits() {
        return membershipBenefits;
    }

    public void setMembershipBenefits(String membershipBenefits) {
        this.membershipBenefits = membershipBenefits;
    }

    public List<MembershipPrice> getMembershipPrices() {
        return membershipPrices;
    }

    public void setMembershipPrices(List<MembershipPrice> membershipPrices) {
        this.membershipPrices = membershipPrices;
    }

    public List<MembershipFaq> getMembershipFaq() {
        return membershipFaq;
    }

    public void setMembershipFaq(List<MembershipFaq> membershipFaq) {
        this.membershipFaq = membershipFaq;
    }
}
