package com.advira.advirafarm.buyer.ui.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.selftracking.LocationTrack;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.adapter.SearchOneAdapter;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.api.ProductListResponse;
import com.advira.advirafarm.buyer.ui.product.api.ProductUnit_;
import com.advira.advirafarm.buyer.ui.product.api.SearchRequest;
import com.advira.advirafarm.buyer.ui.product.categoryapi.HomepageResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductSearchResponse;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

import com.advira.advirafarm.buyer.ui.product.searchroomdb.SearchRoomDB;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallRequest;
import com.advira.advirafarm.buyer.ui.splash.api.AppInstallResponse;
import com.advira.advirafarm.buyer.ui.splash.api.SessionResponse;

import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.DashBoardRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.HomeRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Repository.SearchRepository;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel.DashboardViewModel;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel.HomeViewModel;
import com.advira.advirafarm.buyer.ui.splash.roomDatatbase.ViewModel.SearchViewModel;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;

import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Splash extends AppCompatActivity implements IConsts, InAppUpdateManager.InAppUpdateHandler  {

    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private static final Integer DAYS_FOR_FLEXIBLE_UPDATE = 3;
    private static final int MY_REQUEST_CODE =0 ;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124 ;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private PackageInfo pInfo;
    SearchRepository moviesRepository;

    private DashboardViewModel dashboardViewModel;
    DashBoardRepository dashBoardRepository;

    private static String uniqueID = null;
    private final int SPLASH_DISPLAY_LENGTH = 500;
    String androidId;
    String userstatus = "";
    LocationTrack locationTrack;
    AlertDialog.Builder builder;
    Context mContext;
    //String fcmtoken;

    // Create Room Database
    List<Product_search> dataList=new ArrayList<>();
    SearchRoomDB database;
    //In-app update
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private InAppUpdateManager inAppUpdateManager;
    private AppUpdateManager appUpdateManager;

    @NonNull
    public synchronized static String uuid(@NonNull Context context) {
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
        return uniqueID;
    }

    @Override
    public void onCreate(@NonNull Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        moviesRepository=new SearchRepository(getApplication());

        dashBoardRepository=new DashBoardRepository(getApplication());
        dashboardViewModel=new ViewModelProvider(this).get(DashboardViewModel.class);

        HomeRepository homeRepository = new HomeRepository(getApplication());
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mContext = Splash.this;

        inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                .mode(Constants.UpdateMode.FLEXIBLE)
                .snackBarMessage("An update has just been downloaded.")
                .snackBarAction("RESTART")
                .handler(this);
        Log.d(TAG, "Update flow failed! Result code: ");
        inAppUpdateManager.checkForAppUpdate();

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());
        ImageView image = (ImageView) findViewById(R.id.splashscreen);

        HashMap<String, Object> defaultsRate = new HashMap<>();
        defaultsRate.put("new_version_code", String.valueOf(getVersionCode()));

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0) // change to 3600 on published app
                .build();

        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultsRate);
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Boolean> task) {
                //displayWelcomeMessage();
                if (task.isSuccessful()) {
                    final String new_version_code = mFirebaseRemoteConfig.getString("new_version_code");
                    if(Integer.parseInt(new_version_code) > getVersionCode()) {
                        Log.e(TAG, "onComplete: if-if");
                        showTheDialog("com.advira.advirafarm.buyer", new_version_code);
                    }
                    else{
                        Log.e(TAG, "onComplete: if-else");
                        new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Utilities.hideKeyboard(mContext);
                                    if (Utilities.isNetworkConnected(mContext)) {
                                        Log.e(TAG, "onComplete: else-if");
                                        checksession();
                                        storeHomeData();
                                        storeSearchData();
                                    } else {
                                        Utilities.showNetworkError(mContext);
                                    }
                                }
                            }, SPLASH_DISPLAY_LENGTH);
                    }
                }
                else Log.e("MYLOG", "mFirebaseRemoteConfig.fetchAndActivate() NOT Successful");

            }
        });

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        userstatus = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
        String uuid = uuid(this);
        image.startAnimation(rotate);
    }

    void storeHomeData() {

        //Utilities.showLoading();
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<HomepageResponse> call = RetrofitUrlConnection.loadJSON(token).homepage_v2();
            call.enqueue(new Callback<HomepageResponse>() {
                @Override
                public void onResponse(Call<HomepageResponse> call, Response<HomepageResponse> response) {
                    if(response.isSuccessful())
                    {
                        Log.d("main", "onResponse: "+response.body());
                        assert response.body() != null;
                        dashBoardRepository.insert(response.body().getDashboardBanners());
                        dashBoardRepository.insert_homeProduct(response.body().getProductList());
                        //toast.maketext(mContext, "home Data Saved-1R", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(mContext, "Search Data not Saved, Something Wrong!!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<HomepageResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void storeSearchData() {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<ProductSearchResponse> call = RetrofitUrlConnection.loadJSON(token).productsearch();
            call.enqueue(new Callback<ProductSearchResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductSearchResponse> call, Response<ProductSearchResponse> response) {
                    if(response.isSuccessful())
                    {
                        //Log.d("main", "onResponse: "+response.body());
                        assert response.body() != null;
                        moviesRepository.insert(response.body().getProductList());
                        //Toast.makeText(mContext, "Search Data Saved-1", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(mContext, "Search Data not Saved, Something Wrong!!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductSearchResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTheDialog(final String appPackageName, String versionFromRemoteConfig){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update")
                .setMessage("New Update is available, please update to new!! "/*+versionFromRemoteConfig*/)
                .setPositiveButton("UPDATE", null)
                .show();

        dialog.setCancelable(false);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }


    public int getVersionCode() {
        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("MYLOG", "NameNotFoundException: "+e.getMessage());
        }
        return pInfo.versionCode;
    }

    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, Splash.IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                .show();
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    void checksession() {

        String token = SharedPrefUtil.getUniversalSharedPrefToken(Splash.this, SHARED_PREF_TOKEN, "");
        String tt = SharedPrefUtil.getFCMToken(getApplicationContext(), SHARED_PREF_FCMToken, "");

        try {
            Call<SessionResponse> call = RetrofitUrlConnection.loadJSON(token).checksession();

            call.enqueue(new Callback<SessionResponse>() {
                @Override
                public void onResponse(@NotNull Call<SessionResponse> call,@NotNull Response<SessionResponse> response) {
                    //assert response.body() != null;
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String logintype = response.body().getLoginData().getLoginType();
                        String profilemode = response.body().getLoginData().getUserType();

                        if (logintype.equalsIgnoreCase("PreLogin")) {

                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                            SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);
                            Intent i = new Intent();
                            i.setClass(Splash.this, OneTapLogin.class);
                            // i.putExtra("mobile", "8287020154");
                            //finishAffinity();
                            startActivity(i);
                        } else {
                            Intent i = new Intent();
                            i.setClass(Splash.this, MainActivityNav.class);
                            finishAffinity();
                            startActivity(i);
                        }
                    } else {
                        guestToken();
                    }
                }

                @Override
                public void onFailure(Call<SessionResponse> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    void guestToken() {
        String token = SharedPrefUtil.getUniversalSharedPrefToken(Splash.this, SHARED_PREF_TOKEN, "");

        @SuppressLint("HardwareIds")
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String fcmtoken = SharedPrefUtil.getFCMToken(mContext, SHARED_PREF_FCMToken, "");
        String pincode = SharedPrefUtil.getpreLoginLocation(mContext, SHARED_PREF_PreLoginPin, "");
        //String appversion="22";
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

                    Gson gson = new Gson();
                    String jsondata = gson.toJson(response.body());
                    Log.e(TAG, "guestToken: request "+jsondata );

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String token = response.body().getAccessToken();
                        SharedPrefUtil.setUniversalSharedPrefToken(Splash.this, SHARED_PREF_TOKEN, token);
                        String sassionid=response.body().getSessionid();
                        SharedPrefUtil.setUniversalSharedPrefSessionid(Splash.this,IConsts.SHARED_PREF_SESSION, sassionid);
                        SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "guest");
                        String profilemode = response.body().getUserType();
                        SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, profilemode);
                        String preloginlocation = SharedPrefUtil.getpreLoginLocation(mContext, SHARED_PREF_PreLoginPin, "1");
                        if (preloginlocation.equalsIgnoreCase("")) {
                            getlocation();
                        } else {
                            Intent i = new Intent();
                            i.setClass(Splash.this, OneTapLogin.class);//slide to onetap
                            finishAffinity();
                            startActivity(i);
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<AppInstallResponse> call, @NonNull Throwable t) {
                    //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    void getlocation() {

        locationTrack = new LocationTrack(Splash.this);
        double longitude = 0;
        double latitude = 0;

        if (locationTrack.canGetLocation()) {
            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            if (longitude == 0) {
                builder = new AlertDialog.Builder(mContext);
                builder.setTitle("No able to get location");

                builder.setMessage("Please allow app permission in settings and then retry")
                        .setCancelable(true)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                getlocation();

                            }
                        });
                AlertDialog alert = builder.create();
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
            getlocation();
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
                                ShowAlert(postalCode, "We are currently operational in this location.Expected delivery in " + response.body().getPincodeDetails().get(0).getApproxDeliveryTime(), city, state);
                            }
                        } else {
                            ShowAlert(postalCode, "We are currently not operational in this location", city, state);
                        }
                        Utilities.dismissDialog();
                    } else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
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
        builder.setMessage(message)
                .setCancelable(true)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent();
                        i.setClass(Splash.this, OneTapLogin.class);//slide to onetap
                        finishAffinity();
                        startActivity(i);
                        getlocation();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.setCancelable(false);

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                inAppUpdateManager.checkForAppUpdate();
                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }else{
                Log.d(TAG, "App Updated " + resultCode);
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
