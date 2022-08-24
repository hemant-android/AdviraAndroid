package com.advira.advirafarm.buyer.ui.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.category.CategoryFragment;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.MemberBenefitsAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.MemberfaqAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.api.MemberPlanResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipFaq;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipPrice;
import com.advira.advirafarm.buyer.ui.payment.membershippayment.MembershipPaymentOption;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserRegno;

public class MembershipFragment extends BottomSheetDialogFragment {

    RecyclerView benefitrecyclerView;
    private Context mContext;
    private MemberBenefitsAdapter memberBenefitsAdapter;
    private View rootView;
    RelativeLayout btn_buynow;
    public static TextView tv_buyplan,tv_footertotal,tv_duration;
    String string;

    String memberid="";
    String membershipDetails="";
    public static String price;
    public static String duration;
    public static String buttonLabel;
    String userId="";

    private List<MembershipPrice> orderList;

    public static MembershipFragment newInstance(String string) {
        MembershipFragment f = new MembershipFragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        string = getArguments().getString("string");
        setStyle(DialogFragment.STYLE_NORMAL,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_membership, container, false);
            initUI();

        }
        return rootView;
    }

    private void initUI() {

        mContext = getActivity();

        btn_buynow=rootView.findViewById(R.id.btn_buynow);
        tv_buyplan=rootView.findViewById(R.id.tv_buyplan);
        tv_footertotal=rootView.findViewById(R.id.tv_footertotal);
        tv_duration=rootView.findViewById(R.id.tv_duration);
        benefitrecyclerView = rootView.findViewById(R.id.rv_benefit);
        benefitrecyclerView.setHasFixedSize(false);//true
        benefitrecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent();
                i.setClass(mContext, MembershipPaymentOption.class);
                i.putExtra("memberid",memberid);
                i.putExtra("membershipDetails",membershipDetails);
                i.putExtra("price",price);
                i.putExtra("duration",duration);
                i.putExtra("buttonLabel",buttonLabel);
                i.putExtra("userId",userId);
                //Log.e(TAG, "onClick: membershippayment\n"+ memberid+"\n"+membershipDetails+"\n"+price+"\n"+duration+"\n"+userId);
                startActivity(i);
            }
        });

        PlanRequest();

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
                        /*Gson gson = new Gson();
                        String vakk = gson.toJson(response.body()).toString();*/

                        memberid=response.body().getProductList().getMembershipId();
                        membershipDetails=response.body().getProductList().getMembershipName();
                        userId=SharedPrefUtil.getUserRegno(mContext,SHARED_PREF_UserRegno,"");


                        /*price=tv_footertotal.getText().toString();
                        duration=tv_duration.getText().toString();*/

                        /*int position=response.body().getProductList().getMembershipPrices().size()-1;
                        if(response.body().getProductList().getMembershipPrices().get(position).getIsDefault().equalsIgnoreCase("yes"))
                        {
                            price=response.body().getProductList().getMembershipPrices().get(position).getPrice();
                            String Months=response.body().getProductList().getMembershipPrices().get(position).getMonths();
                            int days=10*Integer.valueOf(Months);
                            duration=String.valueOf(days);
                        }*/


                        orderList = new ArrayList<>();
                        memberBenefitsAdapter = new MemberBenefitsAdapter(mContext, orderList);
                        List<MembershipPrice> mListData = response.body().getProductList().getMembershipPrices();
                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }
                        benefitrecyclerView.setAdapter(memberBenefitsAdapter);
                        //benefitrecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                        benefitrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        //recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
                        benefitrecyclerView.setNestedScrollingEnabled(false);
                        benefitrecyclerView.setHasFixedSize(false);


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
}

