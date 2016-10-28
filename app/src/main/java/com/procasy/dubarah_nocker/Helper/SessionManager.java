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

    private static final String KEY_FName = "firstName";
    private static final String KEY_LName = "lastName";

    private static final String KEY_PP = "profilePic";
    private static final String KEY_AVG_CHARGE = "avg_charge";

    private static final String KEY_IS_NOCKER = "is_nocker";
    private static final String KEY_IS_SOCIAL = "is_social";



    private static final String Key_his_name = "his_name";
    private static final String Key_his_skill = "his_skill";
    private static final String Key_his_language = "his_language";
    private static final String Key_his_phonenumber = "his_phonenumber";
    private static final String Key_his_email = "his_email";
    private static final String Key_his_rating = "his_rating";
    private static final String Key_his_rating_text = "his_rating_text";
    private static final String Key_his_subscription = "his_subscription";




    private static final String Key_Join_Recommend = "join_recommend";
    private static final String Key_ur_skill = "ur_skill";
    private static final String Key_ur_language = "ur_language";
    private static final String Key_ur_gender = "ur_gender";
    private static final String KEY_City = "City";
    private static final String KEY_Country = "Country";
    private static final String KEY_Province = "Province";
    private static final String KEY_Birthyear = "Birthyear";
    private static final String KEY_Phonenumber = "Phonenumber";
    private static final String Key_my_subscription = "my_subscription";




    public void RemoveAllData(){
        editor.clear();
    }

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
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setEmail(String email) {

        editor.putString(KEY_EMAIL, email);
        // commit changes
        editor.commit();

        Log.d(TAG, "User email session modified!");
    }
    public String getEmail(){
        return pref.getString(KEY_EMAIL, "");
    }
    public void setPassword(String password) {

        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();

        Log.d(TAG, "User password session modified!");
    }
    public String getPassword(){
        return pref.getString(KEY_PASSWORD, "");
    }



    public void setFName(String fName) {

        editor.putString(KEY_FName, fName);

        // commit changes
        editor.commit();

        Log.d(TAG, "User fName session modified!");
    }
    public String getFName(){
        return pref.getString(KEY_FName, "");
    }

    public void setLName(String lName) {

        editor.putString(KEY_LName, lName);

        // commit changes
        editor.commit();

        Log.d(TAG, "User lName session modified!");
    }
    public String getLName(){
        return pref.getString(KEY_LName, "");
    }

    public void setPP(String PP) {

        editor.putString(KEY_PP, PP);

        // commit changes
        editor.commit();

        Log.d(TAG, "User PP session modified!");
    }
    public String getPP(){
        return pref.getString(KEY_PP, "");
    }

    public void setUDID(String UDID) {

        editor.putString(KEY_UDID, UDID);

        // commit changes
        editor.commit();

        Log.d(TAG, "User UDID session modified!");
    }
    public String getUDID(){
        return pref.getString(KEY_UDID, "");
    }



    public void setAVG(int avg) {
        editor.putInt(KEY_AVG_CHARGE, avg);
        // commit changes
        editor.commit();
        Log.d(TAG, "User AVG session modified!");
    }
    public int getAVG(){
        return pref.getInt(KEY_AVG_CHARGE, 0);
    }

    public void setKeyIsNocker(int isNocker)
    {
        editor.putInt(KEY_IS_NOCKER, isNocker);
        // commit changes
        editor.commit();
        Log.d(TAG, "User IS_Nocker session modified!");
    }
    public int getKeyNocker(){
        return pref.getInt(KEY_IS_NOCKER, 0);
    }


    public void setKeyIsSocial(int isNocker)
    {
        editor.putInt(KEY_IS_SOCIAL, isNocker);
        // commit changes
        editor.commit();
        Log.d(TAG, "User IS_Social session modified!");
    }
    public int getKeyIsSocial(){
        return pref.getInt(KEY_IS_SOCIAL, 0);
    }


    public void setKeyHisName(String hisName)
    {
        editor.putString(Key_his_name, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "His name session modified!");
    }
    public String getKey_his_name(){
        return pref.getString(Key_his_name, "");
    }

    public void setKeyHisRating(String hisName)
    {
        editor.putString(Key_his_rating, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "His rating session modified!");
    }
    public String getKey_his_rating(){
        return pref.getString(Key_his_rating, "");
    }

    public void setKeyHisRatingText(String hisName)
    {
        editor.putString(Key_his_rating_text, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "His rating text session modified!");
    }
    public String getKey_his_rating_text(){
        return pref.getString(Key_his_rating_text, "");
    }


    public void setKeyHisSkill(String hisName)
    {
        editor.putString(Key_his_skill, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "His skills session modified!");
    }
    public String getKey_his_skill(){
        return pref.getString(Key_his_skill, "");
    }


    public void setKeyHisEmail(String hisName)
    {
        editor.putString(Key_his_email, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "His email session modified!");
    }
    public String getKey_his_email(){
        return pref.getString(Key_his_email, "");
    }

    public void setKeyHisLanguage(String hisLanguage)
    {
        editor.putString(Key_his_language, hisLanguage);
        // commit changes
        editor.commit();
        Log.d(TAG, "His language session modified!");
    }
    public String getKey_his_language(){
        return pref.getString(Key_his_language, "");
    }
    public void setKeyHisPhone(String hisLanguage)
    {
        editor.putString(Key_his_phonenumber, hisLanguage);
        // commit changes
        editor.commit();
        Log.d(TAG, "His phonenumber session modified!");
    }
    public String getKey_his_phone(){
        return pref.getString(Key_his_phonenumber, "");
    }



    public void setJoinRecommend(int hisName)
    {
        editor.putInt(Key_Join_Recommend, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "His Join Recommend session modified!");
    }
    public int getJoinRecommend(){
        return pref.getInt(Key_Join_Recommend, 0);
    }



    public void setKeyUrSkill(String hisName)
    {
        editor.putString(Key_ur_skill, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur skills session modified!");
    }
    public String getKey_ur_skill(){
        return pref.getString(Key_ur_skill, "");
    }


    public void setCity(String hisName)
    {
        editor.putString(KEY_City, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur city session modified!");
    }
    public String getKey_City(){
        return pref.getString(KEY_City, "");
    }

    public void setCountry(String hisName)
    {
        editor.putString(KEY_Country, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur country session modified!");
    }
    public String getKey_Country(){
        return pref.getString(KEY_Country, "");
    }

    public void setKEY_Province(String hisName)
    {
        editor.putString(KEY_Province, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur province session modified!");
    }
    public String getKEY_Province(){
        return pref.getString(KEY_Province, "");
    }

    public void setKEY_Birthyear(String hisName)
    {
        editor.putString(KEY_Birthyear, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur birthyear session modified!");
    }
    public String getKEY_Birthyear(){
        return pref.getString(KEY_Birthyear, "");
    }

    public void setKEY_Phonenumber(String hisName)
    {
        editor.putString(KEY_Phonenumber, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur phonenumber session modified!");
    }
    public String getKEY_Phonenumber(){
        return pref.getString(KEY_Phonenumber, "");
    }




    public void setKEY_language(String hisName)
    {
        editor.putString(Key_ur_language, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur language session modified!");
    }
    public String getKEY_language(){
        return pref.getString(Key_ur_language, "");
    }



    public void setKEY_gender(String hisName)
    {
        editor.putString(Key_ur_gender, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur gender session modified!");
    }
    public String getKEY_gender(){
        return pref.getString(Key_ur_gender, "");
    }



    public void setKEY_my_subscription(String hisName)
    {
        editor.putString(Key_my_subscription, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "my subscription session modified!");
    }
    public String getKEY_my_subscription(){
        return pref.getString(Key_my_subscription, "");
    }



    public void setKEY_his_subscription(String hisName)
    {
        editor.putString(Key_his_subscription, hisName);
        // commit changes
        editor.commit();
        Log.d(TAG, "ur gender session modified!");
    }
    public String getKEY_his_subscription(){
        return pref.getString(Key_his_subscription, "");
    }




}
