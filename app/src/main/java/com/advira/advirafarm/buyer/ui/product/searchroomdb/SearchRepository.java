package com.advira.advirafarm.buyer.ui.product.searchroomdb;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductSearchResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

import java.util.List;

public class SearchRepository {
    public SearchDao searchDao;
    public LiveData<List<ProductList>> getallSearchItem;
    private SearchRoomDB database;

    public SearchRepository(Application application) {
        database = SearchRoomDB.getInstance(application);
        searchDao = database.searchDao();
        getallSearchItem = searchDao.getAllSearchItem();
    }

    public void insert(List<ProductList> model){
        new InsertAsyncTask(searchDao).execute(model);

    }

    public void update(ProductList model) {
        new UpdateSearchAsyncTask(searchDao).execute(model);
    }


    public LiveData<List<ProductList>> getAllSearchItem() {
        return getallSearchItem;
    }

    private static class InsertAsyncTask extends AsyncTask<List<ProductList>,Void,Void>{
        private SearchDao searchDao;

        public InsertAsyncTask(SearchDao searchingDao)
        {
            this.searchDao=searchingDao;
        }
        @Override
        protected Void doInBackground(List<ProductList>... lists) {
            searchDao.insert((ProductList) lists[0]);
            return null;
        }
    }

    private static class UpdateSearchAsyncTask extends AsyncTask<ProductList, Void, Void> {
        private SearchDao searchDao;

        private UpdateSearchAsyncTask(SearchDao searchDao) {
            this.searchDao = searchDao;
        }

        @Override
        protected Void doInBackground(ProductList... models) {
            // below line is use to update
            // our modal in dao.
            searchDao.update(models[0]);
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void insert(final ProductList models) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                searchDao.insert(models);
                return null;
            }
        }.execute();

    }

}