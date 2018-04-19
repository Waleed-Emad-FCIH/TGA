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

import com.tga.Activity.ReservationsDetails;
import com.tga.R;
import com.tga.model.HotelMdoel;

import java.util.List;

/**
 * Created by Mada on 3/1/2018.
 */

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.MyViewHolder> {

    Context context;
    List<HotelMdoel> hotelMdoels;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.reservationImg);
//            price = (TextView) view.findViewById(R.id.price);
            title = (TextView) view.findViewById(R.id.reservationName);

        }

    }


    public ReservationsAdapter(Context mainActivityContacts, List<HotelMdoel> hotelMdoels) {
        this.hotelMdoels = hotelMdoels;
        this.context = mainActivityContacts;
    }



    @Override
    public ReservationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hotel, parent, false);


        return new ReservationsAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ReservationsAdapter.MyViewHolder holder, int position) {
        final HotelMdoel hotelMdoel = hotelMdoels.get(position);

        holder.title.setText(hotelMdoel.getReservationName());
        holder.image.setImageResource(hotelMdoel.getReservationImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReservationsDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hotelMdoels.size();
    }


}


