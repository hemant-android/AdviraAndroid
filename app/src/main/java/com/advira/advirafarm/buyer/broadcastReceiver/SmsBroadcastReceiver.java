package com.advira.advirafarm.buyer.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public SmsBroadcastReceiverListener smsBroadcastReceiverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
           /* Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch(status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    Intent message =extras.getParcelable(SmsRetriever.EXTRA_SMS_MESSAGE);
                    //smsBroadcastReceiverListener.onSuccess(message);
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server.
                    break;
                case CommonStatusCodes.TIMEOUT:
                    //smsBroadcastReceiverListener.onFailure();
                    // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    break;
            }*/
        }

    }

    public interface SmsBroadcastReceiverListener{

        void onSuccess(Intent intent);

        void onFailure();

    }


}
