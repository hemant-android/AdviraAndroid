package com.advira.advirafarm.buyer.ui.cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.adapter.CartAdapter;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cartfragment extends BaseContainerFragment implements IConsts {

    private View rootView;
    public static RecyclerView recyclerView;
    public static TextView tv_priceval, tv_gstval, tv_totalpaidval, tv_rv1, tv_footertotal, tv_footertotalitem;
    public static RelativeLayout rl_content2, rl_noitems;
    CartAdapter cartListAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private RelativeLayout rl_back, rl_search;
    private Context mContext;
    private RelativeLayout btn_buynow;
    private List<CartDatum> cartList;
    private Button btn_shopnow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_cart, container, false);
            initUI();

        }
        return rootView;
    }

    private void initUI() {

        mContext = getActivity();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recycledViewPool=new RecyclerView.RecycledViewPool();
        tv_priceval = rootView.findViewById(R.id.tv_priceval);
        tv_gstval = rootView.findViewById(R.id.tv_gstval);
        tv_totalpaidval = rootView.findViewById(R.id.tv_totalpaidval);
        rl_content2 = rootView.findViewById(R.id.rl_content2);
        tv_rv1 = rootView.findViewById(R.id.tv_rv1);
        tv_footertotal = rootView.findViewById(R.id.tv_footertotal);
        tv_footertotalitem = rootView.findViewById(R.id.tv_footertotalitem);
        rl_noitems = rootView.findViewById(R.id.rl_noitems);
        btn_shopnow = rootView.findViewById(R.id.btn_shopnow);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        btn_buynow = rootView.findViewById(R.id.btn_buynow);
        rl_back = rootView.findViewById(R.id.rl_back);
        rl_search = rootView.findViewById(R.id.rl_search);


        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        if (useractive.equalsIgnoreCase("inactive")) {

            CheckProfile();

        } else if (useractive.equalsIgnoreCase("guest")) {

            CartListRequest();

        } else {

            CartListRequest();
        }


    }

    private void CartListRequest() {

        Utilities.showLoading(mContext);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        CartListRequest cartListRequest = new CartListRequest();
        cartListRequest.setUserCartType(usermode);

        try {

            Call<CartListResponse> call = RetrofitUrlConnection.loadJSON(token).getmycart(cartListRequest);

            call.enqueue(new Callback<CartListResponse>() {
                @Override
                public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        String addressid = "0";
                        if (response.body().getDefaultAddress().size() > 0) {
                            addressid = response.body().getDefaultAddress().get(0).getId();

                        }
                        SharedPrefUtil.setDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, addressid);
                        // SharedPrefUtil.setDefaultAddress(mContext, SHARED_PREF_DefaultAddress, fulladdress);


                        cartList = new ArrayList<>();
                        cartListAdapter = new CartAdapter(mContext, cartList);

                        List<CartDatum> mListData = response.body().getCartData();

                        if (mListData != null && mListData.size() > 0) {

                            CartActivity.rl_content2.setVisibility(View.VISIBLE);
                            cartList.addAll(mListData);
                            rl_noitems.setVisibility(View.GONE);

                            try {
                                for (int i = 0; i < mListData.size(); i++) {
                                    if (mListData.get(i).getProductInstock().equalsIgnoreCase("Out-of-Stock")) {
                                        SharedPrefUtil.setCartItemStock(mContext, SHARED_PREF_CartItemStock, "out of stock");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {

                            }


                        } else {
                            CartActivity.tv_priceval.setText("₹ 0");
                            CartActivity.tv_gstval.setText("+ ₹ 0");
                            CartActivity.tv_totalpaidval.setText("₹ 0");
                            CartActivity.rl_content2.setVisibility(View.GONE);
                            CartActivity.tv_rv1.setText("");

                            rl_noitems.setVisibility(View.VISIBLE);


                            try {
                                MainActivityNav.text.setText("");
                                MainActivityNav.text.setText("");

                            } catch (Exception ex) {

                            }

                            try {
                                ProductDetailsActivity.text.setText("");
                            } catch (Exception ex) {

                            }

                            String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                            if (profilemode.equalsIgnoreCase("B2B")) {
                                SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");//remove 0

                            } else {
                                SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
                                SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");//remove 0
                            }



                        }

                        //cartListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(cartListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setRecycledViewPool(recycledViewPool);


                    } else {
                        ////Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast//remove toast
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<CartListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    private void CheckProfile() {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


        try {

            Call<IsUserVerifiedResponse> call = RetrofitUrlConnection.loadJSON(token).isuserverified();

            call.enqueue(new Callback<IsUserVerifiedResponse>() {
                @Override
                public void onResponse(Call<IsUserVerifiedResponse> call, Response<IsUserVerifiedResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        String popmsg = "Please update below details :";
                        String chkpop = "n";

                        if (response.body().getPersonalProfileStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Personal details";
                            chkpop = "y";
                        }
                        if (response.body().getBusinessProfileStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Business details";
                            chkpop = "y";
                        }
                        if (response.body().getKycDocumentStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Upload KYC documents";
                            chkpop = "y";
                        }

                        if (response.body().getProfileActivateStatus().equalsIgnoreCase("0")) {

                            if (popmsg.contains("Personal details") || popmsg.contains("Business details") || popmsg.contains("Upload KYC documents")) {

                            } else {
                                popmsg = "Your account is not active";
                            }

                            chkpop = "y";
                        }


                        if (chkpop.equalsIgnoreCase("y")) {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "inactive");

                        } else {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "active");

                        }

                        CartListRequest();

                    } else {

                        Utilities.dismissDialog();
                        ////Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast//remove toast
                    }
                }

                @Override
                public void onFailure(Call<IsUserVerifiedResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
}
