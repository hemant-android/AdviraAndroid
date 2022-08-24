package com.advira.advirafarm.buyer.ui.product.categoryapi;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Singleton;

@Entity(tableName = "dashboardBannerList")
@Singleton
public class DashboardBannerList {

    @PrimaryKey(autoGenerate = true)
    private int database_id;

    @ColumnInfo(name="banner_url")
    @SerializedName("banner_url")
    @Expose
    private String bannerUrl;

    @ColumnInfo(name="banner_image_name")
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

    public int getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(int database_id) {
        this.database_id = database_id;
    }
}
