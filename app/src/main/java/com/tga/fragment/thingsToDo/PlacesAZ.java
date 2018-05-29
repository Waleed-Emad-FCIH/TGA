package com.tga.fragment.thingsToDo;

import android.content.Context;
import android.net.Uri;
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
import com.tga.adapter.PlacesAdapter;
import com.tga.adapter.ThingsToDoAdpater;
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


public class PlacesAZ extends Fragment  implements ThingsToDoLoad.ItemClickListener, ThingsToDoLoad.RetryLoadMoreListener{

    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private ThingsToDoLoad mAdapter;
    RequestInterface request;
    private String next_page_token="";
    private int currentPage;
    LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_places_az, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.all_places_recyclerview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        loadJSON(request,request.getPlacesA_Z());

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ThingsToDoLoad(getContext(),this,this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                currentPage = page;
                loadMore(page);

            }
        });

        return v;
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
                Toast.makeText(getContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

}
