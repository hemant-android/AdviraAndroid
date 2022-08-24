package com.advira.advirafarm.buyer.ui.subscrption;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressList;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.DailyBasketAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.AddsubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.AddsubscriptionResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.Product_Basket;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionRequest;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

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

public class AddSubscription extends AppCompatActivity implements IConsts {

    RelativeLayout rl_startdate,rl_deliverymode,rl_back,rl_EndDate,rl_next;
    public static TextView tv_dateval,tv_edateval,tv_next,tv_itemcount, tv_priceval, tv_deliveryval, tv_totalpaidval;;
    public static RecyclerView recyclerView;
    CardView cv_tab;
    Spinner spn_deliveryval;
    RelativeLayout rl_gonext;
    LinearLayout ll_tab1;
    String deliveryMode="";
    String[] deliveryType = {"Choose Plan","Regular","Custom"};
    DailyBasketAdaptor dairyBasketAdator;
    public static List<Product_Basket> orderList;
    private Context mContext;
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    private RadioGroup rg_delimode;
    private RadioButton radioButton;
    private RadioButton rb_regular;
    private RadioButton rb_custom;

    String deliveryplan = "";
    String Id="";

    private String genderchk;

    String startdate = "";
    String enddate="";
    String selectedDate="";
    String subscription_id="";
    String newsubscription_id="";
    String message="";
    String subsID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);
        Init();


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        tv_dateval.setInputType(InputType.TYPE_NULL);
        tv_dateval.requestFocus();
        tv_dateval.setInputType(InputType.TYPE_NULL);



        rl_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerDialogTheme1();
                dialogfragment.show(getFragmentManager(), "Theme 1");

            }
        });

        tv_edateval.setInputType(InputType.TYPE_NULL);
        tv_edateval.requestFocus();
        tv_edateval.setInputType(InputType.TYPE_NULL);

        rl_EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerDialogTheme2();
                dialogfragment.show(getFragmentManager(), "Theme 1");

            }
        });


        spn_deliveryval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {

                    if(spn_deliveryval.getSelectedItem().toString().equalsIgnoreCase("Custom")){
                       rl_EndDate.setVisibility(View.VISIBLE);
                        ll_tab1.setVisibility(View.VISIBLE);
                       deliveryMode=spn_deliveryval.getSelectedItem().toString();
                    }
                    else{
                        rl_EndDate.setVisibility(View.GONE);
                        ll_tab1.setVisibility(View.GONE);
                        deliveryMode=spn_deliveryval.getSelectedItem().toString();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyBasketList();

                /*Intent i=new Intent();
                i.putExtra("startDate", startdate);
                i.putExtra("endDate", enddate);
                Log.e(TAG, "onClick: rl_next"+startdate+"  "+enddate );
                i.putExtra("deliveryMode", deliveryplan);
                i.putExtra("from", "AddSubscription");
                i.setClass(AddSubscription.this, ChooseAddressList.class);
                startActivity(i);*/
            }
        });

        rl_gonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addSubscription();
                if(message.equalsIgnoreCase("adaptor")){
                    updateSubscription();
                }else {
                    addSubscription();
                }
            }
        });

        rg_delimode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = rg_delimode.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);

                try{
                    deliveryplan = radioButton.getText().toString();
                    if(deliveryplan.equalsIgnoreCase("Custom")){
                        rl_EndDate.setVisibility(View.VISIBLE);
                        ll_tab1.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        //cv_tab.setVisibility(View.GONE);
                        rl_gonext.setVisibility(View.VISIBLE);
                        rl_next.setVisibility(View.GONE);

                    }else{
                        rl_EndDate.setVisibility(View.GONE);
                        ll_tab1.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        //cv_tab.setVisibility(View.GONE);
                        rl_gonext.setVisibility(View.VISIBLE);
                    }

                }
                catch (Exception ex)
                {

                }
            }
        });
    }


    private void Init() {

        mContext = AddSubscription.this;
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            selectedDate=extras.getString("selectedDate");
            subsID=extras.getString("subsID");
            message=extras.getString("message");
        }


        rl_startdate=findViewById(R.id.rl_startdate);
        rl_EndDate=findViewById(R.id.rl_EndDate);
        rl_deliverymode=findViewById(R.id.rl_deliverymode);
        //rl_deliverymode.setVisibility(View.GONE);

        rl_back=findViewById(R.id.rl_back);

        Calendar calendar = Calendar.getInstance();

        // get a date to represent "today"
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        startdate= DateFormat.format("dd MMM, yyyy", tomorrow).toString();
        tv_dateval=findViewById(R.id.tv_dateval);
        tv_dateval.setText(startdate);

        tv_edateval=findViewById(R.id.tv_edateval);
        rl_next=findViewById(R.id.rl_next);
        tv_next=findViewById(R.id.tv_next);
        rl_gonext=findViewById(R.id.rl_gonext);
        ll_tab1=findViewById(R.id.ll_tab1);
        spn_deliveryval=findViewById(R.id.spn_deliveryval);
        cv_tab=findViewById(R.id.cv_tab);

        rb_regular = findViewById(R.id.rb_regular);
        rb_custom = findViewById(R.id.rb_custom);
        rg_delimode = findViewById(R.id.rg_delimode);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, deliveryType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_deliveryval.setAdapter(adapter);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

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
        //ProductListRequest();
        //CheckSubscription();

    }

    protected void updateSubscription() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        Date date1=new Date(tv_dateval.getText().toString());
        Date date2=new Date(tv_edateval.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        enddate="";
        if(deliveryplan.equalsIgnoreCase("Custom")) {
            startdate = DateFormat.format("dd-MM-yyyy",date1).toString();
            enddate = DateFormat.format("dd-MM-yyyy",date2).toString();
        }else
        {
            Calendar calendar=Calendar.getInstance();
            // get a date to represent "today"
            Date today = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            //startdate= DateFormat.format("dd-MM-yyyy", date1).toString();
            startdate= DateFormat.format("dd-MM-yyyy", tomorrow).toString();
            calendar.add(Calendar.MONTH, 1);
            Date date3=calendar.getTime();
            enddate = DateFormat.format("dd-MM-yyyy", date3).toString();

        }

        UpdatesubscriptionRequest updatesubscriptionRequest=new UpdatesubscriptionRequest();
        updatesubscriptionRequest.setSubscriptionId(subscription_id);
        updatesubscriptionRequest.setSubscriptionType(deliveryplan);
        updatesubscriptionRequest.setFromDate(startdate);
        updatesubscriptionRequest.setToDate(enddate);

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
                        Id=String.valueOf(response.body().getSubscriptionDetails().get(0).getId());
                        SharedPrefUtil.setSubscriptionID(mContext,SHARED_PREF_SubscriptionID,Id);
                        //DairyBasketAdator.subscription_id=response.body().getSubscriptionDetails().get(0).getSubscriptionId();
                        recyclerView.setVisibility(View.VISIBLE);
                        //cv_tab.setVisibility(View.GONE);
                        rl_gonext.setVisibility(View.GONE);
                        rl_next.setVisibility(View.VISIBLE);
                        ProductListRequest();
                    }
                    else{
                        Utilities.dismissDialog();
                        AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AddSubscription.this);
                        alert.setTitle("Alert!!");
                        alert.setMessage("End Date must be greater than start date.");
                        alert.setPositiveButton("OK",null);
                        alert.show();
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

    private void addSubscription() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        Date date1=new Date(tv_dateval.getText().toString());
        Date date2=new Date(tv_edateval.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        enddate="";
        if(deliveryplan.equalsIgnoreCase("Custom")) {
            startdate = DateFormat.format("dd-MM-yyyy",date1).toString();
            enddate = DateFormat.format("dd-MM-yyyy",date2).toString();
        }else
        {
            Calendar calendar=Calendar.getInstance();
            // get a date to represent "today"
            Date today = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            startdate= DateFormat.format("dd-MM-yyyy", tomorrow).toString();
            calendar.add(Calendar.MONTH,1);
            Date date3=calendar.getTime();
            enddate = DateFormat.format("dd-MM-yyyy", date3).toString();

        }


        AddsubscriptionRequest addsubscriptionRequest=new AddsubscriptionRequest();
        addsubscriptionRequest.setSubscriptionType(deliveryplan);
        addsubscriptionRequest.setFromDate(startdate);
        addsubscriptionRequest.setToDate(enddate);

        Gson gson = new Gson();
        String vakk = gson.toJson(addsubscriptionRequest).toString();
        Log.e(TAG, "addSubscription: "+vakk );


        try{
            Call<AddsubscriptionResponse> call=RetrofitUrlConnection.loadJSON(token).addsubscription(addsubscriptionRequest);

            call.enqueue(new Callback<AddsubscriptionResponse>() {
                @Override
                public void onResponse(Call<AddsubscriptionResponse> call, Response<AddsubscriptionResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Log.e(TAG, "onResponse: Search"+response.body().getStatus());
                        Utilities.dismissDialog();
                        Id=String.valueOf(response.body().getSubscriptionDetails().get(0).getId());
                        SharedPrefUtil.setSubscriptionID(mContext,SHARED_PREF_SubscriptionID,Id);
                        //DairyBasketAdator.subscription_id=response.body().getSubscriptionDetails().get(0).getSubscriptionId();
                        recyclerView.setVisibility(View.VISIBLE);
                        //cv_tab.setVisibility(View.GONE);
                        rl_gonext.setVisibility(View.GONE);
                        rl_next.setVisibility(View.VISIBLE);
                        ProductListRequest();
                    }
                    else{
                        Utilities.dismissDialog();
                        AlertDialog.Builder alert = new android.app.AlertDialog.Builder(AddSubscription.this);
                        alert.setTitle("Alert!!");
                        alert.setMessage(response.body().getMessage());
                        alert.setPositiveButton("OK",null);
                        alert.show();
                    }
                }

                @Override
                public void onFailure(Call<AddsubscriptionResponse> call, Throwable t) {
                    /*android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(ProductDetailsActivityGuest.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Please select a valid quantity.");
                    alert.setPositiveButton("OK",null);
                    alert.show();*/
                    Log.e(TAG, "onFailure: APIResponse"+"Something went wrong." );
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void ProductListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<DailyBasketResponse> call= RetrofitUrlConnection.loadJSON(token).dailybasketproducts();

            call.enqueue(new Callback<DailyBasketResponse>() {
                @Override
                public void onResponse(Call<DailyBasketResponse> call, Response<DailyBasketResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Log.e(TAG, "onResponse: Search"+jjj );
                        Utilities.dismissDialog();

                        orderList = new ArrayList<>();
                        dairyBasketAdator = new DailyBasketAdaptor(mContext, orderList);

                        List<Product_Basket> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);
                            /*rl_noitems.setVisibility(View.GONE);
                            ma_headerq.setVisibility(View.GONE);*/
                        } else {
                            try {
                                /*rl_noitems.setVisibility(View.VISIBLE);
                                ma_headerq.setVisibility(View.GONE);*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //orderAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dairyBasketAdator);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();

                    } else {
                        Utilities.dismissDialog();

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });

        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    public static class DatePickerDialogTheme1 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);
            datepickerdialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()+24*60*60*1000);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, day);
            TextView tv_dateval = (TextView)getActivity().findViewById(R.id.tv_dateval);
            tv_dateval.setText(dateFormatter.format(newDate.getTime()));
            //tv_dateval.setText(day + " " + (month+1) + ":" + year);

        }
    }

    public static class DatePickerDialogTheme2 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);
            datepickerdialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()+24*60*60*1000);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, day);
            TextView tv_edateval = (TextView)getActivity().findViewById(R.id.tv_edateval);
            tv_edateval.setText(dateFormatter.format(newDate.getTime()));
            //tv_dateval.setText(day + " " + (month+1) + ":" + year);

        }
    }

    private void DailyBasketList() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");
        DailyBasketListRequest dailyBasketListRequest=new DailyBasketListRequest();
        dailyBasketListRequest.setSubscriptionId(Id);
        Log.e(TAG, "DailyBasketList: getmydailybasket"+subscription_id );

        try{
            Call<DailyBasketListResponse> call= RetrofitUrlConnection.loadJSON(token).getmydailybasket(dailyBasketListRequest);

            call.enqueue(new Callback<DailyBasketListResponse>() {
                @Override
                public void onResponse(Call<DailyBasketListResponse> call, Response<DailyBasketListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Integer Basketcount=response.body().getBasketCount();
                        if(Basketcount.equals(0)){
                            androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                            alert.setTitle("Alert!!");
                            alert.setMessage("Select at least one item.");
                            alert.setPositiveButton("OK",null);
                            alert.show();

                        }
                        else{
                            Intent i=new Intent();
                            i.putExtra("startDate", startdate);
                            i.putExtra("endDate", enddate);
                            Log.e(TAG, "onClick: rl_next"+startdate+"  "+enddate );
                            i.putExtra("deliveryMode", deliveryplan);
                            i.putExtra("from", "AddSubscription");
                            i.setClass(AddSubscription.this, ChooseAddressList.class);
                            startActivity(i);

                        }
                        /*if(response.body().getBasketCount().equals(0))
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
                        recyclerView.setNestedScrollingEnabled(false);*/
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


    @Override
    public void onRestart() {
        super.onRestart();
        //initUI();
    }

}