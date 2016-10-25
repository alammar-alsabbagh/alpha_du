package com.procasy.dubarah_nocker.Model;

/**
 * Created by Omar on 10/22/2016.
 */

public class Appointement {

    public String appointement_date;
    public String appointement_time;
    public String appointement_ammount;
    public String appointement_nocker_name;
    public String appointement_skill;


    public Appointement(String appointement_date, String appointement_time, String appointement_ammount, String appointement_nocker_name, String appointement_skill) {
        this.appointement_date = appointement_date;
        this.appointement_time = appointement_time;
        this.appointement_ammount = appointement_ammount;
        this.appointement_nocker_name = appointement_nocker_name;
        this.appointement_skill = appointement_skill;
    }

    @Override
    public String toString() {
        return "Appointement{" +
                "appointement_date='" + appointement_date + '\'' +
                ", appointement_time='" + appointement_time + '\'' +
                ", appointement_ammount='" + appointement_ammount + '\'' +
                ", appointement_nocker_name='" + appointement_nocker_name + '\'' +
                ", appointement_skill='" + appointement_skill + '\'' +
                '}';
    }

    public void setAppointement_date(String appointement_date) {
        this.appointement_date = appointement_date;
    }

    public void setAppointement_time(String appointement_time) {
        this.appointement_time = appointement_time;
    }

    public void setAppointement_ammount(String appointement_ammount) {
        this.appointement_ammount = appointement_ammount;
    }

    public void setAppointement_nocker_name(String appointement_nocker_name) {
        this.appointement_nocker_name = appointement_nocker_name;
    }

    public void setAppointement_skill(String appointement_skill) {
        this.appointement_skill = appointement_skill;
    }

    public String getAppointement_date() {
        return appointement_date;
    }

    public String getAppointement_time() {
        return appointement_time;
    }

    public String getAppointement_ammount() {
        return appointement_ammount;
    }

    public String getAppointement_nocker_name() {
        return appointement_nocker_name;
    }

    public String getAppointement_skill() {
        return appointement_skill;
    }
}
