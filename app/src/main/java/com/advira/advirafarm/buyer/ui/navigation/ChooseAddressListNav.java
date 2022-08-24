package com.advira.advirafarm.buyer.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.ui.address.AddressfromMap;
import com.advira.advirafarm.buyer.ui.address.AutoDetectAddressList;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddNewAddressActivity;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.AddressDate;
import com.advira.advirafarm.buyer.ui.address.api.AddressListData;
import com.advira.advirafarm.buyer.ui.address.api.AddressListRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddressListResponse;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.adapter.ChooseAddressListAdapter;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.HomeFragment;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
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

public class ChooseAddressListNav extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart,rl_currentaddress,rl_footer;
    private TextView tv_cartcount;
    private RecyclerView recyclerView;
    private Button cv_add;
    private Button btn_deliverhere;
    private Context mContext;
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    private List<AddressDate> addressList;
    ChooseAddressListAdapter addressListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseaddress);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               ChooseAddressListNav.this.finish();
            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(ChooseAddressListNav.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(ChooseAddressListNav.this, SearchActivity.class);
                i.setClass(ChooseAddressListNav.this, Search_one.class);
                startActivity(i);
            }
        });

        cv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, AddNewAddressActivity.class);
                i.putExtra("from","chooseaddressfromlist");
                mContext.startActivity(i);
            }
        });

        btn_deliverhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAddress();
            }
        });

        rl_currentaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                //i.setClass(mContext, AutoDetectAddressList.class);
                i.setClass(mContext, AddressfromMap.class);
                i.putExtra("from","Home");
                startActivity(i);
            }
        });
    }


    private void initUI() {

        mContext = ChooseAddressListNav.this;
        rl_back = findViewById(R.id.rl_back);
        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_currentaddress=findViewById(R.id.rl_currentaddress);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);
        rl_footer=findViewById(R.id.rl_footer);
        rl_footer.setVisibility(View.GONE);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        //tv_cartcount.setText(cartcount);



        recyclerView = findViewById(R.id.recyclerView);
        cv_add = findViewById(R.id.cv_add);
        btn_deliverhere = findViewById(R.id.btn_deliverhere);
        btn_deliverhere.setText("Select Address");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.tokri);
        bottomnavview.getMenu().getItem(0).setCheckable(false);
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
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
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
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });




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
                        addressListAdapter = new ChooseAddressListAdapter(mContext,addressList);

                        List<AddressDate> mListData = response.body().getAddressListData();

                        if (mListData != null && mListData.size() > 0) {
                            addressList.addAll(mListData);
                            btn_deliverhere.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            btn_deliverhere.setVisibility(View.GONE);
                        }

                        addressListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(addressListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
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

        ChooseAddressListNav.this.finish();

    }

    private void SelectAddress() {

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

                    Utilities.dismissDialog();

                    //String headeraddress = "Deliver to " +response.body().getAddressDate().get(0).getCityName() + " "+ response.body().getAddressDate().get(0).getPincode();
                    String headeraddress = response.body().getAddressDate().get(0).getCityName()+ " "+ response.body().getAddressDate().get(0).getPincode();
                    SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, headeraddress);

                    try{
                        MainActivityNav.tv_deliverto.setText(headeraddress);
                    }
                    catch (Exception ex)
                    {

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




                    ChooseAddressListNav.this.finish();


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
