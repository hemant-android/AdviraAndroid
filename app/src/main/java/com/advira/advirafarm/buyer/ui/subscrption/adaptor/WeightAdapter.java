package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.adapter.ProductUnitAdapter;
import com.advira.advirafarm.buyer.ui.product.api.ProductBanner;
import com.advira.advirafarm.buyer.ui.product.api.ProductUnit;
import com.advira.advirafarm.buyer.ui.subscrption.api.SkuUmit;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightListViewHolder>  implements IConsts  {

    AlertDialog.Builder builder;
    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    //we are storing all the orders in a list
    private List<SkuUmit> productUnit;

    public WeightAdapter(Context mContext, List<SkuUmit> productUnit) {
        this.mContext = mContext;
        this.productUnit = productUnit;
    }

    @NonNull
    @Override
    public WeightListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_weightunit, null);

        return new WeightListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightAdapter.WeightListViewHolder holder, int position) {
        //getting the order of the specified position
        SkuUmit product = productUnit.get(position);
        holder.tv_price.setText(product.getProductSalesprice());
        holder.tv_mrp.setText(product.getProductMrp());
        holder.tv_unit.setText(product.getProductUnits() + " " + product.getProductUnitType());
        holder.tv_unitid.setText(String.valueOf(product.getProductSkuUnitPriceId()));

        if (product.getIsDefault().equalsIgnoreCase("yes")) {
            holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_green_unit));
            holder.tv_unit.setTextColor(mContext.getResources().getColor(R.color.colorYellow));
            //ProductDetailsActivity.tv_placeorder.setText("Out of Stock");

        }
        else
        {
            holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_yellow_unit));
            holder.tv_unit.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

        }

        holder.cv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = productUnit.indexOf(product);


                double price = 0;
                double mrp = 0;
                try {
                    mrp = Double.valueOf(holder.tv_mrp.getText().toString());

                    price = Double.valueOf(holder.tv_price.getText().toString());


                } catch (Exception ex) {

                }

                DecimalFormat form = new DecimalFormat("0.00");

                for (int i = 0; i < productUnit.size(); i++) {
                    if (i == position) {

                        Log.e(TAG, "onClick:123 "+position+"--"+i+"--"+productUnit.size()+"-"+product.getProductSkuUnitPriceId()+"\n-"+holder.tv_mrp.getText().toString() );
                        // holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_red));
                        productUnit.get(i).setIsDefault("yes");

                        DairyBasketAdator.priceval=product.getProductSalesprice();
                        DairyBasketAdator.mrpval=product.getProductMrp();
                        DairyBasketAdator.packid=String.valueOf(product.getProductSkuUnitPriceId());
                        } else {
                        productUnit.get(i).setIsDefault("no");
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productUnit.size();
    }

    public static class WeightListViewHolder extends RecyclerView.ViewHolder {

        public static TextView tv_productid, tv_unit, tv_price,tv_mrp,tv_unitid;
        CardView cv_product;
        Button btn_addtocart;

        public WeightListViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_unitid = itemView.findViewById(R.id.tv_unitid);

        }
    }
}
