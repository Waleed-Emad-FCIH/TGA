package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.tga.R;
import com.tga.adapter.FoodAndDrinksAdapter;
import com.tga.adapter.ReservationsAdapter;
import com.tga.model.HotelMdoel;
import com.tga.model.PlaceModel;

import java.util.ArrayList;

public class Reservations extends AppCompatActivity {

    private ImageView imgFilter;
    private String title[]= {"STANDARD KING","CITY VIEW","DELUXE CITY VIEW","STANDARD KING"};

    private int image[]= {R.drawable.room1,R.drawable.room2,R.drawable.room4,R.drawable.room5};


    private ArrayList<HotelMdoel> arrayList;
    private RecyclerView recyclerView;
    private ReservationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reservation");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.reservation_recycle);
        arrayList = new ArrayList<>();


        for (int i = 0; i < title.length; i++) {
            HotelMdoel beanClassForRecyclerView_contacts = new HotelMdoel(title[i],image[i]);

            arrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new ReservationsAdapter(getApplicationContext(),arrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        imgFilter = (ImageView)findViewById(R.id.imgFilter);
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReservationsFilter.class);
                startActivity(intent);
            }
        });

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
