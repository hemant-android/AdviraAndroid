package com.advira.advirafarm.buyer.ui.guest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.guest.adapter.CategoryListAdapterGuest;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletFragment;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.adapter.CategoryListAdapter;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryListResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_1;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductbycategoryListRequest;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductbycategoryListResponse;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryProductActivityGuest extends AppCompatActivity implements IConsts  {



    private RelativeLayout rl_back ,rl_search,rl_cart;
    public static TextView tv_cartcount;
    private RecyclerView recyclerView;
    private Context mContext;
    private TextView tv_pd_header2;
    String profilemode = "B2B";
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;
    private NestedScrollView nestedscrollview;
    ProgressBar progressbar;
    String categoryid = "";

    private List<Product_home> orderList;
    CategoryListAdapterGuest categoryListAdapterGuest;

    int page=1,limit=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categoryproducts);

        initUI();


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryProductActivityGuest.this.finish();

            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(CategoryProductActivityGuest.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(CategoryProductActivityGuest.this, Search_one.class);
                //i.setClass(CategoryProductActivityGuest.this, SearchActivity.class);
                startActivity(i);

            }
        });

    }



    private void initUI() {

        mContext = CategoryProductActivityGuest.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
        //tv_cartcount.setText(cartcount);
        recyclerView = findViewById(R.id.recyclerView);
        tv_pd_header2 = findViewById(R.id.tv_pd_header2);
        nestedscrollview=findViewById(R.id.nestedscrollview);
        progressbar=findViewById(R.id.progress_bar);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());//23-09-2021
        recyclerView.setNestedScrollingEnabled(false);



        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.getMenu().getItem(0).setCheckable(false);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        /*View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge,
                        mbottomNavigationMenuView, false);
        //((TextView) cart_badge.findViewById(R.id.notifications_badge)).setText();*/

        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);

        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        text.setText(cartcount);
        /*if(profilemode.equalsIgnoreCase("B2B")){
            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
            text.setText(cartcount); }
        else {
            String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
            text.setText(cartcount); }*/

        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        cart_badge.setVisibility(View.VISIBLE);
                        text.setText(cartcount);
                        if (profilemode.equalsIgnoreCase("B2C")) {

                            rl_cart.setVisibility(View.GONE);
                            tv_cartcount.setVisibility(View.GONE);
                        } else {
                            rl_cart.setVisibility(View.GONE);
                            tv_cartcount.setVisibility(View.GONE);
                        }


                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityGuestNav.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home_wallet:

                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.home_subscription:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MySubscription.class));
                            overridePendingTransition(0,0);
                        }
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });




        Bundle extras = getIntent().getExtras();
        categoryid = "";
        String category = "";

        if (extras != null) {
            categoryid = extras.getString("categoryid");
            category = extras.getString("category");

        }

        tv_pd_header2.setText(category);

        ProductListRequest(categoryid);

        /*nestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    page++;
                    progressbar.setVisibility(View.VISIBLE);
                    ProductListRequest(categoryid);
                }
            }
        });*/


    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }


    private void ProductListRequest(String categoryid) {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        ProductbycategoryListRequest productbycategoryListRequest=new ProductbycategoryListRequest();
        productbycategoryListRequest.setCategoryid(categoryid);
        Gson gson = new Gson();
        String vakk = gson.toJson(productbycategoryListRequest).toString();

        try{
            Call<ProductbycategoryListResponse> call=RetrofitUrlConnection.loadJSON(token).productbycategoryidb2c_v2(productbycategoryListRequest,page,limit);

            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

            /*if (profilemode.equalsIgnoreCase("B2C")) {

                call = RetrofitUrlConnection.loadJSON(token).productbycategoryidb2c(productbycategoryListRequest);

            } else {
                call = RetrofitUrlConnection.loadJSON(token).productbycategoryidb2b(productbycategoryListRequest);

            }*/
            call.enqueue(new Callback<ProductbycategoryListResponse>() {
                @Override
                public void onResponse(Call<ProductbycategoryListResponse> call, Response<ProductbycategoryListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        //progressbar.setVisibility(View.GONE);
                        orderList = new ArrayList<>();
                        categoryListAdapterGuest = new CategoryListAdapterGuest(mContext, orderList);

                        List<ProductList_1> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {


                            for (int i = 0; i < mListData.size(); i++) {
                                if (mListData.get(i).getCategoryId().equalsIgnoreCase(categoryid)) {
                                    List<Product_home> mListDataP = mListData.get(i).getProducts();

                                    orderList.addAll(mListDataP);
                                }
                            }

                        }


                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

                        // AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(mContext, 500);
                        //recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemViewCacheSize(20);
                        recyclerView.setDrawingCacheEnabled(true);
                        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                        //searchListAdapter.notifyItemRangeInserted(rangeStart, rangeEnd);
                        categoryListAdapterGuest.setHasStableIds(true);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(categoryListAdapterGuest);


                    } else {
                        Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }


                @Override
                public void onFailure(Call<ProductbycategoryListResponse> call, Throwable t) {

                }
            });
            /*call.enqueue(new Callback<ProductbycategoryListResponse>() {
                @Override
                public void onResponse(Call<ProductbycategoryListResponse> call, Response<ProductbycategoryListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();

                        orderList = new ArrayList<>();
                        categoryListAdapterGuest = new CategoryListAdapterGuest(mContext, orderList);

                        List<ProductList_home> mListData = response.body().getProductlist();

                        if (mListData != null && mListData.size() > 0) {


                            for (int i = 0; i < mListData.size(); i++) {
                                if (mListData.get(i).getCategoryId().equalsIgnoreCase(categoryid)) {
                                    List<Product_home> mListDataP = mListData.get(i).getProducts();

                                    orderList.addAll(mListDataP);
                                }
                            }

                        }


                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

                        // AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(mContext, 500);
                        //recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemViewCacheSize(20);
                        recyclerView.setDrawingCacheEnabled(true);
                        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                        //searchListAdapter.notifyItemRangeInserted(rangeStart, rangeEnd);
                        categoryListAdapterGuest.setHasStableIds(true);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(categoryListAdapterGuest);


                    } else {
                        Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<ProductbycategoryListResponse> call, Throwable t) {

                }
            });
*/

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /*private void ProductListRequest(String categoryid) {

        //Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        try {
            Call<CategoryListResponse> call;

            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

            if (profilemode.equalsIgnoreCase("B2C")) {

                 call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            } else {
                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2b();

            }


            call.enqueue(new Callback<CategoryListResponse>() {
                @Override
                public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();


                        orderList = new ArrayList<>();
                        categoryListAdapterGuest = new CategoryListAdapterGuest(mContext, orderList);

                        List<ProductList> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {


                            for (int i=0;i<mListData.size();i++)
                            {
                                if(mListData.get(i).getCategoryId().equalsIgnoreCase(categoryid))
                                {
                                    List<com.advira.advirafarm.buyer.ui.product.categoryapi.Product> mListDataP = mListData.get(i).getProducts();

                                    orderList.addAll(mListDataP);
                                }
                            }

                        }


                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

                       // AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(mContext, 500);
                        //recyclerView.setLayoutManager(layoutManager);

                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(categoryListAdapterGuest);



                    } else {
                        Utilities.dismissDialog();

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }

                }

                @Override
                public void onFailure(Call<CategoryListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
*/

    @Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }

}
