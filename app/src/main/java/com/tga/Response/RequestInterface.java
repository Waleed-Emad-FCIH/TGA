package com.tga.Response;




import com.tga.R;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RequestInterface {



    @GET("json?query=top+spots+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getTopSpots();

    @GET("json?query=jewelry+or+casino+or+spa+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getPlacesA_Z();

    @GET("json?query=zoo+park+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getOutDoors();

    @GET("json?query=museum+and+mall+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getInDoors();

    @GET("json?query=Restaurants+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getResturants();

    @GET("json?query=cafe+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getCafe();

    @GET("json?query=bar+in+cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getBar();

    @GET("json?query=hotels+in+cairo+giza&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getHotels();

    @GET("json?")
    Call<PlaceDetailsResponse> getPlaceDetails(@Query("placeid") String id,
                                               @Query("key") String key);

    @GET("json?")
    Call<PlaceResponse> getNextPlacePage(@Query("pagetoken") String id,
                                               @Query("key") String key);

}
