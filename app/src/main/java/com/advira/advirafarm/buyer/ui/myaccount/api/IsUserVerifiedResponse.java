package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsUserVerifiedResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("basic_registration_status")
    @Expose
    private String basicRegistrationStatus;
    @SerializedName("personal_profile_status")
    @Expose
    private String personalProfileStatus;
    @SerializedName("business_profile_status")
    @Expose
    private String businessProfileStatus;
    @SerializedName("profileActivate_status")
    @Expose
    private String profileActivateStatus;
    @SerializedName("kyc_document_status")
    @Expose
    private String kycDocumentStatus;


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

    public String getBasicRegistrationStatus() {
        return basicRegistrationStatus;
    }

    public void setBasicRegistrationStatus(String basicRegistrationStatus) {
        this.basicRegistrationStatus = basicRegistrationStatus;
    }

    public String getPersonalProfileStatus() {
        return personalProfileStatus;
    }

    public void setPersonalProfileStatus(String personalProfileStatus) {
        this.personalProfileStatus = personalProfileStatus;
    }

    public String getBusinessProfileStatus() {
        return businessProfileStatus;
    }

    public void setBusinessProfileStatus(String businessProfileStatus) {
        this.businessProfileStatus = businessProfileStatus;
    }

    public String getProfileActivateStatus() {
        return profileActivateStatus;
    }

    public void setProfileActivateStatus(String profileActivateStatus) {
        this.profileActivateStatus = profileActivateStatus;
    }


    public String getKycDocumentStatus() {
        return kycDocumentStatus;
    }

    public void setKycDocumentStatus(String kycDocumentStatus) {
        this.kycDocumentStatus = kycDocumentStatus;
    }

}