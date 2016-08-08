package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 31/07/2016.
 */
public class TestimonialsModel {

    @SerializedName("user_email")
    public String email;

    @SerializedName("user_fname")
    public String username_f;

    @SerializedName("user_lname")
    public String username_l;


    @SerializedName("review_text")
    public String descr;

    @SerializedName("review_date")
    public String date;

    @SerializedName("review_rating")
    public String rate;

    @SerializedName("user_img")
    public String img_url;

    public TestimonialsModel(String email, String usname_f, String username_l,
                             String descr, String date, String rate, String img_url) {
        this.email = email;
        this.descr = descr;
        this.username_f = usname_f;
        this.username_l = username_l;
        this.date = date;
        this.rate = rate;
        this.img_url = img_url;
    }

    public String getDate() {
        return date;
    }

    public String getDescr() {
        return descr;
    }

    public String getEmail() {
        return email;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getRate() {
        return rate;
    }

    public String getUsername() {
        return username_f + " " + username_l;
    }


}
