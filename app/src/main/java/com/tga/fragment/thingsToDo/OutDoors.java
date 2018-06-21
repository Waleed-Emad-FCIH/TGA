package com.tga.fragment.thingsToDo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tga.R;
import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.models.place;
import com.tga.util.EndlessScrollListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutDoors extends Fragment{





    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private ThingsToDoAdpater mAdapter;
    RequestInterface request;
    Retrofit  retrofit;
    private String next_page_token="";
    private int currentPage;
    LinearLayoutManager mLayoutManager;

    public OutDoors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_out_doors, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.out_doors_recyclerview);
        ArrayList = new ArrayList<>();

        retrofit= new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ThingsToDoAdpater(getContext(),ArrayList);
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new EndlessScrollListener(mLayoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (next_page_token!=null && !next_page_token.equals("") ) {
                    loadJSON(request.getNextPlacePage(next_page_token,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));
                    next_page_token = "";

                }else {
                    Log.v("...", "Last Item Wow !");
                }
            }
        });


        loadJSON(request.getOutDoors());



        return v;
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
                    Toast.makeText(getContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

}
