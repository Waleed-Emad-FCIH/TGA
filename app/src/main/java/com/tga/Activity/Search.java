package com.tga.Activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.tga.Response.RequestInterface;
import com.tga.adapter.SearchAdapter;
import com.tga.model.placeDetailsModel;
import java.util.ArrayList;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends AppCompatActivity {

    private int image[]= {R.drawable.reservation,R.drawable.things_to_do,R.drawable.food,R.drawable.discounts,R.drawable.get_around,R.drawable.need_to_know};
    private ArrayList<com.tga.model.Search> ArrayList;
    private RecyclerView recyclerView;
    private SearchAdapter mAdapter;
    private String id="";
    RequestInterface request;
    private java.util.ArrayList<placeDetailsModel> arrayPlaceDetails;
    private ImageView PlImage;
    private TextView txtName,txtRating;
    private RatingBar rtPlace;
    private CardView item_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.reSearch);
        PlImage = findViewById(R.id.PlImage);
        txtName = findViewById(R.id.txtName);
        txtRating = findViewById(R.id.txtRating);
        rtPlace = findViewById(R.id.rtPlace);
        item_search = findViewById(R.id.item_search);

        ArrayList = new ArrayList<>();
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
                request = retrofit.create(RequestInterface.class);
                loadJSON(request,request.getPlaceDetails(id,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));
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


        for (int i = 0; i < image.length; i++) {
            com.tga.model.Search beanClassForRecyclerView_contacts = new com.tga.model.Search(image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new SearchAdapter(getApplicationContext(),ArrayList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }


    private void loadJSON(RequestInterface request, Call<PlaceDetailsResponse> getJSON) {
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
}
