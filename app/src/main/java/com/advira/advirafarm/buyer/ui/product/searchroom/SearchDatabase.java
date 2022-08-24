package com.advira.advirafarm.buyer.ui.product.searchroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

@Database(entities = {ProductList.class}, version = 1)
public abstract class SearchDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
