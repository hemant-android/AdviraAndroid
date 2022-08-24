package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasketDatum {

    @SerializedName("basket_product_id")
    @Expose
    private Integer basketProductId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("subscription_id")
    @Expose
    private Integer subscriptionId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("prodct_name")
    @Expose
    private String prodctName;
    @SerializedName("product_image_url")
    @Expose
    private String productImageUrl;
    @SerializedName("p_units")
    @Expose
    private String pUnits;
    @SerializedName("p_unit_type")
    @Expose
    private String pUnitType;
    @SerializedName("p_price_per_pack")
    @Expose
    private String pPricePerPack;
    @SerializedName("p_count")
    @Expose
    private String pCount;
    @SerializedName("p_total_price")
    @Expose
    private String pTotalPrice;
    @SerializedName("p_tax")
    @Expose
    private String pTax;
    @SerializedName("p_discount")
    @Expose
    private String pDiscount;

    @SerializedName("sku_brand_name")
    @Expose
    private String skuBrandName;

    public String getSkuBrandName() {
        return skuBrandName;
    }

    public void setSkuBrandName(String skuBrandName) {
        this.skuBrandName = skuBrandName;
    }

    @SerializedName("product_schedule_date")
    @Expose
    private String productScheduleDate;
    @SerializedName("product_schedule_type")
    @Expose
    private String productScheduleType;
    @SerializedName("product_delivery_status")
    @Expose
    private String productDeliveryStatus;

    public Integer getBasketProductId() {
        return basketProductId;
    }

    public void setBasketProductId(Integer basketProductId) {
        this.basketProductId = basketProductId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProdctName() {
        return prodctName;
    }

    public void setProdctName(String prodctName) {
        this.prodctName = prodctName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getpPricePerPack() {
        return pPricePerPack;
    }

    public void setpPricePerPack(String pPricePerPack) {
        this.pPricePerPack = pPricePerPack;
    }

    public String getpCount() {
        return pCount;
    }

    public void setpCount(String pCount) {
        this.pCount = pCount;
    }

    public String getpTotalPrice() {
        return pTotalPrice;
    }

    public void setpTotalPrice(String pTotalPrice) {
        this.pTotalPrice = pTotalPrice;
    }

    public String getpTax() {
        return pTax;
    }

    public void setpTax(String pTax) {
        this.pTax = pTax;
    }

    public String getpDiscount() {
        return pDiscount;
    }

    public void setpDiscount(String pDiscount) {
        this.pDiscount = pDiscount;
    }

    public String getpUnits() {
        return pUnits;
    }

    public void setpUnits(String pUnits) {
        this.pUnits = pUnits;
    }

    public String getpUnitType() {
        return pUnitType;
    }

    public void setpUnitType(String pUnitType) {
        this.pUnitType = pUnitType;
    }

    public String getProductScheduleDate() {
        return productScheduleDate;
    }

    public void setProductScheduleDate(String productScheduleDate) {
        this.productScheduleDate = productScheduleDate;
    }

    public String getProductScheduleType() {
        return productScheduleType;
    }

    public void setProductScheduleType(String productScheduleType) {
        this.productScheduleType = productScheduleType;
    }

    public String getProductDeliveryStatus() {
        return productDeliveryStatus;
    }

    public void setProductDeliveryStatus(String productDeliveryStatus) {
        this.productDeliveryStatus = productDeliveryStatus;
    }
}
