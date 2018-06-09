package com.tga.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tga.Activity.MyPrograms;
import com.tga.Controller.ProgramController;
import com.tga.R;
import com.tga.adapter.RecycleAdapter_Home;

import java.util.ArrayList;


/**
 * Created by Wolf Soft on 10/10/2017.
 */

public class Home extends Fragment {

    View view;

    private ArrayList<ProgramController> ArrayList;
    private RecyclerView recyclerView;
    private RecycleAdapter_Home mAdapter;
    private TextView txtMyPrograms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
        ArrayList = ProgramController.listAll();


        //TODO : active this
        //for (int i = 0; i < ArrayList.size(); i++) {
        //    if (ArrayList.get(i).getPrice() == 0)
        //        ArrayList.remove(i);
        //}


        mAdapter = new RecycleAdapter_Home(getActivity(),ArrayList, "Home");

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
