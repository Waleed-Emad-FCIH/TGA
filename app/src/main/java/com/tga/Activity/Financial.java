package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Financial extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Programs");
    DatabaseReference touristReference =FirebaseDatabase.getInstance().getReference().child("tourists");
    HashMap<String,String> touristNames= new HashMap<>();
    ArrayList<String> ProgramsRegistered= new ArrayList<>();
    HashMap<String,ArrayList<String>> report= new HashMap<>();
    HashMap<String,String> programPrice= new HashMap<>();
    ListView List;
    TextView ProgramName;
    TextView total_Prices;
    Button getReport;


    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Financial Report ");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));
        setContentView(R.layout.activity_financial);
        user_id=FirebaseAuth.getInstance().getUid();




        getRegistered();
        ProgramName = (TextView)findViewById(R.id.ProgramName);
        getReport=(Button)findViewById(R.id.getFreport);
        getReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (report.isEmpty()) {
                    ProgramName.setText("No data to be Shown");
                } else {
                    ProgramName.setText("Financial Report\n\n");

                    for (Map.Entry m : report.entrySet()) {
                        ArrayList<String> f =(ArrayList<String>) m.getValue();
                        ProgramName.setText(ProgramName.getText() + "\n title : " + m.getKey().toString() + "\n Participants : " + m.getValue().toString()
                                +"\nTotal Benefit : "+f.size()*Double.parseDouble(programPrice.get( m.getKey().toString()))+ "\n =============================");

                    }
                }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();


    }

    String id;
    String price;
    public void getRegistered()
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot ds:dataSnapshot.getChildren())
                {

                    if (ds.child("ownerID").getValue().toString().equals(user_id))
                    {
                        price=ds.child("price").getValue().toString();
                        programPrice.put(ds.child("title").getValue().toString(),price);

                        if(ds.child("registeredTouristsID").hasChildren())
                        {    final ArrayList<String>    Registered=new ArrayList<String>();

                            for(final DataSnapshot item:ds.child("registeredTouristsID").getChildren())
                            {    touristReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Registered.add(dataSnapshot.child(item.getValue().toString()).child("name").getValue().toString());

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            }
                            report.put(ds.child("title").getValue().toString(),Registered);

                        }
                    }


                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
