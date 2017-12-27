package com.example.diffa.applicationcarorder.General;

import android.location.Location;

import com.example.diffa.applicationcarorder.Movement.FCMClient;
import com.example.diffa.applicationcarorder.Movement.RetrofitClient;
import com.example.diffa.applicationcarorder.Movement.Service;
import com.example.diffa.applicationcarorder.Movement.mGoogleAPI;
import com.example.diffa.applicationcarorder.Model.User;


/**
 * Created by Diffa on 17/12/2017.
 */

public class Common {

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_req_tbl = "PickupRequest";
    public static final String token_tbl = "Token";
    public static final int PICK_IMAGE_REQ = 9999;

    public static User currentUser;

    public static Location mLastLocation=null;

    public static final String baseURL = "https://maps.googleapis.com";
    public static final String fcmURL = "https://fcm.googleapis.com";
    public static final String user_field = "usr";
    public static final String pass_field = "pass";

    public static double base_fare = 2.55;
    private static double time_rate = 0.35;
    private static double distance_rate =1.75;

    public static double formulaPrice(double km,double min){
        return base_fare+(distance_rate*km)+(time_rate*min);
    }


    public static mGoogleAPI getGoogleAPI() {
        return RetrofitClient.getClient(baseURL).create(mGoogleAPI.class);
    }
    public static Service getFCMService() {
        return FCMClient.getClient(fcmURL).create(Service.class);
    }
}
