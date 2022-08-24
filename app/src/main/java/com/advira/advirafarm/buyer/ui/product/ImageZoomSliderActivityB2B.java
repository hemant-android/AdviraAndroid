package com.advira.advirafarm.buyer.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.guest.ImageZoomSliderActivityGuest;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.product.api.ProductBanner;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsRequest;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageZoomSliderActivityB2B extends AppCompatActivity implements IConsts {


    private RelativeLayout rl_back, rl_search, rl_cart;
    private CarouselView carouselView;
    private TextView tv_prodname, tv_cartcount, tv_pd_header2;
    private ImageView iv_rx;
    private Context mContext;
    String productname = "",productid="",isrx="";

    int[] sampleImages = {R.drawable.splash_logo};
    String[] sampleTitles = {"one"};
    String[] sampleNetworkImageURLs = {
            "https://adviratech.com/wp-content/uploads/2020/01/e-learning-banner.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imagezoomslider);

        initUI();

        carouselView.setPageCount(sampleImages.length);
        carouselView.setViewListener(viewListener);


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageZoomSliderActivityB2B.this.finish();

            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(ImageZoomSliderActivityB2B.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(ImageZoomSliderActivityB2B.this, SearchActivity.class);
                i.setClass(ImageZoomSliderActivityB2B.this, Search_one.class);
                startActivity(i);

            }
        });


        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

              /*  AlertDialog.Builder mBuilder = new AlertDialog.Builder(ImageZoomSliderActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.image_zoom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[0]).resize(width, 0).into(photoView);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();*/

                Intent i = new Intent();
                i.setClass(ImageZoomSliderActivityB2B.this, WebViewActivity.class);
                i.putExtra("header",productname);
                i.putExtra("url",sampleNetworkImageURLs[position]);
                i.putExtra("isrx",isrx);
                startActivity(i);

            }
        });
    }

    // To set simple images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[0]).fit().centerInside().into(imageView);

        }
    };

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);

            TextView labelTextView = (TextView) customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);
            fruitImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[0]).into(fruitImageView);
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

    private void initUI() {

        mContext = ImageZoomSliderActivityB2B.this;

        Bundle extras = getIntent().getExtras();
      
        if (extras != null) {
            productname = extras.getString("productname");
            productid= extras.getString("productid");
            isrx= extras.getString("isrx");

        }


        tv_cartcount = findViewById(R.id.tv_cartcount);
        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        carouselView = findViewById(R.id.customCarouselView);
        tv_prodname = findViewById(R.id.tv_prodname);
        tv_pd_header2=findViewById(R.id.tv_pd_header2);
        iv_rx = findViewById(R.id.iv_rx);
        //tv_prodname.setText(productname);
        tv_pd_header2.setText(productname);
        rl_cart.setVisibility(View.GONE);
        rl_search.setVisibility(View.GONE);
        tv_cartcount.setVisibility(View.GONE);

        if (isrx.equalsIgnoreCase("yes")) {

            iv_rx.setVisibility(View.VISIBLE);
        } else {
            iv_rx.setVisibility(View.INVISIBLE);
        }

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
       
        tv_cartcount.setText(cartcount);


        ProductDetails();


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        ImageZoomSliderActivityB2B.this.finish();
    }


    private void ProductDetails() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
        productDetailsRequest.setProductId(productid);

        try {

            Call<ProductDetailsResponse> call;

            String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

            if (profilemode.equalsIgnoreCase("B2C")) {
                call = RetrofitUrlConnection.loadJSON(token).product_detail_categorywise_b2c(productDetailsRequest);
            }
            else {
                call = RetrofitUrlConnection.loadJSON(token).product_detail_categorywise_b2b(productDetailsRequest);

            }
            //Call<ProductDetailsResponse> call = RetrofitUrlConnection.loadJSON(token).product_detail_categorywise_b2b(productDetailsRequest);

            call.enqueue(new Callback<ProductDetailsResponse>() {
                @Override
                public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        List<ProductBanner> productImagesList = new ArrayList<>();
                        productImagesList = response.body().getProductDetails().getProductUnit().get(0).getProductBanners();

                        try {
                            sampleNetworkImageURLs = new String[productImagesList.size()];
                            sampleTitles = new String[productImagesList.size()];
                            sampleImages = new int[productImagesList.size()];

                            for (int i = 0; i < productImagesList.size(); i++) {

                                sampleNetworkImageURLs[i] = productImagesList.get(i).getProductImageUrl();
                                sampleTitles[i] = productImagesList.get(i).getProductImageName();
                                sampleImages[i] = R.drawable.progress_animation;

                            }


                            if (productImagesList.size() > 0) {
                                carouselView.setVisibility(View.VISIBLE);
                            } else {
                                carouselView.setVisibility(View.GONE);
                            }

                            carouselView = findViewById(R.id.customCarouselView);
                            carouselView.setPageCount(sampleImages.length);
                            carouselView.setViewListener(viewListener);


                        } catch (Exception ex) {

                        }


                        Utilities.dismissDialog();

                    } else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


}
