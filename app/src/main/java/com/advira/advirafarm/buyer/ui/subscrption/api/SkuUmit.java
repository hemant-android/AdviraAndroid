package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkuUmit {

    @SerializedName("product_sku_unit_price_id")
    @Expose
    private Integer productSkuUnitPriceId;
    @SerializedName("product_image_url")
    @Expose
    private String productImageUrl;
    @SerializedName("product_units")
    @Expose
    private String productUnits;
    @SerializedName("product_unit_type")
    @Expose
    private String productUnitType;
    @SerializedName("product_mrp")
    @Expose
    private String productMrp;
    @SerializedName("product_salesprice")
    @Expose
    private String productSalesprice;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("product_discount_label")
    @Expose
    private String productDiscountLabel;
    @SerializedName("is_default")
    @Expose
    private String isDefault;
    @SerializedName("product_instock")
    @Expose
    private String productInstock;

    public Integer getProductSkuUnitPriceId() {
        return productSkuUnitPriceId;
    }

    public void setProductSkuUnitPriceId(Integer productSkuUnitPriceId) {
        this.productSkuUnitPriceId = productSkuUnitPriceId;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(String productUnits) {
        this.productUnits = productUnits;
    }

    public String getProductUnitType() {
        return productUnitType;
    }

    public void setProductUnitType(String productUnitType) {
        this.productUnitType = productUnitType;
    }

    public String getProductMrp() {
        return productMrp;
    }

    public void setProductMrp(String productMrp) {
        this.productMrp = productMrp;
    }

    public String getProductSalesprice() {
        return productSalesprice;
    }

    public void setProductSalesprice(String productSalesprice) {
        this.productSalesprice = productSalesprice;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductDiscountLabel() {
        return productDiscountLabel;
    }

    public void setProductDiscountLabel(String productDiscountLabel) {
        this.productDiscountLabel = productDiscountLabel;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getProductInstock() {
        return productInstock;
    }

    public void setProductInstock(String productInstock) {
        this.productInstock = productInstock;
    }

    public SkuUmit(Integer productSkuUnitPriceId, String productUnits, String productUnitType) {
        this.productSkuUnitPriceId = productSkuUnitPriceId;
        this.productUnits = productUnits;
        this.productUnitType = productUnitType;
    }

    @Override
    public String toString() {
        return getProductUnits()+getProductUnitType();
    }

}
