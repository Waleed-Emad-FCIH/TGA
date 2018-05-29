package com.tga.Activity;

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
import android.widget.Toast;

import com.tga.R;
import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.adapter.ThingsToDoLoad;
import com.tga.model.place;
import com.tga.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodType extends AppCompatActivity implements ThingsToDoLoad.ItemClickListener, ThingsToDoLoad.RetryLoadMoreListener{

    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private ThingsToDoLoad mAdapter;
    private String type;
    RequestInterface request;
    private String next_page_token="";
    private int currentPage;

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getStringExtra("type");

        getSupportActionBar().setTitle(type);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView)findViewById(R.id.food_type_recyclerview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        if (type.equals("Restaurants")){
            loadJSON(request,request.getResturants());
        }else if(type.equals("Cafes")){
            loadJSON(request,request.getCafe());
        }else if (type.equals("Bars")){
            loadJSON(request,request.getBar());
        }
        else {
            Toast.makeText(getApplicationContext().getApplicationContext(), "Something wrong, please try again", Toast.LENGTH_LONG).show();
        }

// next
        //https://maps.googleapis.com/maps/api/place/textsearch/json?pagetoken=CpQCBAEAACccnH-i6SEI5bLib8jCUkn8BjngoqMg60LZaVlIAF6C_6zBJKDy9pTeE1towTnpXQ4xisLCys8jfw4iVHmwIUGBLNMF9DSzLKdXF5N02wPN_XSamI5r7H1Yu3kmCgcAQKyX07UItTWANIJCBjRI642KQakOCoZopWpbhTubzrahKeZrXzdiyTxi0IL1E0gVp2G1eYDGsh6g1-Tl7psTGS4EYP5tJ3so71ADZNhBRrcUlhqRP6BlpQUTbQPV-n2WaB2DHX1hnVkxj15oFiTAVAhCuE5V04RiLFEmcQ-bwvn64qx0OBd001PgWXF8luvs1F-P1WK4EMSTWxw-xvqYoUbQtKsK_ZH8UllB8E7iXkRaEhBXuycqhdu7GvvpajXhrm_QGhTxVMvpdk-BhJYDjjUMVOYaGW1FGQ&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ThingsToDoLoad(this,this,this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                currentPage = page;
                loadMore(page);

            }
        });



//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                visibleItemCount = mLayoutManager.getChildCount();
//                totalItemCount = mLayoutManager.getItemCount();
//                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
//
//                if (next_page_token!=null && !next_page_token.equals("") ) {
//                    loadJSON(request,request.getNextPlacePage(next_page_token,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));
//                    next_page_token = "";
//
//                }else {
//                    Log.v("...", "Last Item Wow !");
//                }
//
//            }
//        });

//        recyclerView.setOnScrollListener(new EndlessScrollListener(mLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//
//                if (!next_page_token.equals("")){
//                    loadJSON(request,request.getBar());
//                }
//            }
//        });



    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRetryLoadMore() {
        loadMore(currentPage);
    }

    private void loadMore(final int page){
        mAdapter.startLoadMore();

        // example read end
        if(page == 3){
            mAdapter.onReachEnd();
            return;
        }

        if (next_page_token!=null && !next_page_token.equals("") ) {
                    loadJSON(request,request.getNextPlacePage(next_page_token,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));
                    next_page_token = "";

                }else {
                    Log.v("...", "Last Item Wow !");
                }

        // start load more
    }


    private void loadJSON(RequestInterface request, Call<PlaceResponse> getJSON) {
        Call<PlaceResponse> call = getJSON;
//        ArrayList = new ArrayList<>();
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

                next_page_token = jsonResponse.getNext_page_token();
                mAdapter.add(ArrayList);
                recyclerView.setAdapter(mAdapter);
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
