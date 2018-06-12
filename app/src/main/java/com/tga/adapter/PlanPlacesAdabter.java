package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Abdulrhman   on 6/4/2018.
 */

public class PlanPlacesAdabter extends RecyclerView.Adapter<PlanPlacesAdabter.viewHolder>  {

    private  ArrayList<String> placeIds = new ArrayList<>();
    private int itemLayout;
    private  Context context ;
    private GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private RequestInterface request;
    private java.util.ArrayList<placeDetailsModel> arrayPlaceDetails;
    private ArrayList<placeDetailsModel> placeDetailsModels;
    private String imgLink = "";

    public static class viewHolder extends RecyclerView.ViewHolder{


        private SliderLayout mDemoSlider;
        private TextView txtPlaceName ;
        private TextView txtRate;
        private RatingBar rtPlace;
        private TextView txtOpeningDays;
        private ImageView imgMap;
        private TextView txtWebsite;
        private TextView txtPlacePhone;
        private ImageView imgFav , imgUnFav;
        public viewHolder(View itemView) {
            super(itemView);
            mDemoSlider = (SliderLayout) itemView.findViewById(R.id.slider);
            txtPlaceName = itemView.findViewById(R.id.txtPlaceName);
            txtRate =itemView.findViewById(R.id.txtRate);
            rtPlace =itemView.findViewById(R.id.rtPlace);
            txtOpeningDays = itemView.findViewById(R.id.txtOpeningDays);
            imgMap = itemView.findViewById(R.id.imgMap);
            txtWebsite = itemView.findViewById(R.id.txtWebsite);
            txtPlacePhone = itemView.findViewById(R.id.txtPlacePhone);
            imgFav = itemView.findViewById(R.id.imgFav);
            imgUnFav = itemView.findViewById(R.id.imgUnFav);
            imgUnFav.setVisibility(VISIBLE);
            imgFav.setVisibility(GONE);
            imgUnFav.invalidate();
            imgFav.invalidate();

        }
    }


    public PlanPlacesAdabter (ArrayList<String> placesIds, int item  , Context c )
    {
        this.placeIds = placesIds;
        Log.v("cons" ,placesIds.size()+"" );
        itemLayout = item;
        this.context = c ;


    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_datails,parent, false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Log.v("in tha fun" , "in the di");
        String id = placeIds.get(position);
        arrayPlaceDetails = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(165);

        //Action on favorite Image
        holder.imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgFav.setVisibility(GONE);
                holder.imgUnFav.startAnimation(animation);
                holder.imgUnFav.setVisibility(VISIBLE);
                Firebase firebase=new Firebase("https://tguidea-86215.firebaseio.com/favourites/"+uid+id);
                firebase.removeValue();

            }
        });//end of actiion (imgFav.setOnClickListener)

        holder.imgUnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgUnFav.setVisibility(GONE);
                holder.imgFav.startAnimation(animation);
                holder.imgFav.setVisibility(VISIBLE);
                place place = new place();
                place.setName(holder.txtPlaceName.getText().toString());
                place.setRating(Float.parseFloat(holder.txtRate.getText().toString()));
                place.setPlace_id(id);
                place.setUid(uid);
                place.setImgLink(imgLink);
                mDatabase.child("favourites").child(uid+id).setValue(place);
            }
        });//end of actiion (imgunFav.setOnClickListener)

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
                            holder.imgFav.setVisibility(VISIBLE);
                            holder.imgUnFav.setVisibility(GONE);
                            break;
                        } else {
                            holder.imgFav.setVisibility(GONE);
                            holder.imgUnFav.setVisibility(VISIBLE);
                        }
//                                    unfav.setVisibility(VISIBLE);
//                                    fav.setVisibility(GONE);
                    }
                } catch (Exception e) {
                    holder.imgFav.setVisibility(GONE);
                    holder.imgUnFav.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /////////////////////
        mGeoDataClient = Places.getGeoDataClient(context, null);


        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(context, null);



        Log.v("onb" , "dddd");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/details/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        loadJSON(holder, request,request.getPlaceDetails(id,"AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM"));





    }

    private void lun(ArrayList<placeDetailsModel> placeDetails ,viewHolder holder ){
        placeDetailsModels = placeDetails;
        geometry locations =placeDetailsModels.get(0).getGeometry();
        String name =placeDetailsModels.get(0).getName();
        List<photos> photos =placeDetailsModels.get(0).getPhotos();
        try {
            imgLink = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photos.get(0).getPhoto_reference() + "&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM";
        }
        catch (Exception e){

        }
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

        holder.txtPlaceName.setText(name);
        holder.txtRate.setText(String.valueOf(rate));
        holder.rtPlace.setRating(rate);

        final String web = placeDetailsModels.get(0).getWebsite();
        String phone = placeDetailsModels.get(0).getInternational_phone_number();

        if (web!=null){
            holder.txtWebsite.setText(web);
            holder.txtWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
//                    startActivity(browserIntent);
                }
            });
        }else {
            holder.txtWebsite.setText("No web site");
        }


        if (phone!=null){
            holder.txtPlacePhone.setText(phone);
        }else {
            holder.txtPlacePhone.setText("No phone number");
        }





        holder.txtOpeningDays.setText(open);



        holder.imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
            }
        });

        for(int i = 0;i<3;i++){
            TextSliderView textSliderView = new TextSliderView(context);
            // initialize a SliderLayout
            try {


                textSliderView
                        .image("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photos.get(i).getPhoto_reference() + "&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                        .setScaleType(BaseSliderView.ScaleType.Fit);


                textSliderView.bundle(new Bundle());
                // textSliderView.getBundle().putString("extra",name);
            }
            catch (Exception e)
            {

            }

            holder.mDemoSlider.addSlider(textSliderView);
        }
        holder.mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        holder.mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        holder.mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        holder.mDemoSlider.setDuration(4000);
       // holder.mDemoSlider.addOnPageChangeListener(this);



    }


    private void loadJSON(viewHolder holder , RequestInterface request, Call<PlaceDetailsResponse> getJSON) {
        Call<PlaceDetailsResponse> call = getJSON;
//        ArrayList = new ArrayList<>();
        call.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {

                PlaceDetailsResponse jsonResponse = response.body();
                arrayPlaceDetails = new ArrayList<>(Arrays.asList(jsonResponse.getResult()));
//                placeDetailsModel[] photos =jsonResponse.getResult()
                lun(arrayPlaceDetails  , holder);
            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeIds.size();
    }




}
