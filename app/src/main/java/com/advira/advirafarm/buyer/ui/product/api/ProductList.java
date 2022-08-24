package com.advira.advirafarm.buyer.ui.product.api;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "search_table")
public class ProductList {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("product_variety")
    @Expose
    private String productVariety;

    @Ignore
    @SerializedName("product_units")
    @Expose
    private List<ProductUnit_> productUnits = null;

    public ProductList() {
    }

    public ProductList(@NonNull String id, String brandName, String productname, String productVariety, List<ProductUnit_> productUnits) {
        this.id = id;
        this.brandName = brandName;
        this.productname = productname;
        this.productVariety = productVariety;
        this.productUnits = productUnits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public List<ProductUnit_> getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(List<ProductUnit_> productUnits) {
        this.productUnits = productUnits;
    }

}