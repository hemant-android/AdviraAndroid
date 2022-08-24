package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PincodeDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("is_operational")
    @Expose
    private String isOperational;
    @SerializedName("approx_delivery_time")
    @Expose
    private String approxDeliveryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getIsOperational() {
        return isOperational;
    }

    public void setIsOperational(String isOperational) {
        this.isOperational = isOperational;
    }

    public String getApproxDeliveryTime() {
        return approxDeliveryTime;
    }

    public void setApproxDeliveryTime(String approxDeliveryTime) {
        this.approxDeliveryTime = approxDeliveryTime;
    }

}