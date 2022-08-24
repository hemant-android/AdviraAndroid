package com.advira.advirafarm.buyer.ui.address;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.api.AddAddressResponse;
import com.advira.advirafarm.buyer.ui.address.api.UpdateAddressRequest;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.masterapi.City;
import com.advira.advirafarm.buyer.ui.masterapi.CityList;
import com.advira.advirafarm.buyer.ui.masterapi.CityListAll;
import com.advira.advirafarm.buyer.ui.masterapi.MasterResponse;
import com.advira.advirafarm.buyer.ui.masterapi.State;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.PincodeDetail;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinRequest;
import com.advira.advirafarm.buyer.ui.product.api.CheckPinResponse;
import com.advira.advirafarm.buyer.ui.registration.adapter.CityAdapter;
import com.advira.advirafarm.buyer.ui.registration.adapter.StateAdapter;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddressActivity extends AppCompatActivity implements IConsts {

    StateAdapter arrayAdapterState;
    CityAdapter arrayAdapterCity;
    String address_id = "";
    AlertDialog.Builder builder;
    private RelativeLayout rl_back, rl_search, rl_cart;
    private TextView tv_cartcount;
    private Button btn_save;
    private Button btn_cancel;
    private Context mContext;
    private Spinner spn_castate;
    private Spinner spn_cacity;
    private AutoCompleteTextView et_capin;
    //private EditText et_capin;
    private EditText et_flat;
    private EditText et_street;
    private EditText et_username;
    private EditText et_mobileno;
    private SwitchCompat toggle_default;
    private TextView tv_newadd_header2,tv_mobileno,tv_username;
    private List<StateList> arrayListState;
    private List<CityList> arrayListCity;
    private List<CityList> arrayListCityPermanent;
    private List<CityListAll> arrayListCityAll;
    private Integer cacityid = 0;
    private Integer percityid = 0;
    private DatePickerDialog picker;
    private SimpleDateFormat dateFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewaddress);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateAddressActivity.this.finish();

            }
        });

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(UpdateAddressActivity.this, CartActivity.class);
                startActivity(i);

            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //i.setClass(UpdateAddressActivity.this, SearchActivity.class);
                i.setClass(UpdateAddressActivity.this, Search_one.class);
                startActivity(i);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateAddressActivity.this.finish();

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

                    arrayAdapterCity = new CityAdapter(UpdateAddressActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListCity);
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
                    et_username.setError("Enter Name");
                }else if (et_mobileno.getText().toString().length() < 1) {
                    et_mobileno.setError("Enter Mobile No.");
                }
                else if (curstateid.equalsIgnoreCase("0")) {

                    Singleton.getInstance().showLongToast(mContext, "Select State");
                } else if (curcityid.equalsIgnoreCase("0")) {

                    Singleton.getInstance().showLongToast(mContext, "Select City");
                } else {
                    CheckPin();
                    //updateAddress();
                }

            }
        });

    }

    private void initUI() {

        mContext = UpdateAddressActivity.this;
        tv_newadd_header2 = findViewById(R.id.tv_newadd_header2);


        rl_back = findViewById(R.id.rl_back);
        rl_search = findViewById(R.id.rl_search);
        rl_cart = findViewById(R.id.rl_cart);
        tv_cartcount = findViewById(R.id.tv_cartcount);
        String cartcount = SharedPrefUtil.getCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
        tv_cartcount.setText(cartcount);

        et_capin = findViewById(R.id.et_capin);
        et_flat = findViewById(R.id.et_flat);
        et_street = findViewById(R.id.et_street);
        et_username = findViewById(R.id.et_username);
        et_mobileno = findViewById(R.id.et_mobileno);
        /*et_username.setVisibility(View.GONE);
        et_mobileno.setVisibility(View.GONE);

        tv_username=findViewById(R.id.tv_username);
        tv_mobileno=findViewById(R.id.tv_mobileno);
        tv_mobileno.setVisibility(View.GONE);
        tv_username.setVisibility(View.GONE);*/

        toggle_default = findViewById(R.id.toggle_default);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        spn_castate = findViewById(R.id.spn_castate);
        spn_cacity = findViewById(R.id.spn_cacity);

        tv_newadd_header2.setText("Update Address");
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

                        arrayAdapterState = new StateAdapter(UpdateAddressActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListState);
                        spn_castate.setAdapter(arrayAdapterState);

                        populateFields();

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

    private void populateFields() {


        Bundle extras = getIntent().getExtras();

        String address = "";
        String address2 = "";
        String state = "";
        String city = "";
        String pincode = "";
        String is_dafault = "";
        String address_type = "";
        String drug_licence = "";
        String drug_licence_exp_date = "";
        String user_name="";
        String mobile_no="";


        if (extras != null) {
            address_id = extras.getString("address_id");
            address = extras.getString("address");
            address2 = extras.getString("address2");
            state = extras.getString("state");
            city = extras.getString("city");
            pincode = extras.getString("pincode");
            is_dafault = extras.getString("is_dafault");
            address_type = extras.getString("address_type");
            drug_licence = extras.getString("drug_licence");
            drug_licence_exp_date = extras.getString("drug_licence_exp_date");
            user_name=extras.getString("user_name");
            mobile_no=extras.getString("mobile_no");

        }


        et_capin.setText(pincode);
        et_flat.setText(address);
        et_street.setText(address2);
        et_username.setText(user_name);
        et_mobileno.setText(mobile_no);

        for (int i = 0; i < arrayListState.size(); i++) {
            if (arrayListState.get(i).getId() == (Integer.valueOf(state))) {
                spn_castate.setSelection(i);
                break;
            }
        }

        try {
            cacityid = Integer.valueOf(city);

        } catch (Exception ex) {

        }

        if (is_dafault.equalsIgnoreCase("1")) {
            toggle_default.setChecked(true);
        } else {
            toggle_default.setChecked(false);
        }

        if (address_type.equalsIgnoreCase("home")) {

        } else if (address_type.equalsIgnoreCase("office")) {

        }

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
                            updateAddress();
                        } else {
                            Singleton.getInstance().showLongToast(mContext, "We are currently not operational in this pincode location");

                            et_capin.setError("Delivery unavailable");
                            et_capin.setTextColor(getResources().getColor(R.color.colorRed));
                            et_capin.requestFocus();
                        }

                    } else {

                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
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

    private void updateAddress() {

        Utilities.showLoading(mContext);

        String curstateid = String.valueOf(arrayListState.get(spn_castate.getSelectedItemPosition()).getId());
        String curcityid = String.valueOf(arrayListCity.get(spn_cacity.getSelectedItemPosition()).getId());
        String usertype = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        String defaultaddress = "0";


        UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest();
        updateAddressRequest.setAddressId(address_id);
        updateAddressRequest.setAddress(et_flat.getText().toString());
        updateAddressRequest.setAddress2(et_street.getText().toString());
        updateAddressRequest.setPincode(et_capin.getText().toString());
        updateAddressRequest.setIsDafault(defaultaddress);
        updateAddressRequest.setState(curstateid);
        updateAddressRequest.setCity(curcityid);
        updateAddressRequest.setIsPrimery("0");
        updateAddressRequest.setUserType(usertype);
        updateAddressRequest.setUsername(et_username.getText().toString());
        updateAddressRequest.setMobileno(et_mobileno.getText().toString());

        Gson gson = new Gson();
        String vakk = gson.toJson(updateAddressRequest).toString();

        Call<AddAddressResponse> call = RetrofitUrlConnection.loadJSON(token).updateaddress(updateAddressRequest);
        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Utilities.dismissDialog();
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast

                    String addressid = response.body().getAddressDate().get(0).getId();
                    UpdateAddressActivity.this.finish();

                    //  ShowAlert(addressid);


                } else {
                    Utilities.dismissDialog();
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                Singleton.getInstance().showLongToast(mContext, "Something went wrong");
                Utilities.dismissDialog();
            }
        });

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


    private void ShowAlert(String addressid) {
        builder = new AlertDialog.Builder(mContext);


        builder.setMessage("Update Address").setTitle("Address updated successfully ");

        //Setting message manually and performing action on button click
        builder.setMessage("Your Address has been updated successfully . We need Drug License image in order to activate this address. Do you wish to upload ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Intent i = new Intent();
                        i.setClass(UpdateAddressActivity.this, AddressDLUploadActivity.class);
                        i.putExtra("addressid", addressid);
                        startActivityForResult(i, 101);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        UpdateAddressActivity.this.finish();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

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
    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        UpdateAddressActivity.this.finish();
    }

}
