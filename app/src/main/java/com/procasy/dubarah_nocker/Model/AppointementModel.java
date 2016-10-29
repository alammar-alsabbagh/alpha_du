package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 10/29/2016.
 */

public class AppointementModel {

    @SerializedName("job_id")
    public String job_id;

    @SerializedName("skill_name")
    public String skill_name;

    @SerializedName("qoute_time")
    public String qoute_time;

    @SerializedName("qoute_date")
    public String qoute_date;

    @SerializedName("qoute_charge")
    public String qoute_charge;

    @SerializedName("nocker_name")
    public String nocker_name;

    @SerializedName("user_img")
    public String user_img;

    @Override
    public String toString() {
        return "AppointementModel{" +
                "job_id='" + job_id + '\'' +
                ", skill_name='" + skill_name + '\'' +
                ", qoute_time='" + qoute_time + '\'' +
                ", qoute_date='" + qoute_date + '\'' +
                ", qoute_charge='" + qoute_charge + '\'' +
                ", nocker_name='" + nocker_name + '\'' +
                ", user_img='" + user_img + '\'' +
                '}';
    }
}
