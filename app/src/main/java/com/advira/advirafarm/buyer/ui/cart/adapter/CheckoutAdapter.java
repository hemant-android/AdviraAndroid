package com.advira.advirafarm.buyer.ui.cart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.cart.api.Cart;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;

import java.text.DecimalFormat;
import java.util.List;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfileMode;


public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CartViewHolder> {


    //this context we will use to inflate the layout
    private Context mContext;
    private String tctct;
    private int minteger = 0;


    //we are storing all the products in a list
    private List<Cart> carttList;

    //getting the context and product list with constructor
    public CheckoutAdapter(Context mContext, List<Cart> carttList) {
        this.mContext = mContext;
        this.carttList = carttList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_checkout, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        //getting the product of the specified position
        Cart product = carttList.get(position);

        //binding the data with the view holder views
        holder.tv_prodname.setText(product.getTitle());
        holder.tv_order.setText(product.getMoq());
        holder.img_product.setImageDrawable(mContext.getResources().getDrawable(product.getImage()));
        holder.tv_price.setText(String.valueOf(product.getMrp()));
        //holder.tv_mrpval.setText(String.valueOf(product.getMrp()));



        String discount = product.getDiscount().replace("% OFF","");

        double disc = Double.parseDouble(discount);
        double mrp = product.getMrp();
        double price = (mrp * (100-disc))/100;

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_price.setText(form.format(price) );
        //holder.tv_price.setText(String.valueOf(price));

        holder.tv_mrpval.setText("₹ "+form.format(product.getMrp()));
    }


    @Override
    public int getItemCount() {
        return carttList.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tv_prodname, tv_order, tv_price, tv_deliv,tv_mrpval;
        ImageView img_product;



        public CartViewHolder(View itemView) {
            super(itemView);

            tv_prodname = itemView.findViewById(R.id.tv_prodname);
            tv_order = itemView.findViewById(R.id.tv_order);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_deliv = itemView.findViewById(R.id.tv_deliv);
            img_product = itemView.findViewById(R.id.img_product);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);

            img_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productname", tv_prodname.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-1", Toast.LENGTH_LONG).show();
                       // i.putExtra("productid", tv_prodid.getText().toString());
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_prodname.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-143", Toast.LENGTH_LONG).show();
                       // i.putExtra("productid", tv_prodid.getText().toString());
                        mContext.startActivity(i);
                    }

                }
            });

        }
    }


    private void getData() {

    }
}
