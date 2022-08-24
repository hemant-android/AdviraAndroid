package com.advira.advirafarm.buyer.ui.login;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.MainActivity;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.forgotpassword.FPMobileOTPActivity;
import com.advira.advirafarm.buyer.ui.forgotpassword.ForgotPasswordActivity;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ForgotPasswordResponse;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.login.api.LoginRequest;
import com.advira.advirafarm.buyer.ui.login.api.LoginResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.RegistrationActivity1;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.splash.Splash;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallRequest;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallResponse;
import com.advira.advirafarm.buyer.ui.splash.api.SessionResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements IConsts {

    boolean VISIBLE_PASSWORD = false;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Context mContext;
    private TextView btn_forgot_pass,otp_login1;
    private TextView tv_terms;
    private RelativeLayout rl_skiplogin,otp_login;
    private RelativeLayout txt_first_time_user;
    private CheckBox chk_terms;
    private String initcartsizeb2b = "0";
    private String initcartsizeb2c = "0";
    String profilemode="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_username.getText().toString().trim().isEmpty()) {

                    et_username.setError("Mobile No is required!");

                } else if (et_password.getText().toString().trim().isEmpty()) {
                    et_password.setError("Password is required!");

                } else if (!chk_terms.isChecked()) {
                    chk_terms.setError("Please agree");
                    Singleton.getInstance().showLongToast(mContext, "Please agree to our Terms & Conditions and Privacy Policy");


                } else {
                    String mobile = et_username.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {
                        loginRequest(mobile, password);
                    }
                }
            }
        });

        otp_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    Intent i = new Intent();
                    i.setClass(LoginActivity.this, OneTapLogin.class);
                    startActivity(i);

                } else {

                    Utilities.showNetworkError(mContext);
                }
               /* if (et_username.getText().toString().trim().isEmpty()) {
                    et_username.requestFocus();
                    et_username.setError("Mobile No is required!");

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
                }*/

            }
        });

        btn_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    Intent i = new Intent();
                    i.setClass(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivity(i);

                } else {

                    Utilities.showNetworkError(mContext);
                }

            }
        });

        txt_first_time_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    Intent i = new Intent();
                    i.setClass(LoginActivity.this, RegistrationActivity1.class);
                    startActivity(i);

                } else {


                    Utilities.showNetworkError(mContext);
                }

            }
        });


        rl_skiplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    checksession();

                   /* SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
                    SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, "Guest");
                    SharedPrefUtil.setUserMobile(mContext, SHARED_PREF_UserMobile, "");
                    SharedPrefUtil.setUserEmail(mContext, SHARED_PREF_UserEmailID, "");
                    SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
                    SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, "");
                    SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                    SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                    SharedPrefUtil.setOrderCount(mContext, SHARED_PREF_OrderCount, "0");
                    SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
                    SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, "0");

                    Intent i = new Intent();
                    i.setClass(LoginActivity.this, MainActivityGuestNav.class);
                    startActivity(i);*/

                } else {

                    Utilities.showNetworkError(mContext);
                }

            }
        });


    }



    private void initUI() {
        mContext = LoginActivity.this;
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        otp_login=findViewById(R.id.otp_login);
        otp_login1=findViewById(R.id.otp_login1);
        btn_forgot_pass = findViewById(R.id.btn_forgot_pass);
        tv_terms = findViewById(R.id.tv_terms);
        txt_first_time_user = findViewById(R.id.txt_first_time_user);
        chk_terms = findViewById(R.id.chk_terms);
        rl_skiplogin = findViewById(R.id.rl_skiplogin);


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
                i.setClass(LoginActivity.this, WebViewActivity.class);
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
                i.setClass(LoginActivity.this, WebViewActivity.class);
                i.putExtra("header", "Privacy Policy");
                i.putExtra("url", "https://www.advira.in/privacy-policy-app.php");
                startActivity(i);


            }
        };
        mySpannable2.setSpan(myClickableSpan2, i3, i4 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_terms.setLinkTextColor(getResources().getColor(R.color.colorPrimaryDark));


    }

    private void loginRequest(String mobile, String password) {

        Utilities.showLoading(mContext);

        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "test");

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobileNo(mobile);
        loginRequest.setPassword(password);
        loginRequest.setDeviceId(androidId);
        loginRequest.setDeviceToken(fcmtoken);
        Call<LoginResponse> call = RetrofitUrlConnection.loadJSON("").login(loginRequest);

        Gson gson = new Gson();
        String vakk = gson.toJson(loginRequest).toString();

        String jhjhj;

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    String accessToken = response.body().getAccessToken();
                    String username = response.body().getLoginData().getName();
                    String usermobile = response.body().getLoginData().getMobileNo();
                    String useremail = response.body().getLoginData().getEmail();
                    String image_url = response.body().getLoginData().getProfilePicture();
                    String profileper = response.body().getLoginData().getProfileCompletion();
                    String cartsize = response.body().getCartSize().toString();
                    String cartsizeb2b = response.body().getCartSizeB2B().toString();
                    profilemode = response.body().getLoginData().getUserType().toString();


                    initcartsizeb2b = cartsizeb2b;
                    initcartsizeb2c = cartsize;


                    SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, accessToken);
                    SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, username);
                    SharedPrefUtil.setUserMobile(mContext, SHARED_PREF_UserMobile, usermobile);
                    SharedPrefUtil.setUserEmail(mContext, SHARED_PREF_UserEmailID, useremail);
                    SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, image_url);
                    SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, profileper);
                    SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, cartsizeb2b);
                    SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, cartsize);
                    SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "active");
                    SharedPrefUtil.setOrderCount(mContext, SHARED_PREF_OrderCount, "0");
                    SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
                    SharedPrefUtil.setFCMMessageCount(mContext, SHARED_PREF_FCMMessageCount, "0");
                    SharedPrefUtil.setPaymentCheck(mContext, SHARED_PREF_PaymentCheck, "");
                    SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, "0");
                    SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);


                    try {
                        if (response.body().getDefaultAddress().get(0).getCityName().length() > 0) {

                            //String headeraddress = "Deliver to " + response.body().getDefaultAddress().get(0).getCityName() + " " + response.body().getDefaultAddress().get(0).getPincode();
                            String headeraddress = response.body().getDefaultAddress().get(0).getCityName() + " " + response.body().getDefaultAddress().get(0).getPincode();
                            SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, headeraddress);
                        }
                    } catch (Exception ex) {

                    }
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    CheckProfile();
                    //Utilities.dismissDialog();
                   /* Intent i = new Intent();
                    i.setClass(LoginActivity.this, MainActivityNav.class);
                    startActivity(i);*/

                } else {

                    Utilities.dismissDialog();
                    String msg=response.body().getMessage();
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage(msg);
                    alert.setPositiveButton("OK",null);
                    alert.show();
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }


    private void CheckProfile() {


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


        try {

            Call<IsUserVerifiedResponse> call = RetrofitUrlConnection.loadJSON(token).isuserverified();

            call.enqueue(new Callback<IsUserVerifiedResponse>() {
                @Override
                public void onResponse(Call<IsUserVerifiedResponse> call, Response<IsUserVerifiedResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        String popmsg = "Please update below details :";
                        String chkpop = "n";

                        if (response.body().getPersonalProfileStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Personal details";
                            chkpop = "y";
                        }
                        if (response.body().getBusinessProfileStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Business details";
                            chkpop = "y";
                        }
                        if (response.body().getKycDocumentStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Upload KYC documents";
                            chkpop = "y";
                        }
                        if (response.body().getProfileActivateStatus().equalsIgnoreCase("0")) {

                            if (popmsg.contains("Personal details") || popmsg.contains("Business details") || popmsg.contains("Upload KYC documents")) {

                            } else {
                                popmsg = "Your account is not active";
                            }

                            chkpop = "y";
                        }


                        if (chkpop.equalsIgnoreCase("y")) {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "inactive");

                        } else {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "active");

                        }


                        if (response.body().getBusinessProfileStatus().equalsIgnoreCase("0") && response.body().getKycDocumentStatus().equalsIgnoreCase("0")) {
                            // SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, initcartsizeb2c);
                        } else {
                            // SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
                            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, initcartsizeb2b);

                        }


                        Utilities.dismissDialog();
                        Intent i = new Intent();
                        i.setClass(LoginActivity.this, MainActivityNav.class);
                        startActivity(i);

                    } else {

                        Utilities.dismissDialog();
                    }


                }

                @Override
                public void onFailure(Call<IsUserVerifiedResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                    Singleton.getInstance().showErrorLongToast(mContext, "Something went wrong");

                }
            });
        } catch (Exception e) {
            Utilities.dismissDialog();
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    private void checksession() {

        String token = SharedPrefUtil.getUniversalSharedPrefToken(LoginActivity.this, SHARED_PREF_TOKEN, "");

        String tt = SharedPrefUtil.getFCMToken(getApplicationContext(), SHARED_PREF_FCMToken, "");


        try {
            Call<SessionResponse> call = RetrofitUrlConnection.loadJSON(token).checksession();

            call.enqueue(new Callback<SessionResponse>() {
                @Override
                public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String logintype = response.body().getLoginData().getLoginType();
                        profilemode = response.body().getLoginData().getUserType();

                        SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);


                        if (logintype.equalsIgnoreCase("PreLogin")) {

                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");

                            Intent i = new Intent();
                            i.setClass(LoginActivity.this, MainActivityGuestNav.class);
                            // i.setClass(Splash.this, RegistrationActivity2.class);
                            // i.putExtra("mobile", "8287020154");
                            finishAffinity();
                            startActivity(i);
                        } else {


                            Intent i = new Intent();
                            i.setClass(LoginActivity.this, MainActivityNav.class);
                            //i.setClass(Splash.this, RegistrationActivity2.class);
                            //i.putExtra("mobile", "8287020154");
                            finishAffinity();
                            startActivity(i);
                        }


                    } else {
                        guestToken();
                    }

                }

                @Override
                public void onFailure(Call<SessionResponse> call, Throwable t) {

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void guestToken() {

        String token = SharedPrefUtil.getUniversalSharedPrefToken(LoginActivity.this, SHARED_PREF_TOKEN, "");

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
        String pincode = SharedPrefUtil.getpreLoginLocation(mContext, SHARED_PREF_PreLoginPin, "");


        AppInstallRequest appInstallRequest = new AppInstallRequest();
        appInstallRequest.setDeviceId(androidId);
        appInstallRequest.setPinCode("");
        appInstallRequest.setDeviceToken(fcmtoken);
        appInstallRequest.setSecritKey("");
        appInstallRequest.setUsersession("");

        try {

            Call<AppInstallResponse> call = RetrofitUrlConnection.loadJSON(token).appInstall(appInstallRequest);

            call.enqueue(new Callback<AppInstallResponse>() {
                @Override
                public void onResponse(Call<AppInstallResponse> call, Response<AppInstallResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String token = response.body().getAccessToken();

                        String sassionid=response.body().getSessionid();
                        SharedPrefUtil.setUniversalSharedPrefSessionid(LoginActivity.this,SHARED_PREF_SESSION, sassionid);

                        SharedPrefUtil.setUniversalSharedPrefToken(LoginActivity.this, SHARED_PREF_TOKEN, token);
                        SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");


                        Intent i = new Intent();
                        i.setClass(LoginActivity.this, MainActivityGuestNav.class);
                        // i.setClass(Splash.this, RegistrationActivity2.class);
                        // i.putExtra("mobile", "8287020154");
                        finishAffinity();
                        startActivity(i);

                    }
                }

                @Override
                public void onFailure(Call<AppInstallResponse> call, Throwable t) {

                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void signupRequest() {

            Utilities.showLoading(mContext);
            String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");


            String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            MobileOTPRequest mobileOTPRequest = new MobileOTPRequest();
            mobileOTPRequest.setMobileNo(et_username.getText().toString().trim());
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
                        i.setClass(LoginActivity.this, OneTapLogin.class);
                        i.putExtra("mobileno",et_username.getText().toString());
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


    @Override
    public void onBackPressed() {
    }


}
