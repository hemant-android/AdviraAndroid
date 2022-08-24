package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("product_variety")
    @Expose
    private String productVariety;
    @SerializedName("product_units")
    @Expose
    private List<ProductUnit_> productUnits = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public List<ProductUnit_> getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(List<ProductUnit_> productUnits) {
        this.productUnits = productUnits;
    }

}