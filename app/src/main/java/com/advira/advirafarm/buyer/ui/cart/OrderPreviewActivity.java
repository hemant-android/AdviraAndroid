package com.advira.advirafarm.buyer.ui.cart;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddressList;
import com.advira.advirafarm.buyer.ui.cart.adapter.OrderPreviewAdapter;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.cart.api.Deliverycharges;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountRequest;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountResponse;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.deliverySlot.DeliverySlotActivity;
import com.advira.advirafarm.buyer.ui.discount.DiscountListActivity;
import com.advira.advirafarm.buyer.ui.discount.api.CouponValidationRequest;
import com.advira.advirafarm.buyer.ui.discount.api.CouponValidationResponse;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipFragment;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.ChooseAddressListNav;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class OrderPreviewActivity extends AppCompatActivity implements IConsts {


    public static RecyclerView recyclerView;
    public static TextView tv_priceval, tv_gstval, tv_totalpaidval, tv_rv1, tv_clientname,
            tv_pinval, tv_addid, tv_footertotal, tv_footertotalitem,
            tv_discountid, tv_discount_coupon_name, tv_discount_type,
            tv_discount_amount, tv_discount_details, tv_credit_limit,
            tv_credit_availed, tv_credit_balance,tv_selectdiscount,tv_deliveryval,tv_buyplan;
    public static RelativeLayout rl_content2;
    OrderPreviewAdapter cartListAdapter;
    double discountval = 0;
    private RelativeLayout rl_back, rl_search, rl_cart,ll_header;
    private TextView tv_cartcount;
    private Context mContext;
    private Button btn_change;
    private RelativeLayout btn_buynow;
    private List<CartDatum> cartList;
    public static TextView tv_discount;
    public static TextView tv_discountval;
    private String discountid = "";
    private String discount_coupon_name = "";
    private String discount_type = "";
    private String discount_amount = "";
    private String discount_details = "";
    private String credit_limit = "";
    private String credit_availed = "";
    private String credit_balance = "";
    private RelativeLayout rl_coupon, rl_membershipAds;
    public static Button btn_apply,btn_remove;
    public static EditText et_couponcode;
    public CheckBox checkbox_cheese;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;
    String profilemode="";
    String usertype="";
    double delicharge=0;
    String  membershipName;

    /*String Delivery_time="";
    String Delivery_date="";
*/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpreview);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderPreviewActivity.this.finish();
            }
        });




        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(OrderPreviewActivity.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(OrderPreviewActivity.this, SearchActivity.class);
                i.setClass(OrderPreviewActivity.this, Search_one.class);
                startActivity(i);
            }
        });

        tv_selectdiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent();
                //et_couponcode.setError(null);
                i.setClass(OrderPreviewActivity.this, DiscountListActivity.class);
                startActivity(i);
            }
        });

        tv_buyplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment;
                bottomSheetDialogFragment = MembershipFragment.newInstance("Bottom Sheet Dialog");
                bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.getTag());
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Change or Add New Address");
                    builder.setMessage("Select address for delivery.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SharedPrefUtil.setAddressType(mContext, SHARED_PREF_ADDRESSTYPE, "select");
                                    Intent i = new Intent();
                                    i.setClass(OrderPreviewActivity.this, ChooseAddressList.class);

                                    startActivity(i);


                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();

                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    if (!tv_pinval.getText().toString().equalsIgnoreCase("No address found.")) {

                        String setTotalAmount = "";
                        String setTotalTax = "";
                        String setGrandTotalAmount = "";

                        String discountid = "";
                        String discount_coupon_name = "";
                        String discount_type = "";
                        String discount_amount = "";
                        String discount_details = "";
                        String credit_limit = "";
                        String credit_availed = "";
                        String credit_balance = "";
                        String totaldiscount = "";
                        String deliverycharges="";

                        setTotalAmount = tv_priceval.getText().toString().replace("₹ ", "");
                        setTotalTax = tv_gstval.getText().toString().replace("+ ₹ ", "");
                        setGrandTotalAmount = tv_totalpaidval.getText().toString().replace("₹ ", "");
                        discountid = tv_discountid.getText().toString();
                        discount_coupon_name = tv_discount_coupon_name.getText().toString();
                        discount_type = tv_discount_type.getText().toString();
                        discount_amount = tv_discount_amount.getText().toString();
                        discount_details = tv_discount_details.getText().toString();
                        credit_limit = tv_credit_limit.getText().toString();
                        credit_availed = tv_credit_availed.getText().toString();
                        credit_balance = tv_credit_balance.getText().toString();
                        totaldiscount = tv_discountval.getText().toString().replace("- ₹ ","");
                        deliverycharges=tv_deliveryval.getText().toString().replace("+ ₹ ","");

                        if(membershipName!=null && membershipName.length()>0){
                            Intent i = new Intent();
                            i.setClass(OrderPreviewActivity.this, DeliverySlotActivity.class);
                            i.putExtra("addressid", tv_addid.getText().toString());
                            i.putExtra("address", tv_pinval.getText().toString());
                            i.putExtra("ordertype", "AddtoCart");
                            i.putExtra("totalamount", setTotalAmount);
                            i.putExtra("totaltax", setTotalTax);
                            i.putExtra("totaldiscount", totaldiscount);
                            i.putExtra("grandtotal", setGrandTotalAmount);
                            i.putExtra("orderid", "");
                            i.putExtra("discountid", discountid);
                            i.putExtra("discount_coupon_name", discount_coupon_name);
                            i.putExtra("discount_type", discount_type);
                            i.putExtra("discount_amount", discount_amount);
                            i.putExtra("discount_details", discount_details);
                            i.putExtra("credit_limit", credit_limit);
                            i.putExtra("credit_availed", credit_availed);
                            i.putExtra("credit_balance", credit_balance);
                            i.putExtra("delivery_charges", deliverycharges);
                            startActivity(i);
                        }else {
                            Intent i = new Intent();
                            i.setClass(OrderPreviewActivity.this, PaymentOption.class);
                            i.putExtra("addressid", tv_addid.getText().toString());
                            i.putExtra("address", tv_pinval.getText().toString());
                            i.putExtra("ordertype", "AddtoCart");
                            i.putExtra("totalamount", setTotalAmount);
                            i.putExtra("totaltax", setTotalTax);
                            i.putExtra("totaldiscount", totaldiscount);
                            i.putExtra("grandtotal", setGrandTotalAmount);
                            i.putExtra("orderid", "");
                            i.putExtra("discountid", discountid);
                            i.putExtra("discount_coupon_name", discount_coupon_name);
                            i.putExtra("discount_type", discount_type);
                            i.putExtra("discount_amount", discount_amount);
                            i.putExtra("discount_details", discount_details);
                            i.putExtra("credit_limit", credit_limit);
                            i.putExtra("credit_availed", credit_availed);
                            i.putExtra("credit_balance", credit_balance);
                            i.putExtra("delivery_charges", deliverycharges);
                            i.putExtra("delivery_slot_date","NA");
                            i.putExtra("delivery_time","NA");
                            startActivity(i);
                        }

                    } else {
                        Singleton.getInstance().showLongToast(mContext, "Select a delivery address");

                    }
                    // OrderPlace();
                } else {
                    Utilities.showNetworkError(mContext);
                }
            }
        });

        tv_discount_coupon_name.addTextChangedListener(new TextWatcher() {

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
                {
                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if(profilemode.equalsIgnoreCase("B2C"))
                    {
                        //et_couponcode.clearFocus();
                        validateDiscount(tv_discount_coupon_name.getText().toString());

                    }
                }
                Utilities.dismissDialog();
            }
        });



        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_couponcode.getText().toString().length()<3)
                {
                    et_couponcode.setError("Enter a valid couponcode");
                    et_couponcode.requestFocus();
                }
                else
                {
                    //et_couponcode.clearFocus();
                    validateDiscount(et_couponcode.getText().toString());

                }

            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_apply.setVisibility(View.VISIBLE);
                btn_remove.setVisibility(View.GONE);
                et_couponcode.setEnabled(true);
                et_couponcode.setText("");
                tv_selectdiscount.setVisibility(View.VISIBLE);

                String reversediscount = tv_discount_amount.getText().toString();
                String delicharge=tv_deliveryval.getText().toString();
                String totalamt=tv_totalpaidval.getText().toString();
                reversediscount="-"+reversediscount;

                CalculatePrice(reversediscount);

            }
        });
    }

    private void initUI() {

        mContext = OrderPreviewActivity.this;
        membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");
        recyclerView = findViewById(R.id.recyclerView);
        tv_priceval = findViewById(R.id.tv_priceval);
        tv_gstval = findViewById(R.id.tv_gstval);
        tv_totalpaidval = findViewById(R.id.tv_totalpaidval);
        rl_content2 = findViewById(R.id.rl_content2);
        tv_rv1 = findViewById(R.id.tv_rv1);
        tv_pinval = findViewById(R.id.tv_pinval);
        tv_clientname = findViewById(R.id.tv_clientname);
        tv_addid = findViewById(R.id.tv_addid);
        tv_footertotal = findViewById(R.id.tv_footertotal);
        tv_footertotalitem = findViewById(R.id.tv_footertotalitem);
        tv_deliveryval=findViewById(R.id.tv_deliveryval);
        rl_membershipAds=findViewById(R.id.rl_membershipAds);
        tv_buyplan=findViewById(R.id.tv_buyplan);
        checkbox_cheese=findViewById(R.id.checkbox_cheese);



        if(membershipName!=null && membershipName.length()>0) {
            rl_membershipAds.setVisibility(View.GONE);
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        btn_change = findViewById(R.id.btn_change);
        btn_buynow = findViewById(R.id.btn_buynow);

        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount.setVisibility(View.GONE);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");

        tv_discount = findViewById(R.id.tv_discount);
        tv_discountval = findViewById(R.id.tv_discountval);
        tv_discountid = findViewById(R.id.tv_discountid);
        tv_discount_coupon_name = findViewById(R.id.tv_discount_coupon_name);
        tv_discount_type = findViewById(R.id.tv_discount_type);
        tv_discount_amount = findViewById(R.id.tv_discount_amount);
        tv_discount_details = findViewById(R.id.tv_discount_details);
        tv_credit_limit = findViewById(R.id.tv_credit_limit);
        tv_credit_availed = findViewById(R.id.tv_credit_availed);
        tv_credit_balance = findViewById(R.id.tv_credit_balance);
        tv_selectdiscount = findViewById(R.id.tv_selectdiscount);
        rl_coupon = findViewById(R.id.rl_coupon);
        btn_apply = findViewById(R.id.btn_apply);
        btn_remove = findViewById(R.id.btn_remove);
        et_couponcode = findViewById(R.id.et_couponcode);
        ll_header=findViewById(R.id.ll_header);
        btn_remove.setEnabled(false);


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        bottomnavview.getMenu().getItem(0).setCheckable(false);
        //bottomnavview.setSelectedItemId(R.id.home_btmnav);
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
                profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
                switch (item.getItemId()){

                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), MainActivityGuestNav.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                            overridePendingTransition(0,0);
                        }

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
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();
                return true;
            }
        });
        CartListRequest();
    }

    public void CartListRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

        CartListRequest cartListRequest=new CartListRequest();
        cartListRequest.setUserCartType(profilemode);
        try {

            Call<CartListResponse> call = RetrofitUrlConnection.loadJSON(token).getmycart(cartListRequest);

            call.enqueue(new Callback<CartListResponse>() {
                @Override
                public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        cartList = new ArrayList<>();
                        cartListAdapter = new OrderPreviewAdapter(mContext, cartList);

                        if (response.body().getDefaultAddress() != null && response.body().getDefaultAddress().size() > 0) {

                            String address = response.body().getDefaultAddress().get(0).getAddress()
                                    + " " + response.body().getDefaultAddress().get(0).getAddress2() + " " +
                                    response.body().getDefaultAddress().get(0).getCityName() + ", " +
                                    response.body().getDefaultAddress().get(0).getStateName() + " " +
                                    response.body().getDefaultAddress().get(0).getPincode();
                            tv_pinval.setText(address);
                            tv_addid.setText(response.body().getDefaultAddress().get(0).getId());
                        } else {
                            tv_pinval.setText("No address found.");
                        }

                        List<Deliverycharges> mListdeliveryData = response.body().getDeliverycharges();
                        double ordervalue;

                        ordervalue= Double.parseDouble(response.body().getOrderValue().toString());
                        double orderdist=ordervalue-((ordervalue*10)/100);

                        if (mListdeliveryData != null && mListdeliveryData.size() > 0) {
                            try {
                                for (int i = 0; i < mListdeliveryData.size(); i++) {
                                    double lvalue;
                                    double gvalue;
                                    lvalue=Double.parseDouble(mListdeliveryData.get(i).getLessThan());
                                    gvalue= Double.parseDouble(mListdeliveryData.get(i).getGreaterThan());

                                    Log.e(TAG, "onResponse: cart"+" 1 "+ordervalue+" 2 "+ lvalue+"  "+gvalue);

                                    if(membershipName!=null && membershipName.length()>0){

                                        if (ordervalue < 99) {
                                            delicharge=20;
                                            tv_deliveryval.setText(mListdeliveryData.get(i).getCharges());}
                                        else{delicharge=0;tv_deliveryval.setText("+ ₹ 0.00");}
                                    } else{
                                        if(ordervalue<250){delicharge=20;tv_deliveryval.setText(mListdeliveryData.get(i).getCharges());}
                                        else{delicharge=0;tv_deliveryval.setText("+ ₹ 0.00");}
                                    }
                                }
                            } catch (Exception ex) {

                            }
                        }

                        List<CartDatum> mListData = response.body().getCartData();
                        if (mListData != null && mListData.size() > 0) {

                            rl_content2.setVisibility(View.VISIBLE);
                            cartList.addAll(mListData);

                            if (cartList.size() > 0) {
                                ll_header.setVisibility(View.VISIBLE);
                                rl_content2.setVisibility(View.VISIBLE);
                                tv_rv1.setText("Order Item " + "(" + cartList.size() + ")");

                                double itemmrp = 0;
                                double mrptotal = 0;
                                double amountpayable = 0;
                                double itemtax = 0;
                                double carttotal = 0;
                                double itemprice = 0;
                                double totaltax = 0;
                                double discount = 0;
                                double discountval = 0;
                                double carttotalinit = 0;

                                for (int i = 0; i < cartList.size(); i++) {

                                    itemprice = Double.parseDouble(cartList.get(i).getTotalPrice());
                                    carttotal = carttotal + itemprice;

                                    itemmrp = Double.parseDouble(cartList.get(i).getProductPrice());
                                    mrptotal = mrptotal + itemmrp;

                                    itemtax = Double.parseDouble(cartList.get(i).getProductTax().replaceAll("%", ""));
                                    totaltax = totaltax + (itemprice * itemtax * .01);

                                }

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

                                try
                                {



                                    tv_priceval.setText("₹ " + form.format(carttotalinit));

                                    // tv_gstval.setText("+ ₹ " + form.format(totaltax));
                                    tv_totalpaidval.setText("₹ " + form.format(amountpayable));
                                    tv_footertotal.setText("₹ " + form.format(amountpayable));
                                    tv_footertotalitem.setText(cartList.size() + " ITEMS");
                                    tv_discountval.setText("- ₹ " + form.format(discountval));

                                }
                                catch (Exception ex)
                                {

                                }
                                SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, String.valueOf(cartList.size()));

                            } else {

                                tv_priceval.setText("₹ 0");
                                tv_gstval.setText("+ ₹ 0");
                                tv_totalpaidval.setText("₹ 0");
                                rl_content2.setVisibility(View.GONE);
                                tv_rv1.setText("Item is Empty !");

                            }


                        } else {
                            tv_priceval.setText("₹ 0");
                            tv_gstval.setText("+ ₹ 0");
                            tv_totalpaidval.setText("₹ 0");
                            rl_content2.setVisibility(View.GONE);
                            tv_rv1.setText("Item is Empty !");
                            MainActivityNav.text.setText("0");
                            try {
                                ProductDetailsActivity.text.setText("0");
                            } catch (Exception ex) {

                            }
                            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                        }

                        // cartListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(cartListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                        if(profilemode.equalsIgnoreCase("B2B"))
                        {
                            rl_coupon.setVisibility(View.GONE);
                            getDiscountforB2B();

                        }
                        else
                        {
                            rl_coupon.setVisibility(View.VISIBLE);
                        }


                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
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


    public void validateDiscount(String couponcode) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String carttotalstr = tv_priceval.getText().toString().replace("₹ ","");
        CouponValidationRequest couponValidationRequest = new CouponValidationRequest();
        couponValidationRequest.setCouponCode(couponcode);
        couponValidationRequest.setOrderAmount(carttotalstr);
        couponValidationRequest.setCouponId(tv_discountid.getText().toString());


        Gson gson = new Gson();
        String vakk = gson.toJson(couponValidationRequest).toString();
        String jhd=vakk;

        try {

            Call<CouponValidationResponse> call = RetrofitUrlConnection.loadJSON(token).discountvalidation(couponValidationRequest);

            call.enqueue(new Callback<CouponValidationResponse>() {
                @Override
                public void onResponse(Call<CouponValidationResponse> call, Response<CouponValidationResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        //  Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                        String discount_type = "", discount_amount = "0";

                        discount_amount=response.body().getDiscountedAmount().toString();

                        tv_discount.setText("Discount (" + response.body().getCouponCode() + ")");

                        // tv_discount_coupon_name.setText( response.body().getCouponCode());
                        tv_discount_type.setText( response.body().getDiscountRule());
                        tv_discount_amount.setText(String.valueOf(response.body().getDiscountedAmount().toString()));
                        tv_discount_details.setText( response.body().getDiscountRule());
                        et_couponcode.setText(response.body().getCouponCode());
                        et_couponcode.setEnabled(false);
                        btn_apply.setVisibility(View.GONE);
                        btn_remove.setVisibility(View.VISIBLE);
                        tv_selectdiscount.setVisibility(View.GONE);

                        String credit_balance="0";
                        //et_couponcode.clearFocus();
                        et_couponcode.setError(null);
                        CalculatePrice( tv_discount_amount.getText().toString());


                    } else {
                        Utilities.dismissDialog();
                        et_couponcode.setError("Enter a valid couponcode");
                        et_couponcode.requestFocus();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CouponValidationResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void CalculatePrice(String discount_amount) {

        double itemmrp = 0;
        double mrptotal = 0;
        double amountpayable = 0;
        double itemtax = 0;
        double carttotal = 0;
        double itemprice = 0;
        double totaltax = 0;
        double discount = 0;
        double discountval = 0;
        double carttotalinit = 0;
        double carttotallessdis=0;



        carttotalinit =   Double.parseDouble( tv_priceval.getText().toString().replace("₹ ",""));
        carttotal =   Double.parseDouble( tv_totalpaidval.getText().toString().replace("₹ ",""));

        discount = Double.parseDouble(discount_amount);

        discount_type=tv_discount_type.getText().toString();

        if (discount_type.equalsIgnoreCase("flat")) {
            discountval = discount;
            carttotal = carttotal - discountval;
            
        } else if (discount_type.equalsIgnoreCase("PERCENTAGE")) {

            discountval = (carttotal * discount * .01);
            carttotal = carttotal - discountval;
            //carttotallessdis=carttotal - discount;

        }
        else
        {
            discountval = discount;
            carttotal = carttotal - discountval;


        }
        if(membershipName!=null && membershipName.length()>0){
            if(discountval>0 && carttotal<99)
        {
            amountpayable = carttotal;
        } else if(discountval<0 && carttotal<99) {
            amountpayable = carttotal + delicharge;
        }
        else{
            amountpayable = carttotal;//+delicharge
        }

        }else {

            if (discountval > 0 && carttotal < 250) {
                amountpayable = carttotal;
            } else if (discountval < 0 && carttotal <250) {
                amountpayable = carttotal + delicharge;
            } else {
                amountpayable = carttotal + delicharge;
            }
        }

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

        tv_priceval.setText("₹ " + form.format(carttotalinit));
        //tv_gstval.setText("+ ₹ " + form.format(totaltax));
       // tv_totalpaidval.setText("₹ " + form.format(amountpayable));

        // tv_discountval.setText("- ₹ "+form.format(discountval));
        // tv_footertotalitem.setText(cartList.size() + " ITEMS");

        if(discount_amount.contains("-"))
        {
            tv_discountval.setText("- ₹ 0");
            tv_discount.setText("Discount");
            tv_priceval.setText("₹ " + form.format(carttotalinit));
        }
        else
        {
            tv_discountval.setText("- ₹ " + form.format(discountval));
        }

        /*if(amountpayable < 250)
        {

            tv_deliveryval.setText("+ ₹ " + form.format(delicharge));
            amountpayable=amountpayable+delicharge;

        }
        else{
            tv_deliveryval.setText("₹ 0.00 ");
        }*/
        if(delicharge ==0){
            if(amountpayable<99){
                int delicharge1=20;
                amountpayable=carttotal+delicharge1;
                OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.black));
                OrderPreviewActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge1));
            }else {
                OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                OrderPreviewActivity.tv_deliveryval.setText("+ ₹ 0.00");
            }
        }
        else{
            tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            tv_deliveryval.setText("+ ₹ " + form.format(delicharge));
            amountpayable=amountpayable;//+delicharge
        }

        tv_totalpaidval.setText("₹ " + form.format(amountpayable));
        tv_footertotal.setText("₹ " + form.format(amountpayable));
    }

    public void getDiscountforB2B() {

        String previewtotal = tv_totalpaidval.getText().toString().replace("₹ ","");

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setCouponCode("");
        discountRequest.setOrderAmount(previewtotal);

        try {

            Call<DiscountResponse> call = RetrofitUrlConnection.loadJSON(token).discountB2B(discountRequest);

            call.enqueue(new Callback<DiscountResponse>() {
                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        try {
                            if (response.body().getDiscountCoupon().size() > 0) {

                                tv_discount.setText("Discount (" + response.body().getDiscountCoupon().get(0).getDiscountCouponName() + ")");

                                tv_discountid.setText(response.body().getDiscountCoupon().get(0).getId());
                                tv_discount_coupon_name.setText(response.body().getDiscountCoupon().get(0).getDiscountCouponName());
                                tv_discount_type.setText(response.body().getDiscountCoupon().get(0).getDiscountType());
                                tv_discount_amount.setText(response.body().getDiscountCoupon().get(0).getDiscountAmount());
                                tv_discount_details.setText(response.body().getDiscountCoupon().get(0).getDiscountDetails());

                            } else {
                                tv_discount.setText("Discount");
                                tv_discountval.setText("0");
                                tv_discountid.setText("");
                                tv_discount_coupon_name.setText("");
                                tv_discount_type.setText("");
                                tv_discount_amount.setText("0");
                                tv_discount_details.setText("");

                            }

                            String credit_balance = "0";

                            if (response.body().getCreditDetails().size() > 0) {

                                credit_balance = response.body().getCreditDetails().get(0).getCreditBalance();

                                tv_credit_limit.setText(response.body().getCreditDetails().get(0).getCreditLimit());
                                tv_credit_availed.setText(response.body().getCreditDetails().get(0).getCreditAvailed());
                                tv_credit_balance.setText(response.body().getCreditDetails().get(0).getCreditBalance());
                                SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, credit_balance);

                            }

                            CalculatePrice(tv_discount_amount.getText().toString());
                        }
                        catch (Exception ex){

                        }

                    } else {
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onRestart() {
        super.onRestart();
        //initUI();
    }


}
