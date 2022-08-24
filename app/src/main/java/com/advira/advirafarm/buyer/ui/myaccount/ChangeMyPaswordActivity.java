package com.advira.advirafarm.buyer.ui.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ChangePasswordRequest;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ChangePasswordResponse;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.login.api.LogoutResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeMyPaswordActivity extends AppCompatActivity implements IConsts {

    boolean VISIBLE_PASSWORD = false;
    private EditText et_password;
    private EditText et_password2;
    private Button btn_submit;
    private Context mContext;
    private RelativeLayout rl_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemypassword);
        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeMyPaswordActivity.this.finish();
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
                if (et_password.getText().toString().trim().length() < 8) {
                    et_password.setError("Invalid Password!");
                }
                if (et_password2.getText().toString().trim().length() < 8) {
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
        mContext = com.advira.advirafarm.buyer.ui.myaccount.ChangeMyPaswordActivity.this;

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

                    Utilities.dismissDialog();
                    Singleton.getInstance().showLongToast(mContext, "Your password has been changed successfully. Please login with new credentials");
                    logoutRequest();

                } else {
                    Utilities.dismissDialog();
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
    private void logoutRequest() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<LogoutResponse> call = RetrofitUrlConnection.loadJSON(token).logout();

            call.enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);

                    }
                    Utilities.dismissDialog();

                }


                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }



}
