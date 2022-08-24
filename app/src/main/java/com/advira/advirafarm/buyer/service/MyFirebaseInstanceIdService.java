package com.advira.advirafarm.buyer.service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.utility.SharedPrefUtil;

import com.google.firebase.messaging.FirebaseMessagingService;

import static com.advira.advirafarm.buyer.constants.IConsts.SHARED_PREF_FCMToken;


//the class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIdService extends /*FirebaseInstanceIdService*/ FirebaseMessagingService {


    private static final String TAG = "MyFirebaseIIDService";
    //private SharedPref sharedPref;



    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        /*FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                *//*String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(MyFirebaseInstanceIdService.this, msg, Toast.LENGTH_SHORT).show();*//*
            }
        });*/
        sendRegistrationToServer(token);
    }




    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]



    /*@Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);



        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }*/
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

        SharedPrefUtil.setFCMToken(getApplicationContext(), SHARED_PREF_FCMToken, token);


        String tt = SharedPrefUtil.getFCMToken(getApplicationContext(),SHARED_PREF_FCMToken,"");

        Log.e(TAG, "sendRegistrationToServer: FCM    "+tt );



        // Add custom implementation, as needed.
    }

}
