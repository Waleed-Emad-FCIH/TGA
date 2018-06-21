package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tga.Controller.AgentController;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.SimpleSession;
import com.tga.Controller.TouristController;
import com.tga.R;
import com.tga.adapter.DiscountsAdapter;
import com.tga.adapter.RecycleAdapter_Home;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MyPrograms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter_Home mAdapter;
    private ArrayList<ProgramController> arrayList;
    private FloatingActionButton fabAddProgram;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_programs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Programs");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.myPrograms_recyclerview);
        fabAddProgram = (FloatingActionButton)findViewById(R.id.fabAddProgram);
        arrayList = new ArrayList<>();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (SimpleSession.isNull()){
            Toast.makeText(getApplicationContext(), "Session Expired", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
            finish();
        }
        SimpleSession session = SimpleSession.getInstance();

        if (session.getUserRole() == SimpleSession.TOURIST_ROLE){
            prepareAdapterList(((TouristController) session.getUserObj()).getMyPrograms());
        } else {
            prepareAdapterList(((AgentController) session.getUserObj()).getMyPrograms());
        }

        fabAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Plans.class);
                i.putExtra("ACTIVITY", "ADD_PROG");
                startActivity(i);
            }
        });

    }

    private void prepareAdapterList(ArrayList<String> array) {
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
        mAdapter = new RecycleAdapter_Home(getApplicationContext(),arrayList, "MyPrograms");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
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
