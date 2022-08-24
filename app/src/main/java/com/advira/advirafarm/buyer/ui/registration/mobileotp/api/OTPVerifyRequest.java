package com.advira.advirafarm.buyer.ui.registration.mobileotp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPVerifyRequest {

    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}

