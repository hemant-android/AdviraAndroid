package com.advira.advirafarm.buyer.ui.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.order.OrderCancellationActivity;
import com.advira.advirafarm.buyer.ui.order.OrderDetailsActivity;
import com.advira.advirafarm.buyer.ui.order.api.MemberDatum;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelRequest;
import com.advira.advirafarm.buyer.ui.order.api.OrderCancelResponse;
import com.advira.advirafarm.buyer.ui.order.api.OrderDatum;
import com.advira.advirafarm.buyer.ui.order.api.OrderDatum_v2;
import com.advira.advirafarm.buyer.ui.payment.PaymentOption;
import com.advira.advirafarm.buyer.ui.payment.paylaterpayment.PayLaterPaymentOption;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {


    //this context we will use to inflate the layout
    private Context mContext;
    private String tctct;
    private String orderid = "";
    private String mode="";

    //we are storing all the orders in a list
    //private List<OrderDatum> orderList;
    private List<OrderDatum_v2> orderList;

    //getting the context and order list with constructor
    public OrderAdapter(Context mContext, List<OrderDatum_v2> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_orders, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        //getting the order of the specified position
        //if(orderList!=null && orderList.size()!=0) {
            OrderDatum_v2 order = orderList.get(position);


            //binding the data with the view holder views
            holder.tv_orderno.setText("  Order ID # " + order.getOrderNo());
            holder.tv_deliv.setText(formatDate(order.getCreatedAt()));
            holder.tv_total_items.setText("Items : " + order.getTotalcartCount());
            holder.tv_total_orderval.setText("Amount : ₹ " + order.getGrandTotalAmount());
            holder.tv_status.setText("Item Status : " + order.getDeliveryStatus());
            holder.tv_orderid.setText(order.getId());
            holder.tv_cancelhours.setText(order.getMaxCancellationHours());
            holder.tv_ordercreteddatehour.setText(order.getCreatedAt());
            mode=order.getPayment().get(0).getPaymentStatus();

            orderid=order.getId();

            //holder.tv_addressid.setText(order.getAddress().getId());

            holder.tv_ordertype.setText(order.getOrderType());
            holder.tv_totalamount.setText(order.getTotalAmount());
            holder.tv_totaltax.setText(order.getTotalTax());
            holder.tv_totaldiscount.setText(order.getTotalDiscount());


            /*String address = order.getAddress().getAddress()
                    + " " + order.getAddress().getAddress2() + " " +
                    order.getAddress().getCityName() + ", " +
                    order.getAddress().getStateName() + " " +
                    order.getAddress().getPincode();*/

           /* holder.tv_address.setText(address);*/


            String gg = "#FF6066";
            String pcl = "#00973D";
            String blk = "#000000";
            int paymentsize = order.getPayment().size() - 1;

            try {
                if (order.getPayment().get(paymentsize).getPaymentStatus().equalsIgnoreCase("SUCCESS")) {
                    holder.tv_paymentstatus.setText("Payment Status: " + order.getPayment().get(paymentsize).getPaymentStatus());
                    holder.tv_paymentstatus.setTextColor(Color.parseColor(blk));
                    holder.tv_cancelorder.setVisibility(View.VISIBLE);
                    holder.rl_retrypayment.setVisibility(View.GONE);
                } /*else if (order.getPayment().get(0).getPaymentMode().equalsIgnoreCase("COD") || order.getPayment().get(paymentsize).getPaymentMode().equalsIgnoreCase("Credit")) {
                    holder.tv_paymentstatus.setText("Payment Status: Pending ");
                    holder.tv_paymentstatus.setTextColor(Color.parseColor(pcl));
                    holder.tv_cancelorder.setVisibility(View.VISIBLE);
                    holder.rl_retrypayment.setVisibility(View.GONE);
                    holder.paynowcod.setVisibility(View.VISIBLE);

                }*/
                else {
                    if (order.getPayment().get(paymentsize).getPaymentMode().equalsIgnoreCase("COD") || order.getPayment().get(paymentsize).getPaymentMode().equalsIgnoreCase("Credit")) {
                        if(order.getDeliveryStatus().equalsIgnoreCase("Cancelled")) {
                            holder.paynowcod.setVisibility(View.GONE);
                            holder.rl_retrypayment.setVisibility(View.GONE);
                            holder.tv_paymentstatus.setText("Payment Status: Cancel ");
                            holder.tv_paymentstatus.setTextColor(Color.parseColor(pcl));
                        }else if(order.getDeliveryStatus().equalsIgnoreCase("Delivered")) {
                            holder.paynowcod.setVisibility(View.GONE);
                            holder.rl_retrypayment.setVisibility(View.GONE);
                            holder.tv_paymentstatus.setText("Payment Status: "+ order.getPayment().get(paymentsize).getPaymentStatus());
                            holder.tv_paymentstatus.setTextColor(Color.parseColor(pcl));
                        }
                        else {
                            holder.tv_paymentstatus.setText("Payment Status: Pending ");
                            holder.tv_paymentstatus.setTextColor(Color.parseColor(pcl));
                            holder.tv_cancelorder.setVisibility(View.VISIBLE);
                            holder.rl_retrypayment.setVisibility(View.GONE);
                            holder.paynowcod.setVisibility(View.VISIBLE);
                        }
                    }else if(order.getDeliveryStatus().equalsIgnoreCase("Cancelled")){
                        holder.tv_paymentstatus.setText("Payment Status: " + order.getPayment().get(paymentsize).getPaymentStatus());
                        holder.tv_paymentstatus.setTextColor(Color.parseColor(gg));
                        holder.tv_status.setText("Item Status : Cancelled");
                        //holder.tv_status.setTextColor(Color.parseColor(gg));
                        holder.tv_cancelorder.setVisibility(View.GONE);
                        holder.rl_retrypayment.setVisibility(View.GONE);
                        holder.paynowcod.setVisibility(View.GONE);
                    }
                    else {
                        holder.tv_paymentstatus.setText("Payment Status: " + order.getPayment().get(paymentsize).getPaymentStatus());
                        holder.tv_paymentstatus.setTextColor(Color.parseColor(gg));

                        String cancelhour=order.getMaxretryHours();
                        //String ordercreatedat=order.getMaxretryhoursTime();
                        String ordercreatedat=order.getPayment().get(order.getPayment().size()-1).getPaymentDate();
                        double cacelminutes = Integer.valueOf(cancelhour) * 60;
                        int diff = twoDatesBetweenTime(ordercreatedat);
                        if (diff > cacelminutes) {
                            holder.rl_retrypayment.setVisibility(View.GONE);
                            holder.tv_status.setText("Item Status : Cancelled");
                            holder.tv_status.setTextColor(Color.parseColor(gg));
                            cancelOrder();
                            Utilities.dismissDialog();

                        }
                        else{
                            holder.rl_retrypayment.setVisibility(View.VISIBLE);
                        }
                    }
                }
            /*holder.tv_paymentstatus.setText("Payment Status: " + order.getPayment().get(paymentsize).getPaymentStatus());

            if (order.getPayment().get(paymentsize).getPaymentStatus().equalsIgnoreCase("failed")) {

                holder.tv_paymentstatus.setTextColor(Color.parseColor(gg));
            } else {
                holder.tv_paymentstatus.setTextColor(Color.parseColor("#000000"));
            }*/
            } catch (Exception ex) {

            }


        /*if (order.getPayment().get(0).getPaymentStatus().equalsIgnoreCase("failed")) {


            holder.rl_retrypayment.setVisibility(View.VISIBLE);
            //  holder.tv_cancelorder.setVisibility(View.GONE);


        } else {
            holder.rl_retrypayment.setVisibility(View.GONE);
            // holder.tv_cancelorder.setVisibility(View.VISIBLE);
        }

*/
/*
if(order.getOrderProductList().size()>0) {
    String product_image = order.getOrderProductList().get(0).getProductImage();


    if (product_image.length() > 5) {
        Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);
    } else {
        holder.imageView.setVisibility(View.INVISIBLE);
    }
}
*/


        String orderstatus = order.getDeliveryStatus();

        if (orderstatus.equalsIgnoreCase("Ordered") || orderstatus.equalsIgnoreCase("Order-Confirmed")) {
            if (order.getPayment().get(0).getPaymentStatus().equalsIgnoreCase("success") || order.getPayment().get(0).getPaymentMode().equalsIgnoreCase("COD")) {

                try {
                    String cancelhour = "0";
                    String ordercreatedat = "0";

                    cancelhour = order.getMaxCancellationHours();
                    ordercreatedat = order.getCreatedAt();


                    double cacelminutes = Integer.valueOf(cancelhour) * 60;

                    int diff = twoDatesBetweenTime(ordercreatedat);
                    if (diff > cacelminutes) {

                        holder.tv_cancelorder.setVisibility(View.GONE);

                    } else {
                        holder.tv_cancelorder.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {

                }

            } else {
                holder.tv_cancelorder.setVisibility(View.GONE);
            }


        } else {
            holder.tv_cancelorder.setVisibility(View.GONE);
        }

    }


    private void cancelOrder() {

        Utilities.showLoading(mContext);
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setOrderId(orderid);
        orderCancelRequest.setCancelReasonId("67");
        orderCancelRequest.setCancelReasonOther("My reason is not listed");

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {
            Call<OrderCancelResponse> call = RetrofitUrlConnection.loadJSON(token).canclemyorder(orderCancelRequest);

            call.enqueue(new Callback<OrderCancelResponse>() {
                @Override
                public void onResponse(Call<OrderCancelResponse> call, Response<OrderCancelResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();

                        //OrderCancellationActivity.this.finish();

                        /*Intent i=new Intent();
                        i.setClass(mContext,OrderDetailsActivity.class);
                        i.putExtra("orderid", orderid);
                        i.putExtra("from", "");
                        startActivity(i);*/

                    } else {

                        Utilities.dismissDialog();

                    }
                }

                @Override
                public void onFailure(Call<OrderCancelResponse> call, Throwable t) {
                    Utilities.dismissDialog();

                    //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Utilities.dismissDialog();

            //Toast.makeText(mContext, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    public Integer twoDatesBetweenTime(String oldtime) {
        // TODO Auto-generated method stub
        int day = 0;
        int hh = 0;
        int mm = 0;

        int differencemin = 0;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldDate = dateFormat.parse(oldtime);
            Date cDate = new Date();
            Long timeDiff = cDate.getTime() - oldDate.getTime();
            day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
            differencemin = (int) TimeUnit.MILLISECONDS.toMinutes(timeDiff);
            hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
            mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return differencemin;
    }

    private String formatDate(String dateString) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("EE, MMM dd yyyy");

        Date d = null;
        try {
            d = input.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);

        return formatted;
    }

    @Override
    public int getItemCount() {

        return orderList.size();
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tv_orderid, tv_deliv, tv_total_orderval, tv_total_items, tv_status,
                tv_orderno, tv_paymentstatus, tv_cancelorder, tv_cancelhours,tv_ordercreteddatehour,
                tv_addressid,tv_address,tv_ordertype,tv_totalamount,tv_totaltax,tv_totaldiscount,paynowcod;
        ImageView imageView, iv_status;
        android.widget.RatingBar RatingBar;
        CardView cv_order;
        RelativeLayout rl_retrypayment;
        Button btn_retrypayment;

        public OrderViewHolder(View itemView) {
            super(itemView);

            tv_orderid = itemView.findViewById(R.id.tv_orderid);
            tv_deliv = itemView.findViewById(R.id.tv_deliv);
            tv_total_orderval = itemView.findViewById(R.id.tv_total_orderval);
            RatingBar = itemView.findViewById(R.id.RatingBar);
            iv_status = itemView.findViewById(R.id.iv_status);
            imageView = itemView.findViewById(R.id.imageView);
            tv_total_items = itemView.findViewById(R.id.tv_total_items);
            tv_orderno = itemView.findViewById(R.id.tv_orderno);
            cv_order = itemView.findViewById(R.id.cv_order);
            tv_paymentstatus = itemView.findViewById(R.id.tv_paymentstatus);
            tv_cancelorder = itemView.findViewById(R.id.tv_cancelorder);
            rl_retrypayment = itemView.findViewById(R.id.rl_retrypayment);
            tv_cancelhours = itemView.findViewById(R.id.tv_cancelhours);
            tv_ordercreteddatehour = itemView.findViewById(R.id.tv_ordercreteddatehour);
            tv_addressid = itemView.findViewById(R.id.tv_addressid);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_ordertype = itemView.findViewById(R.id.tv_ordertype);
            tv_totalamount = itemView.findViewById(R.id.tv_totalamount);
            tv_totaltax = itemView.findViewById(R.id.tv_totaltax);
            tv_totaldiscount = itemView.findViewById(R.id.tv_totaldiscount);
            btn_retrypayment = itemView.findViewById(R.id.btn_retrypayment);
            paynowcod=itemView.findViewById(R.id.paynowcod);

            tv_status = itemView.findViewById(R.id.tv_status);
            RatingBar.setNumStars(5);

            cv_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent();
                    i.setClass(mContext, OrderDetailsActivity.class);
                    i.putExtra("orderid", tv_orderid.getText().toString());
                    i.putExtra("from", "");
                    mContext.startActivity(i);

                }
            });


            tv_cancelorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {

                        double cacelminutes = Integer.valueOf(tv_cancelhours.getText().toString()) * 60;

                        int diff = twoDatesBetweenTime(tv_ordercreteddatehour.getText().toString());
                        if (diff > cacelminutes) {
                            tv_cancelorder.setVisibility(View.GONE);
                        } else {
                            tv_cancelorder.setVisibility(View.VISIBLE);

                            Intent i = new Intent();
                            i.setClass(mContext, OrderCancellationActivity.class);
                            i.putExtra("total", tv_total_orderval.getText().toString());
                            i.putExtra("orderid", tv_orderid.getText().toString());
                            i.putExtra("orderno", tv_orderno.getText().toString());
                            mContext.startActivity(i);

                        }
                    } catch (Exception ex) {

                    }

                }
            });

            paynowcod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent mainIntent = new Intent(mContext, PayLaterPaymentOption.class);

                    mainIntent.putExtra("orderid", tv_orderid.getText().toString());
                    mainIntent.putExtra("orderno", tv_orderno.getText().toString().replace("  Order ID # ",""));
                    mainIntent.putExtra("addressid", tv_addressid.getText().toString());
                    mainIntent.putExtra("address", tv_address.getText().toString());
                    mainIntent.putExtra("ordertype", tv_ordertype.getText().toString());
                    mainIntent.putExtra("totalamount", tv_totalamount.getText().toString());
                    mainIntent.putExtra("totaltax", tv_totaltax.getText().toString());
                    mainIntent.putExtra("totaldiscount", tv_totaldiscount.getText().toString());
                    mainIntent.putExtra("grandtotal", tv_total_orderval.getText().toString().replace("Amount : ₹ ",""));
                    mainIntent.putExtra("from", "back");
                    mContext.startActivity(mainIntent);
                }
            });


            btn_retrypayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(mContext, PaymentOption.class);
                    mainIntent.putExtra("orderid", tv_orderid.getText().toString());
                    mainIntent.putExtra("orderno", tv_orderno.getText().toString().replace("  Order ID # ",""));
                    mainIntent.putExtra("addressid", tv_addressid.getText().toString());
                    mainIntent.putExtra("address", tv_address.getText().toString());
                    mainIntent.putExtra("ordertype", tv_ordertype.getText().toString());
                    mainIntent.putExtra("totalamount", tv_totalamount.getText().toString());
                    mainIntent.putExtra("totaltax", tv_totaltax.getText().toString());
                    mainIntent.putExtra("totaldiscount", tv_totaldiscount.getText().toString());
                    mainIntent.putExtra("grandtotal", tv_total_orderval.getText().toString().replace("Amount : ₹ ",""));
                    mainIntent.putExtra("from", "back");
                    mContext.startActivity(mainIntent);

                }
            });
        }
    }
}
