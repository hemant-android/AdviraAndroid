package com.advira.advirafarm.buyer.ui.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.cart.adapter.ChooseAddressListAdapter;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
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

public class SelectAddressListGuest extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart,rl_footer;
    private TextView tv_cartcount;
    private RecyclerView recyclerView;
    private Button cv_add;
    public static Button btn_deliverhere;
    private Context mContext;
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    private List<AddressDate> addressList;
    ChooseAddressListAdapter addressListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_list_guest);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectAddressListGuest.this.finish();
            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(SelectAddressListGuest.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(SelectAddressListGuest.this, SearchActivity.class);
                i.setClass(SelectAddressListGuest.this, Search_one.class);
                startActivity(i);

            }
        });


        cv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Add New Address");
                builder.setMessage("Do you wish to continue?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent i = new Intent();
                                i.setClass(mContext, AddNewAddressActivity.class);
                                mContext.startActivity(i);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


            }
        });

        btn_deliverhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectAddress();

            }
        });
    }


    private void initUI() {

        mContext = SelectAddressListGuest.this;
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
        String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

        rl_back = findViewById(R.id.rl_back);
        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);

        rl_footer=findViewById(R.id.rl_footer);

        if(usertype.equalsIgnoreCase("guest")){
            rl_footer.setVisibility(View.GONE);
        }

        tv_cartcount.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        cv_add = findViewById(R.id.cv_add);
        cv_add.setVisibility(View.GONE);
        btn_deliverhere = findViewById(R.id.btn_deliverhere);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.getMenu().getItem(0).setCheckable(false);
        /*if(usertype.equalsIgnoreCase("guest")){
            bottomnavview.setSelectedItemId(R.id.home_btmnav);
        }
        else{

            //bottomnavview.setSelectedItemId(R.id.tokri);

        }*/

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
                            startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
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


        AddressList();


    }

    public void AddressList() {

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

                        }

                        addressListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(addressListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();


                    } else {
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove Toast
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
        //super.onBackPressed();
        SelectAddressListGuest.this.finish();

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

                    Utilities.dismissDialog();

                    try {
                        // BuynowActivity.tv_add.setText(fulladdress);
                        //BuynowActivity.tv_addid.setText(addid);
                    }
                    catch (Exception ex)
                    {

                    }

                    Intent i = new Intent();
                    i.setClass(mContext, OrderPreviewActivity.class);
                    mContext.startActivity(i);



                } else {

                    Singleton.getInstance().showShortToast(mContext, "Please select an address");
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
