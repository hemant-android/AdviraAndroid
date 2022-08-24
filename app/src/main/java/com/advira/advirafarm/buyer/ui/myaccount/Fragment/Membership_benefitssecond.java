package com.advira.advirafarm.buyer.ui.myaccount.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advira.advirafarm.buyer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Membership_benefitssecond#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Membership_benefitssecond extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_membership_benefitssecond, container, false);
    }
}