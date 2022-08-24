package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchRequest {

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