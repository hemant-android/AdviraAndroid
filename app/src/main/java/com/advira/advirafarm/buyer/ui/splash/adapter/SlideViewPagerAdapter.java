package com.advira.advirafarm.buyer.ui.splash.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.ui.guest.MainActivityGuestNav;
import com.advira.advirafarm.buyer.ui.splash.Fragment.Slidefour;
import com.advira.advirafarm.buyer.ui.splash.Fragment.Slideone;
import com.advira.advirafarm.buyer.ui.splash.Fragment.Slidethree;
import com.advira.advirafarm.buyer.ui.splash.Fragment.Slidetwo;
import com.advira.advirafarm.buyer.ui.splash.SlideActivity;

public class SlideViewPagerAdapter extends FragmentStatePagerAdapter {

        public SlideViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Slidefour tab1=new Slidefour();
                    return tab1;
                case 1:
                    Slideone tab2 = new Slideone();
                    return tab2;

                case 2:
                    Slidetwo tab3 = new Slidetwo();
                    return tab3;

                case 3:
                    Slidethree tab4 = new Slidethree();
                    return tab4;


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
