package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tga.R;

public class AddPlan extends AppCompatActivity {


    private ImageView imgAddPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create your plan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        imgAddPlaces =(ImageView)findViewById(R.id.imgAddPlaces);
        imgAddPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.tga.Activity.Plans.class);
                startActivity(intent);
            }
        });
    }
}
