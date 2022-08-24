package com.advira.advirafarm.buyer.ui.cart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.advira.advirafarm.buyer.R;


public class PaymentActivity extends Activity {

    private Context mContext;
    private RelativeLayout rl_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initUI();

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PaymentActivity.this.finish();

            }
        });

    }

    private void initUI() {

        mContext = PaymentActivity.this;
        rl_back = findViewById(R.id.rl_back);
    }


    @Override
    public void onBackPressed() {finish();
        //super.onBackPressed();
        //MyAccountActivity.this.finish();
    }


}
