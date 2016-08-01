package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 7/28/2016.
 */
public class UserResponse {
    @SerializedName("user_fname")
    private String user_fname;

    @SerializedName("user_lname")
    private String user_lname;

    @SerializedName("user_img")
    private String user_img;

    @SerializedName("user_promo")
    private String user_promo;

    @SerializedName("user_email")
    private String user_email;

    @SerializedName("user_phone")
    private String user_phone;

    @SerializedName("user_description")
    private String user_description;

    @SerializedName("user_birthday")
    private String user_birthday;

    @SerializedName("user_gender")
    private String user_gender;

    @SerializedName("user_is_nocker")
    private int user_is_nocker;

    public int getUser_is_nocker() {
        return user_is_nocker;
    }

    public void setUser_is_nocker(int user_is_nocker) {
        this.user_is_nocker = user_is_nocker;
    }

    public int is_nocker() {
        return user_is_nocker;
    }

    public void setIs_nocker(int is_nocker) {
        this.user_is_nocker = is_nocker;
    }



    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public void setUser_promo(String user_promo) {
        this.user_promo = user_promo;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_description(String user_description) {
        this.user_description = user_description;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_fname() {

        return user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public String getUser_img() {
        return user_img;
    }

    public String getUser_promo() {
        return user_promo;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_description() {
        return user_description;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public String getUser_gender() {
        return user_gender;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_img='" + user_img + '\'' +
                ", user_promo='" + user_promo + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_description='" + user_description + '\'' +
                ", user_birthday='" + user_birthday + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", is_nocker=" + user_is_nocker +
                '}';
    }
}
