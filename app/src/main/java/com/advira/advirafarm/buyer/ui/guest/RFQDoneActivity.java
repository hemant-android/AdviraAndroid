package com.advira.advirafarm.buyer.ui.guest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;

public class RFQDoneActivity extends Activity {

    private Button btn_continueshopping;
    private Context mContext;
    private ImageView chk;
    private TextView tv_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpdone);

        initUI();

        ((Animatable) chk.getDrawable()).start();


        btn_continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RFQDoneActivity.this, MainActivityGuestNav.class);
                RFQDoneActivity.this.startActivity(mainIntent);
                RFQDoneActivity.this.finish();
            }
        });
    }

    private void initUI() {
        mContext = RFQDoneActivity.this;
        btn_continueshopping = findViewById(R.id.btn_continueshopping);
        chk = findViewById(R.id.chk);
        tv_msg=findViewById(R.id.tv_msg);
        tv_msg.setText("Thank you for verifying your mobile. Our team will get back to you soon.");
    }

    @Override
    public void onBackPressed() {

    }


}
