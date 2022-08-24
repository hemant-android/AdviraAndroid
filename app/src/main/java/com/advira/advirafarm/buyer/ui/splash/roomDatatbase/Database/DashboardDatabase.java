package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Banner;
import com.advira.advirafarm.buyer.ui.product.categoryapi.DashboardBannerList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.DashboardDao;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.SearchDao;
@Database(entities = {DashboardBannerList.class, ProductList_home.class},version = 13)
public abstract class DashboardDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="DashboardBanner_list";
    public abstract DashboardDao dashboardDao();

    private static volatile DashboardDatabase INSTANCE=null;

    public static DashboardDatabase getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (DashboardDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE= Room.databaseBuilder(context,DashboardDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static RoomDatabase.Callback callback=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DashboardDatabase.PopulateAsynTask(INSTANCE);
        }
    };

    static class PopulateAsynTask extends AsyncTask<Void,Void,Void>
    {
        private DashboardDao dashboardDao;

        private PopulateAsynTask(DashboardDatabase dashboardDatabase){
            dashboardDao=dashboardDatabase.dashboardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dashboardDao.deleteAll();
            return null;
        }
    }

}
