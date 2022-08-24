package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyBasketCartResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("basketCount")
    @Expose
    private Integer basketCount;
    @SerializedName("basketData")
    @Expose
    private BasketData basketData;

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

    public Integer getBasketCount() {
        return basketCount;
    }

    public void setBasketCount(Integer basketCount) {
        this.basketCount = basketCount;
    }

    public BasketData getBasketData() {
        return basketData;
    }

    public void setBasketData(BasketData basketData) {
        this.basketData = basketData;
    }


}
