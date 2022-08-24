package com.advira.advirafarm.buyer.ui.registration.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.masterapi.CityList;

import java.util.List;

public class CityAdapter extends ArrayAdapter<CityList> {

    LayoutInflater flater;

    public CityAdapter(AppCompatActivity context, int resouceId, int textviewId, List<CityList> list){
        super(context,resouceId,textviewId,list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CityList item = getItem(position);
        View rowview = flater.inflate(R.layout.layout_profile,null,true);
        TextView profile_name = (TextView) rowview.findViewById(R.id.profile_name);
        profile_name.setText(item.getName().toString());
        return rowview;
    }
}
