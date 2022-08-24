package com.advira.advirafarm.buyer.ui.address.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    /*@SerializedName("address_list")
    @Expose
    private List<AddressListData> addressListData = null;

*/
    @SerializedName("address_list")
    @Expose
    private List<AddressDate> addressListData = null;

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

   /* public List<AddressListData> getAddressList() {
        return addressListData;
    }

    public void setAddressList(List<AddressListData> addressListData) {
        this.addressListData = addressListData;
    }*/

    public List<AddressDate> getAddressListData() {
        return addressListData;
    }

    public void setAddressListData(List<AddressDate> addressListData) {
        this.addressListData = addressListData;
    }
}