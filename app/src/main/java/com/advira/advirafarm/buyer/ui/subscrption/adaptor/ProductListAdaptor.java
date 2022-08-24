package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.guest.RFQMobileActivity;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.profile.BusinessDetailsActivity;
import com.advira.advirafarm.buyer.ui.subscrption.DailyBasketProductList;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketListResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.Product_Basket;
import com.advira.advirafarm.buyer.ui.subscrption.api.SubscriptionDailyBasketCartResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdaptor extends RecyclerView.Adapter<ProductListAdaptor.DairyBasketViewHolder> implements IConsts {

    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    String profilemode="";
    String usertype="";
    double ordervalue;
    String membershipName="";
    public  static String subscription_id="";
    String basket_productid="";
    AlertDialog.Builder builder;
    String SelectedDate= DailyBasketProductList.SelectedDate;

    private List<Product_Basket> productList;


    public ProductListAdaptor(Context mContext, List<Product_Basket> productList) {
        this.mContext = mContext;
        this.productList = productList;

    }

    @NonNull
    @Override
    public DairyBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_dairybasket, null);
        return new DairyBasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DairyBasketViewHolder holder, int position) {

        Product_Basket product = productList.get(position);
        holder.tv_productname.setText(product.getProductname());
        holder.textViewShortDesc.setText(product.getProductVariety().replaceAll("##", "\n"));
        //holder.textViewShortDesc.setText(product.getProductVariety().replaceAll("##", "\n"));
        /*holder.textViewRating.setText(String.valueOf(product.getProductUnits().get(0).getProductInstock()));
        holder.tv_pack.setText("1");
        holder.tv_productid.setText(product.getId());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText("1");
        holder.tv_packsize.setText(product.getProductUnits().get(0).getProductUnits() + " " + product.getProductUnits().get(0).getProductUnitType());

        holder.tv_discount.setText(product.getProductUnits().get(0).getProductDiscountLabel());

        String productInstock = product.getProductUnits().get(0).getProductInstock();
*/
        //holder.textViewRating.setText(String.valueOf(product.getProductInstock()));
        holder.tv_pack.setText("1");
        holder.tv_productid.setText(product.getSkuId());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText("1");
        holder.tv_packsize.setText(product.getProductUnits() + " " + product.getProductUnitType());
        holder.tv_discount.setText(product.getProductDiscountLabel());

        String productInstock = product.getProductInstock();

        if (productInstock.equalsIgnoreCase("Out-of-Stock")) {

            holder.btn_addto.setEnabled(false);
            holder.btn_add.setEnabled(false);
            holder.textViewRating.setVisibility(View.VISIBLE);
            // holder.ll_addremovebutton.setVisibility(View.INVISIBLE);
        } else {
            holder.btn_addto.setEnabled(true);
            holder.btn_add.setEnabled(true);
            holder.textViewRating.setVisibility(View.INVISIBLE);
            // holder.ll_addremovebutton.setVisibility(View.VISIBLE);
        }


        String product_image = product.getProductImageUrl();

        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        double mrp = 0;
        double price = 0;


        try {
           /* mrp = Double.valueOf(product.getProductUnits().get(0).getProductMrp());
            price = Double.valueOf(product.getProductUnits().get(0).getProductSalesprice());*/
            mrp = Double.valueOf(product.getProductMrp());
            price = Double.valueOf(product.getProductSalesprice());
        } catch (Exception ex) {

        }

        //double price = (mrp * (100-disc))/100;

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_price.setText(" "+form.format(price));
        // holder.tv_price.setText(String.valueOf(price));

        holder.tv_mrpval.setText(form.format(mrp));
        //holder.tv_inr.setText(" ₹");
        holder.tv_mrp.setText("Rate : ₹ ");
        holder.tv_unitid.setText(product.getProductSkuUnitPriceId());

        //moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

        moq = 1;

        holder.btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    minteger = Integer.parseInt(holder.integer_number.getText().toString());

                    minteger = minteger + 1;
                    holder.integer_number.setText(String.valueOf(minteger));

                    AddToCart(holder.tv_unitid.getText().toString(), "1");

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

                        CartDeleteRequest(holder.tv_unitid.getText().toString());

                        //holder.btn_decrease.setEnabled(false);
                        holder.ll_addremovebutton.setVisibility(View.VISIBLE);
                        holder.ll_addremove.setVisibility(View.GONE);


                    } else {
                        minteger = Integer.parseInt(holder.integer_number.getText().toString());
                        minteger = minteger - 1;
                        holder.integer_number.setText(String.valueOf(minteger));


                        if (minteger < 1) {

                            CartDeleteRequest(holder.tv_unitid.getText().toString());
                        } else {
                            UpdateCart(holder.tv_unitid.getText().toString(), holder.integer_number.getText().toString());

                        }
                    }

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");
                String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                if (usermode.equalsIgnoreCase("B2B")) {
                    if (useractive.equalsIgnoreCase("guest")) {

                        holder.ll_addremove.setVisibility(View.GONE);
                        Intent i = new Intent();
                        i.setClass(mContext, RFQMobileActivity.class);
                        mContext.startActivity(i);

                    } else if (useractive.equalsIgnoreCase("active")) {
                        holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);

                        AddToCart(holder.tv_unitid.getText().toString(), "1");

                    } else {
                        CheckProfile(holder.tv_unitid.getText().toString(), String.valueOf("1"));

                    }
                } else {
                    if (useractive.equalsIgnoreCase("guest")) {
                        holder.ll_addremove.setVisibility(View.GONE);
                        Intent i = new Intent();
                        i.setClass(mContext, OneTapLogin.class);
                        mContext.startActivity(i);
                    }else {
                        holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);
                        AddToCart(holder.tv_unitid.getText().toString(), "1");
                    }
                }


            }
        });

        holder.btn_addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");
                String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");


                if (usermode.equalsIgnoreCase("B2C")) {

                    if (useractive.equalsIgnoreCase("guest")) {
                        holder.ll_addremove.setVisibility(View.GONE);
                        Intent i = new Intent();
                        i.setClass(mContext, OneTapLogin.class);
                        mContext.startActivity(i);
                    }else {
                        holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);
                        AddToCart(holder.tv_unitid.getText().toString(), "1");
                    }


                } else {
                    if (useractive.equalsIgnoreCase("guest")) {
                        // holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.GONE);

                        Intent i = new Intent();
                        i.setClass(mContext, RFQMobileActivity.class);
                        mContext.startActivity(i);

                        //  AddToCart(holder.tv_unitid.getText().toString(), "1");

                    } else if (useractive.equalsIgnoreCase("active")) {
                        holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);

                        AddToCart(holder.tv_unitid.getText().toString(), "1");
                    } else {
                        CheckProfile(holder.tv_unitid.getText().toString(), String.valueOf("1"));

                    }

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    private void CheckProfile(String productid, String qty) {

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
                            AddToCart(productid, qty);

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
                            i.setClass(mContext,ProductDetailsActivityB2B.class);
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

    private void AddToCart(String productunitid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        subscription_id=DailyBasketProductList.subscription_id;

        DailyBasketCartRequest dailyBasketCartRequest = new DailyBasketCartRequest();
        dailyBasketCartRequest.setProductSkuUnitPriceId(productunitid);
        dailyBasketCartRequest.setProductQuantity(qty);
        dailyBasketCartRequest.setSubscriptionId(subscription_id);
        dailyBasketCartRequest.setDatefrom(SelectedDate);

        try {

            Call<SubscriptionDailyBasketCartResponse> call=RetrofitUrlConnection.loadJSON(token).addproducttosubscription(dailyBasketCartRequest);

            call.enqueue(new Callback<SubscriptionDailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<SubscriptionDailyBasketCartResponse> call, Response<SubscriptionDailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //basket_productid=String.valueOf(response.body().getBasketData().getProductId());

                        Utilities.dismissDialog();

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
    private void UpdateCart(String productunitid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        DailyBasketCartRequest dailyBasketCartRequest=new DailyBasketCartRequest();
        dailyBasketCartRequest.setSubscriptionId(subscription_id);
        dailyBasketCartRequest.setProductQuantity(qty);
        dailyBasketCartRequest.setProductSkuUnitPriceId(productunitid);
        dailyBasketCartRequest.setDatefrom(SelectedDate);

        try {

            Call<SubscriptionDailyBasketCartResponse> call=RetrofitUrlConnection.loadJSON(token).updateproducttosubscription(dailyBasketCartRequest);

            call.enqueue(new Callback<SubscriptionDailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<SubscriptionDailyBasketCartResponse> call, Response<SubscriptionDailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                    }
                    else{
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<SubscriptionDailyBasketCartResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /*private void CartDeleteRequest(String productunitid) {

        Utilities.showLoading(mContext);
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        String deletetype="list";
        getBasketproductid();
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


        DailyBasketCartDeleteRequest basketDeleteRequest = new DailyBasketCartDeleteRequest();
        basketDeleteRequest.setBasketProductId(basket_productid);


        try {

            Call<DailyBasketCartDeleteResponse> call=RetrofitUrlConnection.loadJSON(token).delete_from_dailybasket(basketDeleteRequest);

            call.enqueue(new Callback<DailyBasketCartDeleteResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartDeleteResponse> call, Response<DailyBasketCartDeleteResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
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
*/

    private void CartDeleteRequest(String productid) {

        Utilities.showLoading(mContext);
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        String deletetype="list";
        //getBasketproductid();
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");


        DailyBasketCartDeleteRequest basketDeleteRequest = new DailyBasketCartDeleteRequest();
        basketDeleteRequest.setBasketProductId(productid);
        basketDeleteRequest.setSubscriptionId(subscription_id);
        basketDeleteRequest.setSubscriptionDate(SelectedDate);

        try {

            Call<DailyBasketCartDeleteResponse> call=RetrofitUrlConnection.loadJSON(token).cancelproductfromsubscription(basketDeleteRequest);

            call.enqueue(new Callback<DailyBasketCartDeleteResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartDeleteResponse> call, Response<DailyBasketCartDeleteResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
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


    private void getBasketproductid() {
        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");

        DailyBasketListRequest dailyBasketListRequest =new DailyBasketListRequest();
        dailyBasketListRequest.setSubscriptionId(subscription_id);
        try{

            Call<DailyBasketListResponse> call= RetrofitUrlConnection.loadJSON(token).getmydailybasket(dailyBasketListRequest);

            call.enqueue(new Callback<DailyBasketListResponse>() {
                @Override
                public void onResponse(Call<DailyBasketListResponse> call, Response<DailyBasketListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        //Log.e(TAG, "onResponse: Search"+jjj );
                        Utilities.dismissDialog();

                        //basket_productid=response.body().getBasketData().
                    }else
                    {
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketListResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DairyBasketViewHolder extends RecyclerView.ViewHolder {

        TextView tv_productid, tv_productname, textViewShortDesc, textViewRating,
                tv_mrpval, tv_pack, tv_minqty, tv_price, tv_packsize, tv_inr, tv_mrp, tv_discount, tv_unitid;
        ImageView imageView, iv_rx;
        EditText integer_number;
        CardView cv_product;
        Button btn_increase;
        Button btn_decrease;
        Button btn_addto;
        Button btn_add;
        LinearLayout ll_addremovebutton;
        LinearLayout ll_addremove;

        public DairyBasketViewHolder(View itemView) {
            super(itemView);

            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);
            tv_price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.imageView);
            tv_pack = itemView.findViewById(R.id.tv_pack);
            tv_minqty = itemView.findViewById(R.id.tv_minqty);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_packsize = itemView.findViewById(R.id.tv_packsize);
            tv_inr = itemView.findViewById(R.id.tv_inr);
            btn_addto = itemView.findViewById(R.id.btn_addto);
            btn_decrease = itemView.findViewById(R.id.btn_decrease);
            btn_increase = itemView.findViewById(R.id.btn_increase);
            integer_number = itemView.findViewById(R.id.integer_number);
            btn_add = itemView.findViewById(R.id.btn_add);
            ll_addremovebutton = itemView.findViewById(R.id.ll_addremovebutton);
            ll_addremove = itemView.findViewById(R.id.ll_addremove);
            iv_rx = itemView.findViewById(R.id.iv_rx);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tv_unitid = itemView.findViewById(R.id.tv_unitid);


            String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");
            String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

            if (profilemode.equalsIgnoreCase("B2B")) {
                if (useractive.equalsIgnoreCase("guest")) {
                    tv_inr.setVisibility(View.GONE);
                    tv_price.setVisibility(View.INVISIBLE);
                    tv_discount.setVisibility(View.GONE);
                    tv_mrp.setVisibility(View.INVISIBLE);
                    tv_mrpval.setVisibility(View.INVISIBLE);
                    btn_addto.setText("REQUEST QUOTE");
                    btn_add.setEnabled(false);

                }
                else {
                    tv_inr.setVisibility(View.GONE);
                    tv_price.setVisibility(View.VISIBLE);
                    tv_discount.setVisibility(View.VISIBLE);
                    tv_mrpval.setBackgroundResource(R.drawable.strike_through);
                }
            } else {
                tv_inr.setVisibility(View.GONE);
                tv_price.setVisibility(View.VISIBLE);
                tv_discount.setVisibility(View.VISIBLE);
                tv_mrp.setVisibility(View.VISIBLE);
                tv_mrpval.setVisibility(View.VISIBLE);
                tv_mrpval.setBackgroundResource(R.drawable.strike_through);

            }

            // String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

            /*cv_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");

                    String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        if(useractive.equalsIgnoreCase("guest"))
                        {
                            Intent i = new Intent();
                            i.setClass(mContext, ProductDetailsActivityGuest.class);
                            i.putExtra("productname", tv_productname.getText().toString());
                            i.putExtra("productid", tv_productid.getText().toString());
                            mContext.startActivity(i);
                        }
                        else
                        {
                            Intent i = new Intent();
                            i.setClass(mContext, ProductDetailsActivity.class);
                            i.putExtra("productname", tv_productname.getText().toString());
                            i.putExtra("productid", tv_productid.getText().toString());
                            mContext.startActivity(i);
                        }


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_productid.getText().toString());
                        mContext.startActivity(i);

                    }

                    // Toast.makeText(mContext, "Position" + tv_productname.getText().toString(), Toast.LENGTH_SHORT).show();

                    // Toast.makeText(mContext, "Position" + tv_productname.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
*/

        /*    btn_addto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {


                        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

                        int chk = Integer.valueOf(integer_number.getText().toString());


                        if (chk > 0) {
                            if (useractive.equalsIgnoreCase("active")) {
                                AddToCart(tv_unitid.getText().toString(), String.valueOf(chk));
                                integer_number.clearFocus();
                            } else {
                                CheckProfile(tv_unitid.getText().toString(), String.valueOf(chk));

                            }
                        }

                    } else {
                        Utilities.showNetworkError(mContext);
                    }
                    //Toast.makeText(mContext, tv_productname.getText().toString() +" Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
*/

        }
    }

}
