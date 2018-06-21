package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
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
import com.tga.Activity.HomeDetails;
import com.tga.Activity.ProgDetails;
import com.tga.Controller.ProgramController;
import com.tga.R;

import java.util.List;



/**
 * Created by Rp on 6/14/2016.
 */
public class RecycleAdapter_Home extends RecyclerView.Adapter<RecycleAdapter_Home.MyViewHolder> {

    private Context context;
    private List<ProgramController> list;
    private String source;
    GeoDataClient geoDataClient;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, price;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            price = (TextView) view.findViewById(R.id.price);
            title = (TextView) view.findViewById(R.id.title);

        }

    }


    public RecycleAdapter_Home(Context mainActivityContacts, List<ProgramController> list, String source) {
        this.list = list;
        this.context = mainActivityContacts;
        this.source = source;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);


        return new MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProgramController pc = list.get(position);

        holder.price.setText(String.valueOf(pc.getPrice()));
        holder.title.setText(pc.getTitle());
        //Add photo
        try {
            final int[] x = {0};
            Handler handler = new Handler();
            final Runnable[] refreshPhoto = new Runnable[1];
            refreshPhoto[0] = new Runnable() {
                @Override
                public void run() {
                    geoDataClient = Places.getGeoDataClient(context, null);
                    geoDataClient.getPlacePhotos(pc.getPlacesID().get(x[0]++ % pc.getPlacesID().size())).addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                            // Get the list of photos.
                            PlacePhotoMetadataResponse photos = task.getResult();
                            // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                            PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                            // Get the first photo in the list.
                            try {

                                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                                // Get the attribution text.
                                CharSequence attribution = photoMetadata.getAttributions();
                                // Get a full-size bitmap for the photo.
                                try {
                                    Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                                        @Override
                                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                                            try {
                                                PlacePhotoResponse photo = task.getResult();
                                                Bitmap bitmap = photo.getBitmap();
                                                holder.image.setImageBitmap(bitmap);
                                            }
                                            catch (Exception e)
                                            {
                                                System.out.println("bitmap");
                                            }
                                            // Log.v("helllo??/>" , photoi[0].toString());
                                        }

                                    });
                                } catch (Exception e) {
                                    System.out.println("Task");
                                } finally {
                                    photoMetadataBuffer.release();
                                }
                            } catch (Exception e) {
                                System.out.println("PhotoMetaData");
                            }
                        }
                    });
                    handler.postDelayed(refreshPhoto[0], 5000);
                }
            };
            if (pc.getPlacesID().size() > 0)
                handler.post(refreshPhoto[0]);
        }
        catch (Exception e)
        {

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = null;
                if (source.equals("Home"))
                    i = new Intent(context, HomeDetails.class);
                else if (source.equals("MyPrograms"))
                    i = new Intent(context, ProgDetails.class);
                i.putExtra("PROG_ID",pc.getId());
                i.putExtra("user_id",pc.getOwnerID());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}


