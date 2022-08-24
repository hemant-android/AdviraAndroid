package com.advira.advirafarm.buyer.ui.order.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.order.api.OrderProductList;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.squareup.picasso.Picasso;

import java.util.List;


public class OrderDetailsImageAdapter extends RecyclerView.Adapter<OrderDetailsImageAdapter.CartViewHolder> implements IConsts {


    //this context we will use to inflate the layout
    private Context mContext;


    //we are storing all the products in a list
    private List<OrderProductList> productDetailsList;

    //getting the context and product list with constructor
    public OrderDetailsImageAdapter(Context mContext, List<OrderProductList> productDetailsList) {
        this.mContext = mContext;
        this.productDetailsList = productDetailsList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_orderimages, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        OrderProductList product = productDetailsList.get(position);
        holder.tv_prodid.setText(product.getProductId().toString());
        holder.tv_productname.setText(product.getProductName());
         String product_image = product.getProductImage();

        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        }

        else
        {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return productDetailsList.size();

    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv_productname,
                tv_prodid;

        public CartViewHolder(View itemView) {
            super(itemView);


            tv_productname = itemView.findViewById(R.id.tv_productname);
            imageView = itemView.findViewById(R.id.imageView);
            tv_prodid = itemView.findViewById(R.id.tv_prodid);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-6", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        //Toast.makeText(view.getContext(), "Search Data Saved-144", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);
                    }


                }
            });


        }
    }

  

}
