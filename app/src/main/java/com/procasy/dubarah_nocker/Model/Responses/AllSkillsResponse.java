package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 29/07/2016.
 */
public class AllSkillsResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("skills")
    private List<SkillsResponse> mskillsResponse;

    public List<SkillsResponse> getAllSkills()
    {
        return this.mskillsResponse;
    }

    public int getStatus()
    {
        return this.status;
    }


}
