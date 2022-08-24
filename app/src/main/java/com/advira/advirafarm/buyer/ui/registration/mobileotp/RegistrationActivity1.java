package com.advira.advirafarm.buyer.ui.registration.mobileotp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RegistrationActivity1 extends Activity implements IConsts {

    private Context mContext;
    private EditText et_mobileno;
    private RelativeLayout rl_back;
    private Button btn_continue;
    private TextView tv_terms;
    private CheckBox chk_terms;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        initUI();


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(RegistrationActivity1.this, OneTapLogin.class);
                startActivity(i);


            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_mobileno.getText().toString().length() < 10) {
                    et_mobileno.requestFocus();
                    et_mobileno.setError("Invalid mobile no!");

                } else if (!chk_terms.isChecked()) {
                    chk_terms.setError("Please agree");
                    Singleton.getInstance().showLongToast(mContext, "Please agree to our Terms & Conditions and Privacy Policy");
                }else {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {
                        signupRequest();
                    } else {

                        Utilities.showNetworkError(mContext);

                    }
                }
            }
        });
    }

    private void initUI() {
        mContext = RegistrationActivity1.this;
        et_mobileno = findViewById(R.id.et_mobileno);
        btn_continue = findViewById(R.id.btn_continue);
        chk_terms = findViewById(R.id.chk_terms);
        tv_terms = findViewById(R.id.tv_terms);
        rl_back = findViewById(R.id.rl_back);

        String myString = "I agree to Advira's Terms & Conditions and Privacy Policy";
        int i1 = myString.indexOf("Terms");
        int i2 = myString.lastIndexOf("s");

        int i3 = myString.indexOf("Privacy");
        int i4 = myString.lastIndexOf("y");


        tv_terms.setMovementMethod(LinkMovementMethod.getInstance());
        tv_terms.setText(myString, TextView.BufferType.SPANNABLE);

        Spannable mySpannable = (Spannable) tv_terms.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {


                Intent i = new Intent();
                i.setClass(RegistrationActivity1.this, WebViewActivity.class);
                i.putExtra("header", "Terms & Conditions");
                i.putExtra("url", "https://www.advira.in/terms-conditions-app.php");
                startActivity(i);


            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable mySpannable2 = (Spannable) tv_terms.getText();
        ClickableSpan myClickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                Intent i = new Intent();
                i.setClass(RegistrationActivity1.this, WebViewActivity.class);
                i.putExtra("header", "Privacy Policy");
                i.putExtra("url", "https://www.advira.in/privacy-policy-app.php");
                startActivity(i);


            }
        };
        mySpannable2.setSpan(myClickableSpan2, i3, i4 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_terms.setLinkTextColor(getResources().getColor(R.color.colorPrimaryDark));




    }


    private void signupRequest() {

        Utilities.showLoading(mContext);
        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "test");
        String sessionid=SharedPrefUtil.getUniversalSharedPrefSessionid(mContext,SHARED_PREF_SESSION,"");


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        MobileOTPRequest mobileOTPRequest = new MobileOTPRequest();
        mobileOTPRequest.setMobileNo(et_mobileno.getText().toString().trim());
        mobileOTPRequest.setDeviceId(androidId);
        mobileOTPRequest.setDeviceToken(fcmtoken);
        mobileOTPRequest.setUsersession(sessionid);

        Call<MobileOTPResponse> call = RetrofitUrlConnection.loadJSON("").userRegistration(mobileOTPRequest);
        call.enqueue(new Callback<MobileOTPResponse>() {
            @Override
            public void onResponse(Call<MobileOTPResponse> call, Response<MobileOTPResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    Intent i = new Intent();
                    i.setClass(RegistrationActivity1.this, MobileOTPActivity.class);
                    i.putExtra("mobileno", et_mobileno.getText().toString());
                    i.putExtra("from", "newregistration");

                    startActivity(i);
                } else {
                    Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<MobileOTPResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }


    @Override
    public void onBackPressed() {

    }


}
