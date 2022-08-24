package com.advira.advirafarm.buyer.ui.wallet.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MywalletpassbookResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("wallet_data")
    @Expose
    private List<WalletDatum> walletData = null;

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

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public List<WalletDatum> getWalletData() {
        return walletData;
    }

    public void setWalletData(List<WalletDatum> walletData) {
        this.walletData = walletData;
    }

}
