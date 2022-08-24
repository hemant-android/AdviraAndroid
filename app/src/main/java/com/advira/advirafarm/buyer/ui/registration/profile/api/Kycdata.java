package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kycdata {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("gst_document_imagename")
    @Expose
    private String gstDocumentImagename;
    @SerializedName("gst_document_imageurl")
    @Expose
    private String gstDocumentImageurl;
    @SerializedName("pancard_document_imagename")
    @Expose
    private String pancardDocumentImagename;
    @SerializedName("pancard_document_imageurl")
    @Expose
    private String pancardDocumentImageurl;
    @SerializedName("drug_document_imagename")
    @Expose
    private String drugDocumentImagename;
    @SerializedName("drug_document_imageurl")
    @Expose
    private String drugDocumentImageurl;
    @SerializedName("other_doc_type")
    @Expose
    private String otherDocType;
    @SerializedName("other_document_imagename")
    @Expose
    private String otherDocumentImagename;
    @SerializedName("other_document_imageurl")
    @Expose
    private String otherDocumentImageurl;

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

    public String getGstDocumentImagename() {
        return gstDocumentImagename;
    }

    public void setGstDocumentImagename(String gstDocumentImagename) {
        this.gstDocumentImagename = gstDocumentImagename;
    }

    public String getGstDocumentImageurl() {
        return gstDocumentImageurl;
    }

    public void setGstDocumentImageurl(String gstDocumentImageurl) {
        this.gstDocumentImageurl = gstDocumentImageurl;
    }

    public String getPancardDocumentImagename() {
        return pancardDocumentImagename;
    }

    public void setPancardDocumentImagename(String pancardDocumentImagename) {
        this.pancardDocumentImagename = pancardDocumentImagename;
    }

    public String getPancardDocumentImageurl() {
        return pancardDocumentImageurl;
    }

    public void setPancardDocumentImageurl(String pancardDocumentImageurl) {
        this.pancardDocumentImageurl = pancardDocumentImageurl;
    }

    public String getDrugDocumentImagename() {
        return drugDocumentImagename;
    }

    public void setDrugDocumentImagename(String drugDocumentImagename) {
        this.drugDocumentImagename = drugDocumentImagename;
    }

    public String getDrugDocumentImageurl() {
        return drugDocumentImageurl;
    }

    public void setDrugDocumentImageurl(String drugDocumentImageurl) {
        this.drugDocumentImageurl = drugDocumentImageurl;
    }

    public String getOtherDocType() {
        return otherDocType;
    }

    public void setOtherDocType(String otherDocType) {
        this.otherDocType = otherDocType;
    }

    public String getOtherDocumentImagename() {
        return otherDocumentImagename;
    }

    public void setOtherDocumentImagename(String otherDocumentImagename) {
        this.otherDocumentImagename = otherDocumentImagename;
    }

    public String getOtherDocumentImageurl() {
        return otherDocumentImageurl;
    }

    public void setOtherDocumentImageurl(String otherDocumentImageurl) {
        this.otherDocumentImageurl = otherDocumentImageurl;
    }

}