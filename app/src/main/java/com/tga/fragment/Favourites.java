package com.tga.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.R;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.models.place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourites extends Fragment {

    private java.util.ArrayList<place> ArrayList;
    private RecyclerView recPlaces;
    private ThingsToDoAdpater mAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid();


    public Favourites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);


        recPlaces = (RecyclerView) v.findViewById(R.id.recPlaces);




//        mAdapter = new ThingsToDoAdpater(getActivity(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recPlaces.setLayoutManager(mLayoutManager);
        recPlaces.setItemAnimator(new DefaultItemAnimator());

        Query query1 = database.getReference("favourites").orderByChild("uid").equalTo(uid);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, place> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, place>>() {
                });

                try {
                    List<place> data = new ArrayList<>(results.values());
                    mAdapter = new ThingsToDoAdpater(getContext(), data);
                    recPlaces.setAdapter(mAdapter);

                }catch (Exception e){
                    return;
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }

}
