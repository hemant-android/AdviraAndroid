package com.advira.advirafarm.buyer.ui.product.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopulerSearch {

    @SerializedName("Productname")
    @Expose
    private String productname;

    public PopulerSearch(String productname) {
        this.productname = productname;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
