package com.procasy.dubarah_nocker.Model;

/**
 * Created by Omar on 10/22/2016.
 */

public class Notification {

    public String notification_title;
    public String notification_descr;
    public String notification_type;
    public String notification_content;
    public Boolean Seen;
    public int notification_id;

    public Notification(int notification_id ,String notification_title, String notification_descr, String notification_type, String notification_content , Boolean seen) {
        this.notification_title = notification_title;
        this.notification_descr = notification_descr;
        this.notification_type = notification_type;
        this.notification_content = notification_content;
        this.Seen = seen;
        this.notification_id = notification_id;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notification_title='" + notification_title + '\'' +
                ", notification_descr='" + notification_descr + '\'' +
                ", notification_type='" + notification_type + '\'' +
                ", notification_content='" + notification_content + '\'' +
                '}';
    }


    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public void setNotification_descr(String notification_descr) {
        this.notification_descr = notification_descr;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public void setNotification_content(String notification_content) {
        this.notification_content = notification_content;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public String getNotification_descr() {
        return notification_descr;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public String getNotification_content() {
        return notification_content;
    }
}
