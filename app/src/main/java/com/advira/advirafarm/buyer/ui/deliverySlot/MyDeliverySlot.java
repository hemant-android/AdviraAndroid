package com.advira.advirafarm.buyer.ui.deliverySlot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;

public class MyDeliverySlot extends AppCompatActivity {

    private static final String TAG = "DeliverySlotActivity";
    String paymenttype = "";
    String paymentcode = "";
    String totalval = "";
    String addressid = "";
    String address = "";
    String ordertype = "";
    String totalamount = "";
    String totaltax = "";
    String totaldiscount = "";
    String grandtotal = "";
    String deliverycharges="";

    String orderNote = "Order";
    String customerName = "";
    String customerPhone = "";
    String customerEmail = "";
    String rzrpayorderid = "";
    String receipt = "";
    String rzrpaypayamount = "";
    String paymentref = "";
    String retryorderid = "";
    String from = "";

    private String discountid = "";
    private String discount_coupon_name = "";
    private String discount_type = "";
    private String discount_amount = "";
    private String discount_details = "";
    private String credit_limit = "";
    private String credit_availed = "0";
    private String credit_balance = "";
    String Delivery_time;
    String Delivery_date;


    private HorizontalCalendar horizontalCalendar;
    RelativeLayout rl_back,rl_search, rl_dayslist,rl_footer1;


    RelativeLayout rl_date,rl_today,rl_tomorrow;
    TextView tv_todaydate,tv_todayday,tv_tomdate,tv_tomday;
    RadioGroup rg_timeslot;
    String todaydate,todayday,tomdate,tomday;
    Date today,tomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_delivery_slot);

        Calendar calendar = Calendar.getInstance();
        // get a date to represent "today"
        today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        // now get "tomorrow"
        tomorrow = calendar.getTime();
        //cancelTime= DateFormat.format("dd-MM-yyyy hh:mm:ss", tomorrow).toString();
        todaydate= DateFormat.format("dd",today).toString();
        tomdate=DateFormat.format("dd",tomorrow).toString();
        todayday=DateFormat.format("EEE",today).toString();
        tomday=DateFormat.format("EEE",tomorrow).toString();
        Log.e(TAG, "Init: memberShip_101"+today+"\t"+tomorrow+"\t"+todaydate+"\t"+tomdate+"\t"+todayday+"\t"+tomday );

        Init();

        rg_timeslot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_timeslot.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);

                if(radioButton.getText().toString().equalsIgnoreCase("6:00AM TO 9:00AM")){
                    Delivery_time="7:00AM TO 11:00AM";
                }
                else if(radioButton.getText().toString().equalsIgnoreCase("10:00AM TO 1:00PM")){
                    Delivery_time="12:00PM TO 4:00PM";
                }
                else if(radioButton.getText().toString().equalsIgnoreCase("02:00PM TO 05:00PM")){
                    Delivery_time="5:00PM TO 8:00PM";
                }

            }
        });
    }

    private void Init() {

        rl_back=findViewById(R.id.rl_back);
        rl_search=findViewById(R.id.rl_search);
        rl_footer1=findViewById(R.id.rl_footer1);
        rl_dayslist=findViewById(R.id.rl_dayslist);
        rg_timeslot=findViewById(R.id.rg_timeslot);

        rl_date=findViewById(R.id.rl_date);
        rl_today=findViewById(R.id.rl_today);
        rl_tomorrow=findViewById(R.id.rl_tomorrow);

        tv_todaydate=findViewById(R.id.tv_todaydate);
        tv_todayday=findViewById(R.id.tv_todayday);
        tv_tomdate=findViewById(R.id.tv_tomdate);
        tv_tomday=findViewById(R.id.tv_tomday);

        tv_todaydate.setText(todaydate);
        tv_todayday.setText(todayday);
        tv_tomdate.setText(tomdate);
        tv_tomday.setText(tomday);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            addressid = extras.getString("addressid");
            address = extras.getString("address");
            ordertype = extras.getString("ordertype");
            totalamount = extras.getString("totalamount");
            totaltax = extras.getString("totaltax");
            totaldiscount = extras.getString("totaldiscount");
            grandtotal = extras.getString("grandtotal");
            retryorderid = extras.getString("orderid");
            from = extras.getString("from");

            discountid = extras.getString("discountid");
            discount_coupon_name = extras.getString("discount_coupon_name");
            discount_type = extras.getString("discount_type");
            discount_amount = extras.getString("discount_amount");
            discount_details = extras.getString("discount_details");
            credit_limit = extras.getString("credit_limit");
            credit_availed = extras.getString("credit_availed");
            credit_balance = extras.getString("credit_balance");
            deliverycharges = extras.getString("delivery_charges");

        }
    }
}