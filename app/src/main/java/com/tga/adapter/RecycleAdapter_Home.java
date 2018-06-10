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
import com.tga.Activity.ProgDetails;
import com.tga.Controller.ProgramController;
import com.tga.R;
import com.tga.model.Offers;

import java.util.List;



/**
 * Created by Rp on 6/14/2016.
 */
public class RecycleAdapter_Home extends RecyclerView.Adapter<RecycleAdapter_Home.MyViewHolder> {

    private Context context;
    private List<ProgramController> list;
    private String source;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, price;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            price = (TextView) view.findViewById(R.id.price);
            title = (TextView) view.findViewById(R.id.title);

        }

    }


    public RecycleAdapter_Home(Context mainActivityContacts, List<ProgramController> list, String source) {
        this.list = list;
        this.context = mainActivityContacts;
        this.source = source;
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
        final ProgramController pc = list.get(position);

        holder.price.setText(String.valueOf(pc.getPrice()));
        holder.title.setText(pc.getTitle());
        holder.image.setImageResource(R.drawable.loxour); //TODO: places images


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = null;
                if (source.equals("Home"))
                    i = new Intent(context, HomeDetails.class);
                else if (source.equals("MyPrograms"))
                    i = new Intent(context, ProgDetails.class);
                i.putExtra("PROG_ID",pc.getId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}


