package com.advira.advirafarm.buyer.ui.cart;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddNewAddressActivity;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.AddressDate;
import com.advira.advirafarm.buyer.ui.address.api.AddressListData;
//import com.advira.advirafarm.buyer.ui.address.api.AddressList;
import com.advira.advirafarm.buyer.ui.address.api.AddressListRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddressListResponse;
//import com.advira.advirafarm.buyer.ui.address.api.DefaultAddress;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.buynow.BuynowActivity;
import com.advira.advirafarm.buyer.ui.cart.adapter.ChooseAddressListAdapter;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
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

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.ui.buynow.BuynowActivity.imgurl;

public class ChooseAddressListBuynow extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart;
    private TextView tv_cartcount;
    private RecyclerView recyclerView;
    private Button cv_add;
    private Button btn_deliverhere;
    private Context mContext;
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    private List<AddressDate> addressList;
    ChooseAddressListAdapter addressListAdapter;


    private TextView tv_priceval;
    private TextView tv_gstval;
    private TextView tv_totalpaidval;
    private TextView tv_price;
    private TextView tv_qty;
    private TextView tv_mrpval;
    private TextView tv_productdetails;
    private TextView tv_productname;
    private ImageView iv_product, iv_rx;
    private TextView tv_pack;
    private TextView tv_stock;
    private TextView tv_mrp;
    private TextView tv_inr;
    private TextView tv_discount;
    private TextView tv_discountval;
    private String discountid = "";
    private String discount_coupon_name = "";
    private String discount_type = "";
    private String discount_amount = "";
    private String discount_details = "";
    private String credit_limit = "0";
    private String credit_availed = "0";
    private String credit_balance = "0";
    private RelativeLayout rl_coupon;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseaddress);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseAddressListBuynow.this.finish();
            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(ChooseAddressListBuynow.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(ChooseAddressListBuynow.this, SearchActivity.class);
                i.setClass(ChooseAddressListBuynow.this, Search_one.class);
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
                                i.putExtra("from","Buynow");
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

        mContext = ChooseAddressListBuynow.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0

        tv_cartcount.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        cv_add = findViewById(R.id.cv_add);
        btn_deliverhere = findViewById(R.id.btn_deliverhere);
        recyclerView.setHasFixedSize(false);//true
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

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
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        break;
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
        super.onBackPressed();

        ChooseAddressListBuynow.this.finish();

    }

    private void SelectAddress() {

        Utilities.showLoading(mContext);

        String addid = SharedPrefUtil.getDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, "0");
        String fulladdress = SharedPrefUtil.getDefaultAddress(mContext, SHARED_PREF_DefaultAddress, "0");
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

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
                        BuynowActivity.tv_add.setText(fulladdress);
                        BuynowActivity.tv_addid.setText(addid);
                    }
                    catch (Exception ex)
                    {

                    }
                    String qty=BuynowActivity.tv_qty.getText().toString().replaceAll("[^0-9]", "");
                    Intent i = new Intent();
                    i.setClass(mContext, BuynowActivity.class);
                    i.putExtra("productname", BuynowActivity.tv_productname.getText().toString());
                    //i.putExtra("productdetails", tv_productdescription.getText().toString());
                    i.putExtra("mrpval", BuynowActivity.tv_mrpval.getText().toString());
                    i.putExtra("price", BuynowActivity.tv_price.getText().toString());

                    i.putExtra("packsize", BuynowActivity.tv_pack.getText().toString());

                    i.putExtra("pack", BuynowActivity.tv_pack.getText().toString());
                    i.putExtra("stock", BuynowActivity.tv_stock.getText().toString());

                    i.putExtra("address", fulladdress);
                    i.putExtra("addressid", addid);
                    //i.putExtra("isrx", isrx);
                    i.putExtra("mrplabel", BuynowActivity.tv_mrp.getText());
                    i.putExtra("ratelabel", BuynowActivity.tv_inr.getText());
                    //i.putExtra("moqunit", tv_minordervalunit.getText());

                    if(usertype.equalsIgnoreCase("B2C"))
                    {
                        i.putExtra("discountlabel", ProductDetailsActivity.tv_discount.getText());
                        i.putExtra("imgurl", ProductDetailsActivity.imgurl.toString());
                        i.putExtra("gstval", ProductDetailsActivity.gst.toString());
                        i.putExtra("qty", ProductDetailsActivity.integer_number.getText().toString());
                    }
                    else{
                        i.putExtra("discountlabel", ProductDetailsActivityB2B.tv_discount.getText());
                        i.putExtra("imgurl", ProductDetailsActivityB2B.imgurl.toString());
                        i.putExtra("gstval", ProductDetailsActivityB2B.gst.toString());
                        i.putExtra("qty", ProductDetailsActivityB2B.integer_number.getText().toString());
                    }
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
