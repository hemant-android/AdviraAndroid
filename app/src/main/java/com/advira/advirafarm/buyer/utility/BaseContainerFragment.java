package com.advira.advirafarm.buyer.utility;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.advira.advirafarm.buyer.R;


public class BaseContainerFragment extends Fragment {



    public void removeRecentFragment(String fragName) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack(fragName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    public boolean popChildFragment() {
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }

        return isPop;
    }

    public boolean popFragment() {
        boolean isPop = false;
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getFragmentManager().popBackStack();
        } else
            getFragmentManager().popBackStack();
        return isPop;
    }

    public void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void pushFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if (fragment != null) {

            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body2, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void pushFragmentWithOutBackStack(Fragment fragment) {
        if (fragment != null) {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body2, fragment);

            fragmentTransaction.commit();
        }
    }

    public void addFragment(Fragment fragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_body2, fragment)
                .commit();

    }

    public void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction().
                detach(fragment).
                attach(fragment)
                .commit();

    }

    public void getPreviousFragment(Fragment fragment2, Fragment currentFragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .hide(currentFragment)//hide
                .show(fragment2)
                .commit();

    }

}
