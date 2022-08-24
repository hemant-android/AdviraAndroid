package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.advira.advirafarm.buyer.ui.product.api.ProductDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductbycategoryListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Product_list")
    @Expose
    private List<ProductList_1> productList = null;

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

    public List<ProductList_1> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList_1> productList) {
        this.productList = productList;
    }

    /*@SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Product_list")
    @Expose
    private List<ProductList> productlist;

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

    public List<ProductList> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<ProductList> productlist) {
        this.productlist = productlist;
    }*/
}
