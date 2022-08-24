package com.advira.advirafarm.buyer.ui.onetaplogin;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_AvailableCredit;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNTB2B;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNTB2C;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMMessageCount;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMToken;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_HeaderAddress;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_EndDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_MemberShip_StartDate;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_OrderCount;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PROFILEPERCENTAGE;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_PaymentCheck;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfileMode;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfilePic;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserActive;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserEmailID;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserMobile;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserName;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserRegno;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ForgotPasswordResponse;
import com.advira.advirafarm.buyer.ui.login.api.LoginRequest;
import com.advira.advirafarm.buyer.ui.login.api.LoginResponse;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipFragment;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyRequest;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.OTPVerifyResponse;
import com.advira.advirafarm.buyer.ui.splash.SlideActivity;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPDialogFragment extends BottomSheetDialogFragment {

    private String OTP = "";
    String string;
    private View rootView;
    Context mContext;
    RelativeLayout rl_detactotp,btn_buynow;
    TextView tv_mobileno,tv_changemobileno,tv_resend,tv_buyplan;
    PinView firstPinView;

    String mobileno="";
    String androidId="";
    String fcmtoken="";
    String profilemode ="";
    String initcartsizeb2b = "";
    String initcartsizeb2c="";

    public static OTPDialogFragment newInstance(String string) {
        OTPDialogFragment f = new OTPDialogFragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        string = getArguments().getString("string");
        setStyle(DialogFragment.STYLE_NORMAL,0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_otp_dialog, container, false);
            initUI();

        }
        return rootView;
    }

    private void initUI() {

        mContext=getActivity();
        rl_detactotp=rootView.findViewById(R.id.rl_detactotp);
        btn_buynow=rootView.findViewById(R.id.btn_buynow);
        tv_mobileno=rootView.findViewById(R.id.tv_mobileno);
        tv_changemobileno=rootView.findViewById(R.id.tv_changemobileno);
        tv_resend=rootView.findViewById(R.id.tv_resend);
        tv_buyplan=rootView.findViewById(R.id.tv_buyplan);
        firstPinView=rootView.findViewById(R.id.firstPinView);

        Bundle mArgs = getArguments();
        if(mArgs!=null) {
            mobileno = mArgs.getString("mobileno");
            androidId=mArgs.getString("androidId");
            //fcmtoken=mArgs.getString("fcmtoken");
            Log.e(TAG, "onResponse: OTPfragnt"+mobileno+"--"+androidId+"--"+fcmtoken );
        }
        tv_mobileno.setText(mobileno);

        tv_changemobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mContext, SlideActivity.class);
                startActivity(i);
            }
        });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (et_username.getText().toString().length() < 10) {
                    et_username.requestFocus();
                    et_username.setError("Invalid mobile no!");
                }
                else {*/
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {
                        sendOTP();
                        //sendOTPRequest();
                        //Utilities.dismissDialog();
                    } else {
                        Utilities.showNetworkError(mContext);
                    }
                }

            //}
        });

        tv_buyplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (et_username.getText().toString().trim().isEmpty()) {
                    et_username.setError("Mobile No is required!");

                } else*/ if (firstPinView.getText().toString().trim().isEmpty()) {
                    firstPinView.setError("OTP is required!");

                } /*else if (!chk_terms.isChecked()) {
                    chk_terms.setError("Please agree");
                    Singleton.getInstance().showLongToast(mContext, "Please agree to our Terms & Conditions and Privacy Policy");


                }*/ else {
                    OTP = firstPinView.getText().toString().trim();
                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {
                        verifyOTP(mobileno,firstPinView.getText().toString());
                        //loginRequest();
                    }
                }
            }
        });
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
                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Please Enter Correct OTP");
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

        fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");

        //String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //String appversion="22";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobileNo(mobileno);
        loginRequest.setPassword("");
        loginRequest.setDeviceId(androidId);
        loginRequest.setDeviceToken(fcmtoken);
        //loginRequest.setAppversion(appversion);

        Call<LoginResponse> call = RetrofitUrlConnection.loadJSON("").login(loginRequest);

        Gson gson = new Gson();
        String vakk = gson.toJson(loginRequest).toString();

        String jhjhj;

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage()+" 1");//remove toast
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
                        SharedPrefUtil.setmembershipStartTime(mContext, IConsts.SHARED_PREF_MemberShip_StartTime,mem_starttime);

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
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
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
                        i.setClass(mContext, MainActivityNav.class);
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

    private void sendOTP() {

        Utilities.showLoading(mContext);
        fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
        MobileOTPRequest mobileOTPRequest = new MobileOTPRequest();
        mobileOTPRequest.setMobileNo(mobileno);
        mobileOTPRequest.setDeviceId(androidId);
        mobileOTPRequest.setDeviceToken(fcmtoken);

        Gson gson = new Gson();
        String vakk = gson.toJson(mobileOTPRequest).toString();

        Call<ForgotPasswordResponse> call = RetrofitUrlConnection.loadJSON("").forgotpassword(mobileOTPRequest);

        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                    Utilities.dismissDialog();
                } else {
                    Utilities.dismissDialog();
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }

        });
    }
}