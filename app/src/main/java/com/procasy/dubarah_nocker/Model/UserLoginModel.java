package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 7/28/2016.
 */
public class UserLoginModel {
    @SerializedName("user_email")
    String Email;

    @SerializedName("user_password")
    String Password;

    public UserLoginModel(String Email,String Password ) {
        this.Email = Email;
        this.Password = Password;
    }

}
