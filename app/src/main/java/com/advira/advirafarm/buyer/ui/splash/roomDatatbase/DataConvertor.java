package com.advira.advirafarm.buyer.ui.splash.roomDatatbase;

import androidx.room.TypeConverter;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Banner;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_home;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConvertor {

    @TypeConverter
    public String fromBannerList(List<Banner> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Banner>>() {}.getType();
        return gson.toJson(countryLang, type);
    }

    @TypeConverter
    public List<Banner> toBannerList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Banner>>() {}.getType();
        return gson.fromJson(countryLangString, type);
    }

    @TypeConverter
    public String fromHomeProductList(List<Product_home> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product_home>>() {}.getType();
        return gson.toJson(countryLang, type);
    }

    @TypeConverter
    public List<Product_home> toHomeProductList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product_home>>() {}.getType();
        return gson.fromJson(countryLangString, type);
    }

}
