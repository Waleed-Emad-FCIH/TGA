package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tga.Activity.PlaceDetails;
import com.tga.R;
import com.tga.models.photos;
import com.tga.models.place;

import java.util.List;

/**
 * Created by Mada on 5/29/2018.
 */

public class ThingsToDoLoad extends LoadMoreRecyclerViewAdapter<place> {
    private static final int TYPE_ITEM = 1;

    Context context;


    public ThingsToDoLoad(Context context, ItemClickListener itemClickListener,
                          RetryLoadMoreListener retryLoadMoreListener) {
        super(context, itemClickListener, retryLoadMoreListener);
        this.context=context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_things_todo, parent, false);
            return new ItemViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
             final place placeModel = mDataList.get(position);
            ((ItemViewHolder) holder).title.setText(placeModel.getName());
            List<photos> photos = placeModel.getPhotos();

            if (photos==null && placeModel.getImgLink()!=null) {
                Picasso.with(holder.itemView.getContext())
                        .load(placeModel.getImgLink())
                        .into(((ItemViewHolder) holder).image);

            }else {
                try {
                    Picasso.with(holder.itemView.getContext())
                            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photos.get(0).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                            .into(((ItemViewHolder) holder).image);

                }catch (Exception e){
                    Picasso.with(holder.itemView.getContext())
                            .load("https://d2o57arp16h0eu.cloudfront.net/echo/img/no_image_available.png")
                            .into(((ItemViewHolder) holder).image);
                }
            }


//        Glide.with(holder.itemView.getContext())
//                .load(placeModel.getPhoto_reference())
//                .into(holder.image);
            ((ItemViewHolder) holder).txtRating.setText(String.valueOf(placeModel.getRating()));
            ((ItemViewHolder) holder).rtPlace.setRating(placeModel.getRating());


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
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected int getCustomItemViewType(int position) {
        return TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title, txtRating,txtOpenNow;
        RatingBar rtPlace;

        ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.PlImage);
            txtRating = (TextView) itemView.findViewById(R.id.txtRating);
            title = (TextView) itemView.findViewById(R.id.txtName);
            rtPlace = (RatingBar)itemView.findViewById(R.id.rtPlace);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}