package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 7/28/2016.
 */
public class NockerResponse {
    @SerializedName("skill")
    private String skill;

    @SerializedName("user_lname")
    private String user_lname;

    @SerializedName("user_img")
    private String user_img;

    @SerializedName("user_fname")
    private String user_fname;

    @SerializedName("user_email")
    private String user_email;

    @SerializedName("dist_val")
    private int dist_val;

    @SerializedName("dist_text")
    private String dist_text;

    @SerializedName("time")
    private String time;

    @Override
    public String toString() {
        return "NockerResponse{" +
                "skill='" + skill + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_img='" + user_img + '\'' +
                ", user_fname='" + user_fname + '\'' +
                ", user_email='" + user_email + '\'' +
                ", dist_val=" + dist_val +
                ", dist_text='" + dist_text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getuser_fname() {
        return user_fname;
    }

    public void setuser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getDist_val() {
        return dist_val;
    }

    public void setDist_val(int dist_val) {
        this.dist_val = dist_val;
    }

    public String getDist_text() {
        return dist_text;
    }

    public void setDist_text(String dist_text) {
        this.dist_text = dist_text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
