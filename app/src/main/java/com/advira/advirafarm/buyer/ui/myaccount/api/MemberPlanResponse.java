package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberPlanResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_list")
    @Expose
    private Member_Plan productList;

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

    public Member_Plan getProductList() {
        return productList;
    }

    public void setProductList(Member_Plan productList) {
        this.productList = productList;
    }
}
