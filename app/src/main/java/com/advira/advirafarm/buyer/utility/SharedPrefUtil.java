package com.advira.advirafarm.buyer.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.advira.advirafarm.buyer.constants.IConsts;

public class SharedPrefUtil implements IConsts {
    private static SharedPreferences sharedPreferences;

    /*Set String value in shared preferences */
    public static void setUserProfileImageURL(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_ProfilePic, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences */
    public static String getUserProfileImageURL(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_ProfilePic, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }


    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setUniversalSharedPrefSessionid(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_SESSION, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getUniversalSharedPrefSessionid(Context context, String key, String defaultVal) {
        String prefSessionid = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_SESSION, 0);
        prefSessionid = sharedPreferences.getString(key, defaultVal);
        return prefSessionid;
    }



    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setUniversalSharedPrefToken(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_TOKEN, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getUniversalSharedPrefToken(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_TOKEN, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }


    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setCartCount(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_CARTCOUNT, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getCartCount(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_CARTCOUNT, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }

    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setCartCountB2B(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_CARTCOUNTB2B, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getCartCountB2B(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_CARTCOUNTB2B, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }

    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setCartCountB2C(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_CARTCOUNTB2C, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getCartCountB2C(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_CARTCOUNTB2C, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }


    public static void setAddressType(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ADDRESSTYPE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getAddressType(Context context, String key, String defaultVal) {
        String prefUserID = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ADDRESSTYPE, 0);
        prefUserID = sharedPreferences.getString(key, defaultVal);
        return prefUserID;
    }



    public static void setUserActive(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserActive, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserActive(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserActive, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }


    public static void setOrderCount(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_OrderCount, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getOrderCount(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_OrderCount, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }




    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setDefaultAddressId(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_DefaultAddressID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getDefaultAddressId(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_DefaultAddressID, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }


    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setDefaultAddress(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_DefaultAddress, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getDefaultAddress(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_DefaultAddress, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }


    /*Set String value in shared preferences for ACCESS_TOKEN */
    public static void setHeaderAddress(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_HeaderAddress, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences for ACCESS_TOKEN*/
    public static String getHeaderAddress(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_HeaderAddress, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }



    public static void removeValue(Context context, String prefsName, String key) {
        sharedPreferences = context.getSharedPreferences(prefsName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }



    public static void setFCMToken(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FCMToken, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getFCMToken(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FCMToken, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }


    public static void setRegno(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_Regno, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getRegno(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_Regno, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }


    public static void setLoginID(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LoginID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getLoginID(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LoginID, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setLoginPass(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LoginPassword, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getLoginPass(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LoginPassword, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }



    public static void setUserEmail(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserEmailID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserEmail(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserEmailID, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }


    public static void setUserMobile(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserMobile, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserMobile(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserMobile, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setUserName(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserName(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserName, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setMembershipStartDate(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_StartDate, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getMembershipStartDate(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_StartDate, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }
    public static void setMembershipEndDate(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_EndDate, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getMembershipEndDate(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_EndDate, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setMembership(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getMembership(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setUserRegno(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserRegno, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserRegno(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserRegno, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setLanguage(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_Language, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getLanguage(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_Language, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setUserFirstname(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserFirstname, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserFirstname(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserFirstname, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setUserLastname(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserLastname, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserLastname(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_UserLastname, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setUserProfilePic(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ProfilePic, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserProfilePic(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ProfilePic, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }


    public static void setProfilePercent(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_PROFILEPERCENTAGE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getProfilePercent(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_PROFILEPERCENTAGE, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }


    public static void setUserSelectedCountry(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SELECTED_COUNTRY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserSelectedCountry(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SELECTED_COUNTRY, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setUserSelectedState(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SELECTED_STATE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserSelectedState(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SELECTED_STATE, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }



    /*Set String value in shared preferences */
    public static void setLatLon(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_ALL_LAT_LON, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences */
    public static String getLatlon(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_ALL_LAT_LON, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }


    /*Set String value in shared preferences */
    public static void setLatLonArray(Context context, String key, String value) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF_ARRAY_LAT_LON, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /*get String value in shared preferences */
    public static String getLatlonArray(Context context, String key, String defaultVal) {
        String prefToken = defaultVal;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_ARRAY_LAT_LON, 0);
        prefToken = sharedPreferences.getString(key, defaultVal);
        return prefToken;
    }

    public static void setFCMMessage(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FCMMessage, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getFCMMessage(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FCMMessage, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setFCMMessageCount(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FCMMessageCount, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getFCMMessageCount(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FCMMessageCount, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setCartItemStock(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_CartItemStock, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getCartItemStock(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_CartItemStock, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setPaymentCheck(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_PaymentCheck, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPaymentCheck(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_PaymentCheck, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setAvailableCredit(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_AvailableCredit, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getAvailableCredit(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_AvailableCredit, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setProfileMode(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ProfileMode, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getProfileMode(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ProfileMode, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setCartId(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_CartID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getCartId(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_CartID, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setpreLoginLocation(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_PreLoginPin, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getpreLoginLocation(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_PreLoginPin, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setmembershipStartTime(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_StartTime, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getmembershipStartTime(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_StartTime, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setmembershipCancelTime(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_CancelTime, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getmembershipCancelTime(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShip_CancelTime, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setMembershipID(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShipID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getMembershipID(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MemberShipID, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

    public static void setSubscriptionID(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SubscriptionID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSubscriptionID(Context context, String key, String defaultVal) {
        String prefVal = defaultVal;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SubscriptionID, 0);
        prefVal = sharedPreferences.getString(key, defaultVal);
        return prefVal;
    }

}