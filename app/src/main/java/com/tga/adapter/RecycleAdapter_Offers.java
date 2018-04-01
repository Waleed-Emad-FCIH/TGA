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

import com.tga.Activity.HomeDetails;
import com.tga.R;
import com.tga.model.Offers;

import java.util.List;



/**
 * Created by Rp on 6/14/2016.
 */
public class RecycleAdapter_Offers extends RecyclerView.Adapter<RecycleAdapter_Offers.MyViewHolder> {

    Context context;
    List<Offers> OffersList;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, price;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            price = (TextView) view.findViewById(R.id.price);
            title = (TextView) view.findViewById(R.id.address);

        }

    }


    public RecycleAdapter_Offers(Context mainActivityContacts, List<Offers> OffersList) {
        this.OffersList = OffersList;
        this.context = mainActivityContacts;
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
        final Offers offers = OffersList.get(position);

        holder.price.setText(offers.getPrice());
        holder.title.setText(offers.getTitle());
        holder.image.setImageResource(offers.getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, HomeDetails.class);
                i.putExtra("price",offers.getPrice());
                i.putExtra("title",offers.getTitle());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return OffersList.size();
    }


}


