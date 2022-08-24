package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.advira.advirafarm.buyer.ui.product.categoryapi.DashboardBannerList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.DashboardDao;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database.DashboardDatabase;


import java.util.List;

public class DashBoardRepository {

    private DashboardDatabase dashboardDatabase;
    private LiveData<List<DashboardBannerList>> getAllBanner;

    private int id;

    public DashBoardRepository(Application application)
    {
        dashboardDatabase= DashboardDatabase.getInstance(application);
        getAllBanner=dashboardDatabase.dashboardDao().getAllBanner();
    }

    public void insert(List<DashboardBannerList> moviesList)
    {
        new InsertAsyncTask(dashboardDatabase).execute(moviesList);
    }

    public void insert_homeProduct(List<ProductList_home> productListHomes){
        new InsertHomeProductAsyncTask(dashboardDatabase).execute(productListHomes);
    }
    public LiveData<List<DashboardBannerList>> getAllBanner()
    {
        return getAllBanner;
    }

    static class InsertAsyncTask extends AsyncTask<List<DashboardBannerList>,Void,Void>
    {
        private DashboardDao dashboardDao;

        private InsertAsyncTask(DashboardDatabase moviesDatabase)
        {
            dashboardDao=moviesDatabase.dashboardDao();
        }

        @Override
        protected Void doInBackground(List<DashboardBannerList>... lists) {
            dashboardDao.insert(lists[0]);
            return null;
        }
    }

    private class InsertHomeProductAsyncTask extends AsyncTask<List<ProductList_home>,Void,Void>
    {
        private DashboardDao dashboardDao;

        public InsertHomeProductAsyncTask(DashboardDatabase moviesDatabase) {
            dashboardDao=moviesDatabase.dashboardDao();
        }

        @Override
        protected Void doInBackground(List<ProductList_home>... lists) {
            dashboardDao.insert_homeProduct(lists[0]);
            return null;
        }
    }
}
