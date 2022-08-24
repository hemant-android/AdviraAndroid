package com.advira.advirafarm.buyer.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.adapter.CategoryListAdapter;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_1;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductbycategoryListRequest;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductbycategoryListResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductActivity extends AppCompatActivity implements IConsts {

    public static TextView tv_cartcount;
    CategoryListAdapter searchListAdapter;
    private RelativeLayout rl_back, rl_search, rl_cart;
    private RecyclerView recyclerView;
    private Context mContext;
    private List<Product_home> orderList;
    private TextView tv_pd_header2;
    private String profilemode="B2B";
    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    int page=1,limit=10;
    ProgressBar progressbar;
    String categoryid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categoryproducts);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryProductActivity.this.finish();
            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(CategoryProductActivity.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(CategoryProductActivity.this, SearchActivity.class);
                i.setClass(CategoryProductActivity.this, Search_one.class);
                startActivity(i);

            }
        });

    }


    private void initUI() {

        mContext = CategoryProductActivity.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);
        progressbar=findViewById(R.id.progress_bar);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0


        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        if (useractive.equalsIgnoreCase("guest")) {
            rl_cart.setVisibility(View.GONE);//sapna
            tv_cartcount.setVisibility(View.GONE);//sapna
        }
        else {
            rl_cart.setVisibility(View.GONE);//sapna
            tv_cartcount.setVisibility(View.GONE);//sapna
        }

        recyclerView = findViewById(R.id.recyclerView);
        tv_pd_header2 = findViewById(R.id.tv_pd_header2);
        recyclerView.setHasFixedSize(false);//true
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.category);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.getMenu().getItem(0).setCheckable(false);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);
        View view = mbottomNavigationMenuView.getChildAt(4);
        BottomNavigationItemView itemView = (BottomNavigationItemView) view;
        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);
        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        text.setText(cartcount);
        itemView.addView(cart_badge);
        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){
                    case R.id.category:
                        if (profilemode.equalsIgnoreCase("B2C")) {
                            rl_cart.setVisibility(View.GONE);
                            tv_cartcount.setVisibility(View.GONE);
                        } else {
                            rl_cart.setVisibility(View.GONE);
                            tv_cartcount.setVisibility(View.GONE);
                        }
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_subscription:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MySubscription.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.home_wallet:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                }
                return true;
            }
        });
        Bundle extras = getIntent().getExtras();
        categoryid = "";
        String category = "";
        if (extras != null) {
            categoryid = extras.getString("categoryid");
            category = extras.getString("category");
        }
        tv_pd_header2.setText(category);
        ProductListRequest(categoryid);
    }

    private void ProductListRequest(String categoryid) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        recyclerView.setHasFixedSize(false);//true
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        ProductbycategoryListRequest productbycategoryListRequest=new ProductbycategoryListRequest();
        productbycategoryListRequest.setCategoryid(categoryid);
        Gson gson = new Gson();
        String vakk = gson.toJson(productbycategoryListRequest).toString();

        try{
            Call<ProductbycategoryListResponse> call=RetrofitUrlConnection.loadJSON(token).productbycategoryidb2c_v2(productbycategoryListRequest,page,limit);
           // Call<ProductbycategoryListResponse> call;
            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
            call.enqueue(new Callback<ProductbycategoryListResponse>() {
                @Override
                public void onResponse(Call<ProductbycategoryListResponse> call, Response<ProductbycategoryListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        //progressbar.setVisibility(View.GONE);
                        orderList = new ArrayList<>();
                        searchListAdapter = new CategoryListAdapter(mContext, orderList);
                        List<ProductList_1> mListData = response.body().getProductList();
                        if (mListData != null && mListData.size() > 0) {
                            for (int i = 0; i < mListData.size(); i++) {
                                if (mListData.get(i).getCategoryId().equalsIgnoreCase(categoryid)) {
                                    List<Product_home> mListDataP = mListData.get(i).getProducts();
                                    orderList.addAll(mListDataP);
                                }
                            }
                        }
                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                        // AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(mContext, 500);
                        //recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemViewCacheSize(20);
                        recyclerView.setDrawingCacheEnabled(true);
                        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        //searchListAdapter.notifyItemRangeInserted(rangeStart, rangeEnd);
                        searchListAdapter.setHasStableIds(true);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(searchListAdapter);
                    } else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<ProductbycategoryListResponse> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }

}