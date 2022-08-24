package com.advira.advirafarm.buyer.ui.guest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
//import com.advira.advirafarm.buyer.imageupload.GlideApp;
import com.advira.advirafarm.buyer.imageupload.ImagePickerActivity;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.myaccount.BusinessDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.PersonalDetailsEditActivity;
import com.advira.advirafarm.buyer.ui.myaccount.adapter.KYCAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.api.EmailVerifyRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.EmailVerifyResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.myaccount.api.ProfilePictureRequest;
import com.advira.advirafarm.buyer.ui.myaccount.api.ProfilePictureResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.profile.BusinessDetailsActivity;
import com.advira.advirafarm.buyer.ui.registration.profile.api.me.KycInfo;
import com.advira.advirafarm.buyer.ui.registration.profile.api.me.MeResponse;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.white.progressview.HorizontalProgressView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountfragmentGuest extends BaseContainerFragment implements IConsts {


    public static final int REQUEST_IMAGE_1 = 100;
    AlertDialog.Builder builder;
    KYCAdapter kycAdapter;
    private View rootView;
    private Context mContext;
    private Animation animationUp;
    private Animation animationDown;
    private RelativeLayout rl_personaledit;
    private RelativeLayout rl_businessedit;
    private RelativeLayout rl_kycedit;
    private TextView tv_mydetailsl;
    private TextView tv_username, tv_content, tv_mobileverifychk;
    private TextView tv_mobile;
    private RelativeLayout rl_addbusiness, rl_username;
    public CircleImageView profile_image;
    private ImageView img_add, sample_image;
    private HorizontalProgressView hp_profileper;
    private TextView et_email;
    private TextView tv_emailverify;
    private RelativeLayout rl_emailview, rl_footer,rl_email;
    private RelativeLayout ll_emailview;
    private TextView tv_emailview;
    private TextView tv_emailviewval;
    private TextView tv_emailverifychk;
    private String base64String1 = "";
    private Uri uri1;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView tv_dobval;
    private TextView tv_genderval;
    private TextView tv_unitval;
    private TextView tv_gstval;
    private TextView tv_fssaival;
    private TextView tv_fssaiexpiryval;
    private TextView tv_landlineval;
    private TextView tv_corporateaddressval;
    private TextView tv_companymailval;
    private TextView tv_citypin;
    private LinearLayout ll_ownername;
    private TextView txt_business;
    private LinearLayout ll_business, ll_dob, ll_gender;
    private RelativeLayout rl_business2;
    private TextView txt_kyc;
    private TextView tv_companypanval;
    private RecyclerView recyclerView;
    private List<KycInfo> kycInfoList;
    private String personalEdit = "1";
    private String businessEdit = "1";
    private String kycEdit = "1";
    private ImageView tv_nogstview,image_frame;
    private TextView tv_nogstname;
    private String nogsttext = "";
    private String nogstname = "";
    private RelativeLayout rl_businessdetails;
    private RelativeLayout rl_kyc;
    private View v_line5,v_line6,v_line111,v_line101;
    String profilestatus="Inactive";
    private RelativeLayout ll_mobileview;
    private Button btn_login;


    public static MyAccountfragmentGuest newInstance() {
        MyAccountfragmentGuest fragment = new MyAccountfragmentGuest();
        return fragment;
    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_myaccounthome, container, false);

            ButterKnife.bind(this, rootView);

            initUI();

            animationUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
            animationDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);


            rl_personaledit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), PersonalDetailsEditActivity.class);
                    startActivityForResult(i, 101);


                }
            });

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), OneTapLogin.class);
                    startActivityForResult(i, 101);
                }
            });

            /*tv_emailverify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!et_email.getText().toString().trim().matches(emailpattern)) {
                        et_email.requestFocus();
                        et_email.setError("Enter a valid Email!");
                    } else {
                        EmailVerification();
                    }
                }
            });*/

            rl_businessedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPrefUtil.setAddressType(mContext, SHARED_PREF_ADDRESSTYPE, "");

                    Intent i = new Intent(getActivity(), BusinessDetailsEditActivity.class);
                    startActivityForResult(i, 101);

                }
            });
            rl_addbusiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //SharedPrefUtil.setAddressType(mContext, SHARED_PREF_ADDRESSTYPE, "");

                    Intent i = new Intent(getActivity(), BusinessDetailsActivity.class);
                    startActivityForResult(i, 101);
                    startActivity(i);

                }
            });


            rl_kycedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), DocumentUploadActivity.class);
                    startActivity(intent);

                }
            });

            tv_nogstview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShowNoGSTalert();

                }
            });


            // region ------------**************  tv_gst_header.setOnClickListener
            txt_business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_business.isShown()) {
                        ll_business.setVisibility(View.GONE);
                        ll_business.startAnimation(animationUp);
                        rl_business2.setVisibility(View.GONE);
                        rl_business2.startAnimation(animationUp);
                        rl_businessedit.setVisibility(View.GONE);

                        txt_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_business_details_icon, 0, R.drawable.ic_arrow_right_blue_24dp, 0);


                    } else {
                        ll_business.setVisibility(View.VISIBLE);
                        ll_business.startAnimation(animationDown);
                        rl_business2.setVisibility(View.VISIBLE);
                        rl_business2.startAnimation(animationDown);

                        if (businessEdit.equalsIgnoreCase("0")) {
                            rl_businessedit.setVisibility(View.VISIBLE);
                        }

                        txt_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_business_details_icon, 0, R.drawable.ic_arrow_down_blue_24dp, 0);

                    }
                }
            });


            // region ------------**************  tv_gst_header.setOnClickListener
            txt_kyc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerView.isShown()) {
                        recyclerView.setVisibility(View.GONE);
                        recyclerView.startAnimation(animationUp);
                        rl_kycedit.setVisibility(View.GONE);


                        txt_kyc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_kyc_icon, 0, R.drawable.ic_arrow_right_blue_24dp, 0);

                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.startAnimation(animationDown);

                        if (kycEdit.equalsIgnoreCase("0")) {
                            rl_kycedit.setVisibility(View.VISIBLE);
                        }


                        txt_kyc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_kyc_icon, 0, R.drawable.ic_arrow_down_blue_24dp, 0);


                    }
                }
            });

        }
        return rootView;

    }

    private void initUI() {

        mContext = getActivity();

        tv_dobval = rootView.findViewById(R.id.tv_dobval);
        tv_dobval.setVisibility(View.GONE);

        tv_genderval = rootView.findViewById(R.id.tv_genderval);
        tv_genderval.setVisibility(View.GONE);

        tv_companypanval = rootView.findViewById(R.id.tv_companypanval);
        tv_companypanval.setVisibility(View.GONE);

        tv_unitval = rootView.findViewById(R.id.tv_unitval);
        tv_unitval.setVisibility(View.GONE);

        tv_mobileverifychk=rootView.findViewById(R.id.tv_mobileverifychk);
        tv_gstval = rootView.findViewById(R.id.tv_gstval);
        tv_fssaival = rootView.findViewById(R.id.tv_fssaival);
        tv_fssaiexpiryval = rootView.findViewById(R.id.tv_fssaiexpiryval);
        tv_landlineval = rootView.findViewById(R.id.tv_landlineval);
        tv_corporateaddressval = rootView.findViewById(R.id.tv_corporateaddressval);
        tv_companymailval = rootView.findViewById(R.id.tv_companymailval);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        txt_business = rootView.findViewById(R.id.txt_business);
        ll_business = rootView.findViewById(R.id.ll_business);
        rl_business2 = rootView.findViewById(R.id.rl_business2);
        txt_kyc = rootView.findViewById(R.id.txt_kyc);
        rl_personaledit = rootView.findViewById(R.id.rl_personaledit);
        rl_businessedit = rootView.findViewById(R.id.rl_businessedit);
        rl_kycedit = rootView.findViewById(R.id.rl_kycedit);
        tv_username = rootView.findViewById(R.id.tv_username);
        tv_username.setVisibility(View.GONE);
        tv_mobile = rootView.findViewById(R.id.tv_mobile);
        profile_image = rootView.findViewById(R.id.profile_image);
        sample_image=rootView.findViewById(R.id.sample_image);
        hp_profileper = rootView.findViewById(R.id.hp_profileper);
        img_add = rootView.findViewById(R.id.img_add);
        et_email = rootView.findViewById(R.id.et_email);
        tv_emailverify = rootView.findViewById(R.id.tv_emailverify);
        rl_emailview = rootView.findViewById(R.id.rl_emailview);
        rl_email=rootView.findViewById(R.id.rl_email);
        rl_email.setVisibility(View.GONE);
        ll_emailview = rootView.findViewById(R.id.ll_emailview);
        tv_emailviewval = rootView.findViewById(R.id.tv_emailviewval);
        tv_emailverifychk = rootView.findViewById(R.id.tv_emailverifychk);
        tv_citypin = rootView.findViewById(R.id.tv_citypin);
        tv_nogstview = rootView.findViewById(R.id.tv_nogstview);
        rl_businessdetails = rootView.findViewById(R.id.rl_businessdetails);
        rl_kyc = rootView.findViewById(R.id.rl_kyc);
        v_line5 = rootView.findViewById(R.id.v_line5);
        v_line6 = rootView.findViewById(R.id.v_line6);
        v_line111 = rootView.findViewById(R.id.v_line111);
        v_line101 = rootView.findViewById(R.id.v_line101);
        rl_addbusiness=rootView.findViewById(R.id.rl_addbusiness);
        rl_footer=rootView.findViewById(R.id.rl_footer);
        ll_mobileview=rootView.findViewById(R.id.ll_mobileview);
        tv_content=rootView.findViewById(R.id.tv_content);
        btn_login=rootView.findViewById(R.id.btn_login);
        ll_gender=rootView.findViewById(R.id.ll_gender);
        ll_dob=rootView.findViewById(R.id.ll_dob);
        image_frame=rootView.findViewById(R.id.image_frame);
        image_frame.setVisibility(View.GONE);
        //rl_username=rootView.findViewById(R.id.rl_username);


        rl_personaledit.setVisibility(View.GONE);
        img_add.setVisibility(View.GONE);
        ll_mobileview.setVisibility(View.GONE);
        ll_gender.setVisibility(View.GONE);
        ll_dob.setVisibility(View.GONE);
        rl_emailview.setVisibility(View.GONE);
        ll_emailview.setVisibility(View.GONE);
        rl_footer.setVisibility(View.VISIBLE);
        tv_content.setVisibility(View.VISIBLE);
        //rl_username.setVisibility(View.GONE);
        tv_mobileverifychk.setVisibility(View.GONE);
        profile_image.setVisibility(View.GONE);
        sample_image.setVisibility(View.VISIBLE);
        //profile_image.setBackground(getResources().getDrawable(R.drawable.splash_logo));

        String name = SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, "");
        String mobile = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");
        String profilepic = SharedPrefUtil.getUserProfilePic(mContext, SHARED_PREF_ProfilePic, "");
        //Integer profileper = Integer.valueOf(SharedPrefUtil.getProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, "0"));
        String ordercount = SharedPrefUtil.getOrderCount(mContext, SHARED_PREF_OrderCount, "0");
        String headeraddress = SharedPrefUtil.getHeaderAddress(mContext, SHARED_PREF_HeaderAddress, "Select Address");
        String userstatus = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

        /*if (userstatus.equalsIgnoreCase("inactive")) {
            v_line111.setVisibility(View.VISIBLE);
            rl_addbusiness.setVisibility(View.VISIBLE);
            v_line101.setVisibility(View.VISIBLE);
            v_line6.setVisibility(View.GONE);
            rl_businessdetails.setVisibility(View.GONE);
            v_line5.setVisibility(View.GONE);
            rl_kyc.setVisibility(View.GONE);

        } else {
            v_line111.setVisibility(View.GONE);
            rl_addbusiness.setVisibility(View.GONE);
            v_line101.setVisibility(View.GONE);
            v_line6.setVisibility(View.VISIBLE);
            rl_businessdetails.setVisibility(View.VISIBLE);
            v_line5.setVisibility(View.VISIBLE);
            rl_kyc.setVisibility(View.VISIBLE);
        }*/

        try {
            headeraddress = headeraddress.replace("Deliver to ", "");

            tv_citypin.setText(headeraddress);
            tv_citypin.setVisibility(View.GONE);
        } catch (Exception ex) {

        }


        //hp_profileper.setProgressInTime(0, profileper, 1000);
        hp_profileper.setVisibility(View.GONE);

        tv_username.setText(name);
        tv_content.setText("To see your Account Details. Please Login/Signup first.");
        tv_mobile.setText("+91 " + mobile);


        if (profilepic.length() > 5) {
            Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).resize(500,0).error(R.drawable.splash_logo).into(profile_image);
        }
       /* else
        {
            Singleton.getInstance().showLongToast(mContext, "Image Should not be greater than 1MB");
        }*/

        //PopulatePersonalDetails();

    }

    private void EmailVerification() {


        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        EmailVerifyRequest emailVerifyRequest = new EmailVerifyRequest();
        emailVerifyRequest.setEmail(et_email.getText().toString());

        try {
            Call<EmailVerifyResponse> call = RetrofitUrlConnection.loadJSON(token).verifyemail(emailVerifyRequest);

            call.enqueue(new Callback<EmailVerifyResponse>() {
                @Override
                public void onResponse(Call<EmailVerifyResponse> call, Response<EmailVerifyResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());


                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                    Utilities.dismissDialog();

                    PopulatePersonalDetails();
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

    private void PopulatePersonalDetails() {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, null);
        Call<MeResponse> call = RetrofitUrlConnection.loadJSON(token).me();

        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    if (response.body().getBasicInfo().size() > 0) {

                        et_email.setText(response.body().getBasicInfo().get(0).getEmail());
                        String image_url = response.body().getProfilePicture().getProfileimageUrl();
                        String profileper = response.body().getProfileCompletion().toString();
                        String emailverify = response.body().getBasicInfo().get(0).getEmailVerified().toString();

                        String credit_availed = response.body().getBasicInfo().get(0).getCreditAvailed();
                        SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, credit_availed);


                        rl_emailview = rootView.findViewById(R.id.rl_emailview);
                        ll_emailview = rootView.findViewById(R.id.ll_emailview);
                        tv_emailviewval = rootView.findViewById(R.id.tv_emailviewval);
                        rl_emailview.setVisibility(View.GONE);


                        if (emailverify.equalsIgnoreCase("1")) {
                            ll_emailview.setVisibility(View.VISIBLE);
                            tv_emailviewval.setText(response.body().getBasicInfo().get(0).getEmail());
                            tv_emailverifychk.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_verified_icon, 0, 0, 0);
                            tv_emailverifychk.setText("Verified");
                            tv_emailverifychk.setVisibility(View.GONE);
                            tv_emailverifychk.setTextColor(getResources().getColor(R.color.colorGreen));


                            tv_emailverify.setText("Verified");
                            tv_emailverify.setVisibility(View.GONE);
                            tv_emailverify.setVisibility(View.GONE);
                            tv_emailverify.setTextColor(getResources().getColor(R.color.colorGreen));
                            tv_emailverify.setEnabled(false);
                            et_email.setEnabled(false);

                            tv_emailverify.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_verified_icon, 0, 0, 0);


                            rl_emailview.setVisibility(View.GONE);
                        } else {

                            ll_emailview.setVisibility(View.GONE);
                            tv_emailviewval.setText(response.body().getBasicInfo().get(0).getEmail());
                            tv_emailviewval.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_not_verified_icon, 0, 0, 0);
                            tv_emailverifychk.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_not_verified_icon, 0, 0, 0);
                            tv_emailverifychk.setText("Not Verified");
                            tv_emailverifychk.setVisibility(View.GONE);
                            tv_emailverifychk.setTextColor(getResources().getColor(R.color.colorOrageNew));


                            tv_emailverify.setText("Verify Now");
                            tv_emailverify.setVisibility(View.GONE);
                            tv_emailverify.setEnabled(true);
                            tv_emailverify.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_not_verified_icon, 0, 0, 0);
                            tv_emailverify.setTextColor(getResources().getColor(R.color.colorOrageNew));
                            rl_emailview.setVisibility(View.GONE);
                        }


                        SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, image_url);
                        SharedPrefUtil.setProfilePercent(mContext, SHARED_PREF_PROFILEPERCENTAGE, profileper);


                        if (image_url.length() > 5) {
                            Picasso.with(mContext).load(image_url).placeholder(R.drawable.progress_animation).resize(500,0).error(R.drawable.splash_logo).into(profile_image);

                        }
                        /*else{
                            Singleton.getInstance().showLongToast(mContext, "Image Should not be greater than 1MB");
                        }*/


                        tv_dobval.setText(response.body().getBasicInfo().get(0).getUserDob());
                        tv_genderval.setText(response.body().getBasicInfo().get(0).getGender());

                        String pan = "";
                        String maskpan = "";
                        String adhaar = "";
                        String maskadhaar = "";


                        if (response.body().getBusinessProfile().size() > 0) {

                            rl_businessdetails.setVisibility(View.VISIBLE);
                            rl_kyc.setVisibility(View.VISIBLE);
                            v_line5.setVisibility(View.VISIBLE);
                            v_line6.setVisibility(View.VISIBLE);
                            v_line111.setVisibility(View.GONE);
                            rl_addbusiness.setVisibility(View.GONE);
                            v_line101.setVisibility(View.GONE);


                          /*  String landline = response.body().getBusinessProfile().get(0).getCompanyContactStdcode() +
                                    " - "
                                    + response.body().getBusinessProfile().get(0).getCompanyContactLandlineno();
                            */
                            String landline = response.body().getBusinessProfile().get(0).getCompanyContactLandlineno();


                            String businessaddress = response.body().getBusinessProfile().get(0).getBusinessAddress1() + " " +
                                    response.body().getBusinessProfile().get(0).getBusinessAddress2() + " " +
                                    response.body().getBusinessProfile().get(0).getBusinessAddressCityName() + ", " +
                                    response.body().getBusinessProfile().get(0).getBusinessAddressStateName() + " - " +
                                    response.body().getBusinessProfile().get(0).getBusinessAddressPinno();

                            tv_companymailval.setText(response.body().getBusinessProfile().get(0).getCompanyEmailId());
                            tv_unitval.setText(response.body().getBusinessProfile().get(0).getCompanyName());
                            tv_companypanval.setText(response.body().getBusinessProfile().get(0).getCompanyPanNo());

                            String nogst = response.body().getBusinessProfile().get(0).getNoGstDeclaration();

                            if (nogst.equalsIgnoreCase("yes")) {

                                tv_gstval.setText(response.body().getBusinessProfile().get(0).getCompanyGstNo());

                                tv_nogstview.setVisibility(View.INVISIBLE);

                            } else if (nogst.equalsIgnoreCase("no")) {

                                nogsttext = response.body().getBusinessProfile().get(0).getNoGstDeclarationAcceptance();
                                nogstname = response.body().getBusinessProfile().get(0).getNoGstDeclarationAcceptanceName();

                                tv_gstval.setText("No GST Declared");
                                tv_nogstview.setVisibility(View.VISIBLE);

                            }


                            tv_fssaival.setText(response.body().getBusinessProfile().get(0).getFssaiNo());
                            tv_fssaiexpiryval.setText(response.body().getBusinessProfile().get(0).getFssaiLicenseExpiry());
                            tv_landlineval.setText(landline);
                            tv_corporateaddressval.setText(businessaddress);


                        } else {
                            rl_businessdetails.setVisibility(View.GONE);
                            rl_kyc.setVisibility(View.GONE);
                            v_line5.setVisibility(View.GONE);
                            v_line6.setVisibility(View.GONE);
                            v_line111.setVisibility(View.GONE);
                            rl_addbusiness.setVisibility(View.VISIBLE);
                            v_line101.setVisibility(View.GONE);


                        }

                        kycInfoList = new ArrayList<>();
                        kycAdapter = new KYCAdapter(mContext, kycInfoList);

                        List<KycInfo> mListData = response.body().getKycInfo();

                        if (mListData != null && mListData.size() > 0) {

                            kycInfoList.addAll(mListData);

                        }

                        recyclerView.setAdapter(kycAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                        CheckProfile();
                    }

                } else {
                    Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });
    }

    private void ShowNoGSTalert() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.alert_nogst, null);

        TextView textview = (TextView) view.findViewById(R.id.textmsg);
        EditText et_nogstname = (EditText) view.findViewById(R.id.et_nogstname);
        TextView tv_nogstname = (TextView) view.findViewById(R.id.tv_nogstname);


        et_nogstname.setVisibility(View.GONE);
        textview.setText(nogsttext);
        tv_nogstname.setVisibility(View.VISIBLE);
        tv_nogstname.setText(nogstname);

        final AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("NO GST Declaration !!")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();

            }
        });


        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

    }

    @OnClick({R.id.img_add})
    void onProfileImageClick1() {
        Dexter.withActivity(getActivity())
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
            public void onResponse(@NotNull Call<ProfilePictureResponse> call, @NotNull Response<ProfilePictureResponse> response) {

                if (response.body() != null) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        String profilepic = response.body().getProfilePicture().getProfileimageUrl();

                        SharedPrefUtil.setUserProfilePic(mContext, SHARED_PREF_ProfilePic, profilepic);

                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());

                        try{
                            if (profilepic.length() > 5) {
                                Picasso.with(mContext).load(profilepic).placeholder(R.drawable.progress_animation).resize(500,0).error(R.drawable.splash_logo).into(MainActivityNav.profile_image);
                            }
                            /*else{
                                //Picasso.with(getContext()).setLoggingEnabled(true);
                                Singleton.getInstance().showLongToast(mContext, "Image Should not be greater than 1MB");
                            }*/
                        }
                        catch (Exception ex)
                        {

                        }
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    } else {
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }

                }
                //Utilities.dismissDialog();
                else {
                    Singleton.getInstance().showLongToast(mContext, "Image not Upload Successfully");
                    alert("Please Make Sure image should be less than 100 KB !! ");
                    //Picasso.with(mContext).load(R.drawable.image_not_available).into(MainActivityNav.profile_image);
                    Picasso.with(mContext).load(R.drawable.splash_logo).into(profile_image);
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
        ImagePickerActivity.showImagePickerOptions1(getActivity(), new ImagePickerActivity.PickerOptionListener() {
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
        if (requestCode == REQUEST_IMAGE_1) {
            if (resultCode == Activity.RESULT_OK) {
                uri1 = data.getParcelableExtra("path");


                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri1);
                    base64String1 = convert(bitmap);
                    base64String1 = base64String1.replaceAll("\n", "");

                    Picasso.with(mContext).load(uri1.toString()).resize(1080, 600)
                            /*.listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    // log exception
                                    Log.e("xmx1","Error "+e.toString());
                            return false;// important to return false so the error placeholder can be placed
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    Log.e("xmx1","Image Upload Successfully ");
                            return false;
                                }
                            })*/
                            .into(profile_image);

                    profile_image.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.transparent));
                    //saveProfilePicLocal(uri1.toString());
                    ProfileImageUpload();

                } catch (IOException e) {
                    //Log.e("TAG", "Error loading image", e);
                    //e.printStackTrace();
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void CheckProfile() {

        // Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


        try {

            Call<IsUserVerifiedResponse> call = RetrofitUrlConnection.loadJSON(token).isuserverified();

            call.enqueue(new Callback<IsUserVerifiedResponse>() {
                @Override
                public void onResponse(Call<IsUserVerifiedResponse> call, Response<IsUserVerifiedResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        personalEdit = response.body().getPersonalProfileStatus();
                        businessEdit = response.body().getBusinessProfileStatus();
                        kycEdit = response.body().getKycDocumentStatus();




                        if (response.body().getBusinessProfileStatus().equalsIgnoreCase("1")) {
                            rl_businessedit.setVisibility(View.GONE);
                        } else {
                            rl_businessedit.setVisibility(View.VISIBLE);
                        }


                        if (response.body().getKycDocumentStatus().equalsIgnoreCase("1")) {
                            rl_kycedit.setVisibility(View.GONE);
                        } else {
                            rl_kycedit.setVisibility(View.VISIBLE);
                        }


                    } else {

                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<IsUserVerifiedResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onResume() {
        super.onResume();

    }
    private void saveProfilePicLocal(String uri){
        SharedPreferences pref = getContext().getSharedPreferences("Profile_Image", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("profile_uri", uri); // Storing string
        editor.commit(); // commit changes
    }
    public void alert(String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//set title
        alertDialog.setTitle("Message");
//set message
        alertDialog.setMessage(message);
//set positive button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //set what would happen when positive button is clicked
                alertDialog.setCancelable(true);
            }
        });
        alertDialog.show();
    }
}

