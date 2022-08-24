package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeDashboardBanner {
    @SerializedName("banner_url")
    @Expose
    private String bannerUrl;
    @SerializedName("banner_image_name")
    @Expose
    private String bannerImageName;

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerImageName() {
        return bannerImageName;
    }

    public void setBannerImageName(String bannerImageName) {
        this.bannerImageName = bannerImageName;
    }
}
