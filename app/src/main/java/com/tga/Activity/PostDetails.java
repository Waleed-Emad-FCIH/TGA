package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tga.Controller.PostController;
import com.tga.R;
import com.tga.adapter.commentAdapter;

import java.util.ArrayList;

public class PostDetails extends AppCompatActivity {
    private PostController posts,obj;
    private RecyclerView recyclerView;
    private TextView content;
    private Button btnPost;

    private commentAdapter cAdapter;

    private LinearLayout layoutPost;
    public ArrayList<String> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_comments);
        content=(TextView)findViewById(R.id.txtPostContent);
            //btnPost = (Button) findViewById(R.id.btnPost);
        String value = getIntent().getStringExtra("ID");
        posts= new PostController(value,null,System.currentTimeMillis(),null,
                null,0,null);
        cAdapter = new commentAdapter(getApplicationContext(),posts.getComments());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(cAdapter);

    }
}
