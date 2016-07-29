package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 7/28/2016.
 */
public class ReviewsResponse {
    @SerializedName("review_text")
    private String review_text;

    @SerializedName("review_rating")
    private int review_rating;

    @SerializedName("user_fname")
    private String user_fname;

    @SerializedName("user_lname")
    private String user_lname;

    @SerializedName("user_email")
    private String user_email;

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public void setReview_rating(int review_rating) {
        this.review_rating = review_rating;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getReview_text() {

        return review_text;
    }

    public int getReview_rating() {
        return review_rating;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public String getUser_email() {
        return user_email;
    }
}
