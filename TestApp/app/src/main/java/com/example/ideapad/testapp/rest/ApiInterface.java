package com.example.ideapad.testapp.rest;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by IdeaPad on 26.1.2017.
 */

public interface ApiInterface {
    @GET("/")
    Call<TimeMapObject> getTimeObject();
}
