package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.SearchDao;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database.SearchDatabase;

import java.util.List;

public class SearchRepository {

    private SearchDatabase moviesDatabase;
    LiveData<List<Product_search>> getAllItem;
    private int id;

    public SearchRepository(Application application)
    {
        moviesDatabase= SearchDatabase.getInstance(application);
        getAllItem=moviesDatabase.moviesDao().getAllItem();
    }

    public void insert(List<Product_search> moviesList) {
        new InsertAsyncTask(moviesDatabase).execute(moviesList);
    }

    public LiveData<List<Product_search>> getAllItem()
    {
        return getAllItem;
    }

    static class InsertAsyncTask extends AsyncTask<List<Product_search>,Void,Void>
    {
        private SearchDao moviesDao;

        private InsertAsyncTask(SearchDatabase moviesDatabase)
        {
            moviesDao=moviesDatabase.moviesDao();
        }

        @Override
        protected Void doInBackground(List<Product_search>... lists) {
            moviesDao.insert(lists[0]);
            return null;
        }
    }

}
