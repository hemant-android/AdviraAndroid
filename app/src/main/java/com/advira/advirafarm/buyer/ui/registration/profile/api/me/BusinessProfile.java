package com.advira.advirafarm.buyer.ui.registration.profile.api.me;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessProfile {

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
    @SerializedName("no_gst_declaration")
    @Expose
    private String noGstDeclaration;
    @SerializedName("company_gst_no")
    @Expose
    private String companyGstNo;
    @SerializedName("fssai_no")
    @Expose
    private String fssaiNo;
    @SerializedName("fssai_license_expiry")
    @Expose
    private String fssaiLicenseExpiry;
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
    @SerializedName("business_address_state_name")
    @Expose
    private String businessAddressStateName;
    @SerializedName("business_address_city")
    @Expose
    private String businessAddressCity;
    @SerializedName("business_address_city_name")
    @Expose
    private String businessAddressCityName;
    @SerializedName("business_address_pinno")
    @Expose
    private String businessAddressPinno;
    @SerializedName("no_gst_declaration_acceptance")
    @Expose
    private String noGstDeclarationAcceptance;
    @SerializedName("no_gst_declaration_acceptance_name")
    @Expose
    private String noGstDeclarationAcceptanceName;

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

    public String getNoGstDeclaration() {
        return noGstDeclaration;
    }

    public void setNoGstDeclaration(String noGstDeclaration) {
        this.noGstDeclaration = noGstDeclaration;
    }

    public String getCompanyGstNo() {
        return companyGstNo;
    }

    public void setCompanyGstNo(String companyGstNo) {
        this.companyGstNo = companyGstNo;
    }

    public String getFssaiNo() {
        return fssaiNo;
    }

    public void setFssaiNo(String fssaiNo) {
        this.fssaiNo = fssaiNo;
    }

    public String getFssaiLicenseExpiry() {
        return fssaiLicenseExpiry;
    }

    public void setFssaiLicenseExpiry(String fssaiLicenseExpiry) {
        this.fssaiLicenseExpiry = fssaiLicenseExpiry;
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

    public String getBusinessAddressStateName() {
        return businessAddressStateName;
    }

    public void setBusinessAddressStateName(String businessAddressStateName) {
        this.businessAddressStateName = businessAddressStateName;
    }

    public String getBusinessAddressCity() {
        return businessAddressCity;
    }

    public void setBusinessAddressCity(String businessAddressCity) {
        this.businessAddressCity = businessAddressCity;
    }

    public String getBusinessAddressCityName() {
        return businessAddressCityName;
    }

    public void setBusinessAddressCityName(String businessAddressCityName) {
        this.businessAddressCityName = businessAddressCityName;
    }

    public String getBusinessAddressPinno() {
        return businessAddressPinno;
    }

    public void setBusinessAddressPinno(String businessAddressPinno) {
        this.businessAddressPinno = businessAddressPinno;
    }

    public String getNoGstDeclarationAcceptance() {
        return noGstDeclarationAcceptance;
    }

    public void setNoGstDeclarationAcceptance(String noGstDeclarationAcceptance) {
        this.noGstDeclarationAcceptance = noGstDeclarationAcceptance;
    }

    public String getNoGstDeclarationAcceptanceName() {
        return noGstDeclarationAcceptanceName;
    }

    public void setNoGstDeclarationAcceptanceName(String noGstDeclarationAcceptanceName) {
        this.noGstDeclarationAcceptanceName = noGstDeclarationAcceptanceName;
    }


}