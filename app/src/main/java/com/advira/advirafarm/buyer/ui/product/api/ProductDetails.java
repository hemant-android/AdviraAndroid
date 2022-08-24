package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_UID")
    @Expose
    private String productUID;
    @SerializedName("product_category_name")
    @Expose
    private String productCategoryName;
    @SerializedName("product_category_id")
    @Expose
    private Integer productCategoryId;
    @SerializedName("product_brand_id")
    @Expose
    private String productBrandId;
    @SerializedName("product_brand_name")
    @Expose
    private String productBrandName;
    @SerializedName("product_brand_logo")
    @Expose
    private String productBrandLogo;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_variety")
    @Expose
    private String productVariety;
    @SerializedName("product_shelf_life")
    @Expose
    private String productShelfLife;
    @SerializedName("product_country_of_origin")
    @Expose
    private String productCountryOfOrigin;
    @SerializedName("product_descreption")
    @Expose
    private String productDescreption;
    @SerializedName("product_quality_specification")
    @Expose
    private String productQualitySpecification;
    @SerializedName("product_other_details")
    @Expose
    private String productOtherDetails;
    @SerializedName("product_specification")
    @Expose
    private String productSpecification;
    @SerializedName("product_disclaimer")
    @Expose
    private String productDisclaimer;
    @SerializedName("is_deal")
    @Expose
    private String isDeal;
    @SerializedName("product_unit")
    @Expose
    private List<ProductUnit> productUnit = null;
    @SerializedName("similar_products")
    @Expose
    private List<SimilarProduct> similarProducts = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductUID() {
        return productUID;
    }

    public void setProductUID(String productUID) {
        this.productUID = productUID;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(String productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getProductBrandLogo() {
        return productBrandLogo;
    }

    public void setProductBrandLogo(String productBrandLogo) {
        this.productBrandLogo = productBrandLogo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public String getProductShelfLife() {
        return productShelfLife;
    }

    public void setProductShelfLife(String productShelfLife) {
        this.productShelfLife = productShelfLife;
    }

    public String getProductCountryOfOrigin() {
        return productCountryOfOrigin;
    }

    public void setProductCountryOfOrigin(String productCountryOfOrigin) {
        this.productCountryOfOrigin = productCountryOfOrigin;
    }

    public String getProductDescreption() {
        return productDescreption;
    }

    public void setProductDescreption(String productDescreption) {
        this.productDescreption = productDescreption;
    }

    public String getProductQualitySpecification() {
        return productQualitySpecification;
    }

    public void setProductQualitySpecification(String productQualitySpecification) {
        this.productQualitySpecification = productQualitySpecification;
    }

    public String getProductOtherDetails() {
        return productOtherDetails;
    }

    public void setProductOtherDetails(String productOtherDetails) {
        this.productOtherDetails = productOtherDetails;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getProductDisclaimer() {
        return productDisclaimer;
    }

    public void setProductDisclaimer(String productDisclaimer) {
        this.productDisclaimer = productDisclaimer;
    }

    public List<ProductUnit> getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(List<ProductUnit> productUnit) {
        this.productUnit = productUnit;
    }

    public String getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(String isDeal) {
        this.isDeal = isDeal;
    }

    public List<SimilarProduct> getSimilarProducts() {
        return similarProducts;
    }

    public void setSimilarProducts(List<SimilarProduct> similarProducts) {
        this.similarProducts = similarProducts;
    }

}