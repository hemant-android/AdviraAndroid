package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("billed_to")
    @Expose
    private String billedTo;
    @SerializedName("gst_no")
    @Expose
    private String gstNo;
    @SerializedName("fssai_no")
    @Expose
    private String fssaiNo;
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
    @SerializedName("refund_status")
    @Expose
    private String refundStatus;
    @SerializedName("cancel_reason_id")
    @Expose
    private String cancelReasonId;
    @SerializedName("cancel_reason_other")
    @Expose
    private String cancelReasonOther;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("max_cancellation_hours")
    @Expose
    private String maxCancellationHours;
    @SerializedName("invoice_url")
    @Expose
    private String invoiceUrl;
    @SerializedName("discount_coupon_name")
    @Expose
    private String discountCouponName;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("discount_details")
    @Expose
    private String discountDetails;
    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;
    @SerializedName("delivery_slot_date")
    @Expose
    private String deliverySlotDate;
    @SerializedName("delivery_slot_time")
    @Expose
    private String deliverySlotTime;
    @SerializedName("courier_info")
    @Expose
    private List<CourierInfo> courierInfo = null;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("order_product_list")
    @Expose
    private List<OrderProductList> orderProductList = null;


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

    public String getBilledTo() {
        return billedTo;
    }

    public void setBilledTo(String billedTo) {
        this.billedTo = billedTo;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getFssaiNo() {
        return fssaiNo;
    }

    public void setFssaiNo(String fssaiNo) {
        this.fssaiNo = fssaiNo;
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

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCancelReasonOther() {
        return cancelReasonOther;
    }

    public void setCancelReasonOther(String cancelReasonOther) {
        this.cancelReasonOther = cancelReasonOther;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMaxCancellationHours() {
        return maxCancellationHours;
    }

    public void setMaxCancellationHours(String maxCancellationHours) {
        this.maxCancellationHours = maxCancellationHours;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public String getDiscountCouponName() {
        return discountCouponName;
    }

    public void setDiscountCouponName(String discountCouponName) {
        this.discountCouponName = discountCouponName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(String discountDetails) {
        this.discountDetails = discountDetails;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public List<CourierInfo> getCourierInfo() {
        return courierInfo;
    }

    public void setCourierInfo(List<CourierInfo> courierInfo) {
        this.courierInfo = courierInfo;
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
    public String getDeliverySlotTime() {
        return deliverySlotTime;
    }

    public void setDeliverySlotTime(String deliverySlotTime) {
        this.deliverySlotTime = deliverySlotTime;
    }
    public String getDeliverySlotDate() {
        return deliverySlotDate;
    }

    public void setDeliverySlotDate(String deliverySlotDate) {
        this.deliverySlotDate = deliverySlotDate;
    }

}
