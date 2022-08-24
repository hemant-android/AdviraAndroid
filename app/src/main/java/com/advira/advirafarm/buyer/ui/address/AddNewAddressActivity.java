package com.advira.advirafarm.buyer.ui.address;
import static android.content.ContentValues.TAG;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressRequest;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.ChooseAddressListBuynow;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.masterapi.City;
import com.advira.advirafarm.buyer.ui.masterapi.CityList;
import com.advira.advirafarm.buyer.ui.masterapi.CityListAll;
import com.advira.advirafarm.buyer.ui.masterapi.MasterResponse;
import com.advira.advirafarm.buyer.ui.masterapi.State;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.PincodeDetail;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.registration.adapter.CityAdapter;
import com.advira.advirafarm.buyer.ui.registration.adapter.StateAdapter;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewAddressActivity extends AppCompatActivity implements IConsts {

    StateAdapter arrayAdapterState;
    CityAdapter arrayAdapterCity;
    AlertDialog.Builder builder;
    private RelativeLayout rl_back, rl_search, rl_cart;
    private TextView tv_cartcount;
    private Button btn_save;
    private Button btn_cancel;
    private Context mContext;
    private Spinner spn_castate;
    private Spinner spn_cacity;
    private AutoCompleteTextView et_capin;
    private EditText et_flat,et_username, et_mobileno;
    private EditText et_street;
    private SwitchCompat toggle_default;
    private List<StateList> arrayListState;
    private List<CityList> arrayListCity;
    private List<CityList> arrayListCityPermanent;
    private List<CityListAll> arrayListCityAll;
    private Integer cacityid = 0;
    private Integer percityid = 0;
    private String from = "";
    private DatePickerDialog picker;
    public static String addressusername="";
    private SimpleDateFormat dateFormatter;
    String city="",street="",pincode="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewaddress);
        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAddressActivity.this.finish();
            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(AddNewAddressActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(AddNewAddressActivity.this, Search_one.class);
                startActivity(i);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAddressActivity.this.finish();
            }
        });

        spn_castate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    arrayListCity = new ArrayList<>();
                    arrayListCity.add(new CityList(0, "Select City "));
                    String stateid = String.valueOf(arrayListState.get(spn_castate.getSelectedItemPosition()).getId());
                    for (int i = 0; i < arrayListCityAll.size() && !stateid.equalsIgnoreCase("0"); i++) {
                        if (arrayListCityAll.get(i).getState_id().equalsIgnoreCase(stateid)) {
                            int cityid = arrayListCityAll.get(i).getId();
                            String cityname = arrayListCityAll.get(i).getName();
                            arrayListCity.add(new CityList(cityid, cityname));
                        }
                    }
                    arrayAdapterCity = new CityAdapter(AddNewAddressActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListCity);
                    spn_cacity.setAdapter(arrayAdapterCity);
                    for (int i = 0; i < arrayListCity.size(); i++) {
                        if (arrayListCity.get(i).getId() == (cacityid)) {
                            spn_cacity.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spn_cacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    String cityid = String.valueOf(arrayListCity.get(spn_cacity.getSelectedItemPosition()).getId());
                    PopulateCityPincode(cityid);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curstateid = String.valueOf(arrayListState.get(spn_castate.getSelectedItemPosition()).getId());
                String curcityid = "0";
                try {
                    curcityid = String.valueOf(arrayListCity.get(spn_cacity.getSelectedItemPosition()).getId());
                } catch (Exception ex) {
                }
                if (et_capin.getText().toString().length() < 6) {
                    et_capin.setError("Invalid Pincode");
                } else if (et_flat.getText().toString().length() < 1) {
                    et_flat.setError("Enter Address 1");
                } else if (et_street.getText().toString().length() < 1) {
                    et_street.setError("Enter Address 2");
                }else if (et_username.getText().toString().length() < 1) {
                    et_username.setError("Enter UserName");
                }else if (et_mobileno.getText().toString().length() < 1) {
                    et_mobileno.setError("Enter Mobile No.");
                } else if (curstateid.equalsIgnoreCase("0")) {
                    Singleton.getInstance().showLongToast(mContext, "Select State");//remove toast
                } else if (curcityid.equalsIgnoreCase("0")) {
                    Singleton.getInstance().showLongToast(mContext, "Select City");//remove toast
                } else {
                    CheckPin();
                    //getParentActivityIntent();
                    //SaveNewAddress();
                }
            }
        });

    }

    private void initUI() {

        mContext = AddNewAddressActivity.this;

        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        tv_cartcount.setText(cartcount);

        et_capin = findViewById(R.id.et_capin);
        et_username = findViewById(R.id.et_username);
        et_mobileno=findViewById(R.id.et_mobileno);
        et_flat = findViewById(R.id.et_flat);
        et_street = findViewById(R.id.et_street);
        toggle_default = findViewById(R.id.toggle_default);

        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        spn_castate = findViewById(R.id.spn_castate);
        spn_cacity = findViewById(R.id.spn_cacity);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            from = extras.getString("from");
            city=extras.getString("city");
            street=extras.getString("street");
            pincode=extras.getString("pincode");
        }
        if(from.equalsIgnoreCase("currentadd")) {
            et_flat.setText(street);
            et_street.setText(city);
            btn_save.setText("Confirm");
            et_capin.setText(pincode);

        }else
        {
            et_flat.setText(" ");
            et_street.setText(" ");
        }

        String username=SharedPrefUtil.getUserName(mContext,SHARED_PREF_UserName,"");
        String mobileno=SharedPrefUtil.getUserMobile(mContext,SHARED_PREF_UserMobile,"");
        et_username.setText(username);
        et_mobileno.setText(mobileno);

        bindStateCitySpinner();

    }

    private void bindStateCitySpinner() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);

        try {
            Call<MasterResponse> call = RetrofitUrlConnection.loadJSON(token).mastersdata();

            call.enqueue(new Callback<MasterResponse>() {
                @Override
                public void onResponse(Call<MasterResponse> call, Response<MasterResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        List<State> stateList = new ArrayList<>();
                        stateList = response.body().getMasterData().getStates();

                        arrayListState = new ArrayList<>();
                        arrayListState.add(new StateList(0, "Select State "));

                        for (int i = 0; i < stateList.size(); i++) {

                            int stateid = stateList.get(i).getId();
                            String statename = stateList.get(i).getName();
                            arrayListState.add(new StateList(stateid, statename));

                        }


                        List<City> cityList = new ArrayList<>();
                        cityList = response.body().getMasterData().getCities();


                        arrayListCityAll = new ArrayList<>();
                        arrayListCityAll.add(new CityListAll(0, "Select City ", "0"));


                        for (int i = 0; i < cityList.size(); i++) {

                            int cityid = cityList.get(i).getId();
                            String cityname = cityList.get(i).getName();
                            String stateid = cityList.get(i).getStateId();

                            // arrayListCity.add(new CityList(cityid, cityname));
                            arrayListCityAll.add(new CityListAll(cityid, cityname, stateid));
                        }

                        arrayAdapterState = new StateAdapter(AddNewAddressActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListState);
                        spn_castate.setAdapter(arrayAdapterState);


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


    private void PopulateCityPincode(String cityid) {


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);

        PinSuggestionRequest pinSuggestionRequest = new PinSuggestionRequest();
        pinSuggestionRequest.setCity(cityid);
        Call<PinSuggestionResponse> call = RetrofitUrlConnection.loadJSON(token).pincodebycity(pinSuggestionRequest);

        call.enqueue(new Callback<PinSuggestionResponse>() {
            @Override
            public void onResponse(Call<PinSuggestionResponse> call, Response<PinSuggestionResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    if (response.body().getPincodeDetails().size() > 0) {

                        List<PincodeDetail> pincodeDetails;

                        pincodeDetails = response.body().getPincodeDetails();

                        String[] pin = new String[pincodeDetails.size()];


                        for (int i = 0; i < pincodeDetails.size(); i++) {

                            String citypincode = pincodeDetails.get(i).getPincode();
                            pin[i] = citypincode;
                        }


                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, pin);
                        et_capin.setAdapter(adapter);


                    }

                } else {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<PinSuggestionResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }

    private void SaveNewAddress() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String curstateid = String.valueOf(arrayListState.get(spn_castate.getSelectedItemPosition()).getId());
        String curcityid = String.valueOf(arrayListCity.get(spn_cacity.getSelectedItemPosition()).getId());
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        String defaultaddress = "";
        if(from.equalsIgnoreCase("currentadd")){
            defaultaddress = "1";
        }else {
            defaultaddress = "0";
        }

        AddAddressRequest addAddressRequest = new AddAddressRequest();
        addAddressRequest.setAddress(et_flat.getText().toString());
        addAddressRequest.setAddress2(et_street.getText().toString());
        addAddressRequest.setPincode(et_capin.getText().toString());
        addAddressRequest.setUsername(et_username.getText().toString());
        addAddressRequest.setMobileno(et_mobileno.getText().toString());
        addAddressRequest.setIsDafault(defaultaddress);
        addAddressRequest.setState(curstateid);
        addAddressRequest.setCity(curcityid);
        addAddressRequest.setIsPrimery("0");
        addAddressRequest.setUserType(usertype);

        Call<AddAddressResponse> call = RetrofitUrlConnection.loadJSON(token).addnewaddress(addAddressRequest);

        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Utilities.dismissDialog();
                    //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                    if(response.body().getAddressDate().size()>0) {
                        String addressid = response.body().getAddressDate().get(0).getId();


                        if (response.body().getAddressDate().get(0).getIsDafault().equalsIgnoreCase("1")) {
                            Log.e(TAG, "onResponse: isdefault" + addressid + "\n" + response.body().getAddressDate());
                            SharedPrefUtil.setDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, addressid);
                            //SharedPrefUtil.setDefaultAddress(mContext, SHARED_PREF_DefaultAddress, response.body().getAddressDate());
                        }
                        addressusername = response.body().getAddressDate().get(0).getUserName();
                    }

                    if (from.equalsIgnoreCase("Buynow")) {
                        Intent mainIntent = new Intent(AddNewAddressActivity.this, ChooseAddressListBuynow.class);
                        AddNewAddressActivity.this.finish();
                        AddNewAddressActivity.this.startActivity(mainIntent);
                    } else if (from.equalsIgnoreCase("chooseaddressfromfrag")) {
                        Intent mainIntent = new Intent(AddNewAddressActivity.this, AddressList.class);
                        AddNewAddressActivity.this.finish();
                        AddNewAddressActivity.this.startActivity(mainIntent);
                    }else if (from.equalsIgnoreCase("addresslistfrag")) {
                        Intent mainIntent = new Intent(AddNewAddressActivity.this, AddressList.class);
                        AddNewAddressActivity.this.finish();
                        AddNewAddressActivity.this.startActivity(mainIntent);
                    } else {
                        SelectAddress();
                    }

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


    private void CheckPin() {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CheckPinRequest checkPinRequest = new CheckPinRequest();
        checkPinRequest.setPincode(et_capin.getText().toString());

        try {

            Call<CheckPinResponse> call = RetrofitUrlConnection.loadJSON(token).pincode(checkPinRequest);

            call.enqueue(new Callback<CheckPinResponse>() {
                @Override
                public void onResponse(Call<CheckPinResponse> call, Response<CheckPinResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();
                        if (response.body().getPincodeDetails().size() > 0) {
                            et_capin.setTextColor(getResources().getColor(R.color.colorThemeDark));
                            SaveNewAddress();


                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewAddressActivity.this);
                            builder.setMessage("We are currently not operational in this pincode location.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();

                            et_capin.setError("Delivery unavailable");
                            et_capin.setTextColor(getResources().getColor(R.color.colorRed));
                            et_capin.requestFocus();
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
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                String status = data.getStringExtra("status");

                if (status.equalsIgnoreCase("edit")) {
                    finish();
                }


            }
        }
    }

    private void SelectAddress() {

        Utilities.showLoading(mContext);

        String addid = SharedPrefUtil.getDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, "0");
        String fulladdress = SharedPrefUtil.getDefaultAddress(mContext, SHARED_PREF_DefaultAddress, "0");


        DefaultAddressRequest defaultAddressRequest = new DefaultAddressRequest();
        defaultAddressRequest.setAddressId(addid);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        Call<AddAddressResponse> call = RetrofitUrlConnection.loadJSON(token).setdefaultaddress(defaultAddressRequest);

        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Utilities.dismissDialog();

                    try {
                        // BuynowActivity.tv_add.setText(fulladdress);
                        //BuynowActivity.tv_addid.setText(addid);
                    }
                    catch (Exception ex)
                    {

                    }
                    /*if (from.equalsIgnoreCase("AddSubscription")) {

                        Intent mainIntent = new Intent();
                        mainIntent.putExtra("startDate", startDate);
                        mainIntent.putExtra("endDate", endDate);
                        mainIntent.putExtra("deliveryMode", deliveryMode);
                        mainIntent.putExtra("address_id",addid);
                        mainIntent.setClass(mContext, SubscriptionPreviewActivity.class);
                        finish();
                        startActivity(mainIntent);
                    } else {*/


                        Intent i = new Intent();
                        i.setClass(mContext, OrderPreviewActivity.class);
                        mContext.startActivity(i);
                   // }


                } else {

                    Singleton.getInstance().showShortToast(mContext, "Please select an address");
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }
    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {
        AddNewAddressActivity.this.finish();
    }
}
