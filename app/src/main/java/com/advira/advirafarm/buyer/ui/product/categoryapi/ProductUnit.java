package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductUnit {


    @SerializedName("product_units_id")
    @Expose
    private String productUnitsId;
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
    @SerializedName("product_tax")
    @Expose
    private String productTax;
    @SerializedName("product_mfg_date")
    @Expose
    private String productMfgDate;
    @SerializedName("product_packing_date")
    @Expose
    private String productPackingDate;
    @SerializedName("product_expiry_date")
    @Expose
    private String productExpiryDate;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_other_info")
    @Expose
    private String productOtherInfo;
    @SerializedName("product_terms_and_condition")
    @Expose
    private String productTermsAndCondition;
    @SerializedName("product_instock")
    @Expose
    private String productInstock;


    public String getProductUnitsId() {
        return productUnitsId;
    }

    public void setProductUnitsId(String productUnitsId) {
        this.productUnitsId = productUnitsId;
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
    public String getProductTax() {
        return productTax;
    }

    public void setProductTax(String productTax) {
        this.productTax = productTax;
    }

    public String getProductMfgDate() {
        return productMfgDate;
    }

    public void setProductMfgDate(String productMfgDate) {
        this.productMfgDate = productMfgDate;
    }

    public String getProductPackingDate() {
        return productPackingDate;
    }

    public void setProductPackingDate(String productPackingDate) {
        this.productPackingDate = productPackingDate;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductOtherInfo() {
        return productOtherInfo;
    }

    public void setProductOtherInfo(String productOtherInfo) {
        this.productOtherInfo = productOtherInfo;
    }

    public String getProductTermsAndCondition() {
        return productTermsAndCondition;
    }

    public void setProductTermsAndCondition(String productTermsAndCondition) {
        this.productTermsAndCondition = productTermsAndCondition;
    }

    public String getProductInstock() {
        return productInstock;
    }

    public void setProductInstock(String productInstock) {
        this.productInstock = productInstock;
    }

}