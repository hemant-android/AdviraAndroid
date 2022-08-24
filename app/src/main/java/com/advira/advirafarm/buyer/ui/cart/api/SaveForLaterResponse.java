package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveForLaterResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("saveforlater_data")
    @Expose
    private SaveforlaterData saveforlaterData;

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public SaveforlaterData getSaveforlaterData() {
        return saveforlaterData;
    }

    public void setSaveforlaterData(SaveforlaterData saveforlaterData) {
        this.saveforlaterData = saveforlaterData;
    }

}