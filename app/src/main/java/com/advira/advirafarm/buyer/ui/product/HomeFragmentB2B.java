package com.advira.advirafarm.buyer.ui.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.category.adapter.CategoryImageAdapter;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.ChooseAddressListNav;
import com.advira.advirafarm.buyer.ui.product.adapter.CategoryListHeaderAdapter;
import com.advira.advirafarm.buyer.ui.product.adapter.CategoryListHeaderAdapterB2B;
import com.advira.advirafarm.buyer.ui.product.api.DashboardBanner;
import com.advira.advirafarm.buyer.ui.product.api.DashboardBannerResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryListResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.RegistrationActivity1;
import com.advira.advirafarm.buyer.ui.registration.profile.BusinessDetailsActivity;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragmentB2B extends BaseContainerFragment implements IConsts {

    AlertDialog.Builder builder;
    public static TextView tv_deliverto;
    public static RelativeLayout rl_delivpinto, rl_manualrecyclerview,rl_stayhome,rl_businesscycle;
    private CarouselView carouselView;
    int[] sampleImages = {R.drawable.splash_logo};
    String[] sampleTitles = {"one"};
    String[] sampleNetworkImageURLs = {"https://healprod.adviraheal.com/img/uploads/dashboardbanner/2020/07/30/IH-200730-042221-926981.jpg"
    };

   /* String[] sampleNetworkImageURLs = {
            "https://healprod.adviraheal.com/img/uploads/product_images/2020/07/29/IH-200729-092127-160901.jpg"
    };
   */

    public static HomeFragmentB2B newInstance() {
        HomeFragmentB2B fragment = new HomeFragmentB2B();
        return fragment;
    }

    private View rootView;
    private RecyclerView recyclerView,categoryrecyclerView;
    private Context mContext;
    CategoryListHeaderAdapterB2B categoryListHeaderAdapter;
    CategoryImageAdapter categoryImageAdapter;
    private List<ProductList> orderList;
    private List<CategoryList> orderList1;
    private EditText searchView;
    private Button btn_addbusiness,btn_register,btn_faqs,btn_contactus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_producthome, container, false);
            initUI();
            //BannerRequest();
            String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");
            if (useractive.equalsIgnoreCase("inactive")) {

                rl_delivpinto.setVisibility(View.GONE);
                CheckProfile();

            } else if (useractive.equalsIgnoreCase("guest")) {

                ProductListRequest();
                rl_delivpinto.setVisibility(View.GONE);


            } else {
                rl_delivpinto.setVisibility(View.GONE);
                ProductListRequest();
            }
        }
        return rootView;

    }



    private void initUI() {

        mContext = getActivity();

        tv_deliverto = rootView.findViewById(R.id.tv_deliverto);
        String delivaddress = SharedPrefUtil.getHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        tv_deliverto.setText(delivaddress);


        rl_delivpinto = rootView.findViewById(R.id.rl_delivpinto);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        searchView = rootView.findViewById(R.id.searchView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);

        categoryrecyclerView = rootView.findViewById(R.id.categoryrecyclerView);
        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        categoryrecyclerView.setNestedScrollingEnabled(false);

        rl_manualrecyclerview=rootView.findViewById(R.id.rl_manualrecyclerview);
        rl_manualrecyclerview.setVisibility(View.GONE);
        rl_stayhome=rootView.findViewById(R.id.rl_stayhome);
        rl_stayhome.setVisibility(View.GONE);
        rl_businesscycle=rootView.findViewById(R.id.rl_businesscycle);


        carouselView = rootView.findViewById(R.id.customCarouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setViewListener(viewListener);
        carouselView.setVisibility(View.GONE);
        searchView.clearFocus();

        btn_addbusiness=rootView.findViewById(R.id.btn_addbusiness);
        btn_register=rootView.findViewById(R.id.btn_register);
        btn_faqs=rootView.findViewById(R.id.btn_faqs);
        btn_contactus=rootView.findViewById(R.id.btn_contactus);

        if (useractive.equalsIgnoreCase("guest")) {

            btn_addbusiness.setVisibility(View.VISIBLE);
            btn_register.setVisibility(View.VISIBLE);

        } else if (useractive.equalsIgnoreCase("inactive")) {
            btn_addbusiness.setVisibility(View.VISIBLE);
            btn_register.setVisibility(View.GONE);

        } else{
            btn_addbusiness.setVisibility(View.GONE);
            btn_register.setVisibility(View.GONE);
        }


        btn_addbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (useractive.equalsIgnoreCase("guest")) {

                    Intent i = new Intent();
                    i.setClass(mContext, RegistrationActivity1.class);
                    startActivity(i);

                } else if (useractive.equalsIgnoreCase("inactive")) {
                    CheckProfile();
                    //ShowAlert(popmsg);

                } /*else{
                    rl_delivpinto.setVisibility(View.GONE);
                    //ProductListRequest();
                }*/
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (useractive.equalsIgnoreCase("guest")) {

                    Intent i = new Intent();
                    i.setClass(mContext, RegistrationActivity1.class);
                    startActivity(i);

                } /*else  {
                    CheckProfile();

                }*/
            }
        });

        btn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, WebViewActivity.class);
                i.putExtra("header","Contact Us");
                i.putExtra("url","https://www.advira.in/about-app.php");
                startActivity(i);
            }
        });

        btn_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, WebViewActivity.class);
                i.putExtra("header","FAQ");
                i.putExtra("url","https://www.advira.in/faq.php");
                startActivity(i);
            }
        });

        searchView.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchView.clearFocus();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    Intent i = new Intent();
                    //i.setClass(mContext, SearchActivity.class);
                    i.setClass(mContext, Search_one.class);
                    startActivity(i);
                }

                /*.
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);*/
                return false;
            }
        });

        rl_delivpinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent();
                i.setClass(mContext, ChooseAddressListNav.class);
                startActivity(i);


            }
        });

    }


    // To set simple images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Picasso.with(getContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[0]).fit().into(imageView);

        }
    };

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);

            TextView labelTextView = customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = customView.findViewById(R.id.fruitImageView);
            Picasso.with(getContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[0]).fit().into(fruitImageView);
            labelTextView.setText(sampleTitles[position]);
            return customView;
        }
    };

    View.OnClickListener pauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            carouselView.pauseCarousel();

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        searchView.clearFocus();
    }



    private void BannerRequest() {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<DashboardBannerResponse> call = RetrofitUrlConnection.loadJSON(token).dashboardbannersnotoken();

            call.enqueue(new Callback<DashboardBannerResponse>() {
                @Override
                public void onResponse(Call<DashboardBannerResponse> call, Response<DashboardBannerResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        List<DashboardBanner> dashboardBannerList = new ArrayList<>();
                        dashboardBannerList = response.body().getDashboardBanners();

                        sampleNetworkImageURLs = new String[dashboardBannerList.size()];
                        sampleTitles = new String[dashboardBannerList.size()];
                        sampleImages = new int[dashboardBannerList.size()];


                        for (int i = 0; i < dashboardBannerList.size(); i++) {

                            sampleNetworkImageURLs[i] = dashboardBannerList.get(i).getBannerUrl();
                            sampleTitles[i] = dashboardBannerList.get(i).getBannerName();
                            sampleImages[i] = R.drawable.progress_animation;
                        }

                        carouselView = rootView.findViewById(R.id.customCarouselView);
                        carouselView.setPageCount(sampleImages.length);
                        carouselView.setViewListener(viewListener);
                    }

                    Utilities.dismissDialog();
                    carouselView.setViewListener(viewListener);

                    String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");


                    /*if (useractive.equalsIgnoreCase("inactive")) {

                        rl_delivpinto.setVisibility(View.GONE);
                        CheckProfile();

                    } else if (useractive.equalsIgnoreCase("guest")) {

                        ProductListRequest();
                        rl_delivpinto.setVisibility(View.GONE);


                    } else {
                        rl_delivpinto.setVisibility(View.GONE);
                        ProductListRequest();
                    }*/


                }


                @Override
                public void onFailure(Call<DashboardBannerResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void CategoryListRequest() {

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        try{
            Call<CategoryResponse> call=RetrofitUrlConnection.loadJSON(token).categorynotoken();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();


                        orderList1 = new ArrayList<>();

/* categoryAdapter = new CategoryAdapter(mContext, orderList);
                        categoryNameAdapter = new CategoryNameAdapter(mContext,orderList);*/

                        categoryImageAdapter = new CategoryImageAdapter(mContext, orderList1);

                        List<CategoryList> mListData1 = response.body().getProductcategory();

                        if (mListData1 != null && mListData1.size() > 0) {
                            orderList1.addAll(mListData1);

                        }

/*
                        recyclerView1.setAdapter(categoryNameAdapter);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView1.setNestedScrollingEnabled(false);
*/



                       /* recyclerView3.setAdapter(categoryImageAdapter);

                        recyclerView3.setLayoutManager(new GridLayoutManager(mContext, 3));
                        //recyclerView3.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView3.setNestedScrollingEnabled(false);*/
                        categoryrecyclerView.setAdapter(categoryImageAdapter);
                        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        categoryrecyclerView.setNestedScrollingEnabled(false);
                        categoryrecyclerView.setVisibility(View.GONE);


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
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void ProductListRequest() {

        //Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        try {

            Call<CategoryListResponse> call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2b();

            call.enqueue(new Callback<CategoryListResponse>() {
                @Override
                public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        orderList = new ArrayList<>();
                        categoryListHeaderAdapter = new CategoryListHeaderAdapterB2B(mContext, orderList);

                        //categoryImageAdapter = new CategoryImageAdapter(mContext,orderList1);

                        List<com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }


                        recyclerView.setAdapter(categoryListHeaderAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                        /*categoryrecyclerView.setAdapter(categoryImageAdapter);
                        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        categoryrecyclerView.setNestedScrollingEnabled(false);
                        categoryrecyclerView.setVisibility(View.GONE);
*/


                    } else {
                        Utilities.dismissDialog();

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }

                    BannerRequest();
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
                        String chkpop ="n";

                        if(response.body().getPersonalProfileStatus().equalsIgnoreCase("0"))
                        {
                            popmsg = popmsg+ "\n - Personal details";
                            chkpop="y";
                        }
                        if(response.body().getBusinessProfileStatus().equalsIgnoreCase("0"))
                        {
                            popmsg = popmsg+ "\n - Business details";
                            chkpop="y";
                        }
                        if(response.body().getKycDocumentStatus().equalsIgnoreCase("0"))
                        {
                            popmsg = popmsg+ "\n - Upload KYC documents";
                            chkpop="y";
                        }

                        if (response.body().getProfileActivateStatus().equalsIgnoreCase("0")) {

                            if(popmsg.contains("Personal details")||popmsg.contains("Business details")||popmsg.contains("Upload KYC documents"))
                            {

                            }
                            else
                            {
                                popmsg = "Your account is not active";
                            }

                            chkpop = "y";
                        }


                        if(chkpop.equalsIgnoreCase("y"))
                        {
                            SharedPrefUtil.setUserActive(mContext,SHARED_PREF_UserActive,"inactive");
                            ShowAlert(popmsg);

                        }
                        else
                        {
                            SharedPrefUtil.setUserActive(mContext,SHARED_PREF_UserActive,"active");

                        }

                        ProductListRequest();

                    } else {

                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
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
    private void ShowAlert(String ShowAlert) {
        builder = new AlertDialog.Builder(mContext);


        builder.setTitle("Account Inactive !!");

        //Setting message manually and performing action on button click
        builder.setMessage(ShowAlert)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        if (ShowAlert.contains("Personal details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, PersonalDetailsEditActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        } else if (ShowAlert.contains("Business details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, BusinessDetailsActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        } else if (ShowAlert.contains("Upload KYC documents")) {

                            Intent i = new Intent();
                            i.setClass(mContext, DocumentUploadActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();


                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

    }


}
