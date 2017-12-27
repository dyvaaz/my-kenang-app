package com.example.diffa.kenangapplication.General;

import com.example.diffa.kenangapplication.Movement.FCMClient;
import com.example.diffa.kenangapplication.Movement.GoogleMapAPI;
import com.example.diffa.kenangapplication.Movement.IFCMService;
import com.example.diffa.kenangapplication.Movement.IGoogleAPI;

/**
 * Created by Diffa on 21/12/2017.
 */

public class Common {
    public static boolean isDriverFound = false;
    public static String driverId="";

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_req_tbl = "PickupRequest";
    public static final String token_tbl = "Token";
    public static final String rate_detail_tbl = "RateDetails";

    public static final String user_field = "rider_user";
    public static final String pass_field = "rider_pass";

    public static final String fcmURL = "https://fcm.googleapis.com";
    public static final String googleAPIURL = "https://maps.googleapis.com";

    private static double base_rate = 2.55;
    private static double time_rate = 0.35;
    private static double distance_rare = 1.75;

    public static double getPrice(double km, int min){
        return (base_rate+(time_rate*min+(distance_rare*km)));
    }


    public static IFCMService getFCMService() {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }
    public static IGoogleAPI getGoogleService() {
        return GoogleMapAPI.getClient(googleAPIURL).create(IGoogleAPI.class);
    }
}
