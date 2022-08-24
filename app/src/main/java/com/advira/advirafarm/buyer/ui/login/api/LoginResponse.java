package com.advira.advirafarm.buyer.ui.login.api;

import com.advira.advirafarm.buyer.ui.payment.api.MembershipDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cartSize")
    @Expose
    private Integer cartSize;
    @SerializedName("cartSize_B2B")
    @Expose
    private Integer cartSizeB2B;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("login_data")
    @Expose
    private LoginData loginData;
    @SerializedName("app_version")
    @Expose
    private String appversion;
    @SerializedName("default_address")
    @Expose
    private List<DefaultAddress> defaultAddress = null;
    @SerializedName("membership_Data")
    @Expose
    private List<MembershipDetails> membershipData;

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

    public Integer getCartSize() {
        return cartSize;
    }

    public void setCartSize(Integer cartSize) {
        this.cartSize = cartSize;
    }

    public Integer getCartSizeB2B() {
        return cartSizeB2B;
    }

    public void setCartSizeB2B(Integer cartSizeB2B) {
        this.cartSizeB2B = cartSizeB2B;
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

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public List<DefaultAddress> getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(List<DefaultAddress> defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public List<MembershipDetails> getMembershipData() {
        return membershipData;
    }

    public void setMembershipData(List<MembershipDetails> membershipData) {
        this.membershipData = membershipData;
    }
}