package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.subscrption.SubscriptionPreviewActivity;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketCartDeleteRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartDeleteResponse;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartRequest;
import com.advira.advirafarm.buyer.ui.subscrption.api.DailyBasketCartResponse;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SubscriptionPreviewAdaptor extends RecyclerView.Adapter<SubscriptionPreviewAdaptor.SubscriptionPreviewViewHolder> implements IConsts {

    private int minteger = 0;
    private int moq = 10;
    //private String previewtotal="0";

    String membershipName="";

    private List<BasketDatum> basketList;
    private static Context mContext;
    String startDate;
    String endDate;
    long subscriptionDays;
    //String subscription_id="";
    String basket_productid="";
    int itemcount=0;

    public SubscriptionPreviewAdaptor(Context mContext, List<BasketDatum> basketList) {
        this.mContext = mContext;
        this.basketList = basketList;
    }


    @NonNull
    @Override
    public SubscriptionPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_cart, null);
        return new SubscriptionPreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionPreviewViewHolder holder, int position) {
        BasketDatum product = basketList.get(position);
        //binding the data with the view holder views
        holder.tv_prodcartid.setText(String.valueOf(product.getBasketProductId()));
        holder.tv_productname.setText(product.getProdctName());
        // holder.tv_stock.setText(product.getProductInfo().get(0).getProductInstock());
        holder.tv_pack.setText(product.getpUnits()+product.getpUnitType());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText(product.getpCount());
        holder.tv_inr.setText(" ₹");
        holder.tv_mrp.setText("Rate : ₹");
        holder.tv_unitid.setText(String.valueOf(product.getProductId()));

        startDate=SubscriptionPreviewActivity.startDate;
        endDate=SubscriptionPreviewActivity.endDate;
        subscriptionDays=Daybetween(startDate,endDate)+1;
        Log.e(TAG, "onBindViewHolder: selected days"+startDate+"   "+endDate );
        SubscriptionPreviewActivity.tv_daysval.setText(String.valueOf("Total Days : "+subscriptionDays));


        //holder.textViewRating.setText(String.valueOf(product.getProductInstock()));
        //holder.tv_discount.setText(product.getProductMrpDiscountLabel());
        //holder.tv_discount.setVisibility(View.GONE);
        holder.tv_inr.setVisibility(View.GONE);
        holder.textViewRating.setVisibility(View.GONE);

        /*String productInstock = product.getProductInstock();
        String isDeal=product.getIsDeal();


        if (productInstock.equalsIgnoreCase("Out-of-Stock")) {

            holder.ll_addremove.setVisibility(View.INVISIBLE);
            holder.textViewRating.setVisibility(View.VISIBLE);
        } else {
            holder.ll_addremove.setVisibility(View.VISIBLE);
            holder.textViewRating.setVisibility(View.INVISIBLE);
        }

        if(isDeal.equalsIgnoreCase("Yes"))
        {
            //holder.ll_addremove.setEnabled(false);
            holder.btn_decrease.setEnabled(false);
            holder.btn_increase.setEnabled(false);
        }else {
            //holder.ll_addremove.setEnabled(true);
            holder.btn_decrease.setEnabled(true);
            holder.btn_increase.setEnabled(true);
        }
*/
        String product_image = product.getProductImageUrl();


        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }


      /*  String isrx = product.getProductInfo().get(0).getIsRx();
        if (isrx.equalsIgnoreCase("yes")) {
            holder.iv_rx.setVisibility(View.VISIBLE);
        } else {
            holder.iv_rx.setVisibility(View.INVISIBLE);
        }*/


        double price = Double.parseDouble(product.getpPricePerPack());
        double totprice = Double.parseDouble(product.getpTotalPrice());
        double mrp = Double.parseDouble(product.getpPricePerPack());

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_price.setText(form.format(price));
        holder.tv_itempriceval.setText(form.format(totprice));

        moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

       /* if (product.getProductInfo().get(0).getProductMoq().equalsIgnoreCase(product.getProductQuantity())) {
            holder.btn_decrease.setVisibility(View.INVISIBLE);
        }*/

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Remove").setTitle("Remove Item From Cart");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you wish to continue ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Utilities.hideKeyboard(mContext);
                                if (Utilities.isNetworkConnected(mContext)) {

                                    CartDeleteRequest(holder.tv_unitid.getText().toString(),product);
                                } else {
                                    Utilities.showNetworkError(mContext);
                                }


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();


                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Remove Item From Cart");
                alert.show();

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));

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

                    if (Integer.parseInt(holder.integer_number.getText().toString()) > moq) {
                        holder.btn_decrease.setVisibility(View.VISIBLE);
                    }

                    if (Integer.parseInt(holder.integer_number.getText().toString()) >= moq) {


                        UpdateCart(holder.tv_unitid.getText().toString(), holder.integer_number.getText().toString(),product);

                        double price = Double.parseDouble(holder.tv_price.getText().toString());
                        double qty = Double.parseDouble(holder.integer_number.getText().toString());
                        // double pack = Double.parseDouble(holder.tv_boxsize.getText().toString());
                        double totprice = price * qty ;

                        DecimalFormat form = new DecimalFormat("0.00");
                        holder.tv_itempriceval.setText(form.format(totprice));


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

                    moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

                    minteger = Integer.parseInt(holder.integer_number.getText().toString());
                    minteger = minteger - 1;
                    holder.integer_number.setText(String.valueOf(minteger));


                    if (Integer.parseInt(holder.integer_number.getText().toString()) == moq) {


                        // holder.btn_decrease.setVisibility(View.INVISIBLE);
                    }

                    if (Integer.parseInt(holder.integer_number.getText().toString()) < moq) {

                        CartDeleteRequest(holder.tv_unitid.getText().toString(),product);

                    } else if (Integer.parseInt(holder.integer_number.getText().toString()) >= moq) {

                        UpdateCart(holder.tv_unitid.getText().toString(), holder.integer_number.getText().toString(),product);

                        double price = Double.parseDouble(holder.tv_price.getText().toString());
                        double qty = Double.parseDouble(holder.integer_number.getText().toString());
                        //double pack = Double.parseDouble(holder.tv_boxsize.getText().toString());
                        double totprice = price * qty ;

                        DecimalFormat form = new DecimalFormat("0.00");
                        holder.tv_itempriceval.setText(form.format(totprice));


                    }
                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        /*holder.integer_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    ShowQtyDialogue(product);

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });*/

        //getTotalcartvalue();
        CalculatePrice();

    }

    private void AddToCart(String productunitid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        String subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        DailyBasketCartRequest dailyBasketCartRequest = new DailyBasketCartRequest();
        dailyBasketCartRequest.setProductSkuUnitPriceId(productunitid);
        dailyBasketCartRequest.setProductQuantity(qty);
        dailyBasketCartRequest.setSubscriptionId(subscription_id);

        try {

            Call<DailyBasketCartResponse> call= RetrofitUrlConnection.loadJSON(token).addtodailybasket(dailyBasketCartRequest);

            call.enqueue(new Callback<DailyBasketCartResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartResponse> call, Response<DailyBasketCartResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        basket_productid=String.valueOf(response.body().getBasketData().getProductId());

                        Utilities.dismissDialog();

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
    private void UpdateCart(String productunitid, String qty, BasketDatum item) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        String subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

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
                        int position = basketList.indexOf(item);


                        item.setpCount(response.body().getBasketData().getpCount());
                        item.setpTotalPrice(response.body().getBasketData().getpTotalPrice());


                        basketList.set(position, item);
                        SubscriptionPreviewAdaptor cartListAdapter = new SubscriptionPreviewAdaptor(mContext, basketList);
                        cartListAdapter.notifyDataSetChanged();

                        CalculatePrice();
                    }
                    else{
                        minteger = minteger + 1;
                        Utilities.dismissDialog();
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

    private void CartDeleteRequest(String productunitid, BasketDatum item) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String subscription_id=SharedPrefUtil.getSubscriptionID(mContext,SHARED_PREF_SubscriptionID,"");

        BasketCartDeleteRequest basketDeleteRequest = new BasketCartDeleteRequest();
        basketDeleteRequest.setBasketProductId(productunitid);
        basketDeleteRequest.setSubscriptionId(subscription_id);

        try {

            Call<DailyBasketCartDeleteResponse> call=RetrofitUrlConnection.loadJSON(token).delete_from_dailybasket(basketDeleteRequest);

            call.enqueue(new Callback<DailyBasketCartDeleteResponse>() {
                @Override
                public void onResponse(Call<DailyBasketCartDeleteResponse> call, Response<DailyBasketCartDeleteResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        int position = basketList.indexOf(item);
                        basketList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, basketList.size());

                        if (basketList.size() > 0) {
                            //SharedPrefUtil.setCartItemStock(mContext, SHARED_PREF_CartItemStock, "In stock");
                            SubscriptionPreviewAdaptor cartListAdapter = new SubscriptionPreviewAdaptor(mContext, basketList);
                            cartListAdapter.notifyDataSetChanged();
                            SubscriptionPreviewActivity.recyclerView.setAdapter(cartListAdapter);

                            /*try {
                                for (int i = 0; i < basketList.size(); i++) {
                                    if (basketList.get(i).getProductInstock().equalsIgnoreCase("Out-of-Stock")) {
                                        SharedPrefUtil.setCartItemStock(mContext, SHARED_PREF_CartItemStock, "out of stock");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {

                            }*/

                        } else {
                            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                            SubscriptionPreviewActivity.recyclerView.setLayoutManager(layoutManager1);

                        }

                        CalculatePrice();
                        /*if(cartList.size()>0) {
                            CartActivity.text.setText(String.valueOf(cartList.size()));
                            //MainActivityNav.text.setText(String.valueOf(cartList.size()));
                        }else{
                            CartActivity.text.setText("");
                            //MainActivityNav.text.setText("");
                        }*/
                        Utilities.dismissDialog();
                        // Singleton.getInstance().showShortToast(mContext, response.body().getMessage());


                    } else {
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
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

    public class SubscriptionPreviewViewHolder extends RecyclerView.ViewHolder {

        TextView tv_productname, tv_stock;
        TextView integer_number;
        ImageView imageView, iv_rx;
        Button btn_increase;
        Button btn_decrease;
        ImageView btn_remove;
        TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
                tv_prodid,tv_prodcartid, tv_inr, tv_boxsize, tv_mrp, textViewRating,tv_unitid,tv_discount;
        LinearLayout ll_addremove;

        public SubscriptionPreviewViewHolder(@NonNull View itemView) {
            super(itemView);

            membershipName=SharedPrefUtil.getMembership(mContext,SHARED_PREF_MemberShip,"");
            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_stock = itemView.findViewById(R.id.tv_stock);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);
            tv_price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.imageView);
            tv_pack = itemView.findViewById(R.id.tv_pack);
            tv_minqty = itemView.findViewById(R.id.tv_minqty);
            tv_itempriceval = itemView.findViewById(R.id.tv_itempriceval);
            tv_prodid = itemView.findViewById(R.id.tv_prodid);
            tv_prodcartid=itemView.findViewById(R.id.tv_prodcartid);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            btn_decrease = itemView.findViewById(R.id.btn_decrease);
            btn_increase = itemView.findViewById(R.id.btn_increase);
            integer_number = itemView.findViewById(R.id.integer_number);
            tv_inr = itemView.findViewById(R.id.tv_inr);
            tv_boxsize = itemView.findViewById(R.id.tv_boxsize);
            iv_rx = itemView.findViewById(R.id.iv_rx);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            ll_addremove = itemView.findViewById(R.id.ll_addremove);
            tv_discount= itemView.findViewById(R.id.tv_discount);
            tv_unitid = itemView.findViewById(R.id.tv_unitid);
            tv_mrpval.setBackgroundResource(R.drawable.strike_through);

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

        itemcount=basketList.size();
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

        if (basketList.size() > 0) {
            /*SubscriptionPreviewActivity.rl_content2.setVisibility(View.VISIBLE);
            SubscriptionPreviewActivity.rl_noitems.setVisibility(View.GONE);*/
            //CartActivity.tv_rv1.setText("My Cart " + "(" + cartList.size() + ")");
            for (int i = 0; i < basketList.size(); i++) {

                itemprice = Double.parseDouble(basketList.get(i).getpTotalPrice());
                carttotal = carttotal + itemprice;

                /*itemmrp = Double.parseDouble(basketList.get(i).getpPricePerPack());
                mrptotal = mrptotal + itemmrp;*/

                itemtax = Double.parseDouble(basketList.get(i).getpTax());
                totaltax = totaltax + (itemprice * itemtax * .01);
            }

            /*if(membershipName!=null && membershipName.length()>0){
                if (carttotal <= 99) {
                    delicharge = 20;
                } else {
                    delicharge = 0;
                }

            }else {
                if (carttotal <= 250) {
                    delicharge = 20;
                } else {
                    delicharge = 0;
                }
            }*/

            amountpayable = carttotal*subscriptionDays;//24.9.2021
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
            SubscriptionPreviewActivity.tv_priceval.setText("₹ " + form.format(carttotal));

            //AddSubscription.tv_priceval.setText("₹ " + form.format(carttotal));
            //CartActivity.tv_gstval.setText("+ ₹ " + form.format(totaltax));
            /*if(delicharge ==0){
                CartActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                CartActivity.tv_deliveryval.setText("+ ₹ 0.00");
            }
            else{
                CartActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                CartActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge));
            }*/

            SubscriptionPreviewActivity.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
            //AddSubscription.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
           // CartActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));
            //CartActivity.tv_footertotalitem.setText(cartList.size() + " ITEMS");

            /*profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");

            if(profilemode.equalsIgnoreCase("B2B"))
            {
                SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, String.valueOf(cartList.size()));
            }
            else
            {
                SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, String.valueOf(cartList.size()));
            }
            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, String.valueOf(cartList.size()));
            try {
                MainActivityNav.text.setText(String.valueOf(cartList.size()));
                CartActivity.text.setText(String.valueOf(cartList.size()));
            } catch (Exception ex) {

            }*/

            /*try {

                MainActivityGuestNav.text.setText(String.valueOf(cartList.size()));


            } catch (Exception ex) {

            }

            try {

                ProductDetailsActivityGuest.text.setText(String.valueOf(cartList.size()));


            } catch (Exception ex) {

            }

            try {

                CartActivity.text.setText(String.valueOf(cartList.size()));


            } catch (Exception ex) {

            }



            try {
                ProductDetailsActivity.text.setText(String.valueOf(cartList.size()));
            } catch (Exception ex) {

            }

*/
        } else {
            SubscriptionPreviewActivity.tv_priceval.setText("₹ 0");
            //AddSubscription.tv_priceval.setText("₹ 0");
            //CartActivity.tv_gstval.setText("+ ₹ 0");
            SubscriptionPreviewActivity.tv_totalpaidval.setText("₹ 0");
            //AddSubscription.tv_totalpaidval.setText("₹ 0");
            //CartActivity.rl_content2.setVisibility(View.GONE);
            //CartActivity.rl_noitems.setVisibility(View.VISIBLE);
            //CartActivity.tv_rv1.setText("");

            /*try {

                MainActivityNav.text.setText("");
                MainActivityNav.text.setText("");

            } catch (Exception ex) {

            }


            try {
                ProductDetailsActivity.text.setText("");
            } catch (Exception ex) {

            }


            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");*/


        }
    }
    public long Daybetween(String date1,String date2)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date Date1=new Date();
        Date Date2 = new Date();
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
    }
}
