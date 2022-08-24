package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDeleteRequest {

    @SerializedName("product_unit_id")
    @Expose
    private String productUnitId;

    @SerializedName("user_cart_type")
    @Expose
    private String userCartType;

    @SerializedName("deletefrom")
    @Expose
    private String deletefrom;


    public String getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(String productUnitId) {
        this.productUnitId = productUnitId;
    }

    public String getUserCartType() {
        return userCartType;
    }

    public void setUserCartType(String userCartType) {
        this.userCartType = userCartType;
    }

    public String getDeletefrom() {
        return deletefrom;
    }

    public void setDeletefrom(String deletefrom) {
        this.deletefrom = deletefrom;
    }
}