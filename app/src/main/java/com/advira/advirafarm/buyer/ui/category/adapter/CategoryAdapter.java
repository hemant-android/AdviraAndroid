package com.advira.advirafarm.buyer.ui.category.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.product.CategoryProductActivity;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryListViewHolder> implements IConsts {

    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;

    AlertDialog.Builder builder;
    //we are storing all the orders in a list
    private List<ProductList> productList;

    CategoryItemAdapter searchListAdapter;
    private List<com.advira.advirafarm.buyer.ui.product.categoryapi.Product> orderList;


    //getting the context and order list with constructor
    public CategoryAdapter(Context mContext, List<ProductList> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_categoryheader, null);

        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        //getting the order of the specified position
        ProductList product = productList.get(position);
        holder.tv_productname.setText(product.getCategoryName());
        holder.tv_productid.setText(product.getCategoryId());

        orderList = new ArrayList<>();
        searchListAdapter = new CategoryItemAdapter(mContext, orderList);


        List<com.advira.advirafarm.buyer.ui.product.categoryapi.Product> mListData = product.getProducts();

        if (mListData != null && mListData.size() > 0) {


            orderList.addAll(mListData);
            holder.btn_addtocart.setVisibility(View.INVISIBLE);

        }
        else
        {
            holder.tv_productname.setVisibility(View.GONE);
            holder.btn_addtocart.setVisibility(View.GONE);
        }

        //holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setAdapter(searchListAdapter);

        holder.btn_addtocart.setVisibility(View.GONE);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class CategoryListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_productname;
        CardView cv_product;
        Button btn_addtocart;
        RecyclerView recyclerView;

        public CategoryListViewHolder(View itemView) {
            super(itemView);

            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            recyclerView  = itemView.findViewById(R.id.recyclerView2);

            btn_addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent();
                    i.setClass(mContext, CategoryProductActivity.class);
                    i.putExtra("categoryid", tv_productid.getText().toString());
                    i.putExtra("category", tv_productname.getText().toString());
                    mContext.startActivity(i);

                }
            });




        }
    }


}