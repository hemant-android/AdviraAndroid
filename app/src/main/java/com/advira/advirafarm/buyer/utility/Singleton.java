package com.advira.advirafarm.buyer.utility;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;


public class Singleton {
    private static Singleton instance = null;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }


    public void showShortToast(Context context, String msg) {


        DynamicToast.make(context, msg, Color.parseColor("#000000"), Color.parseColor("#67A016"),
                Toast.LENGTH_LONG).show();


    }

    public void showLongToast(Context context, String msg) {


        DynamicToast.make(context, msg, Color.parseColor("#000000"), Color.parseColor("#67A016"),
                Toast.LENGTH_LONG).show();



    }

    public void showErrorLongToast(Context context, String msg) {


        DynamicToast.make(context, msg, Color.parseColor("#000000"), Color.parseColor("#67A016"),
                Toast.LENGTH_LONG).show();


    }


}
