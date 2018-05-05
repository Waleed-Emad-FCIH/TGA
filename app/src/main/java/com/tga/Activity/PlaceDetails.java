package com.tga.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.tga.R;
import com.tga.Response.PlaceDetailsResponse;

import com.tga.Response.RequestInterface;
import com.tga.adapter.ReviewsAdapter;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.model.geometry;
import com.tga.model.photos;
import com.tga.model.placeDetailsModel;
import com.tga.model.reviews;
import com.tga.util.SliderLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private ImageView imgBack;
    private String id;
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private SliderLayout mDemoSlider;
    RequestInterface request;
    private java.util.ArrayList<placeDetailsModel> arrayPlaceDetails;
    private List<placeDetailsModel> placeDetailsModels;
    private RecyclerView recyclerView;
    private ReviewsAdapter mAdapter;

    private TextView txtPlaceName;
    private TextView txtRate;
    private RatingBar rtPlace;
    private TextView txtOpeningDays;
    private ImageView imgMap;
    private TextView txtWebsite;
    private TextView txtPlacePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        id = getIntent().getStringExtra("id");
//        id = "ChIJzbs54n1PWBQRizZuWjV0dMo";
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txtPlaceName = findViewById(R.id.txtPlaceName);
        txtRate =findViewById(R.id.txtRate);
        rtPlace =findViewById(R.id.rtPlace);
        txtOpeningDays = findViewById(R.id.txtOpeningDays);
        recyclerView = findViewById(R.id.recycler_view_reviews);
        imgMap = findViewById(R.id.imgMap);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtPlacePhone = findViewById(R.id.txtPlacePhone);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);



        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/details/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        loadJSON(request,request.getPlaceDetails(id,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));


    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void loadJSON(RequestInterface request, Call<PlaceDetailsResponse> getJSON) {
        Call<PlaceDetailsResponse> call = getJSON;
//        ArrayList = new ArrayList<>();
        call.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {

                PlaceDetailsResponse jsonResponse = response.body();
                arrayPlaceDetails = new ArrayList<>(Arrays.asList(jsonResponse.getResult()));
//                placeDetailsModel[] photos =jsonResponse.getResult();
                lun(arrayPlaceDetails);
            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void loadJSON(RequestInterface request, Call<List<PlaceDetailsResponse>> getJSON) {
//        Call<List<PlaceDetailsResponse>> call = getJSON;
////        ArrayList = new ArrayList<>();
//        call.enqueue(new Callback<List<PlaceDetailsResponse>>() {
//            @Override
//            public void onResponse(Call<List<PlaceDetailsResponse>> call, Response<List<PlaceDetailsResponse>> response) {
//
//                List<PlaceDetailsResponse> jsonResponse = response.body();
//                arrayPlaceDetails = new ArrayList<>(Arrays.asList(jsonResponse.get(0).getResult()));
//                placeDetailsModel[] photos =jsonResponse.get(0).getResult();
//                lun(arrayPlaceDetails);
//            }
//
//            @Override
//            public void onFailure(Call<List<PlaceDetailsResponse>> call, Throwable t) {
//                Toast.makeText(getApplicationContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void lun(ArrayList<placeDetailsModel> placeDetails){
        placeDetailsModels = placeDetails;
        geometry locations =placeDetailsModels.get(0).getGeometry();
        String name =placeDetailsModels.get(0).getName();
        List<photos> photos =placeDetailsModels.get(0).getPhotos();
        float rate = placeDetailsModels.get(0).getRating();
        List<reviews> reviews= placeDetailsModels.get(0).getReviews();
        placeDetailsModel.opening_hours opening_hours = placeDetailsModels.get(0).getOpening_hours();


        String open = "";
        try{
            String [] weekday_text =opening_hours.getWeekday_text();
            for(int i = 0;i<weekday_text.length;i++){
                open = open+"\n"+weekday_text[i];
            }
        }catch (Exception e){
            open = "No information available";
        }
        final String lat= locations.getLocation().getLat();
        final String lng= locations.getLocation().getLng();

        txtPlaceName.setText(name);
        txtRate.setText(String.valueOf(rate));
        rtPlace.setRating(rate);

        final String web = placeDetailsModels.get(0).getWebsite();
        String phone = placeDetailsModels.get(0).getInternational_phone_number();

        if (web!=null){
            txtWebsite.setText(web);
            txtWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                    startActivity(browserIntent);
                }
            });
        }else {
            txtWebsite.setText("No web site");
        }


        if (phone!=null){
            txtPlacePhone.setText(phone);
        }else {
            txtPlacePhone.setText("No phone number");
        }





        txtOpeningDays.setText(open);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter = new ReviewsAdapter((ArrayList<com.tga.model.reviews>) reviews,getApplication());
        recyclerView.setAdapter(mAdapter);

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        for(int i = 0;i<3;i++){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photos.get(i).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());
            // textSliderView.getBundle().putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);



    }

}
