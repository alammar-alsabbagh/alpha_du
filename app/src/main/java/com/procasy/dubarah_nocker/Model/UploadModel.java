package com.procasy.dubarah_nocker.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 10/08/2016.
 */
public class UploadModel {
    @SerializedName("status")
    public int status;

    @SerializedName("path")
    public String path;

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }
}
