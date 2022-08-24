package com.advira.advirafarm.buyer.ui.category;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.category.adapter.CategoryImageAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryListResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.amazon.payments.hosted.mobile.g.a.t;


public class CategoryFragment extends BaseContainerFragment implements IConsts {


    /*CategoryAdapter categoryAdapter;
    CategoryNameAdapter categoryNameAdapter;*/
    CategoryImageAdapter categoryImageAdapter;
    private View rootView;
    private RecyclerView recyclerView1, recyclerView3;
    private Context mContext;
    private List<CategoryList> orderList;
    private EditText searchView;

    private String profilemode="B2B";
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_category, container, false);
            initUI();

        }
        return rootView;

    }


    private void initUI() {

        mContext = getActivity();

       /* recyclerView1 = rootView.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
*/



       /* recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setNestedScrollingEnabled(false);*/

        recyclerView3 = rootView.findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(false);//true
        recyclerView3.setLayoutManager(new LinearLayoutManager(mContext));


        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        if (useractive.equalsIgnoreCase("inactive")) {

            CheckProfile();

        } else if (useractive.equalsIgnoreCase("guest")) {

            //ProductListRequest();
            CategoryListRequest();

        } else {

            //ProductListRequest();
            CategoryListRequest();
        }


    }

    private void CategoryListRequest() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try{
            Call<CategoryResponse> call=RetrofitUrlConnection.loadJSON(token).categorynotoken();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();


                        orderList = new ArrayList<>();

/* categoryAdapter = new CategoryAdapter(mContext, orderList);
                        categoryNameAdapter = new CategoryNameAdapter(mContext,orderList);*/

                        categoryImageAdapter = new CategoryImageAdapter(mContext, orderList);

                        List<CategoryList> mListData = response.body().getProductcategory();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }

/*
                        recyclerView1.setAdapter(categoryNameAdapter);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView1.setNestedScrollingEnabled(false);
*/



                        recyclerView3.setAdapter(categoryImageAdapter);

                        recyclerView3.setLayoutManager(new GridLayoutManager(mContext, 3));
                        //recyclerView3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView3.setNestedScrollingEnabled(false);


                    } else {
                        Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Utilities.dismissDialog();

                }
            });
        } catch (Exception e) {
            //toast.maketext(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }


/*
    private void ProductListRequest() {

        //Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        // recyclerView2.setHasFixedSize(true);
        //recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));

        Call<CategoryListResponse> call;

        try {

            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

            if (profilemode.equalsIgnoreCase("B2C")) {

                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            } else {
                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2b();

            }

            // Call<CategoryListResponse> call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            call.enqueue(new Callback<CategoryListResponse>() {
                @Override
                public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();


                        orderList = new ArrayList<>();
                       */
/* categoryAdapter = new CategoryAdapter(mContext, orderList);
                        categoryNameAdapter = new CategoryNameAdapter(mContext,orderList);*//*

                        categoryImageAdapter = new CategoryImageAdapter(mContext, orderList);

                        List<ProductList> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }
*/
/*
                        recyclerView1.setAdapter(categoryNameAdapter);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView1.setNestedScrollingEnabled(false);
*//*



                        recyclerView3.setAdapter(categoryImageAdapter);

                        recyclerView3.setLayoutManager(new GridLayoutManager(mContext, 3));
                        //recyclerView3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView3.setNestedScrollingEnabled(false);


                    } else {
                        Utilities.dismissDialog();

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CategoryListResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
*/


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

                        //ProductListRequest();
                        CategoryListRequest();

                    } else {

                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<IsUserVerifiedResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            //toast.maketext(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


}