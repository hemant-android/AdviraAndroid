package com.advira.advirafarm.buyer.ui.payment.paylaterpayment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.order.OrderPlacedActivity;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.adapter.CartAdapter;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.order.OrderDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
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

public class PaymentSuccess extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart;
    public static TextView tv_cartcount;

    public static Button btn_continueshopping;
    private Context mContext;
    public static TextView tv_msg;
    private TextView tv_goto;
    private ImageView chk;
    private String orderno="";
    private String orderid="";
    String profilemode = "B2B";
    CartAdapter cartListAdapter;
    private List<CartDatum> cartList;


    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        initUI();

        ((Animatable) chk.getDrawable()).start();


        btn_continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(PaymentSuccess.this, MainActivityNav.class);
                finishAffinity();
                PaymentSuccess.this.startActivity(mainIntent);

            }
        });

        tv_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent mainIntent = new Intent(PaymentSuccess.this, OrderDetailsActivity.class);
                mainIntent.putExtra("orderid",orderid);
                mainIntent.putExtra("from","finish");
                finish();
                PaymentSuccess.this.startActivity(mainIntent);


            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(PaymentSuccess.this, CartActivity.class);
                finishAffinity();
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(PaymentSuccess.this, SearchActivity.class);
                i.setClass(PaymentSuccess.this, Search_one.class);
                finishAffinity();
                startActivity(i);

            }
        });


    }

    private void initUI() {
        mContext = PaymentSuccess.this;

        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);

        //String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNT, "0");
        //tv_cartcount.setText(cartcount);

        /*String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "0");
        SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);
        tv_cartcount.setText(cartcount);*/
        btn_continueshopping = findViewById(R.id.btn_continueshopping);
        chk = findViewById(R.id.chk);
        tv_msg = findViewById(R.id.tv_msg);
        tv_goto = findViewById(R.id.tv_goto);


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
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
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
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
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_subscription:
                        startActivity(new Intent(getApplicationContext(), MySubscription.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_wallet:
                        startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                        overridePendingTransition(0,0);
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




        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            orderno  = extras.getString("orderno");
            orderid  = extras.getString("orderid");


        }

        //tv_msg.setText("Your order #"+orderno +" has been placed successfully");
        tv_msg.setVisibility(View.GONE);

        UpdateCartSize();


    }

    public void UpdateCartSize() {

        Utilities.showLoading(mContext);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        CartListRequest cartListRequest = new CartListRequest();
        cartListRequest.setUserCartType(usermode);

        try {

            Call<CartListResponse> call = RetrofitUrlConnection.loadJSON(token).getmycart(cartListRequest);

            call.enqueue(new Callback<CartListResponse>() {
                @Override
                public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        try {
                            MainActivityNav.text.setText("");
                            MainActivityNav.text.setText("");

                        } catch (Exception ex) {

                        }
                        try {
                            OrderPlacedActivity.text.setText("0");

                        } catch (Exception ex) {

                        }

                        try {
                            ProductDetailsActivity.text.setText("");
                        } catch (Exception ex) {

                        }

                        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                        if (profilemode.equalsIgnoreCase("B2B")) {
                            String cartcount = SharedPrefUtil.getCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");//remove 0
                            SharedPrefUtil.setCartCountB2B(mContext,SHARED_PREF_CARTCOUNT,cartcount);
                            text.setText(cartcount);


                        } else {
                            String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNTB2B, "");//remove 0
                            SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount);
                            text.setText(cartcount);

                            String cartcount1 = SharedPrefUtil.getCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2B, "");//remove 0
                            SharedPrefUtil.setCartCount(mContext,SHARED_PREF_CARTCOUNT,cartcount1);
                            text.setText(cartcount1);
                        }

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
    @Override
    public void onBackPressed() {
        return;
    }


}
