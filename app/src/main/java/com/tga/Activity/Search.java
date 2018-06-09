package com.tga.Activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tga.R;
import com.tga.adapter.SearchAdapter;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<String> categoryList;
    private EditText search_query;
    private ImageView searchicon;
//
//    private RecyclerView recyclerView2;
//    private RecycleAdapter_Home adapter2;
//    private GridLayoutManager gridLayoutManager;
//    private String category;
//    private String prName;
//    private TextView noSearch;
//    private TextView categortyTitle;


    private int image[]= {R.drawable.reservation,R.drawable.things_to_do,R.drawable.food,R.drawable.discounts,R.drawable.get_around,R.drawable.need_to_know};
    private ArrayList<com.tga.model.Search> ArrayList;
    private RecyclerView recyclerView;
    private SearchAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
                ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ic_search_black_24dp);


        recyclerView = (RecyclerView) findViewById(R.id.reSearch);
        ArrayList = new ArrayList<>();



        for (int i = 0; i < image.length; i++) {
            com.tga.model.Search beanClassForRecyclerView_contacts = new com.tga.model.Search(image[i]);

            ArrayList.add(beanClassForRecyclerView_contacts);
        }


        mAdapter = new SearchAdapter(getApplicationContext(),ArrayList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.search, null);
        search_query = (EditText)v.findViewById(R.id.search_query);
        searchicon = (ImageView)v.findViewById(R.id.searchicon);
//
//        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        recyclerView2 = (RecyclerView)findViewById(R.id.searched_recycler_view);
//        recyclerView2.setHasFixedSize(true);
//        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView2.setItemAnimator(new DefaultItemAnimator());
//        noSearch = (TextView)findViewById(R.id.noSearch);
//        categortyTitle = (TextView)findViewById(R.id.categortyTitle);
//
//
//
//
//
//
        actionBar.setCustomView(v);
//        categoryList =new ArrayList();
//        categoryList.add("Museums");
//        categoryList.add("Restaurants");
//        categoryList.add("Malls");
//        categoryList.add("Hotels");
//        categoryList.add("Bars");
//        categoryList.add("Parks");
//        categoryList.add("Places");
//
//        recyclerView= (RecyclerView) findViewById(R.id.search_recycler_view);
//        layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter=new Category(categoryList,getApplicationContext());
//        recyclerView.setAdapter(adapter);
    }
}
