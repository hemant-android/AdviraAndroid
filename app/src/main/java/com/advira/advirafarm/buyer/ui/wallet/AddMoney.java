package com.advira.advirafarm.buyer.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayEMIBank;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayNoCostEMIBank;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayPayment;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayPaymentUPI;
import com.advira.advirafarm.buyer.ui.subscrption.CheckBalance;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletOrderInitRequest;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletOrderInitResponse;
import com.advira.advirafarm.buyer.ui.wallet.razorpay.WalletRazorpayPayment;
import com.advira.advirafarm.buyer.ui.wallet.razorpay.WalletRazorpayPaymentUPI;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.razorpay.Razorpay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PaymentCheck;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserEmailID;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserMobile;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserName;

public class AddMoney extends AppCompatActivity {

    private static final String TAG = "AddMoney";
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
    String deliverycharges="";

    String orderNote = "Order";
    String customerName = "";
    String customerPhone = "";
    String customerEmail = "";
    String customerEmail1 = "";
    String rzrpayorderid = "";
    String receipt = "";
    String rzrpaypayamount = "";
    String paymentref = "";
    String retryorderid = "";
    String from = "";
    String addressID="";
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

    public static RadioButton rb_paylater, rb_emi, rb_nocostemi, rb_cod;
    private TextView txt_availablecredit;
    private Razorpay razorpay;

    private CardView cv_emicards;
    private EditText et_cardno;
    private EditText et_cardname;
    private Spinner spn_month;
    private Spinner spn_year;
    private Spinner spn_emitenure;
    private EditText et_cvv;

    private CardView cv_addmoney;
    private TextView tv_rupees;
    private String addmoney="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        InitUI();

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


                if (retryorderid.toString().length() > 0) {

                    if (from.equalsIgnoreCase("back")) {
                        AddMoney.this.finish();
                    } else {
                        Intent i = new Intent();
                        i.setClass(AddMoney.this, WalletActivity.class);
                        finishAffinity();
                        startActivity(i);
                    }

                } else {

                    Intent i = new Intent();
                    i.setClass(AddMoney.this, CheckBalance.class);
                    finish();
                    startActivity(i);
                }


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
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("No Cost EMI")) {
                    cv_emicards.setVisibility(View.GONE);
                    cv_dcards.setVisibility(View.GONE);
                    ll_wallets.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.GONE);
                    paymenttype = "NOCOSTEMI";
                    paymentcode = "nocostemi";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with UPI")) {

                    paymenttype = "";
                    paymentcode = "";

                    cv_emicards.setVisibility(View.GONE);
                    ll_wallets.setVisibility(View.GONE);
                    cv_dcards.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.VISIBLE);
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Wallets")) {

                    paymenttype = "";
                    paymentcode = "";

                    ll_wallets.setVisibility(View.VISIBLE);
                    cv_dcards.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.GONE);
                    cv_emicards.setVisibility(View.GONE);
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Debit/Credit Card")) {
                    cv_dcards.setVisibility(View.VISIBLE);
                    ll_wallets.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.GONE);
                    cv_emicards.setVisibility(View.GONE);
                    paymenttype = "Pay with Debit/Credit Card";
                    paymentcode = "card";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Net Banking")) {
                    cv_dcards.setVisibility(View.GONE);
                    ll_wallets.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.GONE);
                    cv_emicards.setVisibility(View.GONE);
                    paymenttype = "Pay with Net Banking";
                    paymentcode = "netbanking";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay Later")) {
                    cv_dcards.setVisibility(View.GONE);
                    ll_wallets.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.GONE);
                    cv_emicards.setVisibility(View.GONE);
                    paymenttype = "Pay Later";
                    paymentcode = "paylater";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay On Delivery")) {
                    cv_dcards.setVisibility(View.GONE);
                    ll_wallets.setVisibility(View.GONE);
                    cv_upi.setVisibility(View.GONE);
                    cv_emicards.setVisibility(View.GONE);
                    paymenttype = "COD";
                    paymentcode = "cod";
                    btn_proceedtopay.setText("Recharge Wallet");

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
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Amazon Pay")) {
                    paymenttype = "wallet";
                    paymentcode = "amazonpay";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Freecharge")) {
                    paymenttype = "wallet";
                    paymentcode = "freecharge";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("PhonePe")) {
                    paymenttype = "wallet";
                    paymentcode = "phonepe";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("MobiKwik")) {
                    paymenttype = "wallet";
                    paymentcode = "mobikwik";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Ola Money")) {
                    paymenttype = "wallet";
                    paymentcode = "olamoney";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Reliance Jio Money")) {
                    paymenttype = "wallet";
                    paymentcode = "jiomoney";
                    btn_proceedtopay.setText("Recharge Wallet");

                } else if (radioButton.getText().toString().equalsIgnoreCase("Airtel Money")) {
                    paymenttype = "wallet";
                    paymentcode = "airtelmoney";
                    btn_proceedtopay.setText("Recharge Wallet");

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
                    btn_proceedtopay.setText("Recharge Wallet");
                    rl_upiapplogos.setVisibility(View.VISIBLE);

                } else if (radioButton.getText().toString().equalsIgnoreCase("Pay with Other UPI ID")) {

                    ll_vpa.setVisibility(View.VISIBLE);
                    paymenttype = "Pay with vpa";
                    paymentcode = "upi";
                    btn_proceedtopay.setText("Recharge Wallet");
                    rl_upiapplogos.setVisibility(View.GONE);

                }

            }
        });


        btn_proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paymenttype.equalsIgnoreCase("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddMoney.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Please select a payment mode.");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                    //Singleton.getInstance().showLongToast(mContext, "Please select a payment mode");//remove toast
                } else {

                    walletPaymentInitRequest();
                    //PaymentType();
                }

            }
        });


    }



    private void InitUI() {

        mContext = AddMoney.this;
        razorpay = new Razorpay(this);
        customerName = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        customerEmail1 = SharedPrefUtil.getUserEmail(mContext, SHARED_PREF_UserEmailID, "");
        customerPhone = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");

        if(customerEmail1!=null && customerEmail1.length()>0){
            customerEmail=customerEmail1;
        } else{
            customerEmail="adviratech@gmail.com";
        }

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
        //txt_amounttobepaid = findViewById(R.id.txt_amounttobepaid);
        ll_vpa = findViewById(R.id.ll_vpa);
        rg_upi2 = findViewById(R.id.rg_upi2);
        et_vpa = findViewById(R.id.et_vpa);

        rl_upiapplogos = findViewById(R.id.rl_upiapplogos);
        rb_paylater = findViewById(R.id.rb_paylater);
        rb_emi = findViewById(R.id.rb_emi);
        rb_nocostemi = findViewById(R.id.rb_nocostemi);
        rb_cod = findViewById(R.id.rb_cod);
        rb_cod.setText("Pay On Delivery");

        txt_availablecredit = findViewById(R.id.txt_availablecredit);

        cv_emicards = findViewById(R.id.cv_emicards);
        et_cardno = findViewById(R.id.et_cardno);
        et_cardname = findViewById(R.id.et_cardname);
        spn_month = findViewById(R.id.spn_month);
        spn_year = findViewById(R.id.spn_year);
        et_cvv = findViewById(R.id.et_cvv);
        spn_emitenure = findViewById(R.id.spn_emitenure);

        rb_paylater.setVisibility(View.GONE);
        rb_cod.setVisibility(View.GONE);
        rb_nocostemi.setVisibility(View.GONE);
        rb_emi.setVisibility(View.GONE);

        /*et_dcardno.setText("4111111111111111");
        et_dcardname.setText("Test");
        et_dcvv.setText("123");
        et_cardno.setText("4111111111111111");
        et_cardname.setText("Test");
        et_cvv.setText("123");*/


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

        cv_addmoney=findViewById(R.id.cv_addmoney);
        tv_rupees=findViewById(R.id.tv_rupees);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            addmoney = extras.getString("addmoney");
            from=extras.getString("from");

            Log.e(TAG, "InitUI: ADDMONEY"+addmoney );

        }
        tv_rupees.setText("₹ "+addmoney);
        }

    private void walletPaymentInitRequest() {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        WalletOrderInitRequest walletOrderInitRequest=new WalletOrderInitRequest();
        walletOrderInitRequest.setAmount(addmoney);

        Call<WalletOrderInitResponse> call= RetrofitUrlConnection.loadJSON(token).initwallet(walletOrderInitRequest);

        call.enqueue(new Callback<WalletOrderInitResponse>() {
            @Override
            public void onResponse(Call<WalletOrderInitResponse> call, Response<WalletOrderInitResponse> response) {
                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    orderId = response.body().getOrderInit().getId();
                    rzrpayorderid=orderId;
                    receipt = response.body().getOrderInit().getReceipt();
                    rzrpaypayamount = String.valueOf(response.body().getOrderInit().getAmountDue());
                    Log.e(TAG, "onResponse:rzrpaypayamount "+rzrpaypayamount );

                    if (paymenttype.equalsIgnoreCase("Pay with Debit/Credit Card")) {

                        String name = et_dcardname.getText().toString();
                        String cardno = et_dcardno.getText().toString();
                        String cvv = et_dcvv.getText().toString();
                        String month = spn_dmonth.getSelectedItem().toString();
                        String year = spn_dyear.getSelectedItem().toString();


                        Intent intent = new Intent(AddMoney.this, WalletRazorpayPayment.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        Log.e(TAG, "onResponse: Addmoneyamount"+rzrpaypayamount+"   "+grandtotal );
                        intent.putExtra("adviraorderid", orderId);
                        intent.putExtra("adviraorderno", orderNo);
                        intent.putExtra("from", from);
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


                        Intent intent = new Intent(AddMoney.this, RazorPayEMIBank.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        intent.putExtra("from", from);

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


                        Intent intent = new Intent(AddMoney.this, RazorPayNoCostEMIBank.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        intent.putExtra("from", from);

                        startActivity(intent);
                    } else if (paymenttype.equalsIgnoreCase("Pay with Net Banking")) {


                        Intent intent = new Intent(AddMoney.this, WalletRazorpayPayment.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        intent.putExtra("from", from);
                        Log.e(TAG, "onResponse: addmoney"+from );
                        startActivity(intent);
                        Log.e(TAG, "onResponse: Addmoneyamount"+rzrpaypayamount+"   "+grandtotal );
                    } else if (paymenttype.equalsIgnoreCase("wallet")) {


                        Intent intent = new Intent(AddMoney.this, WalletRazorpayPayment.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        intent.putExtra("from", from);
                        startActivity(intent);
                    } else if (paymenttype.equalsIgnoreCase("Pay with upi")) {


                        Intent intent = new Intent(AddMoney.this, WalletRazorpayPaymentUPI.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        intent.putExtra("from", from);
                        startActivity(intent);
                    } else if (paymenttype.equalsIgnoreCase("Pay with vpa")) {

                        Intent intent = new Intent(AddMoney.this, WalletRazorpayPaymentUPI.class);
                        intent.putExtra("paymentcode", paymentcode);
                        intent.putExtra("addressID",addressID);
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
                        intent.putExtra("from", from);
                        startActivity(intent);
                    }
                }
                else {
                    Utilities.dismissDialog();
                    //singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }
                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<WalletOrderInitResponse> call, Throwable t) {

            }
        });

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


    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        AddMoney.this.finish();
    }
}