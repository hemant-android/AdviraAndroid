package com.advira.advirafarm.buyer.ui.registration.profile;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
//import com.advira.advirafarm.buyer.imageupload.GlideApp;
import com.advira.advirafarm.buyer.imageupload.ImagePickerActivity;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;

import com.advira.advirafarm.buyer.ui.myaccount.api.EmailVerifyRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.EmailVerifyResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.ProfilePictureRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.ProfilePictureResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.registration.mobileotp.RegistrationActivity1;
import com.advira.advirafarm.buyer.ui.registration.profile.api.UserProfileRequest;
import com.advira.advirafarm.buyer.ui.registration.profile.api.UserProfileResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RegistrationActivity2 extends AppCompatActivity implements IConsts, AdapterView.OnItemSelectedListener {


    public static final int REQUEST_IMAGE_1 = 100;
    boolean VISIBLE_PASSWORD = false;
    AlertDialog.Builder builder;
    String[] registeras = {"I want to Sign Up as :", "Business", "Consumer"};
    //String[] registeras = {"I want to Sign Up as :", "Business"};
    private Context mContext;
    private EditText et_name;
    private EditText et_email;
    private EditText et_newpassword;
    private EditText et_conpassword;
    private RelativeLayout rl_back;
    private Button btn_continue;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String passwordpattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$";
    private TextView tv_terms;
    private RadioGroup rg_gender;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private RadioButton radioButton;
    private EditText et_dob;
    private DatePickerDialog picker;
    private SimpleDateFormat dateFormatter;
    private CircleImageView profile_image;
    private ImageView img_add;
    private String base64String1 = "";
    private Uri uri1;
    private Spinner spn_registeras;

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        ButterKnife.bind(this);
        initUI();


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent();
                i.setClass(RegistrationActivity2.this, RegistrationActivity1.class);
                startActivity(i);*/
                RegistrationActivity2.this.finish();

            }
        });


        et_newpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_newpassword.getRight() - et_newpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (VISIBLE_PASSWORD) {
                            VISIBLE_PASSWORD = false;
                            et_newpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_newpassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visibility_off_24dp, 0);
                            et_newpassword.setSelection(et_newpassword.getText().length());

                        } else {
                            VISIBLE_PASSWORD = true;
                            et_newpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            et_newpassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visible_24dp, 0);
                            et_newpassword.setSelection(et_newpassword.getText().length());
                        }
                        return false;
                    }
                }
                return false;
            }

        });

        et_conpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_conpassword.getRight() - et_conpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (VISIBLE_PASSWORD) {
                            VISIBLE_PASSWORD = false;
                            et_conpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_conpassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visibility_off_24dp, 0);
                            et_conpassword.setSelection(et_conpassword.getText().length());

                        } else {
                            VISIBLE_PASSWORD = true;
                            et_conpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            et_conpassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_visible_24dp, 0);
                            et_conpassword.setSelection(et_conpassword.getText().length());
                        }
                        return false;
                    }
                }
                return false;
            }

        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String reg = String.valueOf(spn_registeras.getSelectedItemPosition());
                if (et_name.getText().toString().isEmpty()) {

                    et_name.requestFocus();
                    et_name.setError("Name is required!");

                } /*else if (!et_email.getText().toString().trim().matches(emailpattern)) {
                    et_email.requestFocus();
                    et_email.setError("Enter a valid Email!");

                } *//*else if (reg.equalsIgnoreCase("0")) {
                    spn_registeras.requestFocus();
                    Singleton.getInstance().showLongToast(mContext, "Please select Sign Up as ");
                }*/ else if (et_newpassword.getText().toString().isEmpty()) {
                    et_newpassword.requestFocus();
                    et_newpassword.setError("New Password is required!");

                } else if (!et_newpassword.getText().toString().trim().matches(passwordpattern)) {
                    et_newpassword.requestFocus();
                    et_newpassword.setError("Password should contain uppercase,numeric and special characters(#?!@$%^&*-)!");

                } else if (!et_conpassword.getText().toString().trim().matches(passwordpattern)) {
                    et_conpassword.requestFocus();
                    et_conpassword.setError("Password should contain uppercase,numeric and special characters(#?!@$%^&*-)!");

                } else {
                    if (et_newpassword.getText().toString().equals(et_conpassword.getText().toString())) {

                        Utilities.hideKeyboard(mContext);
                        if (Utilities.isNetworkConnected(mContext)) {
                            signupRequest();
                        } else {
                            Utilities.showNetworkError(mContext);
                        }
                    } else {
                        et_conpassword.setError("Password Mismatch!");
                    }
                }

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
                cldr.add(Calendar.YEAR, -18);

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegistrationActivity2.this, android.R.style.Theme_Holo_Dialog,
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

    }

    private void initUI() {

        mContext = RegistrationActivity2.this;

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_newpassword = findViewById(R.id.et_newpassword);
        et_conpassword = findViewById(R.id.et_conpassword);
        btn_continue = findViewById(R.id.btn_continue);
        rl_back = findViewById(R.id.rl_back);
        tv_terms = findViewById(R.id.tv_terms);
        rg_gender = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        et_dob = findViewById(R.id.et_dob);

        spn_registeras = findViewById(R.id.spn_registeras);
        spn_registeras.setVisibility(View.GONE); //Hide dropdown option_sapna
        //spn_registeras.setOnItemSelectedListener(this);



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registeras);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spn_registeras.setAdapter(aa);


        profile_image = findViewById(R.id.profile_image);
        img_add = findViewById(R.id.img_add);
        profile_image.setVisibility(View.GONE); //Hide image option_sapna
        img_add.setVisibility(View.GONE); //Hide image option_sapna



        String myString = "By creating an account, you agree to Advira's Terms & Conditions and Privacy Policy";
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
                i.setClass(RegistrationActivity2.this, WebViewActivity.class);
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
                i.setClass(RegistrationActivity2.this, WebViewActivity.class);
                i.putExtra("header", "Privacy Policy");
                i.putExtra("url", "https://www.advira.in/privacy-policy-app.php");
                startActivity(i);


            }
        };
        mySpannable2.setSpan(myClickableSpan2, i3, i4 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_terms.setLinkTextColor(getResources().getColor(R.color.colorThemeDark));


    }

    private void signupRequest() {


        Bundle extras = getIntent().getExtras();
        String mobile = "";
        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        //String profilemode="";   //change B2C to B2B

        if (extras != null) {
            mobile = extras.getString("mobile");
            //profilemode=extras.getString("profilemode");

        }


        int selectedId = rg_gender.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

        String gender = "";


        try {
            gender = radioButton.getText().toString();

        } catch (Exception ex) {

        }

        // comment this section for default profile mode B2B_sapna
        //String reg = String.valueOf(spn_registeras.getSelectedItemPosition());
        String usertype="";
        if (profilemode.equalsIgnoreCase("B2C")) {
            usertype = "B2C";
        } else  {
            usertype = "B2B";
        }


        Utilities.showLoading(mContext);
        UserProfileRequest userProfileRequest = new UserProfileRequest();
        userProfileRequest.setMobileNo(mobile);
        userProfileRequest.setEmailId(et_email.getText().toString());
        userProfileRequest.setFullName(et_name.getText().toString());
        userProfileRequest.setPassword(et_newpassword.getText().toString());
        userProfileRequest.setUserDob(et_dob.getText().toString());
        userProfileRequest.setGender(gender);
        userProfileRequest.setUserType(usertype);
        Log.e(TAG, "signupRequest: usertype"+"    "+usertype );




        Call<UserProfileResponse> call = RetrofitUrlConnection.loadJSON("").userprofile(userProfileRequest);

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    // Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    String accessToken = response.body().getAccessToken();
                    String username = response.body().getLoginData().getName();
                    String usermobile = response.body().getLoginData().getMobileNo();
                    String useremail = response.body().getLoginData().getEmail();



                    SharedPrefUtil.setUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, accessToken);
                    SharedPrefUtil.setUserName(mContext, SHARED_PREF_UserName, username);
                    SharedPrefUtil.setUserMobile(mContext, SHARED_PREF_UserMobile, usermobile);
                    SharedPrefUtil.setUserEmail(mContext, SHARED_PREF_UserEmailID, useremail);
                    SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
                    SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, "20");
                    SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");
                    SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "inactive");
                    SharedPrefUtil.setOrderCount(mContext, SHARED_PREF_OrderCount, "0");
                    SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
                    SharedPrefUtil.setProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C"); // change from B2C to B2B_sapna


                } else {
                    Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }



                Utilities.dismissDialog();
                AccountType();
                //EmailVerification();

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });


        /*
        Intent i = new Intent();
        i.setClass(RegistrationActivity2.this, PersonalDetailsActivity.class);
        startActivity(i);
        */
    }

    private void EmailVerification() {
        Utilities.showLoading(mContext);
        EmailVerifyRequest emailVerifyRequest = new EmailVerifyRequest();
        emailVerifyRequest.setEmail(et_email.getText().toString());
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");



        try {
            Call<EmailVerifyResponse> call = RetrofitUrlConnection.loadJSON(token).verifyemail(emailVerifyRequest);

            call.enqueue(new Callback<EmailVerifyResponse>() {
                @Override
                public void onResponse(Call<EmailVerifyResponse> call, Response<EmailVerifyResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());

                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                    Utilities.dismissDialog();

                    AccountType();
                    //ShowAlert();
                }

                @Override
                public void onFailure(Call<EmailVerifyResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                    Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();
            Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void AccountType() {
        String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
        //String reg = String.valueOf(spn_registeras.getSelectedItemPosition());


        if(profilemode.equalsIgnoreCase("B2C")) {
            //if (reg.equalsIgnoreCase("2")) {
            Intent i = new Intent();
            i.setClass(RegistrationActivity2.this, MainActivityNav.class);
            startActivity(i);
        }
        else{
            ShowAlert();
            /*Intent i = new Intent();
            i.setClass(RegistrationActivity2.this, BusinessDetailsActivity.class);
            startActivity(i);*/
        }
        /*} else if (reg.equalsIgnoreCase("1")) {
        Intent i = new Intent();
        i.setClass(RegistrationActivity2.this, BusinessDetailsActivity.class);
        startActivity(i);

        // }*/
    }

    private void ShowAlert() {
        builder = new AlertDialog.Builder(mContext);


        builder.setMessage("Account Created").setTitle("Account Successfully Created");

        //Setting message manually and performing action on button click
        builder.setMessage("Your account has been successfully created with us and a verification link has been send to your email id. You can create a business profile with us by providing few information. Do you wish to continue ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Intent i = new Intent();
                        i.setClass(RegistrationActivity2.this, BusinessDetailsActivity.class);
                        startActivity(i);

                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Intent i = new Intent();
                        i.setClass(RegistrationActivity2.this, MainActivityNav.class);
                        startActivity(i);
                        dialog.cancel();


                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Account Successfully Created");
        alert.show();

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

    }

    @OnClick({R.id.img_add})
    void onProfileImageClick1() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions("image1");
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void ProfileImageUpload() {

        Utilities.showLoading(mContext);

        ProfilePictureRequest profilePictureRequest = new ProfilePictureRequest();
        profilePictureRequest.setProfileImage(base64String1);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);

        Call<ProfilePictureResponse> call = RetrofitUrlConnection.loadJSON(token).update_profile_picture(profilePictureRequest);

        call.enqueue(new Callback<ProfilePictureResponse>() {
            @Override
            public void onResponse(Call<ProfilePictureResponse> call, Response<ProfilePictureResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    String profilepic = response.body().getProfilePicture().getProfileimageUrl();


                    SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, profilepic);

                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                    //SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, profilepic);
                    //SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, profileper);


                    try{
                        if (profilepic.length() > 5) {
                            //Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).into(MainActivityNav.profile_image);
                            //Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).into(profile_image);
                        }
                    }
                    catch (Exception ex)
                    {

                    }

                } else {
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<ProfilePictureResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }

    private void showImagePickerOptions(String pic) {
        ImagePickerActivity.showImagePickerOptions1(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent(pic);
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent(pic);
            }
        });
    }

    private void launchCameraIntent(String picFor) {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        if (picFor.equals("image1")) {
            startActivityForResult(intent, REQUEST_IMAGE_1);
        }

    }

    private void launchGalleryIntent(String picFor) {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);


        if (picFor.equals("image1")) {
            startActivityForResult(intent, REQUEST_IMAGE_1);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_1) {
            if (resultCode == Activity.RESULT_OK) {
                uri1 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri1);
                    base64String1 = convert(bitmap);
                    base64String1 = base64String1.replaceAll("\n", "");

                    Picasso.with(this).load(uri1.toString())
                            .into(profile_image);
                    profile_image.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

                    ProfileImageUpload();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                String status = data.getStringExtra("status");

                if (status.equalsIgnoreCase("edit")) {
                    initUI();
                }


            }
        }

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}