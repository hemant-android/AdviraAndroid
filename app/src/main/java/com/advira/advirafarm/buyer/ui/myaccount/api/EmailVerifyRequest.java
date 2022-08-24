package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailVerifyRequest {
    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}