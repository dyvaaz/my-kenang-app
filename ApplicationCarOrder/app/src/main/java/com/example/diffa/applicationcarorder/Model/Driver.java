package com.example.diffa.applicationcarorder.Model;

/**
 * Created by Diffa on 21/12/2017.
 */

public class Driver {
    public String to;
    public Notifications notif;

    public Driver(String to, Notifications notif) {
        this.to = to;
        this.notif = notif;
    }

    public Driver() {

    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notifications getNotification() {
        return notif;
    }

    public void setNotification(Notifications notif) {
        this.notif = notif;

    }

}