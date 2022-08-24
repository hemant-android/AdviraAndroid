package com.advira.advirafarm.buyer.ui.product.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.product.CategoryProductActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_home;
import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryListHeaderAdapter extends RecyclerView.Adapter<CategoryListHeaderAdapter.CategoryListHeaderViewHolder> implements IConsts {

    //this context we will use to inflate the layout
    private Context mContext;
    CategoryListAdapter categoryListAdapter;
    private int minteger = 0;
    private int moq = 10;

    AlertDialog.Builder builder;
    //we are storing all the orders in a list
    private List<ProductList_home> productList;
    private List<Product_home> orderList;


    //getting the context and order list with constructor
    public CategoryListHeaderAdapter(Context mContext, List<ProductList_home> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    public void getAllHomeProduct(List<ProductList_home> productList)
    {
        this.productList=productList;
    }

    @Override
    public CategoryListHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_categoryheader, null);

        return new CategoryListHeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListHeaderViewHolder holder, int position) {
        //getting the order of the specified position
        ProductList_home product = productList.get(position);
        holder.tv_productname.setText(product.getCategoryName());
        holder.tv_productid.setText(product.getCategoryId());

        if (product.getBanners().size() > 0) {

            holder.tv_bannerproductid.setText(product.getBanners().get(0).getActivityId());
            holder.tv_banneractivity.setText(product.getBanners().get(0).getActivityName());

            String bannerurl = product.getBanners().get(0).getBannerUrl();
            if (bannerurl.length() > 5) {
                holder.rl_footerbanner.setVisibility(View.VISIBLE);
                holder.iv_banner.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(bannerurl).placeholder(R.drawable.progress_animation).into(holder.iv_banner);
            } else {
                holder.iv_banner.setVisibility(View.GONE);
                holder.rl_footerbanner.setVisibility(View.GONE);
            }
        } else {
            holder.iv_banner.setVisibility(View.GONE);
            holder.rl_footerbanner.setVisibility(View.GONE);
        }

        orderList = new ArrayList<>();
        categoryListAdapter = new CategoryListAdapter(mContext, orderList);
        List<Product_home> mListData = product.getProducts();

        if (mListData != null && mListData.size() > 0) {
            if (mListData != null && mListData.size() > 4) {
                orderList.add(mListData.get(0));
                orderList.add(mListData.get(1));
                orderList.add(mListData.get(2));
                orderList.add(mListData.get(3));
                holder.tv_productname.setVisibility(View.VISIBLE);
                holder.btn_viewall.setVisibility(View.VISIBLE);
            } else {
                orderList.addAll(mListData);
                holder.btn_viewall.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.tv_productname.setVisibility(View.GONE);
            holder.btn_viewall.setVisibility(View.GONE);
        }


        //categoryListAdapter.notifyDataSetChanged();
        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setAdapter(categoryListAdapter);
        /*holder.recyclerView.setHasFixedSize(false);
        holder.recyclerView.setItemViewCacheSize(20);
        holder.recyclerView.setDrawingCacheEnabled(true);
        holder.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/

        try {
            String themecolordark = product.getThemecolordark();
            String themecolorlight = product.getThemecolorlight();
            holder.rl_top.setBackgroundColor(Color.parseColor(themecolorlight));
            holder.btn_viewall.getBackground().setColorFilter(Color.parseColor(themecolordark), PorterDuff.Mode.SRC);

        }
        catch (Exception ex)
        {

        }


        String categorybannerurl = product.getCategoryHeaderBanner();


        if (categorybannerurl.length() > 5)

        {
            holder.tv_productname.setVisibility(View.GONE);
            holder.btn_viewall.setVisibility(View.GONE);
            holder.iv_categorybanner.setVisibility(View.VISIBLE);
            holder.rl_headerbanner.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(categorybannerurl).placeholder(R.drawable.progress_animation).into(holder.iv_categorybanner);
        } else {
            holder.tv_productname.setVisibility(View.VISIBLE);
            holder.btn_viewall.setVisibility(View.VISIBLE);
            holder.iv_categorybanner.setVisibility(View.GONE);
            holder.rl_headerbanner.setVisibility(View.GONE);
        }

        holder.iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                if (profilemode.equalsIgnoreCase("B2C")) {

                    Intent i = new Intent();
                    i.setClass(mContext, ProductDetailsActivity.class);
                    i.putExtra("productname", holder.tv_productname.getText().toString());
                    i.putExtra("productid", holder.tv_bannerproductid.getText().toString());
                    Log.e(TAG, "onClick: Productdetailspage via "+holder.tv_productname.getText().toString()+"--"+holder.tv_bannerproductid.getText().toString());
                    //Toast.makeText(view.getContext(), "Search Data Saved-8", Toast.LENGTH_LONG).show();
                    mContext.startActivity(i);


                } else {

                    Intent i = new Intent();
                    i.setClass(mContext, ProductDetailsActivityB2B.class);
                    i.putExtra("productname",holder.tv_productname.getText().toString());
                    i.putExtra("productid", holder.tv_bannerproductid.getText().toString());
                    //Toast.makeText(view.getContext(), "Search Data Saved-145", Toast.LENGTH_LONG).show();
                    mContext.startActivity(i);
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class CategoryListHeaderViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_productname, tv_bannerproductid, tv_banneractivity;
        CardView cv_product;
        Button btn_viewall;
        RecyclerView recyclerView;
        ImageView iv_categorybanner, iv_banner;
        RelativeLayout rl_top, rl_footerbanner, rl_headerbanner;

        public CategoryListHeaderViewHolder(View itemView) {
            super(itemView);

            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            btn_viewall = itemView.findViewById(R.id.btn_viewall);
            cv_product = itemView.findViewById(R.id.cv_product);
            recyclerView = itemView.findViewById(R.id.recyclerView2);
            iv_banner = itemView.findViewById(R.id.iv_banner);
            rl_top = itemView.findViewById(R.id.rl_top);
            tv_bannerproductid = itemView.findViewById(R.id.tv_bannerproductid);
            iv_categorybanner = itemView.findViewById(R.id.iv_categorybanner);
            tv_banneractivity = itemView.findViewById(R.id.tv_banneractivity);
            rl_footerbanner = itemView.findViewById(R.id.rl_footerbanner);
            rl_headerbanner = itemView.findViewById(R.id.rl_headerbanner);

            /*iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String profilemode = SharedPrefUtil.getProfileMode(mContext, SHARED_PREF_ProfileMode, "B2B");

                    if (profilemode.equalsIgnoreCase("B2C")) {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivity.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_bannerproductid.getText().toString());
                        mContext.startActivity(i);


                    } else {

                        Intent i = new Intent();
                        i.setClass(mContext, ProductDetailsActivityB2B.class);
                        i.putExtra("productname", tv_productname.getText().toString());
                        i.putExtra("productid", tv_bannerproductid.getText().toString());
                        mContext.startActivity(i);
                    }


                }
            });
*/

            btn_viewall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent();
                    i.setClass(mContext, CategoryProductActivity.class);
                    i.putExtra("categoryid", tv_productid.getText().toString());
                    i.putExtra("category", tv_productname.getText().toString());
                    mContext.startActivity(i);

                }
            });

            iv_categorybanner.setOnClickListener(new View.OnClickListener() {
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
