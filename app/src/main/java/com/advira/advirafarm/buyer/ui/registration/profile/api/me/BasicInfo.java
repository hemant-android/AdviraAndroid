package com.advira.advirafarm.buyer.ui.registration.profile.api.me;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified")
    @Expose
    private String emailVerified;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("user_dob")
    @Expose
    private String userDob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;


    @SerializedName("user_has_credit")
    @Expose
    private String userHasCredit;
    @SerializedName("credit_limit")
    @Expose
    private String creditLimit;
    @SerializedName("credit_availed")
    @Expose
    private String creditAvailed;
    @SerializedName("credit_balance")
    @Expose
    private String creditBalance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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


    public String getUserHasCredit() {
        return userHasCredit;
    }

    public void setUserHasCredit(String userHasCredit) {
        this.userHasCredit = userHasCredit;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCreditAvailed() {
        return creditAvailed;
    }

    public void setCreditAvailed(String creditAvailed) {
        this.creditAvailed = creditAvailed;
    }

    public String getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(String creditBalance) {
        this.creditBalance = creditBalance;
    }
}