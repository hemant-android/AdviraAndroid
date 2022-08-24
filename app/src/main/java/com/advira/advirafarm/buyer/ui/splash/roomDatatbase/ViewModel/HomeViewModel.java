package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.DashboardDao;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.HomeRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {


    private LiveData<List<ProductList_home>> homeProductLiveData;
    private HomeRepository homeRepository;
    private DashboardDao dashboardDao;

    //public final LiveData<PagedList<ProductList_home>> pagedListLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository=new HomeRepository(application);
        homeProductLiveData=homeRepository.getAllHomeProduct();
        /*pagedListLiveData=new LivePagedListBuilder<>(
                dashboardDao.getAllhomeProd(),5
        ).build();*/
    }

    public LiveData<List<ProductList_home>> getAllHomeProduct()
    {
        return homeProductLiveData;
    }
}
