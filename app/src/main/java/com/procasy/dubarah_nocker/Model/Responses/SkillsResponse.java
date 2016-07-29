package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 7/28/2016.
 */
public class SkillsResponse {
    @SerializedName("skill_id")
    private int skill_id;

    @SerializedName("skill_name")
    private String skill_name;

    @SerializedName("is_hidden")
    private int is_hidden;

    public void setIs_hidden(int is_hidden) {
        this.is_hidden = is_hidden;
    }

    public int getIs_hidden() {

        return is_hidden;
    }

    public int getSkill_id() {
        return skill_id;
    }

    public String getSkill_name() {
        return skill_name;
    }
    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    @Override
    public String toString() {
        return "SkillsResponse{" +
                "skill_id=" + skill_id +
                ", skill_name='" + skill_name + '\'' +
                '}';
    }
}
