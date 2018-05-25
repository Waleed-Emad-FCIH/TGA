package com.tga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tga.R;

public class DiscountsDetails extends AppCompatActivity {


    private ImageView imgBack;
    private ImageView imgDiscountsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts_details);

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgDiscountsEdit = (ImageView)findViewById(R.id.imgDiscountsEdit);



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgDiscountsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditDiscount.class);
                startActivity(intent);
            }
        });
    }
}
