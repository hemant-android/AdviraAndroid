package com.advira.advirafarm.buyer.ui.product.categoryapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;

import java.util.List;

public class HomepageResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dashboard_banners")
    @Expose
    private List<DashboardBannerList> dashboardBanners = null;
    @SerializedName("product_list")
    @Expose
    private List<ProductList_home> productList = null;

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

    public List<DashboardBannerList> getDashboardBanners() {
        return dashboardBanners;
    }

    public void setDashboardBanners(List<DashboardBannerList> dashboardBanners) {
        this.dashboardBanners = dashboardBanners;
    }

    public List<ProductList_home> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList_home> productList) {
        this.productList = productList;
    }
}
