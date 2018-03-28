package com.tga.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tga.R;
import com.tga.adapter.RecycleAdapter_Offers;
import com.tga.model.Offers;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {

    private String title[]= {"loxour","pyramids","sharm"};

    private String price[]= {"$1,00,000","$1,00,000","$1,00,000","$1,00,000"};
    private int image[]= {R.drawable.loxour,R.drawable.pyramids,R.drawable.sharm};
    private ImageView imgBack;


    private java.util.ArrayList<Offers> ArrayList;
    private RecyclerView recyclerView;
    private RecycleAdapter_Offers mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = (RecyclerView) findViewById(R.id.searched_recycler_view);
        ArrayList = new ArrayList<>();
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        for (int i = 0; i < title.length; i++) {
            Offers beanClassForRecyclerView_contacts = new Offers(title[i],price[i],image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new RecycleAdapter_Offers(getApplicationContext(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
}
