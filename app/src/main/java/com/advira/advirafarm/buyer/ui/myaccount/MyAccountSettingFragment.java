package com.advira.advirafarm.buyer.ui.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.login.WebViewActivity;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;

import butterknife.ButterKnife;


public class MyAccountSettingFragment extends BaseContainerFragment implements IConsts {

    public static MyAccountSettingFragment newInstance() {
        MyAccountSettingFragment fragment = new MyAccountSettingFragment();
        return fragment;
    }

    private View rootView;
    private Context mContext;
    private RelativeLayout rl_myacc;
    private CardView rl_changepass;
    private String mobile="";

    private CardView cv_tnc;
    private CardView cv_privacy;
    private CardView cv_return;
    private CardView cv_faq;
    private CardView cv_aboutus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_accountsettings, container, false);
            ButterKnife.bind(this, rootView);
            initUI();



            rl_changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent();
                    i.setClass(mContext, ChangeMyPaswordActivity.class);
                    i.putExtra("mobile",mobile);
                    startActivity(i);

                }
            });


            cv_tnc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent();
                    i.setClass(mContext, WebViewActivity.class);
                    i.putExtra("header","Terms & Conditions");
                    i.putExtra("url","https://www.advira.in/terms-conditions-app.php");
                    startActivity(i);
                }
            });

            cv_privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent();
                    i.setClass(mContext, WebViewActivity.class);
                    i.putExtra("header","Privacy Policy");
                    i.putExtra("url","https://www.advira.in/privacy-policy-app.php");
                    startActivity(i);
                }
            });

            cv_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent();
                    i.setClass(mContext, WebViewActivity.class);
                    i.putExtra("header","Return and Cancellation Policy");
                    i.putExtra("url","https://www.advira.in/return-policy.php");
                    startActivity(i);
                }
            });

            cv_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent();
                    i.setClass(mContext, WebViewActivity.class);
                    i.putExtra("header","FAQ");
                    i.putExtra("url","https://www.advira.in/faq.php");
                    startActivity(i);
                }
            });

            cv_aboutus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent();
                    i.setClass(mContext, WebViewActivity.class);
                    i.putExtra("header","About Us");
                    i.putExtra("url","https://www.advira.in/about-app.php");
                    startActivity(i);
                }
            });

        }
        return rootView;

    }



    private void initUI() {

        mContext = getActivity();
        rl_changepass = rootView.findViewById(R.id.rl_changepass);
        cv_tnc = rootView.findViewById(R.id.cv_tnc);
        cv_privacy = rootView.findViewById(R.id.cv_privacy);
        rl_myacc = rootView.findViewById(R.id.rl_myacc);
        rl_myacc.setVisibility(View.GONE);

        cv_return = rootView.findViewById(R.id.cv_return);
        cv_faq = rootView.findViewById(R.id.cv_faq);
        cv_aboutus = rootView.findViewById(R.id.cv_aboutus);


        mobile = SharedPrefUtil.getUserMobile(mContext, SHARED_PREF_UserMobile, "");

    }




}
