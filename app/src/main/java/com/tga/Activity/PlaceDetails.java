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
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.client.Firebase;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.R;
import com.tga.Response.PlaceDetailsResponse;

import com.tga.Response.RequestInterface;
import com.tga.adapter.ReviewsAdapter;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.model.geometry;
import com.tga.model.photos;
import com.tga.model.place;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    private ImageView imgFav , imgUnFav;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid();
    private DatabaseReference mDatabase;
    private String imgLink="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        id = getIntent().getStringExtra("id");

//        id = "ChIJzbs54n1PWBQRizZuWjV0dMo";
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txtPlaceName =(TextView) findViewById(R.id.txtPlaceName);
        txtRate =(TextView)findViewById(R.id.txtRate);
        rtPlace =(RatingBar)findViewById(R.id.rtPlace);
        txtOpeningDays =(TextView) findViewById(R.id.txtOpeningDays);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view_reviews);
        imgMap =(ImageView) findViewById(R.id.imgMap);
        txtWebsite =(TextView) findViewById(R.id.txtWebsite);
        txtPlacePhone =(TextView) findViewById(R.id.txtPlacePhone);
        imgFav =(ImageView) findViewById(R.id.imgFav);
        imgUnFav =(ImageView) findViewById(R.id.imgUnFav);

        imgUnFav.setVisibility(VISIBLE);
        imgFav.setVisibility(GONE);
        imgUnFav.invalidate();
        imgFav.invalidate();
        final ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(165);

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFav.setVisibility(GONE);
                imgUnFav.startAnimation(animation);
                imgUnFav.setVisibility(VISIBLE);
                Firebase firebase=new Firebase("https://tguidea-86215.firebaseio.com/favourites/"+uid+id);
                firebase.removeValue();

            }
        });

        imgUnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgUnFav.setVisibility(GONE);
                imgFav.startAnimation(animation);
                imgFav.setVisibility(VISIBLE);
                place place = new place();
                place.setName(txtPlaceName.getText().toString());
                place.setRating(Float.parseFloat(txtRate.getText().toString()));
                place.setPlace_id(id);
                place.setUid(uid);
                place.setImgLink(imgLink);
                mDatabase.child("favourites").child(uid+id).setValue(place);
            }
        });




        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot();
        Query applesQuery = db_node.child("favourites").orderByChild("uid").equalTo(uid);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, place> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, place>>() {});
                    List<place> posts = new ArrayList<>(results.values());
                    for (place post : posts) {
                        if (post.getPlace_id().equals(id)) {
                            imgFav.setVisibility(VISIBLE);
                            imgUnFav.setVisibility(GONE);
                            break;
                        } else {
                            imgFav.setVisibility(GONE);
                            imgUnFav.setVisibility(VISIBLE);
                        }
//                                    unfav.setVisibility(VISIBLE);
//                                    fav.setVisibility(GONE);
                    }
                } catch (Exception e) {
                    imgFav.setVisibility(GONE);
                    imgUnFav.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





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
        imgLink = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photos.get(0).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM";
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
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng);
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
