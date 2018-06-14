package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tga.Activity.PlaceDetails;
import com.tga.R;
import com.tga.model.photos;
import com.tga.model.place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mada on 2/28/2018.
 */

public class PlacesAdapter  extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {
    private static final int TYPE_ITEM = 1;
    public   ArrayList<String> checkedPlacesIds = new ArrayList<>();
    String cuurentPlaceItemId ;
    Context context;
    List<place> placeModels;

    public PlacesAdapter(Context mainActivityContacts, List<place> placeModels,ArrayList<String> checkedPlacesIds) {
        this.placeModels = placeModels;
        this.context = mainActivityContacts;
        this.checkedPlacesIds = checkedPlacesIds;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, txtRating,txtOpenNow;
        RatingBar rtPlace;
        CheckBox checkAdd;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.PlImage);
            txtRating = (TextView) view.findViewById(R.id.txtRating);
            title = (TextView) view.findViewById(R.id.txtName);
            rtPlace = (RatingBar)view.findViewById(R.id.rtPlace);
            checkAdd = (CheckBox)itemView.findViewById(R.id.checkAdd);
//            itemView.setOnClickListener(context);
            checkAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        Log.v("this id " , cuurentPlaceItemId);
                        Log.v("this id " , placeModels.get(getAdapterPosition()).getPlace_id()+"");
                        try {
                            if(!checkedPlacesIds.contains(placeModels.get(getAdapterPosition()).getPlace_id()))
                                checkedPlacesIds.add(placeModels.get(getAdapterPosition()).getPlace_id());
                        }
                        catch (Exception e)
                        {
                            checkedPlacesIds.add(placeModels.get(getAdapterPosition()).getPlace_id());

                        }

                    }
                    else {
                        Log.v("hello" , "hello");
                    }
                }
            });
        }

    }


    @Override
    public PlacesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);


        return new PlacesAdapter.MyViewHolder(itemView);


    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final PlacesAdapter.MyViewHolder holder, int position) {

        if (holder instanceof PlacesAdapter.MyViewHolder) {

            final place placeModel = placeModels.get(position);

            cuurentPlaceItemId = placeModels.get(position).getPlace_id();
            try {

                if(checkedPlacesIds.contains(cuurentPlaceItemId))
                {
                    ((MyViewHolder) holder).checkAdd.setChecked(true);

                }
            }catch (Exception e)
            {

            }

            ((PlacesAdapter.MyViewHolder) holder).title.setText(placeModel.getName());
            List<photos> photos = placeModel.getPhotos();


            if (photos==null && placeModel.getImgLink()!=null) {
                Picasso.with(holder.itemView.getContext())
                        .load(placeModel.getImgLink())
                        .into(((PlacesAdapter.MyViewHolder) holder).image);

            }else {
                try {
                    Picasso.with(holder.itemView.getContext())
                            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photos.get(0).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                            .into(((PlacesAdapter.MyViewHolder) holder).image);

                }catch (Exception e){
                    Picasso.with(holder.itemView.getContext())
                            .load("https://d2o57arp16h0eu.cloudfront.net/echo/img/no_image_available.png")
                            .into(((PlacesAdapter.MyViewHolder) holder).image);
                }
            }

            ((PlacesAdapter.MyViewHolder) holder).txtRating.setText(String.valueOf(placeModel.getRating()));
            ((PlacesAdapter.MyViewHolder) holder).rtPlace.setRating(placeModel.getRating());
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

    }


    @Override
    public int getItemCount() {
        return placeModels.size();
    }


}
