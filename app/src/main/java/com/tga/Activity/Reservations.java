package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tga.R;
import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.adapter.FoodAndDrinksAdapter;
import com.tga.adapter.ReservationsAdapter;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.model.HotelMdoel;
import com.tga.model.PlaceModel;
import com.tga.model.place;
import com.tga.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Reservations extends AppCompatActivity {

    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private ThingsToDoAdpater mAdapter;
    private String type;
    RequestInterface request;
    Retrofit retrofit;
    private String next_page_token="";
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reservation");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.reservation_recycle);
        ArrayList = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ThingsToDoAdpater(getApplication(), ArrayList);
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (next_page_token != null && !next_page_token.equals("")) {
                    loadJSON(request.getNextPlacePage(next_page_token, "AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));
                    next_page_token = "";

                } else {
                    Log.v("...", "Last Item Wow !");
                }
            }
        });


        loadJSON(request.getHotels());

    }


    private void loadJSON(Call<PlaceResponse> getJSON) {
        Call<PlaceResponse> call = getJSON;
//        ArrayList = new ArrayList<>();
        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {

                PlaceResponse jsonResponse = response.body();
                ArrayList.addAll(Arrays.asList(jsonResponse.getResults()));
                mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(),ArrayList.size()-1);
                next_page_token = jsonResponse.getNext_page_token();
                if (ArrayList.size() == 0 ){
                    Toast.makeText(getApplicationContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
