package com.advira.advirafarm.buyer.ui.splash.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppInstallRequest {

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("secritKey")
    @Expose
    private String secritKey;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("user_session")
    @Expose
    private String usersession;
    /*@SerializedName("app_version")
    @Expose
    private String appversion;*/

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSecritKey() {
        return secritKey;
    }

    public void setSecritKey(String secritKey) {
        this.secritKey = secritKey;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getUsersession() {
        return usersession;
    }

    public void setUsersession(String usersession) {
        this.usersession = usersession;
    }

    /*public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }*/
}