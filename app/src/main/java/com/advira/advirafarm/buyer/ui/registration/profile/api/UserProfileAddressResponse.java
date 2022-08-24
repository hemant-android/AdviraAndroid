package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileAddressResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("profile_data")
    @Expose
    private List<ProfileDatum> profileData = null;

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

    public List<ProfileDatum> getProfileData() {
        return profileData;
    }

    public void setProfileData(List<ProfileDatum> profileData) {
        this.profileData = profileData;
    }

}