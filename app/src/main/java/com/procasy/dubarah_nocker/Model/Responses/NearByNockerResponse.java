package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 7/28/2016.
 */
public class NearByNockerResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("users")
    private List<NockerResponse> users;

    public NearByNockerResponse() {
        users = new ArrayList<>();
    }

    public static InfoNockerResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        InfoNockerResponse infoNockerResponse = gson.fromJson(response, InfoNockerResponse.class);
        return infoNockerResponse;
    }

    @Override
    public String toString() {
        return "NearByNockerResponse{" +
                "status=" + status +
                ", users=" + users.toString() +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public List<NockerResponse> getUsers() {
        return users;
    }
}

