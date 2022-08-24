package com.advira.advirafarm.buyer.ui.order;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.order.adapter.ItemDetailsAdapter;
import com.advira.advirafarm.buyer.ui.order.adapter.OrderDetailsAdapter;
import com.advira.advirafarm.buyer.ui.order.adapter.OrderDetailsImageAdapter;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderDetailsRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderDetailsResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderProductList;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PartialPaymentActivity;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PayLaterPaymentOption;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.transferwise.sequencelayout.SequenceLayout;
import com.transferwise.sequencelayout.SequenceStep;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.ui.payment.PaymentOption.rb_emi;

public class InvoiceActivity extends AppCompatActivity implements IConsts {

    public static final int PERMISSION_WRITE = 0;
    public static RecyclerView recyclerView, recyclerView2;
    public static TextView tv_paymentpending,
            tv_expecteddelivery, tv_priceval, tv_gstval, tv_totalpaidval, tv_rv5, tv_pinval, tv_addid, tv_pd_header2,
            tv_pd_name,tv_paymenttype, tv_paymentamount, tv_paymentdue, tv_paymentref, tv_paymentmode, tv_paymentdate,
            tv_deliv, tv_cancelorder, tv_billingval, tv_refund, tv_billingvalgst, tv_billingvaldl, tv_tracking_code,
            tv_courier_company_name, tv_tracking_url, tv_paymentduepaynow, tv_paymentfailed, tv_retrypayment, tv_retrypaymentfooter,
            tv_courier_date, tv_deliveryval, tv_totalwithdisval,tv_totalwithdis,tv_taxrate,tv_taxrateval,tv_orderdateval,
            tv_invoicenoval,tv_placeofsupplyval,tv_delimodeval,tv_inpaymentmodeval;

    public static TextView tv_pd_number,tv_pd_email,tv_pd_add,tv_pd_gstin,tv_pd_state,tv_pd_country;
    public static TextView tv_pd_name_bt,tv_pd_number_bt,tv_pd_email_bt,tv_pd_add_bt,tv_pd_gstin_bt,tv_pd_state_bt,tv_pd_country_bt;


    public static RelativeLayout rl_content2, rl_payment, rl_recycler2, ll_refund, rl_courierinfo;
    SequenceStep step_1;
    SequenceStep step_6;
    SequenceStep step_2;
    SequenceStep step_3;
    SequenceStep step_4;
    SequenceStep step_5;

    SequenceLayout sq_layout;
    ItemDetailsAdapter orderDetailsAdapter;

    OrderDetailsImageAdapter orderDetailsImageAdapter;

    String[] descriptionData = {"Ordered", "Order-Confirmed", "Plucking", "Dispatched", "In-transit", "Delivered"};
    String cancelhour = "0";
    String ordercreatedat = "0";
    String invoiceurl = "";
    String pincode="";
    String deliverycharges="";
    ProgressDialog progressDialog;
    ImageView imageView;
    private RelativeLayout rl_back, rl_search, rl_cart;
    private TextView tv_cartcount;
    private View rootView;
    private List<OrderProductList> orderproductList;
    private Context mContext;
    private RelativeLayout rl_cancel;
    private Button btn_addpayment;
    private String orderid = "";
    private String orderno = "";
    private String from = "";
    private TextView tv_tab1;
    private TextView tv_tab2;
    private View v_linetab1;
    private View v_linetab2;
    private LinearLayout ll_sequence;
    private RelativeLayout rl_content5;
    private RelativeLayout rl_retrypayment;
    private String ordertype = "";
    private String totalamount = "";
    private String totaltax = "";
    private String totaldiscount = "";
    private String grandtotal = "";
    private String minimumamount = "0";
    private String paymentdue = grandtotal;
    private double due = 0;
    private RelativeLayout ll_invoice, ll_shipping;
    public static Button btn_retrypayment;

    private TextView tv_discount;
    private TextView tv_discountval;
    //private TextView tv_paymentduepaynow;


    private ListView paymentlistView;
    private ArrayList<String> paymentList = new ArrayList<>();
    private ArrayAdapter paymentListAdapter;

    public static BottomNavigationView bottomnavview;
    public static TextView tv_cart_counter, text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //super.onBackPressed();
                //InvoiceActivity.this.finish();
                    Intent mainIntent = new Intent(InvoiceActivity.this, OrderDetailsActivity.class);
                    mainIntent.putExtra("orderid",orderid);
                    mainIntent.putExtra("from", "");
                    InvoiceActivity.this.finish();
                    InvoiceActivity.this.startActivity(mainIntent);


            }
        });


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(InvoiceActivity.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(InvoiceActivity.this, SearchActivity.class);
                i.setClass(InvoiceActivity.this, Search_one.class);
                startActivity(i);

            }
        });


        btn_addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent();
                i.setClass(InvoiceActivity.this, AddPaymentActivity.class);
                i.putExtra("total", tv_totalpaidval.getText().toString());
                i.putExtra("orderid", orderid);
                i.putExtra("orderno", orderno);
                startActivity(i);

            }
        });

        tv_cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    double cacelminutes = Integer.valueOf(cancelhour) * 60;

                    int diff = twoDatesBetweenTime(ordercreatedat);
                    if (diff > cacelminutes) {
                        rl_cancel.setVisibility(View.GONE);
                    } else {
                        rl_cancel.setVisibility(View.VISIBLE);

                        Intent i = new Intent();
                        i.setClass(InvoiceActivity.this, OrderCancellationActivity.class);
                        i.putExtra("total", tv_totalpaidval.getText().toString());
                        i.putExtra("orderid", orderid);
                        i.putExtra("orderno", orderno);
                        startActivity(i);

                    }
                } catch (Exception ex) {

                }

            }
        });

        btn_retrypayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_retrypayment.equals("Retry Payment")) {
                    Intent mainIntent = new Intent(InvoiceActivity.this, PaymentOption.class);
                    mainIntent.putExtra("orderid", orderid);
                    mainIntent.putExtra("orderno", orderno);
                    mainIntent.putExtra("addressid", tv_addid.getText().toString());
                    mainIntent.putExtra("address", tv_pinval.getText().toString());
                    mainIntent.putExtra("ordertype", ordertype);
                    mainIntent.putExtra("totalamount", totalamount);
                    mainIntent.putExtra("totaltax", totaltax);
                    mainIntent.putExtra("totaldiscount", totaldiscount);
                    mainIntent.putExtra("grandtotal", grandtotal);
                    mainIntent.putExtra("from", "back");
                    mainIntent.putExtra("delivery_charges", deliverycharges);
                    InvoiceActivity.this.startActivity(mainIntent);
                }
                else{
                    Intent mainIntent = new Intent(InvoiceActivity.this, PayLaterPaymentOption.class);
                    //Intent mainIntent = new Intent(InvoiceActivity.this, PaymentOption.class);
                    //rb_emi.setVisibility(View.GONE);
                    mainIntent.putExtra("orderid", orderid);
                    mainIntent.putExtra("orderno", orderno);
                    mainIntent.putExtra("addressid", tv_addid.getText().toString());
                    mainIntent.putExtra("address", tv_pinval.getText().toString());
                    mainIntent.putExtra("ordertype", ordertype);
                    mainIntent.putExtra("totalamount", totalamount);
                    mainIntent.putExtra("totaltax", totaltax);
                    mainIntent.putExtra("totaldiscount", totaldiscount);
                    mainIntent.putExtra("grandtotal", grandtotal);
                    mainIntent.putExtra("from", "back");
                    mainIntent.putExtra("delivery_charges", deliverycharges);
                    InvoiceActivity.this.startActivity(mainIntent);
                }

            }
        });


        imageView = findViewById(R.id.iv_invoice);
        progressDialog = new ProgressDialog(this);


        ll_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkPermission()) {
                    if (invoiceurl.length() > 10) {
                        if (invoiceurl.contains(".pdf")) {

                            new DownloadFile().execute(invoiceurl, orderno + ".pdf");
                            OpenPDF(invoiceurl);

                        } else {
                            new Downloading().execute(invoiceurl);


                        }


                    } else {

                        Singleton.getInstance().showShortToast(mContext, "Invoice not available for this Order..");

                        // Toast.makeText(mContext, "Invoice not available for this Order..", Toast.LENGTH_SHORT).show();
                    }
                }*/
                Intent i=new Intent(InvoiceActivity.this,InvoiceActivity.class);
                i.putExtra("orderid", tv_pd_header2.getText().toString());
                i.putExtra("from", "");
                startActivity(i);
            }
        });

        rl_courierinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(InvoiceActivity.this, WebViewActivity.class);
                i.putExtra("header", orderno);
                i.putExtra("url", tv_tracking_url.getText().toString());
                i.putExtra("isrx", "");
                startActivity(i);

            }
        });

        tv_paymentduepaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent();
                i.setClass(InvoiceActivity.this, PayLaterPaymentOption.class);
                i.putExtra("orderid", orderid);
                i.putExtra("grandtotal", grandtotal);
                startActivity(i);*/


                Intent i = new Intent();
                i.setClass(InvoiceActivity.this, PartialPaymentActivity.class);
                i.putExtra("orderid", orderid);
                i.putExtra("grandtotal", paymentdue);
                //i.putExtra("grandtotal", due);
                i.putExtra("orderno", orderno);
                i.putExtra("minimumamount", minimumamount);

                i.putExtra("addressid", "");
                i.putExtra("address", "");
                i.putExtra("ordertype", ordertype);
                i.putExtra("totalamount", totalamount);
                i.putExtra("totaltax", totaltax);
                i.putExtra("totaldiscount", totaldiscount);
                i.putExtra("from", from);
                i.putExtra("discountid", "");
                i.putExtra("discount_coupon_name", "");
                i.putExtra("discount_type", "");
                i.putExtra("discount_amount", "");
                i.putExtra("discount_details", "");
                i.putExtra("credit_limit", "");
                i.putExtra("credit_availed", "");
                i.putExtra("credit_balance", "");


                startActivity(i);


            }
        });

    }

    private void initUI() {

        mContext = InvoiceActivity.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        rl_cart.setVisibility(View.GONE);
        tv_cartcount.setVisibility(View.GONE);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        //String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        //tv_cartcount.setText(cartcount);

        tv_deliveryval=findViewById(R.id.tv_deliveryval);
        tv_totalwithdisval=findViewById(R.id.tv_totalwithdisval);
        tv_totalwithdis=findViewById(R.id.tv_totalwithdis);
        tv_taxrateval=findViewById(R.id.tv_taxrateval);
        tv_taxrate=findViewById(R.id.tv_taxrate);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        tv_priceval = findViewById(R.id.tv_priceval);
        tv_gstval = findViewById(R.id.tv_gstval);
        tv_totalpaidval = findViewById(R.id.tv_totalpaidval);
        rl_content2 = findViewById(R.id.rl_content2);
        //tv_rv1 = findViewById(R.id.tv_rv1);
        tv_pinval = findViewById(R.id.tv_pinval);
        tv_addid = findViewById(R.id.tv_addid);
        tv_pd_name=findViewById(R.id.tv_pd_name);
        tv_pd_header2 = findViewById(R.id.tv_pd_header2);
        tv_deliv = findViewById(R.id.tv_deliv);
        tv_expecteddelivery = findViewById(R.id.tv_expecteddelivery);
        tv_paymentpending = findViewById(R.id.tv_paymentpending);
        rl_payment = findViewById(R.id.rl_payment);
        tv_paymenttype = findViewById(R.id.tv_paymenttype);
        tv_paymentamount = findViewById(R.id.tv_paymentamount);
        tv_paymentdue = findViewById(R.id.tv_paymentdue);
        tv_paymentref = findViewById(R.id.tv_paymentref);
        tv_paymentmode = findViewById(R.id.tv_paymentmode);
        tv_paymentdate = findViewById(R.id.tv_paymentdate);
        btn_addpayment = findViewById(R.id.btn_addpayment);
        tv_cancelorder = findViewById(R.id.tv_cancelorder);
        rl_cancel = findViewById(R.id.rl_cancel);
        btn_retrypayment = findViewById(R.id.btn_retrypayment);
        ll_refund = findViewById(R.id.ll_refund);
        tv_refund = findViewById(R.id.tv_refund);

        sq_layout = findViewById(R.id.sq_layout);
        step_1 = findViewById(R.id.step_1);
        step_2 = findViewById(R.id.step_2);
        step_3 = findViewById(R.id.step_3);
        step_4 = findViewById(R.id.step_4);
        step_5 = findViewById(R.id.step_5);
        step_6 = findViewById(R.id.step_6);



        tv_tab1 = findViewById(R.id.tv_tab1);

        tv_tab2 = findViewById(R.id.tv_tab2);
        ll_sequence = findViewById(R.id.ll_sequence);
        v_linetab1 = findViewById(R.id.v_linetab1);
        v_linetab2 = findViewById(R.id.v_linetab2);
        rl_recycler2 = findViewById(R.id.rl_recycler2);
        rl_retrypayment = findViewById(R.id.rl_retrypayment);
        ll_invoice = findViewById(R.id.ll_invoice);
        tv_billingval = findViewById(R.id.tv_billingval);
        tv_billingvalgst = findViewById(R.id.tv_billingvalgst);
        tv_billingvaldl = findViewById(R.id.tv_billingvaldl);
        tv_paymentfailed=findViewById(R.id.tv_paymentfailed);
        tv_retrypayment=findViewById(R.id.tv_retrypayment);
        tv_retrypaymentfooter=findViewById(R.id.tv_retrypaymentfooter);

        tv_orderdateval=findViewById(R.id.tv_orderdateval);
        tv_invoicenoval=findViewById(R.id.tv_invoicenoval);
        tv_placeofsupplyval=findViewById(R.id.tv_placeofsupplyval);
        tv_delimodeval=findViewById(R.id.tv_delimodeval);
        tv_inpaymentmodeval=findViewById(R.id.tv_inpaymentmodeval);

        tv_pd_number=findViewById(R.id.tv_pd_number);
        tv_pd_email=findViewById(R.id.tv_pd_email);
        tv_pd_add=findViewById(R.id.tv_pd_add);
        tv_pd_gstin=findViewById(R.id.tv_pd_gstin);
        tv_pd_state=findViewById(R.id.tv_pd_state);
        tv_pd_country=findViewById(R.id.tv_pd_country);

        tv_pd_name_bt=findViewById(R.id.tv_pd_name_bt);
        tv_pd_number_bt=findViewById(R.id.tv_pd_number_bt);
        tv_pd_email_bt=findViewById(R.id.tv_pd_email_bt);
        tv_pd_add_bt=findViewById(R.id.tv_pd_add_bt);
        tv_pd_gstin_bt=findViewById(R.id.tv_pd_gstin_bt);
        tv_pd_state_bt=findViewById(R.id.tv_pd_state_bt);
        tv_pd_country_bt=findViewById(R.id.tv_pd_country_bt);


        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        //String paymentmode= SharedPrefUtil.getPaymentCheck(mContext,SHARED_PREF_PaymentCheck,"FAILED");

        if (profilemode.equalsIgnoreCase("B2C")) {
            tv_billingvalgst.setVisibility(View.GONE);
            tv_billingvaldl.setVisibility(View.GONE);

        } else {
            tv_billingvalgst.setVisibility(View.VISIBLE);
            tv_billingvaldl.setVisibility(View.VISIBLE);
        }
        /*if(paymentmode.equals("COD") || tv_paymenttype.equals("FAILED")){
            btn_retrypayment.setText("Payment");
            tv_paymentfailed.setText("Payment Pending");
        }*/



        tv_tracking_code = findViewById(R.id.tv_tracking_code);
        tv_courier_company_name = findViewById(R.id.tv_courier_company_name);
        tv_tracking_url = findViewById(R.id.tv_tracking_url);
        tv_courier_date = findViewById(R.id.tv_courier_date);
        ll_shipping = findViewById(R.id.ll_shipping);
        rl_courierinfo = findViewById(R.id.rl_courierinfo);


        tv_discount = findViewById(R.id.tv_discount);
        tv_discountval = findViewById(R.id.tv_discountval);
        tv_paymentduepaynow = findViewById(R.id.tv_paymentduepaynow);


        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        //bottomnavview.setVisibility(View.GONE);
        //bottomnavview.setSelectedItemId(R.id.tokri);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
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
        //String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "0");
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                //profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                switch (item.getItemId()){

                    case R.id.category:
                        //tv_cartcount.setVisibility(View.GONE);
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoryFragment())
                                .commit();

                        rl_search.setVisibility(View.VISIBLE);
                        break;*/
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
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
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });




        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        rl_retrypayment.setVisibility(View.GONE);
        // tv_paymentref.setVisibility(View.GONE);
        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        paymentlistView = findViewById(R.id.paymentlistView);
        paymentListAdapter = new ArrayAdapter<String>(this, R.layout.text_view_payment_list, paymentList);


        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            orderid = extras.getString("orderid");
            from = extras.getString("from");
        }

        //Log.e(TAG, "initUI: orderid"+"\n"+orderid+"\n"+from );
        OrderDetailsRequest(orderid);

        tv_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_tab1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_tab2.setTextColor(getResources().getColor(R.color.colorBlack));

                ll_sequence.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                rl_recycler2.setVisibility(View.VISIBLE);

                v_linetab1.setVisibility(View.VISIBLE);
                v_linetab2.setVisibility(View.INVISIBLE);

            }
        });

        tv_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_tab1.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_tab2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                ll_sequence.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                rl_recycler2.setVisibility(View.GONE);


                v_linetab1.setVisibility(View.INVISIBLE);
                v_linetab2.setVisibility(View.VISIBLE);

            }
        });


        paymentlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // submitNetbankingDetails(banksCodesList.get(position));
            }
        });
    }


    public void OrderDetailsRequest(String orderid) {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
        orderDetailsRequest.setOrderId(orderid);

        try {

            Call<OrderDetailsResponse> call = RetrofitUrlConnection.loadJSON(token).myorderdetail(orderDetailsRequest);

            call.enqueue(new Callback<OrderDetailsResponse>() {
                @Override
                public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        /*Gson gson = new Gson();
                        String jsondata = gson.toJson(response.body());
                        String orderstatus = response.body().getOrderDetails().get(0).getDeliveryStatus();
                        Log.e(String.valueOf(InvoiceActivity.class), "onResponse: sapna_orderstatus"+jsondata+"\n"+orderstatus );
*/
                        // CheckPin();
                        int paymentsize1 = response.body().getPaymentDetails().size() - 1;
                        orderproductList = new ArrayList<>();
                        orderDetailsAdapter = new ItemDetailsAdapter(mContext, orderproductList);
                        orderDetailsImageAdapter = new OrderDetailsImageAdapter(mContext, orderproductList);
                        String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
                        String email = SharedPrefUtil.getUserEmail(mContext, SHARED_PREF_UserEmailID, "");
                        String number = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");
                        String ship_name=response.body().getOrderDetails().get(0).getAddress().getReceiverName();
                        String ship_mobile=response.body().getOrderDetails().get(0).getAddress().getReceiverMob();
                        deliverycharges=response.body().getOrderDetails().get(0).getDeliveryCharges();
                        String address = response.body().getOrderDetails().get(0).getAddress().getAddress()
                                + " " + response.body().getOrderDetails().get(0).getAddress().getAddress2() + " " +
                                response.body().getOrderDetails().get(0).getAddress().getCityName() + ",\n" +
                                response.body().getOrderDetails().get(0).getAddress().getStateName() + " " +
                                response.body().getOrderDetails().get(0).getAddress().getPincode();
                       // String address=SharedPrefUtil.getDefaultAddress(mContext,SHARED_PREF_DefaultAddress,"");
                        tv_placeofsupplyval.setText(response.body().getOrderDetails().get(0).getAddress().getCityName());
                        pincode=response.body().getOrderDetails().get(0).getAddress().getPincode();
                        tv_pinval.setText(address);
                        tv_addid.setText(response.body().getOrderDetails().get(0).getAddress().getId());
                        tv_orderdateval.setText(formatDate(response.body().getOrderDetails().get(0).getCreatedAt()));
                        if(response.body().getPaymentDetails().get(paymentsize1).getPaymentMode().equalsIgnoreCase("")) {
                            tv_inpaymentmodeval.setText("Wallet");
                        }else {
                            tv_inpaymentmodeval.setText(response.body().getPaymentDetails().get(paymentsize1).getPaymentMode());
                        }
                        tv_expecteddelivery.setText(response.body().getOrderDetails().get(0).getAddress().getOrderDeliveryDays());
                        tv_pd_header2.setText(response.body().getOrderDetails().get(0).getOrderNo());
                        tv_pd_name.setText(ship_name);
                        tv_pd_name_bt.setText(name);

                        tv_pd_email.setText(email);
                        tv_pd_email_bt.setText(email);

                        tv_pd_number.setText(ship_mobile);
                        tv_pd_number_bt.setText(number);

                        tv_pd_add.setText(address);
                        tv_pd_add_bt.setText(address);

                        tv_pd_gstin.setText("Not Applicable");
                        tv_pd_state.setText(response.body().getOrderDetails().get(0).getAddress().getStateName());
                        tv_pd_country.setText("India");

                        tv_pd_gstin_bt.setText("Not Applicable");
                        tv_pd_state_bt.setText(response.body().getOrderDetails().get(0).getAddress().getStateName());
                        tv_pd_country_bt.setText("India");

                        //tv_taxrateval.setText(response.body().getOrderDetails().get(0).getOrderProductList().get(0).getProductTax()+"%");
                        tv_billingval.setText(response.body().getOrderDetails().get(0).getBilledTo());
                        tv_billingvalgst.setText("GST No : " + response.body().getOrderDetails().get(0).getGstNo());
                        tv_billingvaldl.setText("FSSAI No : " + response.body().getOrderDetails().get(0).getFssaiNo());

                        if (response.body().getOrderDetails().get(0).getCourierInfo().size() > 0) {

                            rl_courierinfo.setVisibility(View.VISIBLE);

                            tv_tracking_code.setText("Tracking ID : " + response.body().getOrderDetails().get(0).getCourierInfo().get(0).getTrackingCode());
                            tv_courier_company_name.setText("Courier provider : " + response.body().getOrderDetails().get(0).getCourierInfo().get(0).getCourierCompanyName());
                            tv_tracking_url.setText(response.body().getOrderDetails().get(0).getCourierInfo().get(0).getTrackingUrl());
                            tv_courier_date.setText(formatDate(response.body().getOrderDetails().get(0).getCourierInfo().get(0).getCourierDate()));
                        } else {
                            rl_courierinfo.setVisibility(View.GONE);
                        }

                        try {

                            minimumamount = response.body().getNewPayment().get(0).getMinimumPayment().toString();
                            paymentdue = response.body().getNewPayment().get(0).getOrderAmountDue().toString();
                        } catch (Exception ex) {

                        }

                        DecimalFormat form = new DecimalFormat("0.00");


                        orderno = response.body().getOrderDetails().get(0).getOrderNo();

                        tv_priceval.setText(response.body().getOrderDetails().get(0).getTotalAmount());
                        //tv_gstval.setText("+ ₹ " + form.format(response.body().getOrderDetails().get(0).getTotalTax()));

                        tv_totalpaidval.setText("₹ " + response.body().getOrderDetails().get(0).getGrandTotalAmount());
                        String discount=response.body().getOrderDetails().get(0).getTotalDiscount();
                        String subtotal=response.body().getOrderDetails().get(0).getTotalAmount();
                        String totaltax=response.body().getOrderDetails().get(0).getTotalTax();
                        double taxrate=((Double.valueOf(totaltax)*100)/Double.valueOf(subtotal));
                        double subtotalwithdiscount=Double.valueOf(subtotal)-Double.valueOf(discount);
                        tv_totalwithdisval.setText(form.format(subtotalwithdiscount));
                        //tv_gstval.setText(form.format(Double.valueOf(totaltax)));
                       /* if(Integer.valueOf(totaltax)>0){
                            tv_taxrateval.setText(form.format(taxrate)+"%");
                        }
                        else{
                            tv_taxrateval.setText("0.00%");
                        }
*/
                        tv_discount.setText("Discount (" + response.body().getOrderDetails().get(0).getDiscountCouponName() + ")");
                        // tv_discountval.setText("- ₹ " + response.body().getOrderDetails().get(0).getTotalDiscount());

                        try {

                            double discountval = Double.valueOf(response.body().getOrderDetails().get(0).getTotalDiscount());
                            int max = (int) discountval;

                            tv_discountval.setText(form.format(max));




                            double delicharge=Double.parseDouble(response.body().getOrderDetails().get(0).getDeliveryCharges());
                            //double carttotalval = Double.valueOf(response.body().getOrderDetails().get(0).getTotalAmount());
                            //double carttotal=carttotalval- discountval;
                            double carttotal=Double.valueOf(response.body().getOrderDetails().get(0).getTotalAmount());


                            if(delicharge==0){
                                InvoiceActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                                InvoiceActivity.tv_deliveryval.setText("0.00");
                            }
                            else{
                                InvoiceActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                                InvoiceActivity.tv_deliveryval.setText(form.format(delicharge));

                            }
                        } catch (Exception ex) {

                        }


                        String refundstatus = response.body().getOrderDetails().get(0).getRefundStatus();

                        if (refundstatus.toString().length() > 1) {
                            ll_refund.setVisibility(View.VISIBLE);
                            tv_refund.setText("Refund Status : " + refundstatus);
                        } else {

                            tv_refund.setText("Refund Status : " + refundstatus);
                            ll_refund.setVisibility(View.GONE);
                        }


                        try {

                            ordertype = response.body().getOrderDetails().get(0).getOrderType();
                            totalamount = response.body().getOrderDetails().get(0).getTotalAmount();
                            totaltax = response.body().getOrderDetails().get(0).getTotalTax();
                            totaldiscount = response.body().getOrderDetails().get(0).getTotalDiscount();
                            grandtotal = response.body().getOrderDetails().get(0).getGrandTotalAmount();


                        } catch (Exception ex) {

                        }


                        if (response.body().getPaymentDetails().size() > 0) {



                            rl_payment.setVisibility(View.GONE);
                            btn_addpayment.setVisibility(View.GONE);

                            int paymentsize = response.body().getPaymentDetails().size() - 1;
                            /*String mode1="";
                            if(response.body().getPaymentDetails().get(paymentsize).getPaymentMode().equalsIgnoreCase("")){
                                mode1="wallet";
                            }
                            else{
                                mode1=response.body().getPaymentDetails().get(paymentsize).getPaymentMode();
                            }*/


                            tv_paymenttype.setText("Payment Status : " + response.body().getPaymentDetails().get(paymentsize).getPaymentStatus());
                            //tv_paymenttype.setText("Payment Status : Pending" );
                            tv_paymentmode.setText("Payment Mode : " + response.body().getPaymentDetails().get(paymentsize).getPaymentMode());
                            tv_paymentdate.setText(formatDate(response.body().getPaymentDetails().get(paymentsize).getCreatedAt()));


                            if (response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("credit")) {
                                tv_paymentdue.setVisibility(View.VISIBLE);
                                tv_paymentduepaynow.setVisibility(View.VISIBLE);
                                tv_paymenttype.setText("Payment Status : Credit");
                                tv_paymentmode.setText("Payment Mode : Pay Later");


                            } /*else if(response.body().getPaymentDetails().get(paymentsize).getPaymentMode().equalsIgnoreCase("COD ")){
                                tv_paymentduepaynow.setVisibility(View.GONE);
                            }*/
                            else {
                                tv_paymentdue.setVisibility(View.GONE);
                                tv_paymentduepaynow.setVisibility(View.GONE);
                            }


                            try {

                                //double paid = Double.valueOf(response.body().getPaymentDetails().get(paymentsize).getPaymentAmount());
                                //double ordamount = Double.valueOf(response.body().getPaymentDetails().get(paymentsize).getOrderPrice());
                                double ordamount = Double.valueOf(response.body().getNewPayment().get(0).getOrderAmount());

                                double paid = Double.valueOf(response.body().getNewPayment().get(0).getAmountPaid());
                                double paymentdue = Double.valueOf(response.body().getNewPayment().get(0).getOrderAmountDue());

                                due=ordamount-paid;
                                int max = (int) due;

                                //Log.d("mydues", String.valueOf(paymentdue));
                                tv_paymentdue.setText("Payment Due : ₹ " + form.format(paymentdue));
                                tv_paymentamount.setText("Amount Paid : ₹ " + form.format(paid));

                                //if (paymentdue > 0 || response.body().getPaymentDetails().get(paymentsize).getPaymentAmount().equalsIgnoreCase(response.body().getPaymentDetails().get(paymentsize).getOrderPrice())) {
                                if(paid!=ordamount||paymentdue > 0)
                                {
                                    if(response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("Pay Later"))
                                    {
                                        rl_retrypayment.setVisibility(View.GONE);
                                        tv_paymentdue.setVisibility(View.VISIBLE);
                                        tv_paymentdue.setText("Payment Due : ₹ " + form.format(paymentdue));
                                        tv_paymentduepaynow.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    tv_paymentdue.setVisibility(View.GONE);
                                    tv_paymentduepaynow.setVisibility(View.GONE);
                                }


                            } catch (Exception ex) {

                            }


                            if (response.body().getPaymentDetails().get(paymentsize).getPaymentStatus().equalsIgnoreCase("FAILED")) {
                                //rl_retrypayment.setVisibility(View.VISIBLE);
                                if(response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("Credit")) {
                                    rl_retrypayment.setVisibility(View.GONE);
                                    //btn_retrypayment.setText("Payment");
                                    //tv_paymentfailed.setText("Payment Pending");
                                    tv_paymentduepaynow.setVisibility(View.VISIBLE);
                                    tv_paymentfailed.setTextColor(getResources().getColor(R.color.colorGreen));
                                    tv_paymenttype.setText("Payment Status : PENDING ");
                                    tv_paymenttype.setTextColor(getResources().getColor(R.color.colorGreen));
                                    tv_paymentref.setText("In case your payment is failed and haven’t completed the payment process then you can retry payment to complete your order.");
                                    tv_retrypayment.setVisibility(View.GONE);
                                    tv_retrypaymentfooter.setVisibility(View.GONE);
                                    tv_paymentref.setVisibility(View.GONE);
                                    //tv_paymenttype.setText("Payment Status : " + response.body().getPaymentDetails().get(paymentsize).getPaymentStatus());
                                    //tv_paymentref.setVisibility(View.VISIBLE);
                                    //tv_paymentduepaynow
                                    String amountpayable = tv_paymentamount.getText().toString();
                                    amountpayable = amountpayable.replace("Paid", "To be Paid");
                                    tv_paymentamount.setText(amountpayable);
                                }
                                else{
                                    tv_paymenttype.setText("Payment Status : " + response.body().getPaymentDetails().get(paymentsize).getPaymentStatus());
                                    tv_paymenttype.setTextColor(getResources().getColor(R.color.colorRed));
                                    //tv_paymentref.setText("In case your payment is failed and haven’t completed the payment process then you can retry payment to complete your order.");
                                    //tv_paymentref.setVisibility(View.VISIBLE);
                                    rl_retrypayment.setVisibility(View.GONE);
                                    tv_paymentref.setVisibility(View.GONE);
                                    //tv_paymentduepaynow
                                    String amountpayable = tv_paymentamount.getText().toString();
                                    amountpayable = amountpayable.replace("Paid", "To be Paid");
                                    tv_paymentamount.setText(amountpayable);
                                }

                            }
                            else if(response.body().getPaymentDetails().get(paymentsize).getPaymentStatus().equalsIgnoreCase("COD")) {
                                rl_retrypayment.setVisibility(View.GONE);
                                btn_retrypayment.setText("Payment");
                                tv_paymentfailed.setText("Payment Pending");
                                tv_paymentfailed.setTextColor(getResources().getColor(R.color.colorGreen));
                                tv_paymenttype.setText("Payment Status : PENDING ");
                                //tv_paymentmode.setText("Payment Mode : Pay on Delivery");
                                tv_paymenttype.setTextColor(getResources().getColor(R.color.colorGreen));
                                tv_paymentref.setText("In case your payment is failed and haven’t completed the payment process then you can retry payment to complete your order.");
                                tv_retrypayment.setVisibility(View.GONE);
                                tv_retrypaymentfooter.setVisibility(View.GONE);
                                tv_paymentref.setVisibility(View.GONE);
                                //tv_paymenttype.setText("Payment Status : " + response.body().getPaymentDetails().get(paymentsize).getPaymentStatus());
                                //tv_paymentref.setVisibility(View.VISIBLE);
                                //tv_paymentduepaynow
                                String amountpayable = tv_paymentamount.getText().toString();
                                //amountpayable = amountpayable.replace("Paid", "To be Paid");
                                tv_paymentamount.setText(amountpayable);
                            }

                            else {
                                rl_retrypayment.setVisibility(View.GONE);
                            }
                            for (int i = 0; i < response.body().getPaymentDetails().size(); i++) {
                                String paymentdetails = "Paid ₹ " + response.body().getPaymentDetails().get(i).getPaymentAmount() +
                                        "  on " + (response.body().getPaymentDetails().get(i).getCreatedAt()) +
                                        "  Payment Mode: " + response.body().getPaymentDetails().get(i).getPaymentMode() + ", Status : " + response.body().getPaymentDetails().get(i).getPaymentStatus();
                                paymentList.add(paymentdetails);

                            }
                            paymentlistView.setAdapter(paymentListAdapter);


                        } else {
                            btn_addpayment.setVisibility(View.GONE);
                            rl_payment.setVisibility(View.GONE);
                        }


                        invoiceurl = response.body().getOrderDetails().get(0).getInvoiceUrl();

                        step_1.setSubtitle(formatDate(response.body().getOrderDetails().get(0).getCreatedAt()));
                        step_1.setPadding(5, 5, 5, 5);

                        String orderstatus = response.body().getOrderDetails().get(0).getDeliveryStatus();

                        if (orderstatus.equalsIgnoreCase("Ordered")) {
                            step_1.setActive(true);
                            step_1.setSubtitle(formatDate(response.body().getOrderDetails().get(0).getCreatedAt()));
                            step_1.setPadding(5, 5, 5, 5);
                            sq_layout.start();
                            if(response.body().getPaymentDetails().get(0).getPaymentStatus().equalsIgnoreCase("COD")||response.body().getPaymentDetails().get(0).getPaymentStatus().equalsIgnoreCase("FAILED"))
                            {
                                rl_retrypayment.setVisibility(View.GONE);
                            }else {
                                rl_retrypayment.setVisibility(View.GONE);
                            }
                            if (response.body().getPaymentDetails().get(0).getPaymentStatus().equalsIgnoreCase("success") || response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("COD")) {
                                try {
                                    cancelhour = response.body().getOrderDetails().get(0).getMaxCancellationHours();
                                    ordercreatedat = response.body().getOrderDetails().get(0).getCreatedAt();
                                    double cacelminutes = Integer.valueOf(cancelhour) * 60;
                                    int diff = twoDatesBetweenTime(ordercreatedat);
                                    if (diff > cacelminutes) {
                                        rl_cancel.setVisibility(View.GONE);
                                        step_1.setActive(false);
                                        step_2.setActive(true);
                                        sq_layout.start();
                                        step_1.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                                        step_2.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                                    } else {
                                        rl_cancel.setVisibility(View.VISIBLE);
                                    }
                                } catch (Exception ex) {

                                }

                            } else {
                                rl_cancel.setVisibility(View.GONE);
                            }



                        }
                        else if (orderstatus.equalsIgnoreCase("Plucking")) {
                            step_6.setActive(true);
                            sq_layout.start();
                            if (response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("COD")) {
                                rl_retrypayment.setVisibility(View.GONE);
                            } else {
                                rl_retrypayment.setVisibility(View.GONE);
                            }
                            step_1.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_2.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_6.setTitleTextAppearance(R.style.txt_style_sequencetitle);

                        }else if (orderstatus.equalsIgnoreCase("Dispatched")) {
                            step_3.setActive(true);
                            sq_layout.start();
                            if(response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("COD"))
                            {
                                rl_retrypayment.setVisibility(View.GONE);
                            }else {
                                rl_retrypayment.setVisibility(View.GONE);
                            }
                            step_1.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_2.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_3.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_6.setTitleTextAppearance(R.style.txt_style_sequencetitle);


                        } else if (orderstatus.equalsIgnoreCase("In-transit")) {
                            step_4.setActive(true);
                            sq_layout.start();
                            if(response.body().getPaymentDetails().get(0).getPaymentMode().equalsIgnoreCase("COD"))
                            {
                                rl_retrypayment.setVisibility(View.GONE);
                            }else {
                                rl_retrypayment.setVisibility(View.GONE);
                            }
                            step_1.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_2.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_3.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_4.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_6.setTitleTextAppearance(R.style.txt_style_sequencetitle);


                        } else if (orderstatus.equalsIgnoreCase("Delivered")) {
                            step_5.setActive(true);
                            sq_layout.start();
                            rl_retrypayment.setVisibility(View.GONE);
                            step_1.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_2.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_3.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_4.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_5.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_6.setTitleTextAppearance(R.style.txt_style_sequencetitle);


                        } else if (orderstatus.equalsIgnoreCase("Cancelled")) {
                            //cancelOrder();

                            //step_2.setSubtitle(formatDate(response.body().getOrderDetails().get(0).getCreatedAt()));
                            sq_layout.setProgressBackgroundColor(getResources().getColor(R.color.colorRed));
                            sq_layout.setProgressForegroundColor(getResources().getColor(R.color.colorRed));

                            step_2.setPadding(5, 5, 5, 5);
                            step_2.setTitle("Cancelled");
                            sq_layout.start();
                            step_1.setActive(false);
                            step_2.setActive(true);

                            step_3.setVisibility(View.GONE);
                            step_4.setVisibility(View.GONE);
                            step_5.setVisibility(View.GONE);
                            step_6.setVisibility(View.GONE);
                            step_2.setTitleTextAppearance(R.style.txt_style_sequencetitle);
                            step_1.setTitleTextAppearance(R.style.txt_style_sequencetitlenormal);


                            rl_cancel.setVisibility(View.GONE);
                            rl_retrypayment.setVisibility(View.GONE);


                        }


                        List<OrderProductList> mListData = response.body().getOrderDetails().get(0).getOrderProductList();

                        //List<OrderProductList> mListData = response.body().getOrderDetails().get(position).getOrderProductList();
                        if (mListData != null && mListData.size() > 0) {
                            orderproductList.addAll(mListData);


                        } else {
                            InvoiceActivity.tv_priceval.setText("0.00");
                            //InvoiceActivity.tv_gstval.setText("0.00");
                            InvoiceActivity.tv_totalpaidval.setText("0.00");
                            InvoiceActivity.rl_content2.setVisibility(View.GONE);
//                            InvoiceActivity.tv_rv1.setText("No Items!");
                        }


                        recyclerView.setAdapter(orderDetailsAdapter);
                        recyclerView2.setAdapter(orderDetailsImageAdapter);
                        recyclerView2.setNestedScrollingEnabled(false);
                        recyclerView.setNestedScrollingEnabled(false);

                        Utilities.dismissDialog();


                        // Singleton.getInstance().showLongToast(mContext, diff);

                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private String formatDate(String dateString) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("EE, MMM dd yyyy");

        Date d = null;
        try {
            d = input.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);

        return formatted;
    }


    public Integer twoDatesBetweenTime(String oldtime) {
        // TODO Auto-generated method stub
        int day = 0;
        int hh = 0;
        int mm = 0;

        int differencemin = 0;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldDate = dateFormat.parse(oldtime);
            Date cDate = new Date();
            Long timeDiff = cDate.getTime() - oldDate.getTime();
            day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
            differencemin = (int) TimeUnit.MILLISECONDS.toMinutes(timeDiff);
            hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
            mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       /* if(day==0)
        {
            return hh + " hour " + mm + " min";
        }
        else if(hh==0)
        {
            return mm + " min";
        }
        else
        {
            return day + " days " + hh + " hour " + mm + " min";
        }*/

        return differencemin;
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }

    @Override
    public void onBackPressed() {

        if (from.equalsIgnoreCase("finish")) {
            Intent mainIntent = new Intent(InvoiceActivity.this, OrderDetailsActivity.class);
            //InvoiceActivity.this.finish();
            InvoiceActivity.this.startActivity(mainIntent);
        } else {
            //super.onBackPressed();
            InvoiceActivity.this.finish();
        }


    }

    private void actionOnBackPress() {

        InvoiceActivity.this.finish();
    }


    private void cancelOrder() {

        Utilities.showLoading(mContext);
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setOrderId(orderid);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<OrderCancelResponse> call = RetrofitUrlConnection.loadJSON(token).canclemyorder(orderCancelRequest);

            call.enqueue(new Callback<OrderCancelResponse>() {
                @Override
                public void onResponse(Call<OrderCancelResponse> call, Response<OrderCancelResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();
                        initUI();

                    }

                    Utilities.dismissDialog();

                }


                @Override
                public void onFailure(Call<OrderCancelResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public class Downloading extends AsyncTask<String, Integer, String> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... url) {
            File mydir = new File(Environment.getExternalStorageDirectory() + "/advira");
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url[0]);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
            String date = dateFormat.format(new Date());

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle("Downloading")
                    .setDestinationInExternalPublicDir("/advira", date + ".jpg");

            manager.enqueue(request);
            return mydir.getAbsolutePath() + File.separator + date + ".jpg";
        }

        @Override
        public void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Singleton.getInstance().showShortToast(mContext, "Invoice downloaded and saved in your directory!! .");

            //Toast.makeText(mContext, "Invoice downloaded and saved in your directory!! .", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory,"/Download/" + "advira_invoice");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
                pdfFile.canRead();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            //OpenPDF();
            return null;
        }


        @Override
        public void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //OpenPDF();
            //Singleton.getInstance().showShortToast(mContext, "Invoice downloaded and saved in 'advira_invoice' folder in your directory!!");

            //Toast.makeText(mContext, "Invoice downloaded and saved in your directory!! .", Toast.LENGTH_SHORT).show();
        }

    }

    public void OpenPDF(String filename) {
        try {
            //String fileName = strings[1];  // -> maven.pdf
            /*File file = null;
            //File(Environment.getExternalStorageDirectory().absolutePath + "/" + filename)
            file = new File(Environment.getExternalStorageDirectory()+"/download/advira_invoice/"+filename);
            Toast.makeText(getApplicationContext(), file.toString() , Toast.LENGTH_LONG).show();
            if(file.exists()) {*/
                /*Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setData(Uri.parse(filename));
            startActivity(target);

            Intent intent = Intent.createChooser(target, "Open File");
                /*try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }
            }
            else
                Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();

        }*/} catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CheckPin() {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CheckPinRequest checkPinRequest = new CheckPinRequest();
        checkPinRequest.setPincode(pincode);

        try {

            Call<CheckPinResponse> call = RetrofitUrlConnection.loadJSON(token).pincode(checkPinRequest);

            call.enqueue(new Callback<CheckPinResponse>() {
                @Override
                public void onResponse(Call<CheckPinResponse> call, Response<CheckPinResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        if (response.body().getPincodeDetails().size() > 0) {
                            if (response.body().getPincodeDetails().get(0).getIsOperational().equalsIgnoreCase("yes")) {

                                tv_expecteddelivery.setVisibility(View.VISIBLE);
                                tv_expecteddelivery.setText("Expected delivery in " + response.body().getPincodeDetails().get(0).getApproxDeliveryTime());

                                tv_expecteddelivery.setTextColor(getResources().getColor(R.color.colorGreen));
                            }
                        } else {
                            tv_expecteddelivery.setVisibility(View.VISIBLE);
                            tv_expecteddelivery.setText("We are currently not operational in this location");
                            tv_expecteddelivery.setTextColor(getResources().getColor(R.color.colorRed));
                        }

                        Utilities.dismissDialog();

                    } else {

                        Utilities.dismissDialog();
                        // Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
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


}
