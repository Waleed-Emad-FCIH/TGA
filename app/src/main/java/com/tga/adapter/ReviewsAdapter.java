package com.tga.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tga.R;
import com.tga.model.reviews;


import java.util.ArrayList;



public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.RecyclerViewHolder> {

    private ArrayList<reviews> results;
    Context context;



    public ReviewsAdapter(ArrayList<reviews> results, Context context){
        this.context=context;
        this.results = results;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new RecyclerViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position)  {

        holder.Name.setText(results.get(position).getAuthor_name());
        holder.Message.setText(results.get(position).getText());
        holder.Rate.setRating(results.get(position).getRating());



    }


    @Override
    public int getItemCount() {
        return results.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

        public TextView Name;
        public TextView Message;
        public RatingBar Rate;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            Name = (TextView)itemView.findViewById(R.id.txtauthorName);
            Message = (TextView) itemView.findViewById(R.id.txtReviewMessage);
            Rate=(RatingBar) itemView.findViewById(R.id.reviewRatingBar);

        }
    }


}
