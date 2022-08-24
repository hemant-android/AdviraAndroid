package com.advira.advirafarm.buyer.ui.registration.mobileotp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.forgotpassword.PasswordChangeActivity;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPResponse;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.RegistrationActivity2;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileOTPActivity extends Activity implements IConsts {


    private Button btn_verifynow;
    private Context mContext;
    private TextView tv_mobile;
    private TextView tv_email;
    private TextView tv_resendmobotp;
    private EditText et_mobileotp;
    private RelativeLayout rl_otp;
    String profilemode="";

    String OTP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp);

        initUI();

        rl_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MobileOTPActivity.this, RegistrationActivity1.class);
                startActivity(i);

            }
        });

        tv_resendmobotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utilities.isNetworkConnected(mContext)) {

                    sendOTPRequest();

                } else {

                    Utilities.showNetworkError(mContext);

                }
            }
        });


        btn_verifynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_mobileotp.getText().toString().isEmpty()) {

                    et_mobileotp.setError("Required!");

                }
                else {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        OTP = et_mobileotp.getText().toString().trim();

                        OTPRequest(OTP);

                    } else {
                        Utilities.showNetworkError(mContext);

                    }
                }
            }
        });
    }

    private void initUI() {

        mContext = MobileOTPActivity.this;

        rl_otp = findViewById(R.id.rl_otp);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email = findViewById(R.id.tv_email);
        tv_resendmobotp = findViewById(R.id.tv_resendmobotp);
        et_mobileotp = findViewById(R.id.et_mobileotp);
        btn_verifynow = findViewById(R.id.btn_verifynow);

        Bundle extras = getIntent().getExtras();
        String mobileno = "";

        if (extras != null) {
            mobileno = extras.getString("mobileno");

        }

        tv_mobile.setText(mobileno);

    }


    private void OTPRequest(String OTP) {

        Utilities.showLoading(mContext);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        OTPVerifyRequest otpVerifyRequest = new OTPVerifyRequest();
        otpVerifyRequest.setMobileNo(tv_mobile.getText().toString().trim());
        otpVerifyRequest.setOtp(OTP);


        Call<OTPVerifyResponse> call = RetrofitUrlConnection.loadJSON("").verifyMobileNo(otpVerifyRequest);

        call.enqueue(new Callback<OTPVerifyResponse>() {
            @Override
            public void onResponse(Call<OTPVerifyResponse> call, Response<OTPVerifyResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

                    Bundle extras = getIntent().getExtras();
                    String from = "";

                    if (extras != null) {
                        from = extras.getString("from");

                    }
                    if (from.equalsIgnoreCase("forgotpassword")) {
                        Intent i = new Intent();
                        i.setClass(MobileOTPActivity.this, PasswordChangeActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent();
                        i.setClass(MobileOTPActivity.this, RegistrationActivity2.class);
                        i.putExtra("mobile", tv_mobile.getText().toString());
                        //i.putExtra("profilemode",profilemode);
                        startActivity(i);
                    }

                } else {
                    clearText();

                    Singleton.getInstance().showLongToast(mContext, "Invalid OTP");

                    //  Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<OTPVerifyResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }

    private void sendOTPRequest() {

        Utilities.showLoading(mContext);

        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
        String sessionid=SharedPrefUtil.getUniversalSharedPrefSessionid(mContext,SHARED_PREF_SESSION,"");


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        MobileOTPRequest mobileOTPRequest = new MobileOTPRequest();
        mobileOTPRequest.setMobileNo(tv_mobile.getText().toString().trim());
        mobileOTPRequest.setDeviceId(androidId);
        mobileOTPRequest.setDeviceToken(fcmtoken);
        mobileOTPRequest.setUsersession(sessionid);


        Call<MobileOTPResponse> call = RetrofitUrlConnection.loadJSON("").userRegistration(mobileOTPRequest);

        call.enqueue(new Callback<MobileOTPResponse>() {
            @Override
            public void onResponse(Call<MobileOTPResponse> call, Response<MobileOTPResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Singleton.getInstance().showLongToast(mContext, response.body().getMessage());


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

    public void clearText() {

        et_mobileotp.setText("");
        et_mobileotp.requestFocus();
    }


    @Override
    public void onBackPressed() {

    }


}
