package com.advira.advirafarm.buyer.ui.product.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartDeleteResponse;
import com.advira.advirafarm.buyer.ui.cart.api.CartRequest;
import com.advira.advirafarm.buyer.ui.cart.api.CartResponse;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.guest.RFQMobileActivity;
import com.advira.advirafarm.buyer.ui.myaccount.api.IsUserVerifiedResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.api.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.ui.product.searchroomdb.SearchRoomDB;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder> implements IConsts {

    AlertDialog.Builder builder;
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    private List<ProductList> productList;
    private List<Product_search> datalist;
    private SearchRoomDB database;


    public SearchListAdapter(Context mContext, List<ProductList> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    public void setFilter(List<ProductList> FilteredDataList) {
        productList = FilteredDataList;
        notifyDataSetChanged();
    }

    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_productssearch, null);
        return new SearchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {
        ProductList product = productList.get(position);
        database=SearchRoomDB.getInstance(mContext);
        //database.searchDao().insert(productList);
        holder.tv_productname.setText(product.getProductname());
        holder.textViewShortDesc.setText(product.getProductVariety().replaceAll("##", "\n"));
        holder.textViewRating.setText(String.valueOf(product.getProductUnits().get(0).getProductInstock()));
        holder.tv_pack.setText("1");
        holder.tv_productid.setText(product.getId());
        holder.tv_minqty.setText("Min order : 1");
        holder.integer_number.setText("1");
        holder.tv_packsize.setText(product.getProductUnits().get(0).getProductUnits() + " " + product.getProductUnits().get(0).getProductUnitType());
        holder.tv_discount.setText(product.getProductUnits().get(0).getProductDiscountLabel());
        String productInstock = product.getProductUnits().get(0).getProductInstock();
        if (productInstock.equalsIgnoreCase("Out-of-Stock")) {
            holder.btn_addto.setEnabled(false);
            holder.btn_add.setEnabled(false);
            holder.textViewRating.setVisibility(View.VISIBLE);
        } else {
            holder.btn_addto.setEnabled(true);
            holder.btn_add.setEnabled(true);
            holder.textViewRating.setVisibility(View.INVISIBLE);
        }
        String product_image = product.getProductUnits().get(0).getProductImage();
        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        double mrp = 0;
        double price = 0;
        try {
            mrp = Double.valueOf(product.getProductUnits().get(0).getProductMrp());
            price = Double.valueOf(product.getProductUnits().get(0).getProductSalesprice());
        } catch (Exception ex) {
        }
        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_price.setText(form.format(price));
        holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_inr.setText("Rate : ₹ ");
        holder.tv_mrp.setText("MRP : ₹ ");
        holder.tv_unitid.setText(product.getProductUnits().get(0).getProductUnitsId());
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
                    holder.ll_addremovebutton.setVisibility(View.GONE);
                    holder.ll_addremove.setVisibility(View.VISIBLE);
                    AddToCart(holder.tv_unitid.getText().toString(), "1");
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
                        holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);
                        AddToCart(holder.tv_unitid.getText().toString(), "1");
                    } else {
                        holder.ll_addremovebutton.setVisibility(View.GONE);
                        holder.ll_addremove.setVisibility(View.VISIBLE);
                        AddToCart(holder.tv_unitid.getText().toString(), "1");
                    }
                } else {
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

    private void ShowAlert(String ShowAlert) {
        builder = new AlertDialog.Builder(mContext);


        builder.setTitle("Account Inactive !!");

        //Setting message manually and performing action on button click
        builder.setMessage(ShowAlert)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                      /*  if (ShowAlert.contains("Personal details")) {

                            Intent i = new Intent();
                            i.setClass(mContext, PersonalDetailsActivity.class);
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

                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());
                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        try {
                            Search_one.tv_cartcount.setText(response.body().getCartSize().toString());
                            MainActivityNav.tv_cartcount.setText(response.body().getCartSize().toString());
                        } catch (Exception ex) {

                        }
                        try {
                            MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());
                        } catch (Exception ex) {

                        }

                        Utilities.dismissDialog();
                        // CalculatePrice();

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

                        Singleton.getInstance().showShortToast(mContext, "Cart updated successfully");

                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        try {
                            Search_one.tv_cartcount.setText(response.body().getCartSize().toString());

                            MainActivityNav.tv_cartcount.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }

                        try {
                            Search_one.tv_cartcount.setText(response.body().getCartSize().toString());

                            MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }


                        // MainActivityNav.tv_cartcount.setText(response.body().getCartSize().toString());

                        Utilities.dismissDialog();
                        // CalculatePrice();

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

    private void CartDeleteRequest(String productunitid) {

        Utilities.showLoading(mContext);
        String usermode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");


        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest();
        cartDeleteRequest.setProductUnitId(productunitid);
        cartDeleteRequest.setUserCartType(usermode);

        try {

            Call<CartDeleteResponse> call = RetrofitUrlConnection.loadJSON(token).delete_from_cart(cartDeleteRequest);

            call.enqueue(new Callback<CartDeleteResponse>() {
                @Override
                public void onResponse(Call<CartDeleteResponse> call, Response<CartDeleteResponse> response) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {


                        SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, response.body().getCartSize().toString());

                        try {
                            Search_one.tv_cartcount.setText(response.body().getCartSize().toString());

                            MainActivityNav.tv_cartcount.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }

                        try {
                            Search_one.tv_cartcount.setText(response.body().getCartSize().toString());

                            MainActivityGuestNav.tv_cartcount.setText(response.body().getCartSize().toString());

                        } catch (Exception ex) {

                        }


                        Utilities.dismissDialog();
                        Singleton.getInstance().showShortToast(mContext, response.body().getMessage());

                        //CalculatePrice();

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
    class SearchListViewHolder extends RecyclerView.ViewHolder {


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

        public SearchListViewHolder(View itemView) {
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
                if (useractive.equalsIgnoreCase("active")) {
                    tv_inr.setVisibility(View.VISIBLE);
                    tv_price.setVisibility(View.VISIBLE);
                    tv_discount.setVisibility(View.VISIBLE);
                    tv_mrpval.setBackgroundResource(R.drawable.strike_through);
                } else if (useractive.equalsIgnoreCase("guest")) {
                    tv_inr.setVisibility(View.INVISIBLE);
                    tv_price.setVisibility(View.INVISIBLE);
                    tv_discount.setVisibility(View.INVISIBLE);
                    tv_mrp.setVisibility(View.INVISIBLE);
                    tv_mrpval.setVisibility(View.INVISIBLE);
                    btn_addto.setText("REQUEST QUOTE");
                    btn_add.setEnabled(false);

                }

            } else {
                tv_inr.setVisibility(View.VISIBLE);
                tv_price.setVisibility(View.VISIBLE);
                tv_discount.setVisibility(View.VISIBLE);
                tv_mrp.setVisibility(View.VISIBLE);
                tv_mrpval.setVisibility(View.VISIBLE);
                tv_mrpval.setBackgroundResource(R.drawable.strike_through);

            }

            // String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "inactive");

            cv_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    String useractive = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        if(useractive.equalsIgnoreCase("guest"))
                        {
                            Intent i = new Intent();
                            i.setClass(mContext, ProductDetailsActivityGuest.class);
                            i.putExtra("productname", tv_productname.getText().toString());
                            i.putExtra("productid", tv_productid.getText().toString());
                            //Toast.makeText(view.getContext(), "Search Data Saved-15", Toast.LENGTH_LONG).show();
                            mContext.startActivity(i);
                        }
                        else
                        {
                            Intent i = new Intent();
                            i.setClass(mContext, ProductDetailsActivity.class);
                            i.putExtra("productname", tv_productname.getText().toString());
                            i.putExtra("productid", tv_productid.getText().toString());
                            //Toast.makeText(view.getContext(), "Search Data Saved-10", Toast.LENGTH_LONG).show();
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