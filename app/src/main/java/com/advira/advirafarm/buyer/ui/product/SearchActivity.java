package com.advira.advirafarm.buyer.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.product.adapter.SearchListAdapter;
import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.api.ProductListResponse;
import com.advira.advirafarm.buyer.ui.product.api.SearchRequest;
import com.advira.advirafarm.buyer.ui.product.searchroomdb.RecentSearch;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity implements IConsts/*, SearchView.OnQueryTextListener */ {



    private RelativeLayout rl_back ,rl_cart;
    public static TextView tv_cartcount;
    private RecyclerView recyclerView;
    private Context mContext;
    SearchListAdapter searchListAdapter;
    private List<ProductList> orderList;
    private List<ProductList> filterorderList;
    //private RecyclerView.RecycledViewPool recycledViewPool;
    private SearchView searchView;
    public static TextView tv_footertotal,tv_footertotalitem;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecentSearch recentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
        //recycledViewPool=new RecyclerView.RecycledViewPool();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("RecentSearch");
        // initializing our object
        // class variable.
        recentSearch = new RecentSearch();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                SearchActivity.this.finish();
            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SearchActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }

    private void initUI() {

        mContext = SearchActivity.this;
        rl_back = findViewById(R.id.rl_back);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "0");
        tv_cartcount.setText(cartcount);

        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        if (useractive.equalsIgnoreCase("guest")) {
            rl_cart.setVisibility(View.INVISIBLE);
            tv_cartcount.setVisibility(View.INVISIBLE);
        }
        else {
            rl_cart.setVisibility(View.VISIBLE);
            tv_cartcount.setVisibility(View.VISIBLE);
        }

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVisibility(View.INVISIBLE);
        ProductListRequest();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addDatatoFirebase(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterorderList = filter(orderList, newText);
                searchListAdapter.setFilter(filterorderList);
                return true;
            }
        });
        tv_footertotal = findViewById(R.id.tv_footertotal);
        tv_footertotalitem = findViewById(R.id.tv_footertotalitem);
    }

    private void addDatatoFirebase(String name) {
        recentSearch.setSearchItemName(name);
        databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            databaseReference.setValue(recentSearch);
            Toast.makeText(SearchActivity.this, "data added", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(SearchActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
        }
        });
        }

   /* @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        filterorderList = filter(orderList, newText);
        searchListAdapter.setFilter(filterorderList);
        return true;
    }*/

    private List<ProductList> filter(List<ProductList> dataList, String newText) {
        newText=newText.toLowerCase();
        String text,text2;
        filterorderList=new ArrayList<>();
        for(ProductList dataFromDataList:dataList){
            text = dataFromDataList.getProductVariety().toLowerCase();
            text2 = dataFromDataList.getProductname().toLowerCase();
            if(text.contains(newText) || text2.contains(newText) ){
                filterorderList.add(dataFromDataList);
            }
        }
        if (filterorderList.size()>0)
        {
            recyclerView.setVisibility(View.VISIBLE);
        }
        return filterorderList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actionOnBackPress();
    }

    private void actionOnBackPress() {
        SearchActivity.this.finish();
    }

    private void ProductListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");

        SearchRequest searchRequest=new SearchRequest();
        searchRequest.setUserCartType(usermode);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        try {

            Call<ProductListResponse> call = RetrofitUrlConnection.loadJSON(token).products(searchRequest);

            call.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        orderList = new ArrayList<>();
                        searchListAdapter = new SearchListAdapter(mContext, orderList);
                        List<ProductList> mListData = response.body().getProductList();
                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);
                        }
                        recyclerView.setAdapter(searchListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        //recyclerView.setRecycledViewPool(recycledViewPool);
                    } else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //initUI();
    }
}