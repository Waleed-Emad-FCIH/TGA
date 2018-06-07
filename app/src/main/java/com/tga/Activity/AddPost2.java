package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tga.R;

public class AddPost2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Post");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));


    }
}
