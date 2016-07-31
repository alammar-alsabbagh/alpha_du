package com.procasy.dubarah_nocker.Model;

/**
 * Created by DELL on 31/07/2016.
 */
public class TestimonialsModel {

    public String id;
    public String username;
    public String descr;
    public String date;
    public String rate;
    public String img_url;

    public TestimonialsModel(String id, String usname, String descr, String date, String rate, String img_url) {
        this.id = id;
        this.descr = descr;
        this.username = usname;
        this.date = date;
        this.rate = rate;
        this.img_url = img_url;

    }


}
