package com.advira.advirafarm.buyer.ui.product.searchroomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;


import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.product.searchroomdb.SearchDao;

@Database(entities = {ProductList.class}, version = 1,exportSchema = false)
public abstract class SearchRoomDB extends RoomDatabase {
    private static SearchRoomDB instance;
    private static final String DATABASE_NAME = "search_database";
    public abstract SearchDao searchDao();

    public static synchronized SearchRoomDB getInstance(Context context) {
        if (instance == null) {
            instance =Room.databaseBuilder(context.getApplicationContext(),
                            SearchRoomDB.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SearchDao searchDao;
        PopulateDbAsyncTask(SearchRoomDB searchRoomDB) {
            searchDao = searchRoomDB.searchDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
