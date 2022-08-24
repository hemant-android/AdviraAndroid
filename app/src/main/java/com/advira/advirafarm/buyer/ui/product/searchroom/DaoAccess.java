package com.advira.advirafarm.buyer.ui.product.searchroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertAllData(ProductList task);

    //Select All Data
    @Query("select * from  search_table")
    List<ProductList> getAllData();

    @Insert
    void insert(ProductList task);

    @Delete
    void delete(ProductList task);

    @Update
    void update(ProductList task);
}
