package com.advira.advirafarm.buyer.ui.registration.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;

import java.util.List;

public class StateAdapter extends ArrayAdapter<StateList> {

    LayoutInflater flater;

    public StateAdapter(AppCompatActivity context, int resouceId, int textviewId, List<StateList> list){
        super(context,resouceId,textviewId,list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StateList item = getItem(position);
        View rowview = flater.inflate(R.layout.layout_profile,null,true);
        TextView profile_name = (TextView) rowview.findViewById(R.id.profile_name);
        profile_name.setText(item.getName().toString());
        return rowview;
    }
}
