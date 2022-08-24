package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductSearchResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_list")
    @Expose
    private List<Product_search> productList = null;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Product_search> getProductList() {
        return productList;
    }
}
