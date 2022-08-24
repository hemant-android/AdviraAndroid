package com.advira.advirafarm.buyer.ui.myaccount.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.advira.advirafarm.buyer.ui.myaccount.Fragment.Membership_benefitsone;
import com.advira.advirafarm.buyer.ui.myaccount.Fragment.Membership_benefitssecond;
import com.advira.advirafarm.buyer.ui.myaccount.Fragment.Membership_benefitthird;

public class SliderAdapter extends FragmentPagerAdapter {

    public SliderAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Membership_benefitsone tab1=new Membership_benefitsone();
                return tab1;
            case 1:
                Membership_benefitssecond tab2=new Membership_benefitssecond();
                return tab2;

            case 2:
                Membership_benefitthird tab3=new Membership_benefitthird();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
