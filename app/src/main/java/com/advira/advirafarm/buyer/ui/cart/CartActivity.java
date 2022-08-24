package com.advira.advirafarm.buyer.ui.cart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.adapter.CartAdapter;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.cart.api.Deliverycharges;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
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

import static android.content.ContentValues.TAG;

public class CartActivity extends AppCompatActivity implements IConsts {


    public static RecyclerView recyclerView;
    public static TextView tv_priceval, tv_gstval, tv_totalpaidval, tv_rv1, tv_footertotal, tv_footertotalitem,tv_deliveryval;
    public static RelativeLayout rl_content2, rl_noitems;
    CartAdapter cartListAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private RelativeLayout rl_back, rl_search;
    private Context mContext;
    private RelativeLayout btn_buynow;
    private List<CartDatum> cartList;
    private Button btn_shopnow;
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;
    String profilemode="";
    public static String carttotal="";
    String usertype="";
    public static double ordervalue=0;
    String isDeal="";
    String membershipName="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartthome);

        initUI();
        recycledViewPool=new RecyclerView.RecycledViewPool();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //actiononbackpressed();

                CartActivity.this.finish();
                // MainActivityNav.bottomnavview.setSelectedItemId(R.id.home_btmnav);
            }
        });


        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(CartActivity.this, SearchActivity.class);
                i.setClass(CartActivity.this, Search_one.class);
                startActivity(i);

            }
        });


        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                    String cartItemStock = SharedPrefUtil.getCartItemStock(mContext, SHARED_PREF_CartItemStock, "");

                    if (cartItemStock.equalsIgnoreCase("out of stock")) {

                        Singleton.getInstance().showErrorLongToast(mContext, "Some of the items are out of stock");
                    } else {

                        if (usertype.equalsIgnoreCase("guest")) {
                            Intent i = new Intent(CartActivity.this, OneTapLogin.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(CartActivity.this, ChooseAddressList.class);
                            startActivity(i);
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
                        Intent i = new Intent(CartActivity.this, MainActivityGuestNav.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(CartActivity.this, MainActivityNav.class);
                        startActivity(i);
                    }


                } else {
                    Utilities.showNetworkError(mContext);
                }


            }
        });


    }

    private void initUI() {

        mContext = CartActivity.this;
        recyclerView = findViewById(R.id.recyclerView);
        tv_priceval = findViewById(R.id.tv_priceval);
        tv_gstval = findViewById(R.id.tv_gstval);
        tv_totalpaidval = findViewById(R.id.tv_totalpaidval);
        rl_content2 = findViewById(R.id.rl_content2);
        tv_rv1 = findViewById(R.id.tv_rv1);
        tv_footertotal = findViewById(R.id.tv_footertotal);
        tv_footertotalitem = findViewById(R.id.tv_footertotalitem);
        rl_noitems = findViewById(R.id.rl_noitems);
        btn_shopnow = findViewById(R.id.btn_shopnow);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        btn_buynow = findViewById(R.id.btn_buynow);
        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        tv_deliveryval=findViewById(R.id.tv_deliveryval);
        membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.tokri);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
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
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
                switch (item.getItemId()){

                    case R.id.category:
                        //tv_cartcount.setVisibility(View.GONE);
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);
                        break;*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), MainActivityGuestNav.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
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
                        /*startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
                        overridePendingTransition(0,0);*/
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

                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();
                return true;
            }
        });


        CartListRequest();
    }


    public void CartListRequest() {

        Utilities.showLoading(mContext);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
        CartListRequest cartListRequest = new CartListRequest();
        cartListRequest.setUserCartType(profilemode);

        try {

            Call<CartListResponse> call = RetrofitUrlConnection.loadJSON(token).getmycart(cartListRequest);

            call.enqueue(new Callback<CartListResponse>() {
                @Override
                public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        Gson gson = new Gson();
                        String vakk = gson.toJson(response.body());
                        Log.e(TAG, "onResponse: orderval"+ vakk);


                        String addressid = "0";
                        if (response.body().getDefaultAddress().size() > 0) {
                            addressid = response.body().getDefaultAddress().get(0).getId();

                        }
                        SharedPrefUtil.setDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, addressid);
                        // SharedPrefUtil.setDefaultAddress(mContext, SHARED_PREF_DefaultAddress, fulladdress);


                        cartList = new ArrayList<>();
                        cartListAdapter = new CartAdapter(mContext, cartList);

                        List<Deliverycharges> mListdeliveryData = response.body().getDeliverycharges();

                        ordervalue= Double.parseDouble(response.body().getOrderValue().toString());


                        List<CartDatum> mListData = response.body().getCartData();

                        if (mListData != null && mListData.size() > 0) {

                            CartActivity.rl_content2.setVisibility(View.VISIBLE);
                            cartList.addAll(mListData);
                            rl_noitems.setVisibility(View.GONE);

                            try {
                                for (int i = 0; i < mListData.size(); i++) {
                                    if (mListData.get(i).getProductInstock().equalsIgnoreCase("Out-of-Stock")) {
                                        SharedPrefUtil.setCartItemStock(mContext, SHARED_PREF_CartItemStock, "out of stock");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {

                            }


                        } else {
                            CartActivity.tv_priceval.setText("₹ 0");
                            //CartActivity.tv_gstval.setText("+ ₹ 0");
                            CartActivity.tv_totalpaidval.setText("₹ 0");
                            CartActivity.rl_content2.setVisibility(View.GONE);
                            CartActivity.tv_rv1.setText("");
                            CartActivity.text.setText("");
                            rl_noitems.setVisibility(View.VISIBLE);


                            try {
                                MainActivityNav.text.setText("");
                                //MainActivityNav.cart_count("0");

                            } catch (Exception ex) {

                            }

                            try {
                                ProductDetailsActivity.text.setText("");
                            } catch (Exception ex) {

                            }

                            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

                            if (profilemode.equalsIgnoreCase("B2B")) {
                                SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");//remove 0

                            } else {
                                SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
                                SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");//remove 0
                            }


                        }

                        //cartListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(cartListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setRecycledViewPool(recycledViewPool);


                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<CartListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    /*@Override
    public void onRestart() {
        super.onRestart();
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
        initUI();
    }*/

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        actiononbackpressed();

    }

    private void actiononbackpressed() {
        usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
        if(usertype.equalsIgnoreCase("guest")) {
            Intent i = new Intent();
            i.setClass(mContext, MainActivityGuestNav.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent();
            //i.putExtra()
            i.setClass(mContext, MainActivityNav.class);
            startActivity(i);
        }
    }
}
