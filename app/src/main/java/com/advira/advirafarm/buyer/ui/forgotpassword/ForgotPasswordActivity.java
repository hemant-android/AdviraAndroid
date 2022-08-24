package com.advira.advirafarm.buyer.ui.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ForgotPasswordResponse;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements IConsts {

    private EditText et_mobileno;
    private Button btn_continue;
    private Context mContext;
    private RelativeLayout rl_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mobileno.getText().toString().isEmpty()) {
                    et_mobileno.requestFocus();
                    et_mobileno.setError("Mobile No is required!");

                } else {

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

        mContext = ForgotPasswordActivity.this;

        et_mobileno = findViewById(R.id.et_mobileno);
        btn_continue = findViewById(R.id.btn_continue);
        rl_back = findViewById(R.id.rl_back);
    }

    private void signupRequest() {

        Utilities.showLoading(mContext);
        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        MobileOTPRequest mobileOTPRequest = new MobileOTPRequest();
        mobileOTPRequest.setMobileNo(et_mobileno.getText().toString().trim());
        mobileOTPRequest.setDeviceId(androidId);
        mobileOTPRequest.setDeviceToken(fcmtoken);

        Gson gson = new Gson();
        String vakk = gson.toJson(mobileOTPRequest).toString();

        Call<ForgotPasswordResponse> call = RetrofitUrlConnection.loadJSON("").forgotpassword(mobileOTPRequest);

        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                   // Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    Intent i = new Intent();
                    i.setClass(ForgotPasswordActivity.this, FPMobileOTPActivity.class);
                    i.putExtra("mobileno",et_mobileno.getText().toString());
                    startActivity(i);


                } else {

                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }



    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        ForgotPasswordActivity.this.finish();
    }

}
