package com.advira.advirafarm.buyer.ui.product.searchroom;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

@SuppressLint("StaticFieldLeak")
public class SearchDatabaseClient {

    private String DB_NAME = "SearchDatabase";
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static SearchDatabaseClient mInstance;

    public SearchDatabase appDatabase;

    public SearchDatabaseClient(Context context) {
        this.context = context;
        appDatabase = Room.databaseBuilder(context, SearchDatabase.class, DB_NAME).build();
    }

    public static synchronized SearchDatabaseClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SearchDatabaseClient(context);
        }
        return mInstance;
    }

    public SearchDatabase getAppDatabase() {
        return appDatabase;
    }
}
