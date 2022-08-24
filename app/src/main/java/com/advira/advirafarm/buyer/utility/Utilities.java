package com.advira.advirafarm.buyer.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Utilities {

    @NonNull
    public static ProgressDialog dialog;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public static void showNetworkError(Context context) {
       /* Toast.makeText(context, "No internet access..", Toast.LENGTH_LONG)
                .show();*/


        Snackbar snack = Snackbar.make(
                (((Activity) context).findViewById(android.R.id.content)),
                "No internet access..", Snackbar.LENGTH_SHORT);
        snack.setDuration(Snackbar.LENGTH_INDEFINITE);//change Duration as you need
        snack.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snack.dismiss();
            }
        });
        View view = snack.getView();
        TextView tv = (TextView) view
                .findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.RED);//change textColor
        tv.setTextSize(16);

        TextView tvAction = (TextView) view
                .findViewById(R.id.snackbar_action);
        tvAction.setTextSize(16);
        tvAction.setTextColor(Color.GREEN);

        snack.show();

    }

    public static void showLoading(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        if (!dialog.isShowing())
            dialog.show();

    }

    public static void showLoadingMsg(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait... ");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        if (!dialog.isShowing())
            dialog.show();

    }

    public static void dismissDialog() {
        try {
            if (dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean currentDate1(String selectedDate) {
        boolean b = false;
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        String currentDate = sdf.format(c.getTime());
        try {
            Date current = sdf.parse(currentDate);
            Date selected = sdf.parse(selectedDate);

            int msDiff = (int) (selected.getTime() - current.getTime());

           // Log.d("Diff", String.valueOf(msDiff));
            if (msDiff > 13) {
                b = true;
            }

        } catch (Exception e) {

        }

        return b;
    }

    public static String currentDateInAge(String selectedDate) {

        int msDiff = 0;
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        String currentDate = sdf.format(c.getTime());
        try {
            Date current = sdf.parse(currentDate);
            Date selected = sdf.parse(selectedDate);

            Calendar a = getCalendar(selected);
            Calendar b = getCalendar(current);
            msDiff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
            if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
                msDiff--;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(msDiff);
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static SpannableStringBuilder spannable(String heading, String text, Context mContext) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString redSpannable = new SpannableString(heading);
        redSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, heading.length(), 0);
        redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, heading.length(), 0);
        builder.append(redSpannable);

        SpannableString blueSpannable = new SpannableString(text);
        blueSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)), 0, text.length(), 0);
        return builder.append(blueSpannable);
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showAlertDialogMenu(final Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                arg0.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public static JSONArray cur2Json(Cursor cursor)
    {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++)
            {
                try {
                    if (cursor.getString(i)!=null) {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } else {
                        rowObject.put(cursor.getColumnName(i), "");
                    }
                } catch (Exception e) {
                    Log.d("", e.getMessage());
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;
    }


    public static void fullScreencall(Activity activity) {
        if(Build.VERSION.SDK_INT < 19){
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    public static class DigitsInputFilter implements InputFilter {

        private final String DOT = ".";

        private int mMaxIntegerDigitsLength;
        private int mMaxDigitsAfterLength;
        private double mMax;


        public DigitsInputFilter(int maxDigitsBeforeDot, int maxDigitsAfterDot, double maxValue) {
            mMaxIntegerDigitsLength = maxDigitsBeforeDot;
            mMaxDigitsAfterLength = maxDigitsAfterDot;
            mMax = maxValue;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String allText = getAllText(source, dest, dstart);
            String onlyDigitsText = getOnlyDigitsPart(allText);

            if (allText.isEmpty()) {
                return null;
            } else {
                double enteredValue;
                try {
                    enteredValue = Double.parseDouble(onlyDigitsText);
                } catch (NumberFormatException e) {
                    return "";
                }
                return checkMaxValueRule(enteredValue, onlyDigitsText);
            }
        }


        private CharSequence checkMaxValueRule(double enteredValue, String onlyDigitsText) {
            if (enteredValue > mMax) {
                return "";
            } else {
                return handleInputRules(onlyDigitsText);
            }
        }

        private CharSequence handleInputRules(String onlyDigitsText) {
            if (isDecimalDigit(onlyDigitsText)) {
                return checkRuleForDecimalDigits(onlyDigitsText);
            } else {
                return checkRuleForIntegerDigits(onlyDigitsText.length());
            }
        }

        private boolean isDecimalDigit(String onlyDigitsText) {
            return onlyDigitsText.contains(DOT);
        }

        private CharSequence checkRuleForDecimalDigits(String onlyDigitsPart) {
            String afterDotPart = onlyDigitsPart.substring(onlyDigitsPart.indexOf(DOT), onlyDigitsPart.length() - 1);
            if (afterDotPart.length() > mMaxDigitsAfterLength) {
                return "";
            }
            return null;
        }

        private CharSequence checkRuleForIntegerDigits(int allTextLength) {
            if (allTextLength > mMaxIntegerDigitsLength) {
                return "";
            }
            return null;
        }

        private String getOnlyDigitsPart(String text) {
            return text.replaceAll("[^0-9?!\\.]", "");
        }

        private String getAllText(CharSequence source, Spanned dest, int dstart) {
            String allText = "";
            if (!dest.toString().isEmpty()) {
                if (source.toString().isEmpty()) {
                    allText = deleteCharAtIndex(dest, dstart);
                } else {
                    allText = new StringBuilder(dest).insert(dstart, source).toString();
                }
            }
            return allText;
        }

        private String deleteCharAtIndex(Spanned dest, int dstart) {
            StringBuilder builder = new StringBuilder(dest);
            builder.deleteCharAt(dstart);
            return builder.toString();
        }
    }

}
