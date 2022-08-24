package com.advira.advirafarm.buyer.ui.product.searchroomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

import java.util.List;

@Dao
public interface SearchDao {

        @Query("SELECT * FROM search_table ") //ORDER BY productname ASC
        LiveData<List<ProductList>> getAllSearchItem();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(ProductList productList);

        @Insert
        void insertAll(ProductList productList);

        @Update
        void update(ProductList productList);
}
