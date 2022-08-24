package com.advira.advirafarm.buyer.ui.product.categoryapi;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.inject.Singleton;

@Entity(tableName = "product_search",indices = @Index(value = {"sku_id"}, unique = true))
public class Product_search {

    @PrimaryKey(autoGenerate = true)
    private int database_id;

    @ColumnInfo(name="sku_id")
    @SerializedName("sku_id")
    @Expose
    private String skuId;

    @ColumnInfo(name="productname")
    @SerializedName("productname")
    @Expose
    private String productname;

    @ColumnInfo(name="product_variety")
    @SerializedName("product_variety")
    @Expose
    private String productVariety;

    @ColumnInfo(name="product_units_id")
    @SerializedName("product_units_id")
    @Expose
    private String productUnitsId;

    @ColumnInfo(name="product_units")
    @SerializedName("product_units")
    @Expose
    private String productUnits;

    @ColumnInfo(name="product_unit_type")
    @SerializedName("product_unit_type")
    @Expose
    private String productUnitType;

    @ColumnInfo(name="product_mrp")
    @SerializedName("product_mrp")
    @Expose
    private String productMrp;

    @ColumnInfo(name="product_salesprice")
    @SerializedName("product_salesprice")
    @Expose
    private String productSalesprice;

    @ColumnInfo(name="product_discount")
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;

    @ColumnInfo(name="product_discount_label")
    @SerializedName("product_discount_label")
    @Expose
    private String productDiscountLabel;

    @ColumnInfo(name="product_image")
    @SerializedName("product_image")
    @Expose
    private String productImage;

    @ColumnInfo(name="product_instock")
    @SerializedName("product_instock")
    @Expose
    private String productInstock;


    public Product_search(@NonNull String skuId, String productname, String productVariety, String productUnitsId, String productUnits, String productUnitType, String productMrp, String productSalesprice, String productDiscount, String productDiscountLabel, String productImage, String productInstock) {
        this.skuId = skuId;
        this.productname = productname;
        this.productVariety = productVariety;
        this.productUnitsId = productUnitsId;
        this.productUnits = productUnits;
        this.productUnitType = productUnitType;
        this.productMrp = productMrp;
        this.productSalesprice = productSalesprice;
        this.productDiscount = productDiscount;
        this.productDiscountLabel = productDiscountLabel;
        this.productImage = productImage;
        this.productInstock = productInstock;
    }

    public Product_search(List<ProductList> productList) {
    }

    @NonNull
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(@NonNull String skuId) {
        this.skuId = skuId;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductInstock() {
        return productInstock;
    }

    public void setProductInstock(String productInstock) {
        this.productInstock = productInstock;
    }

    public int getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(int database_id) {
        this.database_id = database_id;
    }
}
