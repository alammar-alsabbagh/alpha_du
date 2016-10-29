package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;
import com.procasy.dubarah_nocker.Model.JobModel;

import java.util.List;

/**
 * Created by DELL on 09/08/2016.
 */
public class ResponseJob {

    @SerializedName("status")
    public int status;

    @SerializedName("jobs")
    public List<JobModel> jobs;

    public int getStatus() {
        return status;
    }

    public List<JobModel> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return "ResponseJob{" +
                "status=" + status +
                ", jobs=" + jobs +
                '}';
    }
}
