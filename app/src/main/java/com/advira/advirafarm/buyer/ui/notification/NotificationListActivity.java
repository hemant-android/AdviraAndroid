package com.advira.advirafarm.buyer.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

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
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.notification.adapter.NotificationAdapter;
import com.advira.advirafarm.buyer.ui.notification.api.MessageList;
import com.advira.advirafarm.buyer.ui.notification.api.NotificationResponse;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
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

public class NotificationListActivity extends AppCompatActivity implements IConsts {


    private List<MessageList> messageLists;
    public static RecyclerView recyclerView;
    private RelativeLayout rl_back;
    private Context mContext;
    NotificationAdapter notificationAdapter;
    private String from="";
    private static RelativeLayout rl_noitems;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.length()>0)
                {
                    Intent i = new Intent();
                    i.setClass(NotificationListActivity.this, MainActivityNav.class);

                    finishAffinity();
                    startActivity(i);
                }
                else
                {
                    NotificationListActivity.this.finish();
                }




            }
        });
    }


    private void initUI() {

        mContext = NotificationListActivity.this;
        recyclerView = findViewById(R.id.recyclerView);
        rl_back = findViewById(R.id.rl_back);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        SharedPrefUtil.setFCMMessageCount(mContext, SHARED_PREF_FCMMessageCount, "0");
        rl_noitems=findViewById(R.id.rl_noitems);


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
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
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
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
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();
                return true;
            }
        });




        NotificationListRequest();

        try {
            Bundle extras = getIntent().getExtras();


            if (extras != null) {
                from = extras.getString("from");

            }
        }
        catch (Exception ex)
        {

        }


    }


    public void NotificationListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<NotificationResponse> call = RetrofitUrlConnection.loadJSON(token).notification();

            call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        messageLists = new ArrayList<>();
                        notificationAdapter = new NotificationAdapter(mContext,messageLists);

                        List<MessageList> mListData = response.body().getMessageList();

                        if (mListData != null && mListData.size() > 0) {
                            messageLists.addAll(mListData);
                            rl_noitems.setVisibility(View.GONE);
                        }
                        else
                        {
                            rl_noitems.setVisibility(View.VISIBLE);
                        }

                        //notificationAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(notificationAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();


                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }

                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

        NotificationListActivity.this.finish();

    }
}
