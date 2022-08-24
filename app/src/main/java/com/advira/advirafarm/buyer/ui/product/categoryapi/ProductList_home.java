package com.advira.advirafarm.buyer.ui.product.categoryapi;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.DataConvertor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "productList_home",indices = @Index(value = {"category_id"}, unique = true))
public class ProductList_home {

    @PrimaryKey(autoGenerate = true)
    private int database_id;

    @ColumnInfo(name="category_id")
    @SerializedName("category_id")
    @Expose
    private String categoryId;

    @ColumnInfo(name="category_name")
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    @ColumnInfo(name="category_image")
    @SerializedName("category_image")
    @Expose
    private String categoryImage;

    @ColumnInfo(name="themecolordark")
    @SerializedName("themecolordark")
    @Expose
    private String themecolordark;

    @ColumnInfo(name="themecolorlight")
    @SerializedName("themecolorlight")
    @Expose
    private String themecolorlight;

    @ColumnInfo(name="category_header_banner")
    @SerializedName("category_header_banner")
    @Expose
    private String categoryHeaderBanner;

    //@Ignore
    @TypeConverters(DataConvertor.class)
    @ColumnInfo(name="banners")
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;

    //@Ignore
    @TypeConverters(DataConvertor.class)
    @ColumnInfo(name="products")
    @SerializedName("products")
    @Expose
    private List<Product_home> products = null;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getThemecolordark() {
        return themecolordark;
    }

    public void setThemecolordark(String themecolordark) {
        this.themecolordark = themecolordark;
    }

    public String getThemecolorlight() {
        return themecolorlight;
    }

    public void setThemecolorlight(String themecolorlight) {
        this.themecolorlight = themecolorlight;
    }

    public String getCategoryHeaderBanner() {
        return categoryHeaderBanner;
    }

    public void setCategoryHeaderBanner(String categoryHeaderBanner) {
        this.categoryHeaderBanner = categoryHeaderBanner;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<Product_home> getProducts() {
        return products;
    }

    public void setProducts(List<Product_home> products) {
        this.products = products;
    }

    public int getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(int database_id) {
        this.database_id = database_id;
    }
}

