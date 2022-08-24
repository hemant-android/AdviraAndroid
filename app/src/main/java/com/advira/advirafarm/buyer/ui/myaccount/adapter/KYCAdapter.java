package com.advira.advirafarm.buyer.ui.myaccount.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.advira.advirafarm.buyer.ui.registration.profile.api.me.KycInfo;

import java.util.List;

public class KYCAdapter extends RecyclerView.Adapter<KYCAdapter.CategoryListViewHolder> implements IConsts {

    //this context we will use to inflate the layout
    private Context mContext;

    private List<KycInfo> orderList;


    //getting the context and order list with constructor
    public KYCAdapter(Context mContext, List<KycInfo> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_kycview, null);

        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        //getting the order of the specified position
        KycInfo kycInfo = orderList.get(position);
        holder.tv_docname.setText(kycInfo.getDocTypeName());
        holder.tv_status.setText(kycInfo.getVerificationStatus());



        String verificationstatus = kycInfo.getVerificationStatus();

        if (verificationstatus.equalsIgnoreCase("Verified")) {

            holder.tv_status.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_verified_icon),null,null,null);

            holder.tv_status.setTextColor(Color.parseColor("#00973d"));

        } else if (verificationstatus.equalsIgnoreCase("Not-verified")) {

            holder.tv_status.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_not_verified_icon),null,null,null);
            holder.tv_status.setTextColor(Color.parseColor("#f77e0b"));

        } else if (verificationstatus.equalsIgnoreCase("Under-Review")) {

            holder.tv_status.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_progress_24dp),null,null,null);
            holder.tv_status.setTextColor(Color.parseColor("#2E3191"));
        }


    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }


    class CategoryListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_docid, tv_docname,tv_status;
        CardView cv_product;
        Button btn_addtocart;
       
        public CategoryListViewHolder(View itemView) {
            super(itemView);

            tv_docname = itemView.findViewById(R.id.tv_docname);
            tv_docid = itemView.findViewById(R.id.tv_docid);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            tv_status = itemView.findViewById(R.id.tv_status);




        }
    }


}
