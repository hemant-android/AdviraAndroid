package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.SearchDao;

@Database(entities = Product_search.class,version = 10)

public abstract class SearchDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="SearchItem_list";
    public abstract SearchDao moviesDao();


    private static volatile SearchDatabase INSTANCE=null;

    public static SearchDatabase getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (SearchDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE= Room.databaseBuilder(context,SearchDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsynTask(INSTANCE);
        }
    };

    static class PopulateAsynTask extends AsyncTask<Void,Void,Void>
    {
        private SearchDao moviesDao;

        private PopulateAsynTask(SearchDatabase moviesDatabase){
            moviesDao=moviesDatabase.moviesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            moviesDao.deleteAll();
            return null;
        }
    }
}

