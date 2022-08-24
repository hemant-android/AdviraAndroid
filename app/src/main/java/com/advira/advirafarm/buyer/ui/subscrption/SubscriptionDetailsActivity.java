package com.advira.advirafarm.buyer.ui.subscrption;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.BasketDetailsAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.BasketDetailsUpdateAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.BasketDetailsUpdateviaDateAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.DailyBasketAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.SubscriptionPreviewAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DateSubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.Product_Basket;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDetailResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_SubscriptionID;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

public class SubscriptionDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Context mContext;
    RelativeLayout rl_back,rl_save;

    BasketDetailsUpdateAdaptor basketDetailsAdaptor;
    BasketDetailsUpdateviaDateAdaptor basketDetailsUpdateviaDateAdaptorAdaptor;

    //SubscriptionPreviewAdaptor subscriptionPreviewAdaptor;
    private List<BasketDatum> basketList;
    String subscription_id="";
    String addressID="";
    public static String SelectedDate="";

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_details);

        Init();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(SubscriptionDetailsActivity.this, SubscriptionActivity.class);
                startActivity(i);

            }
        });

    }

    private void Init() {
        mContext=SubscriptionDetailsActivity.this;
        rl_back=findViewById(R.id.rl_back);
        rl_save=findViewById(R.id.rl_save);

        recyclerView=findViewById(R.id.recyclerview5);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            addressID=extras.getString("addressID");
                subscription_id=extras.getString("subscription_id");
                SelectedDate=extras.getString("SelectedDate");

        }

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
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

        if(SelectedDate.equalsIgnoreCase("")) {
            subscriptionBasketDetails();
        }else
        {
            subscriptionBasketDetailsviaDate();
        }

    }

    private void subscriptionBasketDetails() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        /*subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        ConfirmSubscriptionRequest confirmSubscriptionRequest=new ConfirmSubscriptionRequest();
        confirmSubscriptionRequest.setSubscriptionId(subscription_id);
        confirmSubscriptionRequest.setAddressId(addressID);*/

        try{
            Call<SubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail();

            call.enqueue(new Callback<SubscriptionDetailResponse>() {
                @Override
                public void onResponse(Call<SubscriptionDetailResponse> call, Response<SubscriptionDetailResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Log.e(TAG, "onResponse: Search"+jjj );
                        Utilities.dismissDialog();
                        //if (SelectedDate.equalsIgnoreCase("")) {
                            basketList = new ArrayList<>();
                            basketDetailsAdaptor = new BasketDetailsUpdateAdaptor(mContext, basketList);

                            for(int i=0;i<response.body().getSubscription().size();i++) {
                                List<BasketDatum> mListData = response.body().getSubscription().get(0).getBasketData();
                                if (mListData != null && mListData.size() > 0) {
                                    basketList.addAll(mListData);
                                } else {
                                    try {
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            //orderAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(basketDetailsAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setNestedScrollingEnabled(false);
                        /*} else {

                            basketList = new ArrayList<>();
                            basketDetailsUpdateviaDateAdaptorAdaptor = new BasketDetailsUpdateviaDateAdaptor(mContext, basketList);

                            //tv_itemcount.setText("Item Count : "+response.body().getBasketCount());
                            List<BasketDatum> mListData = response.body().getBasketData();
                            if (mListData != null && mListData.size() > 0) {
                                basketList.addAll(mListData);
                            } else {
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            //orderAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(basketDetailsUpdateviaDateAdaptorAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setNestedScrollingEnabled(false);
                            Utilities.dismissDialog();

                        }*/
                    }
                    else{
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<SubscriptionDetailResponse> call, Throwable t) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscriptionBasketDetailsviaDate() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        ConfirmSubscriptionRequest confirmSubscriptionRequest=new ConfirmSubscriptionRequest();
        confirmSubscriptionRequest.setSubscriptionId(subscription_id);
        //confirmSubscriptionRequest.setAddressId(addressID);
        confirmSubscriptionRequest.setSubscriptionDate(SelectedDate);

        try{
            Call<DateSubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail_bydate(confirmSubscriptionRequest);

            call.enqueue(new Callback<DateSubscriptionDetailResponse>() {
                @Override
                public void onResponse(Call<DateSubscriptionDetailResponse> call, Response<DateSubscriptionDetailResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Log.e(TAG, "onResponse: Search"+jjj );
                        Utilities.dismissDialog();
                        /*if (SelectedDate.equalsIgnoreCase("")) {
                            basketList = new ArrayList<>();
                            basketDetailsAdaptor = new BasketDetailsUpdateAdaptor(mContext, basketList);

                            List<BasketDatum> mListData = response.body().getBasketData();
                            if (mListData != null && mListData.size() > 0) {
                                basketList.addAll(mListData);
                            } else {
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            //orderAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(basketDetailsAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setNestedScrollingEnabled(false);
                        } else {
*/
                            basketList = new ArrayList<>();
                            basketDetailsUpdateviaDateAdaptorAdaptor = new BasketDetailsUpdateviaDateAdaptor(mContext, basketList);

                            //tv_itemcount.setText("Item Count : "+response.body().getBasketCount());

                            List<BasketDatum> mListData = response.body().getBasketData();
                            if (mListData != null && mListData.size() > 0) {
                                basketList.addAll(mListData);
                            } else {
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            //orderAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(basketDetailsUpdateviaDateAdaptorAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setNestedScrollingEnabled(false);
                            Utilities.dismissDialog();

                       // }
                    }
                    else{
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<DateSubscriptionDetailResponse> call, Throwable t) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}