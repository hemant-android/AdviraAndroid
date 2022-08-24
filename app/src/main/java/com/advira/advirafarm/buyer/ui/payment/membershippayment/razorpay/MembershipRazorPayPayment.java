package com.advira.advirafarm.buyer.ui.payment.membershippayment.razorpay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipActivity;
import com.advira.advirafarm.buyer.ui.myaccount.MyMembershipFragment;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.payment.api.MpaymentreceivedRequest;
import com.advira.advirafarm.buyer.ui.payment.api.MpaymentreceivedResponse;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentRequest;
import com.advira.advirafarm.buyer.ui.payment.api.PGPaymentResponse;
import com.advira.advirafarm.buyer.ui.payment.membershippayment.MembershipPaymentFailed;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PayLaterRazorPayPayment;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PaymentFailure;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PaymentSuccess;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNTB2B;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNTB2C;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_CancelTime;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_EndDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartTime;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PaymentCheck;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserRegno;

public class MembershipRazorPayPayment extends Activity implements PaymentResultWithDataListener {

    private static final String TAG = MembershipRazorPayPayment.class.getSimpleName();
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
    String adviragrandtotal = "";
    String adviraorderid = "";
    String adviraorderno = "";
    String paymentref = "";
    public String paidamount = "0";
    public String dueamount = "0";
    ArrayList<String> banksCodesList = new ArrayList<>();
    AlertDialog.Builder builder;
    private Razorpay razorpay;
    private WebView webview;
    private JSONObject payload;
    private Context mContext;
    private RelativeLayout rl_back;
    private FrameLayout frameLayout;
    private ListView listView;
    private ArrayList<String> banksList = new ArrayList<>();
    private ArrayAdapter banksListAdapter;
    String membership_price = "";
    String membership_duration = "";
    String userId = "";
    String other_pay_details = "";
    String startTime,cancelTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_payment);
        WebView.setWebContentsDebuggingEnabled(false);

        mContext = MembershipRazorPayPayment.this;
        userId=SharedPrefUtil.getUserRegno(mContext,SHARED_PREF_UserRegno,"");
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
                membership_price=extras.getString("adviragrandtotal");
                membership_duration = extras.getString("membership_duration");
                other_pay_details = extras.getString("other_pay_details");

            }
        } catch (Exception ex) {

        }

        Log.e(TAG, "onCreate: MRPP"+userId );


        frameLayout = (FrameLayout) findViewById(R.id.frame);
        webview = findViewById(R.id.payment_webview);

        /*LayoutInflater.from(PayLaterRazorPayPayment.this).inflate(R.layout.fragment_method_netbanking_wallet_list,
                frameLayout, true);*/
        banksListAdapter = new ArrayAdapter<String>(this, R.layout.text_view_list_banks_wallet, banksList);


        initRazorpay();
        createWebView();

        //SubmitCardDetails();

        if (paymentcode.equalsIgnoreCase("card")) {
            frameLayout.setVisibility(View.GONE);

            SubmitCardDetails();

        } else if (paymentcode.contains("emi")) {
            frameLayout.setVisibility(View.GONE);

            paymentcode = paymentcode.replace("emi", "");
            SubmitEMIDetails();

        } else if (paymentcode.equalsIgnoreCase("netbanking")) {
            frameLayout.setVisibility(View.VISIBLE);
        } else if (name.equalsIgnoreCase("wallet")) {
            frameLayout.setVisibility(View.GONE);
            SubmitWalletDetails();

        } else if (paymentcode.equalsIgnoreCase("upi")) {
            frameLayout.setVisibility(View.GONE);
            submitUpiRequest();
        }


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
                                MembershipRazorPayPayment.this.finish();

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


    }

    private void initRazorpay() {
        razorpay = new Razorpay(this);


        rl_back = findViewById(R.id.rl_back);


        razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
            @Override
            public void onPaymentMethodsReceived(String result) {

                /**
                 * This returns JSON data
                 * The structure of this data can be seen at the following link:
                 * https://api.razorpay.com/v1/methods?key_id=rzp_test_1DP5mmOlF5G5ag
                 *
                 */
                //Log.d("Result", "" + result);
                inflateLists(result);
            }

            @Override
            public void onError(String error) {
                Log.d("Get Payment error", error);
            }
        });


    }

    private void inflateLists(String result) {
        try {
            JSONObject paymentMethods = new JSONObject(result);
            JSONObject banksListJSON = paymentMethods.getJSONObject("netbanking");

            Iterator<String> itr1 = banksListJSON.keys();
            while (itr1.hasNext()) {
                String key = itr1.next();
                banksCodesList.add(key);
                try {
                    banksList.add(banksListJSON.getString(key));
                } catch (JSONException e) {
                    //Log.d("Reading Banks List", "" + e.getMessage());
                }
            }


            if (banksListAdapter != null) {
                banksListAdapter.notifyDataSetChanged();
            }
            netbankinglist();

        } catch (Exception e) {
            Log.e("Parsing Result", "" + e.getMessage());
        }
    }


    private void netbankinglist() {
        LayoutInflater.from(MembershipRazorPayPayment.this).inflate(R.layout.fragment_method_netbanking_wallet_list, frameLayout, true);
        listView = findViewById(R.id.method_available_options_list);
        listView.setAdapter(banksListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                submitNetbankingDetails(banksCodesList.get(position));
            }
        });
    }

    public void submitNetbankingDetails(String bankName) {

        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", grandtotal);
            payload.put("contact", customerPhone);
            payload.put("email", customerEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            payload.put("method", "netbanking");
            payload.put("bank", bankName);
            payload.put("order_id", order_id);
            sendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitUpiRequest() {
        razorpay.isValidVpa(name, new ValidateVpaCallback() {
            @Override
            public void onResponse(boolean b) {
                if (b) {

                    String vpa = name;

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
                        payload.put("vpa", vpa);

                        sendRequest();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Singleton.getInstance().showErrorLongToast(MembershipRazorPayPayment.this, "VPA is Not valid");

                    // Toast.makeText(PayLaterRazorPayPayment.this, "VPA is Not valid", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure() {
                Singleton.getInstance().showErrorLongToast(MembershipRazorPayPayment.this, "Error in validating");

                //  Toast.makeText(PayLaterRazorPayPayment.this, "Error in validating", Toast.LENGTH_LONG).show();
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


    private void SubmitEMIDetails() {


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
            payload.put("emi_duration", paymentcode);
            payload.put("card[name]", name);
            payload.put("card[number]", cardNumber);
            payload.put("card[expiry_month]", month);
            payload.put("card[expiry_year]", year);
            payload.put("card[cvv]", cvv);
            sendRequest();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void SubmitCardDetails() {


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
            payload.put("method", "card");
            payload.put("card[name]", name);
            payload.put("card[number]", cardNumber);
            payload.put("card[expiry_month]", month);
            payload.put("card[expiry_year]", year);
            payload.put("card[cvv]", cvv);
            sendRequest();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SubmitWalletDetails() {


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
            payload.put("method", "wallet");
            payload.put("wallet", paymentcode);
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
                    razorpay.submit(payload, MembershipRazorPayPayment.this);
                } catch (Exception e) {
                    Log.e("com.example", "Exception: ", e);
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.d("com.example", "Validation failed: " + error.get("field") + " " + error.get("description"));
                //Toast.makeText(PayLaterRazorPayPayment.this, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
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
    public void onPaymentSuccess(String PayLaterRazorPayPaymentId, PaymentData paymentData) {


        //Prints all extras. Replace with app logic.
        if (paymentData != null) {


            try {
                Gson gson = new Gson();
                String jsondata = gson.toJson(paymentData.getData()).toString();
                Log.d(TAG, "API Response razorpay: " + jsondata);
                paymentref = paymentData.getPaymentId();
            } catch (Exception ex) {

            }
        }


        webview.setVisibility(View.GONE);
        //Singleton.getInstance().showShortToast(PayLaterRazorPayPayment.this, "Payment Successful");
        //Toast.makeText(PayLaterRazorPayPayment.this, "Payment Successful ", Toast.LENGTH_SHORT).show();
        PaymentDetails("completed", "");

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
        //Toast.makeText(PayLaterRazorPayPayment.this, "Error " + Integer.toString(errorCode) + ": " + errorDescription, Toast.LENGTH_SHORT).show();
        //Log.d("com.example", "onError: " + Integer.toString(errorCode) + ": " + errorDescription);
        //Toast.makeText(PayLaterRazorPayPayment.this, "Payment Failed", Toast.LENGTH_SHORT).show();


        String errormessage = "";

        try {

            JSONObject obj = new JSONObject(errorDescription);
            JSONObject obj1 = new JSONObject(obj.getString("error"));
            errormessage = obj1.getString("description");
            Log.d("description ", errormessage);

            //Singleton.getInstance().showShortToast(mContext, errormessage);//remove toast


        } catch (Throwable tx) {


        }


        if (errormessage.contains("number is invalid")) {

            SharedPrefUtil.setPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "retain");
            MembershipRazorPayPayment.this.finish();
            //Singleton.getInstance().showErrorLongToast(mContext, errormessage+"--14");

        } else {
            PaymentDetails("FAILED", errormessage);
        }


    }


    private void PaymentDetails(String paystatus, String errormessage) {


        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        String paytype = "";
        String paymode = "";


        if (paymentcode.equalsIgnoreCase("card")) {
            paytype = "RazorPay";
            paymode = "Debit-Card";
        } else if (paymentcode.equalsIgnoreCase("netbanking")) {
            paytype = "RazorPay";
            paymode = "Net-Banking";
        } else if (paymentcode.equalsIgnoreCase("upi")) {
            paytype = "RazorPay";
            paymode = "UPI";
        } else if (name.equalsIgnoreCase("wallet")) {
            paytype = "RazorPay";
            paymode = "Wallet";
        }

        if (paystatus.equalsIgnoreCase("SUCCESS")) {
            paidamount = grandtotal.substring(0, grandtotal.length() - 2);
            dueamount = "0";
        } else {
            dueamount = grandtotal.substring(0, grandtotal.length() - 2);
            paidamount = "0";
        }


        MpaymentreceivedRequest mpaymentreceivedRequest=new MpaymentreceivedRequest();
        mpaymentreceivedRequest.setMemberId(adviraorderid);
        mpaymentreceivedRequest.setMembershipDuration(membership_duration);
        mpaymentreceivedRequest.setMembershipPrice(membership_price);
        mpaymentreceivedRequest.setOtherPayDetails(other_pay_details);
        mpaymentreceivedRequest.setPayThrough(paymode);
        mpaymentreceivedRequest.setPaymentId(paymentref);
        mpaymentreceivedRequest.setUserId(userId);
        mpaymentreceivedRequest.setPaymentStatus(paystatus);
        Log.e(TAG, "PaymentDetails: membershipRPP"+paystatus );

        try{
            Call<MpaymentreceivedResponse> call=RetrofitUrlConnection.loadJSON(token).memberpaymentreceived(mpaymentreceivedRequest);

            call.enqueue(new Callback<MpaymentreceivedResponse>() {
                @Override
                public void onResponse(Call<MpaymentreceivedResponse> call, Response<MpaymentreceivedResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        if (paystatus.equalsIgnoreCase("completed")) {
                            Log.e(TAG, "PaymentDetails: membershipRPP_insidesuccess"+paystatus );
                            String membershipName=response.body().getMembershipDetails().getMembershipDetails();
                            String mem_expirydate=response.body().getMembershipDetails().getMembershipExpDate();
                            SharedPrefUtil.setMembership(mContext,SHARED_PREF_MemberShip,membershipName);
                            SharedPrefUtil.setMembershipEndDate(mContext,SHARED_PREF_MemberShip_EndDate,mem_expirydate);

                            String mem_startdate=response.body().getMembershipDetails().getMembershipStartDate();
                            SharedPrefUtil.setMembershipStartDate(mContext,SHARED_PREF_MemberShip_StartDate,mem_startdate);
                            Calendar calendar = Calendar.getInstance();

                            // get a date to represent "today"
                            Date today = calendar.getTime();
                            startTime= DateFormat.format("dd-MM-yyyy HH:mm:ss", today).toString();
                            calendar.add(Calendar.HOUR_OF_DAY, 1);

                            // now get "tomorrow"
                            /*Date tomorrow = calendar.getTime();
                            cancelTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", tomorrow).toString();*/
                            Log.e(TAG, "Init: memberShip_101"+"--"+startTime );

                            SharedPrefUtil.setmembershipStartTime(mContext,SHARED_PREF_MemberShip_StartTime,startTime);
                            //SharedPrefUtil.setmembershipCancelTime(mContext,SHARED_PREF_MemberShip_CancelTime,cancelTime);

                            //singleton.getInstance().showShortToast(mContext, "Order Placed Successfully");
                            Intent mainIntent = new Intent(mContext, MembershipActivity.class);
                            //mainIntent.putExtra("orderno", adviraorderno);
                            //mainIntent.putExtra("orderid", adviraorderid);
                            startActivity(mainIntent);
                            finish();

                        }

                        if (paystatus.equalsIgnoreCase("FAILED")) {

                            String membershipDetails="ADVIRA Elite Green Membership";
                            //singleton.getInstance().showShortToast(mContext, "Payment Error");
                            Intent i = new Intent(mContext, MembershipPaymentFailed.class);
                            // mainIntent.putExtra("orderno", adviraorderno);
                            i.putExtra("memberid",adviraorderid);
                            i.putExtra("membershipDetails",membershipDetails);
                            i.putExtra("price",membership_price);
                            i.putExtra("duration",membership_duration);
                            i.putExtra("buttonLabel",other_pay_details);
                            i.putExtra("userId",userId);
                            startActivity(i);
                            finish();
                        }


                    } else {
                        Utilities.dismissDialog();
                        //singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<MpaymentreceivedResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*PGPaymentRequest pgPaymentRequest = new PGPaymentRequest();
        pgPaymentRequest.setOrderId(adviraorderid);
        pgPaymentRequest.setPaymentType(paytype);
        pgPaymentRequest.setPaymentMode(paymode);
        pgPaymentRequest.setPaymentAmount(paidamount);
        pgPaymentRequest.setPaymentDueAmount(dueamount);
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
                        Log.e(TAG, "PaymentDetails: api response" + jsondata);
                        Gson gson = new Gson();
                        String jsondata = gson.toJson(response.body());
                        Log.e(TAG, "onResponse: addpayment" + jsondata);

                        Utilities.dismissDialog();

                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
                        SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, "");//remove 0
                        SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, "");//remove 0

                        if (paystatus.equalsIgnoreCase("SUCCESS")) {


                            //Singleton.getInstance().showShortToast(mContext, "Order Placed Successfully");
                            Intent mainIntent = new Intent(mContext, PaymentSuccess.class);
                            mainIntent.putExtra("orderno", adviraorderno);
                            mainIntent.putExtra("orderid", adviraorderid);
                            MembershipRazorPayPayment.this.startActivity(mainIntent);
                            MembershipRazorPayPayment.this.finish();
                        }

                        if (paystatus.equalsIgnoreCase("FAILED")) {

                            // Singleton.getInstance().showShortToast(mContext, "Payment Error");
                            //Intent mainIntent = new Intent(mContext, OrderPaymentFailedActivity.class);
                            Intent mainIntent = new Intent(mContext, PaymentFailure.class);
                            mainIntent.putExtra("orderno", adviraorderno);
                            mainIntent.putExtra("orderid", adviraorderid);
                            mainIntent.putExtra("grandtotal", adviragrandtotal);
                            MembershipRazorPayPayment.this.startActivity(mainIntent);
                            MembershipRazorPayPayment.this.finish();

                            if (errormessage.length() > 1) {
                                //Singleton.getInstance().showErrorLongToast(mContext, errormessage+"--15");
                            } else {
                                //Singleton.getInstance().showShortToast(mContext, "Payment Failed");//remove toast
                            }

                        }


                    } else {
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
        }*/

    }

}