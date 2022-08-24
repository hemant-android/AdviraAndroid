package com.advira.advirafarm.buyer.ui.registration.profile.api.me;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditableField {

    @SerializedName("personal_info")
    @Expose
    private PersonalInfo personalInfo;
    @SerializedName("business_info")
    @Expose
    private BusinessInfo businessInfo;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

}