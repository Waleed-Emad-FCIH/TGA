package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class PlacesAdapter  extends LoadMoreRecyclerViewAdapter<com.tga.models.PlaceModel> {
    private static final int TYPE_ITEM = 1;
    public   ArrayList<String> checkedPlacesIds = new ArrayList<>();
    Context context;
    String cuurentPlaceItemId ;

    public PlacesAdapter(Context context, ItemClickListener itemClickListener,
                         RetryLoadMoreListener retryLoadMoreListener , ArrayList<String> checkedPlacesIds) {
        super(context, itemClickListener, retryLoadMoreListener);
        this.checkedPlacesIds = checkedPlacesIds;
        this.context=context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_place, parent, false);
            return new PlacesAdapter.ItemViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PlacesAdapter.ItemViewHolder) {

            final place placeModel = mDataList.get(position);

            cuurentPlaceItemId = mDataList.get(position).getPlace_id();
            try {

                if(checkedPlacesIds.contains(cuurentPlaceItemId))
                {
                    ((ItemViewHolder) holder).checkAdd.setChecked(true);

                }
            }catch (Exception e)
            {

            }

            ((PlacesAdapter.ItemViewHolder) holder).title.setText(placeModel.getName());
            List<photos> photos = placeModel.getPhotos();


            if (photos==null && placeModel.getImgLink()!=null) {
                Picasso.with(holder.itemView.getContext())
                        .load(placeModel.getImgLink())
                        .into(((PlacesAdapter.ItemViewHolder) holder).image);

            }else {
                try {
                    Picasso.with(holder.itemView.getContext())
                            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photos.get(0).getPhoto_reference()+"&key=AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                            .into(((PlacesAdapter.ItemViewHolder) holder).image);

                }catch (Exception e){
                    Picasso.with(holder.itemView.getContext())
                            .load("https://d2o57arp16h0eu.cloudfront.net/echo/img/no_image_available.png")
                            .into(((PlacesAdapter.ItemViewHolder) holder).image);
                }
            }

            ((PlacesAdapter.ItemViewHolder) holder).txtRating.setText(String.valueOf(placeModel.getRating()));
            ((PlacesAdapter.ItemViewHolder) holder).rtPlace.setRating(placeModel.getRating());
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
        TextView title, txtRating;
        RatingBar rtPlace;
        CheckBox checkAdd;




        ItemViewHolder(final View itemView) {

            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.PlImage);
            txtRating = (TextView) itemView.findViewById(R.id.txtRating);
            title = (TextView) itemView.findViewById(R.id.txtName);
            rtPlace = (RatingBar)itemView.findViewById(R.id.rtPlace);
            checkAdd = (CheckBox)itemView.findViewById(R.id.checkAdd);
            itemView.setOnClickListener(this);
            try {
                checkAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            Log.v("this id ", cuurentPlaceItemId);
                            Log.v("this id ", mDataList.get(getAdapterPosition()).getPlace_id() + "");
                            try {
                                if (!checkedPlacesIds.contains(mDataList.get(getAdapterPosition()).getPlace_id()))
                                    checkedPlacesIds.add(mDataList.get(getAdapterPosition()).getPlace_id());
                            } catch (Exception e) {
                                checkedPlacesIds.add(mDataList.get(getAdapterPosition()).getPlace_id());

                            }

                        } else {
                            Log.v("hello", "hello");
                        }
                    }
                });
            }
            catch (Exception e)
            {

            }

        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {

                Log.v("this id " , getAdapterPosition()+"");
//                mItemClic kListener.onItemClick(view, getAdapterPosition());
            }
        }

    }

}
