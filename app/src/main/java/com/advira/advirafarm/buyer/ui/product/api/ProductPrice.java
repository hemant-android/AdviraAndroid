package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductPrice {

    @SerializedName("product_mrp")
    @Expose
    private String productMrp;
    @SerializedName("product_salesprice")
    @Expose
    private String productSalesprice;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("product_tax")
    @Expose
    private String productTax;
    @SerializedName("product_terms_and_condition")
    @Expose
    private String productTermsAndCondition;

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

    public String getProductTax() {
        return productTax;
    }

    public void setProductTax(String productTax) {
        this.productTax = productTax;
    }

    public String getProductTermsAndCondition() {
        return productTermsAndCondition;
    }

    public void setProductTermsAndCondition(String productTermsAndCondition) {
        this.productTermsAndCondition = productTermsAndCondition;
    }

}