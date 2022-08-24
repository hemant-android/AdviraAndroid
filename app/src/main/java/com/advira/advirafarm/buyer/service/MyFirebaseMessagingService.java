package com.advira.advirafarm.buyer.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMMessage;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMMessageCount;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMToken;
import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_UserActive;


//class extending FirebaseMessagingService
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.d(TAG, "Token: " + token);
        getToken();
    }


    public void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    Log.e(TAG, "Failed to get the token.");
                    return;
                }

                //get the token from task
                String fcmtoken = task.getResult();
                sendRegistrationToServer(fcmtoken);
                Log.d(TAG, "Token : " + fcmtoken);
                //tvToken.setText(token);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to get the token : " + e.getLocalizedMessage());
            }
        });
    }


    private void sendRegistrationToServer(String token) {

        SharedPrefUtil.setFCMToken(getApplicationContext(), SHARED_PREF_FCMToken, token);


        String tt = SharedPrefUtil.getFCMToken(getApplicationContext(),SHARED_PREF_FCMToken,"");

        Log.e(TAG, "sendRegistrationToServer: FCM-1    "+tt );



        // Add custom implementation, as needed.
    }

    /*public static String getToken(Context context) {
        return PrefUtils.getInstance(context).getStringValue(PrefKeys.FCM_TOKEN, "");
    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

       // Log.d("MyNotification", remoteMessage.getNotification().toString());

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily

        if (remoteMessage.getData().size() > 0) {
            //handle the data message here
        }

        //getting the title and the body
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        // String imageUrl = remoteMessage.getData().get("image-url");


        //then here we can use the title and body to build a notification

        MyNotificationManager.getInstance(getApplicationContext()).displayNotification(title, body);
        //  Log.d("MyNotification", title.toString());
        // Log.d("MyNotification", body.toString());

        SharedPrefUtil.setFCMMessage(getApplicationContext(), SHARED_PREF_FCMMessage, "title :" + title + ", body :" + body);


        String notificationcount = SharedPrefUtil.getFCMMessageCount(getApplicationContext(), SHARED_PREF_FCMMessageCount, "0");
        int notification = Integer.valueOf(notificationcount) - 1;
        notification = notification + 1;
        notificationcount = String.valueOf(notification);
        SharedPrefUtil.setFCMMessageCount(getApplicationContext(), SHARED_PREF_FCMMessageCount, notificationcount);


        Intent intent = new Intent("filter_string");
        intent.putExtra("firebasemessage", "title :" + title + ", body :" + body);
        // put your all data using put extra
        intent.putExtra("firebasemessagecount", notificationcount);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        try {

            if(title.equalsIgnoreCase("Profile Activated"))
            {
                SharedPrefUtil.setUserActive(getApplicationContext(), SHARED_PREF_UserActive, "active");
            }


            else if(title.equalsIgnoreCase("Profile Deactivated"))
            {
                SharedPrefUtil.setUserActive(getApplicationContext(), SHARED_PREF_UserActive, "inactive");
            }


        } catch (Exception ex) {

        }



        /*sendNotification(remoteMessage.getData().get("body"),
                remoteMessage.getData().get("mode_id"), remoteMessage.getData().get("click_action"));
     */
        //ChatListActivity.tv_toastnotification.setText("title :"+title+", body :"+body);

        //Singleton.getInstance().showLongToast(getApplicationContext(), "title :"+title+", body :"+body);
    }



    private void sendNotification(String messageBody, String id, String clickAction) {
        Intent intent = new Intent(clickAction);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        intent.putExtra("id", id);
        intent.putExtra("body", messageBody);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "111")
                .setSmallIcon(R.drawable.advirahealnew)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLights(Color.GREEN, 3000, 3000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("111", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(false);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId("111");
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0, notificationBuilder.build());
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
