package com.tga.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.Controller.AgentController;
import com.tga.R;

import java.util.ArrayList;

public class RequestAgent extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText etxtPhoneNum;
    private EditText confirmPassword;
    private Spinner country;
    private TextView txtRequest;
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_agent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        name = (EditText) findViewById(R.id.etxtFullName);
        email = (EditText) findViewById(R.id.etxtEmail);
        password = (EditText) findViewById(R.id.etxtPassword);
        confirmPassword=(EditText)findViewById(R.id.etxtCPassword);
        country = (Spinner)findViewById(R.id.spCountry);
        txtRequest=(TextView) findViewById(R.id.txtRequest);
        etxtPhoneNum = (EditText)findViewById(R.id.etxtPhoneNum);
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Thanks for your Request we will contact you within next 48 hours");
        dlgAlert.setTitle("App Title");
        dlgAlert.setPositiveButton("OK", null);
//        dlgAlert.setCancelable(true);


        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });

        txtRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateForm()) {
                    return;
                }
                ArrayList<String>myprogIds = new ArrayList<>();
                myprogIds.add("");
                AgentController agentController = new AgentController("id",email.getText().toString(), password.getText().toString(), name.getText().toString(), etxtPhoneNum.getText().toString(), country.getSelectedItem().toString(), "", "",  myprogIds,false);
                agentController.getState();
                DatabaseReference users = mRef.getReference("AgentRequests");
                users.child(users.push().getKey()).setValue(agentController);
                dlgAlert.create().show();
            }
        });

    }


    private boolean validateForm() {
        boolean valid = true;

        String userName = name.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            name.setError("Required.");
            valid = false;
        } else {
            name.setError(null);
        }

        String userEmail = email.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String phone = etxtPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            etxtPhoneNum.setError("Required.");
            valid = false;
        } else {
            etxtPhoneNum.setError(null);
        }

        String userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userPassword)||userPassword.length()<8) {
            password.setError("Password at least 8 character");
            valid = false;
        } else {
            password.setError(null);
        }

        String userPasswordConfirm = confirmPassword.getText().toString();
        if (!userPasswordConfirm.equals(userPassword)){
            confirmPassword.setError("Wrong");
            valid = false;
        }else {
            confirmPassword.setError(null);
        }

        String city = country.getSelectedItem().toString();
        if(city.equals("City"))
        {
            Toast.makeText(RequestAgent.this, "Select your city",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
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
