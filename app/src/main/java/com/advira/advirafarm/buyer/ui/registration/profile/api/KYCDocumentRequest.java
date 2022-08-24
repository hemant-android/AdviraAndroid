package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KYCDocumentRequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doc_type")
    @Expose
    private String docType;
    @SerializedName("api_type")
    @Expose
    private String apiType;
    @SerializedName("document_imagename")
    @Expose
    private String documentImagename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
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