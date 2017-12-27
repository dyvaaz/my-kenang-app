package com.example.diffa.kenangapplication.Movement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.example.diffa.kenangapplication.Model.Driver;
import com.example.diffa.kenangapplication.Model.FCMResponse;

/**
 * Created by Diffa on 21/12/2017.
 */

public interface IFCMService {
    @Headers({
            //Server on KenangApp
            "Content-Type:application/json",
            "Authorization:key=AAAA4u5BDjg:APA91bHI-obcD5ICCtgH4a7XuCwUd2GvF8QHVn0B7Djay9r2_wkiWftnt0y0qJ_mgSsUxCxZyONiz9fz7rzU2Q7ZNBUXi0n4aKTn0FRNNobLbR6zwwkxXLA8VlAXyLDxcU8NEtDt850g"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Driver body);
}
