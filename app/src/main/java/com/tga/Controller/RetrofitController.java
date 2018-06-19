package com.tga.Controller;

import android.content.Context;
import android.widget.Toast;

import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.model.place;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mada on 6/11/2018.
 */

public class RetrofitController {

    ArrayList<place>  ArrayList;
     //

    public RequestInterface LoadPlaces(String baseUrl){

        RequestInterface request;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);

        return request;
    }

    public ArrayList<place> loadJSON(RequestInterface request, Call<PlaceResponse> getJSON, Context context) {
        Call<PlaceResponse> call = getJSON;

        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {

                PlaceResponse jsonResponse = response.body();
                if (ArrayList==null){
                    ArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                }else
                {
                    ArrayList.addAll(Arrays.asList(jsonResponse.getResults()));
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
        return ArrayList;
    }

}
