package com.advira.advirafarm.buyer.ui.address.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddAddressResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("address_date")
    @Expose
    private List<AddressDate> addressDate = null;

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

    public List<AddressDate> getAddressDate() {
        return addressDate;
    }

    public void setAddressDate(List<AddressDate> addressDate) {
        this.addressDate = addressDate;
    }

}