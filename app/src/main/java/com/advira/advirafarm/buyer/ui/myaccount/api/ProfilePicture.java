package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePicture {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("profileimage_url")
    @Expose
    private String profileimageUrl;
    @SerializedName("profileimage_name")
    @Expose
    private String profileimageName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileimageUrl() {
        return profileimageUrl;
    }

    public void setProfileimageUrl(String profileimageUrl) {
        this.profileimageUrl = profileimageUrl;
    }

    public String getProfileimageName() {
        return profileimageName;
    }

    public void setProfileimageName(String profileimageName) {
        this.profileimageName = profileimageName;
    }

}