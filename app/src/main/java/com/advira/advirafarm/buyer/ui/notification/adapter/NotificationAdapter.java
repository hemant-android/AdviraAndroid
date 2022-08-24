package com.advira.advirafarm.buyer.ui.notification.adapter;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.notification.NotificationListActivity;
import com.advira.advirafarm.buyer.ui.notification.api.MessageList;
import com.advira.advirafarm.buyer.ui.notification.api.NotificationDeleteRequest;
import com.advira.advirafarm.buyer.ui.notification.api.NotificationDeleteResponse;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_TOKEN;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.OrderViewHolder> {


    //this context we will use to inflate the layout
    private Context mContext;
    private String tctct;

    //we are storing all the orders in a list
    private List<MessageList> messageList;

    //getting the context and order list with constructor
    public NotificationAdapter(Context mContext, List<MessageList> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_notification, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        //getting the order of the specified position
        MessageList order = messageList.get(position);

        //binding the data with the view holder views

        holder.tv_date.setText(formatDate(order.getCreatedAt()));
        holder.tv_text.setText(order.getMessage());
        holder.tv_title.setText(order.getMessageHeader());
        holder.tv_notificationid.setText(order.getId());

        if (order.getBanners().size()>0){
            holder.tv_itemid.setText(order.getBanners().get(0).getActivityId());
            holder.tv_itemactivity.setText(order.getBanners().get(0).getActivityName());
            holder.tv_itemactivityheader.setText(order.getBanners().get(0).getActivityHeaderName());

           // String product_image = order.getBanners().get(0).getBannerUrl();

            /*if (product_image.length() > 5) {
                Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);
            }
            else
            {
                holder.imageView.setVisibility(View.INVISIBLE);
            }*/

        }
        else
        {
            holder.tv_itemactivity.setText("");
        }


        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationDelete(holder.tv_notificationid.getText().toString(),order);

            }
        });

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
        return messageList.size();
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemid, tv_itemactivity, tv_title, tv_text, tv_itemactivityheader,
                tv_date,tv_notificationid;
        ImageView imageView;
        CardView cv_order;
        ImageView btn_remove;

        public OrderViewHolder(View itemView) {
            super(itemView);

            tv_itemid = itemView.findViewById(R.id.tv_itemid);
            tv_itemactivity = itemView.findViewById(R.id.tv_itemactivity);
            tv_title = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.imageView);
            tv_text = itemView.findViewById(R.id.tv_text);
            tv_date = itemView.findViewById(R.id.tv_date);
            cv_order = itemView.findViewById(R.id.cv_order);
            tv_itemactivityheader = itemView.findViewById(R.id.tv_itemactivityheader);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            tv_notificationid = itemView.findViewById(R.id.tv_notificationid);

           // tv_status = itemView.findViewById(R.id.tv_status);

            cv_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String activityname = tv_itemactivity.getText().toString();
                    if(activityname.equalsIgnoreCase("PRODUCT"))
                    {

                     /*   Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productid", tv_itemid.getText().toString());
                        i.putExtra("productname", tv_itemactivityheader.getText().toString());
                        mContext.startActivity(i);*/
                    }
                    else if(activityname.equalsIgnoreCase("ORDER"))
                    {
                       /* Intent i = new Intent();
                        i.setClass(mContext, OrderDetailsActivity.class);
                        i.putExtra("orderid", tv_itemid.getText().toString());
                        i.putExtra("from", "");
                        mContext.startActivity(i);*/
                    }

                    /*else if(activityname.equalsIgnoreCase("PROFILE"))
                    {
                        Intent i = new Intent();
                        i.setClass(mContext, MainActivityNav.class);
                        i.putExtra("orderid", tv_itemid.getText().toString());
                        i.putExtra("from", "");
                        mContext.startActivity(i);
                    }*/


                }
            });
                      


        }
    }

    private void NotificationDelete(String notificationid,MessageList item) {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        NotificationDeleteRequest notificationDeleteRequest = new NotificationDeleteRequest();
        notificationDeleteRequest.setNotificationId(notificationid);

        try {

            Call<NotificationDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).deletenotification(notificationDeleteRequest);

            call.enqueue(new Callback<NotificationDeleteResponse>() {
                @Override
                public void onResponse(Call<NotificationDeleteResponse> call, Response<NotificationDeleteResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        int position = messageList.indexOf(item);
                        messageList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, messageList.size());

                        if (messageList.size() > 0) {

                            NotificationAdapter notificationAdapter = new NotificationAdapter(mContext, messageList);
                            notificationAdapter.notifyDataSetChanged();
                            NotificationListActivity.recyclerView.setAdapter(notificationAdapter);



                        } else {
                            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                            NotificationListActivity.recyclerView.setLayoutManager(layoutManager1);

                        }



                        Utilities.dismissDialog();
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());


                    } else {

                       // Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();
                    }

                }

                @Override
                public void onFailure(Call<NotificationDeleteResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

}
