package com.advira.advirafarm.buyer.ui.myaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.MainActivity;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.MemberBenefitsAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.MemberfaqAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.SliderAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.api.CancelmembershipResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.MemberPlanResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.Member_Plan;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipFaq;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipPrice;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.order.OrderCancellationActivity;
import com.advira.advirafarm.buyer.ui.order.OrderDetailsActivity;
import com.advira.advirafarm.buyer.ui.order.adapter.MembershipListAdaptor;
import com.advira.advirafarm.buyer.ui.order.api.MemberDatum;
import com.advira.advirafarm.buyer.ui.order.api.OrderListResponse_v2;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayPayment;
import com.advira.advirafarm.buyer.ui.product.adapter.CategoryListHeaderAdapter;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.splash.SlideActivity;
import com.advira.advirafarm.buyer.ui.splash.adapter.SlideViewPagerAdapter;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_CancelTime;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_EndDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartTime;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PaymentCheck;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfileMode;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfilePic;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserActive;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserName;

public class MembershipActivity extends AppCompatActivity {

    public ViewPager viewpager;
    SliderAdapter sliderAdapter;

    private Context mContext;
    String membershipName="";

    MemberBenefitsAdapter memberBenefitsAdapter;
    MemberfaqAdapter memberfaqAdapter;

    private List<MembershipPrice> orderList;
    private List<MembershipFaq> orderList1;

    private RecyclerView benefitrecyclerView,faqrecyclerView;
    TextView tv_content1,tv_footertotal,tv_footertotalitem, tv_buyplan,text;
    RelativeLayout btn_buynow,btn_cancel,btn_renew,rl_content2;
    ImageView image_frame;
    TextView tv_membershipdes,tv_expiry,tv_username,tv_tac,tv_startdate;
    RelativeLayout rl_email, rl_back,rl_membershipAds;
    public CircleImageView profile_image;

    public static String planname="";

    public static BottomNavigationView bottomnavview;

    String memstartTime,memcancelTime,cancelhour;
    String profilemode="";

    List<Integer> color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);



        /*Calendar calendar = Calendar.getInstance();

        // get a date to represent "today"
        Date today = calendar.getTime();
        startTime=DateFormat.format("dd-MM-yyyy hh:mm:ss", today).toString();
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        // now get "tomorrow"
        Date tomorrow = calendar.getTime();
        cancelTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", tomorrow).toString();
        Log.e(TAG, "Init: memberShip_101"+today+"\t"+cancelTime );*/

        Init();

        //benefitrecyclerView=findViewById(R.id.rv_benefit);


        color = new ArrayList<>();

        color.add(R.layout.fragment_membership_benefitsone);
        color.add(R.layout.fragment_membership_benefitssecond);
        color.add(R.layout.fragment_membership_benefitthird);

        viewpager = findViewById(R.id.viewpager);

        CircleIndicator indicator = findViewById(R.id.indicator);

        sliderAdapter = new SliderAdapter(getSupportFragmentManager());

        viewpager.setAdapter(sliderAdapter);

        indicator.setViewPager(viewpager);

        sliderAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        tv_buyplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment;
                bottomSheetDialogFragment = MembershipFragment.newInstance("Bottom Sheet Dialog");
                bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.getTag());

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Alert !! ");

                //Setting message manually and performing action on button click
                builder.setMessage("Do You want to cancel Your Membership. Are you sure ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CancelMembership();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

            }
        });


        tv_tac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, WebViewActivity.class);
                i.putExtra("header","Terms & Conditions");
                i.putExtra("url","https://www.advira.in/membership_program_terms.php");
                startActivity(i);
            }
        });

        PlanRequest();
    }

    private void Init() {


        mContext = MembershipActivity.this;
        faqrecyclerView=findViewById(R.id.rv_faq);
        benefitrecyclerView=findViewById(R.id.rv_benefit);
        tv_content1=findViewById(R.id.tv_content1);
        tv_footertotal=findViewById(R.id.tv_footertotal);
        tv_footertotalitem=findViewById(R.id.tv_footertotalitem);
        tv_buyplan=findViewById(R.id.tv_buyplan);
        image_frame=findViewById(R.id.image_frame);
        rl_email=findViewById(R.id.rl_email);
        tv_membershipdes=findViewById(R.id.tv_membershipdes);
        tv_startdate=findViewById(R.id.tv_startdate);
        tv_expiry=findViewById(R.id.tv_expiry);
        profile_image =findViewById(R.id.profile_image);
        tv_username=findViewById(R.id.tv_username);
        btn_buynow=findViewById(R.id.btn_buynow);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_renew=findViewById(R.id.btn_renew);
        rl_back=findViewById(R.id.rl_back);
        rl_content2=findViewById(R.id.rl_content2);
        tv_tac=findViewById(R.id.tv_tac);
        rl_membershipAds=findViewById(R.id.rl_membershipAds);

        //image_frame.setVisibility(View.GONE);
        rl_email.setVisibility(View.GONE);
        tv_membershipdes.setVisibility(View.GONE);
        tv_startdate.setVisibility(View.GONE);
        tv_expiry.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        btn_renew.setVisibility(View.GONE);

        membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");
        String profilepic = SharedPrefUtil.getUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
        String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        String StartDate=SharedPrefUtil.getMembershipStartDate(mContext,SHARED_PREF_MemberShip_StartDate,"");
        String ExpiryDate=SharedPrefUtil.getMembershipEndDate(mContext,SHARED_PREF_MemberShip_EndDate,"");
        memstartTime=SharedPrefUtil.getmembershipStartTime(mContext,SHARED_PREF_MemberShip_StartTime,"");
        cancelhour="24";

        Log.e(TAG, "Init: memberShip_MA"+memstartTime+"\t"+memcancelTime );


        if (profilepic.length() > 5) {
            Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).resize(500,0).error(R.drawable.image_not_available).into(profile_image);
        }

        if(membershipName!=null && membershipName.length()>0 ){
            //image_frame.setVisibility(View.VISIBLE);
            rl_email.setVisibility(View.GONE);
            tv_membershipdes.setText("Elite Green Member");
            tv_expiry.setVisibility(View.VISIBLE);
            tv_startdate.setVisibility(View.VISIBLE);
            tv_membershipdes.setVisibility(View.VISIBLE);
            tv_username.setText(name);
            tv_username.setVisibility(View.GONE);
            tv_startdate.setText("("+formatDate(StartDate)+" - "+formatDate(ExpiryDate)+")");
            tv_expiry.setText("Valid up to "+formatDate(ExpiryDate));
            tv_expiry.setVisibility(View.GONE);
            btn_buynow.setVisibility(View.GONE);


            double cacelminutes = Integer.valueOf(cancelhour) * 60;
            int diff = twoDatesBetweenTime(memstartTime);
            Log.e(TAG, "Init: diffrence"+"\n"+diff+"\n"+cacelminutes );
            if (diff > cacelminutes) {
                btn_cancel.setVisibility(View.GONE);
                btn_renew.setVisibility(View.GONE);
                btn_buynow.setVisibility(View.GONE);
            }
            else {
                btn_buynow.setVisibility(View.GONE);
                btn_renew.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
            }

            //tv_buyplan.setText("Renew Your Plan");
        }
        else{
            rl_email.setVisibility(View.GONE);
            tv_startdate.setVisibility(View.GONE);
            tv_membershipdes.setVisibility(View.GONE);
            rl_membershipAds.setVisibility(View.VISIBLE);
            tv_expiry.setVisibility(View.GONE);
            btn_buynow.setVisibility(View.VISIBLE);
        }



        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.setSelectedItemId(R.id.tokri);
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
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
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
                        startActivity(new Intent(getApplicationContext(), MySubscription.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_wallet:
                        startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                        overridePendingTransition(0,0);
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

    }


    private void PlanRequest() {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try{

            Call<MemberPlanResponse> call= RetrofitUrlConnection.loadJSON(token).memberplan();

            call.enqueue(new Callback<MemberPlanResponse>() {
                @Override
                public void onResponse(Call<MemberPlanResponse> call, Response<MemberPlanResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")){
                        Gson gson = new Gson();
                        String vakk = gson.toJson(response.body()).toString();
                        //String jjj=vakk;

                        Log.e(TAG, "onResponse: memberplan\n"+vakk);

                        //tv_content1.setText(response.body().getProductList().get(0).getMembershipBenefits());
                        tv_footertotal.setText("Rs."+response.body().getProductList().getMembershipPrices().get(0).getPrice()+"/-");
                        tv_footertotalitem.setText("For "+response.body().getProductList().getMembershipPrices().get(0).getDuration());


                        /*orderList = new ArrayList<>();
                        memberBenefitsAdapter = new MemberBenefitsAdapter(mContext, orderList);
                        List<MembershipPrice> mListData = response.body().getProductList().getMembershipPrices();
                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }
                        benefitrecyclerView.setAdapter(memberBenefitsAdapter);
                        benefitrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        //recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
                        benefitrecyclerView.setNestedScrollingEnabled(false);
                        benefitrecyclerView.setHasFixedSize(false);*/

                        orderList1=new ArrayList<>();
                        memberfaqAdapter=new MemberfaqAdapter(mContext,orderList1);
                        List<MembershipFaq> mListData1=response.body().getProductList().getMembershipFaq();
                        if(mListData1!=null && mListData1.size()>0){
                            orderList1.addAll(mListData1);
                        }
                        faqrecyclerView.setAdapter(memberfaqAdapter);
                        faqrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        faqrecyclerView.setNestedScrollingEnabled(false);
                        faqrecyclerView.setHasFixedSize(false);

                    }
                    else {
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                    Utilities.dismissDialog();
                }

                @Override
                public void onFailure(Call<MemberPlanResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                    Log.e(TAG, "onFailure: response123",t );
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CancelMembership() {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try {

            Call<CancelmembershipResponse> call = RetrofitUrlConnection.loadJSON(token).cancelmembership();

            call.enqueue(new Callback<CancelmembershipResponse>() {
                @Override
                public void onResponse(Call<CancelmembershipResponse> call, Response<CancelmembershipResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")){
                        btn_buynow.setVisibility(View.VISIBLE);
                        rl_membershipAds.setVisibility(View.VISIBLE);
                        tv_membershipdes.setVisibility(View.GONE);
                        tv_startdate.setVisibility(View.GONE);
                        tv_expiry.setVisibility(View.GONE);
                        tv_username.setVisibility(View.GONE);
                        btn_cancel.setVisibility(View.GONE);
                        MainActivityNav.iv_memimg.setVisibility(View.GONE);

                        btn_renew.setVisibility(View.GONE);

                        SharedPrefUtil.setmembershipStartTime(mContext,SHARED_PREF_MemberShip_StartTime,"0");
                        cancelhour="0";
                        SharedPrefUtil.setMembership(mContext,SHARED_PREF_MemberShip,"");
                        //SharedPrefUtil.setmembershipCancelTime(mContext,SHARED_PREF_MemberShip_CancelTime,cancelTime);


                        Utilities.dismissDialog();

                    }
                    else{
                        btn_buynow.setVisibility(View.GONE);
                        btn_cancel.setVisibility(View.VISIBLE);
                        btn_renew.setVisibility(View.GONE);

                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<CancelmembershipResponse> call, Throwable t) {

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

    public Integer twoDatesBetweenTime(String oldtime) {
        // TODO Auto-generated method stub
        int day = 0;
        int hh = 0;
        int mm = 0;

        int differencemin = 0;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
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

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MembershipActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewpager.getCurrentItem() == sliderAdapter.getCount() - 1) { //adapter is your custom ViewPager's adapter
                        viewpager.setCurrentItem(0);
                    } else {
                        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1, true);
                    }

                }
            });
        }
    }

    /*public void MembershipRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<OrderListResponse_v2> call = RetrofitUrlConnection.loadJSON(token).orderlist();

            call.enqueue(new Callback<OrderListResponse_v2>() {
                @Override
                public void onResponse(Call<OrderListResponse_v2> call, Response<OrderListResponse_v2> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        memstartTime=response.body().getMemberData().get(0).getCreatedAt();
                    } else {
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<OrderListResponse_v2> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }*/
}