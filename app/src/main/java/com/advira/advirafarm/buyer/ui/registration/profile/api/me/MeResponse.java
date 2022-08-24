package com.advira.advirafarm.buyer.ui.registration.profile.api.me;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order_count")
    @Expose
    private Integer orderCount;
    @SerializedName("basic_info")
    @Expose
    private List<BasicInfo> basicInfo = null;
    @SerializedName("business_profile")
    @Expose
    private List<BusinessProfile> businessProfile = null;
    @SerializedName("kyc_info")
    @Expose
    private List<KycInfo> kycInfo = null;
    @SerializedName("profile_picture")
    @Expose
    private ProfilePicture profilePicture;
    @SerializedName("profile_completion")
    @Expose
    private Integer profileCompletion;
    @SerializedName("editable_fields")
    @Expose
    private List<EditableField> editableFields = null;

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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public List<BasicInfo> getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(List<BasicInfo> basicInfo) {
        this.basicInfo = basicInfo;
    }

    public List<BusinessProfile> getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(List<BusinessProfile> businessProfile) {
        this.businessProfile = businessProfile;
    }

    public List<KycInfo> getKycInfo() {
        return kycInfo;
    }

    public void setKycInfo(List<KycInfo> kycInfo) {
        this.kycInfo = kycInfo;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getProfileCompletion() {
        return profileCompletion;
    }

    public void setProfileCompletion(Integer profileCompletion) {
        this.profileCompletion = profileCompletion;
    }

    public List<EditableField> getEditableFields() {
        return editableFields;
    }

    public void setEditableFields(List<EditableField> editableFields) {
        this.editableFields = editableFields;
    }



}