package com.advira.advirafarm.buyer.ui.myaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.order.OrderDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.subscrption.AddSubscription;
import com.advira.advirafarm.buyer.ui.subscrption.DailyBasketProductList;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.ui.subscrption.SubscriptionDetailsActivity;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.BasketDetailsAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.AddsubscriptionResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DateSubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionData;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionRequest;
import com.advira.advirafarm.buyer.ui.wallet.api.MywalletpassbookResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.paytm.pgsdk.easypay.OnSwipeTouchListener;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SubscriptionActivity extends AppCompatActivity implements IConsts {

    RelativeLayout rl_carthome,rl_back,rl_addsubscription,rl_subs_details,rl_noitems,rl_next,ll_tab;
    ScrollView scroll_View;
    ImageView iv_addsubscription,iv_item;
    TextView tv_date, tv_remove, tv_addmore,tv_title,tv_subs_type,walletamount, tv_text1,tv_text3;
    CollapsibleCalendar collapsibleCalendar;
    RecyclerView recyclerView;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;
    private Context mContext;
    public  static String subscription_id="";
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

    BasketDetailsAdaptor basketDetailsAdaptor;

    //SubscriptionPreviewAdaptor subscriptionPreviewAdaptor;
    private List<BasketDatum> basketList;

    TextView tv_productname, tv_stock,tv_text2,tv_subsperiod;
    ImageView imageView, iv_rx;
    TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
            tv_orderqtyval, tv_prodid, tv_inr, tv_boxsize, tv_mrp, tv_orderqtyunit,tv_discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Init();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, MainActivityNav.class);
                startActivity(i);

            }
        });

        iv_addsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, AddSubscription.class);
                i.putExtra("selectedDate",SelectedDate);
                i.putExtra("message",message);
                startActivity(i);

            }
        });

        /*tv_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, AddSubscription.class);
                i.putExtra("selectedDate",SelectedDate);
                i.putExtra("message",message);
                startActivity(i);

            }
        });*/

        rl_subs_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, SubscriptionDetailsActivity.class);
                i.putExtra("addressID",addressID);
                startActivity(i);
            }
        });

        tv_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, DailyBasketProductList.class);
                i.putExtra("subscription_id",subscription_id);
                i.putExtra("SelectedDate",SelectedDate);
                startActivity(i);
            }
        });

        tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, SubscriptionDetailsActivity.class);
                i.putExtra("subscription_id",subscription_id);
                i.putExtra("SelectedDate",SelectedDate);
                startActivity(i);
            }
        });
        tv_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SubscriptionActivity.this, DailyBasketProductList.class);
                i.putExtra("subscription_id",subscription_id);
                i.putExtra("SelectedDate",SelectedDate);
                startActivity(i);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Init() {

        mContext = SubscriptionActivity.this;

        tv_text3=findViewById(R.id.tv_text3);
        tv_text1=findViewById(R.id.tv_text1);
        iv_item=findViewById(R.id.iv_item);
        tv_subsperiod=findViewById(R.id.tv_subsperiod);
        rl_carthome=findViewById(R.id.rl_carthome);
        ll_tab=findViewById(R.id.ll_tab);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            addressID=extras.getString("addressID");
            subscription_id=extras.getString("subscription_id");
        }

        collapsibleCalendar = findViewById(R.id.collapsibleCalendarView);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        /*scroll_View.setVisibility(View.GONE);
        rl_next.setVisibility(View.GONE);*/

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
                            startActivity(new Intent(getApplicationContext(),CartActivity.class));
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
                getBasketDetailsViaDate(SelectedDate);
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

        Calendar calendar = Calendar.getInstance();
        // get a date to represent "today"
        Date today = calendar.getTime();
        String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
        getBasketDetailsViaDate(defaultDate);
    }

    private void getBasketDetails() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        /*ConfirmSubscriptionRequest confirmSubscriptionRequest=new ConfirmSubscriptionRequest();
        confirmSubscriptionRequest.setSubscriptionId(subscription_id);
        confirmSubscriptionRequest.setAddressId(addressID);*/

        try{
           Call<SubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail();

           call.enqueue(new Callback<SubscriptionDetailResponse>() {
               @Override
               public void onResponse(Call<SubscriptionDetailResponse> call, Response<SubscriptionDetailResponse> response) {
                   if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                       for(int i=0;i<response.body().getSubscription().size();i++) {
                           String subsID = String.valueOf(response.body().getSubscription().get(i).getId());
                           subscriptioncheck = response.body().getSubscription().get(i).getSubscriptionPaymentStatus();
                           startDate = response.body().getSubscription().get(i).getSubscriptionStartDate();
                           endDate = response.body().getSubscription().get(i).getSubscriptionEndDate();
                           deliverymode = response.body().getSubscription().get(i).getSubscriptionType();

                           tv_subsperiod.setText("(" + formatDate(startDate) + "-" + formatDate(endDate) + ")");

                           Log.e(TAG, "Init: getbasketdetails" + subscriptioncheck);
                           String id = String.valueOf(response.body().getSubscription().get(i).getId());
                           SharedPrefUtil.setSubscriptionID(mContext, SHARED_PREF_SubscriptionID, id);
                           tv_subs_type.setText(response.body().getSubscription().get(i).getSubscriptionType());
                       }


                       if(response.body().getSubscription()!=null){
                           if(subscriptioncheck.equalsIgnoreCase("not-confirmed"))
                           {
                               scroll_View.setVisibility(View.GONE);
                               rl_next.setVisibility(View.GONE);
                               rl_subs_details.setVisibility(View.GONE);
                               rl_noitems.setVisibility(View.VISIBLE);
                               collapsibleCalendar.setVisibility(View.GONE);
                               iv_addsubscription.setVisibility(View.VISIBLE);
                               ll_tab.setVisibility(View.GONE);
                               Log.e(TAG, "onResponse: if condition-notconfiremed and null subscription" );

                           }/*else if(subscriptioncheck.equalsIgnoreCase("in-force"))
                           {
                               scroll_View.setVisibility(View.GONE);
                               rl_next.setVisibility(View.GONE);
                               rl_subs_details.setVisibility(View.GONE);
                               rl_noitems.setVisibility(View.VISIBLE);
                               collapsibleCalendar.setVisibility(View.GONE);
                               iv_addsubscription.setVisibility(View.VISIBLE);
                               ll_tab.setVisibility(View.GONE);

                           }*/
                           else{
                               scroll_View.setVisibility(View.VISIBLE);
                               collapsibleCalendar.setVisibility(View.VISIBLE);
                               rl_subs_details.setVisibility(View.GONE);
                               rl_noitems.setVisibility(View.GONE);
                               iv_addsubscription.setVisibility(View.GONE);//Gone-visible
                               ll_tab.setVisibility(View.GONE);
                               Log.e(TAG, "onResponse: else condition-notconfiremed and null subscription" );
                           }
                           //Subscription Details Not Found
                       }
                       else{
                           /*scroll_View.setVisibility(View.VISIBLE);
                           collapsibleCalendar.setVisibility(View.VISIBLE);
                           rl_subs_details.setVisibility(View.GONE);
                           rl_noitems.setVisibility(View.GONE);
                           iv_addsubscription.setVisibility(View.GONE);
                           ll_tab.setVisibility(View.VISIBLE);*/
                           Log.e(TAG, "onResponse: else condition null subscription" );
                       }

                       //iv_addsubscription.setVisibility(View.GONE);
                       Utilities.dismissDialog();

                   }
                   else{
                       Utilities.dismissDialog();
                   }
               }

               @Override
               public void onFailure(Call<SubscriptionDetailResponse> call, Throwable t) {
                   Utilities.dismissDialog();
                   Log.e(TAG, "onResponse: subscriptiononFAILURE"+subscriptioncheck );
                   scroll_View.setVisibility(View.GONE);
                   rl_subs_details.setVisibility(View.GONE);
                   rl_noitems.setVisibility(View.VISIBLE);
                   collapsibleCalendar.setVisibility(View.GONE);
                   rl_next.setVisibility(View.GONE);
               }
           });
            Utilities.dismissDialog();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regularBasket() {
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
                        Utilities.dismissDialog();
                        basketList = new ArrayList<>();
                        basketDetailsAdaptor=new BasketDetailsAdaptor(mContext,basketList);

                        for(int i=0;i<response.body().getSubscription().size();i++) {
                            //tv_itemcount.setText("Item Count : "+response.body().getBasketCount());
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
                        Utilities.dismissDialog();


                    }
                    else{
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<SubscriptionDetailResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
            Utilities.dismissDialog();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBasketDetailsViaDate(String SelectedDate) {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        ConfirmSubscriptionRequest confirmSubscriptionRequest=new ConfirmSubscriptionRequest();
        confirmSubscriptionRequest.setSubscriptionId("12");
        confirmSubscriptionRequest.setSubscriptionDate(SelectedDate);
        //confirmSubscriptionRequest.setAddressId(addressID);

        try{
            Call<DateSubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail_bydate(confirmSubscriptionRequest);

            call.enqueue(new Callback<DateSubscriptionDetailResponse>() {
                @Override
                public void onResponse(Call<DateSubscriptionDetailResponse> call, Response<DateSubscriptionDetailResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        deliveryStatus=response.body().getSubscriptionDelivaryStatus();

                        if(response.body().getMessage().equalsIgnoreCase("Selected invalid date, Subscription completed")){
                            if(deliverymode.equalsIgnoreCase("Custom")) {
                                iv_addsubscription.setVisibility(View.GONE);
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
                                iv_addsubscription.setVisibility(View.GONE);
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
                            iv_addsubscription.setVisibility(View.VISIBLE);
                            collapsibleCalendar.setVisibility(View.GONE);
                            scroll_View.setVisibility(View.GONE);
                            ll_tab.setVisibility(View.GONE);
                            rl_noitems.setVisibility(View.VISIBLE);
                            tv_text2.setText("Add Subscription Plan");
                            rl_next.setVisibility(View.GONE);
                            message="not confirmed";
                            Log.e(TAG, "onResponse: elseif condition-not confirmed" );
                        }

                        else{
                            iv_addsubscription.setVisibility(View.GONE);
                            collapsibleCalendar.setVisibility(View.VISIBLE);
                            scroll_View.setVisibility(View.VISIBLE);
                            ll_tab.setVisibility(View.GONE);
                            rl_noitems.setVisibility(View.GONE);
                            tv_text2.setText("Add Subscription Plan");
                            rl_next.setVisibility(View.VISIBLE);
                            Log.e(TAG, "onResponse: else condition-valid date" );
                        }

                        if(deliveryStatus!=null && deliveryStatus.equalsIgnoreCase("Deliverd")){
                            tv_addmore.setText("Delivered");
                            tv_addmore.setTextColor(Color.parseColor("#2A882D"));
                            tv_addmore.setEnabled(false);
                            tv_addmore.setBackgroundResource(R.drawable.editbox_selector);
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
                                extenddate();
                            }

                        }


                        //iv_addsubscription.setVisibility(View.GONE);
                        //rl_addsubscription.setVisibility(View.GONE);
                        //tv_pack.setText("Item Count : "+response.body().getBasketdataCount());
                        //tv_orderqtyval.setText("Start Date : "+response.body().getSubscription().getSubscriptionStartDate());
                        basketList = new ArrayList<>();
                        basketDetailsAdaptor=new BasketDetailsAdaptor(mContext,basketList);

                        //tv_itemcount.setText("Item Count : "+response.body().getBasketCount());
                        List<BasketDatum> mListData=response.body().getBasketData();
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

    private void extenddate() {
            Utilities.showLoading(mContext);

            String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
            //String subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

                Calendar calendar=Calendar.getInstance();
                // get a date to represent "today"
                Date today = calendar.getTime();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tomorrow = calendar.getTime();
                String renewstartdate= DateFormat.format("dd-MM-yyyy", tomorrow).toString();
                calendar.add(Calendar.MONTH,1);
                Date date3=calendar.getTime();
                String renewenddate = DateFormat.format("dd-MM-yyyy", date3).toString();


            UpdatesubscriptionRequest updatesubscriptionRequest=new UpdatesubscriptionRequest();
            updatesubscriptionRequest.setSubscriptionId(subscription_id);
            updatesubscriptionRequest.setSubscriptionType("Regular");
            updatesubscriptionRequest.setFromDate(renewstartdate);
            updatesubscriptionRequest.setToDate(renewenddate);

            Gson gson = new Gson();
            String vakk = gson.toJson(updatesubscriptionRequest).toString();
            Log.e(TAG, "addSubscription: "+vakk );

            try {
                Call<AddsubscriptionResponse> call=RetrofitUrlConnection.loadJSON(token).updatesubscription(updatesubscriptionRequest);

                call.enqueue(new Callback<AddsubscriptionResponse>() {
                    @Override
                    public void onResponse(Call<AddsubscriptionResponse> call, Response<AddsubscriptionResponse> response) {
                        if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                            Log.e(TAG, "onResponse: Search"+response.body().getStatus());
                            Utilities.dismissDialog();
                        /*String Id=String.valueOf(response.body().getSubscriptionDetails().get(0).getId());
                        SharedPrefUtil.setSubscriptionID(mContext,SHARED_PREF_SubscriptionID,Id);*/
                            //DairyBasketAdator.subscription_id=response.body().getSubscriptionDetails().get(0).getSubscriptionId();
                            /*recyclerView.setVisibility(View.VISIBLE);
                            //cv_tab.setVisibility(View.GONE);
                            rl_gonext.setVisibility(View.GONE);
                            rl_next.setVisibility(View.VISIBLE);
                            ProductListRequest();*/
                        }
                        else{
                            Utilities.dismissDialog();
                            /*AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AddSubscription.this);
                            alert.setTitle("Alert!!");
                            alert.setMessage("End Date must be greater than start date.");
                            alert.setPositiveButton("OK",null);
                            alert.show();*/
                        }
                    }

                    @Override
                    public void onFailure(Call<AddsubscriptionResponse> call, Throwable t) {
                        Utilities.dismissDialog();
                        Log.e(TAG, "onFailure: APIResponse"+"Something went wrong." );
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }



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
                        Utilities.dismissDialog();

                        balance=response.body().getWalletBalance();
                        Log.e(TAG, "onResponse: wallet123"+balance );
                        walletamount.setText("₹ "+balance);
                        int checkbalance=Integer.valueOf(balance);

                        if(checkbalance<=-250){
                            iv_addsubscription.setVisibility(View.GONE);
                            ll_tab.setVisibility(View.GONE);
                            rl_noitems.setVisibility(View.VISIBLE);
                            tv_text2.setText("Recharge your Wallet");
                            rl_next.setVisibility(View.GONE);

                        }else{
                            iv_addsubscription.setVisibility(View.GONE);
                            ll_tab.setVisibility(View.GONE);
                            rl_noitems.setVisibility(View.GONE);
                            tv_text2.setText("Recharge your Wallet");
                            rl_next.setVisibility(View.VISIBLE);
                        }
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

    private String formatDate(String dateString) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM, yyyy");

        Date d = null;
        try {
            d = input.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);

        return formatted;
    }


}