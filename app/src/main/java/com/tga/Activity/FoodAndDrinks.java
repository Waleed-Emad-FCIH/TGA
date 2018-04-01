package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.tga.R;
import com.tga.adapter.FoodAndDrinksAdapter;
import com.tga.adapter.PlacesAdapter;
import com.tga.model.PlaceModel;

import java.util.ArrayList;

public class FoodAndDrinks extends AppCompatActivity {

    private String title[]= {"Restaurants","Cafes","Bars"};

    private int image[]= {R.drawable.restaurants,R.drawable.cafes,R.drawable.bars};


    private ArrayList<PlaceModel> arrayList;
    private RecyclerView recyclerView;
    private FoodAndDrinksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_and_drinks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food And Drinks");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.food_recyclerview);
        arrayList = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            PlaceModel beanClassForRecyclerView_contacts = new PlaceModel(title[i],image[i]);

            arrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new FoodAndDrinksAdapter(getApplicationContext(),arrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
