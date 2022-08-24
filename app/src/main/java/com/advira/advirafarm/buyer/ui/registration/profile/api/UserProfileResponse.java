package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.advira.advirafarm.buyer.ui.login.api.DefaultAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cartSize")
    @Expose
    private String cartSize;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("login_data")
    @Expose
    private LoginData_profileupdate loginData;

    @SerializedName("default_address")
    @Expose
    private List<DefaultAddress_profileupdate> defaultAddress = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCartSize() {
        return cartSize;
    }

    public void setCartSize(String cartSize) {
        this.cartSize = cartSize;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public LoginData_profileupdate getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData_profileupdate loginData) {
        this.loginData = loginData;
    }

    public List<DefaultAddress_profileupdate> getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(List<DefaultAddress_profileupdate> defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}