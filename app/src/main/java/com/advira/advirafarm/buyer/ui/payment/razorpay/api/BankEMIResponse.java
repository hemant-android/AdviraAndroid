package com.advira.advirafarm.buyer.ui.payment.razorpay.api;

import com.advira.advirafarm.buyer.ui.payment.api.PaymentData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankEMIResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("faq")
    @Expose
    private String faq;
    @SerializedName("terms_and_conditions")
    @Expose
    private String termsAndConditions;
    @SerializedName("disclamer")
    @Expose
    private String disclamer;
    @SerializedName("emi_data")
    @Expose
    private List<EmiDatum> emiData = null;

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

    public String getFaq() {
        return faq;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getDisclamer() {
        return disclamer;
    }

    public void setDisclamer(String disclamer) {
        this.disclamer = disclamer;
    }

    public List<EmiDatum> getEmiData() {
        return emiData;
    }

    public void setEmiData(List<EmiDatum> emiData) {
        this.emiData = emiData;
    }

}