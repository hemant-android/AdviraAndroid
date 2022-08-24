package com.advira.advirafarm.buyer.ui.address.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.IConsts;
import com.advira.advirafarm.buyer.retrofurlconnection.RetrofitUrlConnection;
import com.advira.advirafarm.buyer.ui.address.AddNewAddressActivity;
import com.advira.advirafarm.buyer.ui.address.UpdateAddressActivity;
import com.advira.advirafarm.buyer.ui.address.api.AddressDate;
import com.advira.advirafarm.buyer.ui.address.api.AddressListData;
import com.advira.advirafarm.buyer.ui.address.api.DefaultAddressRequest;
import com.advira.advirafarm.buyer.ui.address.api.DeleteAddressResponse;
import com.advira.advirafarm.buyer.ui.navigation.MainActivityNav;
import com.advira.advirafarm.buyer.ui.product.HomeFragment;
import com.advira.advirafarm.buyer.ui.product.HomeFragmentB2B;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.advira.advirafarm.buyer.utility.Singleton;
import com.advira.advirafarm.buyer.utility.Utilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> implements IConsts {


    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private Context mContext;
    public static String addressusername="";
    private List<AddressDate> addressListData;
    public AddressListAdapter(Context mContext, List<AddressDate> addressListData) {
        this.mContext = mContext;
        this.addressListData = addressListData;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_address, null);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        AddressDate addressListData1 = addressListData.get(position);

        String showselect = SharedPrefUtil.getAddressType(mContext, SHARED_PREF_ADDRESSTYPE, "");

        holder.tv_addid.setText(addressListData1.getId());
        addressusername= addressListData1.getUserName();
        if(addressusername.equalsIgnoreCase("")) {
            holder.tv_addname.setText(SharedPrefUtil.getUserName(mContext, SHARED_PREF_UserName, ""));
        }
        else{
        holder.tv_addname.setText(addressusername);
        }

        holder.tv_addressline1.setText(addressListData1.getAddress());
        holder.tv_addressline2.setText(addressListData1.getAddress2());
        holder.tv_state.setText(addressListData1.getStateName());
        holder.tv_city.setText(addressListData1.getCityName() + ", ");
        holder.tv_stateid.setText(addressListData1.getState());
        holder.tv_cityid.setText(addressListData1.getCity());
        holder.tv_pin.setText(addressListData1.getPincode());
        holder.tv_adddefault.setText(addressListData1.getIsDafault());
        holder.tv_addname.setText(addressListData1.getUserName());
        holder.tv_addmobile.setText(addressListData1.getMobileNo());

        holder.tv_fulladdress.setText(addressListData1.getCityName() + ", " + addressListData1.getStateName() + " " + addressListData1.getPincode());

        holder.rl_edit.setVisibility(View.VISIBLE);
        holder.rl_delete.setVisibility(View.VISIBLE);
        holder.rb_add.setVisibility(View.GONE);

        if (addressListData1.getIsDafault().equalsIgnoreCase("0")) {
            holder.rb_add.setChecked(false);
        } else {

            String headeraddress = addressListData1.getCityName()+" "+addressListData1.getPincode();
            SharedPrefUtil.setHeaderAddress(mContext, SHARED_PREF_HeaderAddress, headeraddress);
            holder.rb_add.setChecked(true);
            holder.rb_add.setTag(new Integer(position));

            try {
                MainActivityNav.tv_deliverto.setText(headeraddress);
            } catch (Exception ex) {

            }

            try{
                HomeFragment.tv_deliverto.setText(headeraddress);
            }
            catch (Exception ex)
            {

            }

            try{
                HomeFragmentB2B.tv_deliverto.setText(headeraddress);
            }
            catch (Exception ex)
            {

            }

        }


        holder.rb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = addressListData.indexOf(addressListData1);

                for (int i = 0; i < addressListData.size(); i++) {
                    if (i == position) {
                        addressListData.get(i).setIsDafault("1");
                        String fulladdress = addressListData1.getAddress() + " " + addressListData1.getAddress2() + " " + addressListData1.getCityName() + ", " + addressListData1.getStateName() + " " + addressListData1.getPincode();

                        SharedPrefUtil.setDefaultAddressId(mContext, SHARED_PREF_DefaultAddressID, addressListData.get(i).getId());
                        SharedPrefUtil.setDefaultAddress(mContext, SHARED_PREF_DefaultAddress, fulladdress);


                    } else {
                        addressListData.get(i).setIsDafault("0");
                    }
                }
                notifyDataSetChanged();
            }
        });


        holder.rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Delete Address");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want to delete this address?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                DeletAddress(holder.tv_addid.getText().toString(), addressListData1);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();

                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


            }
        });


    }


    @Override
    public int getItemCount() {
        return addressListData.size();

    }

    private void DeletAddress(String addid, AddressDate addressListData1) {

        Utilities.showLoading(mContext);

        DefaultAddressRequest defaultAddressRequest = new DefaultAddressRequest();
        defaultAddressRequest.setAddressId(addid);

        String token = SharedPrefUtil.getUniversalSharedPrefToken(mContext, SHARED_PREF_TOKEN, "");
        Call<DeleteAddressResponse> call = RetrofitUrlConnection.loadJSON(token).deleteaddress(defaultAddressRequest);

        call.enqueue(new Callback<DeleteAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteAddressResponse> call, Response<DeleteAddressResponse> response) {

                if (response.body().getStatus().toString().equalsIgnoreCase("100")) {

                    int position = addressListData.indexOf(addressListData1);

                    addressListData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, addressListData.size());
                    notifyDataSetChanged();

                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast


                } else {

                    Utilities.dismissDialog();
                    //Singleton.getInstance().showLongToast(mContext, response.body().getMessage());//remove toast
                }

                Utilities.dismissDialog();
            }

            @Override
            public void onFailure(Call<DeleteAddressResponse> call, Throwable t) {

                Utilities.dismissDialog();
            }
        });

    }

    class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView tv_addid,
                tv_cityid,
                tv_stateid,
                tv_addname,
                tv_addmobile,
                tv_addtype,
                tv_addressline1,
                tv_addressline2,
                tv_city,
                tv_state,
                tv_pin,
                tv_adddefault,
                tv_fulladdress,
                tv_def,
                tv_druglic,
                tv_druglicexpiry;
        RelativeLayout rl_edit, rl_delete;
        CardView cv_settings;
        RadioButton rb_add;

        public AddressViewHolder(View itemView) {
            super(itemView);


            tv_addname = itemView.findViewById(R.id.tv_addname);
            tv_addmobile = itemView.findViewById(R.id.tv_addmobile);
            tv_addtype = itemView.findViewById(R.id.tv_addtype);
            tv_addressline1 = itemView.findViewById(R.id.tv_addressline1);
            tv_addressline2 = itemView.findViewById(R.id.tv_addressline2);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_stateid = itemView.findViewById(R.id.tv_stateid);
            tv_cityid = itemView.findViewById(R.id.tv_cityid);
            tv_pin = itemView.findViewById(R.id.tv_pin);
            tv_addid = itemView.findViewById(R.id.tv_addid);
            tv_adddefault = itemView.findViewById(R.id.tv_adddefault);
            tv_fulladdress = itemView.findViewById(R.id.tv_fulladdress);
            rl_edit = itemView.findViewById(R.id.rl_edit);
            rl_delete = itemView.findViewById(R.id.rl_delete);
            cv_settings = itemView.findViewById(R.id.cv_settings);
            rb_add = itemView.findViewById(R.id.rb_add);
            tv_druglic = itemView.findViewById(R.id.tv_druglic);
            tv_druglicexpiry = itemView.findViewById(R.id.tv_druglicexpiry);

            rl_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Edit Address");
                    builder.setMessage(" Do you wish to continue?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent();
                                    i.setClass(mContext, UpdateAddressActivity.class);
                                    i.putExtra("address_id", tv_addid.getText().toString());
                                    i.putExtra("address", tv_addressline1.getText().toString());
                                    i.putExtra("address2", tv_addressline2.getText().toString());
                                    i.putExtra("state", tv_stateid.getText().toString());
                                    i.putExtra("city", tv_cityid.getText().toString());
                                    i.putExtra("pincode", tv_pin.getText().toString());
                                    i.putExtra("is_dafault", tv_adddefault.getText().toString());
                                    i.putExtra("address_type", tv_addtype.getText().toString());
                                    i.putExtra("drug_licence", tv_druglic.getText().toString());
                                    i.putExtra("user_name",tv_addname.getText().toString());
                                    i.putExtra("mobile_no",tv_addmobile.getText().toString());
                                    i.putExtra("drug_licence_exp_date", tv_druglicexpiry.getText().toString());
                                    mContext.startActivity(i);


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();

                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorThemeDark));


                }
            });

        }
    }


}
