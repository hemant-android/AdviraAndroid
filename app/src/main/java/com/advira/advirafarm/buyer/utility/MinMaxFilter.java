package com.advira.advirafarm.buyer.utility;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

public class MinMaxFilter implements InputFilter {

    private int mIntMin, mIntMax;

    public MinMaxFilter(int minValue, int maxValue) {
        this.mIntMin = minValue;
        this.mIntMax = maxValue;
    }

    public MinMaxFilter(String minValue, String maxValue) {
        //if (!TextUtils.isEmpty(minValue) && TextUtils.isDigitsOnly(maxValue)) {
        try{
            this.mIntMin = Integer.parseInt(minValue);
            this.mIntMax = Integer.parseInt(maxValue);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            this.mIntMin = 0;
            this.mIntMax=0;
        }
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(mIntMin, mIntMax, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}