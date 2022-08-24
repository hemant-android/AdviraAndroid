package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cartSize")
    @Expose
    private Integer cartSize;
    @SerializedName("total_cart_val")
    @Expose
    private String orderValue;
    @SerializedName("cart_data")
    @Expose
    private List<CartDatum> cartData = null;
    @SerializedName("default_address")
    @Expose
    private List<DefaultAddress> defaultAddress = null;
    @SerializedName("delivery_charges")
    @Expose
    private List<Deliverycharges> deliverycharges = null;

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

    /*public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }*/

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public List<CartDatum> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        this.cartData = cartData;
    }

    public List<DefaultAddress> getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(List<DefaultAddress> defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
    public List<Deliverycharges> getDeliverycharges() {
        return deliverycharges;
    }

    public void setDeliverycharges(List<Deliverycharges> deliverycharges) {
        this.deliverycharges = deliverycharges;
    }

}