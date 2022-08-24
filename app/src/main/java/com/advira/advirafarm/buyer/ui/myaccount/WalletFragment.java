package com.advira.advirafarm.buyer.ui.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.wallet.AddMoney;
import com.advira.advirafarm.buyer.utility.BaseContainerFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends BaseContainerFragment {

    private CardView cvAddmoney;
    private View rootView;
    private Context mContext;
    private EditText et_rupees;
    public static TextView tvBalance;
    String addmoney="";
    public static String Amount="";

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_my_wallet, container, false);
            initUI();
        }
        return rootView;
        //return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    private void initUI() {
        mContext = getActivity();
        cvAddmoney=rootView.findViewById(R.id.cvAddMoney);
        et_rupees=rootView.findViewById(R.id.et_rupees);
        tvBalance=rootView.findViewById(R.id.tvBalance);
        tvBalance.setText("₹ " + Amount);


        cvAddmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addmoney=et_rupees.getText().toString().trim();

                Intent i=new Intent();
                i.setClass(mContext, AddMoney.class);
                i.putExtra("addmoney", addmoney);
                i.putExtra("from","wallet");
                startActivity(i);
            }
        });
    }
}