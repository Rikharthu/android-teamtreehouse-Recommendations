package com.example.uberv.recommendations.api;

import com.example.uberv.recommendations.model.ActiveListings;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class Etsy {

    public static final String API_KEY="aveipm7oc0a9imneqh6p3aoq";

    private static RequestInterceptor getInterceptor(){
        // append api key to the end
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api_key",API_KEY);
            }
        };
    }

    private static Api getApi(){
        return new RestAdapter.Builder().
                setEndpoint("https://openapi.etsy.com/v2")
                .setRequestInterceptor(getInterceptor())
                .build()
                .create(Api.class);
    }

    public static void getActiveListing(Callback<ActiveListings> callback){
        getApi().activeListings("Shop,Images",callback);
    }

}
