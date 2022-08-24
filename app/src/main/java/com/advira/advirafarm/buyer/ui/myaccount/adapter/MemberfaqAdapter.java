package com.advira.advirafarm.buyer.ui.myaccount.adapter;

import android.content.Context;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.ui.cart.adapter.CartAdapter;
import com.advira.advirafarm.buyer.ui.myaccount.api.MembershipFaq;

import java.util.List;

public class MemberfaqAdapter extends RecyclerView.Adapter<MemberfaqAdapter.MemberfaqViewHolder> implements IConsts {

    private Context mContext;

    private List<MembershipFaq> faqList;

    public MemberfaqAdapter(Context mContext, List<MembershipFaq> faqList) {
        this.mContext = mContext;
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public MemberfaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_memberfaq, null);
        return new MemberfaqAdapter.MemberfaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberfaqViewHolder holder, int position) {
        MembershipFaq faq=faqList.get(position);
        holder.tv_faqquestion.setText(faq.getQuestion());
        holder.tv_faqanswer.setText(Html.fromHtml(faq.getAnswer()));
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class MemberfaqViewHolder extends RecyclerView.ViewHolder {

        TextView tv_faqquestion,tv_faqanswer;
        boolean VISIBLE_ANSWER = false;


        public MemberfaqViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_faqquestion=itemView.findViewById(R.id.tv_faqquestion);
            tv_faqanswer=itemView.findViewById(R.id.tv_faqanswer);
            tv_faqanswer.setVisibility(View.GONE);

            tv_faqquestion.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(VISIBLE_ANSWER){
                        VISIBLE_ANSWER=false;
                        //tv_faqquestion.setCompoundDrawables();
                        tv_faqquestion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                        tv_faqanswer.setVisibility(View.VISIBLE);
                    }
                    else {
                        VISIBLE_ANSWER = true;
                        //tv_faqquestion.setInputType(InputType.TYPE_CLASS_TEXT);
                        tv_faqanswer.setVisibility(View.GONE);
                        tv_faqquestion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                        //tv_faqquestion.setSelection(tv_faqquestion.getText().length());
                    }
                    return false;
                }
            });

            /*tv_faqquestion.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int DRAWABLE_RIGHT = 2;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (tv_faqquestion.getRight() - tv_faqquestion.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            if (VISIBLE_ANSWER) {
                                VISIBLE_ANSWER = false;
                                //tv_faqquestion.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                tv_faqquestion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                                tv_faqanswer.setVisibility(View.GONE);
                                //tv_faqquestion.setSelection(tv_faqquestion.getText().length());

                            } else {
                                VISIBLE_ANSWER = true;
                                //tv_faqquestion.setInputType(InputType.TYPE_CLASS_TEXT);
                                tv_faqanswer.setVisibility(View.VISIBLE);
                                tv_faqquestion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24dp, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                                //tv_faqquestion.setSelection(tv_faqquestion.getText().length());
                            }
                            return false;
                        }
                    }
                    return false;
                }

            });
*/
        }
    }
}
