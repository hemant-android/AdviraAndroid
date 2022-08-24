package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DateSubscriptionDetailResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subscription_delivary_status")
    @Expose
    private String subscriptionDelivaryStatus;
    @SerializedName("basketCount")
    @Expose
    private Integer basketCount;
    @SerializedName("basketData")
    @Expose
    private List<BasketDatum> basketData = null;

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

    public String getSubscriptionDelivaryStatus() {
        return subscriptionDelivaryStatus;
    }

    public void setSubscriptionDelivaryStatus(String subscriptionDelivaryStatus) {
        this.subscriptionDelivaryStatus = subscriptionDelivaryStatus;
    }

    public Integer getBasketCount() {
        return basketCount;
    }

    public void setBasketCount(Integer basketCount) {
        this.basketCount = basketCount;
    }

    public List<BasketDatum> getBasketData() {
        return basketData;
    }

    public void setBasketData(List<BasketDatum> basketData) {
        this.basketData = basketData;
    }
}
