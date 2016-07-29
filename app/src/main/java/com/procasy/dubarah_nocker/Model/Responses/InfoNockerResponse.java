package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 7/28/2016.
 */
public class InfoNockerResponse {
    @SerializedName("user")
    private UserResponse user;

    @SerializedName("album")
    private List<AlbumResponse> album;

    @SerializedName("skills")
    private List<SkillsResponse> skills;

    @SerializedName("testimonials")
    private List<ReviewsResponse> reviews;

    @SerializedName("avg_charge")
    private int avg_charge;

    public InfoNockerResponse() {
        album = new ArrayList<>();
        skills = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public static InfoNockerResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        InfoNockerResponse infoNockerResponse = gson.fromJson(response, InfoNockerResponse.class);
        return infoNockerResponse;
    }

    @Override
    public String toString() {
        return "InfoNockerResponse{" +
                "user=" + user.toString() +
                ", album=" + album +
                ", skills=" + skills.toString() +
                ", reviews=" + reviews +
                ", avg_charge=" + avg_charge +
                '}';
    }

    public UserResponse getUser() {
        return user;
    }

    public List<AlbumResponse> getAlbum() {
        return album;
    }

    public List<SkillsResponse> getSkills() {
        return skills;
    }

    public List<ReviewsResponse> getReviews() {
        return reviews;
    }

    public int getAvg_charge() {
        return avg_charge;
    }
}
