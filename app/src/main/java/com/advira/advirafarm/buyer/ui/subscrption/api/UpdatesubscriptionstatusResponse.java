package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdatesubscriptionstatusResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subscription_details")
    @Expose
    private List<SubscriptionDetail_1> subscriptionDetails = null;

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

    public List<SubscriptionDetail_1> getSubscriptionDetails() {
        return subscriptionDetails;
    }

    public void setSubscriptionDetails(List<SubscriptionDetail_1> subscriptionDetails) {
        this.subscriptionDetails = subscriptionDetails;
    }
}
