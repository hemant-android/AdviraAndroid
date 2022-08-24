package com.advira.advirafarm.buyer.ui.product;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.order.OrderPlacedActivity;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.buynow.BuynowActivity;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.api.BuyNowRequest;
import com.advira.advirafarm.buyer.ui.cart.api.BuyNowResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartResponse;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.adapter.ProductUnitAdapter;
import com.advira.advirafarm.buyer.ui.product.adapter.ProductUnitAdapterB2B;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.product.api.ProductBanner;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsRequest;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsResponse;
import com.advira.advirafarm.buyer.ui.product.api.ProductUnit;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.profile.BusinessDetailsActivity;
import com.advira.advirafarm.buyer.ui.splash.Splash;
import com.advira.advirafarm.buyer.utility.MinMaxFilter;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailsActivityB2B extends AppCompatActivity implements IConsts {


    public static TextView tv_cartcount;
    public static LinearLayout ll_addremovebutton;
    public static RelativeLayout rl_addtocart;
    public static CarouselView carouselView;
    public static TextView tv_addto;
    public static TextView tv_price;
    public static TextView tv_mrpval;
    public static TextView tv_stock,tv_placeorder;
    public static RelativeLayout btn_buynow;
    static int[] sampleImages = {R.drawable.splash_logo};
    static String[] sampleTitles = {"one"};
    static String[] sampleNetworkImageURLs = {
            "https://adviratech.com/wp-content/uploads/2020/01/e-learning-banner.png"
    };
    private static Button btn_addtocart;
    private static Button btn_increase;
    private static Button btn_decrease;
    private static Button btn_addto;
    private static Button btn_add;
    public static  LinearLayout ll_addremove;
    String[] qty = {"Qty", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "more.."};
    String productid;
    AlertDialog.Builder builder;
    // To set simple images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

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
    ProductUnitAdapterB2B productUnitAdapter;
    private RelativeLayout rl_back, rl_search, rl_cart;
    private ImageView iv_rx;
    private TextView tv_prodname;
    private TextView tv_manufacturename;
    private TextView tv_minorderval;
    private TextView tv_pack;
    public static TextView tv_packsize;
    private TextView tv_productdescription;
    private TextView tv_qualityspec;
    private TextView tv_desclaimerval;
    private TextView tv_deliverystatus;
    private TextView tv_inr;
    private EditText et_pincode;
    public static EditText integer_number;
    private Button btn_check;
    public static Spinner spn_qty;
    private TextView tv_footertotal;
    private TextView tv_footertotalitem;
    private TextView tv_mrp;
    private TextView tv_minordervalunit;
    private RelativeLayout rl_content4;
    private RelativeLayout rl_content5;
    private RelativeLayout rl_content6;
    private String itemgst = "0";
    private int minteger = 10;
    private int moq = 10;
    private Context mContext;
    public static String imgurl = "";
    public static String gst;
    private String moreqty = "0";
    private String isrx = "";
    private String instock = "";
    public static TextView tv_discount;
    private RecyclerView unitrecyclerView;
    private List<ProductUnit> productUnitslist;
    public static TextView tv_unitid;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    public static void CarousalImageChange(List<ProductBanner> productImagesList) {


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

        // carouselView = findViewById(R.id.customCarouselView);
        carouselView.setPageCount(sampleImages.length);
        //  carouselView.setViewListener(viewListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_productdetails);

        initUI();

        carouselView.setPageCount(sampleImages.length);
        carouselView.setViewListener(viewListener);


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDetailsActivityB2B.this.finish();

            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(ProductDetailsActivityB2B.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(ProductDetailsActivityB2B.this, SearchActivity.class);
                i.setClass(ProductDetailsActivityB2B.this, Search_one.class);
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

               /* AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductDetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.image_zoom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[0]).resize(width, 0).into(photoView);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();*/

                Intent i = new Intent();
                i.setClass(ProductDetailsActivityB2B.this, ImageZoomSliderActivityB2B.class);
                i.putExtra("productname", tv_prodname.getText().toString());
                i.putExtra("productid", productid);
                i.putExtra("isrx", isrx);

                startActivity(i);

            }
        });
    }

    private void initUI() {

        mContext = ProductDetailsActivityB2B.this;

        Bundle extras = getIntent().getExtras();
        String productname = "";

        if (extras != null) {
            productname = extras.getString("productname");
            productid = extras.getString("productid");

        }


        unitrecyclerView = findViewById(R.id.unitrecyclerView);
        unitrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        unitrecyclerView.setNestedScrollingEnabled(false);


        btn_addto = findViewById(R.id.btn_addto);
        btn_decrease = findViewById(R.id.btn_decrease);
        btn_increase = findViewById(R.id.btn_increase);
        integer_number = findViewById(R.id.integer_number);
        btn_add = findViewById(R.id.btn_add);
        ll_addremovebutton = findViewById(R.id.ll_addremovebutton);
        ll_addremove = findViewById(R.id.ll_addremove);

        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setVisibility(View.GONE);
        carouselView = findViewById(R.id.customCarouselView);
        tv_prodname = findViewById(R.id.tv_prodname);
        //tv_prodname.setText(productname);
        tv_manufacturename = findViewById(R.id.tv_manufacturename);
        tv_price = findViewById(R.id.tv_price);
        tv_mrpval = findViewById(R.id.tv_mrpval);
        tv_stock = findViewById(R.id.tv_stock);
        tv_minorderval = findViewById(R.id.tv_minorderval);
        tv_pack = findViewById(R.id.tv_pack);
        tv_packsize = findViewById(R.id.tv_packsize);
        tv_discount = findViewById(R.id.tv_discount);
        tv_productdescription = findViewById(R.id.tv_productdescription);
        tv_qualityspec = findViewById(R.id.tv_qualityspec);
        tv_desclaimerval = findViewById(R.id.tv_desclaimerval);
        btn_check = findViewById(R.id.btn_check);
        et_pincode = findViewById(R.id.et_pincode);
        tv_deliverystatus = findViewById(R.id.tv_deliverystatus);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        tv_cartcount.setVisibility(View.GONE);
        tv_inr = findViewById(R.id.tv_inr);
        tv_addto = findViewById(R.id.btn_addto);
        rl_addtocart = findViewById(R.id.rl_addtocart);

        spn_qty = findViewById(R.id.spn_qty);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, qty);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_qty.setAdapter(adapter);


        btn_buynow = findViewById(R.id.btn_buynow);
        tv_placeorder=findViewById(R.id.tv_placeorder);

        rl_content4 = findViewById(R.id.rl_content4);
        rl_content5 = findViewById(R.id.rl_content5);
        rl_content6 = findViewById(R.id.rl_content6);

        tv_footertotal = findViewById(R.id.tv_footertotal);
        tv_footertotalitem = findViewById(R.id.tv_footertotalitem);
        tv_minordervalunit = findViewById(R.id.tv_minordervalunit);
        tv_unitid = findViewById(R.id.tv_unitid);

        iv_rx = findViewById(R.id.iv_rx);
        tv_mrp = findViewById(R.id.tv_mrp);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        if (useractive.equalsIgnoreCase("active")) {
            tv_inr.setVisibility(View.VISIBLE);
            tv_price.setVisibility(View.VISIBLE);
            tv_discount.setVisibility(View.VISIBLE);
            tv_mrp.setVisibility(View.GONE);
            tv_mrpval.setVisibility(View.VISIBLE);
            //tv_footertotal.setVisibility(View.VISIBLE);
            //tv_footertotalitem.setVisibility(View.VISIBLE);
            tv_footertotal.setVisibility(View.INVISIBLE);
            tv_footertotalitem.setVisibility(View.INVISIBLE);
            tv_mrpval.setBackgroundResource(R.drawable.strike_through);


        } else {
            tv_discount.setVisibility(View.GONE);
            tv_footertotal.setVisibility(View.INVISIBLE);
            tv_footertotalitem.setVisibility(View.INVISIBLE);
            tv_mrp.setVisibility(View.GONE);
            tv_mrpval.setVisibility(View.VISIBLE);

        }

        if (useractive.equalsIgnoreCase("guest")) {
            rl_cart.setVisibility(View.GONE);//sapna INVISIBLE
            tv_cartcount.setVisibility(View.GONE);//sapna INVISIBLE
            tv_mrp.setVisibility(View.GONE);
            tv_mrpval.setVisibility(View.INVISIBLE);
        }
        else {
            rl_cart.setVisibility(View.GONE);//sapna
            tv_cartcount.setVisibility(View.GONE);//sapna
            tv_mrp.setVisibility(View.GONE);
            tv_mrpval.setVisibility(View.VISIBLE);
        }

        //Text.setText(cartcount);

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.getMenu().getItem(0).setCheckable(false);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        /*View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge,
                        mbottomNavigationMenuView, false);
        //((TextView) cart_badge.findViewById(R.id.notifications_badge)).setText();*/

        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);

        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        rl_cart.setVisibility(View.GONE);
                        tv_cartcount.setVisibility(View.GONE);
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        return true;
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
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });



        ProductDetails();


        spn_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                } else {

                    //if (useractive.equalsIgnoreCase("active")) {
                        if (spn_qty.getSelectedItem().toString().equalsIgnoreCase("more..")) {

                            ShowQtyDialogue();
                            spn_qty.setSelection(0);
                        } else {
                            calculateprice(spn_qty.getSelectedItem().toString());
                        }
                    /*} else {


                    }
*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    minteger = Integer.parseInt(integer_number.getText().toString());

                    minteger = minteger + 1;
                    integer_number.setText(String.valueOf(minteger));

                    AddToCart( "1");
                    calculateprice(integer_number.getText().toString());

                    if (Integer.parseInt(integer_number.getText().toString()) >= moq) {
                        btn_decrease.setEnabled(true);
                    }

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        btn_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    if (Integer.parseInt(integer_number.getText().toString()) == moq) {



                        ll_addremovebutton.setVisibility(View.INVISIBLE);
                        ll_addremove.setVisibility(View.GONE);
                        CartDeleteRequest();

                    } else {
                        minteger = Integer.parseInt(integer_number.getText().toString());
                        minteger = minteger - 1;
                        integer_number.setText(String.valueOf(minteger));


                        if (minteger < 1) {

                            CartDeleteRequest();
                        } else {
                            UpdateCart( integer_number.getText().toString());
                            calculateprice(integer_number.getText().toString());

                        }


                    }


                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (useractive.equalsIgnoreCase("guest")) {

                    Intent i = new Intent();
                    i.setClass(ProductDetailsActivityB2B.this, LoginActivity.class);
                    finishAffinity();
                    startActivity(i);


                }
                else if (useractive.equalsIgnoreCase("active")) {
                    ll_addremovebutton.setVisibility(View.GONE);
                    ll_addremove.setVisibility(View.VISIBLE);

                    AddToCart( "1");
                } else {
                    CheckProfile(productid, spn_qty.getSelectedItem().toString(), "addtocart");
                }


            }
        });


        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_pincode.getText().toString().trim().length() > 5) {
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        CheckPin();

                    } else {
                        Utilities.showNetworkError(mContext);
                    }

                } else {
                    et_pincode.setError("Invalid Pincode");
                }


            }
        });

        btn_addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                // Singleton.getInstance().showLongToast(mContext, "Please select a valid quantity");

                int chkqty = 0;

                try {
                    chkqty = Integer.valueOf(spn_qty.getSelectedItem().toString());
                } catch (Exception ex) {

                }


                if (chkqty > 0) {
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        if (useractive.equalsIgnoreCase("guest")) {

                            Intent i = new Intent();
                            i.setClass(ProductDetailsActivityB2B.this, LoginActivity.class);
                            finishAffinity();
                            startActivity(i);
                        }
                        else if (useractive.equalsIgnoreCase("active")) {
                            AddToCart(spn_qty.getSelectedItem().toString());

                        } else {
                            CheckProfile(tv_unitid.getText().toString(), spn_qty.getSelectedItem().toString(), "addtocart");
                        }


                    } else {
                        Utilities.showNetworkError(mContext);
                    }

                } else {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(ProductDetailsActivityB2B.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Please select a valid quantity.");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                    //Singleton.getInstance().showLongToast(mContext, "Please select a valid quantity");
                }


            }
        });

        rl_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Singleton.getInstance().showLongToast(mContext, "Please select a valid quantity");
                integer_number.setVisibility(View.INVISIBLE);
                btn_increase.setVisibility(View.INVISIBLE);

                int chkqty = 0;

                try {
                    chkqty = Integer.valueOf(spn_qty.getSelectedItem().toString());
                } catch (Exception ex) {

                }


                if (chkqty > 0) {
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        if (useractive.equalsIgnoreCase("guest")) {

                            Intent i = new Intent();
                            i.setClass(ProductDetailsActivityB2B.this, LoginActivity.class);
                            finishAffinity();
                            startActivity(i);
                        }
                        else if (useractive.equalsIgnoreCase("active")) {
                            AddToCart(spn_qty.getSelectedItem().toString());

                        } else {
                            CheckProfile(tv_unitid.getText().toString(), spn_qty.getSelectedItem().toString(), "addtocart");
                        }


                    } else {
                        Utilities.showNetworkError(mContext);
                    }

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ProductDetailsActivityB2B.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Please select a valid quantity.");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                    //Singleton.getInstance().showLongToast(mContext, "Please select a valid quantity");
                }


            }
        });

        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (integer_number.getText().toString().length() > 0) {
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        if (useractive.equalsIgnoreCase("guest")) {

                            Intent i = new Intent();
                            i.setClass(ProductDetailsActivityB2B.this, LoginActivity.class);
                            finishAffinity();
                            startActivity(i);
                        }
                        else if (useractive.equalsIgnoreCase("active")) {
                            // BuyNow(productid, integer_number.getText().toString());
                            BuyNow(tv_unitid.getText().toString(), integer_number.getText().toString());

                        } else {
                            CheckProfile(tv_unitid.getText().toString(), integer_number.getText().toString(), "addtocart");
                        }

                    } else {
                        Utilities.showNetworkError(mContext);
                    }

                } else {
                    integer_number.setError("Enter Quantity");
                }
            }
        });

        tv_mrpval.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    calculateprice("1");
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        ProductDetailsActivityB2B.this.finish();
    }

    private void CheckProfile(String productid, String qty, String source) {

        Utilities.showLoading(mContext);
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
                            ShowAlert(popmsg);
                        } else {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "active");

                            if (source.equalsIgnoreCase("buynow")) {
                                BuyNow(productid, qty);
                            } else if (source.equalsIgnoreCase("addtocart")) {
                                AddToCart( qty);
                            }

                        }

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

    private void CheckPin() {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CheckPinRequest checkPinRequest = new CheckPinRequest();
        checkPinRequest.setPincode(et_pincode.getText().toString());

        try {

            Call<CheckPinResponse> call = RetrofitUrlConnection.loadJSON(token).pincode(checkPinRequest);

            call.enqueue(new Callback<CheckPinResponse>() {
                @Override
                public void onResponse(Call<CheckPinResponse> call, Response<CheckPinResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        if (response.body().getPincodeDetails().size() > 0) {
                            if (response.body().getPincodeDetails().get(0).getIsOperational().equalsIgnoreCase("yes")) {

                                tv_deliverystatus.setVisibility(View.VISIBLE);
                                tv_deliverystatus.setText("Expected delivery in " + response.body().getPincodeDetails().get(0).getApproxDeliveryTime());

                                tv_deliverystatus.setTextColor(getResources().getColor(R.color.colorGreen));
                            }
                        } else {
                            tv_deliverystatus.setVisibility(View.VISIBLE);
                            tv_deliverystatus.setText("We are currently not operational in this location");
                            tv_deliverystatus.setTextColor(getResources().getColor(R.color.colorRed));
                        }

                        Utilities.dismissDialog();

                    } else {

                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<CheckPinResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    private void AddToCart( String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductUnitId(tv_unitid.getText().toString());
        cartRequest.setProductQuantity(qty);
        cartRequest.setUserCartType(usermode);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).addtocart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove Toast
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());
                        text.setText(response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        Utilities.dismissDialog();

                    } else {

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove Toast
                    }

                    Utilities.dismissDialog();
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void BuyNow(String productid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

        BuyNowRequest buyNowRequest = new BuyNowRequest();
        buyNowRequest.setProductUnitId(productid);
        buyNowRequest.setProductQuantity(qty);
        buyNowRequest.setUserCartType(usermode);

        try {

            Call<BuyNowResponse> call = RetrofitUrlConnection.loadJSON(token).buynow(buyNowRequest);

            call.enqueue(new Callback<BuyNowResponse>() {
                @Override
                public void onResponse(Call<BuyNowResponse> call, Response<BuyNowResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        gst = response.body().getCartDate().getProductTax().replaceAll("%", "");

                        Utilities.dismissDialog();

                        String addressid = "";
                        String address = "No address found";

                        if (response.body().getDefaultAddress() != null && response.body().getDefaultAddress().size() > 0) {

                            address = response.body().getDefaultAddress().get(0).getAddress()
                                    + " " + response.body().getDefaultAddress().get(0).getAddress2() + " " +
                                    response.body().getDefaultAddress().get(0).getCityName() + ", " +
                                    response.body().getDefaultAddress().get(0).getStateName() + " " +
                                    response.body().getDefaultAddress().get(0).getPincode();

                            addressid = response.body().getDefaultAddress().get(0).getId();
                        }


                        Intent i = new Intent();
                        i.setClass(ProductDetailsActivityB2B.this, BuynowActivity.class);
                        i.putExtra("productname", tv_prodname.getText().toString());
                        i.putExtra("productdetails", tv_productdescription.getText().toString());
                        i.putExtra("mrpval", tv_mrpval.getText().toString());
                        i.putExtra("price", tv_price.getText().toString());
                        i.putExtra("qty", integer_number.getText().toString());
                        i.putExtra("packsize", tv_pack.getText().toString());
                        i.putExtra("imgurl", imgurl.toString());
                        i.putExtra("pack", tv_packsize.getText().toString());
                        i.putExtra("stock", tv_stock.getText().toString());
                        i.putExtra("gstval", gst.toString());
                        i.putExtra("address", address);
                        i.putExtra("addressid", addressid);
                        i.putExtra("isrx", isrx);
                        i.putExtra("mrplabel", tv_mrp.getText());
                        i.putExtra("ratelabel", tv_inr.getText());
                        i.putExtra("moqunit", tv_minordervalunit.getText());
                        i.putExtra("discountlabel", tv_discount.getText());
                        startActivity(i);


                    } else {
                        Utilities.dismissDialog();

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove Toast
                    }

                    Utilities.dismissDialog();
                }

                @Override
                public void onFailure(Call<BuyNowResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }



    private void UpdateCart(String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductUnitId(tv_unitid.getText().toString());
        cartRequest.setProductQuantity(qty);
        cartRequest.setUserCartType(usermode);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).updatecart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showShortToast(mContext, "Cart updated successfully");//remove toast

                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        //OrderPlacedActivity.tv_cartcount.setText(response.body().getCartSize().toString());
                        //SearchActivity.tv_cartcount.setText(response.body().getCartSize().toString());

                        Utilities.dismissDialog();


                    } else {

                       // Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    private void CartDeleteRequest() {

        Utilities.showLoading(mContext);
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        String deletetype="list";

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest();
        cartDeleteRequest.setProductUnitId(tv_unitid.getText().toString());
        //cartDeleteRequest.setCartProductId(tv_unitid.getText().toString());
        cartDeleteRequest.setUserCartType(usermode);
        cartDeleteRequest.setDeletefrom(deletetype);

        try {

            Call<CartDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).delete_from_cart(cartDeleteRequest);

            call.enqueue(new Callback<CartDeleteResponse>() {
                @Override
                public void onResponse(@NotNull Call<CartDeleteResponse> call, @NotNull Response<CartDeleteResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                       // tv_cartcount.setText(response.body().getCartSize().toString());
                        text.setText(response.body().getCartSize().toString());

                        Utilities.dismissDialog();
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove Toast


                    } else {
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove Toast
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<CartDeleteResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }




    private void ProductDetails() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
        productDetailsRequest.setProductId(productid);

        try {

            Call<ProductDetailsResponse> call = RetrofitUrlConnection.loadJSON(token).product_detail_categorywise_b2b(productDetailsRequest);

            call.enqueue(new Callback<ProductDetailsResponse>() {
                @Override
                public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        productUnitslist = new ArrayList<>();

                        productUnitAdapter = new ProductUnitAdapterB2B(mContext, productUnitslist);

                        List<ProductUnit> mListData = response.body().getProductDetails().getProductUnit();

                        if (mListData != null && mListData.size() > 0) {
                            productUnitslist.addAll(mListData);


                        }


                        unitrecyclerView.setAdapter(productUnitAdapter);
                        unitrecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        unitrecyclerView.setNestedScrollingEnabled(false);


                        tv_manufacturename.setText(response.body().getProductDetails().getProductBrandName());

                        double price = 0;
                        double mrp = 0;
                        try {
                            mrp = Double.valueOf(response.body().getProductDetails().getProductUnit().get(0).getProductMrp());

                            price = Double.valueOf(response.body().getProductDetails().getProductUnit().get(0).getProductSalesprice());

                        } catch (Exception ex) {

                        }

                        DecimalFormat form = new DecimalFormat("0.00");

                        tv_prodname.setText(response.body().getProductDetails().getProductName());
                        tv_price.setText(form.format(price));
                        tv_mrpval.setText(form.format(mrp));
                        tv_stock.setText(response.body().getProductDetails().getProductUnit().get(0).getProductInstock());

                        tv_minorderval.setText("1");
                        tv_minordervalunit.setText(" ");
                        tv_packsize.setText(response.body().getProductDetails().getProductUnit().get(0).getProductUnits() + " " + response.body().getProductDetails().getProductUnit().get(0).getProductUnitType());
                        // tv_packsize.setText("1");
                        tv_inr.setText("Rate  : ₹ ");
                        tv_mrp.setText("MRP : ₹ ");
                        tv_discount.setText(response.body().getProductDetails().getProductUnit().get(0).getProductDiscountLabel());
                        tv_productdescription.setText(response.body().getProductDetails().getProductDescreption());
                        tv_qualityspec.setText(response.body().getProductDetails().getProductQualitySpecification());
                        tv_desclaimerval.setText(response.body().getProductDetails().getProductDisclaimer());

                        tv_unitid.setText(response.body().getProductDetails().getProductUnit().get(0).getProductUnitsId());
                        instock = response.body().getProductDetails().getProductUnit().get(0).getProductInstock();

                        if (instock.equalsIgnoreCase("Out-of-Stock")) {

                            tv_stock.setVisibility(View.VISIBLE);
                            ll_addremovebutton.setVisibility(View.INVISIBLE);
                            rl_addtocart.setEnabled(false);
                            btn_buynow.setEnabled(false);
                            tv_placeorder.setText("Out of Stock");
                            //btn_addto.setVisibility(View.INVISIBLE);

                        } else {
                            tv_stock.setVisibility(View.INVISIBLE);
                            ll_addremovebutton.setVisibility(View.INVISIBLE);
                            btn_buynow.setVisibility(View.VISIBLE);
                            rl_addtocart.setVisibility(View.VISIBLE);
                        }
                        /*tv_qualityspec.setText(response.body().getProductDetails().getProductDosage());
                        tv_desclaimerval.setText(response.body().getProductDetails().getProductUses());*/

                        minteger = Integer.valueOf(1);
                        moq = Integer.valueOf(1);
                        integer_number.setText("1");
                        integer_number.setFilters(new InputFilter[]{new MinMaxFilter(String.valueOf(moq), "9999")});

                        itemgst = response.body().getProductDetails().getProductUnit().get(0).getProductTax().replaceAll("%", "");
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
                            imgurl = response.body().getProductDetails().getProductUnit().get(0).getProductBanners().get(0).getProductImageUrl();


                            isrx = "no";

                            if (isrx.equalsIgnoreCase("yes")) {

                                iv_rx.setVisibility(View.VISIBLE);
                            } else {
                                iv_rx.setVisibility(View.INVISIBLE);
                            }



                            String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

                            if (useractive.equalsIgnoreCase("active")) {
                                calculateprice(tv_minorderval.getText().toString());
                            }


                        } catch (Exception ex) {

                        }


                        Utilities.dismissDialog();
                        // ShowQtyDialogue();
                    } else {
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
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
                            i.setClass(ProductDetailsActivityB2B.this, PersonalDetailsEditActivity.class);
                            i.putExtra("status", "inactive");
                            startActivity(i);
                        } else if (ShowAlert.contains("Business details")) {

                            Intent i = new Intent();
                            i.setClass(ProductDetailsActivityB2B.this, BusinessDetailsActivity.class);
                            i.putExtra("status", "inactive");
                            startActivity(i);
                        } else if (ShowAlert.contains("Upload KYC documents")) {

                            Intent i = new Intent();
                            i.setClass(ProductDetailsActivityB2B.this, DocumentUploadActivity.class);
                            i.putExtra("status", "inactive");
                            startActivity(i);
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

    private void ShowQtyDialogue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


        alertDialog.setTitle("Quantity");
        alertDialog.setMessage("Add Quantity");


        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(4);


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 30, 0);


        EditText input = new EditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(FilterArray);
        input.setHint("Enter Qty");
        layout.addView(input, params);
        alertDialog.setView(layout);


        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        moreqty = input.getText().toString();

                        if (moreqty.length() > 0) {


                            double itemqty = Double.parseDouble(moreqty);
                            double itemprice = Double.parseDouble(tv_price.getText().toString());
                            // double boxsize = Double.parseDouble(tv_pack.getText().toString());
                            double boxsize = Double.parseDouble("1");


                            double totalitemprice = boxsize * itemqty * itemprice;
                            double itemtax = Double.parseDouble(itemgst);
                            double totaltax = boxsize * itemqty * itemprice * itemtax * .01;


                            double amountpayable = totalitemprice + totaltax;

                            String flag = String.valueOf(amountpayable);
                            int index = flag.indexOf(".");
                            flag = flag.substring(index + 1, index + 2);
                            int chk = Integer.valueOf(flag);

                            if (chk > 4) {
                                double d = amountpayable;
                                int max = (int) Math.ceil(d);
                                String totalval = String.valueOf(max);
                                amountpayable = Double.parseDouble(totalval);

                            } else {
                                double d = amountpayable;
                                int max = (int) Math.floor(d);
                                String totalval = String.valueOf(max);
                                amountpayable = Double.parseDouble(totalval);

                            }

                            DecimalFormat form = new DecimalFormat("0.00");

                            int chkqty = Integer.valueOf(moreqty);

                            if (chkqty > 0) {
                                integer_number.setText(moreqty.toString());

                                if (instock.equalsIgnoreCase("Out-of-Stock")) {

                                    tv_stock.setVisibility(View.VISIBLE);
                                    ll_addremovebutton.setVisibility(View.INVISIBLE);
                                    btn_buynow.setVisibility(View.INVISIBLE);
                                    //btn_addto.setVisibility(View.INVISIBLE);
                                    rl_addtocart.setVisibility(View.INVISIBLE);
                                    ll_addremove.setVisibility(View.INVISIBLE);


                                } else {
                                    tv_stock.setVisibility(View.INVISIBLE);
                                    //ll_addremovebutton.setVisibility(View.VISIBLE);
                                    btn_buynow.setVisibility(View.VISIBLE);
                                    rl_addtocart.setVisibility(View.VISIBLE);
                                    //ll_addremove.setVisibility(View.VISIBLE);
                                }


                                //btn_buynow.setVisibility(View.VISIBLE);
                                tv_footertotal.setText("₹ " + form.format(amountpayable));
                                tv_footertotalitem.setText(moreqty + " ITEMS");
                                Utilities.hideKeyboard(mContext);
                                input.clearFocus();

                                String[] qty = {"Qty", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", moreqty, "more.."};

                                ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, qty);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_qty.setAdapter(adapter);
                                spn_qty.setSelection(11);


                            } else {
                                //integer_number.setText("");
                                btn_buynow.setVisibility(View.GONE);
                                rl_addtocart.setVisibility(View.GONE);
                                Utilities.hideKeyboard(mContext);
                                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(ProductDetailsActivityB2B.this);
                                alert.setTitle("Alert!!");
                                alert.setMessage("Please select a valid quantity.");
                                alert.setPositiveButton("OK",null);
                                alert.show();
                                //Singleton.getInstance().showShortToast(mContext, "Please enter a valid quantity");
                                input.clearFocus();
                            }

                            //calculateprice(moreqty);
                        }
                        // Singleton.getInstance().showShortToast(mContext, moreqty);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

/*
    private void UpdateCart(String productid, String qty) {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductId(productid);
        cartRequest.setProductQuantity(qty);
        try {
            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).updatecart(cartRequest);
            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Singleton.getInstance().showShortToast(mContext, "Cart updated successfully");
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        Utilities.dismissDialog();
                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
    private void CartDeleteRequest(String productid) {
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
        productDetailsRequest.setProductId(productid);
        try {
            Call<CartDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).delete_from_cart(productDetailsRequest);
            call.enqueue(new Callback<CartDeleteResponse>() {
                @Override
                public void onResponse(Call<CartDeleteResponse> call, Response<CartDeleteResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        tv_cartcount.setText(response.body().getCartSize().toString());
                        Utilities.dismissDialog();
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();
                    }
                }
                @Override
                public void onFailure(Call<CartDeleteResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
*/

    private void calculateprice(String moreqty) {

        try {

            if (moreqty.length() > 0) {


                String aaa = tv_price.getText().toString();

                double itemqty = Double.parseDouble(moreqty);
                double itemprice = Double.parseDouble(tv_price.getText().toString());
                double boxsize = Double.parseDouble("1");


                double totalitemprice = boxsize * itemqty * itemprice;
                double itemtax = Double.parseDouble(itemgst);
                double totaltax = boxsize * itemqty * itemprice * itemtax * .01;


                // double amountpayable = totalitemprice + totaltax;
                double amountpayable = totalitemprice ;

                String flag = String.valueOf(amountpayable);
                int index = flag.indexOf(".");
                flag = flag.substring(index + 1, index + 2);
                int chk = Integer.valueOf(flag);

                if (chk > 4) {
                    double d = amountpayable;
                    int max = (int) Math.ceil(d);
                    String totalval = String.valueOf(max);
                    amountpayable = Double.parseDouble(totalval);

                } else {
                    double d = amountpayable;
                    int max = (int) Math.floor(d);
                    String totalval = String.valueOf(max);
                    amountpayable = Double.parseDouble(totalval);

                }

                DecimalFormat form = new DecimalFormat("0.00");

                int chkqty = Integer.valueOf(moreqty);

                if (chkqty > 0) {
                    integer_number.setText(moreqty.toString());

                   /* if (instock.equalsIgnoreCase("Out-of-Stock")) {
                        tv_stock.setVisibility(View.VISIBLE);
                        ll_addremovebutton.setVisibility(View.INVISIBLE);
                        btn_buynow.setVisibility(View.INVISIBLE);
                    } else {
                        tv_stock.setVisibility(View.INVISIBLE);
                        ll_addremovebutton.setVisibility(View.VISIBLE);
                        btn_buynow.setVisibility(View.VISIBLE);
                    }*/

                    // btn_buynow.setVisibility(View.VISIBLE);
                    tv_footertotal.setText("₹ " + form.format(amountpayable));
                    tv_footertotalitem.setText(moreqty + " ITEMS");
                    Utilities.hideKeyboard(mContext);

                } else {
                    integer_number.setText("");
                    btn_buynow.setVisibility(View.GONE);
                    rl_addtocart.setVisibility(View.GONE);
                    Utilities.hideKeyboard(mContext);
                    //Singleton.getInstance().showShortToast(mContext, "Please enter a valid quantity");

                }
            }
        } catch (Exception ex) {

        }
    }

}