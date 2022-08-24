package com.advira.advirafarm.buyer.ui.guest.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartResponse;
import com.advira.advirafarm.buyer.ui.guest.CategoryProductActivityGuest;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.guest.RFQMobileActivity;
import com.advira.advirafarm.buyer.ui.login.LoginActivity;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.onetaplogin.OneTapLogin;
import com.advira.advirafarm.buyer.ui.product.CategoryProductActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_home;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class CategoryListAdapterGuest extends RecyclerView.Adapter<CategoryListAdapterGuest.CategoryListViewHolder> implements IConsts {

    AlertDialog.Builder builder;

    private List<Product_home> productList;
    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    //we are storing all the orders in a list


    //getting the context and order list with constructor
    public CategoryListAdapterGuest(Context mContext, List<Product_home> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }


    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_products, null);

        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        //getting the order of the specified position
        Product_home product = productList.get(position);

        //binding the data with the view holder views
       /* holder.tv_productname.setText(product.getProductname());
        holder.textViewShortDesc.setText(product.getProductComposition().replaceAll("##", "\n"));
        holder.textViewRating.setText(String.valueOf(product.getProductInstock()));
        holder.tv_pack.setText(product.getProductBoxSize());
        holder.tv_productid.setText(product.getId());
        holder.tv_minqty.setText("Min order : " + product.getProductMoq());
        holder.integer_number.setText(product.getProductMoq());
        String productInstock = product.getProductInstock();*/
        //binding the data with the view holder views
        // holder.textViewShortDesc.setText(product.getProductComposition().replaceAll("##", "\n"));
        //holder.tv_unitid.setText(product.getProductUnits().get(0).getProductUnitsId());
        // String productInstock = "In-Stock";
        //String productInstock = product.getProductUnits().get(0).getProductInstock();


        holder.tv_productname.setText(product.getProductname());
        holder.textViewRating.setText("");
        holder.tv_pack.setText("1");
        holder.tv_productid.setText(product.getSkuId());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText("1");
        holder.tv_unitid.setText(product.getProductUnitsId());
        String productInstock = product.getProductInstock();
        if (productInstock.equalsIgnoreCase("Out-of-Stock")) {

            holder.textViewRating.setVisibility(View.VISIBLE);
            holder.textViewRating.setText(product.getProductInstock());
            holder.btn_addto.setEnabled(false);
            holder.btn_add.setEnabled(false);
            //holder.textViewRating.setText(product.getProductUnits().get(0).getProductInstock());
            //holder.btn_addto.getBackground().setColorFilter(Color.parseColor("#A5A5A5"), PorterDuff.Mode.SRC);
            // holder.btn_add.getBackground().setColorFilter(Color.parseColor("#A5A5A5"), PorterDuff.Mode.SRC);
        } else {
            holder.textViewRating.setVisibility(View.INVISIBLE);
            holder.btn_addto.setEnabled(true);
            holder.btn_add.setEnabled(true);
        }


        // String product_image = product.getProductThumbnailUrl();
        //String product_image = product.getProductUnits().get(0).getProductImage();

        String product_image = product.getProductImage();

        if (product_image.length() > 5) {
            Glide.with(mContext).load(product_image).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.progress_animation).into(holder.imageView);
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

        String isrx = "no";

        if (isrx.equalsIgnoreCase("yes")) {

            holder.iv_rx.setVisibility(View.VISIBLE);
        } else {
            holder.iv_rx.setVisibility(View.INVISIBLE);
        }

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_price.setText(form.format(price));
        holder.tv_price.setText(String.valueOf(price));

        holder.tv_mrpval.setText(form.format(mrp));
        //holder.tv_packsize.setText(product.getProductUnits().get(0).getProductUnits() + " " + product.getProductUnits().get(0).getProductUnitType());
        holder.tv_packsize.setText(product.getProductUnits() + " " + product.getProductUnitType());
        holder.tv_inr.setText("₹");
        holder.tv_mrp.setText("₹");
        //holder.tv_discount.setText(product.getProductUnits().get(0).getProductDiscountLabel());
        holder.tv_discount.setText(product.getProductDiscountLabel());

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
                if (useractive.equalsIgnoreCase("guest")) {
                    holder.ll_addremovebutton.setVisibility(View.VISIBLE);
                    holder.ll_addremove.setVisibility(View.GONE);
                    Intent i = new Intent();
                    i.setClass(mContext, OneTapLogin.class);
                    mContext.startActivity(i);
                }
                else {
                    holder.ll_addremovebutton.setVisibility(View.GONE);
                    holder.ll_addremove.setVisibility(View.VISIBLE);

                    AddToCart(holder.tv_unitid.getText().toString(), "1");
                }

            }
        });

        holder.btn_addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Utilities.hideKeyboard(mContext);
                holder.ll_addremovebutton.setVisibility(View.GONE);
                holder.ll_addremove.setVisibility(View.VISIBLE);
                AddToCart(holder.tv_unitid.getText().toString(), "1");*/
                Utilities.hideKeyboard(mContext);
                String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");
                String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");


                //if (useractive.equalsIgnoreCase("guest")) {

                    if (usermode.equalsIgnoreCase("B2C")) {
                        if (useractive.equalsIgnoreCase("guest")) {
                            holder.ll_addremovebutton.setVisibility(View.VISIBLE);
                            holder.ll_addremove.setVisibility(View.GONE);
                            holder.btn_add.setEnabled(false);
                            holder.btn_add.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_gray_border));

                            Intent i = new Intent();
                            i.setClass(mContext, OneTapLogin.class);
                            mContext.startActivity(i);
                            //AddToCart(holder.tv_unitid.getText().toString(), "1");
                        }
                        else{
                            holder.ll_addremovebutton.setVisibility(View.GONE);
                            holder.ll_addremove.setVisibility(View.VISIBLE);
                            //holder.btn_add.setEnabled(false);
                            holder.btn_add.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_gray_border));

                            /*Intent i = new Intent();
                            i.setClass(mContext, OneTapLogin.class);
                            mContext.startActivity(i);*/
                            //AddToCart(holder.tv_unitid.getText().toString(), "1");
                        }

                    } else {
                        /*holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);

                        AddToCart(holder.tv_unitid.getText().toString(), "1");*/
                        holder.ll_addremove.setVisibility(View.GONE);

                        Intent i = new Intent();
                        i.setClass(mContext, RFQMobileActivity.class);
                        mContext.startActivity(i);
                    }
                /*} else {
                    if (useractive.equalsIgnoreCase("guest")) {
                        // holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.GONE);

                        Intent i = new Intent();
                        i.setClass(mContext, RFQMobileActivity.class);
                        mContext.startActivity(i);

                        //  AddToCart(holder.tv_unitid.getText().toString(), "1");

                    }
                    */

                //}

            }
        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void ShowAlert() {
        builder = new AlertDialog.Builder(mContext);


        builder.setTitle("Please Login/Register !!");

        //Setting message manually and performing action on button click
        builder.setMessage("Hello Guest ! Please login with valid credentials to check best price for you.")
                .setCancelable(false)
                .setPositiveButton("Login Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Intent i = new Intent();
                        i.setClass(mContext, LoginActivity.class);
                        mContext.startActivity(i);


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

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductUnitId(productunitid);
        cartRequest.setProductQuantity(qty);
        cartRequest.setUserCartType(usermode);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).addtocart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());
                        //text.setText(response.body().getCartSize().toString());
                        //CategoryProductActivityGuest.text.setText(response.body().getCartSize().toString());
                        //MainActivityGuestNav.text.setText(response.body().getCartSize().toString());
                        try {

                            MainActivityGuestNav.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            MainActivityNav.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            //CategoryProductActivity.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            CategoryProductActivityGuest.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }


                        Utilities.dismissDialog();


                    } else {

                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast
                    }

                    Utilities.dismissDialog();
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

    private void UpdateCart(String productunitid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductUnitId(productunitid);
        cartRequest.setProductQuantity(qty);
        cartRequest.setUserCartType(usermode);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).updatecart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                       // Singleton.getInstance().showShortToast(mContext, "Cart updated successfully");//remove cart

                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        //MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                        //MainActivityGuestNav.text.setText(response.body().getCartSize().toString());
                        try {
                            //MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                            MainActivityGuestNav.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            MainActivityNav.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            //CategoryProductActivity.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            CategoryProductActivityGuest.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }

                        Utilities.dismissDialog();


                    } else {

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

    private void CartDeleteRequest(String productunitid) {

        Utilities.showLoading(mContext);
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String deletetype="list";

        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest();
        cartDeleteRequest.setProductUnitId(productunitid);
        cartDeleteRequest.setUserCartType(usermode);
        cartDeleteRequest.setDeletefrom(deletetype);

        try {

            Call<CartDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).delete_from_cart(cartDeleteRequest);

            call.enqueue(new Callback<CartDeleteResponse>() {
                @Override
                public void onResponse(Call<CartDeleteResponse> call, Response<CartDeleteResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        //MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                        //MainActivityGuestNav.text.setText(response.body().getCartSize().toString());

                        try {
                            //MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                            MainActivityGuestNav.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            MainActivityNav.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            //CategoryProductActivity.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }
                        try {
                            CategoryProductActivityGuest.text.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }


                        Utilities.dismissDialog();
                        //Singleton.getInstance().showShortToast(mContext, response.body().getMessage());//remove toast


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

    class CategoryListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_productname, textViewShortDesc, textViewRating,
                tv_mrpval, tv_pack, tv_minqty, tv_price, tv_packsize, tv_inr, tv_mrp, tv_discount, tv_unitid;
        ImageView imageView, iv_rx;
        EditText integer_number;
        CardView cv_product;
        Button btn_addtocart;
        Button btn_increase;
        Button btn_decrease;
        Button btn_addto;
        Button btn_add;
        LinearLayout ll_addremovebutton;
        LinearLayout ll_addremove;


        public CategoryListViewHolder(View itemView) {
            super(itemView);
            String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);
            tv_price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.imageView);
            tv_pack = itemView.findViewById(R.id.tv_pack);
            tv_minqty = itemView.findViewById(R.id.tv_minqty);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
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

            if(usermode.equalsIgnoreCase("B2B")){
                btn_addto.setText("REQUEST QUOTE");
                //btn_add.setVisibility(View.GONE);
                tv_mrp.setVisibility(View.GONE);
                tv_mrpval.setVisibility(View.GONE);
                tv_price.setVisibility(View.GONE);
                tv_discount.setVisibility(View.GONE);
                ll_addremove.setVisibility(View.GONE);
                //ll_addremovebutton.setVisibility(View.GONE);
            }

            // btn_add.setVisibility(View.GONE);
            // btn_addto.setText("REQUEST QUOTE");

            // ll_addremovebutton.setVisibility(View.GONE);
            // ll_addremove.setVisibility(View.GONE);

            tv_mrpval.setBackgroundResource(R.drawable.strike_through);
            tv_inr.setBackgroundResource(R.drawable.strike_through);

            String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

            if (useractive.equalsIgnoreCase("active")) {
                tv_inr.setVisibility(View.GONE);
                tv_inr.setBackgroundResource(R.drawable.strike_through);
                tv_price.setVisibility(View.VISIBLE);

            }

            cv_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(usermode.equalsIgnoreCase("B2B")) {
                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_productid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-141", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);
                    }
                    else{
                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityGuest.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_productid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-14", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);
                    }

                    //Toast.makeText(mContext, "Position" + tv_productname.getText().toString()+tv_productid.getText().toString()+"  "+usermode, Toast.LENGTH_SHORT).show();
                }
            });

           /* btn_addto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        Intent i = new Intent();
                        i.setClass(mContext, RFQMobileActivity.class);
                        mContext.startActivity(i);

                        //ShowAlert();

                    } else {
                        Utilities.showNetworkError(mContext);
                    }
                    //Toast.makeText(mContext, tv_productname.getText().toString() +" Added to cart", Toast.LENGTH_SHORT).show();
                }
            });

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        Intent i = new Intent();
                        i.setClass(mContext, RFQMobileActivity.class);
                        mContext.startActivity(i);

                        //ShowAlert();

                    } else {
                        Utilities.showNetworkError(mContext);
                    }
                    //Toast.makeText(mContext, tv_productname.getText().toString() +" Added to cart", Toast.LENGTH_SHORT).show();
                }
            });*/

        }
    }


}
