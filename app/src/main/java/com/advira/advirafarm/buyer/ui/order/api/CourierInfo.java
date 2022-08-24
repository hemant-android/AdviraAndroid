package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourierInfo {

    @SerializedName("tracking_code")
    @Expose
    private String trackingCode;
    @SerializedName("courier_company_name")
    @Expose
    private String courierCompanyName;
    @SerializedName("tracking_url")
    @Expose
    private String trackingUrl;
    @SerializedName("courier_date")
    @Expose
    private String courierDate;

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getCourierCompanyName() {
        return courierCompanyName;
    }

    public void setCourierCompanyName(String courierCompanyName) {
        this.courierCompanyName = courierCompanyName;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getCourierDate() {
        return courierDate;
    }

    public void setCourierDate(String courierDate) {
        this.courierDate = courierDate;
    }

}