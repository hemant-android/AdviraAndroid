package com.advira.advirafarm.buyer.ui.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.MemberBenefitsAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.MemberfaqAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.SliderAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.api.CancelmembershipResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.MemberPlanResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipFaq;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipPrice;
import com.advira.advirafarm.buyer.ui.splash.SlideActivity;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MyMembershipFragment extends BaseContainerFragment implements IConsts {

    public ViewPager viewpager;
    SliderAdapter sliderAdapter;
    private View rootView;

    private Context mContext;
    String membershipName="";

    MemberBenefitsAdapter memberBenefitsAdapter;
    MemberfaqAdapter memberfaqAdapter;

    private List<MembershipPrice> orderList;
    private List<MembershipFaq> orderList1;

    private RecyclerView benefitrecyclerView,faqrecyclerView;
    TextView tv_content1,tv_footertotal,tv_footertotalitem, tv_buyplan;
    RelativeLayout btn_buynow,btn_cancel,btn_renew,rl_content2;
    ImageView image_frame;
    TextView tv_membershipdes,tv_expiry,tv_username,tv_tac;
    RelativeLayout rl_email, rl_back;
    public CircleImageView profile_image;

    public static String planname="";
    BottomSheetDialogFragment bottomSheetDialogFragment;

    String memstartTime,memcancelTime,cancelhour;

    List<Integer> color;

    public MyMembershipFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MyMembershipFragment newInstance() {
        MyMembershipFragment fragment = new MyMembershipFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView =  inflater.inflate(R.layout.fragment_my_membership, container, false);

            ButterKnife.bind(this, rootView);

            initUI();

            color = new ArrayList<>();

            color.add(R.layout.fragment_membership_benefitsone);
            color.add(R.layout.fragment_membership_benefitssecond);
            color.add(R.layout.fragment_membership_benefitthird);

            viewpager = rootView.findViewById(R.id.viewpager);

            CircleIndicator indicator = rootView.findViewById(R.id.indicator);

            sliderAdapter = new SliderAdapter(getParentFragmentManager());

            viewpager.setAdapter(sliderAdapter);

            indicator.setViewPager(viewpager);

            sliderAdapter.registerDataSetObserver(indicator.getDataSetObserver());

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);

            /*rl_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                }
            });*/

            tv_buyplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialogFragment.show(getParentFragmentManager(),bottomSheetDialogFragment.getTag());


                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CancelMembership();
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
        return rootView;
    }

    private void initUI() {

        mContext = getActivity();
        faqrecyclerView=rootView.findViewById(R.id.rv_faq);
        benefitrecyclerView=rootView.findViewById(R.id.rv_benefit);
        tv_content1=rootView.findViewById(R.id.tv_content1);
        tv_footertotal=rootView.findViewById(R.id.tv_footertotal);
        tv_footertotalitem=rootView.findViewById(R.id.tv_footertotalitem);
        tv_buyplan=rootView.findViewById(R.id.tv_buyplan);
        image_frame=rootView.findViewById(R.id.image_frame);
        rl_email=rootView.findViewById(R.id.rl_email);
        tv_membershipdes=rootView.findViewById(R.id.tv_membershipdes);
        tv_expiry=rootView.findViewById(R.id.tv_expiry);
        profile_image =rootView.findViewById(R.id.profile_image);
        tv_username=rootView.findViewById(R.id.tv_username);
        btn_buynow=rootView.findViewById(R.id.btn_buynow);
        btn_cancel=rootView.findViewById(R.id.btn_cancel);
        btn_renew=rootView.findViewById(R.id.btn_renew);
        rl_back=rootView.findViewById(R.id.rl_back);
        rl_content2=rootView.findViewById(R.id.rl_content2);
        tv_tac=rootView.findViewById(R.id.tv_tac);


        //image_frame.setVisibility(View.GONE);
        rl_email.setVisibility(View.GONE);
        tv_membershipdes.setVisibility(View.GONE);
        tv_expiry.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        btn_renew.setVisibility(View.GONE);

        membershipName= SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");
        String profilepic = SharedPrefUtil.getUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
        String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        //String StartDate=
        String ExpiryDate=SharedPrefUtil.getMembershipEndDate(mContext,SHARED_PREF_MemberShip_EndDate,"");
        memstartTime=SharedPrefUtil.getmembershipStartTime(mContext,SHARED_PREF_MemberShip_StartTime,"");
        cancelhour="24";

        Log.e(TAG, "Init: memberShip_101"+memstartTime+"\t"+memcancelTime );




        /*Calendar calendar = Calendar.getInstance();

        // get a date to represent "today"
        Date today = calendar.getTime();
        memstartTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", today).toString();
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        // now get "tomorrow"
        Date tomorrow = calendar.getTime();
        memcancelTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", tomorrow).toString();
        Log.e(TAG, "Init: memberShip_101"+today+"\t"+cancelTime );
*/


        if (profilepic.length() > 5) {
            Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).resize(500,0).error(R.drawable.image_not_available).into(profile_image);
        }

        if(membershipName!=null && membershipName.length()>0 ){
            //image_frame.setVisibility(View.VISIBLE);
            rl_email.setVisibility(View.GONE);
            tv_membershipdes.setVisibility(View.GONE);
            tv_expiry.setVisibility(View.VISIBLE);
            tv_username.setText("Hi, "+name);
            tv_expiry.setText("Valid up to "+formatDate(ExpiryDate));
            btn_buynow.setVisibility(View.GONE);

            double cacelminutes = Integer.valueOf(cancelhour) * 60;
            int diff = twoDatesBetweenTime(memstartTime);
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
            tv_membershipdes.setVisibility(View.GONE);
            tv_expiry.setVisibility(View.GONE);
            btn_buynow.setVisibility(View.VISIBLE);
        }

        bottomSheetDialogFragment = MembershipFragment.newInstance("Bottom Sheet Dialog");

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
                        btn_cancel.setVisibility(View.GONE);
                        btn_renew.setVisibility(View.GONE);

                        /*Calendar calendar = Calendar.getInstance();

                        // get a date to represent "today"
                        Date today = calendar.getTime();
                        startTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", today).toString();
                        calendar.add(Calendar.HOUR_OF_DAY, 1);

                        // now get "tomorrow"
                        Date tomorrow = calendar.getTime();
                        cancelTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", tomorrow).toString();
                        Log.e(TAG, "Init: memberShip_101"+today+"\t"+cancelTime );*/

                        SharedPrefUtil.setmembershipStartTime(mContext,SHARED_PREF_MemberShip_StartTime,"0");
                        cancelhour="0";
                        //SharedPrefUtil.setmembershipCancelTime(mContext,SHARED_PREF_MemberShip_CancelTime,cancelTime);
                        SharedPrefUtil.setMembership(mContext,SHARED_PREF_MemberShip,"");

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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
            getActivity().runOnUiThread(new Runnable() {
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
}