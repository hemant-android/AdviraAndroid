package com.advira.advirafarm.buyer.ui.category.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.guest.CategoryProductActivityGuest;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.CategoryProductActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.categoryapi.CategoryList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryImageAdapter extends RecyclerView.Adapter<CategoryImageAdapter.CategoryListViewHolder> implements IConsts {

    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;

    AlertDialog.Builder builder;
    //we are storing all the orders in a list
    private List<CategoryList> productList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    CategoryItemAdapter categoryItemAdapter;
    private List<Product> orderList;


    //getting the context and order list with constructor
    public CategoryImageAdapter(Context mContext, List<CategoryList> productList) {
        this.mContext = mContext;
        this.productList = productList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_categoryimageheader, null);
        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        //getting the order of the specified position
        CategoryList product = productList.get(position);
        holder.tv_productname.setText(product.getCategoryName());
        holder.tv_productid.setText(product.getCategoryId());
        String product_image = product.getCategoryImage();
        if (product_image.length() > 5) {
            Picasso.with(mContext).load(product_image).placeholder(R.drawable.progress_animation).into(holder.iv_category);
        }
        else
        {
            holder.iv_category.setVisibility(View.INVISIBLE);
        }

        orderList = new ArrayList<>();
        categoryItemAdapter = new CategoryItemAdapter(mContext, orderList);



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class CategoryListViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_productname;
        CardView cv_product;
        Button btn_addtocart;
        ImageView iv_category;

        public CategoryListViewHolder(View itemView) {
            super(itemView);

            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            btn_addtocart = itemView.findViewById(R.id.btn_addtocart);
            cv_product = itemView.findViewById(R.id.cv_product);
            iv_category = itemView.findViewById(R.id.iv_category);


            cv_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usertype = SharedPrefUtil.getUserActive(mContext, SHARED_PREF_UserActive, "guest");
                    if (usertype.equalsIgnoreCase("guest")) {
                        Intent i = new Intent();
                        i.setClass(mContext, CategoryProductActivityGuest.class);
                        i.putExtra("categoryid", tv_productid.getText().toString());
                        i.putExtra("category", tv_productname.getText().toString());
                        mContext.startActivity(i);
                    } else {
                        Intent i = new Intent();
                        i.setClass(mContext, CategoryProductActivity.class);
                        i.putExtra("categoryid", tv_productid.getText().toString());
                        i.putExtra("category", tv_productname.getText().toString());
                        ProductDetailsActivity.back=ProductDetailsActivity.back+1;
                        mContext.startActivity(i);
                    }


                }
            });


        }
    }


}