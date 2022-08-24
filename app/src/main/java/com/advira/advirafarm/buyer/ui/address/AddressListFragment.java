package com.advira.advirafarm.buyer.ui.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.adapter.AddressListAdapter;
import com.advira.advirafarm.buyer.ui.address.api.AddressDate;
import com.advira.advirafarm.buyer.ui.address.api.AddressListData;
import com.advira.advirafarm.buyer.ui.address.api.AddressListRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddressListResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListFragment extends BaseContainerFragment implements IConsts {

    public static AddressListFragment newInstance() {
        AddressListFragment fragment = new AddressListFragment();
        return fragment;
    }

    private View rootView;
    private RecyclerView recyclerView;
    private RelativeLayout rl_back,rl_currentaddress;
    private RelativeLayout rl_myaddress;
    private Button cv_add;
    private RelativeLayout rl_footer;
    private Context mContext;

    //private List<AddressListData> addressList;
    private List<AddressDate> addressList;
    AddressListAdapter addressListAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_myaddress, container, false);
            initUI();

            cv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent();
                    i.setClass(mContext, AddNewAddressActivity.class);
                    i.putExtra("from","addresslistfrag");
                    mContext.startActivity(i);

                }
            });

        }
        return rootView;

    }


    private void initUI() {

        mContext = getActivity();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recycledViewPool=new RecyclerView.RecycledViewPool();
        rl_back = rootView.findViewById(R.id.rl_back);
        rl_myaddress = rootView.findViewById(R.id.rl_myaddress);
        cv_add = rootView.findViewById(R.id.cv_add);
        rl_footer = rootView.findViewById(R.id.rl_footer);
        rl_currentaddress=rootView.findViewById(R.id.rl_currentaddress);
        recyclerView.setHasFixedSize(false);//true
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        rl_myaddress.setVisibility(View.GONE);
        rl_footer.setVisibility(View.GONE);

        rl_currentaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                //i.setClass(mContext, AutoDetectAddressList.class);
                i.setClass(mContext, AddressfromMap.class);
                startActivity(i);
            }
        });


        AddressListRequest();


    }

    public void AddressListRequest() {

        // Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        AddressListRequest addressListRequest = new AddressListRequest();
        addressListRequest.setUserType(usertype);

        try {

            Call<AddressListResponse> call = RetrofitUrlConnection.loadJSON(token).addresslist(addressListRequest);

            call.enqueue(new Callback<AddressListResponse>() {
                @Override
                public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        // Utilities.dismissDialog();
                        addressList = new ArrayList<>();
                        addressListAdapter = new AddressListAdapter(mContext, addressList);

                        List<AddressDate> mListData = response.body().getAddressListData();

                        if (mListData != null && mListData.size() > 0) {
                            addressList.addAll(mListData);

                        } else {
                            String headeraddress = "Select Address";
                            SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, headeraddress);
                            try {
                                MainActivityNav.tv_deliverto.setText(headeraddress);
                            } catch (Exception ex) {

                            }
                        }

                        addressListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(addressListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setRecycledViewPool(recycledViewPool);


                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AddressListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        initUI();
        super.onResume();
    }

}
