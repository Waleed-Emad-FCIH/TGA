package com.tga.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.TouristController;
import com.tga.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddProgram extends AppCompatActivity {


    private ImageView imgAddPlaces;
    private EditText imgProgramStart,imgProgramEnd, txtTitle, txtDescription, txtHotelName;
    private int mYear,mMonth,mDay;
    private Button btnAddProgram;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Program");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        imgAddPlaces = (ImageView)findViewById(R.id.imgAddPlaces);
        imgProgramStart = (EditText)findViewById(R.id.imgProgramStart);
        imgProgramEnd =(EditText)findViewById(R.id.imgProgramEnd);
        btnAddProgram = (Button) findViewById(R.id.btnAddProgram);
        txtDescription = (EditText) findViewById(R.id.etxtProgramDesc);
        txtTitle = (EditText) findViewById(R.id.etxtProgramTitle);
        txtHotelName = (EditText) findViewById(R.id.txtPHotelName);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        imgAddPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AllPlaces.class);
                startActivity(intent);
            }
        });

        imgProgramStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddProgram.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        imgProgramStart.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        imgProgramEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddProgram.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        imgProgramEnd.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        btnAddProgram.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (!checkText()) {
                    final ProgramController pc = new ProgramController(txtTitle.getText().toString(), new ArrayList<String>(), txtDescription.getText().toString(),
                            imgProgramStart.getText().toString(), imgProgramEnd.getText().toString(),
                            txtHotelName.getText().toString(), userID);
                    pc.saveToDB();
                    TouristController.getByID(new SimpleCallback<TouristController>() {
                        @Override
                        public void callback(TouristController data) {
                            if (data != null)
                                data.addProgram(pc.getId());
                        }
                    }, userID);
                    Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean checkText(){
        return txtTitle.getText().toString().isEmpty() || txtDescription.getText().toString().isEmpty() ||
                txtHotelName.getText().toString().isEmpty() || imgProgramStart.getText().toString().isEmpty() ||
                imgProgramEnd.getText().toString().isEmpty() ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
