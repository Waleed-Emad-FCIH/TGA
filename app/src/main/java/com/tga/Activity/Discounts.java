package com.tga.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.R;
import com.tga.adapter.DiscountsAdapter;
import com.tga.adapter.SearchAdapter;
import com.tga.model.*;

import java.util.ArrayList;

public class Discounts extends AppCompatActivity {

    private ArrayList<ProgramController> arrayList;
    private RecyclerView recyclerView;
    private DiscountsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Discounts");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        recyclerView = (RecyclerView) findViewById(R.id.reDiscounts);
        ProgramController.listAll(new SimpleCallback<ArrayList<ProgramController>>() {
            @Override
            public void callback(ArrayList<ProgramController> data) {
                arrayList = data;
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getPrice() == 0)
                        arrayList.remove(i);
                }


                mAdapter = new DiscountsAdapter(getApplicationContext(),arrayList);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
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

        if (id == R.id.add){
            Intent intent = new Intent(getApplicationContext(),AddDiscounts.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
