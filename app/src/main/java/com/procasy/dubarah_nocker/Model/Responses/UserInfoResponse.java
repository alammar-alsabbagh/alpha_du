package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;
import com.procasy.dubarah_nocker.Model.TestimonialsModel;

import java.util.List;

/**
 * Created by DELL on 08/08/2016.
 */
public class UserInfoResponse {

    @SerializedName("user")
    private UserResponse user;

    @SerializedName("album")
    private List<AlbumResponse> albums;

    @SerializedName("skills")
    private List<SkillsResponse> skills;

    @SerializedName("testimonials")
    private List<TestimonialsModel> testimonials;

    @SerializedName("avg_charge")
    private String avg_charge;

    public List<AlbumResponse> getAlbums() {
        return albums;
    }

    public List<TestimonialsModel> getTestimonials() {
        return testimonials;
    }

    public List<SkillsResponse> getSkills() {
        return skills;
    }

    public String getAvg_charge() {
        return avg_charge;
    }

    public UserResponse getUser() {
        return user;
    }


}
