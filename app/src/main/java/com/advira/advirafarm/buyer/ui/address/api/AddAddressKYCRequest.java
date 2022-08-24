package com.advira.advirafarm.buyer.ui.address.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddressKYCRequest {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("api_type")
    @Expose
    private String apiType;
    @SerializedName("document_imagename")
    @Expose
    private String documentImagename;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getDocumentImagename() {
        return documentImagename;
    }

    public void setDocumentImagename(String documentImagename) {
        this.documentImagename = documentImagename;
    }

}