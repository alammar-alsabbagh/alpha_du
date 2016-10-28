package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 10/28/2016.
 */

public class HelpRequestModel {

    @SerializedName("hr_id")
    public String hr_id;

    @SerializedName("hr_description")
    public String hr_description;

    @SerializedName("skill_name")
    public String skill_name;


    @SerializedName("qoutes")
    public List<QouteModel> qouteModels = new ArrayList<>();

    @Override
    public String toString() {
        return "HelpRequestModel{" +
                "hr_id='" + hr_id + '\'' +
                ", hr_description='" + hr_description + '\'' +
                ", skill_name='" + skill_name + '\'' +
                ", qouteModels=" + qouteModels +
                '}';
    }
}
