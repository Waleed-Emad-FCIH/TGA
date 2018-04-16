package com.tga.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tga.Activity.MyPrograms;
import com.tga.R;
import com.tga.adapter.RecycleAdapter_Offers;
import com.tga.model.Offers;

import java.util.ArrayList;


/**
 * Created by Wolf Soft on 10/10/2017.
 */

public class Home extends Fragment {

    View view;

    private String title[]= {"loxour","pyramids","sharm"};

    private String price[]= {"$1,00,000","$1,00,000","$1,00,000","$1,00,000"};
    private int image[]= {R.drawable.loxour,R.drawable.pyramids,R.drawable.sharm};


    private ArrayList<Offers> ArrayList;
    private RecyclerView recyclerView;
    private RecycleAdapter_Offers mAdapter;
    private TextView txtMyPrograms;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
        ArrayList = new ArrayList<>();



        for (int i = 0; i < title.length; i++) {
            Offers beanClassForRecyclerView_contacts = new Offers(title[i],price[i],image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new RecycleAdapter_Offers(getActivity(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        txtMyPrograms = view.findViewById(R.id.txtMyPrograms);

        txtMyPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyPrograms.class);
                startActivity(intent);
            }
        });


        return view;

    }






}
