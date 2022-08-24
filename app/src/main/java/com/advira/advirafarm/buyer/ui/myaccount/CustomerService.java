package com.advira.advirafarm.buyer.ui.myaccount;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;

import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;

public class CustomerService extends BaseContainerFragment implements IConsts {

    ImageView iv_call,iv_mail,iv_whatsapp;
    private View rootView;
    private Context mContext;


    public static CustomerService newInstance() {
        CustomerService fragment = new CustomerService();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_customer_service, container, false);
            initUI();
        }
        return rootView;
    }


    private void initUI() {
        mContext = getActivity();
        iv_call=rootView.findViewById(R.id.iv_call);
        iv_mail=rootView.findViewById(R.id.iv_mail);
        iv_whatsapp=rootView.findViewById(R.id.iv_whatsapp);

        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+911292982250"));
                startActivity(callIntent);
            }
        });

        iv_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","info@adviratech.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "message");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        iv_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String digits = "\\d+";
                String mob_num = "9667072941";
                if (mob_num.matches(digits))
                {
                    try {
                        //linking for whatsapp
                        Uri uri = Uri.parse("https://wa.me/+91" + mob_num);
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }
                    catch (ActivityNotFoundException e){
                        e.printStackTrace();
                        //if you're in anonymous class pass context like "YourActivity.this"
                        Toast.makeText(mContext, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


}