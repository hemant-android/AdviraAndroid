package com.advira.advirafarm.buyer.ui.address.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAddressRequest {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("is_dafault")
    @Expose
    private String isDafault;
    @SerializedName("is_primery")
    @Expose
    private String isPrimery;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("mobile_no")
    @Expose
    private String mobileno;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getIsDafault() {
        return isDafault;
    }

    public void setIsDafault(String isDafault) {
        this.isDafault = isDafault;
    }

    public String getIsPrimery() {
        return isPrimery;
    }

    public void setIsPrimery(String isPrimery) {
        this.isPrimery = isPrimery;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}