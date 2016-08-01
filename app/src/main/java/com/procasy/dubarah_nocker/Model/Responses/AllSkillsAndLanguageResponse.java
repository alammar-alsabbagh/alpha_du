package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 29/07/2016.
 */
public class AllSkillsAndLanguageResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("skills")
    private List<SkillsResponse> mskillsResponse;

    @SerializedName("languages")
    private List<LanguageResponse> mLanguageResponse;

    public List<LanguageResponse> getAllLanguage()
    {
        return mLanguageResponse;
    }
    public List<SkillsResponse> getAllSkills()
    {
        return this.mskillsResponse;
    }

    public int getStatus()
    {
        return this.status;
    }


}
