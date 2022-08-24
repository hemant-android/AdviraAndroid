package com.advira.advirafarm.buyer.ui.registration.profile.api.me;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycInfo {
    @SerializedName("doc_type_id")
    @Expose
    private String docTypeId;
    @SerializedName("doc_type_name")
    @Expose
    private String docTypeName;
    @SerializedName("verification_status")
    @Expose
    private String verificationStatus;

    public String getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(String docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

}