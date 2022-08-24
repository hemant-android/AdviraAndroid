package com.advira.advirafarm.buyer.ui.buynow;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddressList;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressList;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressListBuynow;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountRequest;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountResponse;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.deliverySlot.DeliverySlotActivity;
import com.advira.advirafarm.buyer.ui.discount.DiscountListActivity;
import com.advira.advirafarm.buyer.ui.discount.api.CouponValidationRequest;
import com.advira.advirafarm.buyer.ui.discount.api.CouponValidationResponse;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipFragment;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.order.api.OrderPlacedRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderPlacedResponse;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class BuynowActivity extends AppCompatActivity implements IConsts {

    public static TextView tv_add;
    public static TextView tv_addid, tv_selectdiscount,
            tv_discountid, tv_discount_coupon_name,
            tv_discount_type, tv_discount_amount,
            tv_discount_details,tv_discountoff,tv_deliveryval;
    public static Button btn_apply, btn_remove;
    public static EditText et_couponcode;
    double carttotal = 0;
    double discountval = 0;
    private Button btn_buynow;
    private Button btn_change;
    public static TextView tv_priceval;
    public static TextView tv_gstval;
    public static TextView tv_totalpaidval;
    public static TextView tv_price;
    public static TextView tv_qty;
    public static TextView tv_mrpval;
    public static TextView tv_productdetails;
    public static TextView tv_productname;
    public static ImageView iv_product, iv_rx;
    public static TextView tv_pack;
    public static TextView tv_stock;
    public static  TextView tv_mrp;
    public static TextView tv_inr;
    public RelativeLayout rl_back;
    public Context mContext;
    public static TextView tv_discount;
    public static TextView tv_discountval;
    public String discountid = "";
    public String discount_coupon_name = "";
    public String discount_type = "";
    public String discount_amount = "";
    public String discount_details = "";
    public String credit_limit = "0";
    public String credit_availed = "0";
    public String credit_balance = "0";
    public String deliverycharges="";
    private RelativeLayout rl_coupon;
    public static String imgurl = "";
    public static String gst;
    String  membershipName;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;

    private RelativeLayout rl_membershipAds;
    private  TextView tv_buyplan;
    BottomSheetDialogFragment bottomSheetDialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buynow);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuynowActivity.this.finish();

            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Change or Add New Address");
                builder.setMessage("Select address for delivery.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPrefUtil.setAddressType(mContext, SHARED_PREF_ADDRESSTYPE, "select");
                                gst=tv_gstval.getText().toString();
                                Intent i = new Intent();
                                i.setClass(BuynowActivity.this, ChooseAddressListBuynow.class);
                                i.putExtra("productname", tv_productname.getText().toString());
                                i.putExtra("mrpval", tv_mrpval.getText().toString());
                                i.putExtra("price", tv_price.getText().toString());
                                i.putExtra("packsize", tv_pack.getText().toString());
                                i.putExtra("imgurl", imgurl.toString());
                                i.putExtra("pack", tv_pack.getText().toString());
                                i.putExtra("stock", tv_stock.getText().toString());
                                i.putExtra("gstval", gst.toString());
                                i.putExtra("mrplabel", tv_mrp.getText());
                                i.putExtra("ratelabel", tv_inr.getText());
                                i.putExtra("discountlabel", tv_discount.getText());
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
            }
        });


        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    if (!tv_add.getText().toString().equalsIgnoreCase("No address found")) {

                        String  membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");
                        String setTotalAmount = tv_priceval.getText().toString().replace("₹ ", "");
                        String setTotalTax = tv_gstval.getText().toString().replace("+ ₹ ", "");
                        String setGrandTotalAmount = tv_totalpaidval.getText().toString().replace("₹ ", "");
                        String totaldiscount = String.valueOf(discountval);
                        deliverycharges=tv_deliveryval.getText().toString().replace("+ ₹ ","");

                        if(membershipName!=null && membershipName.length()>0){
                            Intent i = new Intent();
                            i.setClass(BuynowActivity.this, DeliverySlotActivity.class);
                            i.putExtra("addressid", tv_addid.getText().toString());
                            i.putExtra("address", tv_add.getText().toString());
                            i.putExtra("ordertype", "BuyNow");
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
                        }
                        else {
                            Intent i = new Intent();
                            i.setClass(BuynowActivity.this, PaymentOption.class);
                            i.putExtra("addressid", tv_addid.getText().toString());
                            i.putExtra("address", tv_add.getText().toString());
                            i.putExtra("ordertype", "BuyNow");
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
                        AlertDialog.Builder alert = new AlertDialog.Builder(BuynowActivity.this);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Select a delivery address.");
                        alert.setPositiveButton("OK",null);
                        alert.show();

                    }
                } else {
                    Utilities.showNetworkError(mContext);
                }
            }
        });

        tv_selectdiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(BuynowActivity.this, DiscountListActivity.class);
                startActivity(i);
            }
        });

        tv_discount_coupon_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (s.length() != 0) {
                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if (profilemode.equalsIgnoreCase("B2C")) {
                        validateDiscount(tv_discount_coupon_name.getText().toString());

                    }
                }

            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_couponcode.getText().toString().length() < 3) {
                    et_couponcode.setError("Enter a valid couponcode");
                    et_couponcode.requestFocus();
                } else {
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
                reversediscount = "-" + reversediscount;


                tv_discountval.setText("- ₹ 0");
                tv_discount.setText("Discount");

                CalculatePrice("flat", "0");

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
    }

    private void initUI() {
        mContext = BuynowActivity.this;
        membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");

        tv_add = findViewById(R.id.tv_add);
        btn_change = findViewById(R.id.btn_change);
        btn_buynow = findViewById(R.id.btn_buynow);
        rl_back = findViewById(R.id.rl_back);
        tv_productname = findViewById(R.id.tv_productname);
        tv_productdetails = findViewById(R.id.tv_productdetails);
        tv_priceval = findViewById(R.id.tv_priceval);
        tv_gstval = findViewById(R.id.tv_gstval);
        tv_price = findViewById(R.id.tv_price);
        tv_qty = findViewById(R.id.tv_qty);
        tv_mrpval = findViewById(R.id.tv_mrpval);
        iv_product = findViewById(R.id.iv_product);
        tv_pack = findViewById(R.id.tv_pack);
        tv_stock = findViewById(R.id.tv_stock);
        tv_totalpaidval = findViewById(R.id.tv_totalpaidval);
        tv_addid = findViewById(R.id.tv_addid);
        iv_rx = findViewById(R.id.iv_rx);
        tv_mrp = findViewById(R.id.tv_mrp);
        tv_inr = findViewById(R.id.tv_inr);
        tv_discount = findViewById(R.id.tv_discount);
        tv_discountval = findViewById(R.id.tv_discountval);
        tv_selectdiscount = findViewById(R.id.tv_selectdiscount);
        tv_deliveryval=findViewById(R.id.tv_deliveryval);

        tv_discountid = findViewById(R.id.tv_discountid);
        tv_discount_coupon_name = findViewById(R.id.tv_discount_coupon_name);
        tv_discount_type = findViewById(R.id.tv_discount_type);
        tv_discount_amount = findViewById(R.id.tv_discount_amount);
        tv_discount_details = findViewById(R.id.tv_discount_details);
        tv_buyplan=findViewById(R.id.tv_buyplan);
        rl_membershipAds=findViewById(R.id.rl_membershipAds);
        rl_coupon = findViewById(R.id.rl_coupon);
        btn_apply = findViewById(R.id.btn_apply);
        btn_remove = findViewById(R.id.btn_remove);
        et_couponcode = findViewById(R.id.et_couponcode);
        tv_discountoff = findViewById(R.id.tv_discountoff);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0

        if(membershipName!=null && membershipName.length()>0) {
            rl_membershipAds.setVisibility(View.GONE);
        }

        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
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

                switch (item.getItemId()){

                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
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

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });


        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

        if (profilemode.equalsIgnoreCase("B2B")) {
            rl_coupon.setVisibility(View.GONE);
        } else {
            rl_coupon.setVisibility(View.VISIBLE);
        }

        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        if (usermode.equalsIgnoreCase("B2B")) {
            getDiscount();
        } else {
            CalculatePrice("flat", "0");
        }

        //getDiscount();
        // CalculatePrice("flat", "0");

    }

    private void CalculatePrice(String discount_type, String discount_amount) {

        DecimalFormat form = new DecimalFormat("0.00");

        Bundle extras = getIntent().getExtras();
        String productname = "";
        String productdetails = "";
        String priceval = "";
        String gstval = "";
        String price = "";
        String qty = "";
        String mrpval = "";
        String imgurl = "";
        String pack = "";
        String stock = "";
        String packsize = "";
        String address = "";
        String addressid = "";
        String isrx = "";
        String mrplabel = "Rate : ₹";
        String ratelabel = "₹ ";
        String moqunit = "";
        String discountlabel="";
        double delicharge=0;


        if (extras != null) {

            productname = extras.getString("productname");
            productdetails = extras.getString("productdetails");
            mrpval = extras.getString("mrpval");
            price = extras.getString("price");
            qty = extras.getString("qty");
            imgurl = extras.getString("imgurl");
            pack = extras.getString("pack");
            stock = extras.getString("stock");
            gstval = extras.getString("gstval");
            packsize = extras.getString("packsize");
            address = extras.getString("address");
            addressid = extras.getString("addressid");
            isrx = extras.getString("isrx");
            mrplabel = extras.getString("mrplabel");
            ratelabel = extras.getString("ratelabel");
            moqunit = extras.getString("moqunit");
            discountlabel = extras.getString("discountlabel");

        }

        tv_productname.setText(productname);
        tv_productdetails.setText(productdetails);
        //tv_priceval.setText(price);
        tv_gstval.setText(gstval);
        tv_price.setText(price);
        //tv_qty.setText("Total Quantity : " + qty + " " + moqunit);
        tv_qty.setText("Total Quantity : " + qty);
        tv_mrpval.setText(form.format(Double.parseDouble(mrpval)));
        tv_pack.setText(pack);
        tv_stock.setText(stock);
        tv_add.setText(address);
        tv_addid.setText(addressid);
        tv_mrp.setText("Rate : ₹ ");
        tv_inr.setText(" ₹");
        tv_discountoff.setText(discountlabel);


        if (imgurl.length() > 5) {
            Picasso.with(mContext).load(imgurl).placeholder(R.drawable.progress_animation).into(iv_product);
        } else {
            iv_product.setVisibility(View.INVISIBLE);
        }

        double itemqty = 0;
        double packqty = 0;
        double itemtax = 0;
        double itemprice = 0;
        double totaltax = 0;
        double discount = 0;
        double amountpayable = 0;
        double carttotalinit = 0;

        gstval = tv_gstval.getText().toString().replace("+ ₹ ", "");
        qty=tv_qty.getText().toString().replaceAll("[^0-9]", "");

        itemprice = Double.parseDouble(price);
        itemtax = Double.parseDouble(gstval);
        itemqty = Double.parseDouble(qty);
        packqty = Double.parseDouble("1");

        if (discount_amount.equalsIgnoreCase("0")) {
            discount = 0;
        } else {
            discount = Double.parseDouble(discount_amount);
        }
        carttotal = (itemqty * itemprice * packqty);

        carttotalinit = carttotal;
        if (discount_type.equalsIgnoreCase("flat")) {
            discountval = discount;
            carttotal = carttotal - discount;

        } else if (discount_type.equalsIgnoreCase("PERCENTAGE")) {

            discountval = (carttotalinit * discount * .01);
            carttotal = carttotal - discountval;

        } else {
            discountval = discount;
            carttotal = carttotal - discountval;

        }

        if(membershipName!=null && membershipName.length()>0){
            if (carttotal <= 99) {
                delicharge=20;
                //tv_deliveryval.setText(mListdeliveryData.get(i).getCharges());
                }
            else{delicharge=0;//tv_deliveryval.setText("+ ₹ 0.00");
                 }
        } else{
            if(carttotal <=250 ){delicharge=20;
            //tv_deliveryval.setText(mListdeliveryData.get(i).getCharges());
            }
            else{delicharge=0;
            //tv_deliveryval.setText("+ ₹ 0.00");
            }
        }

        totaltax = totaltax + (carttotal * itemtax * .01);

        amountpayable = carttotal+delicharge;

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

        tv_priceval.setText("₹ " + form.format(carttotalinit));
        //tv_gstval.setText("+ ₹ " + form.format(totaltax));
        tv_totalpaidval.setText("₹ " + form.format(amountpayable));

        if(delicharge ==0){
            tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            tv_deliveryval.setText("+ ₹ 0.00");
        }
        else{
            tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            tv_deliveryval.setText("+ ₹ " + form.format(delicharge));

        }
        // tv_discountval.setText("- ₹ " + form.format(discountval));

        if (discount_amount.contains("-")) {
            tv_discountval.setText("- ₹ 0");
            tv_discount.setText("Discount");
            tv_priceval.setText("₹ " + form.format(carttotalinit));


        } else {
            tv_discountval.setText("- ₹ " + form.format(discountval));

        }
    }


    public void getDiscount() {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String carttotalstr = String.valueOf(carttotal);
        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setCouponCode("");
        discountRequest.setOrderAmount(carttotalstr);

        try {

            Call<DiscountResponse> call = RetrofitUrlConnection.loadJSON(token).discountB2B(discountRequest);

            call.enqueue(new Callback<DiscountResponse>() {
                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        //String discount_type = "", discount_amount = "0";

                        if (response.body().getDiscountCoupon().size() > 0) {

                            tv_discount.setText("Discount (" + response.body().getDiscountCoupon().get(0).getDiscountCouponName() + ")");

                            discountid = response.body().getDiscountCoupon().get(0).getId();
                            discount_coupon_name = response.body().getDiscountCoupon().get(0).getDiscountCouponName();
                            discount_type = response.body().getDiscountCoupon().get(0).getDiscountType();
                            discount_amount = response.body().getDiscountCoupon().get(0).getDiscountAmount();
                            discount_details = response.body().getDiscountCoupon().get(0).getDiscountDetails();


                        } else {
                            tv_discount.setText("Discount");
                            tv_discountval.setText("0");
                            discount_amount = "0";
                        }

                        if (response.body().getCreditDetails().size() > 0) {

                            credit_limit = response.body().getCreditDetails().get(0).getCreditLimit();
                            credit_availed = response.body().getCreditDetails().get(0).getCreditAvailed();
                            credit_balance = response.body().getCreditDetails().get(0).getCreditBalance();
                            SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, credit_balance);

                        }

                        CalculatePrice(discount_type, discount_amount);


                    } else {
                        Utilities.dismissDialog();
                        ////Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast//remove toast
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


    public void validateDiscount(String couponcode) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        //String carttotalstr = tv_totalpaidval.getText().toString().replace("₹ ", "");//24.9.2021
        String carttotalstr = tv_priceval.getText().toString().replace("₹ ", "");
        CouponValidationRequest couponValidationRequest = new CouponValidationRequest();
        couponValidationRequest.setCouponCode(couponcode);
        couponValidationRequest.setOrderAmount(carttotalstr);
        couponValidationRequest.setCouponId(tv_discountid.getText().toString());

        try {

            Call<CouponValidationResponse> call = RetrofitUrlConnection.loadJSON(token).discountvalidation(couponValidationRequest);

            call.enqueue(new Callback<CouponValidationResponse>() {
                @Override
                public void onResponse(Call<CouponValidationResponse> call, Response<CouponValidationResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                        String discount_type = "", discount_amount = "0";

                        discount_amount = response.body().getDiscountedAmount().toString();

                        tv_discount.setText("Discount (" + response.body().getCouponCode() + ")");
                        tv_discount_type.setText(response.body().getDiscountRule());
                        tv_discount_amount.setText(String.valueOf(response.body().getDiscountedAmount().toString()));
                        tv_discount_details.setText(response.body().getDiscountRule());

                        et_couponcode.setText(response.body().getCouponCode());
                        et_couponcode.setEnabled(false);
                        btn_apply.setVisibility(View.GONE);
                        btn_remove.setVisibility(View.VISIBLE);
                        tv_selectdiscount.setVisibility(View.GONE);


                        String credit_balance = "0";
                        et_couponcode.setError(null);
                        CalculatePrice("flat", tv_discount_amount.getText().toString());

                    } else {
                        Utilities.dismissDialog();
                        et_couponcode.setError("Enter a valid couponcode");
                        et_couponcode.requestFocus();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
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
}
