package com.advira.advirafarm.buyer.ui.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.order.adapter.OrderAdapter;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderDatum;
import com.advira.advirafarm.buyer.ui.order.api.OrderDatum_v2;
import com.advira.advirafarm.buyer.ui.order.api.OrderListResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderListResponse_v2;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrders extends AppCompatActivity implements IConsts {

    //private List<OrderDatum> orderList;
    private List<OrderDatum_v2> orderList;
    private RecyclerView recyclerView;
    private RelativeLayout rl_back;
    private Context mContext;
    OrderAdapter orderAdapter;
    public static String orderid = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyOrders.this.finish();

            }
        });
    }


    private void initUI() {

        mContext = MyOrders.this;
        recyclerView = findViewById(R.id.recyclerView);
        rl_back = findViewById(R.id.rl_back);
        //recyclerView.setHasFixedSize(true);



        OrderListRequest();

        //creating recyclerview adapter
       // OrderAdapter adapter = new OrderAdapter(mContext, orderList);

        //setting adapter to recyclerview
        //recyclerView.setAdapter(adapter);
    }


    public void OrderListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<OrderListResponse_v2> call=RetrofitUrlConnection.loadJSON(token).orderlist();
            call.enqueue(new Callback<OrderListResponse_v2>() {
                @Override
                public void onResponse(Call<OrderListResponse_v2> call, Response<OrderListResponse_v2> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        orderList = new ArrayList<>();
                        orderAdapter = new OrderAdapter(mContext,orderList);

                        List<OrderDatum_v2> mListData = response.body().getOrderData();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                            //recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
                            //recyclerView.setAdapter(new OrderAdapter(mContext,response.body().getOrderData()));
                            //   recyclerView.setNestedScrollingEnabled(false);
                            Utilities.dismissDialog();
                        }

                        //orderAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(orderAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();


                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse_v2> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });

        }catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }



    private void cancelOrder() {

        Utilities.showLoading(mContext);
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setOrderId(orderid);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<OrderCancelResponse> call = RetrofitUrlConnection.loadJSON(token).canclemyorder(orderCancelRequest);

            call.enqueue(new Callback<OrderCancelResponse>() {
                @Override
                public void onResponse(Call<OrderCancelResponse> call, Response<OrderCancelResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                            //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                            Utilities.dismissDialog();

                            //OrderCancellationActivity.this.finish();

                            Intent i=new Intent();
                            i.setClass(mContext,OrderDetailsActivity.class);
                            i.putExtra("orderid", orderid);
                            i.putExtra("from", "");
                            startActivity(i);

                    }

                    Utilities.dismissDialog();

                }


                @Override
                public void onFailure(Call<OrderCancelResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {

        MyOrders.this.finish();

    }




}
