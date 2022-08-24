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
import com.advira.advirafarm.buyer.ui.cart.api.CartDatum;
import com.advira.advirafarm.buyer.ui.order.api.OrderProductList;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_ProfileMode;


public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.CartViewHolder> /*implements IConsts*/ {


    //this context we will use to inflate the layout
    private Context mContext;


    //we are storing all the products in a list
    private List<OrderProductList> productDetailsList;

    //getting the context and product list with constructor
    public OrderDetailsAdapter(Context mContext, List<OrderProductList> productDetailsList) {
        this.mContext = mContext;
        this.productDetailsList = productDetailsList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_orderdetails, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        //getting the product of the specified position
        OrderProductList product = productDetailsList.get(position);
        //binding the data with the view holder views
        holder.tv_prodid.setText(product.getProductId().toString());
        holder.tv_orderqty.setText("Ordered Qty : "+product.getProductQuantity());
       // holder.tv_productname.setText(product.getProductInfo().get(0).getProductname());
       // holder.textViewShortDesc.setText(product.getProductInfo().get(0).getProductComposition().replaceAll("##", "\n"));
       // holder.tv_pack.setText(product.getProductInfo().get(0).getProductCountInBox());
       //holder.tv_inr.setText("Rate Per " + product.getProductInfo().get(0).getProductUnitType() + " : ₹ ");
       // holder.tv_mrp.setText("MRP Per " + product.getProductInfo().get(0).getProductUnitType() + " : ₹ ");
       // holder.tv_boxsize.setText(product.getProductInfo().get(0).getProductBoxSize());
        // holder.tv_stock.setText(product.getProductInfo().get(0).getProductInstock());
        // holder.tv_minqty.setText("Min order : 1");
        // holder.integer_number.setText(product.getProductQuantity());
        //holder.tv_inr.setText(" ₹");
        // holder.tv_unitid.setText(product.getProductUnitId());
        // holder.textViewRating.setText(String.valueOf(product.getProductInstock()));

        String product_image = product.getProductImage();
        holder.tv_productname.setText(product.getProductName());
        holder.tv_pack.setText(product.getProductUnits());
        holder.tv_mrp.setText("Rate : ₹ ");
        holder.tv_discount.setText(product.getProductMrpDiscountLabel());



        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        }

        else
        {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        String isrx = "";

        if (isrx.equalsIgnoreCase("yes")) {

            holder.iv_rx.setVisibility(View.VISIBLE);
        } else {
            holder.iv_rx.setVisibility(View.INVISIBLE);
        }


        double price = Double.parseDouble(product.getProductPrice());
        double totprice = Double.parseDouble(product.getTotalPrice());
        double mrp = Double.parseDouble(product.getProductMrp());

        DecimalFormat form = new DecimalFormat("0.00");
        holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_price.setText(form.format(price));
        holder.tv_itempriceval.setText(form.format(totprice));

    }


    @Override
    public int getItemCount() {
        return productDetailsList.size();

    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,iv_rx;
        TextView tv_productname,
                tv_orderqty,
                tv_mrpval,
                tv_pack,
                tv_minqty,
                tv_price,
                tv_itempriceval,
                tv_prodid,
                textViewShortDesc,
                tv_inr,
                tv_boxsize,
                tv_discount,
                tv_mrp;

        public CartViewHolder(View itemView) {
            super(itemView);


            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_mrpval = itemView.findViewById(R.id.tv_mrpval);
            tv_price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.imageView);
            tv_pack = itemView.findViewById(R.id.tv_pack);
            tv_itempriceval = itemView.findViewById(R.id.tv_itempriceval);
            tv_prodid = itemView.findViewById(R.id.tv_prodid);
            tv_orderqty = itemView.findViewById(R.id.tv_orderqty);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            tv_inr = itemView.findViewById(R.id.tv_inr);
            tv_boxsize = itemView.findViewById(R.id.tv_boxsize);
            iv_rx = itemView.findViewById(R.id.iv_rx);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_discount=itemView.findViewById(R.id.tv_discount);

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
                        //Toast.makeText(view.getContext(), "Search Data Saved-5", Toast.LENGTH_LONG).show();
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_prodid.getText().toString());
                        mContext.startActivity(i);
                    }

                }
            });


        }
    }

  

}
