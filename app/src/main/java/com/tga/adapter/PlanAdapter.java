package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tga.Activity.PlanDetalis;
import com.tga.R;
import com.tga.models.PlanModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Mada on 2/8/2018.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
    private boolean flag = false ;
    Context context;
    List<PlanModel> planList;
    GeoDataClient geoDataClient;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMap,imgSite1,imgSite2,imgSite3,arrowUp,arrowDown;
        TextView title,shortInfo;


        public MyViewHolder(View view) {
            
            super(view);

            title = (TextView) view.findViewById(R.id.txtTitle);
            shortInfo = (TextView) view.findViewById(R.id.txtShortInfoPlan);
            imgMap = (ImageView) view.findViewById(R.id.imgMap);
            imgSite1 = (ImageView) view.findViewById(R.id.imgSite1);
            imgSite2 = (ImageView) view.findViewById(R.id.imgSite2);
            imgSite3 = (ImageView) view.findViewById(R.id.imgSite3);
            arrowUp = (ImageView)view.findViewById(R.id.arrowUp);
            arrowDown = (ImageView)view.findViewById(R.id.arrowDown);

        }

    }


    public PlanAdapter(Context mainActivityContacts, List<PlanModel> planList) {
        this.planList = planList;
        this.context = mainActivityContacts;
    }


    @Override
    public PlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan, parent, false);


        return new PlanAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override

        public void onBindViewHolder(final PlanAdapter.MyViewHolder holder, int position) {
        final PlanModel plan = planList.get(position);
        // Request photos and metadata for the specified place.

         List<PlacePhotoMetadata> photosDataList = new ArrayList<>();
        holder.title.setText(plan.getTitle());
        holder.shortInfo.setText("sds");




            for(int i = 0 ;  i <3 ; i++)
            {
                try {
                    Log.v("place _id is =>>" , plan.getPlacesID().get(i)+"");
                    geoDataClient = Places.getGeoDataClient(context, null);
                    final Task<PlacePhotoMetadataResponse> photoResponse = geoDataClient.getPlacePhotos(plan.getPlacesID().get(i));
                    int finalI = i;
                    photoResponse.addOnCompleteListener
                            (new OnCompleteListener<PlacePhotoMetadataResponse>() {
                                @Override
                                public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {

                                    try {
                                        PlacePhotoMetadataResponse photos = task.getResult();
                                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                                        for (PlacePhotoMetadata photoMetadata : photoMetadataBuffer) {
                                            photosDataList.add(photoMetadataBuffer.get(0).freeze());
                                        }
                                    } catch (Exception e) {

                                    }

                                    Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photosDataList.get(0));
                                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                                        @Override
                                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                                            PlacePhotoResponse photo = task.getResult();
                                            switch (finalI) {
                                                case 1:
                                                    holder.imgSite1.setImageBitmap(photo.getBitmap());
                                                    break;
                                                case 2:
                                                    holder.imgSite2.setImageBitmap(photo.getBitmap());
                                                    break;
                                            }


                                        }// on opmplete
                                    });//photoresponse addoncomplete


                                }//on complete
                            });//onComplete listener close
                }catch (Exception c)
                {

                }


            }//for loop close




//        holder.imgSite2.setImageResource();
//        holder.imgSite3.setImageResource(;
//        holder.imgMap.setImageResource(plan.getImgMap());

        holder.arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.arrowDown.setVisibility(View.GONE);
                holder.arrowUp.setVisibility(View.VISIBLE);
            }
        });

        holder.arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.arrowUp.setVisibility(View.GONE);
                holder.arrowDown.setVisibility(View.VISIBLE);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PlanDetalis.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount()
    {
        return planList.size();

    }

//
//
//    private void getPhotoMetadata(String placeId) {
//        GeoDataClient geoDataClient = Places.getGeoDataClient(context, null);
//
//        final Task<PlacePhotoMetadataResponse> photoResponse = geoDataClient.getPlacePhotos(placeId);
//
//        photoResponse.addOnCompleteListener
//                (new OnCompleteListener<PlacePhotoMetadataResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
//
//                        PlacePhotoMetadataResponse photos = task.getResult();
//                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
//
//                        Log.d(TAG, "number of photos "+photoMetadataBuffer.getCount());
//
//                        for(PlacePhotoMetadata photoMetadata : photoMetadataBuffer){
//                            photosDataList.add(photoMetadataBuffer.get(0).freeze());
//                        }
//
//                        photoMetadataBuffer.release();
//
//                    }
//                });
//    }

//    private Bitmap getPhoto(PlacePhotoMetadata photoMetadata ){
//        final Bitmap[] photoi = new Bitmap[3];
//        Log.v("getPhoto" , "here am i");
//
//        Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
//        photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
//                PlacePhotoResponse photo = task.getResult();
//                photoi[0] = photo.getBitmap();
//                Log.d(TAG, "photo "+photo.toString());
//
//            }
//        });
//
//        return photoi[0];
//    }







































    private Bitmap getPhotos(String placeId) {


        final Bitmap[] photoi = new Bitmap[3];
      GeoDataClient mGeoDataClient = Places.getGeoDataClient(context, null);


        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();
                        photoi[0] = bitmap;
                        Log.v("helllo??/>" , photoi[0].toString());
                    }
                });
            }
        });
        return  photoi[0];
    }

}