package com.advira.advirafarm.buyer.ui.order;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
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

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.order.api.PaymentRequest;
import com.advira.advirafarm.buyer.ui.order.api.PaymentResponse;
import com.advira.advirafarm.buyer.utility.MinMaxFilter;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPaymentActivity extends AppCompatActivity implements IConsts {

    private Button btn_continueshopping;
    private Context mContext;
    private TextView tv_paymentpending;
    private TextView tv_msg;
    private RelativeLayout rl_back;
    private ImageView chk;
    private RadioGroup rg_paymenttype;
    private EditText et_paymentamount;
    private String orderid="";
    private String orderno="";
    private String total = "";
    private String paymenttype = "full";
    String totalval ="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpayment);

        initUI();


        btn_continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(paymenttype.equalsIgnoreCase("full") || paymenttype.equalsIgnoreCase("partial")) {
                    if (et_paymentamount.getText().toString().trim().isEmpty()) {

                        et_paymentamount.requestFocus();
                        et_paymentamount.setError("Payment amount is required!");

                    }
                    else
                    {
                        PaymentDetails();
                    }
                }
                else if(paymenttype.equalsIgnoreCase("credit")) {

                    PaymentDetails();
                }



            }
        });


        rg_paymenttype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                int selectedId = rg_paymenttype.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);

                if(radioButton.getText().toString().equalsIgnoreCase("full"))
                {
                    et_paymentamount.setFilters(new InputFilter[]{new MinMaxFilter(totalval, totalval)});
                    et_paymentamount.setText(totalval);
                    et_paymentamount.setEnabled(false);
                    paymenttype = "full";
                }
                else if(radioButton.getText().toString().equalsIgnoreCase("partial"))
                {

                    et_paymentamount.setEnabled(true);
                    et_paymentamount.setText("");
                    et_paymentamount.setFilters(new InputFilter[]{new MinMaxFilter("1", totalval)});
                    paymenttype = "partial";

                }
                else if(radioButton.getText().toString().equalsIgnoreCase("minimum"))
                {

                    et_paymentamount.setFilters(new InputFilter[]{new MinMaxFilter("0", "0")});
                    et_paymentamount.setEnabled(false);
                    et_paymentamount.setText("0");
                    paymenttype = "credit";

                }

            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddPaymentActivity.this.finish();
            }
        });



    }

    private void initUI() {
        mContext = AddPaymentActivity.this;
        btn_continueshopping = findViewById(R.id.btn_continueshopping);
        tv_paymentpending = findViewById(R.id.tv_paymentpending);
        tv_msg = findViewById(R.id.tv_msg);
        rg_paymenttype = findViewById(R.id.rg_paymenttype);
        et_paymentamount = findViewById(R.id.et_paymentamount);
        rl_back = findViewById(R.id.rl_back);



        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            total = extras.getString("total");
            orderid  = extras.getString("orderid");
            orderno  = extras.getString("orderno");


        }

        try
        {

        tv_paymentpending.setText("Payment Pending " + total);
        tv_msg.setText("Please add payment details for your Order #"+orderno );

        String maxval = total.replace("₹ ", "");
        double d = Double.parseDouble(maxval);
        int max = (int) d;
        totalval = String.valueOf(max);

        }
        catch (Exception ex)
        {

        }
        et_paymentamount.setText(totalval);
        et_paymentamount.setEnabled(false);



    }



    private void PaymentDetails() {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        int selectedId = rg_paymenttype.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        String mode="";

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderid);
        paymentRequest.setPaymentAmount(et_paymentamount.getText().toString().trim());
        paymentRequest.setPaymentMode(mode);
        paymentRequest.setPaymentType(radioButton.getText().toString());

        try {

            Call<PaymentResponse> call = RetrofitUrlConnection.loadJSON(token).addpaymentdetails(paymentRequest);

            call.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        AddPaymentActivity.this.finish();


                    } else {
                        Utilities.dismissDialog();
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

    }


}
