package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 7/28/2016.
 */
public class UserRegistrationModel {
    @SerializedName("user_fname")
    String FirstName;
    @SerializedName("user_lname")
    String LastName;
    @SerializedName("user_email")
    String UserEmail;
    @SerializedName("user_phone")
    String Phone;
    @SerializedName("user_birthday")
    String Birthday;
    @SerializedName("user_password")
    String Password;
    @SerializedName("user_gender")
    String Gender;

    public UserRegistrationModel(String firstName, String lastName, String userEmail, String phone, String birthday, String password, String gender) {
        FirstName = firstName;
        LastName = lastName;
        UserEmail = userEmail;
        Phone = phone;
        Birthday = birthday;
        Password = password;
        Gender = gender;
    }
}
