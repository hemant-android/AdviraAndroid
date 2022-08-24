package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_category")
    @Expose
    private List<CategoryList> productcategory = null;

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

    public List<CategoryList> getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(List<CategoryList> productcategory) {
        this.productcategory = productcategory;
    }
}
