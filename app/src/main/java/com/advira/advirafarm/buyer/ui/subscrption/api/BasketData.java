package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasketData {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("base_prodct_name")
    @Expose
    private String baseProdctName;
    @SerializedName("product_image_url")
    @Expose
    private String productImageUrl;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getBaseProdctName() {
        return baseProdctName;
    }

    public void setBaseProdctName(String baseProdctName) {
        this.baseProdctName = baseProdctName;
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

}
