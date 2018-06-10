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
import android.widget.TextView;

import com.tga.Activity.FoodDetails;
import com.tga.Activity.HomeDetails;
import com.tga.R;
import com.tga.model.PlaceModel;

import java.util.List;

/**
 * Created by Mada on 4/19/2018.
 */

public class foodTypeAdapter extends RecyclerView.Adapter<foodTypeAdapter.MyViewHolder> {

    Context context;
    List<PlaceModel> placeModels;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, price;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.PlImage);
//            price = (TextView) view.findViewById(R.id.price);
            //title = (TextView) view.findViewById(R.id.address);

        }

    }


    public foodTypeAdapter(Context mainActivityContacts, List<PlaceModel> placeModels) {
        this.placeModels = placeModels;
        this.context = mainActivityContacts;
    }



    @Override
    public foodTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_things_todo, parent, false);


        return new foodTypeAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final foodTypeAdapter.MyViewHolder holder, int position) {
        final PlaceModel placeModel = placeModels.get(position);

        holder.title.setText(placeModel.getTitle());
        holder.image.setImageResource(placeModel.getPlImg());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("price","");
                i.putExtra("title","");
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


