package com.advira.advirafarm.buyer.ui.cart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountRequest;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountResponse;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class OrderPreviewAdapter extends RecyclerView.Adapter<OrderPreviewAdapter.CartViewHolder> implements IConsts {


    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    private String previewtotal="0";

    String membershipName="";

    //we are storing all the products in a list
    private List<CartDatum> cartList;

    //getting the context and product list with constructor
    public OrderPreviewAdapter(Context mContext, List<CartDatum> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_orderpreview, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        //getting the product of the specified position
        CartDatum product = cartList.get(position);
        //binding the data with the view holder views
        holder.tv_prodid.setText(product.getProductId());
        holder.tv_productname.setText(product.getProductName());
        //holder.tv_stock.setText(String.valueOf(product.getProductInfo().get(0).getProductInstock()));
        // holder.tv_pack.setText(product.getProductDiscount());
        holder.tv_pack.setText(product.getProductUnits());

        holder.tv_minqty.setText("Min order : " + product.getProductQuantity());
        holder.tv_orderqtyval.setText(product.getProductQuantity());
        holder.tv_inr.setText("Rate ₹ ");
        holder.tv_mrp.setText("MRP ₹ ");

        // holder.tv_boxsize.setText(product.getProductInfo().get(0).getProductBoxSize());
        String product_image = product.getProductImage();


        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

      /*  String isrx = product.getProductInfo().get(0).getIsRx();
        if (isrx.equalsIgnoreCase("yes")) {
            holder.iv_rx.setVisibility(View.VISIBLE);
        } else {
            holder.iv_rx.setVisibility(View.GONE);
        }*/


        double price = Double.parseDouble(product.getProductPrice());
        double totprice = Double.parseDouble(product.getTotalPrice());
        double mrp = Double.parseDouble(product.getProductMrp());

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_price.setText(form.format(price));
        holder.tv_itempriceval.setText(form.format(totprice));
        holder.tv_discount.setText(product.getProductMrpDiscountLabel());
        //holder.tv_orderqtyunit.setText(product.getProductQuantity());

        moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

        // CalculatePrice("flat","0");
        //CalculatePrice(OrderPreviewActivity.tv_discount_type.getText().toString(), OrderPreviewActivity.tv_discount_amount.getText().toString());


        String usermode = SharedPrefUtil.getProfileMode(mContext,SHARED_PREF_ProfileMode,"B2C");

        if(usermode.equalsIgnoreCase("B2B"))
        {
            int cartitemcount =getItemCount();
            for (int i=0;i<=cartitemcount;i++)
            {
                if(i==cartitemcount)
                {
                    getDiscount();
                }
            }
        }
        else
        {
            try{
                OrderPreviewActivity.tv_discountval.setText("0");
                OrderPreviewActivity.tv_discountid.setText("");
                OrderPreviewActivity.tv_discount_coupon_name.setText("");
                OrderPreviewActivity.tv_discount_type.setText("");
                OrderPreviewActivity.tv_discount_amount.setText("0");
                OrderPreviewActivity.tv_discount_details.setText("");
                CalculatePrice(OrderPreviewActivity.tv_discount_type.getText().toString(), OrderPreviewActivity.tv_discount_amount.getText().toString());
            }
            catch (Exception ex)
            {
            }
            // OrderPreviewActivity.tv_discount.setText("Discount");
        }


    }


    @Override
    public int getItemCount() {
        return cartList.size();

    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tv_productname, tv_stock;
        ImageView imageView, iv_rx;
        TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
                tv_orderqtyval, tv_prodid, tv_inr, tv_boxsize, tv_mrp, tv_orderqtyunit,tv_discount;

        public CartViewHolder(View itemView) {
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
            tv_orderqtyval = itemView.findViewById(R.id.tv_orderqtyval);
            tv_inr = itemView.findViewById(R.id.tv_inr);
            tv_boxsize = itemView.findViewById(R.id.tv_boxsize);
            iv_rx = itemView.findViewById(R.id.iv_rx);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_orderqtyunit = itemView.findViewById(R.id.tv_orderqtyunit);
            tv_discount = itemView.findViewById(R.id.tv_discount);


            tv_mrpval.setBackgroundResource(R.drawable.strike_through);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-3", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        mContext.startActivity(i);
                    }


                    // Toast.makeText(mContext, "Position" + tv_productname.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });



        }
    }


    private void CalculatePrice(String discount_type, String discount_amount) {
        double itemmrp = 0;
        double mrptotal = 0;
        double amountpayable = 0;
        double itemtax = 0;
        double carttotal = 0;
        double itemprice = 0;
        double totaltax = 0;
        double discount = 0;
        double discountval = 0;
        double carttotalinit = 0;
        double delicharge=0;

        if (cartList.size() > 0) {
            OrderPreviewActivity.rl_content2.setVisibility(View.VISIBLE);
            OrderPreviewActivity.tv_rv1.setText("Order Item " + "(" + cartList.size() + ")");
            delicharge=Double.parseDouble(OrderPreviewActivity.tv_deliveryval.getText().toString().replace("+ ₹ ", ""));
            for (int i = 0; i < cartList.size(); i++) {
                itemprice = Double.parseDouble(cartList.get(i).getTotalPrice());
                carttotal = carttotal + itemprice;
                itemmrp = Double.parseDouble(cartList.get(i).getProductPrice());
                mrptotal = mrptotal + itemmrp;
                itemtax = Double.parseDouble(cartList.get(i).getProductTax().replaceAll("%", ""));
                totaltax = totaltax + (itemprice * itemtax * .01);
            }
            previewtotal = String.valueOf(previewtotal);
            discount = Double.parseDouble(discount_amount);
            carttotalinit = carttotal;
            double discx = 0;
            discx = ((totaltax*100)/carttotal);

            if (discount_type.equalsIgnoreCase("flat")) {
                discountval = discount;
                carttotal = carttotal - discount;
            } else if (discount_type.equalsIgnoreCase("PERCENTAGE")) {
                discountval = (carttotal * discount * .01);
                carttotal = carttotal - discountval;
            }else{
                discountval = discount;
                carttotal = carttotal - discountval;
            }

            Log.e(TAG, "CalculatePrice: deliprice" +" "+delicharge );

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
            try
            {
            OrderPreviewActivity.tv_priceval.setText("₹ " + form.format(carttotalinit));
            OrderPreviewActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));
            OrderPreviewActivity.tv_footertotalitem.setText(cartList.size() + " ITEMS");
            OrderPreviewActivity.tv_discountval.setText("- ₹ " + form.format(discountval));
                if(delicharge ==0 ){
                    if(amountpayable<99){
                        int delicharge1=20;
                        amountpayable=carttotal+delicharge1;
                        OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.black));
                        OrderPreviewActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge1));
                    }else {
                        OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                        OrderPreviewActivity.tv_deliveryval.setText("+ ₹ 0.00");
                    }
                }
                else{
                    OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    OrderPreviewActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge));

                }
                if(membershipName!=null && membershipName.length()>0){
                    if(amountpayable<99){
                        //delicharge=20;
                        amountpayable=carttotal+delicharge;
                        OrderPreviewActivity.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
                        OrderPreviewActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));
                        OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                        OrderPreviewActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge));
                    }
                    else{
                        OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                        OrderPreviewActivity.tv_deliveryval.setText("+ ₹ 0.00");
                        amountpayable=carttotal+delicharge-discountval;
                        Log.e(TAG, "CalculatePrice: <99"+amountpayable );
                        OrderPreviewActivity.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
                        OrderPreviewActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));

                    }
                }else {
                    if ((carttotalinit-discountval) < 250) {
                        amountpayable = carttotal + delicharge;
                        OrderPreviewActivity.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
                        OrderPreviewActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));
                        OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                        OrderPreviewActivity.tv_deliveryval.setText("+ ₹ " + form.format(delicharge));
                    } else {
                        OrderPreviewActivity.tv_deliveryval.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                        OrderPreviewActivity.tv_deliveryval.setText("+ ₹ 0.00");
                        amountpayable = carttotalinit - discountval;
                        OrderPreviewActivity.tv_totalpaidval.setText("₹ " + form.format(amountpayable));
                        OrderPreviewActivity.tv_footertotal.setText("₹ " + form.format(amountpayable));

                    }
                }
        }

            catch (Exception ex)
        {
        }
            SharedPrefUtil.setCartCount(mContext, SHARED_PREF_CARTCOUNT, String.valueOf(cartList.size()));
        } else {
            try
            {
            OrderPreviewActivity.tv_priceval.setText("₹ 0");
            //OrderPreviewActivity.tv_gstval.setText("+ ₹ 0");
            OrderPreviewActivity.tv_totalpaidval.setText("₹ 0");
            OrderPreviewActivity.rl_content2.setVisibility(View.GONE);
            OrderPreviewActivity.tv_rv1.setText("Item is Empty !");
        }
            catch (Exception ex)
        {
        }
        }
    }
    public void getDiscount() {
       // Utilities.showLoading(mContext);
        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        String carttotalstr = String.valueOf(previewtotal);
        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setCouponCode("");
        discountRequest.setOrderAmount(carttotalstr);
        try {
            Call<DiscountResponse> call = RetrofitUrlConnection.loadJSON(token).discountB2B(discountRequest);
            call.enqueue(new Callback<DiscountResponse>() {
                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("100")) {
                        Utilities.dismissDialog();
                        //String discount_type = "", discount_amount = "0";
                        try {
                            if (response.body().getDiscountCoupon().size() > 0) {
                                OrderPreviewActivity.tv_discount.setText("Discount (" + response.body().getDiscountCoupon().get(0).getDiscountCouponName() + ")");
                                OrderPreviewActivity.tv_discountid.setText(response.body().getDiscountCoupon().get(0).getId());
                                OrderPreviewActivity.tv_discount_coupon_name.setText(response.body().getDiscountCoupon().get(0).getDiscountCouponName());
                                OrderPreviewActivity.tv_discount_type.setText(response.body().getDiscountCoupon().get(0).getDiscountType());
                                OrderPreviewActivity.tv_discount_amount.setText(response.body().getDiscountCoupon().get(0).getDiscountAmount());
                                OrderPreviewActivity.tv_discount_details.setText(response.body().getDiscountCoupon().get(0).getDiscountDetails());
                            } else {
                                OrderPreviewActivity.tv_discount.setText("Discount");
                                OrderPreviewActivity.tv_discountval.setText("0");
                                OrderPreviewActivity.tv_discountid.setText("");
                                OrderPreviewActivity.tv_discount_coupon_name.setText("");
                                OrderPreviewActivity.tv_discount_type.setText("");
                                OrderPreviewActivity.tv_discount_amount.setText("0");
                                OrderPreviewActivity.tv_discount_details.setText("");
                            }
                            String credit_balance = "0";
                            if (response.body().getCreditDetails().size() > 0) {
                                credit_balance = response.body().getCreditDetails().get(0).getCreditBalance();
                                OrderPreviewActivity.tv_credit_limit.setText(response.body().getCreditDetails().get(0).getCreditLimit());
                                OrderPreviewActivity.tv_credit_availed.setText(response.body().getCreditDetails().get(0).getCreditAvailed());
                                OrderPreviewActivity.tv_credit_balance.setText(response.body().getCreditDetails().get(0).getCreditBalance());
                                SharedPrefUtil.setAvailableCredit(mContext, SHARED_PREF_AvailableCredit, credit_balance);
                            }
                            CalculatePrice(OrderPreviewActivity.tv_discount_type.getText().toString(), OrderPreviewActivity.tv_discount_amount.getText().toString());
                        }
                        catch (Exception ex){
                        }
                    } else {
                        Utilities.dismissDialog();
                        //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {
                    Utilities.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(mContext, "Invalid request, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

}