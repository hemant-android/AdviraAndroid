package com.advira.advirafarm.buyer.ui.product;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfileMode;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.product.adapter.SearchListAdapter;
import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.api.SearchRequest;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductSearchResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.product.searchroomdb.SearchRepository;
import com.advira.advirafarm.buyer.ui.product.searchroomdb.SearchRoomDB;
import com.advira.advirafarm.buyer.ui.product.searchroomdb.SearchViewModel;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSearchActivity extends AppCompatActivity {

    Context mContext;
    private SearchListAdapter searchListAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.RecycledViewPool recycledViewPool;


    private SearchRepository repository;
    private SearchViewModel searchViewModel;
    private List<Product_search> getallitem;
    private SearchRoomDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsearch);
        mContext=NewSearchActivity.this;
        repository=new SearchRepository(getApplication());
        getallitem=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        recycledViewPool=new RecyclerView.RecycledViewPool();
        recyclerView.setLayoutManager(new LinearLayoutManager(NewSearchActivity.this));
        recyclerView.setHasFixedSize(true);

        database=SearchRoomDB.getInstance(mContext);

        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        //searchListAdapter=new SearchListAdapter(this, getallitem);
        makeRequest();
        searchViewModel.getAllSearchItem().observe(this, new Observer<List<ProductList>>() {
            @Override
            public void onChanged(List<ProductList> model) {
                recyclerView.setAdapter(searchListAdapter);
                //searchListAdapter.getAllDatas(model);
                Log.d("main", "onChanged: "+model);
            }
        });
    }

    private void makeRequest() {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        SearchRequest searchRequest=new SearchRequest();
        searchRequest.setUserCartType(usermode);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Gson gson = new Gson();
        //String vakk = gson.toJson(searchRequest).toString();
        //String jjj=vakk;
        try {
            Call<ProductSearchResponse> call= RetrofitUrlConnection.loadJSON(token).productsearch();
            call.enqueue(new Callback<ProductSearchResponse>() {
                @Override
                public void onResponse(Call<ProductSearchResponse> call, Response<ProductSearchResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        repository.insert((List<ProductList>) response.body());
                        getallitem = new ArrayList<>();
                        //searchListAdapter = new SearchListAdapter(mContext, getallitem);
                        List<Product_search> mListData = response.body().getProductList();
                        if (mListData != null && mListData.size() > 0) {
                            getallitem.addAll(mListData);
                        }
                        recyclerView.setAdapter(searchListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setRecycledViewPool(recycledViewPool);
                        Utilities.dismissDialog();
                    } else {
                        Utilities.dismissDialog();
                    }
                }
                @Override
                public void onFailure(Call<ProductSearchResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}