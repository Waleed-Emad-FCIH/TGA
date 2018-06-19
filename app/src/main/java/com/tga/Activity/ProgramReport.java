package com.tga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ProgramReport extends AppCompatActivity {
    DatabaseReference dbRef;
    ArrayList<String> ProgramsOfAgent = new ArrayList<>();
    ArrayList<String>   Registered = new ArrayList<>();
    String user_id;
    ArrayList<ProgramModel> myPrograms=new ArrayList<>();
    ArrayList<String>myProgramsIds=new ArrayList<>();
    TextView content;
    Button getReport ,intentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_report);
        user_id=FirebaseAuth.getInstance().getUid();
        content = (TextView) findViewById(R.id.content);
        ListProgramsOfAgent(user_id);
        getReport=(Button) findViewById(R.id.getReport);
        getReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Programs.isEmpty())
                    content.setText("You never added any program yet");
                else{
                    content.setText("Program Report ");

                    for (ProgramModel PM :Programs)
                {
                    if(PM.registeredTouristsID!=null) {
                        if(PM.discountID.equals("")) PM.discountID="No discount";
                        content.setText(content.getText()+"\nTitle : " + PM.title + "\n description : " + PM.description + "\n Hotel Name :" + PM.hotelName + "\n Discount : " + PM.discountID + "\n start Date :" + PM.startDate + "\n End Date :" +
                                PM.endDate + "\n Registered Tourist :" + PM.registeredTouristsID.toString() + "\n Hit rate : " + PM.hitRate +"\n price : "+PM.price+ "\n Rate :" + PM.rate + "\n Reviews : " + PM.reviews
                                +"\n benefit : " + PM.registeredTouristsID.size()*PM.price +"\n===============================");

                    }
                }
                }

            }
        });
    }

    //============================== imbo code =================
    public ArrayList<String> ListProgramsOfAgent (final String id)
    {
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if (ds.child("ownerID").getValue().equals(id) )
                    {
                        ProgramsOfAgent.add(ds.child("id").getValue().toString());
                    }
                }

                GetSomePrograms();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return ProgramsOfAgent;
    }

    //-----------------------------------------------------------

    //-------------------------------
    ArrayList<String> Tourists= new ArrayList<>();

    ArrayList<String> getTouristsRegistered(String id)
    {

        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.child(id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot tourists:dataSnapshot.child("registeredTouristsID").getChildren())
                {
                    Tourists.add(tourists.getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  Tourists;
    }
    //------------------------------------------------------------------
    ArrayList<String> reviews= new ArrayList<>();

    ArrayList<String> getProgramReviews(String id)
    {

        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.child(id).child("reviews").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tourists:dataSnapshot.getChildren())
                {
                    reviews.add(tourists.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  reviews;
    }
    ArrayList<ProgramModel> Programs= new ArrayList<>();

    public ArrayList<ProgramModel> GetSomePrograms()
    {
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( String programId :ProgramsOfAgent) {
                    final ProgramModel programModel= new ProgramModel();
                    programModel.registeredTouristsID= new ArrayList<>();
                    programModel.reviews= new ArrayList<String>();
                    programModel.id = dataSnapshot.child(programId).child("id").getValue().toString();
                    programModel.description = dataSnapshot.child(programId).child("description").getValue().toString();
                    programModel.endDate = dataSnapshot.child(programId).child("endDate").getValue().toString();
                    programModel.startDate = dataSnapshot.child(programId).child("startDate").getValue().toString();
                    programModel.title = dataSnapshot.child(programId).child("title").getValue().toString();
                    if (!dataSnapshot.child(programId).child("price").getValue().equals(""))
                        programModel.price = Double.parseDouble( dataSnapshot.child(programId).child("price").getValue().toString());
                    programModel.discountID = dataSnapshot.child(programId).child("discountID").getValue().toString();
                    programModel.hotelName = dataSnapshot.child(programId).child("hotelName").getValue().toString();
                    programModel.rate = Integer.parseInt( dataSnapshot.child(programId).child("rate").getValue().toString());
                    programModel.ownerID = dataSnapshot.child(programId).child("ownerID").getValue().toString();
                    programModel.hitRate = Integer.parseInt(dataSnapshot.child(programId).child("hitRate").getValue().toString());
                    if (dataSnapshot.child(programId).hasChild("reviews")) {
                        for (DataSnapshot review:dataSnapshot.child(programId).child("reviews").getChildren())
                        {
                            programModel.reviews.add(review.getValue().toString());

                        }

                    }


                    if (dataSnapshot.child(programId).hasChild("registeredTouristsID")) {
                        for (DataSnapshot tourists:dataSnapshot.child(programId).child("registeredTouristsID").getChildren())
                        {
                            final String id =tourists.getValue().toString();
                          DatabaseReference f = FirebaseDatabase.getInstance().getReference("tourists");
f.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
                programModel.registeredTouristsID.add(dataSnapshot.child(id).child("name").getValue().toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

                        }

                        Programs.add(programModel);

                    }
                    else      Programs.add(programModel);


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return Programs;
    }
}