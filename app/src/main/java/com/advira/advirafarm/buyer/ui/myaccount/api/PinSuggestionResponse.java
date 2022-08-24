package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PinSuggestionResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Pincode_details")
    @Expose
    private List<PincodeDetail> pincodeDetails = null;

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

    public List<PincodeDetail> getPincodeDetails() {
        return pincodeDetails;
    }

    public void setPincodeDetails(List<PincodeDetail> pincodeDetails) {
        this.pincodeDetails = pincodeDetails;
    }

}