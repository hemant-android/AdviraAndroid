package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveLaterListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("listSize")
    @Expose
    private Integer listSize;
    @SerializedName("saveforlater_data")
    @Expose
    private List<SaveforlaterDatum> saveforlaterData = null;

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

    public Integer getListSize() {
        return listSize;
    }

    public void setListSize(Integer listSize) {
        this.listSize = listSize;
    }

    public List<SaveforlaterDatum> getSaveforlaterData() {
        return saveforlaterData;
    }

    public void setSaveforlaterData(List<SaveforlaterDatum> saveforlaterData) {
        this.saveforlaterData = saveforlaterData;
    }

}