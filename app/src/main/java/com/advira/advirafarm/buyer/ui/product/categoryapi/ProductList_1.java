package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList_1 {
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("category_header_banner")
    @Expose
    private String categoryHeaderBanner;
    @SerializedName("products")
    @Expose
    private List<Product_home> products = null;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryHeaderBanner() {
        return categoryHeaderBanner;
    }

    public void setCategoryHeaderBanner(String categoryHeaderBanner) {
        this.categoryHeaderBanner = categoryHeaderBanner;
    }

    public List<Product_home> getProducts() {
        return products;
    }

    public void setProducts(List<Product_home> products) {
        this.products = products;
    }
}
