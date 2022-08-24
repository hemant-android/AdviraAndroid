package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.category.adapter.CategoryItemAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.subscrption.AddSubscription;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DateSubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionstatusRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.UpdatesubscriptionstatusResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MySubscriptionAdaptor extends RecyclerView.Adapter<MySubscriptionAdaptor.MySubscriptionViewHolder> implements IConsts {


    private Context mContext;
    List<SubscriptionDatum> subscription;

    //RecyclerView recyclerViewexp;
    public static String subscription_id;

    BasketDetailsAdaptorProduct basketDetailsAdaptor;
    private List<BasketDatum> basketList;


    public MySubscriptionAdaptor(Context mContext, List<SubscriptionDatum> subscription) {
        this.mContext = mContext;
        this.subscription = subscription;
    }

    @NonNull
    @Override
    public MySubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_mysubscription, null);
        return new MySubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySubscriptionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubscriptionDatum product=subscription.get(position);

        int num=position+1;
        holder.tv_orderno.setText(" Daily Essential Tokri Id : #"+product.getId());
        holder.tv_itemCount.setText("Item : "+product.getBasketdataCount());
        holder.tv_subsid.setText(String.valueOf(product.getId()));
        subscription_id=holder.tv_subsid.getText().toString();

        holder.tv_status.setText("Status : "+product.getSubscriptionPaymentStatus());

        Calendar calendar = Calendar.getInstance();
        // get a date to represent "today"
        Date today = calendar.getTime();
        String CurrentDate= DateFormat.format("yyyy-MM-dd", today).toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1=new Date();
        Date date2=new Date();
        Date date3=new Date();

        try {
            /*date1 = sdf.parse(CurrentDate);
            date2 = sdf.parse(subscription.get(i).getSubscriptionEndDate());*/
            date3 = sdf.parse(product.getSubscriptionStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Boolean flag=false,flag1=false;
        for(int i=0;i<subscription.size();i++) {
            try {
                date1 = sdf.parse(CurrentDate);
                date2 = sdf.parse(subscription.get(i).getSubscriptionEndDate());
                //date3 = sdf.parse(subscription.get(i).getSubscriptionStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date1.after(date2)) {
                //subscription_id=String.valueOf(subscription.get(i).getId());
                Updatestatus(subscription_id);
                holder.tv_status.setText("Status : Delivered");
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            } else {
            }
        }

        /*if(flag)
        {
            Updatestatus(subscription_id);
            holder.tv_status.setText("Status : Delivered");
        }else{

        }*/




        String pcl = "#00973D";
        if(product.getSubscriptionPaymentStatus().equalsIgnoreCase("in-force")){
            if(date1.before(date3)){
                holder.tv_status.setText("Status : Upcoming");
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.amberYellow));
                holder.tv_edit.setVisibility(View.GONE);
            }else {
                holder.tv_status.setText("Status : Active");
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
                holder.tv_edit.setVisibility(View.GONE);
            }
        }else if(product.getSubscriptionPaymentStatus().equalsIgnoreCase("not-confirmed")){
            holder.tv_status.setText("Status : Pending");
            holder.tv_status.setTextColor(Color.RED);
            holder.tv_edit.setVisibility(View.VISIBLE);
        }else{
            holder.tv_status.setText("Status : Delivered");
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.tv_edit.setVisibility(View.GONE);
        }

        if(product.getSubscriptionType().equalsIgnoreCase("Regular")){
            holder.tv_subs_type.setText("Delivery Plan : Daily");
            holder.tv_subsperiod.setText(formatDate(product.getSubscriptionStartDate()));

        }else{
            holder.tv_subs_type.setText("Delivery Plan : "+product.getSubscriptionType());
            holder.tv_subsperiod.setText(formatDate(product.getSubscriptionStartDate())+" - "+formatDate(product.getSubscriptionEndDate()));
        }


        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefUtil.setSubscriptionID(mContext,IConsts.SHARED_PREF_SubscriptionID,holder.tv_subsid.getText().toString());
                Intent i=new Intent();
                i.setClass(mContext, AddSubscription.class);
                i.putExtra("subsID",product.getId());
                i.putExtra("message","adaptor");
                Log.e(TAG, "onClick: MySubscriptionadptr"+product.getId());
                //Toast.makeText(mContext, "subsid"+subscription_id, Toast.LENGTH_SHORT).show();
                mContext.startActivity(i);

            }
        });

        holder.cv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductList(position);

            }

            private void getProductList(int listnumber) {

                Utilities.showLoading(mContext);

                String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


                try{
                    Call<SubscriptionDetailResponse> call= RetrofitUrlConnection.loadJSON(token).getmysubscriptiondetail();
                    call.enqueue(new Callback<SubscriptionDetailResponse>() {
                        @Override
                        public void onResponse(Call<SubscriptionDetailResponse> call, Response<SubscriptionDetailResponse> response) {
                            if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                                Utilities.dismissDialog();

                                basketList=new ArrayList<>();
                                basketDetailsAdaptor=new BasketDetailsAdaptorProduct(mContext,basketList);

                                List<BasketDatum> mListData = response.body().getSubscription().get(position).getBasketData();

                                if (mListData != null && mListData.size() > 0) {
                                    basketList.addAll(mListData);
                                    //rl_noitems.setVisibility(View.GONE);
                                    //ma_headerq.setVisibility(View.GONE);
                                } else {
                                    try {
                                        //rl_noitems.setVisibility(View.VISIBLE);
                                        //ma_headerq.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                //orderAdapter.notifyDataSetChanged();
                                holder.recyclerViewexp.setAdapter(basketDetailsAdaptor);
                                holder.recyclerViewexp.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true));
                                holder.recyclerViewexp.setNestedScrollingEnabled(false);
                                Utilities.dismissDialog();
                            }
                            else{
                                Utilities.dismissDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<SubscriptionDetailResponse> call, Throwable t) {
                            Utilities.dismissDialog();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void Updatestatus(String subscriptionid) {

        //Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        UpdatesubscriptionstatusRequest r=new UpdatesubscriptionstatusRequest();
        r.setSubscriptionId(subscriptionid);

        try{
            Call<UpdatesubscriptionstatusResponse> call=RetrofitUrlConnection.loadJSON(token).updatesubscriptionstatus(r);
            call.enqueue(new Callback<UpdatesubscriptionstatusResponse>() {
                @Override
                public void onResponse(Call<UpdatesubscriptionstatusResponse> call, Response<UpdatesubscriptionstatusResponse> response) {
                    Utilities.dismissDialog();
                    if(response.body().getStatus().equals("100")){
                        Utilities.dismissDialog();
                    }
                    else{
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<UpdatesubscriptionstatusResponse> call, Throwable t) {
                        Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Utilities.dismissDialog();
        }

    }

    @Override
    public int getItemCount() {
        return subscription.size();
    }

    public class MySubscriptionViewHolder extends RecyclerView.ViewHolder {

        CardView cv_product;
        TextView tv_title,tv_subsid,tv_subsperiod,tv_subs_type,tv_orderno,tv_itemCount,tv_status,tv_edit;
        RecyclerView recyclerViewexp;

        public MySubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerViewexp=itemView.findViewById(R.id.recyclerViewexp);
            cv_product=itemView.findViewById(R.id.cv_subs);
            tv_subs_type=itemView.findViewById(R.id.tv_subs_type);
            tv_subsid=itemView.findViewById(R.id.tv_subsid);
            tv_subsperiod=itemView.findViewById(R.id.tv_subsperiod);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_orderno=itemView.findViewById(R.id.tv_orderno);
            tv_itemCount=itemView.findViewById(R.id.tv_itemCount);
            tv_status=itemView.findViewById(R.id.tv_status);
            recyclerViewexp.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            tv_edit=itemView.findViewById(R.id.tv_edit);

        }
    }

    private String formatDate(String dateString) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM, yyyy");

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

}
