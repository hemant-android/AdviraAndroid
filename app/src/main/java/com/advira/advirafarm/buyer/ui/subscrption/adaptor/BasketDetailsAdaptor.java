package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.profile.BusinessDetailsActivity;
import com.advira.advirafarm.buyer.ui.subscrption.AddSubscription;
import com.advira.advirafarm.buyer.ui.subscrption.DailyBasketProductList;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.ui.subscrption.SubscriptionPreviewActivity;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.ConfirmSubscriptionRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDailyBasketCartResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDetailResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.product_dairybasket;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class BasketDetailsAdaptor extends RecyclerView.Adapter<BasketDetailsAdaptor.BasketDetailsViewHolder> implements IConsts {

    private int minteger = 0;
    private int moq = 10;
    private String previewtotal="0";
    AlertDialog.Builder builder;

    String membershipName="";
    String basket_productid="";

    private List<BasketDatum> basketList;
    private static Context mContext;
    //String subscription_id="";
    String SelectedDate= MySubscription.SelectedDate;


    public BasketDetailsAdaptor(Context mContext, List<BasketDatum> basketList) {
        this.mContext = mContext;
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public BasketDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_basketdetails, null);

        return new BasketDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketDetailsViewHolder holder, int position) {
        BasketDatum product = basketList.get(position);

        //holder.tv_srno.setText(product.getProductId());
        holder.tv_productname.setText(product.getProdctName());
        holder.tv_price.setText(product.getpTotalPrice());
        holder.integer_number.setText(product.getpCount());
        holder.tv_subsid.setText(String.valueOf(product.getSubscriptionId()));
        holder.textViewRating.setText(product.getSkuBrandName());

        holder.tv_prodid.setText(String.valueOf(product.getProductId()));
        basket_productid=String.valueOf(product.getProductId());
        //holder.tv_productname.setText(product.getProdctName());
        //holder.tv_stock.setText(pro);
        // holder.tv_pack.setText(product.getProductDiscount());
        holder.tv_pack.setText(product.getpCount()+" X "+product.getpUnits()+product.getpUnitType());
        DecimalFormat form = new DecimalFormat("0.00");
        //holder.tv_minqty.setText("Min order : " + product.get());
        holder.tv_orderqtyval.setText(product.getpCount());
        holder.tv_inr.setText("Rate ₹ ");
        holder.tv_mrp.setText("₹ "+product.getpTotalPrice());
        holder.tv_price.setText(product.getpPricePerPack());
        String product_delivery_status=product.getProductDeliveryStatus();
        //SelectedDate=SubscriptionActivity.SelectedDate;
        if(product_delivery_status!=null) {
            if (product_delivery_status.equalsIgnoreCase("Cancelled")) {
                holder.tv_remove.setVisibility(View.GONE);
                holder.ll_addremove.setVisibility(View.GONE);
                holder.tv_discount.setText("Cancelled");
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.tv_discount);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.tv_pack.setLayoutParams(params);
                holder.tv_discount.setTextColor(Color.RED);
                holder.tv_discount.setVisibility(View.VISIBLE);
                holder.tv_productname.setBackgroundResource(R.drawable.strike_through);
            } else if (product_delivery_status.equalsIgnoreCase("Deliverd")) {
                holder.ll_addremove.setVisibility(View.GONE);
                holder.tv_discount.setVisibility(View.VISIBLE);
                holder.tv_discount.setText("Delivered");
                holder.tv_discount.setTextColor(Color.parseColor("#2A882D"));
                holder.tv_remove.setBackgroundResource(R.color.white);
                holder.tv_discount.setEnabled(false);
                holder.tv_remove.setVisibility(View.GONE);
            } else {

            }
        }

        String product_image = product.getProductImageUrl();

        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        double price = Double.parseDouble(product.getpPricePerPack());
        double totprice = Double.parseDouble(product.getpTotalPrice());
        double mrp = Double.parseDouble(product.getpTotalPrice());

        //DecimalFormat form = new DecimalFormat("0.00");
        //holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_price.setText(form.format(price));
        holder.tv_itempriceval.setText(form.format(totprice));

        //holder.tv_discount.setText(product.getProductMrpDiscountLabel());
        //holder.tv_orderqtyunit.setText(product.getProductQuantity());

        moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

        /*holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefromsubscription(holder.tv_prodid.getText().toString());
            }
        });*/

        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.tv_remove.setVisibility(View.GONE);
                holder.ll_addremove.setVisibility(View.VISIBLE);
            }
        });

        holder.btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    minteger = Integer.parseInt(holder.integer_number.getText().toString());

                    minteger = minteger + 1;
                    holder.integer_number.setText(String.valueOf(minteger));
                    AddToCart(holder.tv_prodid.getText().toString(), "1",holder.tv_subsid.getText().toString());
                    holder.tv_pack.setText("("+minteger+" X "+product.getpUnits()+product.getpUnitType()+")");
                    double price = Double.parseDouble(holder.tv_price.getText().toString());
                    double qty = Double.parseDouble(holder.integer_number.getText().toString());
                    // double pack = Double.parseDouble(holder.tv_boxsize.getText().toString());
                    double totprice = price * qty ;

                    DecimalFormat form = new DecimalFormat("0.00");
                    holder.tv_mrp.setText("₹ "+form.format(totprice));

                    if (Integer.parseInt(holder.integer_number.getText().toString()) >= moq) {
                        holder.btn_decrease.setEnabled(true);
                    }

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        holder.btn_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    if (Integer.parseInt(holder.integer_number.getText().toString()) == moq) {

                        CartDeleteRequest(holder.tv_prodid.getText().toString(),holder.tv_subsid.getText().toString());

                        if(Integer.parseInt(holder.integer_number.getText().toString())==0) {
                            //holder.btn_decrease.setEnabled(false);
                            //holder.tv_productname
                            holder.tv_discount.setText("Cancelled");
                            holder.tv_discount.setTextColor(Color.RED);
                            holder.tv_discount.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.addRule(RelativeLayout.BELOW, R.id.tv_discount);
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            holder.tv_pack.setLayoutParams(params);
                            holder.ll_addremove.setVisibility(View.GONE);
                            holder.tv_remove.setVisibility(View.GONE);
                            holder.tv_productname.setBackgroundResource(R.drawable.strike_through);
                        }
                        /*else{

                            holder.tv_discount.setText("Cancelled");
                            holder.tv_discount.setTextColor(Color.RED);
                            holder.tv_discount.setVisibility(View.VISIBLE);
                            holder.ll_addremove.setVisibility(View.GONE);
                            holder.tv_remove.setVisibility(View.GONE);

                        }*/


                    } else {
                        minteger = Integer.parseInt(holder.integer_number.getText().toString());
                        minteger = minteger - 1;
                        holder.integer_number.setText(String.valueOf(minteger));


                        if (minteger < 1) {

                            CartDeleteRequest(holder.tv_prodid.getText().toString(),holder.tv_subsid.getText().toString());
                            holder.tv_discount.setText("Cancelled");
                            holder.tv_discount.setTextColor(Color.RED);
                            holder.tv_discount.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.addRule(RelativeLayout.BELOW, R.id.tv_discount);
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            holder.tv_pack.setLayoutParams(params);
                            holder.ll_addremove.setVisibility(View.GONE);
                            holder.tv_remove.setVisibility(View.GONE);
                            holder.tv_productname.setBackgroundResource(R.drawable.strike_through);

                        } else {
                            //int packprice=Integer.parseInt(holder.tv_price.getText().toString());
                            //Log.e(TAG, "onClick: btndecrease"+packprice );
                            UpdateCart(holder.tv_prodid.getText().toString(), holder.integer_number.getText().toString(),holder.tv_subsid.getText().toString());
                            holder.tv_pack.setText("("+minteger+" X "+product.getpUnits()+product.getpUnitType()+")");
                            double price = Double.parseDouble(holder.tv_price.getText().toString());
                            double qty = Double.parseDouble(holder.integer_number.getText().toString());
                            // double pack = Double.parseDouble(holder.tv_boxsize.getText().toString());
                            double totprice = price * qty ;

                            DecimalFormat form = new DecimalFormat("0.00");
                            holder.tv_mrp.setText("₹ "+form.format(totprice));
                        }
                    }

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        CalculatePrice();

    }

    private void CheckProfile(String productid, String qty,String subscription_id) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        try {

            Call<IsUserVerifiedResponse> call = RetrofitUrlConnection.loadJSON(token).isuserverified();

            call.enqueue(new Callback<IsUserVerifiedResponse>() {
                @Override
                public void onResponse(Call<IsUserVerifiedResponse> call, Response<IsUserVerifiedResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Utilities.dismissDialog();

                        String popmsg = "Please update below details :";
                        String chkpop = "n";

                        if (response.body().getPersonalProfileStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Personal details";
                            chkpop = "y";
                        }
                        if (response.body().getBusinessProfileStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Business details";
                            chkpop = "y";
                        }
                        if (response.body().getKycDocumentStatus().equalsIgnoreCase("0")) {
                            popmsg = popmsg + "\n - Upload KYC documents";
                            chkpop = "y";
                        }

                        if (response.body().getProfileActivateStatus().equalsIgnoreCase("0")) {

                            if (popmsg.contains("Personal details") || popmsg.contains("Business details") || popmsg.contains("Upload KYC documents")) {

                            } else {
                                popmsg = "Your account is not active";
                            }

                            chkpop = "y";
                        }

                        if (chkpop.equalsIgnoreCase("y")) {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "inactive");
                            ShowAlert(popmsg);
                        } else {
                            SharedPrefUtil.setUserActive(mContext, SHARED_PREF_UserActive, "active");
                            AddToCart(productid, qty,subscription_id);

                        }

                    } else {

                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<IsUserVerifiedResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void ShowAlert(String ShowAlert) {
        builder = new AlertDialog.Builder(mContext);


        builder.setTitle("Account Inactive !!");

        //Setting message manually and performing action on button click
        builder.setMessage(ShowAlert)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        if (ShowAlert.contains("Personal details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, ProductDetailsActivityB2B.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        } else if (ShowAlert.contains("Business details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, BusinessDetailsActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        } else if (ShowAlert.contains("Upload KYC documents")) {

                            Intent i = new Intent();
                            i.setClass(mContext, DocumentUploadActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();


                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

    }

    private void AddToCart(String productunitid, String qty,String subscription_id) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");
        String dateFrom="";
        if(SelectedDate.equalsIgnoreCase("")){
            Calendar calendar = Calendar.getInstance();
            // get a date to represent "today"
            Date today = calendar.getTime();
            String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
            dateFrom=defaultDate;
        }
        else{
            dateFrom=MySubscription.SelectedDate;
            Log.e(TAG, "AddToCart: basketDetailsAdaptor"+MySubscription.SelectedDate );
        }


        DailyBasketCartRequest dailyBasketCartRequest = new DailyBasketCartRequest();
        dailyBasketCartRequest.setProductSkuUnitPriceId(productunitid);
        dailyBasketCartRequest.setProductQuantity(qty);
        dailyBasketCartRequest.setSubscriptionId(subscription_id);
        dailyBasketCartRequest.setDatefrom(dateFrom);

        try {

            Call<SubscriptionDailyBasketCartResponse> call=RetrofitUrlConnection.loadJSON(token).addproducttosubscription(dailyBasketCartRequest);

            call.enqueue(new Callback<SubscriptionDailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<SubscriptionDailyBasketCartResponse> call, Response<SubscriptionDailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //basket_productid=String.valueOf(response.body().getBasketData().getProductId());
                        Utilities.dismissDialog();
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Order Updated Successfully.");
                        alert.setPositiveButton("OK",null);
                        alert.show();

                    } else {

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<SubscriptionDailyBasketCartResponse> call, Throwable t) {

                    Utilities.dismissDialog();

                }
            });

        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
    private void UpdateCart(String productunitid, String qty, String subscription_id) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");
        String dateFrom="";
        if(SelectedDate.equalsIgnoreCase("")){
            Calendar calendar = Calendar.getInstance();
            // get a date to represent "today"
            Date today = calendar.getTime();
            String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
            dateFrom=defaultDate;
        }
        else{
            dateFrom=MySubscription.SelectedDate;
        }
        Log.e(TAG, "UpdateCart: basketdetails"+SelectedDate );
        DailyBasketCartRequest dailyBasketCartRequest = new DailyBasketCartRequest();
        dailyBasketCartRequest.setProductSkuUnitPriceId(productunitid);
        dailyBasketCartRequest.setProductQuantity(qty);
        dailyBasketCartRequest.setSubscriptionId(subscription_id);
        dailyBasketCartRequest.setDatefrom(dateFrom);

        try {

            Call<SubscriptionDailyBasketCartResponse> call=RetrofitUrlConnection.loadJSON(token).updateproducttosubscription(dailyBasketCartRequest);

            call.enqueue(new Callback<SubscriptionDailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<SubscriptionDailyBasketCartResponse> call, Response<SubscriptionDailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Order Updated Successfully.");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                        //getBasketDetailsViaDate(SelectedDate);
                    }
                    else{
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<SubscriptionDailyBasketCartResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                    Log.e(TAG, "Failed to get the token : " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
    private void CartDeleteRequest(String productid,String subscription_id) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        //subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");
        Log.e(TAG, "DeleteCart: deletebasketdetails"+SelectedDate );
        String dateFrom="";
        if(SelectedDate.equalsIgnoreCase("")){
            Calendar calendar = Calendar.getInstance();
            // get a date to represent "today"
            Date today = calendar.getTime();
            String defaultDate= DateFormat.format("dd-MM-yyyy", today).toString();
            dateFrom=defaultDate;
        }
        else{
            dateFrom=MySubscription.SelectedDate;
        }

        Log.e(TAG, "CartDeleteRequest: cartdeleteRequest"+SelectedDate );
        DailyBasketCartDeleteRequest basketDeleteRequest = new DailyBasketCartDeleteRequest();
        basketDeleteRequest.setBasketProductId(productid);
        basketDeleteRequest.setSubscriptionId(subscription_id);
        basketDeleteRequest.setSubscriptionDate(dateFrom);

        try {

            Call<DailyBasketCartDeleteResponse> call=RetrofitUrlConnection.loadJSON(token).cancelproductfromsubscription(basketDeleteRequest);

            call.enqueue(new Callback<DailyBasketCartDeleteResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartDeleteResponse> call, Response<DailyBasketCartDeleteResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);
                        alert.setTitle("Alert!!");
                        alert.setMessage("Order item cancel Successfully.");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                    }
                    else {
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketCartDeleteResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });

        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {

        return basketList.size();
    }

    public static class BasketDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productname, tv_stock,textViewRating;
        ImageView imageView, iv_rx;
        TextView integer_number;
        Button btn_increase;
        Button btn_decrease;
        ImageView btn_remove;
        LinearLayout ll_addremove;
        TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
                tv_orderqtyval, tv_prodid, tv_inr, tv_boxsize,tv_subsid, tv_mrp, tv_orderqtyunit,tv_discount,tv_remove;


        public BasketDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_stock = itemView.findViewById(R.id.tv_stock);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);
            tv_price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.imageView);
            tv_pack = itemView.findViewById(R.id.tv_pack);
            tv_minqty = itemView.findViewById(R.id.tv_minqty);
            tv_itempriceval = itemView.findViewById(R.id.tv_itempriceval);
            tv_prodid = itemView.findViewById(R.id.tv_prodid);
            tv_orderqtyval = itemView.findViewById(R.id.tv_orderqtyval);
            tv_inr = itemView.findViewById(R.id.tv_inr);
            tv_boxsize = itemView.findViewById(R.id.tv_boxsize);
            iv_rx = itemView.findViewById(R.id.iv_rx);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_orderqtyunit = itemView.findViewById(R.id.tv_orderqtyunit);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tv_mrpval.setBackgroundResource(R.drawable.strike_through);
            tv_remove=itemView.findViewById(R.id.tv_remove);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            btn_decrease = itemView.findViewById(R.id.btn_decrease);
            btn_increase = itemView.findViewById(R.id.btn_increase);
            integer_number = itemView.findViewById(R.id.integer_number);
            ll_addremove=itemView.findViewById(R.id.ll_addremove);
            tv_subsid=itemView.findViewById(R.id.tv_subsid);
            textViewRating=itemView.findViewById(R.id.textViewRating);

        }
        }

    private void CalculatePrice() {

        double itemmrp = 0;
        double mrptotal = 0;
        double amountpayable = 0;
        double itemtax = 0;
        double carttotal = 0;
        double itemprice = 0;
        double totaltax = 0;
        double delicharge=0;

        /*itemcount=basketList.size();
        if(itemcount==0)
        {
            SubscriptionPreviewActivity.rl_noitems.setVisibility(View.VISIBLE);
            SubscriptionPreviewActivity.ll_tab.setVisibility(View.GONE);
            SubscriptionPreviewActivity.recyclerView.setVisibility(View.GONE);
            SubscriptionPreviewActivity.rl_content2.setVisibility(View.GONE);
            SubscriptionPreviewActivity.tv_daysval.setVisibility(View.GONE);
            SubscriptionPreviewActivity.tv_itemcount.setVisibility(View.GONE);
        }else{
            SubscriptionPreviewActivity.rl_noitems.setVisibility(View.GONE);
            SubscriptionPreviewActivity.ll_tab.setVisibility(View.VISIBLE);
            SubscriptionPreviewActivity.recyclerView.setVisibility(View.VISIBLE);
            SubscriptionPreviewActivity.rl_content2.setVisibility(View.VISIBLE);
            SubscriptionPreviewActivity.tv_daysval.setVisibility(View.VISIBLE);
            SubscriptionPreviewActivity.tv_itemcount.setVisibility(View.VISIBLE);
        }
        SubscriptionPreviewActivity.tv_itemcount.setText(String.valueOf("Item Count : "+itemcount));
        Log.e(TAG, "CalculatePrice: aaptor"+itemcount );
*/
        if (basketList.size() > 0) {
            for (int i = 0; i < basketList.size(); i++) {

                itemprice = Double.parseDouble(basketList.get(i).getpTotalPrice());
                carttotal = carttotal + itemprice;
                itemtax = Double.parseDouble(basketList.get(i).getpTax());
                totaltax = totaltax + (itemprice * itemtax * .01);
            }

            amountpayable = carttotal;//24.9.2021
            Log.e(TAG, "CalculatePrice: amountpayable"+amountpayable );

            String flag = String.valueOf(amountpayable);
            int index = flag.indexOf(".");
            flag = flag.substring(index + 1, index + 2);
            int chk = Integer.valueOf(flag);

            if (chk > 4) {
                double d = amountpayable;
                int max = (int) Math.ceil(d);
                String totalval = String.valueOf(max);
                amountpayable = Double.parseDouble(totalval);

            } else {
                double d = amountpayable;
                int max = (int) Math.floor(d);
                String totalval = String.valueOf(max);
                amountpayable = Double.parseDouble(totalval);
            }

            DecimalFormat form = new DecimalFormat("0.00");
            //MySubscription.tv_priceval.setText("₹ " + form.format(carttotal));
            MySubscription.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
        } else {
            //SubscriptionPreviewActivity.tv_priceval.setText("₹ 0");
            MySubscription.tv_totalpaidval.setText("₹ 0");

        }
    }
    }


