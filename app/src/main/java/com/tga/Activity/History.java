package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.TouristController;
import com.tga.R;
import com.tga.adapter.DiscountsAdapter;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private RecyclerView reMyPrograms;
    private DiscountsAdapter mAdapter;
    private ArrayList<ProgramController> arrayList;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My History");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        reMyPrograms = (RecyclerView) findViewById(R.id.reMyPrograms);
        arrayList = new ArrayList<>();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final TouristController[] tc = new TouristController[1];
        TouristController.getByID(new SimpleCallback<TouristController>() {
            @Override
            public void callback(TouristController data) {
                if (data == null){
                    System.out.println("tc is null");
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Tourist not found", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    tc[0] = data;
                    ArrayList<String> array = tc[0].getHistoryPrograms();
                    for (int i = 0; i < array.size(); i++){
                        ProgramController.getByID(new SimpleCallback<ProgramController>() {
                            @Override
                            public void callback(ProgramController pc) {
                                if (pc != null){
                                    arrayList.add(pc);
                                }
                            }
                        }, array.get(i));
                    }

                    mAdapter = new DiscountsAdapter(getApplicationContext(),arrayList);

                    reMyPrograms.setHasFixedSize(true);
                    reMyPrograms.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    reMyPrograms.setItemAnimator(new DefaultItemAnimator());
                    reMyPrograms.setAdapter(mAdapter);
                }
            }
        }, userID);
    }
}

