package com.advira.advirafarm.buyer.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.dbHandler.DBHandler;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.product.adapter.PopulerSearchAdaptor;
import com.advira.advirafarm.buyer.ui.product.adapter.RecentSearchAdaptor;
import com.advira.advirafarm.buyer.ui.product.adapter.SearchListAdapter;
import com.advira.advirafarm.buyer.ui.product.adapter.SearchOneAdapter;
import com.advira.advirafarm.buyer.ui.product.api.PopulerSearch;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.product.searchroom.SearchDatabase;
import com.advira.advirafarm.buyer.ui.product.searchroom.SearchDatabaseClient;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.SearchRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel.SearchViewModel;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import java.util.ArrayList;
import java.util.List;

public class Search_one extends AppCompatActivity  implements IConsts {

    private RelativeLayout rl_back ,rl_cart,rl_recentsearch,rl_populeritem;
    public static TextView tv_cartcount,tv_noitems;
    private RecyclerView recyclerView,mvrecyclerView;
    public static RecyclerView rsrecyclerView;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private Context mContext;

    SearchListAdapter searchListAdapter;
    PopulerSearchAdaptor populerSearchAdaptor;
    RecentSearchAdaptor recentSearchAdaptor;

    private ArrayList<PopulerSearch> courseModalArrayList;
    private List<Product_search> orderList;
    private PagedList<Product_search> movies;
    private List<Product_search> filterorderList;

    public static SearchView searchView;
    public static TextView tv_footertotal,tv_footertotalitem;

    private SearchViewModel searchViewModel;

    private SearchRepository moviesRepository;
    private SearchOneAdapter moviesAdapter;

    private DBHandler dbHandler;
    private SearchDatabaseClient database;
    private SearchDatabase database_1;
    //private SearchViewModel searchViewModel;

    String populersearch[]={"Mango","Rice","Cucumber","Apple","Grapes","Onion","Ginger","Papaya","Orange","Coconut(Nariyal Pani)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchone);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        initUI();

        dbHandler = new DBHandler(Search_one.this);
        database=SearchDatabaseClient.getInstance(getApplicationContext());
        recycledViewPool=new RecyclerView.RecycledViewPool();

        courseModalArrayList = dbHandler.readList();
        // on below line passing our array lost to our adapter class.
        recentSearchAdaptor = new RecentSearchAdaptor(courseModalArrayList, Search_one.this);
        // setting layout manager for our recycler view.
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
        rsrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        // setting our adapter to recycler view.
        rsrecyclerView.setAdapter(recentSearchAdaptor);
        rsrecyclerView.setRecycledViewPool(recycledViewPool);
        //ProductListRequest();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                finish();
            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Search_one.this, CartActivity.class);
                startActivity(i);
            }
        });
    }

    private void initUI() {

        mContext = Search_one.this;
        rl_back = findViewById(R.id.rl_back);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_noitems=findViewById(R.id.tv_noitems);
        tv_noitems.setVisibility(View.GONE);
        rl_recentsearch=findViewById(R.id.rl_recentsearch);
        rl_populeritem=findViewById(R.id.rl_populeritem);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        moviesRepository=new SearchRepository(getApplication());
        orderList=new ArrayList<>();
        moviesAdapter=new SearchOneAdapter(this,orderList);

        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getAllItem().observe(this, new Observer<List<Product_search>>() {
            @Override
            public void onChanged(List<Product_search> actorList) {
                recyclerView.setAdapter(moviesAdapter);
                moviesAdapter.getAllActors(actorList);
                orderList=actorList;
                Log.d("main", "onChanged: "+actorList);
            }
        });
        rsrecyclerView = findViewById(R.id.rsrecyclerView);
        // getting our course array
        // list from db handler class.
        mvrecyclerView = findViewById(R.id.mvrecyclerView);
        mvrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
        populerSearchAdaptor=new PopulerSearchAdaptor(populersearch, this);
        mvrecyclerView.setAdapter(populerSearchAdaptor);
        mvrecyclerView.setRecycledViewPool(recycledViewPool);

        searchView = findViewById(R.id.searchView);
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dbHandler.addRecentSearch(query);
                rl_recentsearch.setVisibility(View.GONE);
                rl_populeritem.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // ProductListRequest();
                if(newText.length()>2) {
                    filterorderList = filter(orderList, newText);
                    moviesAdapter.setFilter(filterorderList);
                    rl_recentsearch.setVisibility(View.GONE);
                    rl_populeritem.setVisibility(View.GONE);
                }
                if(newText.length()>3) {
                    dbHandler.addRecentSearch(newText);
                }
                return true;
            }
        });

        tv_footertotal = findViewById(R.id.tv_footertotal);
        tv_footertotalitem = findViewById(R.id.tv_footertotalitem);
        //ProductListRequest();
        //saveData();
    }

    private List<Product_search> filter(List<Product_search> dataList, String newText) {
        newText=newText.toLowerCase();
        String text,text2;
        filterorderList=new ArrayList<>();
        for(Product_search dataFromDataList:dataList){
            text = dataFromDataList.getProductVariety().toLowerCase();
            text2 = dataFromDataList.getProductname().toLowerCase();
            if(text.contains(newText) || text2.contains(newText) ){
                filterorderList.add(dataFromDataList);
            }
        }
        if (filterorderList.size()>0)
        {
            recyclerView.setVisibility(View.VISIBLE);
            tv_noitems.setVisibility(View.GONE);
            rl_recentsearch.setVisibility(View.GONE);
            rl_populeritem.setVisibility(View.GONE);
        }
        else{
            tv_noitems.setVisibility(View.VISIBLE);
            rl_recentsearch.setVisibility(View.VISIBLE);
            rl_populeritem.setVisibility(View.VISIBLE);
        }
        return filterorderList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actionOnBackPress();
    }

    private void actionOnBackPress() {
        finish();
    }

  /*  private void ProductListRequest() {

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
                        List<Product_search> mListData = response.body().getProductList();
                        //new SaveTask().execute();
                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);
                        }
                        recyclerView.setAdapter(searchListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setRecycledViewPool(recycledViewPool);
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
    }*/

    @Override
    public void onRestart() {
        super.onRestart();
        //initUI();
    }
}