package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tga.Activity.MapViewActivity;
import com.tga.Activity.PlanDetalis;
import com.tga.R;
import com.tga.models.PlanModel;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Mada on 2/8/2018.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
    private boolean flag = false ;
    Context context;
    private GoogleMap mMap;
    List<PlanModel> planList;
    List<PlacePhotoMetadata> photosDataList = new ArrayList<>();
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

    public Bitmap getMapImage(MyViewHolder holder, String STATIC_MAP_API_ENDPOINT)
    {



        Log.d("STATICMAPS", STATIC_MAP_API_ENDPOINT);

        AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>(){
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                                Bitmap bmp = null;
                                HttpClient httpclient = new DefaultHttpClient();
                                HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT);

                                InputStream in = null;
                                try {
                                HttpResponse response = httpclient.execute(request);
                                in = response.getEntity().getContent();
                                bmp = BitmapFactory.decodeStream(in);
                                in.close();
                                }
                                catch (Exception e) {
                                e.printStackTrace();
                                }
                        return bmp;
                            }
                        protected void onPostExecute(Bitmap bmp) {
                                if (bmp!=null) {

                                holder.imgMap.setImageBitmap(bmp);


                                }

                        }
        };

        setImageFromUrl.execute();


        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final PlanAdapter.MyViewHolder holder, int position) {
        final String[] STATIC_MAP_API_ENDPOINT = {"http://maps.googleapis.com/maps/api/staticmap?size=230x200"};
        final PlanModel plan = planList.get(position);
        holder.title.setText(plan.getTitle());
        holder.shortInfo.setText("Location : " + plan.getLocation()+" " + plan.getDescription());
        // Request photos and metadata for the specified place.
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for(int i = 0 ;  i <plan.getPlacesID().size() ; i++) {
            //////////map image//////////////////
            try {
                geoDataClient = Places.getGeoDataClient(context, null);
                final int[] finalI2 = {i};
                geoDataClient.getPlaceById(plan.getPlacesID().get(i)).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        PlaceBufferResponse places = task.getResult();
                        Place myPlace = places.get(0);

                        int order = finalI2[0] + 1;
                        String ad = "color:orange|label:" + order + "|" + myPlace.getAddress();
                        try {
                            ad = URLEncoder.encode(ad, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        STATIC_MAP_API_ENDPOINT[0] = STATIC_MAP_API_ENDPOINT[0] + "&markers=" + ad;
                        latLngs.add(myPlace.getLatLng());
                        //Log.v("sssss123" , adresses.size()+"");
                        places.release();
                        //
                    } else {
                        //
                        //Log.e(TAG, "Place not found.");
                    }
                    if (finalI2[0] == plan.getPlacesID().size() - 1) {

                        getMapImage(holder, STATIC_MAP_API_ENDPOINT[0]);
                    }

                });


            } catch (Exception e) {

            }
            ///////////Map image Eng///////////////

            int finalI = i;
            geoDataClient.getPlacePhotos(plan.getPlacesID().get(i)).addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
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
                    Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            Bitmap bitmap = photo.getBitmap();
                            Log.v("am in 111111", "" + finalI);
                            switch (finalI) {
                                case 0:
                                    holder.imgSite1.setImageBitmap(bitmap);
                                    break;
                                case 1:
                                    holder.imgSite2.setImageBitmap(bitmap);
                                    break;
                                case 2:
                                    holder.imgSite3.setImageBitmap(bitmap);
                                    break;
                            }
                            // Log.v("helllo??/>" , photoi[0].toString());
                        }
                    });
                }
            });
            ///Set imgs for places//

        }///for loop close

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
                i.putExtra("placesIds" , plan.getPlacesID());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(context , MapViewActivity.class);
            i.putExtra("latLngs" , latLngs);
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
                       // Log.v("helllo??/>" , photoi[0].toString());
                    }
                });
            }
        });
        return  photoi[0];
    }

}