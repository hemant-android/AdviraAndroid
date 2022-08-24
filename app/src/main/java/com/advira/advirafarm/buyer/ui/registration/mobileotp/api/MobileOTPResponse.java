package com.advira.advirafarm.buyer.ui.registration.mobileotp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileOTPResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success_data")
    @Expose
    private SuccessData successData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SuccessData getSuccessData() {
        return successData;
    }

    public void setSuccessData(SuccessData successData) {
        this.successData = successData;
    }

}

