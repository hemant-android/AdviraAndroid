package com.advira.advirafarm.buyer.ui.payment.paylaterpayment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentRequest;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentResponse;
import com.advira.advirafarm.buyer.ui.payment.api.RzpayOrderInitRequest;
import com.advira.advirafarm.buyer.ui.payment.api.RzpayOrderInitResponse;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Razorpay;
import com.razorpay.ValidateVpaCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNTB2B;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNTB2C;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PaymentCheck;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

public class PLPaymentOption extends Activity implements IConsts {

        private static final String TAG = "PLPaymentOption";
        String paymenttype = "";
        String paymentcode = "";
        String totalval = "";
        String addressid = "";
        String address = "";
        String ordertype = "";
        String totalamount = "";
        String totaltax = "";
        String totaldiscount = "";
        String grandtotal = "";

        String orderNote = "Order";
        String customerName = "";
        String customerPhone = "";
        String customerEmail = "";
        String rzrpayorderid = "";
        String receipt = "";
        String rzrpaypayamount = "";
        String paymentref = "";
        String retryorderid = "";
        String from = "";
        Map<String, String> params = new HashMap<>();
        double ca = 0;
        private RelativeLayout rl_back;
        private Context mContext;
        private String taxtoken, orderId = "", orderNo = "";
        private LinearLayout ll_wallets;
        private Button btn_proceedtopay;
        private RadioGroup rg_wallet;
        private RadioGroup rg_wallet2;
        private EditText et_dcardno;
        private EditText et_dcardname;
        private Spinner spn_dmonth;
        private Spinner spn_dyear;
        private EditText et_dcvv;
        private CardView cv_dcards;
        private TextView txt_amounttobepaid;
        private CardView cv_upi;
        private RelativeLayout ll_vpa;
        private RadioGroup rg_upi2;
        private EditText et_vpa;
        private RelativeLayout rl_upiapplogos;
        private String discountid = "";
        private String discount_coupon_name = "";
        private String discount_type = "";
        private String discount_amount = "";
        private String discount_details = "";
        private String credit_limit = "";
        private String credit_availed = "0";
        private String credit_balance = "";
        private RadioButton rb_paylater, rb_emi, rb_nocostemi, rb_cod;
        private TextView txt_availablecredit;
        private Razorpay razorpay;

        private CardView cv_emicards;
        private EditText et_cardno;
        private EditText et_cardname;
        private Spinner spn_month;
        private Spinner spn_year;
        private Spinner spn_emitenure;
        private EditText et_cvv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_paymentoptions);
            WebView.setWebContentsDebuggingEnabled(false);


            initUI();

            et_cardno.addTextChangedListener(new TextWatcher() {

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
                //ye wali activity call hoti hai retry payment me
                    if(s.length()>5)
                    {
                        if(razorpay.isValidCardNumber(et_cardno.getText().toString()))
                        {
                            if (s.length() > 5) {

                                try {
                                    String cardnetwork = razorpay.getCardNetwork(et_cardno.getText().toString());

                                    if (cardnetwork.equalsIgnoreCase("visa")) {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa, 0);
                                    } else if (cardnetwork.equalsIgnoreCase("mastercard")) {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_master, 0);
                                    } else if (cardnetwork.equalsIgnoreCase("maestro")) {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_maestro, 0);
                                    } else if (cardnetwork.equalsIgnoreCase("rupay")) {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_rupay, 0);
                                    } else if (cardnetwork.equalsIgnoreCase("amex")) {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_americanexpress, 0);
                                    } else if (cardnetwork.equalsIgnoreCase("diners")) {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dinersclub, 0);
                                    } else {
                                        et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_field_credit_card_icon, 0);

                                    }

                                } catch (Exception ex) {

                                }
                            } else {
                                et_cardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_field_credit_card_icon, 0);

                            }
                        }
                        else
                        {
                            et_cardno.setError("Invalid Card No");
                        }
                    }

                }
            });


            et_dcardno.addTextChangedListener(new TextWatcher() {

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
                    if (s.length() > 5) {

                        try {
                            String cardnetwork = razorpay.getCardNetwork(et_dcardno.getText().toString());

                            if (cardnetwork.equalsIgnoreCase("visa")) {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa, 0);
                            } else if (cardnetwork.equalsIgnoreCase("mastercard")) {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_master, 0);
                            } else if (cardnetwork.equalsIgnoreCase("maestro")) {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_maestro, 0);
                            } else if (cardnetwork.equalsIgnoreCase("rupay")) {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_rupay, 0);
                            } else if (cardnetwork.equalsIgnoreCase("amex")) {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_americanexpress, 0);
                            } else if (cardnetwork.equalsIgnoreCase("diners")) {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dinersclub, 0);
                            } else {
                                et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_field_credit_card_icon, 0);

                            }

                        } catch (Exception ex) {

                        }
                    } else {
                        et_dcardno.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_field_credit_card_icon, 0);

                    }

                }
            });

            rl_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    PLPaymentOption.this.finish();
                }
            });

            rg_wallet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    int selectedId = rg_wallet.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(selectedId);

                    if (radioButton.getText().toString().equalsIgnoreCase("Pay With EMI")) {
                        cv_emicards.setVisibility(View.GONE);
                        cv_dcards.setVisibility(View.GONE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        paymenttype = "EMI";
                        paymentcode = "emi";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("No Cost EMI")) {
                        cv_emicards.setVisibility(View.GONE);
                        cv_dcards.setVisibility(View.GONE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        paymenttype = "NOCOSTEMI";
                        paymentcode = "nocostemi";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with UPI")) {

                        paymenttype = "";
                        paymentcode = "";

                        cv_emicards.setVisibility(View.GONE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_dcards.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.VISIBLE);
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Wallets")) {

                        paymenttype = "";
                        paymentcode = "";

                        ll_wallets.setVisibility(View.VISIBLE);
                        cv_dcards.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        cv_emicards.setVisibility(View.GONE);
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Debit/Credit Card")) {
                        cv_dcards.setVisibility(View.VISIBLE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        cv_emicards.setVisibility(View.GONE);
                        paymenttype = "Pay with Debit/Credit Card";
                        paymentcode = "card";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Net Banking")) {
                        cv_dcards.setVisibility(View.GONE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        cv_emicards.setVisibility(View.GONE);
                        paymenttype = "Pay with Net Banking";
                        paymentcode = "netbanking";
                        btn_proceedtopay.setText("CHOOSE BANK TO PAY ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay Later")) {
                        cv_dcards.setVisibility(View.GONE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        cv_emicards.setVisibility(View.GONE);
                        paymenttype = "Pay Later";
                        paymentcode = "paylater";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay On Delivery/COD")) {
                        cv_dcards.setVisibility(View.GONE);
                        ll_wallets.setVisibility(View.GONE);
                        cv_upi.setVisibility(View.GONE);
                        cv_emicards.setVisibility(View.GONE);
                        paymenttype = "COD";
                        paymentcode = "cod";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    }

                }
            });

            rg_wallet2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    int selectedId = rg_wallet2.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(selectedId);
                    paymenttype = "";
                    paymentcode = "";

                    if (radioButton.getText().toString().equalsIgnoreCase("Paytm")) {
                        paymenttype = "wallet";
                        paymentcode = "paytm";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Amazon Pay")) {
                        paymenttype = "wallet";
                        paymentcode = "amazonpay";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Freecharge")) {
                        paymenttype = "wallet";
                        paymentcode = "freecharge";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("PhonePe")) {
                        paymenttype = "wallet";
                        paymentcode = "phonepe";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("MobiKwik")) {
                        paymenttype = "wallet";
                        paymentcode = "mobikwik";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Ola Money")) {
                        paymenttype = "wallet";
                        paymentcode = "olamoney";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Reliance Jio Money")) {
                        paymenttype = "wallet";
                        paymentcode = "jiomoney";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Airtel Money")) {
                        paymenttype = "wallet";
                        paymentcode = "airtelmoney";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);

                    }

                }
            });

            rg_upi2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    int selectedId = rg_upi2.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(selectedId);

                    paymenttype = "";
                    paymentcode = "";

                    if (radioButton.getText().toString().equalsIgnoreCase("Choose UPI Apps")) {
                        paymenttype = "Pay with UPI";
                        paymentcode = "upi";
                        ll_vpa.setVisibility(View.GONE);
                        btn_proceedtopay.setText("CHOOSE APP TO PAY ₹" + grandtotal);
                        rl_upiapplogos.setVisibility(View.VISIBLE);

                    } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Other UPI ID")) {

                        ll_vpa.setVisibility(View.VISIBLE);
                        paymenttype = "Pay with vpa";
                        paymentcode = "upi";
                        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);
                        rl_upiapplogos.setVisibility(View.GONE);

                    }

                }
            });


            btn_proceedtopay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (paymenttype.equalsIgnoreCase("")) {
                        Singleton.getInstance().showLongToast(mContext, "Please select a payment mode");
                    } else {
                        PaymentType();
                    }

                }
            });

        }


        private void initUI() {

            mContext = PLPaymentOption.this;
            razorpay = new Razorpay(this);
            customerName = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
            customerEmail = SharedPrefUtil.getUserEmail(mContext, SHARED_PREF_UserEmailID, "");
            customerPhone = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");

            rl_back = findViewById(R.id.rl_back);
            ll_wallets = findViewById(R.id.ll_wallets);
            btn_proceedtopay = findViewById(R.id.btn_proceedtopay);
            rg_wallet = findViewById(R.id.rg_wallet);
            rg_wallet2 = findViewById(R.id.rg_wallet2);

            et_dcardno = findViewById(R.id.et_dcardno);
            et_dcardname = findViewById(R.id.et_dcardname);
            spn_dmonth = findViewById(R.id.spn_dmonth);
            spn_dyear = findViewById(R.id.spn_dyear);
            et_dcvv = findViewById(R.id.et_dcvv);
            cv_dcards = findViewById(R.id.cv_dcards);
            cv_upi = findViewById(R.id.cv_upi);
            txt_amounttobepaid = findViewById(R.id.txt_amounttobepaid);
            ll_vpa = findViewById(R.id.ll_vpa);
            rg_upi2 = findViewById(R.id.rg_upi2);
            et_vpa = findViewById(R.id.et_vpa);

            rl_upiapplogos = findViewById(R.id.rl_upiapplogos);
            rb_paylater = findViewById(R.id.rb_paylater);
            rb_emi = findViewById(R.id.rb_emi);
            rb_nocostemi = findViewById(R.id.rb_nocostemi);
            rb_cod = findViewById(R.id.rb_cod);

            rb_paylater.setVisibility(View.GONE);
            rb_cod.setVisibility(View.GONE);
            rb_nocostemi.setVisibility(View.GONE);
            rb_emi.setVisibility(View.GONE);

            txt_availablecredit = findViewById(R.id.txt_availablecredit);

            cv_emicards = findViewById(R.id.cv_emicards);
            et_cardno = findViewById(R.id.et_cardno);
            et_cardname = findViewById(R.id.et_cardname);
            spn_month = findViewById(R.id.spn_month);
            spn_year = findViewById(R.id.spn_year);
            et_cvv = findViewById(R.id.et_cvv);
            spn_emitenure = findViewById(R.id.spn_emitenure);

            et_dcardno.setText("4111111111111111");
            et_dcardname.setText("Test");
            et_dcvv.setText("123");
            et_cardno.setText("4111111111111111");
            et_cardname.setText("Test");
            et_cvv.setText("123");


            // Spinner Drop down elements
            List<String> year = new ArrayList<String>();
            // year.add("2020");
            year.add("2021");
            year.add("2022");
            year.add("2023");
            year.add("2024");
            year.add("2025");
            year.add("2026");
            year.add("2027");
            year.add("2028");
            year.add("2029");
            year.add("2030");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spn_dyear.setAdapter(dataAdapter);
            spn_year.setAdapter(dataAdapter);

            // Spinner Drop down elements
            List<String> month = new ArrayList<String>();
            month.add("01");
            month.add("02");
            month.add("03");
            month.add("04");
            month.add("05");
            month.add("06");
            month.add("07");
            month.add("08");
            month.add("09");
            month.add("10");
            month.add("11");
            month.add("12");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);

            // Drop down layout style - list view with radio button
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner

            spn_month.setAdapter(dataAdapter2);
            spn_dmonth.setAdapter(dataAdapter2);

            spn_month.setSelection(1);
            spn_year.setSelection(1);

            spn_dmonth.setSelection(1);
            spn_dyear.setSelection(1);


            List<String> emitenure = new ArrayList<String>();
            emitenure.add("3 Months");
            emitenure.add("6 Months");
            emitenure.add("9 Months");
            emitenure.add("12 Months");
            emitenure.add("18 Months");
            emitenure.add("24 Months");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, emitenure);

            // Drop down layout style - list view with radio button
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spn_emitenure.setAdapter(dataAdapter3);


            Bundle extras = getIntent().getExtras();


            if (extras != null) {

                addressid = extras.getString("addressid");
                address = extras.getString("address");
                ordertype = extras.getString("ordertype");
                totalamount = extras.getString("totalamount");
                totaltax = extras.getString("totaltax");
                totaldiscount = extras.getString("totaldiscount");
                grandtotal = extras.getString("grandtotal");
                retryorderid = extras.getString("orderid");
                orderId = extras.getString("orderid");
                from = extras.getString("from");

                discountid = extras.getString("discountid");
                discount_coupon_name = extras.getString("discount_coupon_name");
                discount_type = extras.getString("discount_type");
                discount_amount = extras.getString("discount_amount");
                discount_details = extras.getString("discount_details");
                credit_limit = extras.getString("credit_limit");
                credit_availed = extras.getString("credit_availed");
                credit_balance = extras.getString("credit_balance");


                double gt = 0;
                double td = 0;

                try {

                    gt = Double.valueOf(grandtotal);
                    ca = Double.valueOf(credit_balance);
                    td = Double.valueOf(totaldiscount);

                } catch (Exception ex) {

                    credit_balance = SharedPrefUtil.getAvailableCredit(mContext, SHARED_PREF_AvailableCredit, "0");
                    ca = Double.valueOf(credit_balance);

                }





                if (gt >= 3000) {
                    rb_emi.setEnabled(true);
                    rb_nocostemi.setEnabled(true);
                    rb_emi.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    rb_nocostemi.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                } else {
                    rb_emi.setEnabled(false);
                    rb_nocostemi.setEnabled(false);
                    rb_emi.setTextColor(mContext.getResources().getColor(R.color.colorGreymidlight));
                    rb_nocostemi.setTextColor(mContext.getResources().getColor(R.color.colorGreymidlight));
                }

                rb_emi.setEnabled(false);
                rb_nocostemi.setEnabled(false);
                rb_emi.setTextColor(mContext.getResources().getColor(R.color.colorGreymidlight));
                rb_nocostemi.setTextColor(mContext.getResources().getColor(R.color.colorGreymidlight));

                String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");



                if (td > 0) {
                    rb_nocostemi.setEnabled(false);
                    rb_nocostemi.setTextColor(mContext.getResources().getColor(R.color.colorGreymidlight));
                }


            }


            txt_amounttobepaid.setText("Amount to pay : ₹ "+grandtotal);
            txt_amounttobepaid.setVisibility(View.GONE);

            btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + grandtotal);
        }


        private void rzpayOrderInitRequest(String orderId, String paymentoption) {

            Utilities.showLoading(mContext);
            String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


            RzpayOrderInitRequest rzpayOrderInitRequest = new RzpayOrderInitRequest();
            rzpayOrderInitRequest.setOrderId(orderId);
            rzpayOrderInitRequest.setAmount(grandtotal);
            Call<RzpayOrderInitResponse> call = RetrofitUrlConnection.loadJSON(token).rzpayOrderInit(rzpayOrderInitRequest);

            call.enqueue(new Callback<RzpayOrderInitResponse>() {
                @Override
                public void onResponse(Call<RzpayOrderInitResponse> call, Response<RzpayOrderInitResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        rzrpayorderid = response.body().getOrderInit().getId();
                        receipt = response.body().getOrderInit().getReceipt();
                        rzrpaypayamount = String.valueOf(response.body().getOrderInit().getAmountDue());


                        if (paymenttype.equalsIgnoreCase("Pay with Debit/Credit Card")) {

                            String name = et_dcardname.getText().toString();
                            String cardno = et_dcardno.getText().toString();
                            String cvv = et_dcvv.getText().toString();
                            String month = spn_dmonth.getSelectedItem().toString();
                            String year = spn_dyear.getSelectedItem().toString();


                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayPayment.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", name);
                            intent.putExtra("cardno", cardno);
                            intent.putExtra("cvv", cvv);
                            intent.putExtra("month", month);
                            intent.putExtra("year", year);
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);

                            startActivity(intent);
                        } else if (paymenttype.equalsIgnoreCase("EMI")) {

                            String name = et_dcardname.getText().toString();
                            String cardno = et_dcardno.getText().toString();
                            String cvv = et_dcvv.getText().toString();
                            String month = spn_dmonth.getSelectedItem().toString();
                            String year = spn_dyear.getSelectedItem().toString();
                            String tenure = spn_emitenure.getSelectedItem().toString();
                            tenure = tenure.replace(" Months", "");

                            paymentcode = paymentcode + tenure;


                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayEMIBank.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", name);
                            intent.putExtra("cardno", cardno);
                            intent.putExtra("cvv", cvv);
                            intent.putExtra("month", month);
                            intent.putExtra("year", year);
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);

                            startActivity(intent);
                        } else if (paymenttype.equalsIgnoreCase("NOCOSTEMI")) {

                            String name = et_dcardname.getText().toString();
                            String cardno = et_dcardno.getText().toString();
                            String cvv = et_dcvv.getText().toString();
                            String month = spn_dmonth.getSelectedItem().toString();
                            String year = spn_dyear.getSelectedItem().toString();
                            String tenure = spn_emitenure.getSelectedItem().toString();
                            tenure = tenure.replace(" Months", "");

                            paymentcode = paymentcode + tenure;


                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayNoCostEMIBank.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", name);
                            intent.putExtra("cardno", cardno);
                            intent.putExtra("cvv", cvv);
                            intent.putExtra("month", month);
                            intent.putExtra("year", year);
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);


                            intent.putExtra("ordertype", ordertype);
                            intent.putExtra("totalamount", totalamount);
                            intent.putExtra("totaltax", totaltax);
                            intent.putExtra("totaldiscount", totaldiscount);
                            intent.putExtra("addressid", addressid);
                            intent.putExtra("grandtotal", grandtotal);
                            intent.putExtra("discount_coupon_name", discount_coupon_name);
                            intent.putExtra("discount_type", discount_type);
                            intent.putExtra("discount_amount", discount_amount);
                            intent.putExtra("discount_details", discount_details);
                            intent.putExtra("discountid", discountid);
                            startActivity(intent);
                        } else if (paymenttype.equalsIgnoreCase("Pay with Net Banking")) {
                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayPayment.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", "");
                            intent.putExtra("cardno", "");
                            intent.putExtra("cvv", "");
                            intent.putExtra("month", "");
                            intent.putExtra("year", "");
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);

                            startActivity(intent);
                        } else if (paymenttype.equalsIgnoreCase("wallet")) {


                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayPayment.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", "wallet");
                            intent.putExtra("cardno", "");
                            intent.putExtra("cvv", "");
                            intent.putExtra("month", "");
                            intent.putExtra("year", "");
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);

                            startActivity(intent);
                        } else if (paymenttype.equalsIgnoreCase("Pay with upi")) {


                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayPaymentUPI.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", "");
                            intent.putExtra("cardno", "");
                            intent.putExtra("cvv", "");
                            intent.putExtra("month", "");
                            intent.putExtra("year", "");
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);
                            intent.putExtra("upiorvpa", "upi");
                            intent.putExtra("upiid", "");

                            startActivity(intent);
                        } else if (paymenttype.equalsIgnoreCase("Pay with vpa")) {

                            Intent intent = new Intent(PLPaymentOption.this, PayLaterRazorPayPaymentUPI.class);
                            intent.putExtra("paymentcode", paymentcode);
                            intent.putExtra("name", "");
                            intent.putExtra("cardno", "");
                            intent.putExtra("cvv", "");
                            intent.putExtra("month", "");
                            intent.putExtra("year", "");
                            intent.putExtra("order_id", rzrpayorderid);
                            intent.putExtra("receipt", receipt);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("customerEmail", customerEmail);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("grandtotal", rzrpaypayamount);
                            intent.putExtra("adviragrandtotal", grandtotal);
                            intent.putExtra("adviraorderid", orderId);
                            intent.putExtra("adviraorderno", orderNo);
                            intent.putExtra("upiorvpa", "vpa");
                            intent.putExtra("upiid", et_vpa.getText().toString());

                            startActivity(intent);
                        }


                    } else {
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }

                    Utilities.dismissDialog();
                }

                @Override
                public void onFailure(Call<RzpayOrderInitResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });

        }


        private void PaymentType() {


            if (paymenttype.equalsIgnoreCase("wallet")) {




                if (orderId.equalsIgnoreCase("")) {


                } else {

                    rzpayOrderInitRequest(orderId, "wallet");

                }


            } else if (paymenttype.equalsIgnoreCase("EMI")) {


                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    if (orderId.equalsIgnoreCase("")) {

                    } else {
                        rzpayOrderInitRequest(orderId, "emi");
                    }


                }


            } else if (paymenttype.equalsIgnoreCase("NOCOSTEMI")) {


                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    if (orderId.equalsIgnoreCase("")) {

                    } else {
                        rzpayOrderInitRequest(orderId, "nocostemi");
                    }


                }


            } else if (paymenttype.equalsIgnoreCase("Pay with Debit/Credit Card")) {

                if (et_dcardno.getText().toString().trim().length() < 14) {

                    et_dcardno.setError("Card No is required!");

                } else if (et_dcardname.getText().toString().trim().isEmpty()) {
                    et_dcardname.setError("Name is required!");

                } else if (et_dcvv.getText().toString().trim().length() < 3) {
                    et_dcvv.setError("CVV is required");

                } else {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {


                        if (orderId.equalsIgnoreCase("")) {

                        } else {
                            rzpayOrderInitRequest(orderId, "card");
                        }


                    }
                }

            } else if (paymenttype.equalsIgnoreCase("Pay with Net Banking")) {

                if (orderId.equalsIgnoreCase("")) {

                } else {
                    rzpayOrderInitRequest(orderId, "Net-Banking");
                }


            } else if (paymenttype.equalsIgnoreCase("Pay with UPI")) {

                if (orderId.equalsIgnoreCase("")) {


                } else {
                    rzpayOrderInitRequest(orderId, "upi");
                }

            } else if (paymenttype.equalsIgnoreCase("Pay with vpa")) {

                if (et_vpa.getText().toString().trim().length() < 6) {

                    et_vpa.setError("UPI ID is required!");

                } else {
                    if (orderId.equalsIgnoreCase("")) {


                    } else {
                        rzpayOrderInitRequest(orderId, "upi");
                    }
                }

            }


        }


        private void PaymentDetails(String paystatus) {

            Utilities.showLoading(mContext);
            String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
            String paytype = "";
            String paymode = "";
            String paidamount = grandtotal;
            String dueamount = "0";

            if (paymenttype.equalsIgnoreCase("wallet")) {
                paytype = "Cashfree";
                paymode = "Ewallets";

            } else if (paymenttype.equalsIgnoreCase("Pay with Credit Card")) {
                paytype = "Cashfree";
                paymode = "Credit-Card";

            } else if (paymenttype.equalsIgnoreCase("Pay with Debit/Credit Card")) {
                paytype = "RazorPay";
                paymode = "Debit-Card";

            } else if (paymenttype.equalsIgnoreCase("Pay with Net Banking")) {
                paytype = "RazorPay";
                paymode = "Net-Banking";

            } else if (paymenttype.equalsIgnoreCase("Pay with UPI")) {
                paytype = "RazorPay";
                paymode = "UPI";

            } else if (paymenttype.equalsIgnoreCase("Pay with vpa")) {
                paytype = "RazorPay";
                paymode = "UPI";

            }


            paymentref = "{" + paymentref + "}";


            PGPaymentRequest pgPaymentRequest = new PGPaymentRequest();
            pgPaymentRequest.setOrderId(orderId);
            pgPaymentRequest.setPaymentType(paytype);
            pgPaymentRequest.setPaymentMode(paymode);
            pgPaymentRequest.setPaymentAmount(paidamount);
            pgPaymentRequest.setPaymentDueAmount(dueamount);
            pgPaymentRequest.setPaymentRef(paymentref);
            pgPaymentRequest.setPaymentStatus(paystatus);

            try {

                Call<PGPaymentResponse> call = RetrofitUrlConnection.loadJSON(token).addpgpaymentdetails(pgPaymentRequest);

                call.enqueue(new Callback<PGPaymentResponse>() {
                    @Override
                    public void onResponse(Call<PGPaymentResponse> call, Response<PGPaymentResponse> response) {

                        if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                            Utilities.dismissDialog();

                            if (paystatus.equalsIgnoreCase("SUCCESS")) {
                                Intent mainIntent = new Intent(PLPaymentOption.this, PaymentSuccess.class);
                                mainIntent.putExtra("orderno", orderNo);
                                mainIntent.putExtra("orderid", orderId);
                                PLPaymentOption.this.startActivity(mainIntent);
                                PLPaymentOption.this.finish();
                            } else {
                                if (paystatus.equalsIgnoreCase("FAILED")) {

                                    //Singleton.getInstance().showShortToast(mContext, "Payment Failed");//remove toast
                                    Intent mainIntent = new Intent(mContext, PaymentFailure.class);
                                    //Intent mainIntent = new Intent(mContext, OrderPlacedActivity.class);

                                    mainIntent.putExtra("orderno", orderNo);
                                    mainIntent.putExtra("orderid", orderId);
                                    mainIntent.putExtra("addressid", addressid);
                                    mainIntent.putExtra("address", address);
                                    mainIntent.putExtra("ordertype", ordertype);
                                    mainIntent.putExtra("totalamount", totalamount);
                                    mainIntent.putExtra("totaltax", totaltax);
                                    mainIntent.putExtra("totaldiscount", totaldiscount);
                                    mainIntent.putExtra("grandtotal", grandtotal);
                                    PLPaymentOption.this.startActivity(mainIntent);
                                    PLPaymentOption.this.finish();
                                }


                            }
                        }else {
                            Utilities.dismissDialog();
                            //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                        }

                    }

                    @Override
                    public void onFailure(Call<PGPaymentResponse> call, Throwable t) {

                        Utilities.dismissDialog();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //Utilities.showLoading(mContext);
            //Same request code for all payment APIs.
            // Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
            // Log.d(TAG, "API Response : ");
            //Prints all extras. Replace with app logic.
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        if (bundle.getString(key) != null) {
                            // Log.d(TAG,"error"+ key + " : " + bundle.getString(key));
                            paymentref = paymentref + key + ":" + bundle.getString(key) + ", ";
                        }

                    }

                    try {
                        String txStatus = bundle.getString("txStatus");
                        String txMsg = bundle.getString("txMsg");
                        if (txStatus.equalsIgnoreCase("SUCCESS")) {
                            //Singleton.getInstance().showLongToast(mContext, "Payment successfully captured");//remove toast
                            PaymentDetails("SUCCESS");
                        } else if (txStatus.equalsIgnoreCase("FAILED")) {

                            if (txMsg.contains("Invalid Card")) {
                                SharedPrefUtil.setPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "retain");
                                //Singleton.getInstance().showLongToast(mContext, txMsg + " Payment Failed");

                            } else {
                                // PaymentDetails("FAILED");
                                finish();
                            }

                        }

                    } catch (Exception Ex) {

                    }
                }
            }
        }

        @Override
        public void onRestart() {
            super.onRestart();


            String paymentretain = SharedPrefUtil.getPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "");

            if (paymentretain.equalsIgnoreCase("retain")) {

            } else {
                paymenttype = "";
                paymentcode = "";

            }

        }


        @Override
        public void onBackPressed() {
            finish();

        }

    }
