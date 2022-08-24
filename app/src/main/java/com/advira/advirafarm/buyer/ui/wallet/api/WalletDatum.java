package com.advira.advirafarm.buyer.ui.wallet.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDatum {

    @SerializedName("data_type")
    @Expose
    private String dataType;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("order_details")
    @Expose
    private String orderDetails;
    @SerializedName("order_detail_id")
    @Expose
    private String orderDetailId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
