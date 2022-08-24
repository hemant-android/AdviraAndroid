package com.advira.advirafarm.buyer.ui.deliverySlot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.skyhope.weekday.WeekDaySelector;
import com.skyhope.weekday.callback.WeekItemClickListener;
import com.skyhope.weekday.data.Holiday;
import com.skyhope.weekday.model.WeekModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;


public class DeliverySlotActivity extends AppCompatActivity {

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


    private HorizontalCalendar horizontalCalendar;
    RelativeLayout rl_back,rl_search, rl_dayslist,rl_footer1;
    Button btn_selectdt;
    RadioGroup rg_timeslot;
    RadioButton rb_slot_1,rb_slot_2,rb_slot_3,rb_slot_4;
    String Delivery_time="";
    String Delivery_date,current_time, end_time,SlotcloseTime1,SlotcloseTime2,SlotcloseTime3;
    Date today,tomorrow, date1;
    String current_Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_slot);

        SlotcloseTime1="06:00";
        SlotcloseTime2="10:00";
        SlotcloseTime3="14:00";



        Calendar calendar=Calendar.getInstance();
        //start before 1 month from now
        Calendar startDate = Calendar.getInstance();
        //startDate.add(Calendar.DATE, 0);
        // end after 1 month from now
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 6);

       /* Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String current_Date = df.format(c);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        c = calendar.getTime();
        String tomorrow_Date = df.format(c);*/
        //today=calendar.getTime();
        //today=calendar.getTime();
        current_time=new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        end_time="17:30";
        boolean isTimegreater=checktimings(current_time,end_time);
        if(isTimegreater==false)
        {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            today = calendar.getTime();
            startDate.add(Calendar.DATE,1);
            //Log.i("Default Date-tomorrow", Delivery_date);

        }
        else{
            today=calendar.getTime();
            startDate.add(Calendar.DATE,0);
            //Log.i("Default Date", Delivery_date);
        }


        // Default Date set to Today.
        //final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calenderView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(7)// Number of Dates cells shown on screen (Recommended 5)
                .dayNameFormat("EEE")	  // WeekDay text format
                .dayNumberFormat("dd")    // Date format
                .monthFormat("MMM") 	  // Month format
                .showDayName(true)	  // Show or Hide dayName text
                .showMonthName(true)	  // Show or Hide month text
                .textColor(Color.LTGRAY, Color.parseColor("#2A882D"))   // Text color for none selected Dates, Text color for selected Date.
                .selectedDateBackground(Color.WHITE)  // Background color of the selected date cell.
                .selectorColor(Color.parseColor("#2A882D"))   // Color of the selection indicator bar (default to colorAccent).
                .defaultSelectedDate(today)
                .build();

        Delivery_date=DateFormat.format("dd-MM-yyyy", today).toString();
        Log.i("Default Date", Delivery_date);



        Calendar toDayCalendar = Calendar.getInstance();
        date1 = toDayCalendar.getTime();
        current_Date=DateFormat.format("dd-MM-yyyy",date1).toString();
        Init();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                // =DateFormat.format("dd-MM-yyyy", date).toString();
                //Toast.makeText(DeliverySlotActivity.this, DateFormat.getDateFormat(DeliverySlotActivity.this).format(date) + " is selected!", Toast.LENGTH_SHORT).show();
                Delivery_date=DateFormat.format("dd-MM-yyyy",date).toString();
                //Toast.makeText(DeliverySlotActivity.this,"selected date="+Delivery_date,Toast.LENGTH_SHORT).show();

                /*Calendar toDayCalendar = Calendar.getInstance();
                Date date1 = toDayCalendar.getTime();
                String current_Date=DateFormat.format("dd-MM-yyyy",date1).toString();*/
                //Delivery_date="22-01-2022";
                Toast.makeText(DeliverySlotActivity.this,current_Date+"=selected date="+Delivery_date,Toast.LENGTH_SHORT).show();
                boolean SlotDate=checkDate(Delivery_date,current_Date);

                boolean Slot1=checktimings(current_time,SlotcloseTime1);
                boolean Slot2=checktimings(current_time,SlotcloseTime2);
                boolean Slot3=checktimings(current_time,SlotcloseTime3);

                if(SlotDate==true)
                {
                    if(Slot1==false)
                        rb_slot_1.setEnabled(false);

                }
                else{
                    rb_slot_1.setEnabled(true);
                }

                if(SlotDate==true){
                    if(Slot2==false) {
                        rb_slot_1.setEnabled(false);
                        rb_slot_2.setEnabled(false);
                    }
                }
                else{
                    rb_slot_1.setEnabled(true);
                    rb_slot_2.setEnabled(true);
                }
                if(SlotDate==true ){
                    if(Slot3==false) {
                        rb_slot_1.setEnabled(false);
                        rb_slot_2.setEnabled(false);
                        rb_slot_3.setEnabled(false);
                    }
                }else{
                    rb_slot_1.setEnabled(true);
                    rb_slot_2.setEnabled(true);
                    rb_slot_3.setEnabled(true);
                }

            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliverySlotActivity.this.finish();
            }
        });

        rg_timeslot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_timeslot.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);

                if(radioButton.getText().toString().equalsIgnoreCase("06:00AM TO 09:00AM")){
                    Delivery_time="06:00AM TO 09:00AM";

                }
                else if(radioButton.getText().toString().equalsIgnoreCase("10:00AM TO 01:00PM")){
                    Delivery_time="10:00AM TO 01:00PM";

                }
                else if(radioButton.getText().toString().equalsIgnoreCase("02:00PM TO 05:00PM")){
                    Delivery_time="02:00PM TO 05:00PM";

                }
                else if(radioButton.getText().toString().equalsIgnoreCase("06:00PM TO 09:00PM")){
                    Delivery_time="06:00PM TO 09:00PM";
                }

            }
        });

        btn_selectdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Delivery_time.equalsIgnoreCase("")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(DeliverySlotActivity.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Please select time Slot.");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                    //Singleton.getInstance().showLongToast(mContext, "Please select a payment mode");//remove toast
                } else {

                    Intent i = new Intent();
                    i.setClass(DeliverySlotActivity.this, PaymentOption.class);
                    i.putExtra("addressid", addressid);
                    i.putExtra("address", address);
                    i.putExtra("ordertype", ordertype);
                    i.putExtra("totalamount", totalamount);
                    i.putExtra("totaltax", totaltax);
                    i.putExtra("totaldiscount", totaldiscount);
                    i.putExtra("grandtotal", grandtotal);
                    i.putExtra("orderid", "");
                    i.putExtra("discountid", discountid);
                    i.putExtra("discount_coupon_name", discount_coupon_name);
                    i.putExtra("discount_type", discount_type);
                    i.putExtra("discount_amount", discount_amount);
                    i.putExtra("discount_details", discount_details);
                    i.putExtra("credit_limit", credit_limit);
                    i.putExtra("credit_availed", credit_availed);
                    i.putExtra("credit_balance", credit_balance);
                    i.putExtra("delivery_charges", deliverycharges);
                    i.putExtra("delivery_slot_date", Delivery_date);
                    i.putExtra("delivery_time", Delivery_time);
                    //Toast.makeText(DeliverySlotActivity.this, Delivery_date + " ,"+Delivery_time, Toast.LENGTH_SHORT).show();
                    startActivity(i);

                }
            }
        });

    }

    private void Init() {
        rl_back=findViewById(R.id.rl_back);
        rl_search=findViewById(R.id.rl_search);
        rl_footer1=findViewById(R.id.rl_footer1);
        rl_dayslist=findViewById(R.id.rl_dayslist);
        btn_selectdt=findViewById(R.id.btn_selectdt);
        rg_timeslot=findViewById(R.id.rg_timeslot);
        rb_slot_1=findViewById(R.id.rb_slot_1);
        rb_slot_2=findViewById(R.id.rb_slot_2);
        rb_slot_3=findViewById(R.id.rb_slot_3);

        boolean Slot1=checktimings(current_time,SlotcloseTime1);
        boolean Slot2=checktimings(current_time,SlotcloseTime2);
        boolean Slot3=checktimings(current_time,SlotcloseTime3);

        if(Slot1==false) {
                rb_slot_1.setEnabled(false);
            }

        if(Slot2==false){
            rb_slot_1.setEnabled(false);
            rb_slot_2.setEnabled(false);

        }
        if(Slot3==false){
            rb_slot_1.setEnabled(false);
            rb_slot_2.setEnabled(false);
            rb_slot_3.setEnabled(false);
        }

        if(today.after(date1)){
            rb_slot_1.setEnabled(true);
            rb_slot_2.setEnabled(true);
            rb_slot_3.setEnabled(true);
        }

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

    private boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return true;
    }
    private boolean checkDate(String currentDate, String selectDate) {

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(currentDate);
            Date date2 = sdf.parse(selectDate);

            if(date1.equals(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return true;
    }

}
