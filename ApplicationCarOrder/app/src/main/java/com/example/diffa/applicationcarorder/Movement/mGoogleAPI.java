package com.example.diffa.applicationcarorder.Movement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Diffa on 17/12/2017.
 */

public interface mGoogleAPI {
    @GET
    Call<String> getPath(@Url String url);

}
