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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.registration.profile.api.UserProfileRequest;
import com.advira.advirafarm.buyer.ui.registration.profile.api.UserProfileResponse;
import com.advira.advirafarm.buyer.ui.registration.profile.api.me.MeResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PersonalDetailsEditActivity extends AppCompatActivity implements IConsts {

    private TextView tv_skip;
    private TextView txt_loginhere;
    private Context mContext;
    private EditText et_dob;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private DatePickerDialog picker;
    private SimpleDateFormat dateFormatter;
    private EditText et_name;
    private EditText et_email;

    private RelativeLayout rl_back;
    private Button btn_next;
    private Button btn_edit;
    private RadioGroup rg_gender;
    private RadioButton radioButton;
    private String mobileno;

    private String namechk;
    private String emailchk;
    private String mobile_nochk;
    private String user_dobchk;
    private String genderchk;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        initUI();


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
                i.setClass(com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity.this, BusinessDetailsEditActivity.class);
                startActivity(i);


            }
        });


        et_dob.setInputType(InputType.TYPE_NULL);
        et_dob.requestFocus();
        et_dob.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.YEAR,-18);

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity.this, android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, monthOfYear, dayOfMonth);
                                et_dob.setText(dateFormatter.format(newDate.getTime()));



                            }
                        }, year, month, day);

                picker.getDatePicker().setSpinnersShown(true);
                picker.getDatePicker().setCalendarViewShown(false);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_name.getText().toString().length() < 1) {
                    et_name.setError("Enter Full Name");
                } else if (et_dob.getText().toString().length() < 10) {
                    et_dob.setError("Enter valid DOB");
                }else {
                    SavePersonalDetails();
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



    }

    private void initUI() {

        mContext = com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity.this;
        tv_skip = findViewById(R.id.tv_skip);
        rl_back = findViewById(R.id.rl_back);

        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        et_dob = findViewById(R.id.et_dob);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);

        btn_next = findViewById(R.id.btn_next);
        btn_edit = findViewById(R.id.btn_edit);
        rg_gender = findViewById(R.id.rg_gender);

        PopulatePersonalDetails();

        btn_next.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);
        tv_skip.setVisibility(View.GONE);


    }

    private void EnableForm()
    {

        if(namechk.equalsIgnoreCase("yes"))
        {
            et_name.setEnabled(true);
        }
        else
        {
            et_name.setEnabled(false);
            et_name.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));

        }

        if(emailchk.equalsIgnoreCase("yes"))
        {
            et_email.setEnabled(true);

        }
        else
        {
            et_email.setEnabled(false);
            et_email.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if(user_dobchk.equalsIgnoreCase("yes"))
        {
            et_dob.setEnabled(true);
        }
        else
        {
            et_dob.setEnabled(false);
            et_dob.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

        if(genderchk.equalsIgnoreCase("yes"))
        {
            rg_gender.setEnabled(true);
            rb_male.setEnabled(true);
            rb_female.setEnabled(true);
        }
        else
        {
            rg_gender.setEnabled(false);
            rb_male.setEnabled(false);
            rb_female.setEnabled(false);
            rg_gender.setBackgroundColor(getResources().getColor(R.color.colorGreyVerylight));
        }

    }

    private void DisableForm()
    {
        btn_edit.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.GONE);
        et_dob.setEnabled(false) ;
        rg_gender.setEnabled(false) ;
    }


    private void SavePersonalDetails() {

        Utilities.showLoading(mContext);

        int selectedId = rg_gender.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

        String gender = "";

        try{
            gender = radioButton.getText().toString();
        }
        catch (Exception ex)
        {

        }

        UserProfileRequest userProfileRequest = new UserProfileRequest();
        userProfileRequest.setUserDob(et_dob.getText().toString());
        userProfileRequest.setGender(gender);
        userProfileRequest.setEmailId(et_email.getText().toString());
        userProfileRequest.setFullName(et_name.getText().toString());
        userProfileRequest.setMobileNo(mobileno);
        userProfileRequest.setPassword("");

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);



        Call<UserProfileResponse> call = RetrofitUrlConnection.loadJSON(token).userprofile(userProfileRequest);

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    String name=response.body().getLoginData().getName();
                    SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, name);
                    try {
                        MyAccountfragment.tv_username.setText(name);
                        MyAccountfragment.tv_mobile.setText(response.body().getLoginData().getMobileNo());
                        MyAccountfragment.tv_dobval.setText(response.body().getLoginData().getUserDob());
                        MyAccountfragment.tv_genderval.setText(response.body().getLoginData().getGender());
                        MainActivityNav.tv_username.setText(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent();
                    intent.putExtra("status", "edit");
                    setResult(RESULT_OK, intent);
                    //finish();
                    finish();


                } else {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });


    }


    private void PopulatePersonalDetails() {

        Utilities.showLoading(mContext);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);



        Call<MeResponse> call = RetrofitUrlConnection.loadJSON(token).me();

        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    if(response.body().getBasicInfo().size()>0) {

                       // DisableForm();
                        et_dob.setText(response.body().getBasicInfo().get(0).getUserDob());
                        et_name.setText(response.body().getBasicInfo().get(0).getName());
                        et_email.setText(response.body().getBasicInfo().get(0).getEmail());
                        mobileno=response.body().getBasicInfo().get(0).getMobileNo();

                        if(response.body().getBasicInfo().get(0).getGender().equalsIgnoreCase("male"))
                        {
                            rb_male.setChecked(true);
                        }
                        else if(response.body().getBasicInfo().get(0).getGender().equalsIgnoreCase("female"))

                        {
                            rb_female.setChecked(true);
                        }


                        namechk=response.body().getEditableFields().get(0).getPersonalInfo().getName();
                        emailchk=response.body().getEditableFields().get(0).getPersonalInfo().getEmail();
                        mobile_nochk=response.body().getEditableFields().get(0).getPersonalInfo().getMobileNo();
                        user_dobchk=response.body().getEditableFields().get(0).getPersonalInfo().getUserDob();
                        genderchk=response.body().getEditableFields().get(0).getPersonalInfo().getGender();


                    }

                    EnableForm();

                } else {
                    Utilities.dismissDialog();
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


    private void MandatoryField()
    {


       /* if(et_aadhar.getText().toString().trim().length()< 1)
        {

            et_aadhar.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if(et_curaddress1.getText().toString().trim().length()< 1)
        {
            et_curaddress1.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if(et_curaddress2.getText().toString().trim().length()< 1)
        {
            et_curaddress2.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if(et_capin.getText().toString().trim().length()< 1)
        {
            et_capin.setBackground(getResources().getDrawable(R.drawable.border_bg_color_red));
        }

        if(spn_castate.getSelectedItem().toString().equalsIgnoreCase("Select State "))
        {
            spn_castate.setBackground(getResources().getDrawable(R.drawable.spinnerbackred));
            spn_cacity.setBackground(getResources().getDrawable(R.drawable.spinnerbackred));
        }*/

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        actionOnBackPress();

    }

    private void actionOnBackPress() {

        PersonalDetailsEditActivity.this.finish();
    }


}
