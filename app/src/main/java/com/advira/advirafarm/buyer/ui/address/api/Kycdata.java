package com.advira.advirafarm.buyer.ui.address.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kycdata {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("document_imagename")
    @Expose
    private String documentImagename;
    @SerializedName("document_imageurl")
    @Expose
    private String documentImageurl;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDocumentImagename() {
        return documentImagename;
    }

    public void setDocumentImagename(String documentImagename) {
        this.documentImagename = documentImagename;
    }

    public String getDocumentImageurl() {
        return documentImageurl;
    }

    public void setDocumentImageurl(String documentImageurl) {
        this.documentImageurl = documentImageurl;
    }

}