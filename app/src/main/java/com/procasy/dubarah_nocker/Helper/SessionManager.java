package com.procasy.dubarah_nocker.Helper;

/**
 * Created by DELL on 29/07/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Nocker";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_UDID = "udid";
    private static final String SOCIAL_TYPE = "social_type";
    private static final String IMG_URL = "img_url";
    private static final String KEY_FName = "firstName";
    private static final String KEY_LName = "lastName";

    private static final String KEY_PP = "profilePic";
    private static final String KEY_AVG_CHARGE = "avg_charge";

    private static final String KEY_IS_NOCKER = "is_nocker";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setEmail(String email) {

        editor.putString(KEY_EMAIL, email);
        // commit changes
        editor.commit();

        Log.d(TAG, "User email session modified!");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setPassword(String password) {

        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();

        Log.d(TAG, "User password session modified!");
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, "");
    }

    public void setImgUrl(String imgUrl) {

        editor.putString(IMG_URL, imgUrl);

        // commit changes
        editor.commit();

        Log.d(TAG, "User password session modified!");
    }

    public String getImgUrl() {
        return pref.getString(IMG_URL, "");
    }


    public void setFName(String fName) {

        editor.putString(KEY_FName, fName);

        // commit changes
        editor.commit();

        Log.d(TAG, "User fName session modified!");
    }

    public String getFName() {
        return pref.getString(KEY_FName, "");
    }

    public void setLName(String lName) {

        editor.putString(KEY_LName, lName);

        // commit changes
        editor.commit();

        Log.d(TAG, "User lName session modified!");
    }

    public String getLName() {
        return pref.getString(KEY_LName, "");
    }

    public void setPP(String PP) {

        editor.putString(KEY_PP, PP);

        // commit changes
        editor.commit();

        Log.d(TAG, "User PP session modified! "+PP);
    }

    public String getPP() {
        return pref.getString(KEY_PP, "");
    }

    public void setUDID(String UDID) {

        editor.putString(KEY_UDID, UDID);

        // commit changes
        editor.commit();

        Log.d(TAG, "User UDID session modified!");
    }

    public String getUDID() {
        return pref.getString(KEY_UDID, "");
    }


    public void setSocialType(String socialType) {

        editor.putString(SOCIAL_TYPE, socialType);

        // commit changes
        editor.commit();

        Log.d(TAG, "User Social Type session modified!");
    }

    public String getSocialType() {
        return pref.getString(SOCIAL_TYPE, "");
    }


    public void setAVG(int avg) {
        editor.putInt(KEY_AVG_CHARGE, avg);
        // commit changes
        editor.commit();
        Log.d(TAG, "User AVG session modified!");
    }

    public int getAVG() {
        return pref.getInt(KEY_AVG_CHARGE, 0);
    }

    public void setKeyIsNocker(int isNocker) {
        editor.putInt(KEY_IS_NOCKER, isNocker);
        // commit changes
        editor.commit();
        Log.d(TAG, "User IS_Nocker session modified!");
    }

    public int getKeyNocker() {
        return pref.getInt(KEY_IS_NOCKER, 0);
    }


}
