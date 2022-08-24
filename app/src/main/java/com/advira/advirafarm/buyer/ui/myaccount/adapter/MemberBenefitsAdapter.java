package com.advira.advirafarm.buyer.ui.myaccount.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.myaccount.MembershipFragment;
import com.advira.advirafarm.buyer.ui.myaccount.api.Member_Plan;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipFaq;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipPrice;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.HomeFragment;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
import com.advira.advirafarm.buyer.ui.product.ProductDetailsActivity;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;

import static android.content.ContentValues.TAG;
import java.text.DecimalFormat;
import java.util.List;

public class MemberBenefitsAdapter extends RecyclerView.Adapter<MemberBenefitsAdapter.MemberBenefitsViewHolder> implements IConsts {

    private Context mContext;
    private List<MembershipPrice> BenefitList;
    public static double price1;
    public static int duration1;
    public static String buttonLabel;


    public MemberBenefitsAdapter(Context mContext, List<MembershipPrice> benefitList) {
        this.mContext = mContext;
        BenefitList = benefitList;
    }

    @NonNull
    @Override
    public MemberBenefitsAdapter.MemberBenefitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_memberbenefits, null);
        return new MemberBenefitsAdapter.MemberBenefitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberBenefitsAdapter.MemberBenefitsViewHolder holder, int position) {
        MembershipPrice faq=BenefitList.get(position);
        holder.tv_addname.setText("FOR "+faq.getDuration());
        holder.tv_description.setText("Enjoy Exclusive deals, discount and offers with membership.");
        holder.tv_Price.setText("Rs."+faq.getPrice()+"/-");

        if(faq.getIsDefault().equalsIgnoreCase("Yes")){
            holder.rb_add.setChecked(true);
            price1 = Double.valueOf(faq.getPrice());
            duration1=Integer.valueOf(faq.getMonths());
            buttonLabel=faq.getButtonLabel();
            int days=duration1*30;

            MembershipFragment.price=String.valueOf(price1);
            MembershipFragment.duration=String.valueOf(days);
            MembershipFragment.buttonLabel=buttonLabel;

        }
        else{
            holder.rb_add.setChecked(false);
        }


        holder.rb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = BenefitList.indexOf(faq);

                try {
                    price1 = Double.valueOf(faq.getPrice());
                    duration1=Integer.valueOf(faq.getMonths());
                    buttonLabel=faq.getButtonLabel();
                    int days=duration1*30;

                    MembershipFragment.price=String.valueOf(price1);
                    MembershipFragment.duration=String.valueOf(days);
                    MembershipFragment.buttonLabel=buttonLabel;
                    //Log.e(TAG, "onClick: mba\n"+price1+"\n"+duration1+"\n"+days);
                } catch (Exception ex) {

                }
                DecimalFormat form = new DecimalFormat("0.00");

                for (int i = 0; i < BenefitList.size(); i++) {
                    if (i == position) {
                        BenefitList.get(i).setIsDefault("yes");


                    } else {
                        BenefitList.get(i).setIsDefault("no");

                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return BenefitList.size();
    }

    public class MemberBenefitsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_addname,tv_description,tv_Price;
        CardView cv_settings;
        RadioButton rb_add;

        public MemberBenefitsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_addname=itemView.findViewById(R.id.tv_addname);
            tv_description=itemView.findViewById(R.id.tv_description);
            cv_settings = itemView.findViewById(R.id.cv_settings);
            rb_add = itemView.findViewById(R.id.rb_add);
            tv_Price = itemView.findViewById(R.id.tv_Price);
        }
    }


}
