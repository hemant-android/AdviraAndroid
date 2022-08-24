package com.advira.advirafarm.buyer.ui.product.api;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Banner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarProduct {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("themecolordark")
    @Expose
    private String themecolordark;
    @SerializedName("themecolorlight")
    @Expose
    private String themecolorlight;
    @SerializedName("category_header_banner")
    @Expose
    private String categoryHeaderBanner;
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

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

    public String getThemecolordark() {
        return themecolordark;
    }

    public void setThemecolordark(String themecolordark) {
        this.themecolordark = themecolordark;
    }

    public String getThemecolorlight() {
        return themecolorlight;
    }

    public void setThemecolorlight(String themecolorlight) {
        this.themecolorlight = themecolorlight;
    }

    public String getCategoryHeaderBanner() {
        return categoryHeaderBanner;
    }

    public void setCategoryHeaderBanner(String categoryHeaderBanner) {
        this.categoryHeaderBanner = categoryHeaderBanner;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}