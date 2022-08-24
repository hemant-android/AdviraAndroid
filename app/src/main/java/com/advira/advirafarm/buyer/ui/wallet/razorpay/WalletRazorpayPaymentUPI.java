package com.advira.advirafarm.buyer.ui.wallet.razorpay;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipActivity;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.payment.api.MpaymentreceivedRequest;
import com.advira.advirafarm.buyer.ui.payment.api.MpaymentreceivedResponse;
import com.advira.advirafarm.buyer.ui.payment.membershippayment.MembershipPaymentFailed;
import com.advira.advirafarm.buyer.ui.payment.membershippayment.razorpay.MembershipRazorPayPaymentUPI;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.ui.wallet.WalletPaymentFailure;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletPaymentRequest;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletPaymentResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Razorpay;
import com.razorpay.ValidateVpaCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_EndDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartTime;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PaymentCheck;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

public class WalletRazorpayPaymentUPI extends Activity implements PaymentResultWithDataListener {

    private static final String TAG = WalletRazorpayPaymentUPI.class.getSimpleName();
    String name = "";
    String cardNumber = "";
    String cvv = "";
    String month = "";
    String year = "";
    String paymentcode = "";
    String order_id = "";
    String receipt = "";
    String customerName = "";
    String customerPhone = "";
    String customerEmail = "";
    String grandtotal = "";
    String adviraorderid = "";
    String adviraorderno = "";
    String adviragrandtotal = "";
    String paymentref = "";
    String upiorvpa = "";
    String upiid = "";
    Button btn_chooseapptopay;
    Button btn_vpapay;
    EditText et_vpa;
    AlertDialog.Builder builder;
    private Razorpay razorpay;
    private WebView webview;
    private JSONObject payload;
    private Context mContext;
    private RelativeLayout rl_back;
    String membership_price = "";
    String membership_duration = "";
    String userId = "";
    String other_pay_details = "";
    String startTime;
    String addressID="";
    String from="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_paymentupi);
        WebView.setWebContentsDebuggingEnabled(false);

        mContext = WalletRazorpayPaymentUPI.this;


        try {

            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                name = extras.getString("name");
                cardNumber = extras.getString("cardno");
                cvv = extras.getString("cvv");
                month = extras.getString("month");
                year = extras.getString("year");
                paymentcode = extras.getString("paymentcode");
                order_id = extras.getString("order_id");
                receipt = extras.getString("receipt");
                customerName = extras.getString("customerName");
                customerEmail = extras.getString("customerEmail");
                customerPhone = extras.getString("customerPhone");
                grandtotal = extras.getString("grandtotal");
                adviraorderid = extras.getString("adviraorderid");
                adviraorderno = extras.getString("adviraorderno");
                adviragrandtotal = extras.getString("adviragrandtotal");
                upiorvpa = extras.getString("upiorvpa");
                upiid = extras.getString("upiid");
                membership_price=extras.getString("adviragrandtotal");
                userId = extras.getString("userId");
                membership_duration = extras.getString("membership_duration");
                //other_pay_details = extras.getString("other_pay_details");
                addressID=extras.getString("addressID");
                from=extras.getString("from");


            }
        } catch (Exception ex) {

        }


        webview = findViewById(R.id.payment_webview);

        initRazorpay();
        createWebView();

        String paidamount = grandtotal.substring(0, grandtotal.length() - 2);


        btn_chooseapptopay.setText("Choose a UPI App to Pay : ₹" + paidamount);


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Go Back !!");

                //Setting message manually and performing action on button click
                builder.setMessage("Going back will abort the payment process. Are you sure ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();
                                SharedPrefUtil.setPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "retain");

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


        btn_chooseapptopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitUPIIntentDetails();
            }
        });


        btn_vpapay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_vpa.getText().toString().trim().length() < 4) {

                    et_vpa.requestFocus();
                    et_vpa.setError("UPI ID is required!");

                } else {


                    et_vpa.setError(null);
                    submitVPARequest();
                }
            }
        });


        if (upiorvpa.equalsIgnoreCase("upi")) {
            SubmitUPIIntentDetails();
        } else if (upiorvpa.equalsIgnoreCase("vpa")) {
            et_vpa.setText(upiid);
            submitVPARequest();
        }

    }

    private void initRazorpay() {

        razorpay = new Razorpay(this);
        rl_back = findViewById(R.id.rl_back);
        btn_chooseapptopay = findViewById(R.id.btn_chooseapptopay);
        btn_vpapay = findViewById(R.id.btn_vpapay);
        et_vpa = findViewById(R.id.et_vpa);


    }


    public void submitVPARequest() {
        String vpaname = et_vpa.getText().toString().trim();

        razorpay.isValidVpa(vpaname, new ValidateVpaCallback() {
            @Override
            public void onResponse(boolean b) {
                if (b) {


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
                        payload.put("method", "upi");
                        payload.put("vpa", vpaname);

                        sendRequest();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(WalletRazorpayPaymentUPI.this, "UPI ID is Not valid", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(WalletRazorpayPaymentUPI.this, "Invalid VPA/UPI ID", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }

    private void createWebView() {
        /**
         * You need to pass the webview to Razorpay
         */
        razorpay.setWebView(webview);

        /**
         * Override the RazorpayWebViewClient for your custom hooks
         */
        /*razorpay.setWebviewClient(new RazorpayWebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "Custom client onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "Custom client onPageFinished");
            }
        });*/
    }


    public void SubmitUPIIntentDetails() {


        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("order_id", order_id);
            payload.put("amount", grandtotal);
            payload.put("contact", customerPhone);
            payload.put("email", customerEmail);
            payload.put("display_logo", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray jArray = new JSONArray();
            jArray.put("in.org.npci.upiapp");
            jArray.put("com.snapwork.hdfc");
            payload.put("description", "Credits towards ADVIRAHEAL");
            payload.put("method", "upi");
            payload.put("_[flow]", "intent");
            payload.put("preferred_apps_order", jArray);
            payload.put("other_apps_order", jArray);

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
                    razorpay.submit(payload, WalletRazorpayPaymentUPI.this);
                } catch (Exception e) {
                    Log.e("com.example", "Exception: ", e);
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.d("com.example", "Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(WalletRazorpayPaymentUPI.this, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, "API Response razorpay:124 " + jsondata);

                paymentref = paymentData.getPaymentId();
                other_pay_details=jsondata;

            } catch (Exception ex) {


            }
        }


        webview.setVisibility(View.GONE);

        //singleton.getInstance().showShortToast(RazorPayPaymentUPI.this, "Payment Successful");
        // Toast.makeText(RazorPayPaymentUPI.this, "Payment Successful ", Toast.LENGTH_SHORT).show();
        PaymentDetails("SUCCESS");

        // finish();
    }

    @Override
    public void onPaymentError(int errorCode, String errorDescription, PaymentData paymentData) {

        if (paymentData != null) {
            Gson gson = new Gson();
            String jsondata = gson.toJson(paymentData).toString();
            Log.d(TAG, "API Response : " + jsondata);
            paymentref = errorDescription;
            other_pay_details=errorDescription;
        }

        webview.setVisibility(View.GONE);
        //Toast.makeText(RazorPayPaymentUPI.this, "Error " + Integer.toString(errorCode) + ": " + errorDescription, Toast.LENGTH_LONG).show();
        // Log.d("com.example", "onError: " + Integer.toString(errorCode) + ": " + errorDescription);
        //Toast.makeText(RazorPayPaymentUPI.this, "Payment Failed", Toast.LENGTH_SHORT).show();
        PaymentDetails("FAILED");


        // finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (razorpay != null) {
            razorpay.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void PaymentDetails(String paystatus) {


        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String paytype = "";
        String paymode = "";

        if (paymentcode.equalsIgnoreCase("upi")) {
            paytype = "RazorPay";
            paymode = "UPI";
        }

        if (paymentcode.equalsIgnoreCase("vpa")) {
            paytype = "RazorPay";
            paymode = "UPI";
        }


        String paidamount = grandtotal.substring(0, grandtotal.length() - 2);

        WalletPaymentRequest walletPaymentRequest=new WalletPaymentRequest();
        walletPaymentRequest.setOrderInitId(adviraorderid);
        walletPaymentRequest.setPaymentStatus(paystatus);
        walletPaymentRequest.setPaymentRef(other_pay_details);
        walletPaymentRequest.setOrderPayThrough(paymode);
        walletPaymentRequest.setRazpayPaymentId(paymentref);

        try{
            Call<WalletPaymentResponse> call=RetrofitUrlConnection.loadJSON(token).addwalletpayment(walletPaymentRequest);

            call.enqueue(new Callback<WalletPaymentResponse>() {
                @Override
                public void onResponse(Call<WalletPaymentResponse> call, Response<WalletPaymentResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        String amount=response.body().getWalletAmount();
                        if (paystatus.equalsIgnoreCase("SUCCESS")) {
                            if (from.equalsIgnoreCase("wallet")) {
                                Intent mainIntent = new Intent(mContext, WalletActivity.class);
                                mainIntent.putExtra("addressID", addressID);
                                mainIntent.putExtra("amount",amount);
                                //mainIntent.putExtra("orderid", adviraorderid);
                                startActivity(mainIntent);
                                finish();
                            }else {
                                Intent mainIntent = new Intent(mContext, MySubscription.class);
                                mainIntent.putExtra("addressID", addressID);
                                mainIntent.putExtra("amount",amount);
                                //mainIntent.putExtra("orderid", adviraorderid);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                        if (paystatus.equalsIgnoreCase("FAILED")) {


                            //singleton.getInstance().showShortToast(mContext, "Payment Error");
                            Intent i = new Intent(mContext, WalletPaymentFailure.class);
                            // mainIntent.putExtra("orderno", adviraorderno);
                            i.putExtra("addressID",addressID);
                            i.putExtra("amount",amount);
                            i.putExtra("from",from);
                            startActivity(i);
                            finish();
                        }

                    }
                }

                @Override
                public void onFailure(Call<WalletPaymentResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
