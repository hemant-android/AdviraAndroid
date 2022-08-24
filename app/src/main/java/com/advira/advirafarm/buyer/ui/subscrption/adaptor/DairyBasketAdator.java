package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddNewAddressActivity;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartResponse;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.guest.RFQMobileActivity;
import com.advira.advirafarm.buyer.ui.masterapi.CityList;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.SearchActivity;
import com.advira.advirafarm.buyer.ui.product.adapter.ProductUnitAdapter;
import com.advira.advirafarm.buyer.ui.product.adapter.SearchListAdapter;
import com.advira.advirafarm.buyer.ui.product.api.ProductUnit;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.registration.DocumentUploadActivity;
import com.advira.advirafarm.buyer.ui.registration.adapter.CityAdapter;
import com.advira.advirafarm.buyer.ui.registration.adapter.StateAdapter;
import com.advira.advirafarm.buyer.ui.registration.profile.BusinessDetailsActivity;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.SkuUmit;
import com.advira.advirafarm.buyer.ui.subscrption.api.product_dairybasket;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DairyBasketAdator extends RecyclerView.Adapter<DairyBasketAdator.DairyBasketViewHolder> implements IConsts {

    /*public static TextView tv_productid, tv_productname, textViewShortDesc, textViewRating,
            tv_mrpval, tv_pack, tv_minqty, tv_price, tv_packsize, tv_inr, tv_mrp, tv_discount, tv_unitid;

    public static ImageView imageView, iv_rx;
    public static EditText integer_number;
    public static CardView cv_product;
    public static Button btn_increase;
    public static Button btn_decrease;
    public static Button btn_addto;
    public static Button btn_add;
    public static Spinner spn_weight;
    public static LinearLayout ll_addremovebutton;
    public static LinearLayout ll_addremove;
    public static RecyclerView rl_packsize;*/
    public  static String subscription_id="";
    String basket_productid="";
    private Integer cacityid = 0;

    public static String packid="";
    public static  String priceval="";
    public static  String mrpval="";
    //Spinner spn_packsize;


    AlertDialog.Builder builder;
    //this context we will use to inflate the layout
    private static Context mContext;
    private int minteger = 0;
    private int moq = 10;
    //we are storing all the orders in a list
    private List<product_dairybasket> productList;
    private List<SkuUmit> productUnitslist;

    // private List<CartDatum> cartList;

    WeightAdapter productUnitAdapter;
    PackAdaptor arrayAdapterPack;
    private List<SkuUmit> arrayListPack;

    //ProductUnitAdapter productUnitAdapter;


    public DairyBasketAdator(Context mContext, List<product_dairybasket> productList) {
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

        product_dairybasket product = productList.get(position);
        holder.tv_productname.setText(product.getProductname());
        holder.textViewShortDesc.setText(product.getProductVariety().replaceAll("##", "\n"));
        holder.textViewRating.setText(String.valueOf(product.getSkuUmit().get(0).getProductInstock()));
        holder.tv_productid.setText(product.getSkuId());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText("1");
        holder.tv_unitid.setText(packid);
        Log.e(TAG, "onBindViewHolder: "+ holder.tv_unitid.getText().toString() );
        String productInstock = product.getSkuUmit().get(0).getProductInstock();

        Log.e(TAG, "onClick:123456 "+position/*product.getProductSkuUnitPriceId()+"\n-"+holder.tv_mrp.getText().toString()*/ );

        productUnitslist = new ArrayList<>();
        productUnitAdapter = new WeightAdapter(mContext, productUnitslist);

        List<SkuUmit> mListData = product.getSkuUmit();

        if (mListData != null && mListData.size() > 0) {
            productUnitslist.addAll(mListData);
        }

        holder.rv_packsize.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.rv_packsize.setNestedScrollingEnabled(false);
        holder.rv_packsize.setAdapter(productUnitAdapter);

        if (productInstock.equalsIgnoreCase("Out-of-Stock")) {

            holder.btn_addto.setEnabled(false);
            holder.btn_add.setEnabled(false);
            holder.textViewRating.setVisibility(View.VISIBLE);
        } else {
            holder.btn_addto.setEnabled(true);
            holder.btn_add.setEnabled(true);
            holder.textViewRating.setVisibility(View.GONE);
        }


        String product_image = product.getSkuUmit().get(0).getProductImageUrl();

        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        double mrp = 0;
        double price = 0;


        try {
            mrp = Double.valueOf(product.getSkuUmit().get(0).getProductMrp());
            price = Double.valueOf(product.getSkuUmit().get(0).getProductSalesprice());

        } catch (Exception ex) {

        }


        DecimalFormat form = new DecimalFormat("0.00");

        holder.tv_price.setText(" "+form.format(price));
        holder.tv_mrpval.setText(form.format(mrp));

        holder.tv_mrp.setText("Rate : ₹ ");
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
       /* holder.spn_packsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                } else {
                    arrayListPack = new ArrayList<>();
                    arrayListPack.add(new SkuUmit(0, "Select"," City "));

                    packid=String.valueOf(arrayListPack.get(holder.spn_packsize.getSelectedItemPosition()).getProductSkuUnitPriceId());

                    //String stateid = String.valueOf(arrayListState.get(spn_castate.getSelectedItemPosition()).getId());

                    for (int i = 0; i < arrayListPack.size() && !packid.equalsIgnoreCase("0"); i++) {

                        if (arrayListPack.get(i).getProductSkuUnitPriceId().equals(packid)) {
                            int packsizeid = arrayListPack.get(i).getProductSkuUnitPriceId();
                            String packunit = arrayListPack.get(i).getProductUnits();
                            String packunittype=arrayListPack.get(i).getProductUnitType();
                            arrayListPack.add(new SkuUmit(packsizeid, packunit,packunittype));
                        }
                    }

                    //holder.spn_packsize.setAdapter(arrayAdapterPack);

                    //arrayAdapterPack = new PackAdaptor(DairyBasketAdator.this, R.layout.layout_profile, R.id.profile_name, arrayListPack);
                    holder.spn_packsize.setAdapter(arrayAdapterPack);

                    for (int i = 0; i < arrayListPack.size(); i++) {
                        if (arrayListPack.get(i).getProductSkuUnitPriceId() == (cacityid)) {
                            holder.spn_packsize.setSelection(i);
                            break;
                        }
                    }
                }


                *//*if (position == 0) {

                } else {
                    packid=String.valueOf(arrayListPack.get(holder.spn_packsize.getSelectedItemPosition()).getProductSkuUnitPriceId());

                    //PopulateCityPincode(cityid);
                }*//*
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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
        subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        DailyBasketCartRequest dailyBasketCartRequest = new DailyBasketCartRequest();
        dailyBasketCartRequest.setProductSkuUnitPriceId(productunitid);
        dailyBasketCartRequest.setProductQuantity(qty);
        dailyBasketCartRequest.setSubscriptionId(subscription_id);

        try {

            Call<DailyBasketCartResponse> call=RetrofitUrlConnection.loadJSON(token).addtodailybasket(dailyBasketCartRequest);

            call.enqueue(new Callback<DailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartResponse> call, Response<DailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        basket_productid=String.valueOf(response.body().getBasketData().getProductId());



                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        //SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                       /* try {
                            SearchActivity.tv_cartcount.setText(response.body().getCartSize().toString());
                            MainActivityNav.text.setText(response.body().getCartSize().toString());
                            MainActivityNav.text.setText(response.body().getCartSize().toString());
                        } catch (Exception ex) {

                        }
                        try {
                            //MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                            MainActivityGuestNav.text.setText(response.body().getCartSize().toString());
                        } catch (Exception ex) {

                        }*/

                        Utilities.dismissDialog();
                        // CalculatePrice();

                    } else {

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketCartResponse> call, Throwable t) {

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

        try {

            Call<DailyBasketCartResponse> call=RetrofitUrlConnection.loadJSON(token).updatedailybasket(dailyBasketCartRequest);

            call.enqueue(new Callback<DailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartResponse> call, Response<DailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                    }
                    else{
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DailyBasketCartResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void CartDeleteRequest(String productunitid) {

        Utilities.showLoading(mContext);
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        String deletetype="list";

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");


        BasketCartDeleteRequest basketDeleteRequest = new BasketCartDeleteRequest();
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




    static class DairyBasketViewHolder extends RecyclerView.ViewHolder {

        /*ImageView imageView, iv_rx;
        EditText integer_number;
        CardView cv_product;
        Button btn_increase;
        Button btn_decrease;
        Button btn_addto;
        Button btn_add;*/
        Spinner spn_weight,spn_packsize;
        RecyclerView rv_packsize;

        public static TextView tv_productid, tv_productname, textViewShortDesc, textViewRating,
                tv_mrpval, tv_pack, tv_minqty, tv_price, tv_packsize, tv_inr, tv_mrp, tv_discount, tv_unitid;

        public static ImageView imageView, iv_rx;
        public static EditText integer_number;
        public static CardView cv_product;
        public static Button btn_increase;
        public static Button btn_decrease;
        public static Button btn_addto;
        public static Button btn_add;
        //public static Spinner spn_weight;
        public static LinearLayout ll_addremovebutton;
        public static LinearLayout ll_addremove;
        public static RecyclerView rl_packsize;

        public DairyBasketViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);
            tv_price = itemView.findViewById(R.id.tv_price);
            //spn_packsize = itemView.findViewById(R.id.spn_packsize);
            imageView = itemView.findViewById(R.id.imageView);
            tv_pack = itemView.findViewById(R.id.tv_pack);
            tv_minqty = itemView.findViewById(R.id.tv_minqty);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_packsize = itemView.findViewById(R.id.tv_packsize);
            //spn_weight=itemView.findViewById(R.id.spn_weight);
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
            //rv_packsize=itemView.findViewById(R.id.rv_packsize);

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
        }
    }
}
