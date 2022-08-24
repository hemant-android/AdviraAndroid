package com.advira.advirafarm.buyer.ui.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.advira.advirafarm.buyer.MainActivity;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;


public class RegistrationCompleteActivity extends Activity {

    private Button btn_continueshopping;
    private Context mContext;
    private ImageView chk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationdone);

        initUI();

        ((Animatable) chk.getDrawable()).start();


        btn_continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegistrationCompleteActivity.this, MainActivityNav.class);
                RegistrationCompleteActivity.this.startActivity(mainIntent);
                RegistrationCompleteActivity.this.finish();
            }
        });
    }

    private void initUI() {
        mContext = RegistrationCompleteActivity.this;
        btn_continueshopping = findViewById(R.id.btn_continueshopping);
        chk = findViewById(R.id.chk);
    }

    @Override
    public void onBackPressed() {

    }


}
