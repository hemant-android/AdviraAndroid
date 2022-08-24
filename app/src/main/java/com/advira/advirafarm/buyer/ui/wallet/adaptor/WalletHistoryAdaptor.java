package com.advira.advirafarm.buyer.ui.wallet.adaptor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.subscrption.adaptor.BasketDetailsAdaptor;
import com.advira.advirafarm.buyer.ui.wallet.api.WalletDatum;

import java.util.List;

public class WalletHistoryAdaptor extends RecyclerView.Adapter<WalletHistoryAdaptor.WalletHistoryViewHolder> implements IConsts {

    private static Context mContext;
    private List<WalletDatum> transactionList;

    public WalletHistoryAdaptor(Context mContext,List<WalletDatum> transactionList) {
        this.mContext = mContext;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public WalletHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_wallethistory, null);

        return new WalletHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryViewHolder holder, int position) {

        WalletDatum product=transactionList.get(position);

        holder.tv_shortdes.setText(product.getOrderDate());
        //holder.tv_amount.setText(product.getAmount());
        String deductionType=product.getDataType();


        if(product.getStatus().equalsIgnoreCase("FAILED")){
            holder.tv_title.setText("Transaction Failed");
            holder.tv_amount.setTextColor(Color.RED);
            holder.tv_amount.setText("- ₹ "+product.getAmount());
        }else if(product.getDataType().equalsIgnoreCase("withdrawal")){
            holder.tv_title.setText(product.getOrderDetails());
            holder.tv_amount.setTextColor(Color.RED);
            holder.tv_amount.setText("- ₹ "+product.getAmount());
        }
        else{
            holder.tv_title.setText(product.getOrderDetails());
            holder.tv_amount.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.tv_amount.setText("+ ₹ "+product.getAmount());
        }

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class WalletHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_shortdes,tv_amount;

        public WalletHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title=itemView.findViewById(R.id.tv_title);
            tv_shortdes=itemView.findViewById(R.id.tv_shortdes);
            tv_amount=itemView.findViewById(R.id.tv_amount);

        }
    }
}
