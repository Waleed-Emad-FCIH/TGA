package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.tga.R;
import com.tga.adapter.RecycleAdapter_Offers;
import com.tga.model.Offers;

import java.util.ArrayList;

public class MyPrograms extends AppCompatActivity {

    private String title[]= {"loxour","pyramids","sharm"};

    private String price[]= {"$1,00,000","$1,00,000","$1,00,000","$1,00,000"};
    private int image[]= {R.drawable.loxour,R.drawable.pyramids,R.drawable.sharm};


    private java.util.ArrayList<Offers> ArrayList;
    private RecyclerView recyclerView;
    private RecycleAdapter_Offers mAdapter;
    private FloatingActionButton fabAddProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_programs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Programs");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.myPrograms_recyclerview);
        fabAddProgram = (FloatingActionButton)findViewById(R.id.fabAddProgram);
        ArrayList = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            Offers beanClassForRecyclerView_contacts = new Offers(title[i],price[i],image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new RecycleAdapter_Offers(getApplicationContext(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        fabAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProgram.class);
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
