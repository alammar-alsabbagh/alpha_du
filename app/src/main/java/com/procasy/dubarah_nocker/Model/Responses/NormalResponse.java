package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 10/29/2016.
 */

public class NormalResponse {


    @SerializedName("status")
    public int status ;

    @SerializedName("message")
    public String message;


    @Override
    public String toString() {
        return "NormalResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
