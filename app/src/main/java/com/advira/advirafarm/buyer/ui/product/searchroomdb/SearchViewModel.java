package com.advira.advirafarm.buyer.ui.product.searchroomdb;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

import java.util.List;
public class SearchViewModel extends AndroidViewModel {

    private SearchRepository repository;
    private LiveData<List<ProductList>> getallSearchItem;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository=new SearchRepository(application);
        getallSearchItem=repository.getAllSearchItem();
    }
    // below line is to update data in our repository.
    public void insert(List<ProductList> model){
        repository.insert(model);
    }
    public void update(ProductList model) {
        repository.update(model);
    }
    public LiveData<List<ProductList>> getAllSearchItem() {

        return getallSearchItem;
    }

}