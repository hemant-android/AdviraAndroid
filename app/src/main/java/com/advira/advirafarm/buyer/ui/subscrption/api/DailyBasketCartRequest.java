package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyBasketCartRequest {

    @SerializedName("product_sku_unit_price_id")
    @Expose
    private String productSkuUnitPriceId;
    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("date_from")
    @Expose
    private String datefrom;

    public String getProductSkuUnitPriceId() {
        return productSkuUnitPriceId;
    }

    public void setProductSkuUnitPriceId(String productSkuUnitPriceId) {
        this.productSkuUnitPriceId = productSkuUnitPriceId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

}
