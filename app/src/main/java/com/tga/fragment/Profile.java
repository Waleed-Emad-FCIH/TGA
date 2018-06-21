package com.tga.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tga.Activity.Community2;
import com.tga.Activity.EditProfile;
import com.tga.Activity.History;
import com.tga.Activity.MyMessage;
import com.tga.Activity.Reports;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.TouristController;
import com.tga.R;
import com.tga.util.CircleTransform;


public class Profile extends Fragment {
    String s ;

    private TextView txtEditProfile,txtHistory;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView txtFullName,txtNationality,txtPhone,txtEmail,com,myMessages,showReports;
    private ImageView imgProfile;
    private String picProfile="";

    public Profile() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_profile, container, false);

        txtEditProfile = (TextView)v.findViewById(R.id.txtEditProfile);
        txtHistory = (TextView)v.findViewById(R.id.txtHistory);
        myMessages = (TextView) v.findViewById(R.id.myMessagesBtn);
        showReports=(TextView) v.findViewById(R.id.showReports);
        showReports.setVisibility(v.INVISIBLE);
        showReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent Reports_intent= new Intent(getContext(),Reports.class);
startActivity(Reports_intent);
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Agents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid()))
                {
                    s="tourists";
                    showReports.setVisibility(v.VISIBLE);

                }
                else
                {
                    s="Agents";
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(getContext(),MyMessage.class));
                intent.putExtra("key",s);
                startActivity(intent);


            }
        });

        com = (TextView) v.findViewById(R.id.com);
        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(getContext(),Community2.class));
                startActivity(intent);
            }
        });

        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                intent.putExtra("fullName",txtFullName.getText());
                intent.putExtra("phone",txtPhone.getText());
                intent.putExtra("email",txtEmail.getText());
                intent.putExtra("pic",picProfile);
                startActivity(intent);
            }
        });

        txtHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), History.class);
                startActivity(intent);
            }
        });

        txtFullName = v.findViewById(R.id.txtFullName);
        txtNationality = v.findViewById(R.id.txtNationality);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtEmail = v.findViewById(R.id.txtEmail);
        imgProfile = v.findViewById(R.id.imgProfile);


        final String uid = mAuth.getCurrentUser().getUid();
        TouristController.getByID(new SimpleCallback<TouristController>() {
            @Override
            public void callback(TouristController data) {
                txtFullName.setText(data.getName());
                txtNationality.setText(data.getNationality());
                txtPhone.setText(data.getPhoneNumber());
                txtEmail.setText(data.getEmail());
                if (data.getPhoto()!=null  && data.getPhoto()!= "" && !data.getPhoto().equals("") && !data.getPhoto().equals(null)){
                    Picasso.with(getContext())
                            .load(data.getPhoto())
                            .transform(new CircleTransform())
                            .into(imgProfile);
                    picProfile = data.getPhoto();
                }
            }
        },uid);


        return v;
    }


}
