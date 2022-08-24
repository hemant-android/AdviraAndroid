package com.advira.advirafarm.buyer.ui.product.categoryapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductbycategoryListRequest {
    @SerializedName("category_id")
    @Expose
    private String categoryid;

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
}
