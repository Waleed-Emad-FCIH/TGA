package com.tga.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.tga.Controller.PlanController;
import com.tga.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddPlan extends AppCompatActivity {


    private ImageView imgAddPlaces;
    private EditText edtTitle  , imgPlanEnd;
    private EditText edtDes;


    private Button submit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create your plan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        edtTitle = (EditText)findViewById(R.id.etxtPlanTitle);
        edtDes = (EditText) findViewById(R.id.etxtPlanDesc);
        submit = (Button) findViewById(R.id.btnAddProgram);
        ArrayList<String> placesIds = (ArrayList<String>) getIntent().getExtras().get("placesId");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String des = edtDes.getText().toString();

                Log.v("des" , title);
                PlanController pc = new PlanController(null , placesIds ,"0" ,"0",  "Cairo" , des, title);
                pc.saveToDB();
                Intent intent =  intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
