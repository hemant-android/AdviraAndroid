package com.advira.advirafarm.buyer.ui.myaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.subscrption.CheckBalance;
import com.advira.advirafarm.buyer.ui.subscrption.SubscriptionPreviewActivity;
import com.advira.advirafarm.buyer.ui.wallet.AddMoney;
import com.advira.advirafarm.buyer.ui.wallet.WalletHistoryActivity;
import com.advira.advirafarm.buyer.ui.wallet.api.MywalletpassbookResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

public class WalletActivity extends AppCompatActivity {

    private RelativeLayout cvAddmoney,rl_back;
    private Context mContext;
    private EditText et_rupees;
    String addmoney="";
    public static String Amount="";
    public static String balance="";

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text,tvBalance,tvhistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(WalletActivity.this, MainActivityNav.class);
                finish();
                startActivity(i);



            }
        });


        cvAddmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_rupees.getText().toString().length()==0) {
                    et_rupees.requestFocus();
                    et_rupees.setError("Please Enter Amount!!");
                }else if (et_rupees.getText().toString().length()<3) {
                    et_rupees.requestFocus();
                    et_rupees.setError("Amount not less than 100.");
                }
                else {
                    addmoney = et_rupees.getText().toString().trim();
                    Intent i = new Intent();
                    i.setClass(mContext, AddMoney.class);
                    i.putExtra("addmoney", addmoney);
                    i.putExtra("from", "wallet");
                    startActivity(i);
                }
            }
        });

        tvhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(mContext, WalletHistoryActivity.class);
                startActivity(i);
            }
        });


    }


    private void initUI() {
        mContext = WalletActivity.this;
        cvAddmoney=findViewById(R.id.cvAddMoney);
        et_rupees=findViewById(R.id.et_rupees);
        tvBalance=findViewById(R.id.tvBalance);
        tvhistory=findViewById(R.id.tvhistory);
        rl_back=findViewById(R.id.rl_back);

        /*Bundle extras = getIntent().getExtras();


        if (extras != null) {

            Amount=extras.getString("amount");
            WalletFragment.Amount=Amount;
            CheckBalance.Amount=Amount;

        }
        tvBalance.setText("₹ " + Amount);*/

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.tokri);
        bottomnavview.setSelectedItemId(R.id.home_wallet);
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

        getWalletBalance();

    }

    private void getWalletBalance() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try{
            Call<MywalletpassbookResponse> call= RetrofitUrlConnection.loadJSON(token).mywalletpassbook();

            call.enqueue(new Callback<MywalletpassbookResponse>() {
                @Override
                public void onResponse(Call<MywalletpassbookResponse> call, Response<MywalletpassbookResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        balance=response.body().getWalletBalance();
                        Log.e(TAG, "onResponse: wallet123"+balance );
                        tvBalance.setText("₹"+balance);
                        Utilities.dismissDialog();
                    }
                    else{
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<MywalletpassbookResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}