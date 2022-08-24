package com.advira.advirafarm.buyer.ui.payment.razorpay;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.order.OrderPlacedActivity;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentRequest;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentResponse;
import com.advira.advirafarm.buyer.ui.payment.paymentfailed.OrderPaymentFailedActivity;
import com.advira.advirafarm.buyer.ui.payment.razorpay.adapter.EMIListAdapter;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.BankEMIRequest;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.BankEMIResponse;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.EmiDatum;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Razorpay;

import org.json.JSONObject;

import java.util.ArrayList;
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

public class RazorPayEMIList extends Activity implements PaymentResultWithDataListener {

    private static final String TAG = RazorPayEMIList.class.getSimpleName();
    public static TextView tv_tenure;
    String name = "";
    String cardNumber = "";
    String cvv = "";
    String month = "";
    String year = "";
    String paymentcode = "";
    String order_id = "";
    String bankcode = "";
    String customerName = "";
    String customerPhone = "";
    String customerEmail = "";
    String grandtotal = "";
    String adviragrandtotal = "";
    String adviraorderid = "";
    String adviraorderno = "";
    String paymentref = "";
    ArrayList<String> banksCodesList = new ArrayList<>();
    AlertDialog.Builder builder;
    EMIListAdapter emiListAdapter;
    private Razorpay razorpay;
    private WebView webview;
    private JSONObject payload;
    private Context mContext;
    private RelativeLayout rl_back;
    private List<EmiDatum> emiDatumList;
    private RecyclerView recyclerView;
    private CardView cv_emicards;
    private EditText et_cardno;
    private EditText et_cardname;
    private Spinner spn_month;
    private Spinner spn_year;
    private Spinner spn_emitenure;
    private EditText et_cvv;
    private Button btn_proceedtopay;
    private NestedScrollView ns_emi;
    private TextView tv_terms;
    private TextView tv_newadd_header2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_list);
        WebView.setWebContentsDebuggingEnabled(false);

        mContext = RazorPayEMIList.this;
        try {

            Bundle extras = getIntent().getExtras();

            if (extras != null) {

                paymentcode = extras.getString("paymentcode");
                order_id = extras.getString("order_id");
                customerName = extras.getString("customerName");
                customerEmail = extras.getString("customerEmail");
                customerPhone = extras.getString("customerPhone");
                adviraorderid = extras.getString("adviraorderid");
                adviraorderno = extras.getString("adviraorderno");
                grandtotal = extras.getString("grandtotal");
                adviragrandtotal = extras.getString("adviragrandtotal");
                bankcode = extras.getString("bankcode");

            }
        } catch (Exception ex) {

        }


        webview = findViewById(R.id.payment_webview);


        initRazorpay();
        createWebView();

        if (paymentcode.contains("emi")) {

            paymentcode = paymentcode.replace("emi", "");
            //  SubmitEMIDetails();

        }
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
        });


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Go Back !! ");

                //Setting message manually and performing action on button click
                builder.setMessage("Going back will abort the payment process. Are you sure ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPrefUtil.setPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "retain");
                                RazorPayEMIList.this.finish();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


            }
        });


        btn_proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_cardno.getText().toString().trim().length() < 14) {

                    et_cardno.setError("Card No is required!");

                } else if (et_cardname.getText().toString().trim().isEmpty()) {
                    et_cardname.setError("Name is required!");

                } else if (et_cvv.getText().toString().trim().length() < 3) {
                    et_cvv.setError("CVV is required");

                } else {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        webview.setVisibility(View.VISIBLE);
                        SubmitEMIDetails();

                    }
                }

            }
        });

    }


    private void initRazorpay() {
        razorpay = new Razorpay(this);

        tv_tenure = findViewById(R.id.tv_tenure);
        tv_tenure.setText("3");
        rl_back = findViewById(R.id.rl_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        cv_emicards = findViewById(R.id.cv_emicards);
        et_cardno = findViewById(R.id.et_cardno);
        et_cardname = findViewById(R.id.et_cardname);
        spn_month = findViewById(R.id.spn_month);
        spn_year = findViewById(R.id.spn_year);
        et_cvv = findViewById(R.id.et_cvv);
        spn_emitenure = findViewById(R.id.spn_emitenure);
        ns_emi = findViewById(R.id.ns_emi);
        btn_proceedtopay = findViewById(R.id.btn_proceedtopay);
        tv_newadd_header2=findViewById(R.id.tv_newadd_header2);
        tv_newadd_header2.setText("EMI");


        tv_terms = findViewById(R.id.tv_terms);
        String myString = "Pay in easy monthly installments from any of the options below Terms & Conditions apply";
        int i1 = myString.indexOf("Terms");
        int i2 = myString.lastIndexOf("s");

        tv_terms.setMovementMethod(LinkMovementMethod.getInstance());
        tv_terms.setText(myString, TextView.BufferType.SPANNABLE);

        Spannable mySpannable = (Spannable) tv_terms.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                Intent i = new Intent();
                i.setClass(RazorPayEMIList.this, WebViewActivity.class);
                i.putExtra("header", "EMI Terms & Conditions");
                i.putExtra("url", "https://www.advira.in/emi-terms-conditions-app.php");
                startActivity(i);

            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_terms.setLinkTextColor(getResources().getColor(R.color.colorThemeDark));



        String paidamount = grandtotal.substring(0, grandtotal.length() - 2);

        btn_proceedtopay.setText("PROCEED TO PAY NOW ₹" + paidamount);


        // et_cardno.setText("4111111111111111");
        //et_cardno.setText("5104015555555558");
        //et_cardno.setText("4854980604708430");
        //et_cardname.setText("Test");
        //et_cvv.setText("123");


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

        spn_month.setSelection(1);
        spn_year.setSelection(1);

        BankEmiListRequest();

    }


    public void BankEmiListRequest() {

        Utilities.showLoading(mContext);

        String paidamount = grandtotal.substring(0, grandtotal.length() - 2);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        BankEMIRequest bankEMIRequest = new BankEMIRequest();
        bankEMIRequest.setBankCode(bankcode);
        bankEMIRequest.setOrderAmount(paidamount);


        try {

            Call<BankEMIResponse> call = RetrofitUrlConnection.loadJSON(token).bankemi(bankEMIRequest);

            call.enqueue(new Callback<BankEMIResponse>() {
                @Override
                public void onResponse(Call<BankEMIResponse> call, Response<BankEMIResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        emiDatumList = new ArrayList<>();
                        emiListAdapter = new EMIListAdapter(mContext, emiDatumList);

                        List<EmiDatum> mListData = response.body().getEmiData();


                        if (mListData != null && mListData.size() > 0) {

                            mListData.get(0).setEmiMinAmount(1);

                            emiDatumList.addAll(mListData);

                        }

                        emiListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(emiListAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        Utilities.dismissDialog();


                    } else {
                        //singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                    }

                }

                @Override
                public void onFailure(Call<BankEMIResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void createWebView() {
        /**
         * You need to pass the webview to Razorpay
         */
        razorpay.setWebView(webview);

        /**
         * Override the RazorpayWebViewClient for your custom hooks
         */

    }


    private void SubmitEMIDetails() {

        String name = et_cardname.getText().toString();
        String cardNumber = et_cardno.getText().toString();
        String cvv = et_cvv.getText().toString();
        String month = spn_month.getSelectedItem().toString();
        String year = spn_year.getSelectedItem().toString();
        String tenure = tv_tenure.getText().toString();


        year = year.substring(2, 4);


        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", grandtotal);
            payload.put("contact", customerPhone);
            payload.put("email", customerEmail);
            payload.put("order_id", order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            payload.put("method", "emi");
            payload.put("emi_duration", tenure);
            payload.put("card[name]", name);
            payload.put("card[number]", cardNumber);
            payload.put("card[expiry_month]", month);
            payload.put("card[expiry_year]", year);
            payload.put("card[cvv]", cvv);

            ns_emi.setVisibility(View.GONE);
            createWebView();
            sendRequest();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendRequest() {
        razorpay.validateFields(payload, new Razorpay.ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    webview.setVisibility(View.VISIBLE);
                    razorpay.submit(payload, RazorPayEMIList.this);
                } catch (Exception e) {
                    Log.e("com.example", "Exception: ", e);
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.d("com.example", "Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(RazorPayEMIList.this, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        razorpay.onBackPressed();
        super.onBackPressed();
        webview.setVisibility(View.GONE);
    }

    /* callback for permission requested from android */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (razorpay != null) {
            razorpay.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {


        //Prints all extras. Replace with app logic.
        if (paymentData != null) {


            try {
                Gson gson = new Gson();
                String jsondata = gson.toJson(paymentData.getData()).toString();
                Log.d(TAG, "API Response razorpay: " + jsondata);
                paymentref = jsondata;
            } catch (Exception ex) {

            }
        }


        webview.setVisibility(View.GONE);
        //Singleton.getInstance().showShortToast(RazorPayEMIList.this, "Payment Successful");//remove toast
        //Toast.makeText(RazorPayPayment.this, "Payment Successful ", Toast.LENGTH_SHORT).show();
        PaymentDetails("SUCCESS", "");

        // finish();
    }

    @Override
    public void onPaymentError(int errorCode, String errorDescription, PaymentData paymentData) {

        if (paymentData != null) {
            Gson gson = new Gson();
            String jsondata = gson.toJson(paymentData).toString();
            Log.d(TAG, "API Response razorpay: " + jsondata);
            paymentref = errorDescription;
        }

        webview.setVisibility(View.GONE);
        //Toast.makeText(RazorPayPayment.this, "Error " + Integer.toString(errorCode) + ": " + errorDescription, Toast.LENGTH_SHORT).show();
        //Log.d("com.example", "onError: " + Integer.toString(errorCode) + ": " + errorDescription);
        //Toast.makeText(RazorPayPayment.this, "Payment Failed", Toast.LENGTH_SHORT).show();


        String errormessage = "";

        try {

            JSONObject obj = new JSONObject(errorDescription);
            JSONObject obj1 = new JSONObject(obj.getString("error"));
            errormessage = obj1.getString("description");
            Log.d("description ", errormessage);

            //singleton.getInstance().showShortToast(mContext, errormessage+"--16");


        } catch (Throwable tx) {


        }


        if (errormessage.contains("number is invalid")) {

            SharedPrefUtil.setPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "retain");
            RazorPayEMIList.this.finish();
            //singleton.getInstance().showErrorLongToast(mContext, errormessage+"--17");

        } else {
            PaymentDetails("FAILED", errormessage);
        }


    }


    private void PaymentDetails(String paystatus, String errormessage) {


        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String paytype = "";
        String paymode = "";


        paytype = "EMI";
        paymode = "EMI";


        String paidamount = grandtotal.substring(0, grandtotal.length() - 2);

        PGPaymentRequest pgPaymentRequest = new PGPaymentRequest();
        pgPaymentRequest.setOrderId(adviraorderid);
        pgPaymentRequest.setPaymentType(paytype);
        pgPaymentRequest.setPaymentMode(paymode);
        pgPaymentRequest.setPaymentAmount(paidamount);
        pgPaymentRequest.setPaymentDueAmount("0");
        pgPaymentRequest.setPaymentRef(paymentref);
        pgPaymentRequest.setPaymentStatus(paystatus);

        Gson gson = new Gson();
        String jsondata = gson.toJson(pgPaymentRequest).toString();

        try {

            Call<PGPaymentResponse> call = RetrofitUrlConnection.loadJSON(token).addpgpaymentdetails(pgPaymentRequest);

            call.enqueue(new Callback<PGPaymentResponse>() {
                @Override
                public void onResponse(Call<PGPaymentResponse> call, Response<PGPaymentResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                        SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");
                        SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");

                        try {
                            MainActivityNav.text.setText("");
                            MainActivityNav.text.setText("");
                        } catch (Exception ex) {

                        }

                        try {
                            ProductDetailsActivity.text.setText("");
                        } catch (Exception ex) {

                        }

                        try {
                            ProductDetailsActivityB2B.text.setText("");
                        } catch (Exception ex) {

                        }

                        if (paystatus.equalsIgnoreCase("SUCCESS")) {

                            //Singleton.getInstance().showShortToast(mContext, "Order Placed Successfully");
                            Intent mainIntent = new Intent(mContext, OrderPlacedActivity.class);
                            mainIntent.putExtra("orderno", adviraorderno);
                            mainIntent.putExtra("orderid", adviraorderid);
                            RazorPayEMIList.this.startActivity(mainIntent);
                            RazorPayEMIList.this.finish();
                        }

                        if (paystatus.equalsIgnoreCase("FAILED")) {

                            // Singleton.getInstance().showShortToast(mContext, "Payment Error");
                            Intent mainIntent = new Intent(mContext, OrderPaymentFailedActivity.class);
                            mainIntent.putExtra("orderno", adviraorderno);
                            mainIntent.putExtra("orderid", adviraorderid);
                            mainIntent.putExtra("grandtotal", adviragrandtotal);
                            RazorPayEMIList.this.startActivity(mainIntent);
                            RazorPayEMIList.this.finish();

                            if (errormessage.length() > 1) {
                                //singleton.getInstance().showErrorLongToast(mContext, errormessage+"--18");
                            } else {
                                //Singleton.getInstance().showShortToast(mContext, "Payment Failed");
                            }

                        }


                    } else {
                        Utilities.dismissDialog();
                        //singleton.getInstance().showShortToast(mContext, response.body().getMessage());
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


}
