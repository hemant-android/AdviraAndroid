package com.advira.advirafarm.buyer.ui.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.selftracking.LocationTrack;
import com.advira.advirafarm.buyer.ui.forgotpassword.api.ForgotPasswordResponse;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipFragment;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OTPDialogFragment;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.api.MobileOTPRequest;
import com.advira.advirafarm.buyer.ui.splash.adapter.SlideViewPagerAdapter;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallRequest;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallResponse;
import com.advira.advirafarm.buyer.ui.splash.api.SessionResponse;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.ContentValues.TAG;
import static eu.dkaratzas.android.inapp.update.Constants.UpdateMode;

public class SlideActivity extends AppCompatActivity implements IConsts, InAppUpdateManager.InAppUpdateHandler {

    public ViewPager viewpager;
    //AutoScrollViewPager viewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    SlideViewPagerAdapter slideViewPagerAdapter;
    Button btngetstarted;

    String mobileno="";
    String fcmtoken="";
    String userstatus = "";

    private EditText et_username;

    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private static String uniqueID = null;
    /**
     * Duration of wait
     **/
   // private final int SPLASH_DISPLAY_LENGTH = 1500;
    String androidId="";

    LocationTrack locationTrack;
    AlertDialog.Builder builder;
    private Context mContext;
    String profilemode="";

    List<Integer> color;

    public synchronized static String uuid(Context context) {
        if (uniqueID == null) {

            SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }

        // Log.d("androidId", uniqueID);
        return uniqueID;
    }

    //In-app update
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    //private static final String TAG = "MainActivity";
    private InAppUpdateManager inAppUpdateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        InitUI();

        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                .mode(UpdateMode.FLEXIBLE)
                .snackBarMessage("An update has just been downloaded.")
                .snackBarAction("RESTART")
                .handler(this);

        inAppUpdateManager.checkForAppUpdate();

        //checkUpdate();

        mContext = SlideActivity.this;
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        userstatus = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
        getToken();

        // Log.d("androidId", androidId);

        String uuid = uuid(this);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {
                    checksession();
                } else {
                    Utilities.showNetworkError(mContext);
                }


            }
        },3000);
*/



        btngetstarted=findViewById(R.id.btnGetStarted);
        viewpager = findViewById(R.id.viewpager);

        color = new ArrayList<>();
        color.add(R.layout.fragment_slidefour);
        color.add(R.layout.fragment_slideone);
        color.add(R.layout.fragment_slidetwo);
        color.add(R.layout.fragment_slidethree);

        CircleIndicator indicator = findViewById(R.id.indicator);

        slideViewPagerAdapter = new SlideViewPagerAdapter(getSupportFragmentManager());

        viewpager.setAdapter(slideViewPagerAdapter);

        indicator.setViewPager(viewpager);

        slideViewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        btngetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.isNetworkConnected(mContext)) {
                    //checksession();
                    //btngetstarted.setEnabled(false);   //disable button

                    //post a message to run in UI Thread after a delay in milliseconds
                    /*btngetstarted.postDelayed(new Runnable() {
                        public void run() {
                            btngetstarted.setEnabled(true);    //enable button again
                        }
                    },2000);*/

                    Utilities.dismissDialog();
                    if (et_username.getText().toString().length() < 10) {
                        et_username.requestFocus();
                        et_username.setError("Invalid mobile no!");

                    } else {

                        Utilities.hideKeyboard(mContext);
                        if (Utilities.isNetworkConnected(mContext)) {
                            mobileno=et_username.getText().toString();
                            sendOTP();
                            Bundle args = new Bundle();
                            args.putString("mobileno", mobileno);
                            args.putString("androidId",androidId);
                            Log.e(TAG, "onResponse: slide"+mobileno+"--"+androidId+"--"+fcmtoken );
                            BottomSheetDialogFragment bottomSheetDialogFragment;
                            bottomSheetDialogFragment = OTPDialogFragment.newInstance("Bottom Sheet Dialog");
                            bottomSheetDialogFragment.show(getSupportFragmentManager() ,bottomSheetDialogFragment.getTag());

                        } else {

                            Utilities.showNetworkError(mContext);

                        }
                    }
                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
    }

    private void InitUI() {

        et_username = findViewById(R.id.et_username);
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
                SharedPrefUtil.setFCMToken(mContext,SHARED_PREF_FCMToken,fcmtoken);
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

    private void sendOTP() {

        Utilities.showLoading(mContext);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

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

                    Utilities.dismissDialog();

                   /* rl_footer1.setVisibility(View.GONE);
                    et_password.setVisibility(View.VISIBLE);
                    rl_footer.setVisibility(View.VISIBLE);
                    resend.setVisibility(View.VISIBLE);*/

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

    private class SliderTimer extends TimerTask {
            @Override
            public void run() {
                SlideActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewpager.getCurrentItem() == slideViewPagerAdapter.getCount() - 1) { //adapter is your custom ViewPager's adapter
                            viewpager.setCurrentItem(0);
                            /*if(userstatus.equalsIgnoreCase("guest")){
                                viewpager.setCurrentItem(0);
                            }
                            else{
                                Intent i = new Intent();
                                i.setClass(SlideActivity.this, MainActivityNav.class);
                                finishAffinity();
                                startActivity(i);
                                //finishAndRemoveTask();
                                Timer timer = new Timer();
                                timer.scheduleAtFixedRate(new SliderTimer(), 0,0);

                            }*/
                        } else {
                            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1, true);
                        }

                    }
                });
            }
        }

    private void checksession() {

        String token = SharedPrefUtil.getUniversalSharedPrefToken(SlideActivity.this, SHARED_PREF_TOKEN, "");
        String tt = SharedPrefUtil.getFCMToken(getApplicationContext(), SHARED_PREF_FCMToken, "");

        try {
            Call<SessionResponse> call = RetrofitUrlConnection.loadJSON(token).checksession();

            call.enqueue(new Callback<SessionResponse>() {
                @Override
                public void onResponse(@NotNull Call<SessionResponse> call, @NotNull Response<SessionResponse> response) {

                    assert response.body() != null;
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String logintype = response.body().getLoginData().getLoginType();
                        profilemode = response.body().getLoginData().getUserType();
                        //Log.e(TAG, "onResponse: profilemode"+logintype+" "+profilemode );

                        //SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode,profilemode );//change for b2c mode
                        SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode,profilemode );

                        if (logintype.equalsIgnoreCase("PreLogin")) {

                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                            //new SliderTimer();
                            //SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);
                            Intent i = new Intent();
                            //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setClass(SlideActivity.this, MainActivityGuestNav.class);
                            // i.setClass(Splash.this, RegistrationActivity2.class);
                            // i.putExtra("mobile", "8287020154");
                            finishAffinity();
                            startActivity(i);
                        } else {


                            Intent i = new Intent();
                            //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setClass(SlideActivity.this, MainActivityNav.class);
                            // i.setClass(Splash.this, OrderPreviewActivity.class);
                            SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);

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

                    //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

            //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void guestToken() {

        String token = SharedPrefUtil.getUniversalSharedPrefToken(SlideActivity.this, SHARED_PREF_TOKEN, "");

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
        String pincode = SharedPrefUtil.getpreLoginLocation(mContext, SHARED_PREF_PreLoginPin, "");

        AppInstallRequest appInstallRequest = new AppInstallRequest();
        appInstallRequest.setDeviceId(androidId);
        appInstallRequest.setPinCode(pincode);
        appInstallRequest.setDeviceToken(fcmtoken);
        appInstallRequest.setSecritKey("");
        appInstallRequest.setUsersession("");

        try {

            Call<AppInstallResponse> call = RetrofitUrlConnection.loadJSON(token).appInstall(appInstallRequest);

            call.enqueue(new Callback<AppInstallResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppInstallResponse> call,@NotNull Response<AppInstallResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String token = response.body().getAccessToken();

                        String sassionid=response.body().getSessionid();
                        SharedPrefUtil.setUniversalSharedPrefSessionid(SlideActivity.this, SHARED_PREF_SESSION, sassionid);

                        SharedPrefUtil.setUniversalSharedPrefToken(SlideActivity.this, SHARED_PREF_TOKEN, token);
                        SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                        profilemode = response.body().getUserType();
                        SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);
                        //SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                        String preloginlocation = SharedPrefUtil.getpreLoginLocation(mContext, SHARED_PREF_PreLoginPin, "1");

                        if (preloginlocation.equalsIgnoreCase("")) {
                            getlocation();
                        } else {

                            Intent i = new Intent();
                            i.setClass(SlideActivity.this, MainActivityGuestNav.class);
                            // i.setClass(Splash.this, RegistrationActivity2.class);
                            // i.putExtra("mobile", "8287020154");
                            finishAffinity();
                            startActivity(i);
                        }


                    }
                }

                @Override
                public void onFailure(Call<AppInstallResponse> call, Throwable t) {

                    //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

            //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getlocation() {
        locationTrack = new LocationTrack(SlideActivity.this);

        double longitude = 0;
        double latitude = 0;

        if (locationTrack.canGetLocation()) {


            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();


            // Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            // Log.d("location", "Longitude:" + Double.toString(longitude) + "Latitude:" + Double.toString(latitude));


            if (longitude == 0) {
                builder = new AlertDialog.Builder(mContext);
                builder.setTitle("No able to get location");

                //Setting message manually and performing action on button click
                builder.setMessage("Please allow app permission in settings and then retry")
                        .setCancelable(true)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                getlocation();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

            } else {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                String postalCode = "0";
                String city = "";
                String state = "";

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    postalCode = addresses.get(0).getPostalCode();
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String knownName = addresses.get(0).getFeatureName();
                } catch (Exception ex) {

                }

                if (postalCode.equalsIgnoreCase("0")) {
                    Toast.makeText(getApplicationContext(), "Not able to get your location, Please check app permission in settings", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPrefUtil.setpreLoginLocation(mContext, SHARED_PREF_PreLoginPin, postalCode);

                    CheckPin(postalCode, city, state);
                }


            }

        } else {

            locationTrack.showSettingsAlert();
            //getlocation();
        }


    }

    private void CheckPin(String postalCode, String city, String state) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CheckPinRequest checkPinRequest = new CheckPinRequest();
        checkPinRequest.setPincode(postalCode);

        try {

            Call<CheckPinResponse> call = RetrofitUrlConnection.loadJSON(token).pincode(checkPinRequest);

            call.enqueue(new Callback<CheckPinResponse>() {
                @Override
                public void onResponse(Call<CheckPinResponse> call, Response<CheckPinResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        if (response.body().getPincodeDetails().size() > 0) {
                            if (response.body().getPincodeDetails().get(0).getIsOperational().equalsIgnoreCase("yes")) {

                                //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                                ShowAlert(postalCode, "We are currently operational in this location.Expected delivery in " + response.body().getPincodeDetails().get(0).getApproxDeliveryTime(), city, state);
                            }
                        } else {

                            ShowAlert(postalCode, "We are currently not operational in this location", city, state);
                            //Toast.makeText(getApplicationContext(), "We are currently not operational in this location", Toast.LENGTH_SHORT).show();

                        }

                        Utilities.dismissDialog();

                    } else {

                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                    //
                }

                @Override
                public void onFailure(Call<CheckPinResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void ShowAlert(String pincode, String message, String city, String state) {
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle(state + "-" + pincode);
        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(true)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                        Intent i = new Intent();
                        i.setClass(SlideActivity.this, MainActivityGuestNav.class);
                        // i.setClass(Splash.this, RegistrationActivity2.class);
                        // i.putExtra("mobile", "8287020154");
                        finishAffinity();
                        startActivity(i);


                        // getlocation();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();
        alert.setCancelable(false);

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // If the update is cancelled by the user,
                // you can request to start the update again.
                inAppUpdateManager.checkForAppUpdate();

                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {
        Log.d(TAG, "code: " + code, error);
    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
    }
}
