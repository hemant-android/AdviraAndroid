package com.advira.advirafarm.buyer.ui.discount.adapter;

import android.app.Activity;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.buynow.BuynowActivity;
import com.advira.advirafarm.buyer.ui.cart.OrderPreviewActivity;
import com.advira.advirafarm.buyer.ui.cart.api.DiscountCoupon;
import com.advira.advirafarm.buyer.ui.discount.DiscountListActivity;

import java.util.List;

import static android.content.ContentValues.TAG;


public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.AddressViewHolder> implements IConsts {


    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    //this context we will use to inflate the layout
    private Context mContext;
    //we are storing all the products in a list
    private List<DiscountCoupon> discountCouponList;

    //getting the context and product list with constructor
    public DiscountListAdapter(Context mContext, List<DiscountCoupon> discountCouponList) {
        this.mContext = mContext;
        this.discountCouponList = discountCouponList;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_discount, null);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        //getting the product of the specified position
        DiscountCoupon discountCoupon = discountCouponList.get(position);
        //binding the data with the view holder views


        holder.tv_discountid.setText(discountCoupon.getId());
        holder.tv_discountname.setText(discountCoupon.getDiscountCouponName());
        holder.tv_discountdetails.setText(discountCoupon.getDiscountDetails());


        holder.btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                OrderPreviewActivity.tv_discountid.setText(discountCoupon.getId());
                OrderPreviewActivity.tv_discount_coupon_name.setText(discountCoupon.getDiscountCouponName());
                OrderPreviewActivity.tv_discount_details.setText(discountCoupon.getDiscountDetails());


                ((Activity)mContext).finish();*/

                try {
                    OrderPreviewActivity.tv_discountid.setText(discountCoupon.getId());
                    OrderPreviewActivity.tv_discount_details.setText(discountCoupon.getDiscountDetails());
                    OrderPreviewActivity.tv_discount_coupon_name.setText(discountCoupon.getDiscountCouponName());
                    OrderPreviewActivity.tv_discount_type.setText(discountCoupon.getDiscountType());

                    //Log.e(TAG, "onClick: "+"\n"+discountCoupon.getId()+"\n"+discountCoupon.getDiscountDetails()+"\n"+discountCoupon.getDiscountCouponName()+"\n"+discountCoupon.getDiscountAmount());
                    ((Activity) mContext).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    BuynowActivity.tv_discountid.setText(discountCoupon.getId());
                    BuynowActivity.tv_discount_coupon_name.setText(discountCoupon.getDiscountCouponName());
                    BuynowActivity.tv_discount_details.setText(discountCoupon.getDiscountDetails());
                    //OrderPreviewActivity.tv_discount_amount.setText(discountCoupon.getDiscountAmount());
                    ((Activity) mContext).finish();
                }catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

    }


    @Override
    public int getItemCount() {
        return discountCouponList.size();

    }


    class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView tv_discountid,
                tv_discountname,
                tv_discountdetails;

        Button btn_apply;

        public AddressViewHolder(View itemView) {
            super(itemView);


            tv_discountname = itemView.findViewById(R.id.tv_discountname);
            tv_discountdetails = itemView.findViewById(R.id.tv_discountdetails);
            tv_discountid = itemView.findViewById(R.id.tv_discountid);
            btn_apply = itemView.findViewById(R.id.btn_apply);

        }
    }



}
