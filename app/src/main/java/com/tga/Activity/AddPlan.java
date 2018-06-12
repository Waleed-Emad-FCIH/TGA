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
    private int mYear,mMonth,mDay;
    private EditText edtLocation;
    private Button submit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create your plan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        edtTitle = (EditText)findViewById(R.id.etxtPlanTitle);
        edtDes  =(EditText) findViewById(R.id.etxtPlanDesc);
        edtLocation =(EditText) findViewById(R.id.etxtPlanLocation);
        submit = (Button) findViewById(R.id.btnAddProgram);
        imgPlanEnd =(EditText)findViewById(R.id.imgProgramEnd);
        ArrayList<String> placesIds = (ArrayList<String>) getIntent().getExtras().get("placesId");
        imgPlanEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddPlan.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        imgPlanEnd.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String des = edtDes.getText().toString();
                String loc = edtLocation.getText().toString();
                Log.v("des" , title);
                PlanController pc = new PlanController(null , placesIds ,"0" , imgPlanEnd.getText().toString(), loc , des, title);
                pc.saveToDB();
                Intent intent =  intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
