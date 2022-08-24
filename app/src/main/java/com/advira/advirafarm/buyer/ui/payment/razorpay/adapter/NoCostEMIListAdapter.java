package com.advira.advirafarm.buyer.ui.payment.razorpay.adapter;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayEMIList;
import com.advira.advirafarm.buyer.ui.payment.razorpay.RazorPayNoCostEMIList;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.EmiDatum;
import com.advira.advirafarm.buyer.ui.payment.razorpay.api.EmiDatumNoCost;

import java.util.List;


public class NoCostEMIListAdapter extends RecyclerView.Adapter<NoCostEMIListAdapter.EMIViewHolder> implements IConsts {


    //this context we will use to inflate the layout
    private Context mContext;
    //we are storing all the products in a list
    private List<EmiDatumNoCost> emiListData;

    //getting the context and product list with constructor
    public NoCostEMIListAdapter(Context mContext, List<EmiDatumNoCost> emiListData) {
        this.mContext = mContext;
        this.emiListData = emiListData;
    }

    @Override
    public EMIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_emi, null);
        return new EMIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EMIViewHolder holder, int position) {
        //getting the product of the specified position
        EmiDatumNoCost emiDatum = emiListData.get(position);
        //binding the data with the view holder views

        String emidetails = "₹" + emiDatum.getNocostEmi().toString() + " for  "
                + emiDatum.getEmiDuration().toString() + "  months  ";

        holder.tv_emitenure.setText(emiDatum.getEmiDuration().toString());
        holder.tv_emipercent.setText(  "  @ " + emiDatum.getEmiInterestRate().toString() + "%  ");
        holder.tv_emitotalpaid.setText(  "Effective amount could be ₹" + emiDatum.getTotalNocostEmiPaid().toString() + " with interest");

        RazorPayNoCostEMIList.tv_emidiscount.setText("No Cost EMI Discount ₹"+ emiDatum.getDiscount().toString() );


        holder.tv_emidetails.setText(emidetails);
        String hjh = emiDatum.getEmiMinAmount().toString();

        if (emiDatum.getEmiMinAmount().toString().equalsIgnoreCase("1")) {
            holder.rb_emitenure.setChecked(true);
            holder.rb_emitenure.setTag(new Integer(position));

        } else {

            holder.rb_emitenure.setChecked(false);

        }


        holder.rb_emitenure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = emiListData.indexOf(emiDatum);

                for (int i = 0; i < emiListData.size(); i++) {
                    if (i == position) {
                        try
                        {
                            emiListData.get(i).setEmiMinAmount(1);
                            RazorPayNoCostEMIList.tv_tenure.setText(emiListData.get(i).getEmiDuration().toString());
                            RazorPayNoCostEMIList.tv_emidiscount.setText("No Cost EMI Discount ₹"+ emiListData.get(i).getDiscount().toString() );


                        }
                        catch (Exception ex)
                        {

                        }


                    } else {
                        emiListData.get(i).setEmiMinAmount(0);
                    }
                }
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return emiListData.size();

    }


    class EMIViewHolder extends RecyclerView.ViewHolder {

        TextView tv_emitenure,
                tv_emidetails,tv_emipercent,tv_emitotalpaid;
        CardView cv_emi;
        RadioButton rb_emitenure;

        public EMIViewHolder(View itemView) {
            super(itemView);

            tv_emidetails = itemView.findViewById(R.id.tv_emidetails);
            tv_emitenure = itemView.findViewById(R.id.tv_emitenure);
            cv_emi = itemView.findViewById(R.id.cv_emi);
            rb_emitenure = itemView.findViewById(R.id.rb_emitenure);
            tv_emipercent = itemView.findViewById(R.id.tv_emipercent);
            tv_emitotalpaid = itemView.findViewById(R.id.tv_emitotalpaid);



        }
    }


}
