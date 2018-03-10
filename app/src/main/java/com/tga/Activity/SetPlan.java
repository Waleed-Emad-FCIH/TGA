package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ListView;

import com.tga.R;
import com.tga.adapter.PlacesAdapter;
import com.tga.model.PlaceModel;

import java.util.ArrayList;

public class SetPlan extends AppCompatActivity {

    String[] title = {"Tahreer square"   , "Qasr Elneil bridge" , "Cairo Tower" , "Egptian musuem "};
//    String[] descreption = {"Tahrir Square also known as \"Martyr Square\", is a major public town square in Downtown Cairo, Egypt.",
//            "The Qasr El Nil Bridge also commonly spelled Kasr El Nil Bridge, is a historic structure dating to 1931 and replaced the first bridge to span the Nile River in central Cairo, Egypt",
//            "The Cairo Tower is a free-standing concrete tower located in Cairo, Egypt. At 187 m (614 ft), it has been the tallest structure in Egypt and North Africa for about 50 years",
//            "The Museum of Egyptian Antiquities, known commonly as the Egyptian Museum or Museum of Cairo, in Cairo, Egypt, is home to an extensive collection of ancient Egyptian antiquities"
//    };
    Integer[] imgs = {R.drawable.img1, R.drawable.img2, R.drawable.img1, R.drawable.img3} ;
    private ArrayList<PlaceModel> arrayList;
    private RecyclerView recyclerView;
    private PlacesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Your Plan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.set_plan_recyclerview);
        arrayList = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            PlaceModel beanClassForRecyclerView_contacts = new PlaceModel(title[i],imgs[i]);

            arrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new PlacesAdapter(getApplicationContext(),arrayList);

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
