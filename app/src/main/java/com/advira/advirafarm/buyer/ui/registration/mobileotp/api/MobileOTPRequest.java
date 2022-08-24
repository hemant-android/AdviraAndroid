package com.advira.advirafarm.buyer.ui.registration.mobileotp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileOTPRequest {

    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("login_type")
    @Expose
    private String loginType;
    @SerializedName("user_session")
    @Expose
    private String usersession;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getUsersession() {
        return usersession;
    }

    public void setUsersession(String usersession) {
        this.usersession = usersession;
    }
}

