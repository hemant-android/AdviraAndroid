package com.advira.advirafarm.buyer.ui.address;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_CARTCOUNT;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfileMode;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserActive;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserMobile;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.Constants;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;

import com.advira.advirafarm.buyer.service.FetchAddressIntentService;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.category.CategoryActivity;
import com.advira.advirafarm.buyer.ui.masterapi.City;
import com.advira.advirafarm.buyer.ui.masterapi.MasterResponse;
import com.advira.advirafarm.buyer.ui.myaccount.WalletActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoDetectAddressList extends AppCompatActivity implements OnMapReadyCallback{

    SupportMapFragment smf;
    FusedLocationProviderClient flp;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static BottomNavigationView bottomnavview;
    public static TextView text;
    ResultReceiver resultReceiver;
    TextView tv_local,tv_city;
    EditText et_localedit,et_searchadd;
    ImageView iv_edit;
    RelativeLayout rl_currentaddress,rl_back,rl_area,rl_search;
    Button btn_getadd;
    Context mContext;

    String houseno=" ";
    String street="";
    String city="";
    String state="";
    String pincode="",stateid="" ;
    String from="";
    int cityid;

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    List<Place.Field> fields;
    Place place;
    GoogleMap mMap;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autodetectaddresslist);

        resultReceiver = new AddressResultReceiver(new Handler());

        InitUI();

        if(!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyB3u2VFxJbQTa5u6aLDak8j0NwxzvZHiFg");
        }

        PlacesClient placesClient = Places.createClient(this);
        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initAutosearch();
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(AutoDetectAddressList.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });

        final String placeId = "INSERT_PLACE_ID_HERE";

        // Specify the fields to return.
        //final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, fields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i(TAG, "Place found: " + place.getName());
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                final ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + exception.getMessage());
                final int statusCode = apiException.getStatusCode();
                // TODO: Handle error with given status code.
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               et_localedit.setText(houseno);
               et_localedit.setVisibility(View.VISIBLE);
            }
        });

        btn_getadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(from.equalsIgnoreCase("Home")) {
                    Intent i = new Intent();
                    i.setClass(mContext, MainActivityNav.class);
                    i.putExtra("from", "currentadd");
                    i.putExtra("street", city);
                    i.putExtra("pincode", pincode);
                    mContext.startActivity(i);
                }else if(from.equalsIgnoreCase("Cart")){
                    Intent i = new Intent();
                    i.setClass(mContext, AddNewAddressActivity.class);
                    i.putExtra("from", "currentadd");
                    i.putExtra("street", street);
                    i.putExtra("city",city);
                    i.putExtra("pincode", pincode);
                    mContext.startActivity(i);
                }
            }
        });
    }

    private void initAutosearch() {

    }

    private void SaveAddress() {

        getStateandCityID();
        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        String username=SharedPrefUtil.getUserName(mContext,SHARED_PREF_UserName,"");
        String mobileno=SharedPrefUtil.getUserMobile(mContext,SHARED_PREF_UserMobile,"");

        String defaultaddress = "0";

        AddAddressRequest addAddressRequest = new AddAddressRequest();
        addAddressRequest.setAddress(street);
        addAddressRequest.setAddress2(city);
        addAddressRequest.setPincode(pincode);
        addAddressRequest.setUsername(username);
        addAddressRequest.setMobileno(mobileno);
        addAddressRequest.setIsDafault(defaultaddress);
        addAddressRequest.setState(stateid);
        addAddressRequest.setCity(String.valueOf(cityid));
        addAddressRequest.setIsPrimery("0");
        addAddressRequest.setUserType(usertype);

        Call<AddAddressResponse> call = RetrofitUrlConnection.loadJSON(token).addnewaddress(addAddressRequest);

        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Utilities.dismissDialog();
                    Log.e(TAG, "onResponseSAVEBYAUTO: "+"SUCCESS" );
                    //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast

                    /*String addressid = response.body().getAddressDate().get(0).getId();
                    addressusername=response.body().getAddressDate().get(0).getUserName();

                    if (from.equalsIgnoreCase("Buynow")) {
                        Intent mainIntent = new Intent(AddNewAddressActivity.this, ChooseAddressListBuynow.class);
                        AddNewAddressActivity.this.finish();
                        AddNewAddressActivity.this.startActivity(mainIntent);
                    } else {
                        AddNewAddressActivity.this.finish();
                    }*/

                } else {
                    Utilities.dismissDialog();
                }
                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });
    }

    private void getStateandCityID() {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);

        try {
            Call<MasterResponse> call = RetrofitUrlConnection.loadJSON(token).mastersdata();

            call.enqueue(new Callback<MasterResponse>() {
                @Override
                public void onResponse(Call<MasterResponse> call, Response<MasterResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        List<City> cityList = new ArrayList<>();
                        cityList = response.body().getMasterData().getCities();

                        for (int i = 0; i < cityList.size(); i++) {
                            if(city.equalsIgnoreCase(response.body().getMasterData().getCities().get(i).getName())){
                                cityid=response.body().getMasterData().getCities().get(i).getId();
                                stateid=response.body().getMasterData().getCities().get(i).getStateId();
                            }
                        }
                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                    Utilities.dismissDialog();


                }

                @Override
                public void onFailure(Call<MasterResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();
            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }


}


    private void InitUI() {

        mContext=AutoDetectAddressList.this;

        tv_local=findViewById(R.id.tv_local);
        tv_city=findViewById(R.id.tv_city);
        rl_back=findViewById(R.id.rl_back);
        rl_currentaddress=findViewById(R.id.rl_currentaddress);
        btn_getadd=findViewById(R.id.btn_getadd);
        et_searchadd=findViewById(R.id.et_searchadd);
        rl_search=findViewById(R.id.rl_search);

        iv_edit=findViewById(R.id.iv_edit);
        et_localedit=findViewById(R.id.et_localedit);
        rl_area=findViewById(R.id.rl_area);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            from=extras.getString("from");

        }

        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        smf.getMapAsync(AutoDetectAddressList.this);
        flp = LocationServices.getFusedLocationProviderClient(this);

        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");//remove 0
        bottomnavview=findViewById(R.id.bottom_navigatin_view);
        bottomnavview.setSelectedItemId(R.id.home_btmnav);
        bottomnavview.setItemIconTintList(null);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomnavview.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;
        LayoutInflater inflate=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cart_badge = inflate.inflate(R.layout.notification_badge,mbottomNavigationMenuView, false);
        text=(TextView) cart_badge.findViewById(R.id.notifications_badge);
        text.setText(cartcount);
        itemView.addView(cart_badge);

        bottomnavview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                switch (item.getItemId()){

                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_btmnav:
                        startActivity(new Intent(getApplicationContext(), MainActivityNav.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tokri:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home_subscription:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), MySubscription.class));
                            overridePendingTransition(0,0);
                        }
                        break;
                    case R.id.home_wallet:
                        if (usertype.equalsIgnoreCase("guest")) {
                            startActivity(new Intent(getApplicationContext(), OneTapLogin.class));
                            overridePendingTransition(0,0);
                        } else {
                            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                            overridePendingTransition(0,0);
                        }
                        break;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragmentGuest()).commit();

                return true;
            }
        });

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getMyLocation();
                        getCurrentLocation();
                        //CheckPin();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();

        //CheckPin();
        //SaveAddress();
    }
    public void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = flp.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        mMap=googleMap;
                        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("you are here....");

                        mMap.addMarker(markerOptions);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
                    }
                });
            }
        });
    }

    private void getCurrentLocation() {
        Utilities.showLoading(AutoDetectAddressList.this);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(AutoDetectAddressList.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();

                            Location location = new Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);
                            fetchaddressfromlocation(location);
                            Utilities.dismissDialog();
                        } else {
                            Utilities.dismissDialog();

                        }
                    }
                }, Looper.getMainLooper());

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap=googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {

                houseno=resultData.getString(Constants.ADDRESS);
                street=resultData.getString(Constants.LOCAITY);
                city=resultData.getString(Constants.DISTRICT);
                state=resultData.getString(Constants.STATE);
                pincode=resultData.getString(Constants.POST_CODE);
                CheckPin(pincode);
                //SharedPrefUtil.setHeaderAddress(AutoDetectAddressList.this,SHARED_PREF_HeaderAddress,resultData.getString(Constants.LOCAITY)+","+resultData.getString(Constants.POST_CODE));
                tv_local.setText(houseno);
                Log.e(TAG, "onReceiveResult: \n"+houseno+"\n-1"+street+"\n-2"+city+"\n-3"+state+"-4"+pincode );
                //et_localedit.setText(houseno);

            } else {
                //toast.maketext(AutoDetectAddressList.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            Utilities.dismissDialog();
        }
    }

    private void fetchaddressfromlocation(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private void CheckPin(String pincode) {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CheckPinRequest checkPinRequest = new CheckPinRequest();
        checkPinRequest.setPincode(pincode);

        try {

            Call<CheckPinResponse> call = RetrofitUrlConnection.loadJSON(token).pincode(checkPinRequest);

            call.enqueue(new Callback<CheckPinResponse>() {
                @Override
                public void onResponse(Call<CheckPinResponse> call, Response<CheckPinResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        if (response.body().getPincodeDetails().size() > 0) {
                            tv_local.setText(houseno);
                            rl_area.setVisibility(GONE);
                            btn_getadd.setEnabled(true);
                            btn_getadd.setBackground(getResources().getDrawable(R.drawable.button_selector));


                        } else {
                           /* AlertDialog.Builder builder = new AlertDialog.Builder(AutoDetectAddressList.this);
                            builder.setMessage("We are currently not operational in this pincode location.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();*/

                            rl_area.setVisibility(VISIBLE);
                            btn_getadd.setEnabled(false);
                            btn_getadd.setBackground(getResources().getDrawable(R.drawable.button_selector_gray));
                            btn_getadd.setTextColor(getResources().getColor(R.color.white));
                            //Singleton.getInstance().showLongToast(mContext, "We are currently not operational in this pincode location");
                            /*tv_local.setError("Delivery unavailable");
                            tv_local.setTextColor(getResources().getColor(R.color.colorRed));
                            tv_local.requestFocus();*/

                            /*et_localedit.setError("Delivery unavailable");
                            et_localedit.setTextColor(getResources().getColor(R.color.colorRed));
                            et_localedit.requestFocus();*/
                        }
                    } else {

                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<CheckPinResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            //toast.maketext(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = Autocomplete.getPlaceFromIntent(data);
                tv_local.setText(place.getAddress());

                /*double longi=place.getLatLng().longitude;
                double lati=place.getLatLng().latitude;*/

                mMap.clear();
                MarkerOptions markerOptions=new MarkerOptions().position(place.getLatLng()).title("you are here....");

                mMap.addMarker(markerOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),16));

                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }

    }
}