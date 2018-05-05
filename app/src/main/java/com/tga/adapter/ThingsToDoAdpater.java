package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.tga.Activity.HomeDetails;
import com.tga.Activity.PlaceDetails;
import com.tga.R;
import com.tga.model.photos;
import com.tga.model.place;

import java.util.List;

/**
 * Created by Mada on 4/19/2018.
 */

public class ThingsToDoAdpater extends RecyclerView.Adapter<ThingsToDoAdpater.MyViewHolder> {

    Context context;
    List<place> placeModels;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, txtRating,txtOpenNow;
        RatingBar rtPlace;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.PlImage);
            txtRating = (TextView) view.findViewById(R.id.txtRating);
            title = (TextView) view.findViewById(R.id.txtName);
            rtPlace = (RatingBar)view.findViewById(R.id.rtPlace);
//            txtOpenNow = (TextView)view.findViewById(R.id.txtOpenNow);

        }

    }


    public ThingsToDoAdpater(Context mainActivityContacts, List<place> placeModels) {
        this.placeModels = placeModels;
        this.context = mainActivityContacts;
    }



    @Override
    public ThingsToDoAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_things_todo, parent, false);


        return new ThingsToDoAdpater.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ThingsToDoAdpater.MyViewHolder holder, int position) {
        final place placeModel = placeModels.get(position);

        holder.title.setText(placeModel.getName());
        List<photos> photos = placeModel.getPhotos();


        try {
            Picasso.with(holder.itemView.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photos.get(0).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                    .into(holder.image);
        }catch (Exception e){
            Picasso.with(holder.itemView.getContext())
                    .load("https://d2o57arp16h0eu.cloudfront.net/echo/img/no_image_available.png")
                    .into(holder.image);
        }

//        Glide.with(holder.itemView.getContext())
//                .load(placeModel.getPhoto_reference())
//                .into(holder.image);
        holder.txtRating.setText(String.valueOf(placeModel.getRating()));
        holder.rtPlace.setRating(placeModel.getRating());


//        if (opening_hours == null) {
//            holder.txtOpenNow.setText("");
//        }
//        else if (opening_hours.get(0).isOpen_now()){
//            holder.txtOpenNow.setText("Open");
//        }else {
//            holder.txtOpenNow.setText("Closed");
//        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, PlaceDetails.class);
                i.putExtra("id",placeModel.getPlace_id());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }


}


