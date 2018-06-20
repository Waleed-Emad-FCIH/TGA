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

import com.tga.R;
import com.tga.models.Search;

import java.util.List;

/**
 * Created by Mada on 2/8/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;
    List<Search> searchList;


    private String getMyClass(int pos)
    {
        String myClass = null;
        switch (pos)
        {
            case 0: myClass = "com.tga.Activity.Reservations";
                break;
            case 1: myClass = "com.tga.Activity.ThingsToDo";
                break;
            case 2: myClass = "com.tga.Activity.FoodAndDrinks";
                break;
            case 3: myClass = "com.tga.Activity.Discounts";
                break;
            case 4: myClass = "com.tga.Activity.GettingAround";
                break;
            case 5: myClass = "com.tga.Activity.NeedToKnow";
                break;
            //...
            default: // do whatever is appropriate
        }
        return myClass;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView)itemView.findViewById(R.id.imgSearch);
        }

    }


    public SearchAdapter(Context mainActivityContacts,List<Search> searchList) {
        this.context = mainActivityContacts;
        this.searchList = searchList;
    }


    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);



        return new SearchAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final SearchAdapter.MyViewHolder holder, final int position) {

        final Search search = searchList.get(position);
        holder.img.setImageResource(search.getImg());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String SECONDPAGE_CLASS = getMyClass(position);
                try
                {
                    Intent myint = new Intent(context, Class.forName(SECONDPAGE_CLASS));
                    myint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myint);
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
//                Intent i = new Intent(context, GettingAround.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
            }
        });


//        holder.img.setOnClickListener();

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
}