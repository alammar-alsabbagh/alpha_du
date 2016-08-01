package com.procasy.dubarah_nocker.Model.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 01/08/2016.
 */
public class LanguageResponse {

    @SerializedName("lang_id")
    private int lang_id;

    @SerializedName("lang_code")
    private String lang_code;

    @SerializedName("lang_name")
    private String lang_name;

    public int getlang_id()
    {
        return lang_id;
    }

    public String getLang_code()
    {
        return lang_code;
    }

    public String getLang_name()
    {
        return lang_name;
    }

}
