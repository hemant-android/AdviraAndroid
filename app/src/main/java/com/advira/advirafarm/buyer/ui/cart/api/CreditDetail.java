package com.advira.advirafarm.buyer.ui.cart.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditDetail {


    @SerializedName("credit_limit")
    @Expose
    private String creditLimit;
    @SerializedName("credit_availed")
    @Expose
    private String creditAvailed;
    @SerializedName("credit_balance")
    @Expose
    private String creditBalance;

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCreditAvailed() {
        return creditAvailed;
    }

    public void setCreditAvailed(String creditAvailed) {
        this.creditAvailed = creditAvailed;
    }

    public String getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(String creditBalance) {
        this.creditBalance = creditBalance;
    }

}