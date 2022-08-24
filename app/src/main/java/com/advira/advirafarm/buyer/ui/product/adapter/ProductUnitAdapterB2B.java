package com.advira.advirafarm.buyer.ui.product.adapter;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.api.ProductBanner;
import com.advira.advirafarm.buyer.ui.product.api.ProductUnit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductUnitAdapterB2B extends RecyclerView.Adapter<ProductUnitAdapterB2B.ProductUnitListViewHolder> implements IConsts {

    AlertDialog.Builder builder;
    List<ProductBanner> productImagesList = new ArrayList<>();
    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;
    //we are storing all the orders in a list
    private List<ProductUnit> productUnit;

    //getting the context and order list with constructor
    public ProductUnitAdapterB2B(Context mContext, List<ProductUnit> productUnit) {
        this.mContext = mContext;
        this.productUnit = productUnit;
    }

    @Override
    public ProductUnitListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_productunit, null);

        return new ProductUnitListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductUnitListViewHolder holder, int position) {
        //getting the order of the specified position
        ProductUnit product = productUnit.get(position);
        holder.tv_price.setText(product.getProductSalesprice());
        holder.tv_mrp.setText(product.getProductMrp());
        holder.tv_unit.setText(product.getProductUnits() + " " + product.getProductUnitType());
        holder.tv_unitid.setText(product.getProductUnitsId());

        if (product.getProductIsDefault().equalsIgnoreCase("yes")) {
            holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_green_unit));
            holder.tv_unit.setTextColor(mContext.getResources().getColor(R.color.colorYellow));

        } else {
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
                ProductDetailsActivityB2B.tv_price.setText(form.format(price));
                ProductDetailsActivityB2B.tv_mrpval.setText(form.format(mrp));

                ProductDetailsActivityB2B.tv_unitid.setText(holder.tv_unitid.getText().toString());
                ProductDetailsActivityB2B.tv_packsize.setText(holder.tv_unit.getText().toString());



                for (int i = 0; i < productUnit.size(); i++) {
                    if (i == position) {
                        // holder.cv_product.setBackground(mContext.getResources().getDrawable(R.drawable.border_bg_color_red));
                        productUnit.get(i).setProductIsDefault("yes");
                        productImagesList = productUnit.get(i).getProductBanners();

                        if (productUnit.get(i).getProductInstock().equalsIgnoreCase("Out-of-Stock")) {
                            ProductDetailsActivityB2B.tv_stock.setText("Out-of-Stock");
                            ProductDetailsActivityB2B.tv_stock.setVisibility(View.VISIBLE);
                            ProductDetailsActivityB2B.ll_addremovebutton.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityB2B.ll_addremove.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityB2B.btn_buynow.setEnabled(false);
                            ProductDetailsActivityB2B.rl_addtocart.setEnabled(false);
                            ProductDetailsActivityB2B.tv_placeorder.setText("Out of Stock");
                        } else {
                            ProductDetailsActivityB2B.tv_stock.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityB2B.ll_addremovebutton.setVisibility(View.INVISIBLE);
                            ProductDetailsActivityB2B.btn_buynow.setEnabled(true);
                            ProductDetailsActivityB2B.rl_addtocart.setEnabled(true);
                            ProductDetailsActivityB2B.ll_addremove.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        productUnit.get(i).setProductIsDefault("no");
                    }
                }

                ProductDetailsActivityB2B.CarousalImageChange(productImagesList);
                ProductDetailsActivityB2B.spn_qty.setSelection(0);


                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return productUnit.size();
    }


    class ProductUnitListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_unit, tv_price, tv_mrp,tv_unitid;
        CardView cv_product;
        Button btn_addtocart;

        public ProductUnitListViewHolder(View itemView) {
            super(itemView);

            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_mrp = itemView.findViewById(R.id.tv_mrp);
            tv_unitid= itemView.findViewById(R.id.tv_unitid);


        }
    }


}
