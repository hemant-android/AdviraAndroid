package com.advira.advirafarm.buyer.ui.wallet.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletOrderInitRequest {

    @SerializedName("amount")
    @Expose
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
