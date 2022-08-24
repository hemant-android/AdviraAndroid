package com.advira.advirafarm.buyer.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.DailyBasketAdaptor;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.Product_Basket;
import com.advira.advirafarm.buyer.ui.wallet.adaptor.WalletHistoryAdaptor;
import com.advira.advirafarm.buyer.ui.wallet.api.MywalletpassbookResponse;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletDatum;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

public class WalletHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Context mContext;
    RelativeLayout rl_back;
    TextView errortext;


    WalletHistoryAdaptor walletHistoryAdaptor;
    private List<WalletDatum> transactionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);

        Init();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(WalletHistoryActivity.this, WalletActivity.class);
                startActivity(i);

            }
        });


    }

    private void Init() {
        mContext=WalletHistoryActivity.this;

        errortext=findViewById(R.id.errortext);

        recyclerView=findViewById(R.id.wh_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        rl_back=findViewById(R.id.rl_back);

        getWalletBalance();

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
                        transactionList = new ArrayList<>();
                        walletHistoryAdaptor = new WalletHistoryAdaptor(mContext, transactionList);
                        List<WalletDatum> mListData = response.body().getWalletData();
                        if (mListData != null && mListData.size() > 0) {
                            transactionList.addAll(mListData);
                            errortext.setVisibility(View.GONE);
                        } else {
                            try {
                                errortext.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //orderAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(walletHistoryAdaptor);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setNestedScrollingEnabled(false);
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
}