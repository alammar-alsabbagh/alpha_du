package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 29/07/2016.
 */
public class LocationResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }


    @Override
    public String toString() {
        return "LoginResponse{" +
                "status=" + status +
                +
                        '}';
    }

}
