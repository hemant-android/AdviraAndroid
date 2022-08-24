package com.advira.advirafarm.buyer.ui.order;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.order.adapter.MembershipListAdaptor;
import com.advira.advirafarm.buyer.ui.order.adapter.OrderAdapter;
import com.advira.advirafarm.buyer.ui.order.api.MemberDatum;
import com.advira.advirafarm.buyer.ui.order.api.OrderDatum_v2;
import com.advira.advirafarm.buyer.ui.order.api.OrderListResponse_v2;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersFragment extends BaseContainerFragment implements IConsts {

    OrderAdapter orderAdapter;
    MembershipListAdaptor memberAdaptor;

    private View rootView;

    //private List<OrderDatum> orderList;
    private List<OrderDatum_v2> orderList;
    private List<MemberDatum> memberList;
    private RecyclerView recyclerView, mem_recyclerView;
    private RelativeLayout rl_back;
    private RelativeLayout rl_myorders;
    private Context mContext;
    private RelativeLayout rl_noitems;
    private Button btn_shopnow;
    private TextView ma_headerq;
    private TextView tv_tab1;
    private TextView tv_tab2;
    private TextView tv_blankcart;
    private View v_linetab1;
    private View v_linetab2;

    ReviewManager reviewManager;
    ReviewInfo reviewInfo=null;


    public static MyOrdersFragment newInstance() {
        MyOrdersFragment fragment = new MyOrdersFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_myorders, container, false);
            initUI();

            reviewManager= ReviewManagerFactory.create(getActivity());

            tv_tab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tv_tab1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tv_tab2.setTextColor(getResources().getColor(R.color.colorBlack));

                    //ll_sequence.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mem_recyclerView.setVisibility(View.GONE);

                    v_linetab1.setVisibility(View.VISIBLE);
                    v_linetab2.setVisibility(View.INVISIBLE);

                    OrderListRequest();

                }
            });

            tv_tab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tv_tab1.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_tab2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                    recyclerView.setVisibility(View.GONE);
                    mem_recyclerView.setVisibility(View.VISIBLE);

                    v_linetab1.setVisibility(View.INVISIBLE);
                    v_linetab2.setVisibility(View.VISIBLE);
                    MembershipRequest();

                }
            });
        }
        return rootView;
    }


    private void initUI() {

        mContext = getActivity();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        mem_recyclerView=rootView.findViewById(R.id.mem_recyclerView);
        rl_back = rootView.findViewById(R.id.rl_back);
        rl_myorders = rootView.findViewById(R.id.rl_myorders);
        tv_blankcart= rootView.findViewById(R.id.tv_blankcart);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mem_recyclerView.setHasFixedSize(true);
        mem_recyclerView.setLayoutManager(new LinearLayoutManager(mContext) );
        mem_recyclerView.setVisibility(View.GONE);

        rl_myorders.setVisibility(View.GONE);

        rl_noitems = rootView.findViewById(R.id.rl_noitems);
        ma_headerq = rootView.findViewById(R.id.ma_headerq);

        tv_tab1 = rootView.findViewById(R.id.tv_tab1);
        tv_tab2 = rootView.findViewById(R.id.tv_tab2);

        v_linetab1 = rootView.findViewById(R.id.v_linetab1);
        v_linetab2 = rootView.findViewById(R.id.v_linetab2);

       /* tv_tab1.setVisibility(View.GONE);
        tv_tab2.setVisibility(View.GONE);
        v_linetab1.setVisibility(View.GONE);
        v_linetab2.setVisibility(View.GONE);*/

        //MembershipRequest();
        OrderListRequest();

    }


    public void OrderListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<OrderListResponse_v2> call = RetrofitUrlConnection.loadJSON(token).orderlist();

            call.enqueue(new Callback<OrderListResponse_v2>() {
                @Override
                public void onResponse(Call<OrderListResponse_v2> call, Response<OrderListResponse_v2> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        orderList = new ArrayList<>();
                        orderAdapter = new OrderAdapter(mContext, orderList);

                        List<OrderDatum_v2> mListData = response.body().getOrderData();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);
                            rl_noitems.setVisibility(View.GONE);
                            ma_headerq.setVisibility(View.GONE);
                            //ShowAppReview();
                        } else {
                            try {
                                rl_noitems.setVisibility(View.VISIBLE);
                                tv_blankcart.setText("You haven't placed any order yet.");
                                ma_headerq.setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //orderAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(orderAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();

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

    }

    private void ShowAppReview() {

        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(getActivity(), reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                // show native rate app dialog on error
                //showRateAppFallbackDialog();
            }
        });

    }

    /*private void showRateAppFallbackDialog() {
        new MaterialAlertDialogBuilder(mContext)
                .setTitle(R.string.rate_app_title)
                .setMessage(R.string.rate_app_message)
                .setPositiveButton(R.string.rate_btn_pos, (dialog, which) -> redirectToPlayStore())
                .setNegativeButton(R.string.rate_btn_neg,
                        (dialog, which) -> {
                            // take action when pressed not now
                        })
                .setNeutralButton(R.string.rate_btn_nut,
                        (dialog, which) -> {
                            // take action when pressed remind me later
                        })
                .setOnDismissListener(dialog -> {
                })
                .show();

    }

    private void redirectToPlayStore() {
        final String appPackageName = getPa();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException exception) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }*/

    public void MembershipRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<OrderListResponse_v2> call = RetrofitUrlConnection.loadJSON(token).orderlist();

            call.enqueue(new Callback<OrderListResponse_v2>() {
                @Override
                public void onResponse(Call<OrderListResponse_v2> call, Response<OrderListResponse_v2> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        memberList = new ArrayList<>();
                        memberAdaptor = new MembershipListAdaptor(mContext, memberList);
                        List<MemberDatum> mListData1 = response.body().getMemberData();
                        if (mListData1 != null && mListData1.size() > 0) {
                            memberList.addAll(mListData1);
                            rl_noitems.setVisibility(View.GONE);
                        }
                        else{
                            rl_noitems.setVisibility(View.VISIBLE);
                            tv_blankcart.setText("You don't have any Membership Plan.");
                        }
                        mem_recyclerView.setAdapter(memberAdaptor);
                        recyclerView.setVisibility(View.GONE);
                        mem_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        mem_recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();

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
    }

    @Override
    public void onResume() {
        Utilities.dismissDialog();
        super.onResume();
        initUI();
    }

}