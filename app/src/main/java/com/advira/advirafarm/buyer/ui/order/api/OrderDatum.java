package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;
    @SerializedName("grand_total_amount")
    @Expose
    private String grandTotalAmount;
    @SerializedName("totalcart_count")
    @Expose
    private String totalcartCount;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("order_product_list")
    @Expose
    private List<OrderProductList> orderProductList = null;

    @SerializedName("payment")
    @Expose
    private List<Payment> payment = null;


    @SerializedName("max_cancellation_hours")
    @Expose
    private String maxCancellationHours;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public void setGrandTotalAmount(String grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    public String getTotalcartCount() {
        return totalcartCount;
    }

    public void setTotalcartCount(String totalcartCount) {
        this.totalcartCount = totalcartCount;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderProductList> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProductList> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }



    public String getMaxCancellationHours() {
        return maxCancellationHours;
    }

    public void setMaxCancellationHours(String maxCancellationHours) {
        this.maxCancellationHours = maxCancellationHours;
    }

}