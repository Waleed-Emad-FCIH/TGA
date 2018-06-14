package com.tga.Activity;

import ernestoyaquello.com.verticalstepperform.*;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.TouristController;
import com.tga.R;
import com.tga.adapter.PlacesAdapter;
import com.tga.adapter.PlanPlacesAdabter;
import com.tga.models.TouristModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class PlanDetalis extends AppCompatActivity  {

    private ImageView imgBack,imgEditPlan;
    private RecyclerView planPlaces ;
    private PlanPlacesAdabter planPlacesAdabter ;
    private RecyclerView.LayoutManager layoutManager;
    private Button choosePlan , removePlan , submitChoosingPlan;
    private LinearLayout timeLayout;
    private EditText imgPlanEnd;
    private int mYear,mMonth,mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detalis);
        timeLayout = (LinearLayout) findViewById(R.id.planTime);
        choosePlan = (Button)findViewById(R.id.choosePlan);
        removePlan = (Button) findViewById(R.id.removePlanbrn);
        submitChoosingPlan = (Button) findViewById(R.id.submitChoosingPlan);
        ArrayList<String> placesId = new ArrayList<>();
        placesId  = (ArrayList<String>) getIntent().getExtras().get("placesIds");
        String planID = (String) getIntent().getExtras().get("planId");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        TouristController tc  = new TouristController( userId, null ,
                null , null , null , null );

        tc.getByID(new SimpleCallback<TouristController>() {
            @Override
            public void callback(TouristController data) {
                TouristController touristController  = data;
                try {
                    if(touristController.getMyPlans().contains(planID))
                    {
                        choosePlan.setVisibility(View.GONE);
                        timeLayout.setVisibility(View.GONE);
                        removePlan.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e )
                {

                }
            }
        } , userId);


        choosePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePlan.setVisibility(View.GONE);
                submitChoosingPlan.setVisibility(View.VISIBLE);
                timeLayout.setVisibility(View.VISIBLE);
               // tc.addPlan(planID , userId);

            }
        });
        removePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc.delPlan(planID , userId);
                removePlan.setVisibility(View.GONE);
                choosePlan.setVisibility(View.VISIBLE);
            }
        });

        imgPlanEnd =(EditText)findViewById(R.id.imgProgramEnd);
        imgPlanEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(PlanDetalis.this, new DatePickerDialog.OnDateSetListener() {
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

        submitChoosingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String planTime = imgPlanEnd.getText().toString();
                if(imgPlanEnd.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext() , "please enter the date" , Toast.LENGTH_LONG).show();
                }
                else{
                    tc.addPlan(planID , userId , planTime);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }


            }
        });
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgEditPlan = (ImageView)findViewById(R.id.imgEditPlan);
        ArrayList<String> finalPlacesId = placesId;
        imgEditPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDetalis.this,Plans.class);
                intent.putExtra("placesIds" , finalPlacesId);
                startActivity(intent);
            }
        });

        planPlaces = (RecyclerView) findViewById(R.id.plan_places);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        planPlaces.setLayoutManager(layoutManager);
        planPlaces.setItemAnimator(new DefaultItemAnimator());
        planPlacesAdabter = new PlanPlacesAdabter(placesId , 0 , this);
        planPlaces.setAdapter(planPlacesAdabter);



    }
}
