package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuyNowResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cartSize")
    @Expose
    private Integer cartSize;
    @SerializedName("cart_date")
    @Expose
    private CartDate cartDate;
    @SerializedName("default_address")
    @Expose
    private List<DefaultAddress> defaultAddress = null;

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

    public Integer getCartSize() {
        return cartSize;
    }

    public void setCartSize(Integer cartSize) {
        this.cartSize = cartSize;
    }

    public CartDate getCartDate() {
        return cartDate;
    }

    public void setCartDate(CartDate cartDate) {
        this.cartDate = cartDate;
    }

    public List<DefaultAddress> getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(List<DefaultAddress> defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

}