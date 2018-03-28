package com.tga.fragment.thingsToDo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tga.R;
import com.tga.adapter.PlacesAdapter;
import com.tga.model.PlaceModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutDoors extends Fragment {

    private String title[]= {"loxour","pyramids","sharm"};
    private int image[]= {R.drawable.loxour,R.drawable.pyramids,R.drawable.sharm};


    private java.util.ArrayList<PlaceModel> ArrayList;
    private RecyclerView recyclerView;
    private PlacesAdapter mAdapter;

    public OutDoors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_out_doors, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.out_doors_recyclerview);
        ArrayList = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            PlaceModel beanClassForRecyclerView_contacts = new PlaceModel(title[i],image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new PlacesAdapter(getActivity(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return v;
    }

}
