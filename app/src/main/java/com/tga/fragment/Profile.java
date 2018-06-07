package com.tga.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.tga.Activity.Community;
import com.tga.Activity.EditProfile;
import com.tga.Activity.History;
import com.tga.R;
import com.tga.model.User;
import com.tga.util.CircleTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Profile extends Fragment {

    private TextView txtEditProfile,txtHistory;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView txtFullName,txtNationality,txtPhone,txtEmail,com;
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
        com = (TextView) v.findViewById(R.id.com);
        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(getContext(),Community.class));
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query query2 = database.getReference("users").orderByChild("id").equalTo(uid);
        query2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                // do some stuff once

                HashMap<String, User> results = snapshot.getValue(new GenericTypeIndicator<HashMap<String, User>>() {
                });

                List<User> posts = new ArrayList<>(results.values());

                for (User post : posts) {
                    txtFullName.setText(post.getName());
                    txtNationality.setText(post.getCountry());
                    txtPhone.setText(post.getPhoneNumber());
                    txtEmail.setText(post.getEmail());
                    if (post.getProfilePic()!=null  && post.getProfilePic()!= "" && !post.getProfilePic().equals("") && !post.getProfilePic().equals(null)){
                        Picasso.with(getContext())
                                .load(post.getProfilePic())
                                .transform(new CircleTransform())
                                .into(imgProfile);
                        picProfile = post.getProfilePic();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }


}
