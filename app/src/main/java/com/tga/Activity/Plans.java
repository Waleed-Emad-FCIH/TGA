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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tga.R;
import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.adapter.PlacesAdapter;
import com.tga.adapter.ThingsToDoLoad;
import com.tga.model.PlaceModel;
import com.tga.model.place;
import com.tga.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Plans extends AppCompatActivity implements ThingsToDoLoad.ItemClickListener, ThingsToDoLoad.RetryLoadMoreListener{


    private RadioButton rbOnlyOneDay,rbMakeYourProgram;
    private Button submit ;
    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private PlacesAdapter mAdapter;
    RequestInterface request;
    private String next_page_token="";
    private int currentPage;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select places");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));
         //=====================  " imbo Code " ====================
                rbOnlyOneDay = (RadioButton) findViewById(R.id.rbOnleOneDay);
                rbMakeYourProgram = (RadioButton) findViewById(R.id.rbMakeYourProgram);

        View.OnClickListener first_radio_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        View.OnClickListener second_radio_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        rbOnlyOneDay.setOnClickListener(second_radio_listener);
        rbMakeYourProgram.setOnClickListener(first_radio_listener);


       //===========================================================

        recyclerView = (RecyclerView) findViewById(R.id.set_plan_recyclerview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        loadJSON(request,request.getPlacesA_Z());

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new PlacesAdapter(this,this,this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                currentPage = page;
                loadMore(page);

            }
        });
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbOnlyOneDay.isChecked())
                {
                    Intent intent  = new Intent(getApplicationContext(), AddPlan.class);
                    startActivity(intent);
                }
                else if(rbMakeYourProgram.isChecked()){
                    Intent intent  = new Intent(getApplicationContext(), AddProgram.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext() , "Please choase the type of plan " , Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        Toast.makeText(this ,id+"" , Toast.LENGTH_LONG);
        return super.onOptionsItemSelected(item);
    }
}
