package com.advira.advirafarm.buyer.ui.payment.razorpay.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmiDatumNoCost {

    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("bank_code")
    @Expose
    private String bankCode;
    @SerializedName("emi_min_amount")
    @Expose
    private Integer emiMinAmount;
    @SerializedName("emi_interest_rate")
    @Expose
    private Integer emiInterestRate;
    @SerializedName("emi_duration")
    @Expose
    private Integer emiDuration;
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("nocost_emi")
    @Expose
    private Integer nocostEmi;
    @SerializedName("monthly_emi")
    @Expose
    private Integer monthlyEmi;
    @SerializedName("total_emi_paid")
    @Expose
    private Integer totalEmiPaid;
    @SerializedName("total_nocost_emi_paid")
    @Expose
    private Integer totalNocostEmiPaid;
    @SerializedName("discount")
    @Expose
    private Integer discount;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getEmiMinAmount() {
        return emiMinAmount;
    }

    public void setEmiMinAmount(Integer emiMinAmount) {
        this.emiMinAmount = emiMinAmount;
    }

    public Integer getEmiInterestRate() {
        return emiInterestRate;
    }

    public void setEmiInterestRate(Integer emiInterestRate) {
        this.emiInterestRate = emiInterestRate;
    }

    public Integer getEmiDuration() {
        return emiDuration;
    }

    public void setEmiDuration(Integer emiDuration) {
        this.emiDuration = emiDuration;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getNocostEmi() {
        return nocostEmi;
    }

    public void setNocostEmi(Integer nocostEmi) {
        this.nocostEmi = nocostEmi;
    }

    public Integer getMonthlyEmi() {
        return monthlyEmi;
    }

    public void setMonthlyEmi(Integer monthlyEmi) {
        this.monthlyEmi = monthlyEmi;
    }

    public Integer getTotalEmiPaid() {
        return totalEmiPaid;
    }

    public void setTotalEmiPaid(Integer totalEmiPaid) {
        this.totalEmiPaid = totalEmiPaid;
    }

    public Integer getTotalNocostEmiPaid() {
        return totalNocostEmiPaid;
    }

    public void setTotalNocostEmiPaid(Integer totalNocostEmiPaid) {
        this.totalNocostEmiPaid = totalNocostEmiPaid;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

}