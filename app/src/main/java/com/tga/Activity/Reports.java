package com.tga.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.R;
import com.tga.models.ProgramModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reports extends AppCompatActivity {
    String user_id;



    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Programs");
    DatabaseReference touristReference =FirebaseDatabase.getInstance().getReference().child("tourists");
    HashMap<String,String> touristNames= new HashMap<>();
    ArrayList<String> ProgramsRegistered= new ArrayList<>();
    HashMap<String,ArrayList<String>> report= new HashMap<>();
    HashMap<String,String> programPrice= new HashMap<>();
    ListView List;
    TextView ProgramName;
    TextView total_Prices;
    Button getReport,Financial;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        user_id=FirebaseAuth.getInstance().getUid();
        Financial =(Button) findViewById(R.id.financialReport);
        Financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FinancialIntent = new Intent(getApplicationContext(),Financial.class);
                startActivity(FinancialIntent);

            }
        });

        getReport =(Button)findViewById(R.id.programReport);
        getReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProgramIntent = new Intent(getApplicationContext(),ProgramReport.class);
                startActivity(ProgramIntent);

            }
        });



    }



}
