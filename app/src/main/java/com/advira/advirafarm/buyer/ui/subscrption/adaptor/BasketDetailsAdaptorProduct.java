package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.myaccount.SubscriptionActivity;
import com.advira.advirafarm.buyer.ui.subscrption.MySubscription;
import com.advira.advirafarm.buyer.ui.subscrption.api.BasketDatum;
import com.advira.advirafarm.buyer.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class BasketDetailsAdaptorProduct extends RecyclerView.Adapter<BasketDetailsAdaptorProduct.BasketDetailsViewHolder> implements IConsts {

    private int minteger = 0;
    private int moq = 10;
    private String previewtotal="0";
    AlertDialog.Builder builder;

    String membershipName="";
    String basket_productid="";

    private List<BasketDatum> basketList;
    private static Context mContext;
    String subscription_id="";
    String SelectedDate= MySubscription.SelectedDate;


    public BasketDetailsAdaptorProduct(Context mContext, List<BasketDatum> basketList) {
        this.mContext = mContext;
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public BasketDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_basketproduct, null);

        return new BasketDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketDetailsViewHolder holder, int position) {
        BasketDatum product = basketList.get(position);

        //holder.tv_srno.setText(product.getProductId());
        holder.tv_productname.setText(product.getProdctName());
        holder.tv_price.setText(product.getpTotalPrice());
        holder.integer_number.setText(product.getpCount());
        holder.textViewRating.setText(product.getSkuBrandName());

        holder.tv_prodid.setText(String.valueOf(product.getProductId()));
        basket_productid=String.valueOf(product.getProductId());
        holder.ll_addremove.setVisibility(View.GONE);
        //holder.tv_productname.setText(product.getProdctName());
        //holder.tv_stock.setText(pro);
        // holder.tv_pack.setText(product.getProductDiscount());
        holder.tv_pack.setText(product.getpCount()+" X "+product.getpUnits()+product.getpUnitType());

        //holder.tv_minqty.setText("Min order : " + product.get());
        holder.tv_orderqtyval.setText(product.getpCount());
        holder.tv_inr.setText("Rate ₹ ");
        holder.tv_mrp.setText("₹ "+product.getpTotalPrice());
        holder.tv_price.setText(product.getpPricePerPack());
        String product_delivery_status=product.getProductDeliveryStatus();
        //SelectedDate=SubscriptionActivity.SelectedDate;
        if(product_delivery_status!=null) {
            if (product_delivery_status.equalsIgnoreCase("Cancelled")) {
                holder.tv_remove.setVisibility(View.GONE);
                holder.ll_addremove.setVisibility(View.GONE);
                holder.tv_discount.setText("Cancelled");
                holder.tv_discount.setTextColor(Color.RED);
                holder.tv_discount.setVisibility(View.VISIBLE);
                holder.tv_productname.setBackgroundResource(R.drawable.strike_through);
            } else if (product_delivery_status.equalsIgnoreCase("Deliverd")) {
                holder.ll_addremove.setVisibility(View.GONE);
                holder.tv_remove.setVisibility(View.VISIBLE);
                holder.tv_remove.setText("Delivered");
                holder.tv_remove.setTextColor(Color.parseColor("#2A882D"));
                holder.tv_remove.setBackgroundResource(R.color.white);
                holder.tv_remove.setEnabled(false);
            } else {

            }
        }

        String product_image = product.getProductImageUrl();

        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.imageView);

        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        double price = Double.parseDouble(product.getpPricePerPack());
        double totprice = Double.parseDouble(product.getpTotalPrice());
        double mrp = Double.parseDouble(product.getpTotalPrice());

        DecimalFormat form = new DecimalFormat("0.00");
        //holder.tv_mrpval.setText(form.format(mrp));
        holder.tv_price.setText(form.format(price));
        holder.tv_itempriceval.setText(form.format(totprice));

        //holder.tv_discount.setText(product.getProductMrpDiscountLabel());
        //holder.tv_orderqtyunit.setText(product.getProductQuantity());

        moq = Integer.valueOf(holder.tv_minqty.getText().toString().replace("Min order : ", ""));

        /*holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefromsubscription(holder.tv_prodid.getText().toString());
            }
        });*/

        holder.cv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cv_product.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class BasketDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productname, tv_stock,textViewRating;
        ImageView imageView, iv_rx;
        TextView integer_number;
        Button btn_increase;
        Button btn_decrease;
        ImageView btn_remove;
        CardView cv_product;
        LinearLayout ll_addremove;
        TextView tv_mrpval, tv_pack, tv_minqty, tv_price, tv_itempriceval,
                tv_orderqtyval, tv_prodid, tv_inr, tv_boxsize, tv_mrp, tv_orderqtyunit,tv_discount,tv_remove;

        public BasketDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productname = itemView.findViewById(R.id.tv_productname);
            cv_product=itemView.findViewById(R.id.cv_product);
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
            tv_remove=itemView.findViewById(R.id.tv_remove);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            btn_decrease = itemView.findViewById(R.id.btn_decrease);
            btn_increase = itemView.findViewById(R.id.btn_increase);
            integer_number = itemView.findViewById(R.id.integer_number);
            ll_addremove=itemView.findViewById(R.id.ll_addremove);
            textViewRating=itemView.findViewById(R.id.textViewRating);

        }
    }
}
