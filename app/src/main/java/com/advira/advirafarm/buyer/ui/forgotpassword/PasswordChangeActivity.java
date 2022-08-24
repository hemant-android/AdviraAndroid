package com.advira.advirafarm.buyer.ui.forgotpassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ChangePasswordRequest;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ChangePasswordResponse;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeActivity extends Activity implements IConsts {

    boolean VISIBLE_PASSWORD = false;
    private EditText et_password;
    private EditText et_password2;
    private Button btn_submit;
    private Context mContext;
    private RelativeLayout rl_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordchange);
        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PasswordChangeActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });


        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (VISIBLE_PASSWORD) {
                            VISIBLE_PASSWORD = false;
                            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visibility_off_24dp, 0);
                            et_password.setSelection(et_password.getText().length());

                        } else {
                            VISIBLE_PASSWORD = true;
                            et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                            et_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visible_24dp, 0);
                            et_password.setSelection(et_password.getText().length());
                        }
                        return false;
                    }
                }
                return false;
            }

        });

        et_password2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_password2.getRight() - et_password2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (VISIBLE_PASSWORD) {
                            VISIBLE_PASSWORD = false;
                            et_password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_password2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visibility_off_24dp, 0);
                            et_password2.setSelection(et_password2.getText().length());

                        } else {
                            VISIBLE_PASSWORD = true;
                            et_password2.setInputType(InputType.TYPE_CLASS_TEXT);
                            et_password2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visible_24dp, 0);
                            et_password2.setSelection(et_password2.getText().length());
                        }
                        return false;
                    }
                }
                return false;
            }

        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_password.getText().toString().length() < 8) {
                    et_password.setError("Invalid Password!");
                }
                if (et_password2.getText().toString().length() < 8) {
                    et_password2.setError("Invalid Password!");
                }
                else {
                    if (et_password.getText().toString().equals(et_password2.getText().toString())) {

                        passwordchangeRequest();
                    }
                    else {
                        et_password2.setError("Password Mismatch!");
                    }
                }
            }

        });
    }

    private void initUI() {
        mContext = PasswordChangeActivity.this;

        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);
        btn_submit = findViewById(R.id.btn_submit);
        rl_back = findViewById(R.id.rl_back);
    }


    private void passwordchangeRequest() {


        Bundle extras = getIntent().getExtras();
        String mobile = "";

        if (extras != null) {
            mobile = extras.getString("mobile");

        }

        Utilities.showLoading(mContext);
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setMobileNo(mobile);
        changePasswordRequest.setPassword(et_password.getText().toString());

        Call<ChangePasswordResponse> call = RetrofitUrlConnection.loadJSON("").changepassword(changePasswordRequest);

        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                   // Singleton.getInstance().showLongToast(mContext, response.body().getMessage());

                    Intent i = new Intent();
                    i.setClass(PasswordChangeActivity.this, ForgotPasswordDoneActivity.class);
                    startActivity(i);

                } else {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });



    }


    @Override
    public void onBackPressed() {

    }


}
