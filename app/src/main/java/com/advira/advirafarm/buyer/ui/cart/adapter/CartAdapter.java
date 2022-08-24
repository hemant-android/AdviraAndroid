package com.advira.advirafarm.buyer.ui.cart.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.api.CartData;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartListRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartListResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartResponse;
import com.advira.advirafarm.buyer.ui.cart.api.Deliverycharges;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.api.ProductDetailsRequest;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements IConsts {


    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    String profilemode="";
    String usertype="";
    double ordervalue;
    String membershipName="";


    //we are storing all the products in a list
    private List<CartDatum> cartList;
    //private List<Deliverycharges> deliverycharge;


    public CartAdapter(Context mContext, List<CartDatum> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_cart, null);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        //getting the product of the specified position

        //Deliverycharges dcharge=deliverycharge.get(position);

        CartDatum product = cartList.get(position);
        //binding the data with the view holder views
        holder.tv_prodid.setText(product.getProductId());
        holder.tv_prodcartid.setText(product.getCartProductId());
        holder.tv_productname.setText(product.getProductName());
        // holder.tv_stock.setText(product.getProductInfo().get(0).getProductInstock());
        holder.tv_pack.setText(product.getProductUnits());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText(product.getProductQuantity());
        holder.tv_inr.setText(" ₹");
        holder.tv_mrp.setText("Rate : ₹");
        holder.tv_unitid.setText(product.getProductUnitId());
        holder.textViewRating.setText(String.valueOf(product.getProductInstock()));
        holder.tv_discount.setText(product.getProductMrpDiscountLabel());
        //holder.tv_discount.setVisibility(View.GONE);
        holder.tv_inr.setVisibility(View.GONE);

        String productInstock = product.getProductInstock();
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

        String product_image = product.getProductImage();


        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        double price = Double.parseDouble(product.getProductPrice());
        double totprice = Double.parseDouble(product.getTotalPrice());
        double mrp = Double.parseDouble(product.getProductMrp());

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

                                    CartDeleteRequest(holder.tv_prodcartid.getText().toString(), product);
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


                        UpdateCart(holder.tv_unitid.getText().toString(), holder.integer_number.getText().toString(), product);

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

                        CartDeleteRequest(holder.tv_prodcartid.getText().toString(), product);

                    } else if (Integer.parseInt(holder.integer_number.getText().toString()) >= moq) {

                        UpdateCart(holder.tv_unitid.getText().toString(), holder.integer_number.getText().toString(), product);

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

        holder.integer_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    ShowQtyDialogue(product);

                } else {
                    Utilities.showNetworkError(mContext);
                }

            }
        });

        //getTotalcartvalue();
        CalculatePrice();
    }


    @Override
    public int getItemCount() {
        return cartList.size();

    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productname, tv_stock;
        TextView integer_number;
        ImageView imageView, iv_rx;
        Button btn_increase;
        Button btn_decrease;
        ImageView btn_remove;
        TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
                tv_prodid,tv_prodcartid, tv_inr, tv_boxsize, tv_mrp, textViewRating,tv_unitid,tv_discount;
        LinearLayout ll_addremove;

        public CartViewHolder(View itemView) {
            super(itemView);
            profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
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



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2C");
                    //Log.e(TAG, "onClick: cart"+profilemode );

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-2", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        mContext.startActivity(i);
                    }

                    //Toast.makeText(mContext, "Position" + tv_productname.getText().toString()+"\n"+tv_prodid.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void getTotalcartvalue() {

        Utilities.showLoading(mContext);


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
        CartListRequest cartListRequest = new CartListRequest();
        cartListRequest.setUserCartType(profilemode);

        try {

            Call<CartListResponse> call = RetrofitUrlConnection.loadJSON(token).getmycart(cartListRequest);
            call.enqueue(new Callback<CartListResponse>() {
                @Override
                public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        ordervalue= Double.parseDouble(response.body().getOrderValue());
                        //CalculatePrice();
                        Log.e(TAG, "CalculatePrice: deliprice" +" 2---"+ordervalue );
                    }else {
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<CartListResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdateCart(String productunitid, String qty, CartDatum item) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductUnitId(productunitid);
        cartRequest.setProductQuantity(qty);
        cartRequest.setUserCartType(profilemode);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).updatecart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showShortToast(mContext, "Cart updated successfully");//remove toast


                        if(profilemode.equalsIgnoreCase("B2B"))
                        {
                            SharedPrefUtil.setCartCountB2B(mContext, SHARED_PREF_CARTCOUNTB2B, response.body().getCartSize().toString());

                        }
                        else
                        {
                            SharedPrefUtil.setCartCountB2C(mContext, SHARED_PREF_CARTCOUNTB2C, response.body().getCartSize().toString());

                        }


                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        try{

                            MainActivityNav.text.setText(response.body().getCartSize().toString());
                            //MainActivityNav.text.setText(response.body().getCartSize().toString());

                        }
                        catch (Exception ex)
                        {

                        }

                        try{

                            //MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                            MainActivityGuestNav.text.setText(response.body().getCartSize().toString());

                        }
                        catch (Exception ex)
                        {

                        }
                        Utilities.dismissDialog();

                        int position = cartList.indexOf(item);


                        item.setProductQuantity(response.body().getCartData().getProductQuantity());
                        item.setTotalPrice(response.body().getCartData().getTotalPrice());


                        cartList.set(position, item);
                        CartAdapter cartListAdapter = new CartAdapter(mContext, cartList);
                        cartListAdapter.notifyDataSetChanged();

                        CalculatePrice();

                    } else {


                        minteger = minteger + 1;
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


    private void CartDeleteRequest(String productunitid, CartDatum item) {

        Utilities.showLoading(mContext);
        profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String deletetype="cart";

        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest();
        cartDeleteRequest.setProductUnitId(productunitid);
        cartDeleteRequest.setUserCartType(profilemode);
        cartDeleteRequest.setDeletefrom(deletetype);

        Gson gson = new Gson();
        String vakk = gson.toJson(cartDeleteRequest).toString();
        String jjj=vakk;

        try {

            Call<CartDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).delete_from_cart(cartDeleteRequest);

            call.enqueue(new Callback<CartDeleteResponse>() {
                @Override
                public void onResponse(Call<CartDeleteResponse> call, Response<CartDeleteResponse> response) {
                    //Log.e(TAG, "onResponse: cartdelete"+"\n"+jjj );

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        int position = cartList.indexOf(item);
                        cartList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartList.size());

                        if (cartList.size() > 0) {
                            SharedPrefUtil.setCartItemStock(mContext, SHARED_PREF_CartItemStock, "In stock");
                            CartAdapter cartListAdapter = new CartAdapter(mContext, cartList);
                            cartListAdapter.notifyDataSetChanged();
                            CartActivity.recyclerView.setAdapter(cartListAdapter);

                            try {
                                for (int i = 0; i < cartList.size(); i++) {
                                    if (cartList.get(i).getProductInstock().equalsIgnoreCase("Out-of-Stock")) {
                                        SharedPrefUtil.setCartItemStock(mContext, SHARED_PREF_CartItemStock, "out of stock");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {

                            }

                        } else {
                            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext);
                            CartActivity.recyclerView.setLayoutManager(layoutManager1);

                        }

                        CalculatePrice();
                        if(cartList.size()>0) {
                            CartActivity.text.setText(String.valueOf(cartList.size()));
                            //MainActivityNav.text.setText(String.valueOf(cartList.size()));
                        }else{
                            CartActivity.text.setText("");
                            //MainActivityNav.text.setText("");
                        }
                        Utilities.dismissDialog();
                        // Singleton.getInstance().showShortToast(mContext, response.body().getMessage());


                    } else {
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                        Utilities.dismissDialog();
                    }
                }

                @Override
                public void onFailure(Call<CartDeleteResponse> call, Throwable t) {

                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
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

        if (cartList.size() > 0) {
            CartActivity.rl_content2.setVisibility(View.VISIBLE);
            CartActivity.rl_noitems.setVisibility(View.GONE);
            CartActivity.tv_rv1.setText("My Cart " + "(" + cartList.size() + ")");
            for (int i = 0; i < cartList.size(); i++) {

                itemprice = Double.parseDouble(cartList.get(i).getTotalPrice());
                carttotal = carttotal + itemprice;

                itemmrp = Double.parseDouble(cartList.get(i).getProductPrice());
                mrptotal = mrptotal + itemmrp;

                itemtax = Double.parseDouble(cartList.get(i).getProductTax().replaceAll("%", ""));
                totaltax = totaltax + (itemprice * itemtax * .01);
            }

            if(membershipName!=null && membershipName.length()>0){
                if (carttotal < 99) {
                    delicharge = 20;
                } else {
                    delicharge = 0;
                }

            }else {
                if (carttotal < 250) {
                    delicharge = 20;
                } else {
                    delicharge = 0;
                }
            }

            amountpayable = carttotal+delicharge;//24.9.2021

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

            CartActivity.tv_priceval.setText("₹ " + form.format(carttotal));
            //CartActivity.tv_gstval.setText("+ ₹ " + form.format(totaltax));
            if(delicharge ==0){
                CartActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                CartActivity.tv_deliveryval.setText("+ ₹ 0.00");
            }
            else{
                CartActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                CartActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge));
            }

            CartActivity.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
            CartActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));
            CartActivity.tv_footertotalitem.setText(cartList.size() + " ITEMS");

           profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "");

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

            }

            try {

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


        } else {
            CartActivity.tv_priceval.setText("₹ 0");
            //CartActivity.tv_gstval.setText("+ ₹ 0");
            CartActivity.tv_totalpaidval.setText("₹ 0");
            CartActivity.rl_content2.setVisibility(View.GONE);
            CartActivity.rl_noitems.setVisibility(View.VISIBLE);
            CartActivity.tv_rv1.setText("");

            try {

                MainActivityNav.text.setText("");
                MainActivityNav.text.setText("");

            } catch (Exception ex) {

            }


            try {
                ProductDetailsActivity.text.setText("");
            } catch (Exception ex) {

            }


            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, "");


        }
    }


    private void ShowQtyDialogue(CartDatum item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


        alertDialog.setTitle("Quantity");
        alertDialog.setMessage("Add Quantity");


        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(4);


        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 30, 0);


        EditText input = new EditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(FilterArray);
        input.setHint("Enter Qty");
        layout.addView(input, params);
        alertDialog.setView(layout);


        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        String moreqty = input.getText().toString();

                        if (moreqty.length() > 0) {

                            int chkqty = Integer.valueOf(moreqty);


                            if (chkqty > 0) {

                                double itemqty = Double.parseDouble(moreqty);
                                double itemprice = Double.parseDouble(item.getProductPrice());
                                double boxsize = Double.parseDouble("1");

                                double totprice = itemqty * itemprice * boxsize;

                                DecimalFormat form = new DecimalFormat("0.00");
                                String totalprice = form.format(totprice);

                                int position = cartList.indexOf(item);

                                item.setProductQuantity(moreqty);
                                item.setTotalPrice(totalprice);

                                cartList.set(position, item);

                                notifyDataSetChanged();

                                UpdateCart(item.getProductUnitId(), moreqty, item);


                                Utilities.hideKeyboard(mContext);
                                input.clearFocus();


                            } else {

                                Utilities.hideKeyboard(mContext);
                                //Singleton.getInstance().showShortToast(mContext, "Please enter a valid quantity");//remove Toast
                                input.clearFocus();
                            }
                        }
                        // Singleton.getInstance().showShortToast(mContext, moreqty);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }


}