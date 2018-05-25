package com.tga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.tga.R;

import java.util.ArrayList;

public class HomeDetails extends AppCompatActivity  {

    private TextView price, title;
    private ImageView imgBack;
    private String strprice, strtitle, strimage;
    private ImageView imgProgramEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        strprice = i.getStringExtra("price");
        strtitle = i.getStringExtra("title");
        strimage = i.getStringExtra("image");

        price = (TextView) findViewById(R.id.txtPrice);
        title = (TextView) findViewById(R.id.txtTitle);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgProgramEdit = (ImageView)findViewById(R.id.imgProgramEdit);

        price.setText(strprice);
        title.setText(strtitle);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgProgramEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditProgram.class);
                startActivity(intent);
            }
        });


    }


}
