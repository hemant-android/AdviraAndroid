package com.advira.advirafarm.buyer.ui.subscrption;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.DailyBasketAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.DairyBasketAdator;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.ProductListAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.Product_Basket;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyBasketProductList extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_save;
    public static TextView tv_cartcount;

    public static RecyclerView recyclerView;
    private Context mContext;
    ProductListAdaptor productListAdaptor;
    DailyBasketAdaptor dairyBasketAdator;
    public static List<Product_Basket> orderList;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;
    public static String SelectedDate;
    public static String subscription_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_basket_product_list);

        initUI();
        //ProductListRequest();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(DailyBasketProductList.this, SearchActivity.class);
                i.setClass(DailyBasketProductList.this, Search_one.class);
                startActivity(i);

            }
        });

        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(DailyBasketProductList.this, MySubscription.class);
                startActivity(i);

            }
        });


    }

    private void initUI() {

        mContext = DailyBasketProductList.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_save=findViewById(R.id.rl_save);
        //tv_cartcount = findViewById(R.id.tv_cartcount);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        //tv_cartcount.setText(cartcount);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            subscription_id=extras.getString("subscription_id");
            SelectedDate=extras.getString("SelectedDate");

            }
        if(SelectedDate.equalsIgnoreCase("")){
            Calendar calendar = Calendar.getInstance();
            // get a date to represent "today"
            Date today = calendar.getTime();
            String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
            SelectedDate=defaultDate;
        }

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.tokri);
        bottomnavview.setSelectedItemId(R.id.home_subscription);
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
        //String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "0");
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_subscription:
                        startActivity(new Intent(getApplicationContext(), MySubscription.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_wallet:
                        startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return true;
            }
        });

        ProductListRequest();
    }

    private void ProductListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<DailyBasketResponse> call= RetrofitUrlConnection.loadJSON(token).dailybasketproducts();

            call.enqueue(new Callback<DailyBasketResponse>() {
                @Override
                public void onResponse(Call<DailyBasketResponse> call, Response<DailyBasketResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Log.e(TAG, "onResponse: Search"+jjj );
                        Utilities.dismissDialog();

                        /*if(SelectedDate.equalsIgnoreCase("")){
                            orderList = new ArrayList<>();
                            dairyBasketAdator = new DailyBasketAdaptor(mContext, orderList);

                            List<Product_Basket> mListData = response.body().getProductList();

                            if (mListData != null && mListData.size() > 0) {
                                orderList.addAll(mListData);
                            *//*rl_noitems.setVisibility(View.GONE);
                            ma_headerq.setVisibility(View.GONE);*//*
                            } else {
                                try {
                                *//*rl_noitems.setVisibility(View.VISIBLE);
                                ma_headerq.setVisibility(View.GONE);*//*
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            //orderAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(dairyBasketAdator);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setNestedScrollingEnabled(false);
                        }else {*/

                            orderList = new ArrayList<>();
                            productListAdaptor = new ProductListAdaptor(mContext, orderList);

                            List<Product_Basket> mListData = response.body().getProductList();

                            if (mListData != null && mListData.size() > 0) {
                                orderList.addAll(mListData);
                            } else {
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            recyclerView.setAdapter(productListAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setNestedScrollingEnabled(false);
                        //}
                        Utilities.dismissDialog();

                    } else {
                        //Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });

        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
}