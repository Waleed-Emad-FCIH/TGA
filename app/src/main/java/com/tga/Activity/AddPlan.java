package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tga.Controller.PlanController;
import com.tga.R;
import com.tga.util.StaticVarible;

public class AddPlan extends AppCompatActivity {


    private ImageView imgAddPlaces;
    private EditText edtTitle;
    private EditText edtDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create your plan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        edtTitle = (EditText)findViewById(R.id.etxtPlanTitle);
        edtDes  = findViewById(R.id.etxtPlanDesc);
        PlanController pc = new PlanController(null , StaticVarible.placesIds ,"0" , "0 " , edtDes.getText().toString());
        pc.saveToDB();



    }
}
