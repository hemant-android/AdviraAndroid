package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database.DashboardDatabase;

import java.util.List;

public class HomeRepository {

    private DashboardDatabase dashboardDatabase;
    private LiveData<List<ProductList_home>> getAllhomeProduct;

    public HomeRepository(Application application)
    {
        dashboardDatabase=DashboardDatabase.getInstance(application);
        getAllhomeProduct=dashboardDatabase.dashboardDao().getAllHomeProduct();
    }

    public LiveData<List<ProductList_home>> getAllHomeProduct()
    {
        return getAllhomeProduct;
    }
}
