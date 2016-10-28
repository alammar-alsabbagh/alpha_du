package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 10/28/2016.
 */

public class QouteModel {

    @SerializedName("qoute_id")
    public String qoute_id;

    @SerializedName("qoute_nocker_id")
    public String qoute_nocker_id;

    @SerializedName("qoute_hr_id")
    public String qoute_hr_id;

    @SerializedName("qoute_charge")
    public String qoute_charge;

    @SerializedName("qoute_date")
    public String qoute_date;

    @SerializedName("qoute_time")
    public String qoute_time;

    @SerializedName("qoute_description")
    public String qoute_description;


    @SerializedName("qoute_accept_nocker")
    public String qoute_accept_nocker;


    @SerializedName("qoute_accept_user")
    public String qoute_accept_user;

    @SerializedName("qoute_cancel")
    public String qoute_cancel;


    @SerializedName("nocker_name")
    public String nocker_name;

    @SerializedName("nocker_address")
    public String nocker_address;

    @SerializedName("user_img")
    public String user_img;

    @Override
    public String toString() {
        return "QouteModel{" +
                "qoute_id='" + qoute_id + '\'' +
                ", qoute_nocker_id='" + qoute_nocker_id + '\'' +
                ", qoute_hr_id='" + qoute_hr_id + '\'' +
                ", qoute_charge='" + qoute_charge + '\'' +
                ", qoute_date='" + qoute_date + '\'' +
                ", qoute_time='" + qoute_time + '\'' +
                ", qoute_description='" + qoute_description + '\'' +
                ", qoute_accept_nocker='" + qoute_accept_nocker + '\'' +
                ", qoute_accept_user='" + qoute_accept_user + '\'' +
                ", qoute_cancel='" + qoute_cancel + '\'' +
                ", nocker_name='" + nocker_name + '\'' +
                ", nocker_address='" + nocker_address + '\'' +
                ", user_img='" + user_img + '\'' +
                '}';
    }
}
