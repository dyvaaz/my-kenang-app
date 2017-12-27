package com.example.diffa.applicationcarorder.Movement;

import com.example.diffa.applicationcarorder.Model.Driver;
import com.example.diffa.applicationcarorder.Model.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Diffa on 21/12/2017.
 */

public interface Service {
    @Headers({
            //Server on ApplicationCarOrder
            "Content-Type:application/json",
            "Authorization:key=AAAASlNEmq4:AAAA4u5BDjg:APA91bGzWf53uI6qxXE0GAu7G_nYBuOZ9syrlrRRyDB6hj0yUPKRriyZLM5GEgvabW-G-xijsTZFjCahXMiyyd2MoFQ4oRdx2VGPNCrt40K7JMdLAIyWV1Acttyc05zP4Utwd2NiW79q"
    })
    @POST("fcm/send")
    Call<FCMResponse>sendMessage(@Body Driver body);
}
