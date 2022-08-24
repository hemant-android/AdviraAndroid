package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao.SearchDao;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Database.SearchDatabase;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.SearchRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private SearchDao moviesDao;
    private SearchRepository searchRepository;
    private LiveData<List<Product_search>> getAllItems;

    public final LiveData<PagedList<Product_search>> pagedListLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);

        moviesDao= SearchDatabase.getInstance(application).moviesDao();
        searchRepository=new SearchRepository(application);
        getAllItems=searchRepository.getAllItem();
        pagedListLiveData=new LivePagedListBuilder<>(
                moviesDao.getAllMovies(),10
        ).build();
    }

    public void insert(List<Product_search> list)
    {
        searchRepository.insert(list);
    }

    public LiveData<List<Product_search>> getAllItem()
    {
        return getAllItems;
    }


}
