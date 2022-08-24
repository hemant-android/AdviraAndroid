package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessProfileResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("businessprofile_data")
    @Expose
    private List<BusinessprofileDatum> businessprofileData = null;

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

    public List<BusinessprofileDatum> getBusinessprofileData() {
        return businessprofileData;
    }

    public void setBusinessprofileData(List<BusinessprofileDatum> businessprofileData) {
        this.businessprofileData = businessprofileData;
    }

}