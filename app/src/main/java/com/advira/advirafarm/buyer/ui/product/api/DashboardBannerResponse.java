package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardBannerResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dashboard_banners")
    @Expose
    private List<DashboardBanner> dashboardBanners = null;

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

    public List<DashboardBanner> getDashboardBanners() {
        return dashboardBanners;
    }

    public void setDashboardBanners(List<DashboardBanner> dashboardBanners) {
        this.dashboardBanners = dashboardBanners;
    }

}