package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;

import com.tga.R;
import com.tga.adapter.DiscountsAdapter;
import com.tga.adapter.SearchAdapter;
import com.tga.model.*;

import java.util.ArrayList;

public class Discounts extends AppCompatActivity {

    private int image[]= {R.drawable.room1,R.drawable.room2,R.drawable.room4,R.drawable.room5,R.drawable.room1};
    private String title[]= {"special offer for new married","new offer to you","offer offer offer","special offer for new married","new offer to you"};
    private String offer[]= {"Up to 10%","15% off","10$ Discounts","Up to 10%","15% off"};
    private String companyName[]= {"blabla","coco","HamadaTourists","Your Guide","HELMT"};
    private java.util.ArrayList<DiscountsModel> ArrayList;
    private RecyclerView recyclerView;
    private DiscountsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Discounts");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.reDiscounts);
        ArrayList = new ArrayList<>();



        for (int i = 0; i < image.length; i++) {
            DiscountsModel beanClassForRecyclerView_contacts = new DiscountsModel(offer[i],title[i],companyName[i],image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new DiscountsAdapter(getApplicationContext(),ArrayList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
