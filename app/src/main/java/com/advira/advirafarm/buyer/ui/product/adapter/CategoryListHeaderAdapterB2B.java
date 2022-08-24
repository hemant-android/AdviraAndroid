package com.advira.advirafarm.buyer.ui.product.adapter;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.product.CategoryProductActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivityB2B;
import com.advira.advirafarm.buyer.ui.product.categoryapi.ProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryListHeaderAdapterB2B extends RecyclerView.Adapter<CategoryListHeaderAdapterB2B.CategoryListHeaderViewHolder> implements IConsts {

    //this context we will use to inflate the layout
    private Context mContext;
    private int minteger = 0;
    private int moq = 10;

    AlertDialog.Builder builder;
    //we are storing all the orders in a list
    private List<ProductList> productList;

    CategoryListAdapterB2B categoryListAdapter;
    private List<com.advira.advirafarm.buyer.ui.product.categoryapi.Product> orderList;


    //getting the context and order list with constructor
    public CategoryListHeaderAdapterB2B(Context mContext, List<ProductList> productList) {
        this.mContext = mContext;
        this.productList = productList;
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
        ProductList product = productList.get(position);
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
        categoryListAdapter = new CategoryListAdapterB2B(mContext, orderList);

        List<com.advira.advirafarm.buyer.ui.product.categoryapi.Product> mListData = product.getProducts();

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


       /* DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / 400 + 0.5); // +0.5 for correct rounding to int.
       */

        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        //AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(mContext, 500);
        //holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setAdapter(categoryListAdapter);


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

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class CategoryListHeaderViewHolder extends RecyclerView.ViewHolder {


        TextView tv_productid, tv_productname, tv_bannerproductid,tv_banneractivity;
        CardView cv_product;
        Button btn_viewall;
        RecyclerView recyclerView;
        ImageView iv_banner, iv_categorybanner;
        RelativeLayout rl_top,rl_footerbanner,rl_headerbanner;

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

            iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent();
                    i.setClass(mContext, ProductDetailsActivityB2B.class);
                    i.putExtra("productname", tv_productname.getText().toString());
                    i.putExtra("productid", tv_bannerproductid.getText().toString());
                    mContext.startActivity(i);

                }
            });


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
