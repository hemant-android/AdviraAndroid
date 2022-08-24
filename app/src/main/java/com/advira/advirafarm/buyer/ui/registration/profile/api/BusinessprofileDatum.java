package com.advira.advirafarm.buyer.ui.registration.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessprofileDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_pan_no")
    @Expose
    private String companyPanNo;
    @SerializedName("company_gst_no")
    @Expose
    private String companyGstNo;
    @SerializedName("drug_license_no")
    @Expose
    private String drugLicenseNo;
    @SerializedName("company_age_year")
    @Expose
    private String companyAgeYear;
    @SerializedName("company_age_month")
    @Expose
    private String companyAgeMonth;
    @SerializedName("company_email_id")
    @Expose
    private String companyEmailId;
    @SerializedName("company_contact_ccode")
    @Expose
    private String companyContactCcode;
    @SerializedName("company_contact_stdcode")
    @Expose
    private String companyContactStdcode;
    @SerializedName("company_contact_landlineno")
    @Expose
    private String companyContactLandlineno;
    @SerializedName("business_address_1")
    @Expose
    private String businessAddress1;
    @SerializedName("business_address_2")
    @Expose
    private String businessAddress2;
    @SerializedName("business_address_state")
    @Expose
    private String businessAddressState;
    @SerializedName("business_address_city")
    @Expose
    private String businessAddressCity;
    @SerializedName("business_address_pinno")
    @Expose
    private String businessAddressPinno;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPanNo() {
        return companyPanNo;
    }

    public void setCompanyPanNo(String companyPanNo) {
        this.companyPanNo = companyPanNo;
    }

    public String getCompanyGstNo() {
        return companyGstNo;
    }

    public void setCompanyGstNo(String companyGstNo) {
        this.companyGstNo = companyGstNo;
    }

    public String getDrugLicenseNo() {
        return drugLicenseNo;
    }

    public void setDrugLicenseNo(String drugLicenseNo) {
        this.drugLicenseNo = drugLicenseNo;
    }

    public String getCompanyAgeYear() {
        return companyAgeYear;
    }

    public void setCompanyAgeYear(String companyAgeYear) {
        this.companyAgeYear = companyAgeYear;
    }

    public String getCompanyAgeMonth() {
        return companyAgeMonth;
    }

    public void setCompanyAgeMonth(String companyAgeMonth) {
        this.companyAgeMonth = companyAgeMonth;
    }

    public String getCompanyEmailId() {
        return companyEmailId;
    }

    public void setCompanyEmailId(String companyEmailId) {
        this.companyEmailId = companyEmailId;
    }

    public String getCompanyContactCcode() {
        return companyContactCcode;
    }

    public void setCompanyContactCcode(String companyContactCcode) {
        this.companyContactCcode = companyContactCcode;
    }

    public String getCompanyContactStdcode() {
        return companyContactStdcode;
    }

    public void setCompanyContactStdcode(String companyContactStdcode) {
        this.companyContactStdcode = companyContactStdcode;
    }

    public String getCompanyContactLandlineno() {
        return companyContactLandlineno;
    }

    public void setCompanyContactLandlineno(String companyContactLandlineno) {
        this.companyContactLandlineno = companyContactLandlineno;
    }

    public String getBusinessAddress1() {
        return businessAddress1;
    }

    public void setBusinessAddress1(String businessAddress1) {
        this.businessAddress1 = businessAddress1;
    }

    public String getBusinessAddress2() {
        return businessAddress2;
    }

    public void setBusinessAddress2(String businessAddress2) {
        this.businessAddress2 = businessAddress2;
    }

    public String getBusinessAddressState() {
        return businessAddressState;
    }

    public void setBusinessAddressState(String businessAddressState) {
        this.businessAddressState = businessAddressState;
    }

    public String getBusinessAddressCity() {
        return businessAddressCity;
    }

    public void setBusinessAddressCity(String businessAddressCity) {
        this.businessAddressCity = businessAddressCity;
    }

    public String getBusinessAddressPinno() {
        return businessAddressPinno;
    }

    public void setBusinessAddressPinno(String businessAddressPinno) {
        this.businessAddressPinno = businessAddressPinno;
    }

}