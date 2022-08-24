package com.advira.advirafarm.buyer.ui.product.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.dbHandler.DBHandler;
import com.advira.advirafarm.buyer.ui.cart.CartActivity;
import com.advira.advirafarm.buyer.ui.cart.adapter.CartAdapter;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.api.PopulerSearch;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.ArrayList;

public class RecentSearchAdaptor extends RecyclerView.Adapter<RecentSearchAdaptor.RecentSearchViewHolder>{

    private ArrayList<PopulerSearch> rsModalArrayList;
    private Context context;
    private DBHandler dbHandler;


    public RecentSearchAdaptor(ArrayList<PopulerSearch> courseModalArrayList, Context context) {
        this.rsModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dbHandler = new DBHandler(context);
        return new RecentSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.populer_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PopulerSearch modal = rsModalArrayList.get(position);
        holder.itemNameTV.setText(modal.getProductname());
        holder.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteItem(position,holder.itemNameTV.getText().toString());
                dbHandler.deleteCourse(holder.itemNameTV.getText().toString());
                rsModalArrayList.remove(position);
                notifyDataSetChanged();

            }
        });
        holder.rl_populeritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_one.searchView.setQuery(holder.itemNameTV.getText().toString(),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rsModalArrayList.size();
    }

    public class RecentSearchViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivItem,ivClear;
        private final TextView itemNameTV;
        private RelativeLayout rl_populeritem;

        public RecentSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTV = itemView.findViewById(R.id.tv_itemname);
            ivItem = itemView.findViewById(R.id.iv_history);
            ivClear=itemView.findViewById(R.id.iv_clear);
            ivClear.setVisibility(View.VISIBLE);
            rl_populeritem=itemView.findViewById(R.id.rl_populeritem);
        }
    }
}
