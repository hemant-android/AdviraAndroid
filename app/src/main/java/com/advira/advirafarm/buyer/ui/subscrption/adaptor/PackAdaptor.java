package com.advira.advirafarm.buyer.ui.subscrption.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.masterapi.StateList;
import com.advira.advirafarm.buyer.ui.subscrption.api.SkuUmit;

import java.util.List;

public class PackAdaptor extends ArrayAdapter<SkuUmit> {

        LayoutInflater flater;


        public PackAdaptor(AppCompatActivity context, int resouceId, int textviewId, List<SkuUmit> list){
            super(context,resouceId,textviewId,list);
            flater = context.getLayoutInflater();
        }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SkuUmit item = getItem(position);
            View rowview = flater.inflate(R.layout.layout_profile,null,true);
            TextView profile_name = (TextView) rowview.findViewById(R.id.profile_name);
            profile_name.setText(item.getProductUnits()+item.getProductUnitType());
            return rowview;
        }
    }

