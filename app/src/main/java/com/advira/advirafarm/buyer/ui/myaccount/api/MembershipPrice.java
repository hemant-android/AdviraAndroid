package com.advira.advirafarm.buyer.ui.myaccount.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipPrice {
    @SerializedName("button_label")
    @Expose
    private String buttonLabel;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("months")
    @Expose
    private String months;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("is_default")
    @Expose
    private String isDefault;

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
