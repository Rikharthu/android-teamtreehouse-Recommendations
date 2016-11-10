package com.example.uberv.recommendations.api;


import com.example.uberv.recommendations.model.ActiveListings;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Api {

    @GET("/listings/active")
    void activeListings(@Query("includes") String includes, // quaery appended parameter to the url
                        Callback<ActiveListings> callback);

}
