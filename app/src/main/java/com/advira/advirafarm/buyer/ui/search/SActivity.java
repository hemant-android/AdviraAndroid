package com.advira.advirafarm.buyer.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;

public class SActivity extends AppCompatActivity {

    RelativeLayout rl_header,rl_search;
    TextView tv_noitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sactivity);
    }
}