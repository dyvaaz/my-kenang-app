package com.example.diffa.kenangapplication.Movement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Diffa on 25/12/2017.
 */

public interface IGoogleAPI {
    @GET
    Call<String>getPath(@Url String url);
}
