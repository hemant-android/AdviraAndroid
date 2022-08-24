package com.advira.advirafarm.buyer.ui.order.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderProductList {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("cart_product_id")
    @Expose
    private String cartProductId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_unit_id")
    @Expose
    private String productUnitId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_units")
    @Expose
    private String productUnits;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_instock")
    @Expose
    private String productInstock;
    @SerializedName("product_tax")
    @Expose
    private String productTax;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("product_mrp")
    @Expose
    private String productMrp;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("product_mrp_discount")
    @Expose
    private String productMrpDiscount;
    @SerializedName("product_mrp_discount_label")
    @Expose
    private String productMrpDiscountLabel;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(String cartProductId) {
        this.cartProductId = cartProductId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(String productUnitId) {
        this.productUnitId = productUnitId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(String productUnits) {
        this.productUnits = productUnits;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductInstock() {
        return productInstock;
    }

    public void setProductInstock(String productInstock) {
        this.productInstock = productInstock;
    }

    public String getProductTax() {
        return productTax;
    }

    public void setProductTax(String productTax) {
        this.productTax = productTax;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductMrp() {
        return productMrp;
    }

    public void setProductMrp(String productMrp) {
        this.productMrp = productMrp;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductMrpDiscount() {
        return productMrpDiscount;
    }

    public void setProductMrpDiscount(String productMrpDiscount) {
        this.productMrpDiscount = productMrpDiscount;
    }

    public String getProductMrpDiscountLabel() {
        return productMrpDiscountLabel;
    }

    public void setProductMrpDiscountLabel(String productMrpDiscountLabel) {
        this.productMrpDiscountLabel = productMrpDiscountLabel;
    }

}