package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionDetail_1 {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subscription_id")
    @Expose
    private Integer subscriptionId;
    @SerializedName("subscription_type")
    @Expose
    private String subscriptionType;
    @SerializedName("subscription_mode")
    @Expose
    private String subscriptionMode;
    @SerializedName("subscription_start_date")
    @Expose
    private String subscriptionStartDate;
    @SerializedName("subscription_end_date")
    @Expose
    private String subscriptionEndDate;
    @SerializedName("subscription_payment_status")
    @Expose
    private String subscriptionPaymentStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionMode() {
        return subscriptionMode;
    }

    public void setSubscriptionMode(String subscriptionMode) {
        this.subscriptionMode = subscriptionMode;
    }

    public String getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(String subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public String getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getSubscriptionPaymentStatus() {
        return subscriptionPaymentStatus;
    }

    public void setSubscriptionPaymentStatus(String subscriptionPaymentStatus) {
        this.subscriptionPaymentStatus = subscriptionPaymentStatus;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
