package com.advira.advirafarm.buyer.ui.address;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.adapter.AddressListAdapter;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.AddressDate;
import com.advira.advirafarm.buyer.ui.address.api.AddressListData;
import com.advira.advirafarm.buyer.ui.address.api.AddressListRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddressListResponse;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.buynow.BuynowActivity;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.HomeFragment;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressList extends AppCompatActivity implements IConsts {


    private RecyclerView recyclerView;
    private RelativeLayout rl_back;
    private Button cv_add;
    private Button btn_deliverhere;
    private Button btn_setdefault;
    private Context mContext;

    //private List<AddressListData> addressList;
    private List<AddressDate> addressList;
    AddressListAdapter addressListAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddress);

        initUI();
        recycledViewPool=new RecyclerView.RecycledViewPool();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddressList.this.finish();


            }
        });


        cv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(mContext, AddNewAddressActivity.class);
                mContext.startActivity(i);

            }


        });

        btn_deliverhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectAddress();

            }
        });

        btn_setdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MakeDefault();

            }
        });

    }


    private void initUI() {

        mContext = AddressList.this;

        recyclerView = findViewById(R.id.recyclerView);
        rl_back = findViewById(R.id.rl_back);
        cv_add = findViewById(R.id.cv_add);
        btn_deliverhere = findViewById(R.id.btn_deliverhere);
        btn_setdefault = findViewById(R.id.btn_setdefault);
        recyclerView.setHasFixedSize(false);//true
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        String showselect = SharedPrefUtil.getAddressType(mContext, SHARED_PREF_ADDRESSTYPE, "");

        if (showselect.equalsIgnoreCase("select")) {
            btn_deliverhere.setVisibility(View.VISIBLE);
            btn_setdefault.setVisibility(View.GONE);
        } else {
            btn_deliverhere.setVisibility(View.GONE);
            btn_setdefault.setVisibility(View.VISIBLE);
        }

        AddressListRequest();


    }

    public void AddressListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        AddressListRequest addressListRequest= new AddressListRequest();
        addressListRequest.setUserType(usertype);

        try {

            Call<AddressListResponse> call = RetrofitUrlConnection.loadJSON(token).addresslist(addressListRequest);

            call.enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        addressList = new ArrayList<>();
                        addressListAdapter = new AddressListAdapter(mContext, addressList);

                        List<AddressDate> mListData = response.body().getAddressListData();
                        if (mListData != null && mListData.size() > 0) {
                            addressList.addAll(mListData);

                        } else {
                            String headeraddress = "Select Address";
                            SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, headeraddress);

                            try {
                                MainActivityNav.tv_deliverto.setText(headeraddress);
                            } catch (Exception ex) {

                            }

                            try{
                                HomeFragment.tv_deliverto.setText(headeraddress);
                            }
                            catch (Exception ex)
                            {

                            }

                            try{
                                HomeFragmentB2B.tv_deliverto.setText(headeraddress);
                            }
                            catch (Exception ex)
                            {

                            }
                        }


                        addressListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(addressListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setRecycledViewPool(recycledViewPool);
                        Utilities.dismissDialog();


                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AddressListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AddressList.this.finish();

    }

    private void SelectAddress() {

        Utilities.showLoading(mContext);

        String addid = SharedPrefUtil.getDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, "0");
        String fulladdress = SharedPrefUtil.getDefaultAddress(mContext, SHARED_PREF_DefaultAddress, "0");


        DefaultAddressRequest defaultAddressRequest = new DefaultAddressRequest();
        defaultAddressRequest.setAddressId(addid);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        Call<AddAddressResponse> call = RetrofitUrlConnection.loadJSON(token).setdefaultaddress(defaultAddressRequest);

        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                    String fulladdress = response.body().getAddressDate().get(0).getAddress() +
                            " " + response.body().getAddressDate().get(0).getAddress2() + " "
                            + response.body().getAddressDate().get(0).getCityName() + ", "
                            + response.body().getAddressDate().get(0).getStateName() + " "
                            + response.body().getAddressDate().get(0).getPincode();


                    SharedPrefUtil.setDefaultAddress(mContext, SHARED_PREF_DefaultAddress, fulladdress);

                    try {
                        BuynowActivity.tv_add.setText(fulladdress);
                        BuynowActivity.tv_addid.setText(addid);
                    } catch (Exception ex) {

                    }

                    try {
                        OrderPreviewActivity.tv_pinval.setText(fulladdress);
                        OrderPreviewActivity.tv_addid.setText(addid);
                    } catch (Exception ex) {

                    }



                    try {
                        /*OrderPaymentActivity.tv_pinval.setText(fulladdress);
                        OrderPaymentActivity.tv_addid.setText(addid);*/
                    } catch (Exception ex) {

                    }


                    ((Activity) mContext).finish();

                } else {
                    Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }

    private void MakeDefault() {

        Utilities.showLoading(mContext);

        String addid = SharedPrefUtil.getDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, "0");

        DefaultAddressRequest defaultAddressRequest = new DefaultAddressRequest();
        defaultAddressRequest.setAddressId(addid);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        Call<AddAddressResponse> call = RetrofitUrlConnection.loadJSON(token).setdefaultaddress(defaultAddressRequest);

        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Singleton.getInstance().showShortToast(mContext, "Address set as default successfully");

                } else {
                    Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }


    @Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }

}
