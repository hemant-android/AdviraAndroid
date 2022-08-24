package com.advira.advirafarm.buyer.ui.guest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.guest.ProductDetailsActivityGuest;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.api.ProductBanner;
import com.advira.advirafarm.buyer.ui.product.api.ProductUnit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductUnitAdapterGuest extends RecyclerView.Adapter<ProductUnitAdapterGuest.CategoryListViewHolder> implements IConsts {

    AlertDialog.Builder builder;
    List<ProductBanner> productImagesList = new ArrayList<>();
    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    //we are storing all the orders in a list
    private List<ProductUnit> productUnit;

    //getting the context and order list with constructor
    public ProductUnitAdapterGuest(Context mContext, List<ProductUnit> productUnit) {
        this.mContext = mContext;
        this.productUnit = productUnit;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_productunit, null);

        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        //getting the order of the specified position
        ProductUnit product = productUnit.get(position);
        holder.tv_price.setText(product.getProductSalesprice());
        holder.tv_mrp.setText(product.getProductMrp());
        holder.tv_unit.setText(product.getProductUnits() + " " + product.getProductUnitType());
        holder.tv_unitid.setText(product.getProductUnitsId());

        if (product.getProductIsDefault().equalsIgnoreCase("yes")) {
            holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_green_unit));
            holder.tv_unit.setTextColor(mContext.getResources().getColor(R.color.colorYellow));

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

                //  tv_prodname.setText(response.body().getProductDetails().getProductName());
                ProductDetailsActivityGuest.tv_price.setText(form.format(price));
                ProductDetailsActivityGuest.tv_mrpval.setText(form.format(mrp));
                ProductDetailsActivityGuest.tv_unitid.setText(holder.tv_unitid.getText().toString());


                for (int i = 0; i < productUnit.size(); i++) {
                    if (i == position) {
                        // holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_red));
                        productUnit.get(i).setProductIsDefault("yes");
                        productImagesList = productUnit.get(i).getProductBanners();


                        if (productUnit.get(i).getProductInstock().equalsIgnoreCase("Out-of-Stock")) {
                            ProductDetailsActivityGuest.tv_stock.setText("Out-of-Stock");
                            ProductDetailsActivityGuest.tv_stock.setVisibility(View.VISIBLE);
                            ProductDetailsActivityGuest.ll_addremovebutton.setVisibility(View.INVISIBLE);
                            //ProductDetailsActivityGuest.ll_addremove.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityGuest.btn_buynow.setEnabled(false);
                            ProductDetailsActivityGuest.rl_addtocart.setEnabled(false);
                            ProductDetailsActivityGuest.tv_placeorder.setText("Out of Stock");
                        } else {
                            ProductDetailsActivityGuest.tv_stock.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityGuest.ll_addremovebutton.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityGuest.btn_buynow.setEnabled(true);
                            //ProductDetailsActivityGuest.ll_addremove.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityGuest.rl_addtocart.setEnabled(true);
                            ProductDetailsActivityGuest.tv_placeorder.setText("Buy Now");
                        }


                    } else {
                        productUnit.get(i).setProductIsDefault("no");
                    }
                }

                ProductDetailsActivityGuest.CarousalImageChange(productImagesList);
                ProductDetailsActivityGuest.spn_qty.setSelection(0);


                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return productUnit.size();
    }


    class CategoryListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_unit, tv_price, tv_mrp, tv_unitid;
        CardView cv_product;
        Button btn_addtocart;

        public CategoryListViewHolder(View itemView) {
            super(itemView);

            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_unitid = itemView.findViewById(R.id.tv_unitid);

           /* cv_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    double price = 0;
                    double mrp = 0;
                    try {
                        //  mrp = Double.valueOf(tv_price.getText().toString());

                        price = Double.valueOf(tv_price.getText().toString());

                    } catch (Exception ex) {

                    }

                    DecimalFormat form = new DecimalFormat("0.00");

                    //  tv_prodname.setText(response.body().getProductDetails().getProductName());
                    ProductDetailsActivityGuest.tv_price.setText(form.format(price));

                    for (int i = 0; i < productUnit.size(); i++) {
                        // cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_light));
                    }


                    //itemView.getId();

                    //cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_red));


                    //  tv_mrpval.setText(form.format(mrp));


                }
            });*/


        }
    }


}
