package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderListResponse_v2 {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("member_data")
    @Expose
    private List<MemberDatum> memberData;
    @SerializedName("order_data")
    @Expose
    private List<OrderDatum_v2> orderData = null;

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

    public List<OrderDatum_v2> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDatum_v2> orderData) {
        this.orderData = orderData;
    }

    public List<MemberDatum> getMemberData() {
        return memberData;
    }

    public void setMemberData(List<MemberDatum> memberData) {
        this.memberData = memberData;
    }
}
