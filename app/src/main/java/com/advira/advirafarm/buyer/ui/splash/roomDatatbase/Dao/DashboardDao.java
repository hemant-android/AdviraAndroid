package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.advira.advirafarm.buyer.ui.product.categoryapi.DashboardBannerList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

import java.util.List;
@Dao
public interface DashboardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DashboardBannerList> dashboardBannerLists);

    @Query("SELECT * FROM dashboardBannerList")
    LiveData<List<DashboardBannerList>> getAllBanner();

    @Query("DELETE FROM dashboardBannerList")
    void deleteAll();

    @Query("SELECT * FROM productList_home")
    DataSource.Factory<Integer,ProductList_home> getAllhomeProd();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert_homeProduct(List<ProductList_home> productListHomes);

    @Query("SELECT * FROM productList_home")
    LiveData<List<ProductList_home>> getAllHomeProduct();
}
