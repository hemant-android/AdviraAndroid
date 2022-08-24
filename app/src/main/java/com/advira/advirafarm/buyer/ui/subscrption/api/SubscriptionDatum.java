package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscriptionDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
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
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("city")
    @Expose
    private Integer city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("subscription_payment_status")
    @Expose
    private String subscriptionPaymentStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("basketdata_count")
    @Expose
    private Integer basketdataCount;
    @SerializedName("basketData")
    @Expose
    private List<BasketDatum> basketData = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSubscriptionPaymentStatus() {
        return subscriptionPaymentStatus;
    }

    public void setSubscriptionPaymentStatus(String subscriptionPaymentStatus) {
        this.subscriptionPaymentStatus = subscriptionPaymentStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getBasketdataCount() {
        return basketdataCount;
    }

    public void setBasketdataCount(Integer basketdataCount) {
        this.basketdataCount = basketdataCount;
    }

    public List<BasketDatum> getBasketData() {
        return basketData;
    }

    public void setBasketData(List<BasketDatum> basketData) {
        this.basketData = basketData;
    }

}
