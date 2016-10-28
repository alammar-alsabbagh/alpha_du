package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;
import com.procasy.dubarah_nocker.Model.HelpRequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 10/28/2016.
 */

public class UserQoutesResponse {
    @SerializedName("status")
    public int status;

    @SerializedName("help_requests")
    public List<HelpRequestModel> helpRequests = new ArrayList<>();

    @Override
    public String toString() {
        return "UserQoutesResponse{" +
                "status=" + status +
                ", helpRequests=" + helpRequests +
                '}';
    }
}
