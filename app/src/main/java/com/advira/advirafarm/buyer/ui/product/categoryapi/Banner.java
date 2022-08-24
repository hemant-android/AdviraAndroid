package com.advira.advirafarm.buyer.ui.product.categoryapi;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "banner",indices = @Index(value = {"id"}, unique = true))
public class Banner {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    @SerializedName("id")
    @Expose
    private String id;

    @ColumnInfo(name="activity_name")
    @SerializedName("activity_name")
    @Expose
    private String activityName;

    @ColumnInfo(name="activity_id")
    @SerializedName("activity_id")
    @Expose
    private String activityId;

    @ColumnInfo(name="activity_header_name")
    @SerializedName("activity_header_name")
    @Expose
    private String activityHeaderName;

    @ColumnInfo(name="banner_url")
    @SerializedName("banner_url")
    @Expose
    private String bannerUrl;

    @ColumnInfo(name="param_1")
    @SerializedName("param_1")
    @Expose
    private String param1;

    @ColumnInfo(name="param_2")
    @SerializedName("param_2")
    @Expose
    private String param2;

    @ColumnInfo(name="param_3")
    @SerializedName("param_3")
    @Expose
    private String param3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityHeaderName() {
        return activityHeaderName;
    }

    public void setActivityHeaderName(String activityHeaderName) {
        this.activityHeaderName = activityHeaderName;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

}