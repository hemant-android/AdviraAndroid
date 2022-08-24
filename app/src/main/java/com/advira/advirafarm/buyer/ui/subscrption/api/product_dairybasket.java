package com.advira.advirafarm.buyer.ui.subscrption.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class product_dairybasket {

    @SerializedName("sku_id")
    @Expose
    private String skuId;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("product_variety")
    @Expose
    private String productVariety;
    @SerializedName("product_descreption")
    @Expose
    private String productDescreption;
    @SerializedName("sku_umit")
    @Expose
    private List<SkuUmit> skuUmit = null;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
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

    public String getProductDescreption() {
        return productDescreption;
    }

    public void setProductDescreption(String productDescreption) {
        this.productDescreption = productDescreption;
    }

    public List<SkuUmit> getSkuUmit() {
        return skuUmit;
    }

    public void setSkuUmit(List<SkuUmit> skuUmit) {
        this.skuUmit = skuUmit;
    }
}
