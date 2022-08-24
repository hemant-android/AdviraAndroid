package com.advira.advirafarm.buyer.ui.myaccount;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.masterapi.City;
import com.advira.advirafarm.buyer.ui.masterapi.CityList;
import com.advira.advirafarm.buyer.ui.masterapi.CityListAll;
import com.advira.advirafarm.buyer.ui.masterapi.MasterResponse;
import com.advira.advirafarm.buyer.ui.masterapi.ProfileTypeList;
import com.advira.advirafarm.buyer.ui.masterapi.Profiletype;
import com.advira.advirafarm.buyer.ui.masterapi.State;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.PinSuggestionResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.PincodeDetail;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.adapter.CityAdapter;
import com.advira.advirafarm.buyer.ui.registration.adapter.ProfileAdapter;
import com.advira.advirafarm.buyer.ui.registration.adapter.StateAdapter;
import com.advira.advirafarm.buyer.ui.registration.profile.api.BusinessProfileRequest;
import com.advira.advirafarm.buyer.ui.registration.profile.api.BusinessProfileResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.api.me.MeResponse;
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

public class BusinessDetailsEditActivity extends AppCompatActivity implements IConsts {


    private Context mContext;
    private Spinner spn_castate;
    private Spinner spn_cacity;
    private RelativeLayout rl_back;
    private View v_line2;
    private Button btn_next;
    private Button btn_edit;
    private EditText et_unit;
   // private EditText et_email;
    private EditText et_gst;
    private EditText et_fssai;
    private EditText et_contrycode;
    private EditText et_stdcode;
    private EditText et_landline;
    private EditText et_fssaiexpiry;
    private EditText et_flat;
    private EditText et_street;
    private AutoCompleteTextView et_capin;
    
    private TextView tv_skip;
    private TextView tv_nogst;
    private CheckBox chk_nogst;

    private List<StateList> arrayListState;
    private List<CityList> arrayListCity;
    private List<CityListAll> arrayListCityAll;
    StateAdapter arrayAdapterState;
    CityAdapter arrayAdapterCity;

    private Integer cacityid = 0;
    private String chkself = "";

    private DatePickerDialog picker;
    private SimpleDateFormat dateFormatter;

    private List<ProfileTypeList> arrayListProfiletype;
    ProfileAdapter arrayAdapterProfile;


    private String companyemailchk = "";
    private String company_namechk = "";
    private String no_gst_declarationchk = "";
    private String company_gst_nochk = "";
    private String drug_license_nochk = "";
    private String drug_license_expirationchk = "";
    private String company_contact_ccodechk = "";
    private String company_contact_stdcodechk = "";
    private String company_contact_landlinenochk = "";
    private String business_address_1chk = "";
    private String business_address_2chk = "";
    private String business_address_statechk = "";
    private String business_address_citychk = "";
    private String business_address_pinnochk = "";

    private RadioGroup rg_gst;
    private RadioButton radioButton;
    private TextView tv_gst;
    private RadioButton rb_gstyes;
    private RadioButton rb_gstno;

    String nogstflag = "";
    String nogsttext = "";
    String nogstname = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        initUI();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });


        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity.this, MainActivityNav.class);
                startActivity(i);


            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedId = rg_gst.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                String nogst = "No";

                try {

                    nogst = radioButton.getText().toString();
                    if (nogst.equalsIgnoreCase("no")) {
                        nogst = "Yes";
                        et_gst.setFocusable(false);
                        et_gst.setError(null);
                        SaveBusinessDetails();
                    } else if (nogst.equalsIgnoreCase("yes")) {
                        if (et_gst.getText().toString().length() < 2) {
                            et_gst.setError("GST No Required");
                            et_gst.requestFocus();

                        } else {
                            SaveBusinessDetails();
                        }


                        nogst = "No";
                    }

                } catch (Exception ex) {

                }


            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_next.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
                EnableForm();
            }
        });

        et_fssaiexpiry.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        et_fssaiexpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.YEAR, 0);

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity.this, android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, monthOfYear, dayOfMonth);
                                et_fssaiexpiry.setText(dateFormatter.format(newDate.getTime()));


                            }
                        }, year, month, day);

                picker.getDatePicker().setSpinnersShown(true);
                picker.getDatePicker().setCalendarViewShown(false);
                picker.getDatePicker().setMinDate(cldr.getTimeInMillis());
                picker.show();

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

                    arrayAdapterCity = new CityAdapter(com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListCity);
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

        /*chk_nogst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk_nogst.isChecked()) {

                    ShowNoGSTalert();

                } else {
                    System.out.println("Un-Checked");
                }
            }
        });*/

    }

    private void initUI() {
        mContext = com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity.this;

        tv_skip = findViewById(R.id.tv_skip);
        rl_back = findViewById(R.id.rl_back);
        v_line2 = findViewById(R.id.v_line2);
        spn_castate = findViewById(R.id.spn_castate);
        spn_cacity = findViewById(R.id.spn_cacity);
        btn_next = findViewById(R.id.btn_next);
        btn_edit = findViewById(R.id.btn_edit);

      
        et_unit = findViewById(R.id.et_unit);
       // et_email = findViewById(R.id.et_email);
        et_gst = findViewById(R.id.et_gst);
        et_fssai = findViewById(R.id.et_fssai);
        et_contrycode = findViewById(R.id.et_contrycode);
        et_stdcode = findViewById(R.id.et_stdcode);
        et_landline = findViewById(R.id.et_landline);
        et_flat = findViewById(R.id.et_flat);
        et_street = findViewById(R.id.et_street);
        et_capin = findViewById(R.id.et_capin);
        et_fssaiexpiry = findViewById(R.id.et_fssaiexpiry);
        bindStateCitySpinner();

        btn_next.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);
       // tv_skip.setVisibility(View.GONE);
        //EnableForm();

        rb_gstyes = findViewById(R.id.rb_gstyes);
        rb_gstno = findViewById(R.id.rb_gstno);
        // tv_nogst = findViewById(R.id.tv_nogst);
        //chk_nogst = findViewById(R.id.chk_nogst);
        rg_gst = findViewById(R.id.rg_gst);
        tv_gst = findViewById(R.id.tv_gst);

        String myString = "I/We do hereby declare that we are not covered under the ambit of GST";
        /*//String myString = "I agree to AdviraHeal's Terms & Conditions and Privacy Policy";
        int i1 = myString.indexOf("not");
        int i2 = myString.lastIndexOf("T");

        tv_nogst.setMovementMethod(LinkMovementMethod.getInstance());
        tv_nogst.setText(myString, TextView.BufferType.SPANNABLE);

        Spannable mySpannable = (Spannable) tv_nogst.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {


                Intent i = new Intent();
                i.setClass(BusinessDetailsEditActivity.this, WebViewActivity.class);
                i.putExtra("header", "No GST Declaration");
                i.putExtra("url", "https://www.adviraheal.com/no-gst-declaration.php");
                startActivity(i);


            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
*/

        // tv_nogst.setLinkTextColor(getResources().getColor(R.color.colorBlue));

        //tv_nogst.setText(myString);

        rg_gst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = rg_gst.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);

                String radiotext = radioButton.getText().toString();

                if (radiotext.equalsIgnoreCase("yes")) {
                    tv_gst.setVisibility(View.VISIBLE);
                    et_gst.setVisibility(View.VISIBLE);
                    nogstflag = "show";


                } else if (radiotext.equalsIgnoreCase("no")) {
                    tv_gst.setVisibility(View.GONE);
                    et_gst.setVisibility(View.GONE);

                    if (nogstflag.equalsIgnoreCase("show")|| nogstflag.equalsIgnoreCase("")) {

                        nogstflag = "hide";

                        ShowNoGSTalert();

                    }
                }
            }
        });


    }


    private void EnableForm() {

        if (company_namechk.equalsIgnoreCase("yes")) {
            et_unit.setEnabled(true);
        } else {
            et_unit.setEnabled(false);
            et_unit.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

      /*  if(companyemailchk.equalsIgnoreCase("yes"))
        {
            et_email.setEnabled(true);

        }
        else
        {
            et_email.setEnabled(false);
            et_email.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }
*/

        if (no_gst_declarationchk.equalsIgnoreCase("yes")) {
            rg_gst.setEnabled(true);
            rb_gstyes.setEnabled(true);
            rb_gstno.setEnabled(true);
        } else {
            rg_gst.setEnabled(false);
            rg_gst.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
            rb_gstyes.setEnabled(false);
            rb_gstno.setEnabled(false);
        }


        if (company_gst_nochk.equalsIgnoreCase("yes")) {
            et_gst.setEnabled(true);
        } else {
            et_gst.setEnabled(false);
            et_gst.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }


        if (drug_license_nochk.equalsIgnoreCase("yes")) {
            et_fssai.setEnabled(true);
        } else {
            et_fssai.setEnabled(false);
            et_fssai.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }


        if (drug_license_expirationchk.equalsIgnoreCase("yes")) {
            et_fssaiexpiry.setEnabled(true);
        } else {
            et_fssaiexpiry.setEnabled(false);
            et_fssaiexpiry.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if (company_contact_ccodechk.equalsIgnoreCase("yes")) {
            et_contrycode.setEnabled(true);
        } else {
            et_contrycode.setEnabled(false);
            et_contrycode.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if (company_contact_stdcodechk.equalsIgnoreCase("yes")) {
            et_stdcode.setEnabled(true);
        } else {
            et_stdcode.setEnabled(false);
            et_stdcode.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if (company_contact_landlinenochk.equalsIgnoreCase("yes")) {
            et_landline.setEnabled(true);
        } else {
            et_landline.setEnabled(false);
            et_landline.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }


        if (business_address_1chk.equalsIgnoreCase("yes")) {
            et_flat.setEnabled(true);
        } else {
            et_flat.setEnabled(false);
            et_flat.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }


        if (business_address_2chk.equalsIgnoreCase("yes")) {
            et_street.setEnabled(true);
        } else {
            et_street.setEnabled(false);
            et_street.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if (business_address_statechk.equalsIgnoreCase("yes")) {
            spn_castate.setEnabled(true);
        } else {
            spn_castate.setEnabled(false);
            spn_castate.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if (business_address_citychk.equalsIgnoreCase("yes")) {
            spn_cacity.setEnabled(true);
        } else {
            spn_cacity.setEnabled(false);
            spn_cacity.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if (business_address_pinnochk.equalsIgnoreCase("yes")) {
            et_capin.setEnabled(true);
        } else {
            et_capin.setEnabled(false);
            et_capin.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }


    }

    private void DisableForm() {
        btn_edit.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.GONE);

        spn_castate.setEnabled(false);
        spn_cacity.setEnabled(false);
        et_unit.setEnabled(false);
        et_gst.setEnabled(false);
        et_fssai.setEnabled(false);
        et_fssaiexpiry.setEnabled(false);
        et_contrycode.setEnabled(false);
        et_stdcode.setEnabled(false);
        et_landline.setEnabled(false);
        et_flat.setEnabled(false);
        et_street.setEnabled(false);
        et_capin.setEnabled(false);

    }


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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

                        arrayListCity = new ArrayList<>();
                        arrayListCity.add(new CityList(0, "Select City "));

                        arrayListCityAll = new ArrayList<>();
                        arrayListCityAll.add(new CityListAll(0, "Select City ", "0"));


                        for (int i = 0; i < cityList.size(); i++) {

                            int cityid = cityList.get(i).getId();
                            String cityname = cityList.get(i).getName();
                            String stateid = cityList.get(i).getStateId();

                            arrayListCity.add(new CityList(cityid, cityname));
                            arrayListCityAll.add(new CityListAll(cityid, cityname, stateid));

                        }

                        arrayAdapterState = new StateAdapter(com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListState);
                        spn_castate.setAdapter(arrayAdapterState);

                        arrayAdapterCity = new CityAdapter(com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity.this, R.layout.layout_profile, R.id.profile_name, arrayListCity);
                        spn_cacity.setAdapter(arrayAdapterCity);



                    } else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    }
                    Utilities.dismissDialog();

                    PopulateBusinessDetails();

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

        // Utilities.showLoading(mContext);

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


    private void PopulateBusinessDetails() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);
        Call<MeResponse> call = RetrofitUrlConnection.loadJSON(token).me();

        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    if (response.body().getBusinessProfile().size() > 0) {

                        //DisableForm();
                        et_unit.setText(response.body().getBusinessProfile().get(0).getCompanyName());
                       // et_email.setText(response.body().getBusinessProfile().get(0).getCompanyEmailId());
                        et_gst.setText(response.body().getBusinessProfile().get(0).getCompanyGstNo());
                        et_fssai.setText(response.body().getBusinessProfile().get(0).getFssaiNo());
                        et_fssaiexpiry.setText(response.body().getBusinessProfile().get(0).getFssaiLicenseExpiry());
                        et_stdcode.setText(response.body().getBusinessProfile().get(0).getCompanyContactStdcode());
                        et_landline.setText(response.body().getBusinessProfile().get(0).getCompanyContactLandlineno());
                        et_flat.setText(response.body().getBusinessProfile().get(0).getBusinessAddress1());
                        et_street.setText(response.body().getBusinessProfile().get(0).getBusinessAddress2());
                        et_capin.setText(response.body().getBusinessProfile().get(0).getBusinessAddressPinno());

                        String nogst = response.body().getBusinessProfile().get(0).getNoGstDeclaration();

                        if (nogst.equalsIgnoreCase("yes")) {
                            nogstflag = "hide";
                            rb_gstyes.setChecked(true);

                            //chk_nogst.setChecked(false);

                        } else if (nogst.equalsIgnoreCase("no")) {
                            nogstflag = "hide";
                            //chk_nogst.setChecked(true);
                            rb_gstno.setChecked(true);
                        }


                        companyemailchk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessType();
                        company_namechk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyName();
                        no_gst_declarationchk = response.body().getEditableFields().get(0).getBusinessInfo().getNoGstDeclaration();
                        company_gst_nochk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyGstNo();
                        drug_license_nochk = response.body().getEditableFields().get(0).getBusinessInfo().getDrugLicenseNo();
                        drug_license_expirationchk = response.body().getEditableFields().get(0).getBusinessInfo().getDrugLicenseExpiration();
                        company_contact_ccodechk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyContactCcode();
                        company_contact_stdcodechk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyContactStdcode();
                        company_contact_landlinenochk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyContactLandlineno();
                        business_address_1chk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddress1();
                        business_address_2chk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddress2();
                        business_address_statechk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddressState();
                        business_address_citychk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddressCity();
                        business_address_pinnochk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddressPinno();



                        for (int i = 0; i < arrayListState.size(); i++) {
                            if (arrayListState.get(i).getId() == (Integer.parseInt(response.body().getBusinessProfile().get(0).getBusinessAddressState()))) {
                                spn_castate.setSelection(i);
                                break;
                            }
                        }

                        for (int i = 0; i < arrayListCity.size(); i++) {
                            if (arrayListCity.get(i).getId() == (Integer.valueOf(response.body().getBusinessProfile().get(0).getBusinessAddressCity()))) {
                                spn_cacity.setSelection(i);
                                break;
                            }
                        }

                        try {
                            cacityid = Integer.valueOf(response.body().getBusinessProfile().get(0).getBusinessAddressCity());

                        } catch (Exception ex) {

                        }




                    }

                    if(response.body().getEditableFields().size()>0)
                    {
                        //companyemailchk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessType();
                        company_namechk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyName();
                        no_gst_declarationchk = response.body().getEditableFields().get(0).getBusinessInfo().getNoGstDeclaration();
                        company_gst_nochk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyGstNo();
                        drug_license_nochk = response.body().getEditableFields().get(0).getBusinessInfo().getDrugLicenseNo();
                        drug_license_expirationchk = response.body().getEditableFields().get(0).getBusinessInfo().getDrugLicenseExpiration();
                        company_contact_ccodechk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyContactCcode();
                        company_contact_stdcodechk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyContactStdcode();
                        company_contact_landlinenochk = response.body().getEditableFields().get(0).getBusinessInfo().getCompanyContactLandlineno();
                        business_address_1chk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddress1();
                        business_address_2chk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddress2();
                        business_address_statechk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddressState();
                        business_address_citychk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddressCity();
                        business_address_pinnochk = response.body().getEditableFields().get(0).getBusinessInfo().getBusinessAddressPinno();


                    }

                    EnableForm();

                } else {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }


    private void SaveBusinessDetails() {

        Utilities.showLoading(mContext);


        String stateid = String.valueOf(arrayListState.get(spn_castate.getSelectedItemPosition()).getId());
        String cityid = "0";

        try {
            cityid = String.valueOf(arrayListCity.get(spn_cacity.getSelectedItemPosition()).getId());

        } catch (Exception ex) {

        }


        int selectedId = rg_gst.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        String nogst = "No";

        try {

            nogst = radioButton.getText().toString();
            if (nogst.equalsIgnoreCase("no")) {
                nogst = "No";
                et_gst.setText("");

            } else if (nogst.equalsIgnoreCase("yes")) {

                nogst = "Yes";
            }

        } catch (Exception ex) {

        }

        BusinessProfileRequest businessProfileRequest = new BusinessProfileRequest();
       // businessProfileRequest.setCompanyEmailId(et_email.getText().toString());
        businessProfileRequest.setCompanyName(et_unit.getText().toString());
        businessProfileRequest.setCompanyGstNo(et_gst.getText().toString());
        businessProfileRequest.setNoGstDeclaration(nogst);
        businessProfileRequest.setFssaiNo(et_fssai.getText().toString());
        businessProfileRequest.setFssaiLicenseExpiry(et_fssaiexpiry.getText().toString());
        businessProfileRequest.setCompanyContactCcode("91");
        businessProfileRequest.setCompanyContactStdcode(et_stdcode.getText().toString());
        businessProfileRequest.setCompanyContactLandlineno(et_landline.getText().toString());
        businessProfileRequest.setBusinessAddress1(et_flat.getText().toString());
        businessProfileRequest.setBusinessAddress2(et_street.getText().toString());
        businessProfileRequest.setBusinessAddressState(stateid);
        businessProfileRequest.setBusinessAddressCity(cityid);
        businessProfileRequest.setBusinessAddressPinno(et_capin.getText().toString());
        businessProfileRequest.setNoGstDeclarationAcceptance(nogsttext);
        businessProfileRequest.setNoGstDeclarationAcceptanceName(nogstname);

        Gson gson = new Gson();
        String vakk = gson.toJson(businessProfileRequest).toString();

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);

        Call<BusinessProfileResponse> call = RetrofitUrlConnection.loadJSON(token).businessprofile(businessProfileRequest);

        call.enqueue(new Callback<BusinessProfileResponse>() {
            @Override
            public void onResponse(Call<BusinessProfileResponse> call, Response<BusinessProfileResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    Utilities.dismissDialog();
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    //SaveNewAddress();

                    Intent intent = new Intent();
                    intent.putExtra("status", "edit");
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<BusinessProfileResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });


    }


    private void MandatoryField() {


        if (et_unit.getText().toString().trim().length() < 1) {

            et_unit.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }


        if (et_gst.getText().toString().trim().length() < 1) {
            et_gst.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }
        if (et_fssai.getText().toString().trim().length() < 1) {
            et_fssai.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if (et_stdcode.getText().toString().trim().length() < 1) {
            et_stdcode.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if (et_landline.getText().toString().trim().length() < 1) {
            et_landline.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if (et_flat.getText().toString().trim().length() < 1) {
            et_flat.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if (et_street.getText().toString().trim().length() < 1) {
            et_street.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }


        if (et_capin.getText().toString().trim().length() < 1) {
            et_capin.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if (spn_castate.getSelectedItem().toString().equalsIgnoreCase("Select State ")) {
            spn_castate.setBackground(getResources().getDrawable(R.drawable.spinnerbackred));
            spn_cacity.setBackground(getResources().getDrawable(R.drawable.spinnerbackred));
        }

       /* if (spn_business.getSelectedItem().toString().equalsIgnoreCase("Select Type of Business ")) {
            spn_business.setBackground(getResources().getDrawable(R.drawable.spinnerbackred));
        }*/


        if (et_fssaiexpiry.getText().toString().trim().length() < 1) {
            et_fssaiexpiry.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }


    }


    private void ShowNoGSTalert()

    {

         LayoutInflater inflater= LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.alert_nogst, null);

        TextView textview=(TextView)view.findViewById(R.id.textmsg);
        EditText et_nogstname=(EditText)view.findViewById(R.id.et_nogstname);



       // AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        String nogstmessage = "(This declaration should be completed by the proprietor, partner, director and/or authorized signatory, who has the authority to do so)\n" +
                "\n" +
                "I/We do hereby declare that we are not covered under the ambit of GST and are not required to obtain registration or comply with GST procedures and formalities. Additionally, we also confirm that taxes applied on the invoices will be paid as per the governing law.\n" +
                "\n" +
                "We acknowledge that information furnished above on ADVIRA digital platform are true to the best of our knowledge and that we shall be bound by the acts of duly constituted attorney. In case any of the above information is found to be incorrect at a later date, my registration shall be liable to be cancelled and my any payment shall be withheld by Advira Technomanagement Private Limited and any unprocessed transaction shall remain withheld by the company.\n" +
                "\n" +
                "We also undertake the responsibility to inform all subsequent changes in the constitution or working of firm, affecting the accuracy of the answers given will be promptly communicated to your Organization.";

        textview.setText(nogstmessage);


        nogsttext = nogstmessage;



        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("NO GST Declaration !!")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("I Agree", null)
                .setNegativeButton("I Disagree", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_nogstname.getText().toString().length() > 1) {
                    rb_gstno.setChecked(true);
                    nogstflag = "hide";
                    dialog.cancel();
                    nogstname = et_nogstname.getText().toString();
                }
                else
                {
                    et_nogstname.requestFocus();
                    et_nogstname.setError("Enter name");
                    //Singleton.getInstance().showLongToast(mContext, "enter name");
                }

            }
        });

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rb_gstno.setChecked(false);
                nogstflag = "show";
                dialog.cancel();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRestart() {
        super.onRestart();
        initUI();
    }


}
