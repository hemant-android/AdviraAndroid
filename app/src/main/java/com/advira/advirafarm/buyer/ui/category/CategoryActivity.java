package com.advira.advirafarm.buyer.ui.category;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressList;
import com.advira.advirafarm.buyer.ui.cart.adapter.CartAdapter;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.category.adapter.CategoryImageAdapter;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryListResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryResponse;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements IConsts {


    public static RecyclerView recyclerView;
    public static TextView tv_priceval, tv_gstval, tv_totalpaidval, tv_rv1, tv_footertotal, tv_footertotalitem, tv_pd_header2;
    public static RelativeLayout rl_content2, rl_noitems;
    CartAdapter cartListAdapter;
    private RelativeLayout rl_back, rl_search;
    CategoryImageAdapter categoryImageAdapter;
    private Context mContext;
    private RelativeLayout btn_buynow;
    private List<CategoryList>  orderList;
    private Button btn_shopnow;
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;
    private String profilemode="B2B";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                if (usertype.equalsIgnoreCase("guest")) {
                    Intent i = new Intent();
                    i.setClass(CategoryActivity.this, MainActivityGuestNav.class);
                    CategoryActivity.this.startActivity(i);
                } else {
                    Intent i = new Intent();
                    i.setClass(CategoryActivity.this, MainActivityNav.class);
                    CategoryActivity.this.startActivity(i);
                }

            }
        });


        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(CategoryActivity.this, SearchActivity.class);
                i.setClass(CategoryActivity.this, Search_one.class);
                startActivity(i);

            }
        });


        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                    String cartItemStock = SharedPrefUtil.getCartItemStock(mContext, SHARED_PREF_CartItemStock, "");

                    if (cartItemStock.equalsIgnoreCase("out of stock")) {

                        Singleton.getInstance().showErrorLongToast(mContext, "Some of the items are out of stock");
                    } else {

                        if (usertype.equalsIgnoreCase("guest")) {
                            Intent i = new Intent(CategoryActivity.this, LoginActivity.class);
                            CategoryActivity.this.startActivity(i);
                        } else {
                            Intent i = new Intent(CategoryActivity.this, ChooseAddressList.class);
                            CategoryActivity.this.startActivity(i);
                        }

                    }


                } else {
                    Utilities.showNetworkError(mContext);
                }


            }
        });

        btn_shopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {


                    String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                    if (usertype.equalsIgnoreCase("guest")) {
                        Intent i = new Intent(CategoryActivity.this, MainActivityGuestNav.class);
                        finishAffinity();
                        CategoryActivity.this.startActivity(i);
                    } else {
                        Intent i = new Intent(CategoryActivity.this, MainActivityNav.class);
                        finishAffinity();
                        CategoryActivity.this.startActivity(i);
                    }


                } else {
                    Utilities.showNetworkError(mContext);
                }


            }
        });


    }

    private void initUI() {

        mContext = CategoryActivity.this;
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        recyclerView = findViewById(R.id.recyclerView);
        tv_priceval = findViewById(R.id.tv_priceval);
        tv_gstval = findViewById(R.id.tv_gstval);
        tv_totalpaidval = findViewById(R.id.tv_totalpaidval);
        rl_content2 = findViewById(R.id.rl_content2);
        tv_rv1 = findViewById(R.id.tv_rv1);
        tv_rv1.setVisibility(View.GONE);
        tv_pd_header2=findViewById(R.id.tv_pd_header2);
        tv_pd_header2.setText("Category");
        tv_footertotal = findViewById(R.id.tv_footertotal);
        tv_footertotalitem = findViewById(R.id.tv_footertotalitem);
        rl_noitems = findViewById(R.id.rl_noitems);
        btn_shopnow = findViewById(R.id.btn_shopnow);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        btn_buynow = findViewById(R.id.btn_buynow);
        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.category);
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
       /* if(profilemode.equalsIgnoreCase("B2C")) {
            String cartcount = SharedPrefUtil.getCartCount(mContext, IConsts.SHARED_PREF_CARTCOUNT, "");
            text.setText(cartcount);
        }else{
            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, IConsts.SHARED_PREF_CARTCOUNTB2B, "");
            text.setText(cartcount);
        }*/
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String cartcount = SharedPrefUtil.getCartCount(mContext, IConsts.SHARED_PREF_CARTCOUNT, "");
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        //tv_cartcount.setVisibility(View.GONE);
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);
                        break;*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        text.setText(cartcount);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home_btmnav:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), MainActivityGuestNav.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                            overridePendingTransition(0,0);
                        }

                        return true;
                    case R.id.home_subscription:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MySubscription.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.home_wallet:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });



        ProductListRequest();


    }


    private void ProductListRequest() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try{
            Call<CategoryResponse> call=RetrofitUrlConnection.loadJSON(token).categorynotoken();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();


                        orderList = new ArrayList<>();

/* categoryAdapter = new CategoryAdapter(mContext, orderList);
                        categoryNameAdapter = new CategoryNameAdapter(mContext,orderList);*/

                        categoryImageAdapter = new CategoryImageAdapter(mContext, orderList);

                        List<CategoryList> mListData = response.body().getProductcategory();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }

/*
                        recyclerView1.setAdapter(categoryNameAdapter);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView1.setNestedScrollingEnabled(false);
*/



                        recyclerView.setAdapter(categoryImageAdapter);

                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                        //recyclerView3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setNestedScrollingEnabled(false);


                    } else {
                        Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                }
            });
        } catch (Exception e) {
            //toast.maketext(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

        //Utilities.showLoading(mContext);

        /*String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        // recyclerView2.setHasFixedSize(true);
        //recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));

        Call<CategoryListResponse> call;

        try {

            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

            if (profilemode.equalsIgnoreCase("B2C")) {

                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            } else {
                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2b();

            }

            // Call<CategoryListResponse> call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            call.enqueue(new Callback<CategoryListResponse>() {
                @Override
                public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();


                        orderList = new ArrayList<>();
                       *//* categoryAdapter = new CategoryAdapter(mContext, orderList);
                        categoryNameAdapter = new CategoryNameAdapter(mContext,orderList);*//*
                        categoryImageAdapter = new CategoryImageAdapter(mContext, orderList);

                        List<CategoryList> mListData = response.body().get();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }
*//*
                        recyclerView1.setAdapter(categoryNameAdapter);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView1.setNestedScrollingEnabled(false);
*//*


                        recyclerView.setAdapter(categoryImageAdapter);

                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                        //recyclerView3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setNestedScrollingEnabled(false);


                    } else {
                        Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
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
*/
    }


    /*@Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }*/

    @Override
    public void onBackPressed() {
        String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

        if (usertype.equalsIgnoreCase("guest")) {
            Intent i = new Intent();
            i.setClass(CategoryActivity.this, MainActivityGuestNav.class);
            CategoryActivity.this.startActivity(i);
        } else {
            Intent i = new Intent();
            i.setClass(CategoryActivity.this, MainActivityNav.class);
            CategoryActivity.this.startActivity(i);
        }
        
        //super.onBackPressed();
        //actionOnBackPress();
        //CategoryActivity.this.finish();

    }

    private void actionOnBackPress() {

        CategoryActivity.this.finish();
    }



}
