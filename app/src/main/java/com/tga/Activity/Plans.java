package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.squareup.picasso.Picasso;
import com.tga.R;
import com.tga.Response.PlaceDetailsResponse;
import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.adapter.PlacesAdapter;
import com.tga.adapter.ThingsToDoLoad;
import com.tga.model.PlaceModel;
import com.tga.model.place;
import com.tga.model.placeDetailsModel;
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
    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private PlacesAdapter mAdapter;
    RequestInterface request,request2;
    private String next_page_token="";
    private int currentPage;
    private LinearLayoutManager mLayoutManager;
    private ImageView PlImage;
    private TextView txtName,txtRating;
    private RatingBar rtPlace;
    private CardView item_search;
    private String id = "";
    private java.util.ArrayList<placeDetailsModel> arrayPlaceDetails;

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
        rbOnlyOneDay.setOnClickListener(first_radio_listener);
        rbMakeYourProgram.setOnClickListener(second_radio_listener);


       //===========================================================

        recyclerView = (RecyclerView) findViewById(R.id.set_plan_recyclerview);
        PlImage = findViewById(R.id.PlImage);
        txtName = findViewById(R.id.txtName);
        txtRating = findViewById(R.id.txtRating);
        rtPlace = findViewById(R.id.rtPlace);
        item_search = findViewById(R.id.item_search);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setCountry("EG").build();
        autocompleteFragment.setFilter(autocompleteFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
//                Toast.makeText(getApplicationContext(),"Place: " + place.getPriceLevel(),Toast.LENGTH_SHORT).show();
//                Log.i("", "Place: " + place.getPriceLevel());

                id = place.getId();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/maps/api/place/details/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                request2 = retrofit.create(RequestInterface.class);
                loadJSON2(request2,request2.getPlaceDetails(id,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));
                item_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),PlaceDetails.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
                Log.i("", "An error occurred: " + status);
            }
        });


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

    private void loadJSON2(RequestInterface request, Call<PlaceDetailsResponse> getJSON) {
        Call<PlaceDetailsResponse> call = getJSON;
//        ArrayList = new ArrayList<>();
        call.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {

                PlaceDetailsResponse jsonResponse = response.body();
                arrayPlaceDetails = new ArrayList<>(Arrays.asList(jsonResponse.getResult()));
                txtName.setText(arrayPlaceDetails.get(0).getName());
                txtRating.setText(String.valueOf(arrayPlaceDetails.get(0).getRating()));
                rtPlace.setRating(arrayPlaceDetails.get(0).getRating());
                try {
                    Picasso.with(getApplicationContext())
                            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+arrayPlaceDetails.get(0).getPhotos().get(0).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                            .into(PlImage);
                    item_search.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }catch (Exception e){
                    Picasso.with(getApplicationContext())
                            .load("https://d2o57arp16h0eu.cloudfront.net/echo/img/no_image_available.png")
                            .into(PlImage);
                    item_search.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
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
