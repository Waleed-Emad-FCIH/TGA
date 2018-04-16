package com.tga.Activity;

import ernestoyaquello.com.verticalstepperform.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tga.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class PlanDetalis extends AppCompatActivity implements VerticalStepperForm {

    private ImageView imgBack,imgEditPlan;
    private VerticalStepperFormLayout verticalStepperForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detalis);

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgEditPlan = (ImageView)findViewById(R.id.imgEditPlan);
        imgEditPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDetalis.this,EditPlan.class);
                startActivity(intent);
            }
        });

        String[] mySteps = {"Place 1", "Place 2", "Place 3"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimaryDark)
                .primaryDarkColor(colorPrimary)
                .displayBottomNavigation(true)
                .materialDesignInDisabledSteps(true)
                // It is true by default, so in this case this line is not necessary
                .init();


    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = createNameStep();
                break;
            case 1:
                view = createEmailStep();
                break;
            case 2:
                view = createPhoneNumberStep();
                break;
        }
        return view;
    }
    private View createNameStep() {
        // Here we generate programmatically the view that will be added by the system to the step content layout
        ImageView img = new ImageView(this);
        img.setBackgroundResource(R.drawable.pyramids);
        return img;
    }

    private View createEmailStep() {
        // In this case we generate the view by inflating a XML file

        ImageView img = new ImageView(this);
        img.setBackgroundResource(R.drawable.pyramids);
        return img;
    }

    private View createPhoneNumberStep() {
        ImageView img = new ImageView(this);
        img.setBackgroundResource(R.drawable.pyramids);
        return img;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                verticalStepperForm.setStepAsCompleted(0);
                break;
            case 1:
                verticalStepperForm.setStepAsCompleted(1);
                break;
            case 2:
                // As soon as the phone number step is open, we mark it as completed in order to show the "Continue"
                // button (We do it because this field is optional, so the user can skip it without giving any info)
                verticalStepperForm.setStepAsCompleted(2);
                // In this case, the instruction above is equivalent to:
                // verticalStepperForm.setActiveStepAsCompleted();
                break;
        }
    }

    @Override
    public void sendData() {

    }
}
