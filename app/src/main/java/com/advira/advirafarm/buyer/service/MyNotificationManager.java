package com.advira.advirafarm.buyer.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.Constants;
import com.advira.advirafarm.buyer.ui.splash.Splash;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMMessageCount;

public class MyNotificationManager {

    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentTitle(title)
                        .setContentText(body);


        /*
        *  Clicking on the notification will take us to this intent
        *  Right now we are using the MainActivity as this is the only activity we have in our application
        *  But for your project you can customize it as you want
        * */

        Intent resultIntent = new Intent(mCtx, Splash.class);

        /*
        *  Now we will create a pending intent
        *  The method getActivity is taking 4 parameters
        *  All paramters are describing themselves
        *  0 is the request code (the second parameter)
        *  We can detect this code in the activity that will open by this we can get
        *  Which notification opened the activity
        * */

        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        /*
        *  Setting the pending intent to notification builder
        * */

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

        /*
        * The first parameter is the notification id
        * better don't give a literal here (right now we are giving a int literal)
        * because using this id we can modify it later
        * */

        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());

            String notificationcount = SharedPrefUtil.getFCMMessageCount(mCtx, SHARED_PREF_FCMMessageCount, "0");
            int notification = Integer.valueOf(notificationcount);
            notification = notification + 1;
            notificationcount = String.valueOf(notification);
            SharedPrefUtil.setFCMMessageCount(mCtx, SHARED_PREF_FCMMessageCount, notificationcount);


        }
    }

}
