package com.tga.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.Activity.AddPlan;
import com.tga.Controller.PlanController;
import com.tga.R;
import com.tga.adapter.PlanAdapter;
import com.tga.callback;
import com.tga.model.Plan;
import com.tga.models.PlanModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Plans extends Fragment {

    View view;
    private FloatingActionButton floatingActionButton;

    private String title[]= {"Midtown","Explore Cairo","Giza Highlights"};
    private String sites[]= {"8 sights","3 sights","5 sights"};
    private String shortInfo[]= {"Typically these sights open every day.","some sights may not be open on Sat & Sun. Check openinig hours",
            "Typically these sights open every day."};
    private int imaMap[]= {R.drawable.map,R.drawable.map,R.drawable.map};
    private int imaSite1[]= {R.drawable.sharm,R.drawable.loxour,R.drawable.pyramids};
    private int imaSite2[]= {R.drawable.pyramids,R.drawable.loxour,R.drawable.sharm};
    private int imaSite3[]= {R.drawable.loxour,R.drawable.pyramids,R.drawable.sharm};




    private RecyclerView recyclerView;
    private PlanAdapter mAdapter;

    public Plans() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_plans, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rePlansSuggested);
        floatingActionButton = (FloatingActionButton)v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), com.tga.Activity.Plans.class);
                startActivity(intent);
            }
        });



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ///take from database
        FirebaseDatabase mRef = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference reference =mRef.getReference().child("plans");

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 java.util.ArrayList<PlanModel> ArrayList =new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlanModel plan = snapshot.getValue(PlanModel.class);
                    Log.v("data??>>>", "here + " + plan.getTitle() + ArrayList.size());
                    ArrayList.add(plan);

                }
                mAdapter = new PlanAdapter(getActivity(),ArrayList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return v;
    }

}
