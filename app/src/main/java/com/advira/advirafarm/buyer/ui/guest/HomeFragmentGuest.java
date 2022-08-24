package com.advira.advirafarm.buyer.ui.guest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitURLConnectionNew;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.category.adapter.CategoryImageAdapter;
import com.advira.advirafarm.buyer.ui.guest.adapter.CategoryImageAdapterGuest;
import com.advira.advirafarm.buyer.ui.guest.adapter.CategoryListHeaderAdapterGuest;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.adapter.CategoryListHeaderAdapter;
import com.advira.advirafarm.buyer.ui.product.api.DashboardBanner;
import com.advira.advirafarm.buyer.ui.product.api.DashboardBannerResponse;

import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryListResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.DashboardBannerList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.HomeDashboardBanner;
import com.advira.advirafarm.buyer.ui.product.categoryapi.HomepageResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.DashBoardRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.HomeRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel.DashboardViewModel;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel.HomeViewModel;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static android.content.ContentValues.TAG;


public class HomeFragmentGuest extends BaseContainerFragment implements IConsts  {


    private CarouselView carouselView;
    private List<DashboardBannerList> dashboardBannerList;
    int lastItem,preLast;
    public static RelativeLayout rl_largeb2b, rl_getstarted, rl_faqs,rl_stayhome,rl_businesscycle;
    public static RelativeLayout rl_delivpinto, rl_manualrecyclerview,rl_f2b, rl_addbusiness, rl_manualrecyclerviewb2b;
    int[] sampleImages = {R.drawable.splash_logo};
    String[] sampleTitles = {"one"};
    String[] sampleNetworkImageURLs = {"https://healprod.adviraheal.com/img/uploads/dashboardbanner/2020/07/30/IH-200730-042221-926981.jpg"
    };

   /* String[] sampleNetworkImageURLs = {
            "https://healprod.adviraheal.com/img/uploads/product_images/2020/07/29/IH-200729-092127-160901.jpg"
    };
   */

    public static HomeFragmentGuest newInstance() {
        HomeFragmentGuest fragment = new HomeFragmentGuest();
        return fragment;
    }

    private View rootView;
    private RecyclerView recyclerView,categoryrecyclerView;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private Context mContext;
    CategoryListHeaderAdapterGuest categoryListHeaderAdapterGuest;
    CategoryImageAdapterGuest categoryImageAdapterGuest;
    private List<ProductList_home> orderList;
    private List<CategoryList> orderList1;
    private EditText searchView;
    private RelativeLayout rl_search;
    private String profilemode="B2B";

    private RelativeLayout rl_membershipAds;

    private DashboardViewModel dashboardViewModel;
    private HomeViewModel homeViewModel;
    private DashBoardRepository dashBoardRepository;
    private HomeRepository homeRepository;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_producthome, container, false);

            initUI();
            recycledViewPool=new RecyclerView.RecycledViewPool();
            //ItemListRequest();
            //ProductListRequest();
            //BannerRequest();
        }
        return rootView;

    }

    private void initUI() {

        mContext = getActivity();
        //Toast.makeText(mContext, "home fragment guest.", Toast.LENGTH_SHORT).show();
        searchView = rootView.findViewById(R.id.searchView);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        homeRepository=new HomeRepository((getActivity().getApplication()));
        orderList=new ArrayList<>();
        categoryListHeaderAdapterGuest=new CategoryListHeaderAdapterGuest(mContext,orderList);
        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAllHomeProduct().observe(getActivity(), new Observer<List<ProductList_home>>() {
            @Override
            public void onChanged(List<ProductList_home> productListHomes) {
                Log.e(TAG, "onChanged: homeGuest by roomdb");
                recyclerView.setAdapter(categoryListHeaderAdapterGuest);
                categoryListHeaderAdapterGuest.getAllHomeProduct(productListHomes);
                //orderList=actorList;
            }
        });

        dashBoardRepository=new DashBoardRepository(getActivity().getApplication());
        dashboardBannerList=new ArrayList<>();

        dashboardViewModel=new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardViewModel.getAllBanner().observe(getActivity(), new Observer<List<DashboardBannerList>>() {
            @Override
            public void onChanged(List<DashboardBannerList> actorList) {
                dashboardBannerList=actorList;
                sampleNetworkImageURLs = new String[dashboardBannerList.size()];
                sampleTitles = new String[dashboardBannerList.size()];
                sampleImages = new int[dashboardBannerList.size()];

                for (int i = 0; i < dashboardBannerList.size(); i++) {
                    sampleNetworkImageURLs[i] = dashboardBannerList.get(i).getBannerUrl();
                    sampleTitles[i] = dashboardBannerList.get(i).getBannerImageName();
                    sampleImages[i] = R.drawable.progress_animation;
                }

                carouselView = rootView.findViewById(R.id.customCarouselView);
                carouselView.setPageCount(sampleImages.length);
                carouselView.setViewListener(viewListener);
                Log.d("main", "onChanged: "+actorList);
            }
        });

        rl_search = rootView.findViewById(R.id.rl_search);
        rl_membershipAds=rootView.findViewById(R.id.rl_membershipAds);
        rl_membershipAds.setVisibility(View.GONE);

        categoryrecyclerView = rootView.findViewById(R.id.categoryrecyclerView);
        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        categoryrecyclerView.setNestedScrollingEnabled(false);
        rl_f2b=rootView.findViewById(R.id.rl_f2b);
        rl_addbusiness=rootView.findViewById(R.id.rl_addbusiness);
        rl_manualrecyclerviewb2b=rootView.findViewById(R.id.rl_manualrecyclerviewb2b);
        rl_f2b.setVisibility(View.GONE);
        rl_addbusiness.setVisibility(View.GONE);
        rl_manualrecyclerviewb2b.setVisibility(View.GONE);
        rl_largeb2b=rootView.findViewById(R.id.rl_largeb2b);
        rl_getstarted=rootView.findViewById(R.id.rl_getstarted);
        rl_faqs=rootView.findViewById(R.id.rl_faqs);
        rl_largeb2b.setVisibility(View.GONE);
        rl_getstarted.setVisibility(View.GONE);
        rl_faqs.setVisibility(View.GONE);
        carouselView = rootView.findViewById(R.id.customCarouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setViewListener(viewListener);
        searchView.clearFocus();
        rl_delivpinto = rootView.findViewById(R.id.rl_delivpinto);
        rl_delivpinto.setVisibility(View.GONE);
        rl_manualrecyclerview=rootView.findViewById(R.id.rl_manualrecyclerview);
        rl_manualrecyclerview.setVisibility(View.VISIBLE);
        rl_businesscycle=rootView.findViewById(R.id.rl_businesscycle);
        rl_stayhome=rootView.findViewById(R.id.rl_stayhome);
        rl_stayhome.setVisibility(View.VISIBLE);
        rl_businesscycle.setVisibility(View.GONE);

        searchView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchView.clearFocus();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent i = new Intent();
                    i.setClass(mContext, Search_one.class);
                    //i.setClass(mContext, SearchActivity.class);
                    startActivity(i);
                }

                return false;
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


    /*private void BannerRequest() {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<HomepageResponse> call=RetrofitUrlConnection.loadJSON(token).homepage_v2();
            call.enqueue(new Callback<HomepageResponse>() {
                @Override
                public void onResponse(Call<HomepageResponse> call, Response<HomepageResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Log.i(TAG, "onResponse: dashborad"+response.body());

                        dashboardBannerList = new ArrayList<>();
                        dashboardBannerList = response.body().getDashboardBanners();

                        sampleNetworkImageURLs = new String[dashboardBannerList.size()];
                        sampleTitles = new String[dashboardBannerList.size()];
                        sampleImages = new int[dashboardBannerList.size()];


                        for (int i = 0; i < dashboardBannerList.size(); i++) {

                            sampleNetworkImageURLs[i] = dashboardBannerList.get(i).getBannerUrl();
                            sampleTitles[i] = dashboardBannerList.get(i).getBannerImageName();
                            sampleImages[i] = R.drawable.progress_animation;
                        }

                        carouselView = rootView.findViewById(R.id.customCarouselView);
                        carouselView.setPageCount(sampleImages.length);
                        carouselView.setViewListener(viewListener);


                        orderList = new ArrayList<>();
                        categoryListHeaderAdapterGuest = new CategoryListHeaderAdapterGuest(mContext, orderList);
                        //categoryImageAdapterGuest = new CategoryImageAdapterGuest(mContext,orderList);

                        List<ProductList_home> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }


                        recyclerView.setAdapter(categoryListHeaderAdapterGuest);
                        //recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setHasFixedSize(false);


                        categoryrecyclerView.setAdapter(categoryImageAdapterGuest);
                        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        categoryrecyclerView.setNestedScrollingEnabled(false);
                        categoryrecyclerView.setVisibility(View.GONE);



                    }

                    Utilities.dismissDialog();
                    //carouselView.setViewListener(viewListener);


                    String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");


                    //ProductListRequest();


                }

                @Override
                public void onFailure(Call<HomepageResponse> call, Throwable t) {
                    //Utilities.dismissDialog();
                    t.printStackTrace();
                    Log.e(TAG, "onFailure: response123",t );
                    //Toast.makeText(mContext, "Something went wrong 123, Please try again.", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Utilities.dismissDialog();
            Log.e(TAG, "BannerRequest: faileure",e );
            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }*/


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

                        categoryImageAdapterGuest = new CategoryImageAdapterGuest(mContext, orderList1);

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
                        categoryrecyclerView.setAdapter(categoryImageAdapterGuest);
                        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        categoryrecyclerView.setNestedScrollingEnabled(false);
                        categoryrecyclerView.setVisibility(View.GONE);


                    } else {
                        Utilities.dismissDialog();

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
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


    /*private void ProductListRequest() {

        //Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        Call<CategoryListResponse> call;

        try {

            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
            call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();
            *//*if (profilemode.equalsIgnoreCase("B2C")) {

                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            } else {
                call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2b();

            }
*//*
          //  Call<CategoryListResponse> call = RetrofitUrlConnection.loadJSON(token).productcategorywiseb2c();

            call.enqueue(new Callback<CategoryListResponse>() {
                @Override
                public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Utilities.dismissDialog();


                        orderList = new ArrayList<>();
                        categoryListHeaderAdapterGuest = new CategoryListHeaderAdapterGuest(mContext, orderList);
                        //categoryImageAdapterGuest = new CategoryImageAdapterGuest(mContext,orderList);

                        List<com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList> mListData = response.body().getProductList();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }


                        recyclerView.setAdapter(categoryListHeaderAdapterGuest);
                        //recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setHasFixedSize(false);


                        categoryrecyclerView.setAdapter(categoryImageAdapterGuest);
                        categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        categoryrecyclerView.setNestedScrollingEnabled(false);
                        categoryrecyclerView.setVisibility(View.GONE);



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

    }*/

/*private void ItemListRequest() {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        try {
            Call<HomepageResponse> call = RetrofitUrlConnection.loadJSON(token).homepage_v2();
            //Log.e(" Login url", call.request().url().toString());
            call.enqueue(new Callback<HomepageResponse>() {
                @Override
                public void onResponse(Call<HomepageResponse> call, Response<HomepageResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        List<HomeDashboardBanner> dashboardBannerList = new ArrayList<>();
                        dashboardBannerList = response.body().getHomedashboardBanners();

                        sampleNetworkImageURLs = new String[dashboardBannerList.size()];
                        sampleTitles = new String[dashboardBannerList.size()];
                        sampleImages = new int[dashboardBannerList.size()];


                        for (int i = 0; i < dashboardBannerList.size(); i++) {

                            sampleNetworkImageURLs[i] = dashboardBannerList.get(i).getBannerUrl();
                            sampleTitles[i] = dashboardBannerList.get(i).getBannerImageName();
                            sampleImages[i] = R.drawable.progress_animation;
                        }

                        carouselView = rootView.findViewById(R.id.customCarouselView);
                        carouselView.setPageCount(sampleImages.length);
                        carouselView.setViewListener(viewListener);


                        orderList = new ArrayList<>();
                        categoryListHeaderAdapterGuest = new CategoryListHeaderAdapterGuest(mContext, orderList);
                        //categoryImageAdapterGuest = new CategoryImageAdapterGuest(mContext,orderList);

                        List<ProductList> mListData = response.body().getProductlist();

                        if (mListData != null && mListData.size() > 0) {
                            orderList.addAll(mListData);

                        }


                        recyclerView.setAdapter(categoryListHeaderAdapterGuest);
                        //recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setHasFixedSize(false);


                        *//*categoryrecyclerView.setAdapter(categoryImageAdapterGuest);
                    categoryrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    categoryrecyclerView.setNestedScrollingEnabled(false);
                    categoryrecyclerView.setVisibility(View.GONE);*//*

                        carouselView.setViewListener(viewListener);


                        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");



                    }
                    else {
                        Utilities.dismissDialog();

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<HomepageResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong 1020200, Please try again.", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
*/

}
