package com.tga.Activity;

import android.content.Intent;
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

        imgAddPlaces =(ImageView)findViewById(R.id.imgAddPlaces);
        imgAddPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPlan.this,Places.class);
                startActivity(intent);
            }
        });
    }
}
