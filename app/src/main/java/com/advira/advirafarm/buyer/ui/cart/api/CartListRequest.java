package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartListRequest {


    @SerializedName("user_cart_type")
    @Expose
    private String userCartType;

    public String getUserCartType() {
        return userCartType;
    }

    public void setUserCartType(String userCartType) {
        this.userCartType = userCartType;
    }

}