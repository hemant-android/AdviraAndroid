package com.advira.advirafarm.buyer.ui.onetaplogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.broadcastReceiver.SmsBroadcastReceiver;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.buynow.BuynowActivity;
import com.advira.advirafarm.buyer.ui.forgotpassword.ForgotPasswordActivity;
import com.advira.advirafarm.buyer.ui.forgotpassword.PasswordChangeActivity;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ForgotPasswordResponse;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;

import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.ui.login.api.LoginRequest;
import com.advira.advirafarm.buyer.ui.login.api.LoginResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.notification.NotificationListActivity;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.RegistrationActivity1;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPResponse;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.RegistrationActivity2;
import com.advira.advirafarm.buyer.ui.splash.Splash;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallRequest;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallResponse;
import com.advira.advirafarm.buyer.ui.splash.api.SessionResponse;
import com.advira.advirafarm.buyer.ui.wallet.AddMoney;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;


import com.chaos.view.PinView;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneTapLogin extends AppCompatActivity implements IConsts{

    public static final String OTP_REGEX = "[0-9][0-9][0-9][0-9]";
    private static final String TAG = OneTapLogin.class.getSimpleName();
    private static final int REQ_USER_CONSENT=200;
    SmsBroadcastReceiver smsBroadcastReceiver;

    private Context mContext;
    private RelativeLayout rl_footer,rl_footer1;
    private String initcartsizeb2b = "0";
    private String initcartsizeb2c = "0";
    String profilemode = "";
    boolean VISIBLE_PASSWORD = false;
    protected EditText et_username;
    protected PinView et_password;
    private Button btn_login,btn_getotp;

    private TextView tv_terms;
    private RelativeLayout rl_skiplogin, rl_back;
    private RelativeLayout txt_first_time_user,resend;
    private CheckBox chk_terms;
    SmsVerifyCatcher smsVerifyCatcher;
    String fcmtoken;
    String OTP;
    //private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_tap_login);
        initUI();

       smsVerifyCatcher = new SmsVerifyCatcher(OneTapLogin.this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                et_password.setText(code);//set code in edit text
                verifyOTP(et_username.getText().toString(),et_password.getText().toString());
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(OneTapLogin.this, MainActivityNav.class);
                startActivity(i);

            }
        });

        btn_getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.dismissDialog();
                if (et_username.getText().toString().length() < 10) {
                    et_username.requestFocus();
                    et_username.setError("Invalid mobile no!");

                } else {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        sendOTP();
                        //sendOTPRequest();
                        //Utilities.dismissDialog();
                    } else {

                        Utilities.showNetworkError(mContext);

                    }
                }
        }
        });

                resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_username.getText().toString().length() < 10) {
                            et_username.requestFocus();
                            et_username.setError("Invalid mobile no!");
                        }
                        else {
                            Utilities.hideKeyboard(mContext);
                            if (Utilities.isNetworkConnected(mContext)) {
                                sendOTP();
                                //sendOTPRequest();
                                //Utilities.dismissDialog();
                            } else {
                                Utilities.showNetworkError(mContext);
                            }
                        }

                    }
                });

                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (et_username.getText().toString().trim().isEmpty()) {
                            et_username.setError("Mobile No is required!");
                        } else if (et_password.getText().toString().trim().isEmpty()) {
                            et_password.setError("OTP is required!");
                        } else if (!chk_terms.isChecked()) {
                            chk_terms.setError("Please agree");
                            Singleton.getInstance().showLongToast(mContext, "Please agree to our Terms & Conditions and Privacy Policy");
                        } else {
                            OTP = et_password.getText().toString().trim();
                            Utilities.hideKeyboard(mContext);
                            if (Utilities.isNetworkConnected(mContext)) {
                                verifyOTP(et_username.getText().toString(),et_password.getText().toString());
                                //loginRequest();
                            }
                        }
                    }
                });

                txt_first_time_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Utilities.hideKeyboard(mContext);
                        if (Utilities.isNetworkConnected(mContext)) {

                            Intent i = new Intent();
                            i.setClass(OneTapLogin.this, RegistrationActivity1.class);
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

                            //checksession();

                            /*SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
                            SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, "Guest");
                            SharedPrefUtil.setUserMobile(mContext, SHARED_PREF_UserMobile, "");
                            SharedPrefUtil.setUserEmail(mContext, SHARED_PREF_UserEmailID, "");
                            SharedPrefUtil.setUserRegno(mContext,SHARED_PREF_UserRegno,"");
                            SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
                            SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, "");
                            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                            SharedPrefUtil.setOrderCount(mContext, SHARED_PREF_OrderCount, "0");
                            SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
                            SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, "0");
*/
                            Intent i = new Intent();
                            i.setClass(OneTapLogin.this, MainActivityGuestNav.class);
                            startActivity(i);

                        } else {

                            Utilities.showNetworkError(mContext);
                        }

                    }
                });


            }

    private void startSmartUserConsent() {
        SmsRetrieverClient client=SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_USER_CONSENT){
            if(resultCode==RESULT_OK && (data!=null)){
                String message=data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOTPfromMessage(message);
            }

            
        }
    }

    private void getOTPfromMessage(String message) {
        Pattern pattern = Pattern.compile(OTP_REGEX);
        Matcher matcher = pattern.matcher(message);
        String otp = "";
        if (matcher.find())
        {
            et_password.setText(matcher.group());
        }
    }

    private void registerBroadcastReceiver(){
        smsBroadcastReceiver =new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener=new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityIfNeeded(intent,REQ_USER_CONSENT);
            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter=new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver,intentFilter);

    }


    public void getToken() {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {

                    if (!task.isSuccessful()) {
                        Log.e(TAG, "Failed to get the token.");
                        return;
                    }

                    //get the token from task
                    fcmtoken = task.getResult();

                    Log.d(TAG, "Token : " + fcmtoken);
                    //tvToken.setText(token);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Failed to get the token : " + e.getLocalizedMessage());
                }
            });
        }



    private void initUI() {
                mContext = OneTapLogin.this;
                et_username = findViewById(R.id.et_username);
                et_password = findViewById(R.id.et_password);
                btn_login = findViewById(R.id.btn_login);
                btn_getotp = findViewById(R.id.btn_getotp);

                tv_terms = findViewById(R.id.tv_terms);
                txt_first_time_user = findViewById(R.id.txt_first_time_user);
                chk_terms = findViewById(R.id.chk_terms);
                rl_skiplogin = findViewById(R.id.rl_skiplogin);
                et_password.setVisibility(View.GONE);
                rl_footer=findViewById(R.id.rl_footer);
                rl_footer1=findViewById(R.id.rl_footer1);
                rl_footer.setVisibility(View.GONE);
                rl_back=findViewById(R.id.rl_back);
                resend=findViewById(R.id.resend);
                resend.setVisibility(View.GONE);

                txt_first_time_user.setVisibility(View.GONE);

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
                        i.setClass(OneTapLogin.this, WebViewActivity.class);
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
                        i.setClass(OneTapLogin.this, WebViewActivity.class);
                        i.putExtra("header", "Privacy Policy");
                        i.putExtra("url", "https://www.advira.in/privacy-policy-app.php");
                        startActivity(i);


                    }
                };
                mySpannable2.setSpan(myClickableSpan2, i3, i4 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                tv_terms.setLinkTextColor(getResources().getColor(R.color.colorPrimaryDark));
                getToken();

            }

    private void verifyOTP(String mobileno, String OTP) {

        Utilities.showLoading(mContext);

        OTPVerifyRequest otpVerifyRequest=new OTPVerifyRequest();
        otpVerifyRequest.setMobileNo(mobileno);
        otpVerifyRequest.setOtp(OTP);
        try{
            Call<OTPVerifyResponse> call=RetrofitUrlConnection.loadJSON("").verifyMobileNo(otpVerifyRequest);
            call.enqueue(new Callback<OTPVerifyResponse>() {
                @Override
                public void onResponse(Call<OTPVerifyResponse> call, Response<OTPVerifyResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        loginRequest();
                    }
                    else{
                        Utilities.dismissDialog();
                        AlertDialog.Builder alert = new AlertDialog.Builder(OneTapLogin.this);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Wrong OTP.Please Enter OTP");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                    }
                }

                @Override
                public void onFailure(Call<OTPVerifyResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loginRequest() {

                Utilities.showLoading(mContext);
                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setMobileNo(et_username.getText().toString().trim());
                loginRequest.setPassword("");
                loginRequest.setDeviceId(androidId);
                loginRequest.setDeviceToken(fcmtoken);
                Call<LoginResponse> call = RetrofitUrlConnection.loadJSON("").login(loginRequest);
                Gson gson = new Gson();

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                            Utilities.dismissDialog();
                            String accessToken = response.body().getAccessToken();
                            String username = response.body().getLoginData().getName();
                            String userid=response.body().getLoginData().getId();
                            String usermobile = response.body().getLoginData().getMobileNo();
                            String useremail = response.body().getLoginData().getEmail();
                            String image_url = response.body().getLoginData().getProfilePicture();
                            String profileper = response.body().getLoginData().getProfileCompletion();
                            String cartsize = response.body().getCartSize().toString();
                            String cartsizeb2b = response.body().getCartSizeB2B().toString();

                            profilemode = response.body().getLoginData().getUserType().toString();
                            initcartsizeb2b = cartsizeb2b;
                            initcartsizeb2c = cartsize;

                            if(response.body().getMembershipData()!=null && response.body().getMembershipData().size()>0){
                                String membershipName=response.body().getMembershipData().get(0).getMembershipDetails();
                                String mem_expirydate=response.body().getMembershipData().get(0).getMembershipExpDate();
                                String mem_startdate=response.body().getMembershipData().get(0).getMembershipStartDate();
                                String mem_starttime=response.body().getMembershipData().get(0).getCreatedAt();
                                SharedPrefUtil.setMembershipStartDate(mContext,SHARED_PREF_MemberShip_StartDate,mem_startdate);

                                SharedPrefUtil.setMembership(mContext,SHARED_PREF_MemberShip,membershipName);
                                SharedPrefUtil.setMembershipEndDate(mContext,SHARED_PREF_MemberShip_EndDate,mem_expirydate);
                                SharedPrefUtil.setmembershipStartTime(mContext,IConsts.SHARED_PREF_MemberShip_StartTime,mem_starttime);

                            }
                            else{
                                SharedPrefUtil.setMembership(mContext,SHARED_PREF_MemberShip,"");
                                SharedPrefUtil.setMembershipEndDate(mContext,SHARED_PREF_MemberShip_EndDate,"");
                                SharedPrefUtil.setMembershipStartDate(mContext,SHARED_PREF_MemberShip_StartDate,"");
                            }

                            String memstarttime=SharedPrefUtil.getmembershipStartTime(mContext,IConsts.SHARED_PREF_MemberShip_StartTime,"");
                            Log.e(TAG, "onResponse: loginmem"+memstarttime );

                            SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, accessToken);
                            SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, username);
                            SharedPrefUtil.setUserRegno(mContext,SHARED_PREF_UserRegno,userid);
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

                            /*Utilities.dismissDialog();
                            Intent i = new Intent();
                            i.setClass(OneTapLogin.this, MainActivityNav.class);
                            startActivity(i);*/

                            CheckProfile();
                            //Utilities.dismissDialog();
                           /* Intent i = new Intent();
                            i.setClass(LoginActivity.this, MainActivityNav.class);
                            startActivity(i);*/

                        } else {

                            Utilities.dismissDialog();
                            String msg=response.body().getMessage();
                            AlertDialog.Builder alert = new AlertDialog.Builder(OneTapLogin.this);
                            alert.setTitle("Alert!!");
                            alert.setMessage(msg);
                            alert.setPositiveButton("OK",null);
                            alert.show();
                            //Singleton.getInstance().showLongToast(mContext, response.body().getMessage()+" 2");//remove toast
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

                                }
                                else {
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
                                i.setClass(OneTapLogin.this, MainActivityNav.class);
                                startActivity(i);

                            } else {

                                Utilities.dismissDialog();
                            }


                        }

                        @Override
                        public void onFailure(Call<IsUserVerifiedResponse> call, Throwable t) {
                            Utilities.dismissDialog();
                            //Singleton.getInstance().showErrorLongToast(mContext, "Something went wrong"+" 3");

                        }
                    });
                } catch (Exception e) {
                    Utilities.dismissDialog();
                    Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
                }

            }


      /*      private void checksession() {

                String token = SharedPrefUtil.getUniversalSharedPrefToken(OneTapLogin.this, SHARED_PREF_TOKEN, "");

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
                                    i.setClass(OneTapLogin.this, MainActivityGuestNav.class);
                                    finishAffinity();
                                    startActivity(i);
                                } else {


                                    Intent i = new Intent();
                                    i.setClass(OneTapLogin.this, MainActivityNav.class);
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
            }*/

            private String parseCode(String message) {

                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(message);
                String otp = "";
                while (matcher.find())
                {
                    otp = matcher.group();
                }
                return otp;
            }

            private void guestToken() {

                String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                //String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
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
                                SharedPrefUtil.setUniversalSharedPrefSessionid(OneTapLogin.this,SHARED_PREF_SESSION, sassionid);
                                SharedPrefUtil.setUniversalSharedPrefToken(OneTapLogin.this, SHARED_PREF_TOKEN, token);
                                SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                                Intent i = new Intent();
                                i.setClass(OneTapLogin.this, MainActivityGuestNav.class);
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


            private void sendOTP() {

                Utilities.showLoading(mContext);
                //String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");


                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                MobileOTPRequest mobileOTPRequest = new MobileOTPRequest();
                mobileOTPRequest.setMobileNo(et_username.getText().toString().trim());
                //mobileOTPRequest.setMobileNo("7827661058");
                mobileOTPRequest.setDeviceId(androidId);
                mobileOTPRequest.setDeviceToken(fcmtoken);

                Gson gson = new Gson();
                String vakk = gson.toJson(mobileOTPRequest).toString();

                Call<ForgotPasswordResponse> call = RetrofitUrlConnection.loadJSON("").forgotpassword(mobileOTPRequest);

                call.enqueue(new Callback<ForgotPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                        if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                            rl_footer1.setVisibility(View.GONE);
                            et_password.setVisibility(View.VISIBLE);
                            rl_footer.setVisibility(View.VISIBLE);
                            resend.setVisibility(View.VISIBLE);

                             //Singleton.getInstance().showLongToast(mContext, response.body().getMessage()+" 4");
                           /*Intent i = new Intent();
                            i.setClass(OneTapLogin.this, OneTapLogin.class);
                            i.putExtra("mobileno",et_username.getText().toString());
                            startActivity(i);*/

                        } else {
                            /*AlertDialog.Builder alert = new AlertDialog.Builder(OneTapLogin.this);
                            alert.setTitle("Alert!!");
                            alert.setMessage("Your "+response.body().getMessage()+".Do you want to Register with us?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i=new Intent();
                                    i.setClass(OneTapLogin.this,RegistrationActivity1.class);
                                    startActivity(i);
                                }
                            });
                            alert.setNegativeButton("No",null);
                            alert.show();*/
                            //Singleton.getInstance().showLongToast(mContext, response.body().getMessage()+" 5");//remove toast
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
    protected void onStart() {
        super.onStart();
        //smsVerifyCatcher.onStart();
        //registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //smsVerifyCatcher.onStop();
        //unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


            @Override
            public void onBackPressed() {
                    super.onBackPressed();
            }




}
