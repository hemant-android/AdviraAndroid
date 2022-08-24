package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardBanner {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("banner_name")
    @Expose
    private String bannerName;
    @SerializedName("banner_details")
    @Expose
    private String bannerDetails;
    @SerializedName("banner_action_type")
    @Expose
    private String bannerActionType;
    @SerializedName("banner_action")
    @Expose
    private String bannerAction;
    @SerializedName("banner_url")
    @Expose
    private String bannerUrl;
    @SerializedName("banner_image_name")
    @Expose
    private String bannerImageName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerDetails() {
        return bannerDetails;
    }

    public void setBannerDetails(String bannerDetails) {
        this.bannerDetails = bannerDetails;
    }

    public String getBannerActionType() {
        return bannerActionType;
    }

    public void setBannerActionType(String bannerActionType) {
        this.bannerActionType = bannerActionType;
    }

    public String getBannerAction() {
        return bannerAction;
    }

    public void setBannerAction(String bannerAction) {
        this.bannerAction = bannerAction;
    }

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