package com.advira.advirafarm.buyer.ui.payment.paylaterpayment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.order.OrderPlacedActivity;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddressDLUploadActivity;
import com.advira.advirafarm.buyer.ui.order.api.PaymentRequest;
import com.advira.advirafarm.buyer.ui.order.api.PaymentResponse;
import com.advira.advirafarm.buyer.utility.MinMaxFilter;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartialPaymentActivity extends AppCompatActivity implements IConsts {

    String totalval = "";
    private Button btn_continueshopping;
    private Context mContext;
    private TextView tv_paymentpending;
    private TextView tv_msg;
    private RelativeLayout rl_back;
    private ImageView chk;
    private RadioGroup rg_paymenttype;
    private EditText et_paymentamount;
    private String orderid = "";
    private String orderno = "";
    private String total = "";
    private String minimumamount = "";
    private String paymenttype = "full";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpayment);

        initUI();


        btn_continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_paymentamount.getText().toString().trim().isEmpty()) {

                    et_paymentamount.requestFocus();
                    et_paymentamount.setError("Payment amount is required!");

                } else {

                    double m = Double.parseDouble(minimumamount);

                    if(paymenttype.equalsIgnoreCase("partial"))
                    {

                        String maxval = et_paymentamount.getText().toString();
                        double d = Double.parseDouble(maxval);

                        if(d<m)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(PartialPaymentActivity.this);
                            alert.setTitle("Alert!!");
                            alert.setMessage("Partial amount should be greater then minimum payable amount.");
                            alert.setPositiveButton("OK",null);
                            alert.show();
                            //Singleton.getInstance().showShortToast(mContext, "Partial amount should be greater then minimum payable amount");
                        }
                        else
                        {
                            PaymentDetails();
                        }

                    }
                    else {

                        PaymentDetails();
                    }

                }
            }


        });


        rg_paymenttype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                int selectedId = rg_paymenttype.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);

                if (radioButton.getText().toString().equalsIgnoreCase("full")) {
                    et_paymentamount.setFilters(new InputFilter[]{new MinMaxFilter(totalval, totalval)});
                    et_paymentamount.setText(totalval);
                    et_paymentamount.setEnabled(false);
                    paymenttype = "full";
                } else if (radioButton.getText().toString().equalsIgnoreCase("partial")) {

                    et_paymentamount.setEnabled(true);
                    et_paymentamount.setText("");
                    et_paymentamount.setFilters(new InputFilter[]{new MinMaxFilter("0", totalval)});
                    paymenttype = "partial";

                } else if (radioButton.getText().toString().equalsIgnoreCase("minimum")) {
                    et_paymentamount.setFilters(new InputFilter[]{new MinMaxFilter(minimumamount, minimumamount)});
                    et_paymentamount.setEnabled(false);
                    et_paymentamount.setText(minimumamount);
                    paymenttype = "minimum";

                }

            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PartialPaymentActivity.this.finish();
            }
        });


    }

    private void initUI() {
        mContext = PartialPaymentActivity.this;
        btn_continueshopping = findViewById(R.id.btn_continueshopping);
        tv_paymentpending = findViewById(R.id.tv_paymentpending);
        tv_msg = findViewById(R.id.tv_msg);
        rg_paymenttype = findViewById(R.id.rg_paymenttype);
        et_paymentamount = findViewById(R.id.et_paymentamount);
        rl_back = findViewById(R.id.rl_back);


        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            total = extras.getString("grandtotal");
            Log.d("mydueamount: ", String.valueOf(total));
            orderid = extras.getString("orderid");
            orderno = extras.getString("orderno");
            minimumamount = extras.getString("minimumamount");
        }

        try {

            tv_paymentpending.setText("Payment Pending " + total);
            tv_msg.setText("Please add payment details for your Order #" + orderno);

            String maxval = total.replace("₹ ", "");
            double d = Double.parseDouble(maxval);
            int max = (int) d;
            totalval = String.valueOf(max);


            String minval = minimumamount.replace("₹ ", "");
            double m = Double.parseDouble(minval);
            int min = (int) m;
            minimumamount = String.valueOf(min);



        } catch (Exception ex) {

        }
        et_paymentamount.setText(totalval);
        et_paymentamount.setEnabled(false);


    }


    private void PaymentDetails() {

        Intent i = new Intent();
        i.setClass(PartialPaymentActivity.this, PLPaymentOption.class);
        i.putExtra("orderid", orderid);
        i.putExtra("grandtotal", et_paymentamount.getText().toString());
        startActivity(i);
    }

    @Override
    public void onBackPressed() {

    }


}