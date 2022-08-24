package com.advira.advirafarm.buyer.ui.product.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> implements IConsts {

    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;

    AlertDialog.Builder builder;
    //we are storing all the orders in a list
    private List<ProductList> productList;

    //getting the context and order list with constructor
    public ProductListAdapter(Context mContext, List<ProductList> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_products, null);

        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        //getting the order of the specified position
        ProductList product = productList.get(position);

        //binding the data with the view holder views
        holder.tv_productname.setText(product.getProductname());
        holder.textViewShortDesc.setText(product.getProductVariety().replaceAll("##", "\n"));
        holder.textViewRating.setText(String.valueOf(product.getProductUnits().get(0).getProductInstock()));
        holder.tv_pack.setText("1");
        holder.tv_productid.setText(product.getId());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText("1");

        String productInstock = product.getProductUnits().get(0).getProductInstock();

        if (productInstock.equalsIgnoreCase("Out-of-Stock")) {

            holder.textViewRating.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.textViewRating.setVisibility(View.INVISIBLE);
        }


        String product_image = product.getProductUnits().get(0).getProductImage();

        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);
        }
        else
        {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        double mrp = 0;
        double price =0;


        try{
             mrp = Double.valueOf(product.getProductUnits().get(0).getProductMrp());
             price = Double.valueOf(product.getProductUnits().get(0).getProductSalesprice());
        }
        catch (Exception ex)
        {

        }

        //double price = (mrp * (100-disc))/100;

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_price.setText(form.format(price));
        //holder.tv_price.setText(String.valueOf(price));

        holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_packsize.setText("1");
        holder.tv_inr.setText("Rate : ₹ ");

        moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

        /*if (product.getProductMoq().equalsIgnoreCase(product.getProductQuantity())) {
            holder.btn_decrease.setVisibility(View.GONE);
        }*/


        holder.btn_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilities.hideKeyboard(mContext);
                if (Utilities.isNetworkConnected(mContext)) {

                    minteger = Integer.parseInt(holder.integer_number.getText().toString());

                    minteger = minteger + 1;
                    holder.integer_number.setText(String.valueOf(minteger));

                   // AddToCart(holder.tv_productid.getText().toString(), "1");

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

                       // CartDeleteRequest(holder.tv_productid.getText().toString());

                        //holder.btn_decrease.setEnabled(false);
                        holder.ll_addremovebutton.setVisibility(View.VISIBLE);
                        holder.ll_addremove.setVisibility(View.GONE);


                    } else {
                        minteger = Integer.parseInt(holder.integer_number.getText().toString());
                        minteger = minteger - 1;
                        holder.integer_number.setText(String.valueOf(minteger));


                        if(minteger<1)
                        {

                           // CartDeleteRequest(holder.tv_productid.getText().toString());
                        }
                        else
                        {
                          //  UpdateCart(holder.tv_productid.getText().toString(), holder.integer_number.getText().toString());

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
                holder.ll_addremovebutton.setVisibility(View.GONE);
                holder.ll_addremove.setVisibility(View.VISIBLE);

                //AddToCart(holder.tv_productid.getText().toString(), "1");

            }
        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_productname, textViewShortDesc, textViewRating, tv_mrpval, tv_pack, tv_minqty, tv_price, tv_packsize,tv_inr;
        ImageView imageView, iv_wishlist;
        EditText integer_number;
        CardView cv_product;
        Button btn_addtocart;
        Button btn_increase;
        Button btn_decrease;
        Button btn_addto;
        Button btn_add;
        LinearLayout ll_addremovebutton;
        LinearLayout ll_addremove;

        public ProductListViewHolder(View itemView) {
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
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_packsize = itemView.findViewById(R.id.tv_packsize);
            tv_inr  = itemView.findViewById(R.id.tv_inr);
            btn_addto = itemView.findViewById(R.id.btn_addto);
            btn_decrease = itemView.findViewById(R.id.btn_decrease);
            btn_increase = itemView.findViewById(R.id.btn_increase);
            integer_number = itemView.findViewById(R.id.integer_number);
            btn_add = itemView.findViewById(R.id.btn_add);
            ll_addremovebutton = itemView.findViewById(R.id.ll_addremovebutton);
            ll_addremove = itemView.findViewById(R.id.ll_addremove);

            String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");


            if(useractive.equalsIgnoreCase("active"))
            {
                tv_inr.setVisibility(View.VISIBLE);
                tv_price.setVisibility(View.VISIBLE);

            }
            else
            {
                tv_inr.setVisibility(View.INVISIBLE);
                tv_price.setVisibility(View.INVISIBLE);
            }

            cv_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_productid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-9", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_productid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-146", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);
                    }

                    // Toast.makeText(mContext, "Position" + tv_productname.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            btn_addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

                       int chk = Integer.valueOf(integer_number.getText().toString());


                        if(chk > 0) {
                            if (useractive.equalsIgnoreCase("active")) {
                                //AddToCart(tv_productid.getText().toString(), String.valueOf(chk));
                                integer_number.clearFocus();
                            } else {
                                CheckProfile(tv_productid.getText().toString(), String.valueOf(chk));

                            }
                        }

                    } else {
                        Utilities.showNetworkError(mContext);
                    }
                    //Toast.makeText(mContext, tv_productname.getText().toString() +" Added to cart", Toast.LENGTH_SHORT).show();
                }
            });


            btn_addto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utilities.hideKeyboard(mContext);
                    if (Utilities.isNetworkConnected(mContext)) {

                        String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

                        int chk = Integer.valueOf(integer_number.getText().toString());


                        if(chk > 0) {
                            if (useractive.equalsIgnoreCase("active")) {
                               // AddToCart(tv_productid.getText().toString(), String.valueOf(chk));
                                integer_number.clearFocus();
                            } else {
                                CheckProfile(tv_productid.getText().toString(), String.valueOf(chk));

                            }
                        }

                    } else {
                        Utilities.showNetworkError(mContext);
                    }
                    //Toast.makeText(mContext, tv_productname.getText().toString() +" Added to cart", Toast.LENGTH_SHORT).show();
                }
            });



        }
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
                        String chkpop ="n";

                        if(response.body().getPersonalProfileStatus().equalsIgnoreCase("0"))
                        {
                            popmsg = popmsg+ "\n - Personal details";
                            chkpop="y";
                        }
                        if(response.body().getBusinessProfileStatus().equalsIgnoreCase("0"))
                        {
                            popmsg = popmsg+ "\n - Business details";
                            chkpop="y";
                        }
                        if(response.body().getKycDocumentStatus().equalsIgnoreCase("0"))
                        {
                            popmsg = popmsg+ "\n - Upload KYC documents";
                            chkpop="y";
                        }

                        if (response.body().getProfileActivateStatus().equalsIgnoreCase("0")) {

                            if(popmsg.contains("Personal details")||popmsg.contains("Business details")||popmsg.contains("Upload KYC documents"))
                            {

                            }
                            else
                            {
                                popmsg = "Your account is not active";
                            }

                            chkpop = "y";
                        }


                        if(chkpop.equalsIgnoreCase("y"))
                        {
                            SharedPrefUtil.setUserActive(mContext,SHARED_PREF_UserActive,"inactive");
                            ShowAlert(popmsg);
                        }
                        else
                        {
                            SharedPrefUtil.setUserActive(mContext,SHARED_PREF_UserActive,"active");
                              //  AddToCart(productid, qty);

                        }

                    } else {

                        Utilities.dismissDialog();
                        Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
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

    private void ShowAlert(String ShowAlert)
    {
        builder = new AlertDialog.Builder(mContext);


        builder.setTitle("Account Inactive !!");

        //Setting message manually and performing action on button click
        builder.setMessage(ShowAlert)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /*if (ShowAlert.contains("Personal details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, PersonalDetailsActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        }

                        else if (ShowAlert.contains("Business details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, BusinessDetailsActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        }
                        else if (ShowAlert.contains("Upload KYC documents")) {

                            Intent i = new Intent();
                            i.setClass(mContext, DocumentUploadActivity.class);
                            i.putExtra("status", "inactive");
                            mContext.startActivity(i);
                        }*/



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
/*

    private void AddToCart(String productid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductId(productid);
        cartRequest.setProductQuantity(qty);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).addtocart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());
                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        Utilities.dismissDialog();


                    } else {

                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
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

    private void UpdateCart(String productid, String qty) {

        Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CartRequest cartRequest = new CartRequest();
        cartRequest.setProductId(productid);
        cartRequest.setProductQuantity(qty);

        try {

            Call<CartResponse> call = RetrofitUrlConnection.loadJSON(token).updatecart(cartRequest);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                        Singleton.getInstance().showShortToast(mContext, "Cart updated successfully");

                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        MainActivityNav.text.setText(response.body().getCartSize().toString());
                        Utilities.dismissDialog();


                    } else {

                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
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


    private void CartDeleteRequest(String productid) {

        Utilities.showLoading(mContext);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
        productDetailsRequest.setProductId(productid);

        try {

            Call<CartDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).delete_from_cart(productDetailsRequest);

            call.enqueue(new Callback<CartDeleteResponse>() {
                @Override
                public void onResponse(Call<CartDeleteResponse> call, Response<CartDeleteResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        MainActivityNav.text.setText(response.body().getCartSize().toString());

                        Utilities.dismissDialog();
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());


                    } else {
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
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
*/



}
