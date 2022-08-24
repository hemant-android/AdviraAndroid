package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.advira.advirafarm.buyer.ui.order.api.OrderDatum_v2;
import com.advira.advirafarm.buyer.ui.payment.api.MembershipDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyBasketResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_list")
    @Expose
    private List<Product_Basket> productList = null;

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

    public List<Product_Basket> getProductList() {
        return productList;
    }

    public void setProductList(List<Product_Basket> productList) {
        this.productList = productList;
    }
}
