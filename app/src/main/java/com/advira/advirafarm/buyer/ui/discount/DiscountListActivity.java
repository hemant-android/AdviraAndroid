package com.advira.advirafarm.buyer.ui.discount;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddNewAddressActivity;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.AddressListData;
import com.advira.advirafarm.buyer.ui.address.api.AddressListRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddressListResponse;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.cart.adapter.ChooseAddressListAdapter;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountCoupon;
import com.advira.advirafarm.buyer.ui.discount.adapter.DiscountListAdapter;
import com.advira.advirafarm.buyer.ui.discount.api.DiscountListResponse;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountListActivity extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart;
    private TextView tv_cartcount;
    private RecyclerView recyclerView;
    private Button cv_add;
    private Button btn_deliverhere;
    private Context mContext;

    private List<DiscountCoupon> discountCouponList;
    DiscountListAdapter discountListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdiscount);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DiscountListActivity.this.finish();
            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(DiscountListActivity.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(DiscountListActivity.this, SearchActivity.class);
                i.setClass(DiscountListActivity.this, Search_one.class);
                startActivity(i);

            }
        });


    }


    private void initUI() {

        mContext = DiscountListActivity.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        tv_cartcount.setText(cartcount);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        DiscountListRequest();


    }

    public void DiscountListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");


        try {

            Call<DiscountListResponse> call = RetrofitUrlConnection.loadJSON(token).discount();

            call.enqueue(new Callback<DiscountListResponse>() {
                @Override
                public void onResponse(Call<DiscountListResponse> call, Response<DiscountListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        discountCouponList = new ArrayList<>();
                        discountListAdapter = new DiscountListAdapter(mContext,discountCouponList);

                        List<DiscountCoupon> mListData = response.body().getDiscountCoupon();

                        if (mListData != null && mListData.size() > 0) {
                            discountCouponList.addAll(mListData);

                        }

                        discountListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(discountListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();


                    } else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<DiscountListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        /*Intent i=new Intent();
        i.setClass(mContext,OrderPreviewActivity.class);
        startActivity(i);*/

        DiscountListActivity.this.finish();
    }




}
