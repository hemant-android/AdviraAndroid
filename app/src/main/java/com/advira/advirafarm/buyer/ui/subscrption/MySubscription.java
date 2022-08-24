package com.advira.advirafarm.buyer.ui.subscrption;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;

import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;

import com.advira.advirafarm.buyer.ui.subscrption.adaptor.BasketDetailsAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.MySubscriptionAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DateSubscriptionDetailResponse;

import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDetailResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_SubscriptionID;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserActive;

public class MySubscription extends AppCompatActivity {


    Context mContext;
    RelativeLayout rl_back, rl_addsubscription,rl_noitems;
    ImageView iv_addsubscription;
    RecyclerView recyclerViewmy,recyclerView;
    TextView text;
    private TextView tv_tab1;
    private TextView tv_tab2;
    private View v_linetab1;
    private View v_linetab2;


    RelativeLayout rl_carthome,rl_subs_details,rl_next,ll_tab,rl_mycalander,rl_noorder,rl_delivery;
    ScrollView scroll_View;
    ImageView iv_item;
    TextView tv_date, tv_remove, tv_addmore,tv_title,tv_subs_type,walletamount, tv_text1,tv_text3,tv_delivery;
    public static TextView tv_totalpaidval;
    CollapsibleCalendar collapsibleCalendar;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter;
    public String subscription_id="";
    String addressID="";
    public static String SelectedDate="";
    String subscriptioncheck="";
    String deliveryStatus="";
    String message="";
    String startDate="";
    String endDate="";
    String modecheckDate="";
    String balance="";
    String deliverymode="";
    String Subscription_status="";

    TextView tv_productname, tv_stock,tv_text2,tv_subsperiod;
    ImageView imageView, iv_rx;
    TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
            tv_orderqtyval, tv_prodid, tv_inr, tv_boxsize, tv_mrp, tv_orderqtyunit,tv_discount;

    MySubscriptionAdaptor mySubscriptionAdaptor;
    List<SubscriptionDatum> subscription;

    BasketDetailsAdaptor basketDetailsAdaptor;
    private List<BasketDatum> basketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscription);

        InitUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent mainIntent = new Intent(MySubscription.this, MainActivityNav.class);
                    finish();
                    startActivity(mainIntent);

            }
        });


        iv_addsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Subscription_status.equalsIgnoreCase("not-confirmed")){
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(MySubscription.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("For adding new subscription. Please Confirm/Update Your Pending Subscription.");
                    alert.setPositiveButton("OK",null);
                    alert.show();

                }else {
                    Intent i = new Intent();
                    i.setClass(MySubscription.this, AddSubscription.class);
                /*i.putExtra("selectedDate",SelectedDate);
                i.putExtra("message",message);*/
                    startActivity(i);
                }

            }
        });

        tv_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MySubscription.this, DailyBasketProductList.class);
                i.putExtra("subscription_id",subscription_id);
                i.putExtra("SelectedDate",SelectedDate);
                Log.e(TAG, "onClick: tvaddmore"+subscription_id );
                startActivity(i);
            }
        });

        tv_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_tab1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_tab2.setTextColor(getResources().getColor(R.color.colorBlack));

                //ll_sequence.setVisibility(View.VISIBLE);
                recyclerViewmy.setVisibility(View.GONE);
                rl_mycalander.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                //recyclerView.setLayoutManager(new LinearLayoutManager(mContext) );
                collapsibleCalendar.setVisibility(View.VISIBLE);
                tv_addmore.setVisibility(View.VISIBLE);
                rl_noitems.setVisibility(View.GONE);
                iv_addsubscription.setVisibility(View.GONE);

                v_linetab1.setVisibility(View.VISIBLE);
                v_linetab2.setVisibility(View.INVISIBLE);
                Calendar calendar = Calendar.getInstance();
                // get a date to represent "today"
                Date today = calendar.getTime();
                String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
                getMyProduct(defaultDate);


            }
        });

        tv_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_tab1.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_tab2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                recyclerViewmy.setVisibility(View.VISIBLE);
                rl_mycalander.setVisibility(View.GONE);
                collapsibleCalendar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                tv_addmore.setVisibility(View.GONE);
                rl_delivery.setVisibility(View.GONE);
                iv_addsubscription.setVisibility(View.VISIBLE);

                v_linetab1.setVisibility(View.INVISIBLE);
                v_linetab2.setVisibility(View.VISIBLE);

                getMySubscription();



            }
        });


    }



    private void InitUI() {

        mContext=MySubscription.this;
        rl_back=findViewById(R.id.rl_back);
        rl_addsubscription=findViewById(R.id.rl_addsubscription);
        recyclerViewmy=findViewById(R.id.recyclerviewmy);
        recyclerViewmy.setHasFixedSize(true);
        recyclerViewmy.setLayoutManager(new LinearLayoutManager(mContext));
        rl_mycalander=findViewById(R.id.rl_mycalander);
        tv_tab1 = findViewById(R.id.tv_tab1);
        tv_tab2 =findViewById(R.id.tv_tab2);
        tv_delivery=findViewById(R.id.tv_delivery);
        tv_totalpaidval=findViewById(R.id.tv_totalpaidval);
        rl_delivery=findViewById(R.id.rl_delivery);

        v_linetab1 = findViewById(R.id.v_linetab1);
        v_linetab2 = findViewById(R.id.v_linetab2);
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);*/
        iv_addsubscription=findViewById(R.id.iv_addsubscription);
        rl_noitems=findViewById(R.id.rl_noitems);
        rl_noorder=findViewById(R.id.rl_nosubs);
        iv_addsubscription.setVisibility(View.GONE);

        tv_text3=findViewById(R.id.tv_text3);
        tv_text1=findViewById(R.id.tv_text1);
        iv_item=findViewById(R.id.iv_item);
        tv_subsperiod=findViewById(R.id.tv_subsperiod);
        rl_carthome=findViewById(R.id.rl_carthome);
        ll_tab=findViewById(R.id.ll_tab11);
        tv_title=findViewById(R.id.tv_title);
        tv_subs_type=findViewById(R.id.tv_subs_type);
        walletamount=findViewById(R.id.walletamount);
        /*String balance=WalletActivity.balance;
        walletamount.setText(balance);*/
        rl_noitems=findViewById(R.id.rl_noitems);
        rl_back=findViewById(R.id.rl_back);
        rl_addsubscription=findViewById(R.id.rl_addsubscription);
        scroll_View=findViewById(R.id.scroll_View);

        tv_date = findViewById(R.id.tv_date);
        iv_addsubscription=findViewById(R.id.iv_addsubscription);
        tv_productname = findViewById(R.id.tv_productname);
        tv_stock = findViewById(R.id.tv_stock);
        tv_mrpval = findViewById(R.id.tv_mrpval);
        tv_price = findViewById(R.id.tv_price);
        imageView = findViewById(R.id.imageView);
        tv_pack = findViewById(R.id.tv_pack);
        tv_minqty = findViewById(R.id.tv_minqty);
        tv_itempriceval = findViewById(R.id.tv_itempriceval);
        tv_prodid = findViewById(R.id.tv_prodid);
        tv_orderqtyval = findViewById(R.id.tv_orderqtyval);
        tv_inr = findViewById(R.id.tv_inr);
        tv_boxsize = findViewById(R.id.tv_boxsize);
        iv_rx = findViewById(R.id.iv_rx);
        tv_mrp = findViewById(R.id.tv_mrp);
        tv_orderqtyunit = findViewById(R.id.tv_orderqtyunit);
        tv_discount = findViewById(R.id.tv_discount);
        tv_mrpval.setBackgroundResource(R.drawable.strike_through);
        rl_subs_details=findViewById(R.id.rl_subs_details);
        rl_next=findViewById(R.id.rl_next);
        tv_remove=findViewById(R.id.tv_remove);
        tv_addmore=findViewById(R.id.tv_addmore);
        tv_text2=findViewById(R.id.tv_text2);

        recyclerView=findViewById(R.id.recyclerview5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setVisibility(View.GONE);
        tv_addmore.setVisibility(View.GONE);
        rl_delivery.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            addressID=extras.getString("addressID");
            //subscription_id=extras.getString("subscription_id");
        }

        collapsibleCalendar = findViewById(R.id.collapsibleCalendarView);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        bottomnavview.setSelectedItemId(R.id.home_subscription);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;
        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);

        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        //String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "0");
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){
                    case R.id.category:
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

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {

            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                SelectedDate=day.getYear() + "-" + (day.getMonth() + 1) + "-" + day.getDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
                getMyProduct(SelectedDate);
                Log.e(TAG, "onDaySelect: ondayselect"+deliverymode );
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {
                
            }

            @Override
            public void onMonthChange() {
            }

            @Override
            public void onWeekChange(int i) {
            }
        });

        //getMySubscription();

        Calendar calendar = Calendar.getInstance();
        // get a date to represent "today"
        Date today = calendar.getTime();
        String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
        getMyProduct(defaultDate);

    }


    private void getMyProduct(String SelectedDate) {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        ConfirmSubscriptionRequest confirmSubscriptionRequest=new ConfirmSubscriptionRequest();
        confirmSubscriptionRequest.setSubscriptionId("1");
        confirmSubscriptionRequest.setSubscriptionDate(SelectedDate);
        //confirmSubscriptionRequest.setAddressId(addressID);

        try{
            Call<DateSubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail_bydate(confirmSubscriptionRequest);

            call.enqueue(new Callback<DateSubscriptionDetailResponse>() {
                @Override
                public void onResponse(Call<DateSubscriptionDetailResponse> call, Response<DateSubscriptionDetailResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();

                        Utilities.dismissDialog();
                        deliveryStatus=response.body().getSubscriptionDelivaryStatus();


                        if(response.body().getBasketData()!=null && response.body().getBasketCount()!=0){
                            if(response.body().getMessage().equalsIgnoreCase("Selected invalid date, Subscription completed")){
                                if(deliverymode.equalsIgnoreCase("Custom")) {
                                    //iv_addsubscription.setVisibility(View.GONE);
                                    ll_tab.setVisibility(View.GONE);
                                    rl_noitems.setVisibility(View.VISIBLE);
                                    iv_item.setVisibility(View.GONE);
                                    tv_text1.setVisibility(View.GONE);
                                    tv_text3.setVisibility(View.GONE);
                                    tv_text2.setText("Add Product");
                                    rl_next.setVisibility(View.GONE);
                                    message = "subscription completed";
                                    Log.e(TAG, "onResponse: if condition-invaliddate and custom" );
                                    //Log.e(TAG, "onResponse: ", );
                                }else{
                                    //iv_addsubscription.setVisibility(View.GONE);
                                    ll_tab.setVisibility(View.GONE);
                                    rl_noitems.setVisibility(View.VISIBLE);
                                    iv_item.setVisibility(View.GONE);
                                    tv_text1.setVisibility(View.GONE);
                                    tv_text3.setVisibility(View.GONE);
                                    //regularBasket();
                                    rl_next.setVisibility(View.GONE);
                                    Log.e(TAG, "onResponse: else condition-invaliddate and custom" );
                                }
                            } else if(response.body().getMessage().equalsIgnoreCase("you have not confirmed your subscription yet, please confirm")){
                                //iv_addsubscription.setVisibility(View.VISIBLE);
                                collapsibleCalendar.setVisibility(View.GONE);
                                scroll_View.setVisibility(View.GONE);
                                ll_tab.setVisibility(View.GONE);
                                rl_noorder.setVisibility(View.VISIBLE);
                                //tv_text2.setText("Add Subscription Plan");
                                rl_next.setVisibility(View.GONE);
                                message="not confirmed";
                                Log.e(TAG, "onResponse: elseif condition-not confirmed" );
                            }

                            else{
                                //iv_addsubscription.setVisibility(View.GONE);
                                collapsibleCalendar.setVisibility(View.VISIBLE);
                                scroll_View.setVisibility(View.VISIBLE);
                                ll_tab.setVisibility(View.GONE);
                                rl_noorder.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                tv_addmore.setVisibility(View.VISIBLE);
                                //tv_text2.setText("Add Subscription Plan");
                                rl_next.setVisibility(View.VISIBLE);
                                Log.e(TAG, "onResponse: else condition-valid date" );
                            }}
                        else{
                            //iv_addsubscription.setVisibility(View.GONE);
                            collapsibleCalendar.setVisibility(View.VISIBLE);
                            scroll_View.setVisibility(View.VISIBLE);
                            ll_tab.setVisibility(View.GONE);
                            rl_noorder.setVisibility(View.VISIBLE);
                            tv_addmore.setVisibility(View.GONE);
                            //tv_text2.setText("Add Subscription Plan");
                            rl_next.setVisibility(View.GONE);
                            Log.e(TAG, "onResponse: else condition-valid date" );
                        }

                        if(deliveryStatus!=null && deliveryStatus.equalsIgnoreCase("Deliverd")){
                            rl_next.setVisibility(View.GONE);
                            rl_delivery.setVisibility(View.VISIBLE);
                            tv_totalpaidval.setVisibility(View.VISIBLE);
                            tv_delivery.setText("Delivered");
                            tv_addmore.setVisibility(View.GONE);
                            tv_delivery.setTextColor(getResources().getColor(R.color.colorYellow));
                            rl_delivery.setEnabled(false);
                            //tv_delivery.setBackgroundResource(R.drawable.button_selector);
                            //rl_next.setBackgroundResource(R.drawable.bg_corners_border_blue);
                        }
                        else {
                            tv_addmore.setText("Add New Product");
                            tv_addmore.setTextColor(Color.parseColor("#FFF100"));
                            tv_addmore.setEnabled(true);
                            tv_addmore.setBackgroundResource(R.drawable.button_selector);
                        }

                        if(deliverymode.equalsIgnoreCase("Regular")){
                            if(endDate==SelectedDate){
                                //extenddate();
                            }

                        }

                        basketList = new ArrayList<>();
                        basketDetailsAdaptor=new BasketDetailsAdaptor(mContext,basketList);

                        //tv_itemcount.setText("Item Count : "+response.body().getBasketCount());
                        List<BasketDatum> mListData=response.body().getBasketData();
                        if (mListData != null && mListData.size() > 0) {
                            subscription_id=String.valueOf(response.body().getBasketData().get(0).getSubscriptionId());
                            basketList.addAll(mListData);
                        } else {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //orderAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(basketDetailsAdaptor);
                        recyclerViewmy.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();

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


    private void getMySubscription() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try{
            Call<SubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail();
            call.enqueue(new Callback<SubscriptionDetailResponse>() {
                @Override
                public void onResponse(Call<SubscriptionDetailResponse> call, Response<SubscriptionDetailResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();

                        /*if (response.body().getMessage().equalsIgnoreCase("Subscription Details Not Found")) {
                            rl_noitems.setVisibility(View.VISIBLE);
                        } else {*/


                            subscription = new ArrayList<>();
                            mySubscriptionAdaptor = new MySubscriptionAdaptor(mContext, subscription);

                            List<SubscriptionDatum> mListData = response.body().getSubscription();

                            if (mListData != null && mListData.size() > 0) {
                                int last=response.body().getSubscription().size();
                                Subscription_status=response.body().getSubscription().get(last-1).getSubscriptionPaymentStatus();
                                subscription.addAll(mListData);
                                rl_noitems.setVisibility(View.GONE);
                                //ma_headerq.setVisibility(View.GONE);
                            } else {
                                try {
                                    rl_noitems.setVisibility(View.VISIBLE);
                                    //ma_headerq.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            //orderAdapter.notifyDataSetChanged();
                            recyclerViewmy.setAdapter(mySubscriptionAdaptor);
                            recyclerViewmy.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true));
                            recyclerViewmy.setNestedScrollingEnabled(false);
                            Utilities.dismissDialog();
                        }
                    //}
                    else{
                            Utilities.dismissDialog();
                        }

                }

                @Override
                public void onFailure(Call<SubscriptionDetailResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}