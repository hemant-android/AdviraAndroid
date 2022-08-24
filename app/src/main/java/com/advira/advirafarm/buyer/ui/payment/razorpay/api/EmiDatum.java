package com.advira.advirafarm.buyer.ui.payment.razorpay.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmiDatum {
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
    @SerializedName("monthly_emi")
    @Expose
    private Integer monthlyEmi;
    @SerializedName("total_emi_paid")
    @Expose
    private Integer totalEmiPaid;
    @SerializedName("total_interest_paid")
    @Expose
    private Integer totalInterestPaid;

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

    public Integer getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public void setTotalInterestPaid(Integer totalInterestPaid) {
        this.totalInterestPaid = totalInterestPaid;
    }

}
