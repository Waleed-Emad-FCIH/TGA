package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.tga.Activity.DiscountDetails;
import com.tga.Controller.AgentController;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.R;

import java.util.List;

/**
 * Created by Mada on 3/1/2018.
 */

public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.MyViewHolder> {

    private Context context;
    private List<ProgramController> progControls;
    GeoDataClient geoDataClient;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDisc;
        TextView title,companyName, discount, txtDiscountEndDate;

        public MyViewHolder(View view) {
            super(view);
            imgDisc = (ImageView)itemView.findViewById(R.id.imgDisc);
            title = (TextView)itemView.findViewById(R.id.title);
            companyName = (TextView)itemView.findViewById(R.id.companyName);
            discount = (TextView) itemView.findViewById(R.id.txtDiscount);
            txtDiscountEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
        }

    }


    public DiscountsAdapter(Context mainActivityContacts,List<ProgramController> progControls) {
        this.context = mainActivityContacts;
        this.progControls = progControls;
    }


    @Override
    public DiscountsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discount, parent, false);



        return new DiscountsAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final DiscountsAdapter.MyViewHolder holder, final int position) {

        final ProgramController progControl = progControls.get(position);
        try {
            final int[] x = {0};
            Handler handler = new Handler();
            final Runnable[] refreshPhoto = new Runnable[1];
            refreshPhoto[0] = new Runnable() {
                @Override
                public void run() {
                    geoDataClient = Places.getGeoDataClient(context, null);
                    geoDataClient.getPlacePhotos(progControl.getPlacesID().get(x[0]++ % progControl.getPlacesID().size())).addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
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
                                                holder.imgDisc.setImageBitmap(bitmap);
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
            if (progControl.getPlacesID().size() > 0)
                handler.post(refreshPhoto[0]);
        }
        catch (Exception e)
        {

        }
        AgentController.getByID(new SimpleCallback<AgentController>() {
            @Override
            public void callback(AgentController data) {
                holder.companyName.setText(data.getName());
                holder.title.setText(progControl.getTitle());
                String s = String.valueOf(progControl.getPrice()) +
                        " (" + String.valueOf(progControl.getDiscountPercentage() * 100) + "%" + ")";
                holder.discount.setText(s);
                holder.txtDiscountEndDate.setText(progControl.getDiscountEndDate());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DiscountDetails.class);
                        intent.putExtra("PROG_ID", progControl.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        }, progControl.getOwnerID());
    }

    @Override
    public int getItemCount() {
        return progControls.size();
    }
}