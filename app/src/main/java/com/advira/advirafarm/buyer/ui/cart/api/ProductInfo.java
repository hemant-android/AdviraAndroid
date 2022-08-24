package com.advira.advirafarm.buyer.ui.cart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductInfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("is_rx")
    @Expose
    private String isRx;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("product_composition")
    @Expose
    private String productComposition;
    @SerializedName("product_moq")
    @Expose
    private String productMoq;
    @SerializedName("product_unit_type")
    @Expose
    private String productUnitType;
    @SerializedName("product_box_size")
    @Expose
    private String productBoxSize;
    @SerializedName("product_count_in_box")
    @Expose
    private String productCountInBox;
    @SerializedName("product_instock")
    @Expose
    private String productInstock;
    @SerializedName("product_thumbnail_name")
    @Expose
    private String productThumbnailName;
    @SerializedName("product_thumbnail_url")
    @Expose
    private String productThumbnailUrl;
    @SerializedName("product_price")
    @Expose
    private List<ProductPrice> productPrice = null;

    @SerializedName("product_moq_unit")
    @Expose
    private String productMoqUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsRx() {
        return isRx;
    }

    public void setIsRx(String isRx) {
        this.isRx = isRx;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductComposition() {
        return productComposition;
    }

    public void setProductComposition(String productComposition) {
        this.productComposition = productComposition;
    }

    public String getProductMoq() {
        return productMoq;
    }

    public void setProductMoq(String productMoq) {
        this.productMoq = productMoq;
    }

    public String getProductUnitType() {
        return productUnitType;
    }

    public void setProductUnitType(String productUnitType) {
        this.productUnitType = productUnitType;
    }

    public String getProductBoxSize() {
        return productBoxSize;
    }

    public void setProductBoxSize(String productBoxSize) {
        this.productBoxSize = productBoxSize;
    }

    public String getProductCountInBox() {
        return productCountInBox;
    }

    public void setProductCountInBox(String productCountInBox) {
        this.productCountInBox = productCountInBox;
    }

    public String getProductInstock() {
        return productInstock;
    }

    public void setProductInstock(String productInstock) {
        this.productInstock = productInstock;
    }

    public String getProductThumbnailName() {
        return productThumbnailName;
    }

    public void setProductThumbnailName(String productThumbnailName) {
        this.productThumbnailName = productThumbnailName;
    }

    public String getProductThumbnailUrl() {
        return productThumbnailUrl;
    }

    public void setProductThumbnailUrl(String productThumbnailUrl) {
        this.productThumbnailUrl = productThumbnailUrl;
    }

    public List<ProductPrice> getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(List<ProductPrice> productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductMoqUnit() {
        return productMoqUnit;
    }

    public void setProductMoqUnit(String productMoqUnit) {
        this.productMoqUnit = productMoqUnit;
    }

}