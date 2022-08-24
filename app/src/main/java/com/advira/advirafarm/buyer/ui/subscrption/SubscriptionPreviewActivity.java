package com.advira.advirafarm.buyer.ui.subscrption;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressList;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.SubscriptionPreviewAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListResponse;
import com.advira.advirafarm.buyer.ui.wallet.api.MywalletpassbookResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_SubscriptionID;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserActive;

public class SubscriptionPreviewActivity extends AppCompatActivity {

    private Context mContext;
    public static LinearLayout ll_tab;
    RelativeLayout rl_startdate, rl_EndDate, rl_deliverymode;
    public static RelativeLayout rl_noitems,rl_content2;
    Button btn_shopnow;
    RelativeLayout ll_header;
    TextView tv_addid,tv_delivaddress,tv_clientname,tv_client_addresstype,tv_pinval,btn_change;
    public static TextView tv_dateval, tv_edateval,tv_placeorder, tv_deliverytypeval, tv_itemcount, tv_priceval, tv_deliveryval, tv_totalpaidval,tv_days,tv_daysval;
    public static RecyclerView recyclerView;
    private RelativeLayout rl_back, rl_search, rl_cart,rl_confirm;

    public static String endDate = "";
    public static String startDate = "";
    private String deliveryMode = "";
    public  static String subscription_id="";
    private String address_id="";
    long subscriptionDays;
    String setGrandTotalAmount = "";

    SubscriptionPreviewAdaptor subscriptionPreviewAdaptor;
    private List<BasketDatum> basketList;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    public static Double totalprice;
    String balance="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_preview);

        Init();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubscriptionPreviewActivity.this.finish();
            }
        });


        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(SubscriptionPreviewActivity.this, SearchActivity.class);
                i.setClass(SubscriptionPreviewActivity.this, Search_one.class);
                startActivity(i);
            }
        });

        rl_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubscriptionConfirmation();
            }
        });

        btn_shopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {
                    String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                        Intent i = new Intent(SubscriptionPreviewActivity.this, MySubscription.class);
                        startActivity(i);

                } else {
                    Utilities.showNetworkError(mContext);
                }


            }
        });

    }

    private void Init() {

        mContext = SubscriptionPreviewActivity.this;

        rl_startdate=findViewById(R.id.rl_startdate);
        rl_EndDate=findViewById(R.id.rl_EndDate);
        rl_deliverymode=findViewById(R.id.rl_deliverymode);
        ll_tab=findViewById(R.id.ll_tab);

        tv_dateval=findViewById(R.id.tv_dateval);
        tv_edateval=findViewById(R.id.tv_edateval);
        tv_deliverytypeval=findViewById(R.id.tv_deliverytypeval);
        tv_itemcount=findViewById(R.id.tv_itemcount);
        tv_priceval=findViewById(R.id.tv_priceval);
        tv_deliveryval=findViewById(R.id.tv_deliveryval);
        tv_totalpaidval=findViewById(R.id.tv_totalpaidval);
        tv_placeorder=findViewById(R.id.tv_placeorder);
        tv_days=findViewById(R.id.tv_days);
        tv_daysval=findViewById(R.id.tv_daysval);
        rl_content2=findViewById(R.id.rl_content2);


        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_confirm=findViewById(R.id.rl_confirm);
        rl_search.setVisibility(View.GONE);

        ll_header=findViewById(R.id.ll_header);
        tv_addid=findViewById(R.id.tv_addid);
        tv_delivaddress=findViewById(R.id.tv_delivaddress);
        tv_pinval=findViewById(R.id.tv_pinval);
        tv_client_addresstype=findViewById(R.id.tv_client_addresstype);
        tv_clientname=findViewById(R.id.tv_clientname);
        btn_change=findViewById(R.id.btn_change);
        rl_noitems=findViewById(R.id.rl_noitems);
        btn_shopnow=findViewById(R.id.btn_shopnow);


        //rl_cart = findViewById(R.id.rl_cart);
//        rl_cart.setVisibility(View.GONE);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            endDate = extras.getString("endDate");
            startDate = extras.getString("startDate");
            deliveryMode = extras.getString("deliveryMode");
            address_id=extras.getString("address_id");
        }

        tv_dateval.setText(startDate);
        tv_edateval.setText(endDate);
        tv_deliverytypeval.setText(deliveryMode);

        //subscriptionDays=Daybetween(startDate,endDate);


        //tv_daysval.setText(String.valueOf(subscriptionDays));
        //String totalprice=tv_priceval.getText().toString().replace("₹ ", "");
        //String grandTotal=String.valueOf(totalprice*subscriptionDays);
        //Log.e(TAG, "Init: Subs_days"+subscriptionDays+"\n"+totalprice );

        //tv_totalpaidval.setText(grandTotal);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.tokri);
        bottomnavview.setSelectedItemId(R.id.home_subscription);
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


        DailyBasketList();



    }

    private void SubscriptionConfirmation() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        ConfirmSubscriptionRequest confirmSubscriptionRequest=new ConfirmSubscriptionRequest();
        confirmSubscriptionRequest.setSubscriptionId(subscription_id);
        confirmSubscriptionRequest.setAddressId(address_id);


        try{

            Call<ConfirmSubscriptionResponse> call=RetrofitUrlConnection.loadJSON(token).confirmsubscription(confirmSubscriptionRequest);

            call.enqueue(new Callback<ConfirmSubscriptionResponse>() {
                @Override
                public void onResponse(Call<ConfirmSubscriptionResponse> call, Response<ConfirmSubscriptionResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        setGrandTotalAmount = tv_totalpaidval.getText().toString().replace("₹ ", "");
                        if(setGrandTotalAmount.equalsIgnoreCase("0")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage("Alert").setTitle("Empty Cart!!!");

                            //Setting message manually and performing action on button click
                            builder.setMessage("No item in the cart.Do you want to add item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Utilities.hideKeyboard(mContext);
                                            Intent i = new Intent();
                                            i.setClass(SubscriptionPreviewActivity.this, AddSubscription.class);
                                            startActivity(i);


                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                            Intent i = new Intent();
                                            i.setClass(SubscriptionPreviewActivity.this, MainActivityNav.class);
                                            startActivity(i);


                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Empty Cart!!");
                            alert.show();

                            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                        } else{

                            getWalletBalance();

                        }
                    }
                }

                @Override
                public void onFailure(Call<ConfirmSubscriptionResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void DailyBasketList() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        DailyBasketListRequest dailyBasketListRequest =new DailyBasketListRequest();
        dailyBasketListRequest.setSubscriptionId(subscription_id);
        try{
        Call<DailyBasketListResponse> call= RetrofitUrlConnection.loadJSON(token).getmydailybasket(dailyBasketListRequest);

        call.enqueue(new Callback<DailyBasketListResponse>() {
                @Override
                public void onResponse(Call<DailyBasketListResponse> call, Response<DailyBasketListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        if(response.body().getBasketCount().equals(0))
                        {
                            rl_noitems.setVisibility(View.VISIBLE);
                            ll_tab.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            rl_content2.setVisibility(View.GONE);
                        }else{
                            rl_noitems.setVisibility(View.GONE);
                            ll_tab.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            rl_content2.setVisibility(View.VISIBLE);
                        }
                        //Log.e(TAG, "onResponse: Search"+jjj );
                        Utilities.dismissDialog();

                        basketList = new ArrayList<>();
                        subscriptionPreviewAdaptor=new SubscriptionPreviewAdaptor(mContext,basketList);
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
                        recyclerView.setAdapter(subscriptionPreviewAdaptor);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();

                    }
                    else{
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketListResponse> call, Throwable t) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWalletBalance(){
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try{
            Call<MywalletpassbookResponse> call= RetrofitUrlConnection.loadJSON(token).mywalletpassbook();

            call.enqueue(new Callback<MywalletpassbookResponse>() {
                @Override
                public void onResponse(Call<MywalletpassbookResponse> call, Response<MywalletpassbookResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        balance=response.body().getWalletBalance();
                        double totalamt=Double.parseDouble(setGrandTotalAmount);
                        Log.e(TAG, "onResponse: wallet123"+balance );
                        if(balance.equalsIgnoreCase("0")){
                            Intent i = new Intent();
                            i.putExtra("addressID", address_id);
                            i.putExtra("GrandTotalAmount", setGrandTotalAmount);
                            i.setClass(SubscriptionPreviewActivity.this, CheckBalance.class);
                            startActivity(i);

                        }else{
                            if(totalamt>Integer.parseInt(balance)){
                                Intent i = new Intent();
                                i.putExtra("addressID", address_id);
                                i.putExtra("GrandTotalAmount", setGrandTotalAmount);
                                i.setClass(SubscriptionPreviewActivity.this, CheckBalance.class);
                                startActivity(i);
                            }else {
                                Intent i = new Intent();
                            /*i.putExtra("addressID", address_id);
                            i.putExtra("GrandTotalAmount", setGrandTotalAmount);*/
                                i.setClass(SubscriptionPreviewActivity.this, MainActivityNav.class);
                                startActivity(i);
                            }
                        }
                        //tvBalance.setText("₹ "+balance);
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

    public static int numDaysBetween(final long fromTime, final long toTime) {
        Calendar c = Calendar.getInstance();
        int result = 0;
        if (toTime <= fromTime) return result;

        c.setTimeInMillis(toTime);
        final int toYear = c.get(Calendar.YEAR);
        result += c.get(Calendar.DAY_OF_YEAR);

        c.setTimeInMillis(fromTime);
        result -= c.get(Calendar.DAY_OF_YEAR);

        while (c.get(Calendar.YEAR) < toYear) {
            result += c.getActualMaximum(Calendar.DAY_OF_YEAR);
            c.add(Calendar.YEAR, 1);
        }

        return result;
    }
    public long Daybetween(String date1,String date2)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date Date1 = null,Date2 = null;
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
    }

}