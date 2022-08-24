package com.advira.advirafarm.buyer.ui.payment.membershippayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.order.OrderDetailsActivity;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PayLaterPaymentOption;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PaymentFailure;
import com.advira.advirafarm.buyer.ui.payment.paymentfailed.OrderFailedCancellationActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MembershipPaymentFailed extends AppCompatActivity implements IConsts {

    private RelativeLayout rl_back ,rl_search,rl_cart;
    private TextView tv_cartcount;

    private Button btn_retrypayment;
    private Context mContext;
    public static TextView tv_msg;
    private TextView tv_goto;
    private TextView tv_cancelorder;
    private ImageView chk;
    private String orderno="";
    private String orderid="";

    String memberid = "";
    String membershipDetails = "";
    String price = "";
    String duration = "";
    String userId = "";
    String buttonLabel = "";

    private String totalval = "";
    private String addressid = "";
    private String address = "";
    private String ordertype = "";
    private String totalamount = "";
    private String totaltax = "";
    private String totaldiscount = "";
    private String grandtotal = "";

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_failure);

        initUI();

        btn_retrypayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MembershipPaymentFailed.this, MembershipPaymentOption.class);

                i.putExtra("memberid",memberid);
                i.putExtra("membershipDetails",membershipDetails);
                i.putExtra("price",price);
                i.putExtra("duration",duration);
                i.putExtra("buttonLabel",buttonLabel);
                i.putExtra("userId",userId);
                i.putExtra("from", "finish");
                finishAffinity();
                startActivity(i);

            }
        });


        tv_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent mainIntent = new Intent(MembershipPaymentFailed.this, MembershipActivity.class);
                //mainIntent.putExtra("orderid",orderid);
                mainIntent.putExtra("from","finish");
                finish();
                startActivity(mainIntent);


            }
        });


        tv_cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(MembershipPaymentFailed.this, OrderFailedCancellationActivity.class);
                i.putExtra("total", grandtotal);
                i.putExtra("orderid", orderid);
                i.putExtra("orderno", orderno);
                finishAffinity();
                startActivity(i);


            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(MembershipPaymentFailed.this, CartActivity.class);
                finishAffinity();
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(MembershipPaymentFailed.this, SearchActivity.class);
                i.setClass(MembershipPaymentFailed.this, Search_one.class);
                finishAffinity();
                startActivity(i);

            }
        });


    }

    private void initUI() {

        mContext = MembershipPaymentFailed.this;

        rl_search  = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
        //tv_cartcount.setText(cartcount);


        btn_retrypayment = findViewById(R.id.btn_retrypayment);
        chk = findViewById(R.id.chk);
        tv_msg = findViewById(R.id.tv_msg);
        tv_goto = findViewById(R.id.tv_goto);

        tv_goto.setText("Go to Membership Page.");
        tv_cancelorder = findViewById(R.id.tv_cancelorder);
        tv_cancelorder.setVisibility(View.GONE);


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
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        rl_cart.setVisibility(View.GONE);
                        tv_cartcount.setVisibility(View.GONE);
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
            addressid = extras.getString("addressid");
            address = extras.getString("address");
            ordertype = extras.getString("ordertype");
            totalamount = extras.getString("totalamount");
            totaltax = extras.getString("totaltax");
            totaldiscount = extras.getString("totaldiscount");
            grandtotal = extras.getString("grandtotal");
            memberid=extras.getString("memberid");
            membershipDetails=extras.getString("membershipDetails");
            price=extras.getString("price");
            duration=extras.getString("duration");
            buttonLabel=extras.getString("buttonLabel");
            userId=extras.getString("userId");

        }


        tv_msg.setText("Your Membership not started.Please complete your payment.");



    }


    public Integer twoDatesBetweenTime(String oldtime) {
        // TODO Auto-generated method stub
        int day = 0;
        int hh = 0;
        int mm = 0;

        int differencemin = 0;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldDate = dateFormat.parse(oldtime);
            Date cDate = new Date();
            Long timeDiff = cDate.getTime() - oldDate.getTime();
            day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
            differencemin = (int) TimeUnit.MILLISECONDS.toMinutes(timeDiff);
            hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
            mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       /* if(day==0)
        {
            return hh + " hour " + mm + " min";
        }
        else if(hh==0)
        {
            return mm + " min";
        }
        else
        {
            return day + " days " + hh + " hour " + mm + " min";
        }*/

        return differencemin;
    }


    @Override
    public void onBackPressed() {
        return;
    }

}