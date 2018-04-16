package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.tga.R;
import com.tga.adapter.PlacesAdapter;
import com.tga.model.PlaceModel;

import java.util.ArrayList;

public class Plans extends AppCompatActivity {
RadioButton rbOnlyOneDay,rbMakeYourProgram;

    String[] title = {"Tahreer square"   , "Qasr Elneil bridge" , "Cairo Tower" , "Egptian musuem "};
//    String[] descreption = {"Tahrir Square also known as \"Martyr Square\", is a major public town square in Downtown Cairo, Egypt.",
//            "The Qasr El Nil Bridge also commonly spelled Kasr El Nil Bridge, is a historic structure dating to 1931 and replaced the first bridge to span the Nile River in central Cairo, Egypt",
//            "The Cairo Tower is a free-standing concrete tower located in Cairo, Egypt. At 187 m (614 ft), it has been the tallest structure in Egypt and North Africa for about 50 years",
//            "The Museum of Egyptian Antiquities, known commonly as the Egyptian Museum or Museum of Cairo, in Cairo, Egypt, is home to an extensive collection of ancient Egyptian antiquities"
//    };
    Integer[] imgs = {R.drawable.img1, R.drawable.img2, R.drawable.img1, R.drawable.img3} ;
    private ArrayList<PlaceModel> Places;
    private RecyclerView recyclerView;
    private PlacesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Your Plan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));
         //=====================  " imbo Code " ====================
                rbOnlyOneDay = (RadioButton) findViewById(R.id.rbOnleOneDay);
                rbMakeYourProgram = (RadioButton) findViewById(R.id.rbMakeYourProgram);
        View.OnClickListener first_radio_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        View.OnClickListener second_radio_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        rbOnlyOneDay.setOnClickListener(first_radio_listener);
        rbMakeYourProgram.setOnClickListener(second_radio_listener);


       //===========================================================

        recyclerView = (RecyclerView) findViewById(R.id.set_plan_recyclerview);
        Places = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            PlaceModel beanClassForRecyclerView_contacts = new PlaceModel(title[i],imgs[i]);

            Places.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new PlacesAdapter(getApplicationContext(),Places);

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
