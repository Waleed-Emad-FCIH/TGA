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

import com.tga.Activity.DiscountsDetails;
import com.tga.R;
import com.tga.model.DiscountsModel;

import java.util.List;

/**
 * Created by Mada on 3/1/2018.
 */

public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.MyViewHolder> {

    Context context;
    List<DiscountsModel> discountsModels;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDisc;
        TextView title,offer,companyName;


        public MyViewHolder(View view) {
            super(view);
            imgDisc = (ImageView)itemView.findViewById(R.id.imgDisc);
            title = (TextView)itemView.findViewById(R.id.title);
            offer = (TextView)itemView.findViewById(R.id.offer);
            companyName = (TextView)itemView.findViewById(R.id.companyName);
        }

    }


    public DiscountsAdapter(Context mainActivityContacts,List<DiscountsModel> discountsModels) {
        this.context = mainActivityContacts;
        this.discountsModels = discountsModels;
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

        final DiscountsModel discountsModel = discountsModels.get(position);
        holder.imgDisc.setImageResource(discountsModel.getOfferImg());
        holder.companyName.setText(discountsModel.getCompanyName());
        holder.offer.setText(discountsModel.getOffer());
        holder.title.setText(discountsModel.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiscountsDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return discountsModels.size();
    }
}