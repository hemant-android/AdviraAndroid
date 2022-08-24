package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_dob")
    @Expose
    private String userDob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("pan_card")
    @Expose
    private String panCard;
    @SerializedName("aadhar_card")
    @Expose
    private String aadharCard;
    @SerializedName("current_address_1")
    @Expose
    private String currentAddress1;
    @SerializedName("current_address_2")
    @Expose
    private String currentAddress2;
    @SerializedName("current_address_state")
    @Expose
    private String currentAddressState;
    @SerializedName("current_address_city")
    @Expose
    private String currentAddressCity;
    @SerializedName("current_address_pinno")
    @Expose
    private String currentAddressPinno;
    @SerializedName("current_address_as_p_address")
    @Expose
    private String currentAddressAsPAddress;
    @SerializedName("permanent_address_1")
    @Expose
    private String permanentAddress1;
    @SerializedName("permanent_address_2")
    @Expose
    private String permanentAddress2;
    @SerializedName("permanent_address_state")
    @Expose
    private String permanentAddressState;
    @SerializedName("permanent_address_city")
    @Expose
    private String permanentAddressCity;
    @SerializedName("permanent_address_pinno")
    @Expose
    private String permanentAddressPinno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public String getCurrentAddress1() {
        return currentAddress1;
    }

    public void setCurrentAddress1(String currentAddress1) {
        this.currentAddress1 = currentAddress1;
    }

    public String getCurrentAddress2() {
        return currentAddress2;
    }

    public void setCurrentAddress2(String currentAddress2) {
        this.currentAddress2 = currentAddress2;
    }

    public String getCurrentAddressState() {
        return currentAddressState;
    }

    public void setCurrentAddressState(String currentAddressState) {
        this.currentAddressState = currentAddressState;
    }

    public String getCurrentAddressCity() {
        return currentAddressCity;
    }

    public void setCurrentAddressCity(String currentAddressCity) {
        this.currentAddressCity = currentAddressCity;
    }

    public String getCurrentAddressPinno() {
        return currentAddressPinno;
    }

    public void setCurrentAddressPinno(String currentAddressPinno) {
        this.currentAddressPinno = currentAddressPinno;
    }

    public String getCurrentAddressAsPAddress() {
        return currentAddressAsPAddress;
    }

    public void setCurrentAddressAsPAddress(String currentAddressAsPAddress) {
        this.currentAddressAsPAddress = currentAddressAsPAddress;
    }

    public String getPermanentAddress1() {
        return permanentAddress1;
    }

    public void setPermanentAddress1(String permanentAddress1) {
        this.permanentAddress1 = permanentAddress1;
    }

    public String getPermanentAddress2() {
        return permanentAddress2;
    }

    public void setPermanentAddress2(String permanentAddress2) {
        this.permanentAddress2 = permanentAddress2;
    }

    public String getPermanentAddressState() {
        return permanentAddressState;
    }

    public void setPermanentAddressState(String permanentAddressState) {
        this.permanentAddressState = permanentAddressState;
    }

    public String getPermanentAddressCity() {
        return permanentAddressCity;
    }

    public void setPermanentAddressCity(String permanentAddressCity) {
        this.permanentAddressCity = permanentAddressCity;
    }

    public String getPermanentAddressPinno() {
        return permanentAddressPinno;
    }

    public void setPermanentAddressPinno(String permanentAddressPinno) {
        this.permanentAddressPinno = permanentAddressPinno;
    }

}