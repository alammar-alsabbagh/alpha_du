package com.procasy.dubarah_nocker.Model;

/**
 * Created by DELL on 31/07/2016.
 */
public class ServiceModel {

    public String Description;
    public String servicename;
    public String id;

    public ServiceModel(String id, String servicename, String Description) {
        this.servicename = servicename;
        this.id = id;
        this.Description = Description;

    }

}
