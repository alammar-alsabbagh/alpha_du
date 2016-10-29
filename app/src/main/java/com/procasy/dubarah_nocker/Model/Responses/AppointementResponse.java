package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;
import com.procasy.dubarah_nocker.Model.AppointementModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 10/29/2016.
 */

public class AppointementResponse {

    @SerializedName("status")
    public int status;

    @SerializedName("nocker_jobs")
    public List<AppointementModel> nocker_jobs = new ArrayList<>();

    @SerializedName("user_jobs")
    public List<AppointementModel> user_jobs = new ArrayList<>();

    @Override
    public String toString() {
        return "AppointementResponse{" +
                "status=" + status +
                ", nocker_jobs=" + nocker_jobs +
                ", user_jobs=" + user_jobs +
                '}';
    }
}
