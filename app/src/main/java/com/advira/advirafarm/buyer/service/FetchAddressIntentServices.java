package com.advira.advirafarm.buyer.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.advira.advirafarm.buyer.R;
import com.advira.advirafarm.buyer.constants.Constants;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentServices extends IntentService {

    public static final String TAG = FetchAddressIntentServices.class.getSimpleName();
    protected ResultReceiver receiver;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FetchAddressIntentServices() {
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        receiver = intent.getParcelableExtra(Constants.RECEIVER);
        double latitude = intent.getDoubleExtra(Constants.LOCATION_LAT_EXTRA, -1);
        double longitude = intent.getDoubleExtra(Constants.LOCATION_LNG_EXTRA, -1);
        String language = intent.getStringExtra(Constants.LANGUAGE);


        String errorMessage = "";
        List<Address> addresses = null;

        //Locale locale = new Locale(language);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
            );
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        }
        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            //deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);

        } else {
            //StringBuilder result = new StringBuilder();
            Address address = addresses.get(0);

            String str_postcode = address.getPostalCode();
            String str_Country = address.getCountryName();
            String str_state = address.getAdminArea();
            String str_district = address.getSubAdminArea();
            String str_locality = address.getPremises()+","+address.getSubLocality();//getsublocality naubasta
            String str_address = address.getAddressLine(0);



            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            /*for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                if (i == address.getMaxAddressLineIndex()) {
                    result.append(address.getAddressLine(i));
                } else {
                    result.append(address.getAddressLine(i) + ",");
                }
            }*/
            Log.i(TAG, getString(R.string.address_found));
            //Log.i(TAG, "address : " + result);

            deliverResultToReceiver(Constants.SUCCESS_RESULT, str_address, str_locality, str_district, str_state, str_Country, str_postcode,address.getAddressLine(0));

           /* deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    result.toString());*/
        }

    }

    private void deliverResultToReceiver(int resultcode, String message,String address, String locality, String district, String state, String country, String postcode) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ADDRESS, address);
        bundle.putString(Constants.LOCAITY, locality);
        bundle.putString(Constants.DISTRICT, district);
        bundle.putString(Constants.STATE, state);
        bundle.putString(Constants.COUNTRY, country);
        bundle.putString(Constants.POST_CODE, postcode);
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        receiver.send(resultcode, bundle);
    }

    /*private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        receiver.send(resultCode, bundle);
    }*/
}

