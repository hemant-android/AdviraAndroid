package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePictureRequest {

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}