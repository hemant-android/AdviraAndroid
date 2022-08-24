package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyNowRequest {

    @SerializedName("product_unit_id")
    @Expose
    private String productUnitId;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("user_cart_type")
    @Expose
    private String userCartType;

    public String getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(String productUnitId) {
        this.productUnitId = productUnitId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getUserCartType() {
        return userCartType;
    }

    public void setUserCartType(String userCartType) {
        this.userCartType = userCartType;
    }

}