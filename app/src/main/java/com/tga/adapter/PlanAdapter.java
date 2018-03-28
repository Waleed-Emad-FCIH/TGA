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
import com.tga.Activity.PlanDetalis;
import com.tga.R;
import com.tga.model.Plan;

import java.util.List;

/**
 * Created by Mada on 2/8/2018.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {

    Context context;
    List<Plan> planList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMap,imgSite1,imgSite2,imgSite3,arrowUp,arrowDown;
        TextView title,shortInfo;


        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.txtTitle);
            shortInfo = (TextView) view.findViewById(R.id.txtShortInfoPlan);
            imgMap = (ImageView) view.findViewById(R.id.imgMap);
            imgSite1 = (ImageView) view.findViewById(R.id.imgSite1);
            imgSite2 = (ImageView) view.findViewById(R.id.imgSite2);
            imgSite3 = (ImageView) view.findViewById(R.id.imgSite3);
            arrowUp = (ImageView)view.findViewById(R.id.arrowUp);
            arrowDown = (ImageView)view.findViewById(R.id.arrowDown);

        }

    }


    public PlanAdapter(Context mainActivityContacts, List<Plan> planList) {
        this.planList = planList;
        this.context = mainActivityContacts;
    }


    @Override
    public PlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan, parent, false);


        return new PlanAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final PlanAdapter.MyViewHolder holder, int position) {
        final Plan plan = planList.get(position);


        holder.title.setText(plan.getTitle());
        holder.shortInfo.setText(plan.getShortInfo()+"/n"+plan.getSites());
        holder.imgSite1.setImageResource(plan.getImgSite1());
        holder.imgSite2.setImageResource(plan.getImgSite2());
        holder.imgSite3.setImageResource(plan.getImgSite3());
        holder.imgMap.setImageResource(plan.getImgMap());

        holder.arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.arrowDown.setVisibility(View.GONE);
                holder.arrowUp.setVisibility(View.VISIBLE);
            }
        });

        holder.arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.arrowUp.setVisibility(View.GONE);
                holder.arrowDown.setVisibility(View.VISIBLE);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PlanDetalis.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return planList.size();
    }
}