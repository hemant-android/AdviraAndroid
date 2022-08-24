package com.advira.advirafarm.buyer.ui.registration.adapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.masterapi.DocumentList;

import java.util.List;

public class DocumentAdapter extends ArrayAdapter<DocumentList> {

    LayoutInflater flater;

    public DocumentAdapter(AppCompatActivity context, int resouceId, int textviewId, List<DocumentList> list){
        super(context,resouceId,textviewId,list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DocumentList item = getItem(position);
        View rowview = flater.inflate(R.layout.layout_profile,null,true);
        TextView profile_name = (TextView) rowview.findViewById(R.id.profile_name);
        profile_name.setText(item.getName().toString());
        return rowview;
    }
}
