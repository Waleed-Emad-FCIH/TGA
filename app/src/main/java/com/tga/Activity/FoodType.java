package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tga.R;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.adapter.foodTypeAdapter;
import com.tga.model.PlaceModel;

import java.util.ArrayList;

public class FoodType extends AppCompatActivity {

    private String title[]= {"loxour","pyramids","sharm"};
    private int image[]= {R.drawable.loxour,R.drawable.pyramids,R.drawable.sharm};


    private java.util.ArrayList<PlaceModel> ArrayList;
    private RecyclerView recyclerView;
    private foodTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food Type");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView)findViewById(R.id.food_type_recyclerview);
        ArrayList = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            PlaceModel beanClassForRecyclerView_contacts = new PlaceModel(title[i],image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new foodTypeAdapter(getApplicationContext(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
