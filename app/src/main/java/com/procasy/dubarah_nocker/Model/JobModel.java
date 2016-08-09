package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 09/08/2016.
 */
public class JobModel {

    @SerializedName("title")
    public String job_title;

    @SerializedName("description")
    public String job_descr;

    @SerializedName("dist_text")
    public String job_distance;

    @SerializedName("address")
    public String job_country;

    public JobModel(String job_title, String job_descr, String job_distance, String job_country) {
        this.job_title = job_title;
        this.job_descr = job_descr;
        this.job_country = job_country;
        this.job_distance = job_distance;
    }
}
