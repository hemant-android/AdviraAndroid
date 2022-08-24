package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.advira.advirafarm.buyer.ui.product.api.DashboardBanner;
import com.advira.advirafarm.buyer.ui.product.categoryapi.DashboardBannerList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.DashboardDao;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database.DashboardDatabase;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.DashBoardRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.SearchRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardDao dashboardDao;
    private DashBoardRepository dashBoardRepository;
    private LiveData<List<DashboardBannerList>> getAllBanners;

    //public final LiveData<PagedList<DashboardBannerList>> pagedListLiveData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        dashboardDao= DashboardDatabase.getInstance(application).dashboardDao();
        dashBoardRepository=new DashBoardRepository(application);
        getAllBanners=dashBoardRepository.getAllBanner();
        /*pagedListLiveData=new LivePagedListBuilder<>(
                dashboardDao.getAlldBanner(),10
        ).build();*/
    }
    public void insert(List<DashboardBannerList> list)
    {
        dashBoardRepository.insert(list);
    }

    public LiveData<List<DashboardBannerList>> getAllBanner()
    {
        return getAllBanners;
    }

}
