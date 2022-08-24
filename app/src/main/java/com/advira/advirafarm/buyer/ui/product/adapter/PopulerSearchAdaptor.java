package com.advira.advirafarm.buyer.ui.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.product.Search_one;
import com.advira.advirafarm.buyer.ui.product.api.PopulerSearch;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class PopulerSearchAdaptor extends RecyclerView.Adapter<PopulerSearchAdaptor.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<PopulerSearch> coursesArrayList;
    String data[];
    private Context context;

    // creating constructor for our adapter class
    public PopulerSearchAdaptor(String data[], Context context) {
        this.data=data;
        this.context = context;
    }

    @NonNull
    @Override
    public PopulerSearchAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.populer_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopulerSearchAdaptor.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        holder.itemNameTV.setText(data[position]);
        holder.rl_populeritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_one.searchView.setQuery(holder.itemNameTV.getText().toString(),false);
            }
        });

    }


    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final ImageView ivItem;
        private final TextView itemNameTV;
        private RelativeLayout rl_populeritem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            itemNameTV = itemView.findViewById(R.id.tv_itemname);
            ivItem = itemView.findViewById(R.id.iv_history);
            rl_populeritem=itemView.findViewById(R.id.rl_populeritem);
        }
    }
}
