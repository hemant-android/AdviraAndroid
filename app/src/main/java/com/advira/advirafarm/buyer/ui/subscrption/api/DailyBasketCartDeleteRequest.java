package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyBasketCartDeleteRequest {

    @SerializedName("product_id")
    @Expose
    private String basketProductId;
    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;
    @SerializedName("subscription_date")
    @Expose
    private String subscriptionDate;

    public String getBasketProductId() {
        return basketProductId;
    }

    public void setBasketProductId(String basketProductId) {
        this.basketProductId = basketProductId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
