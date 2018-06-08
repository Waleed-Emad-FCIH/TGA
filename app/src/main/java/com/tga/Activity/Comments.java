package com.tga.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tga.Controller.PostController;
import com.tga.R;
import com.tga.adapter.PostAdapter;
import com.tga.adapter.commentAdapter;

import java.util.ArrayList;

/**
 * Created by yaser on 31/05/18.
 */

public class Comments  extends AppCompatActivity{

    private PostController posts,s;
    private RecyclerView recyclerView;
    private EditText txtWritePost;
    private Button btnPost;
    private commentAdapter cAdapter;

    private LinearLayout layoutPost;
    public ArrayList<String> comments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setTitle("Comment");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
        txtWritePost = (EditText) findViewById(R.id.txtWritePost);
        btnPost = (Button) findViewById(R.id.btnPost);
        layoutPost = (LinearLayout) findViewById(R.id.layoutPost);
        String value = getIntent().getStringExtra("ID");
        posts= new PostController(value,null,System.currentTimeMillis(),null,
                null,0,null);
        cAdapter = new commentAdapter(getApplicationContext(),posts.getComments());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(cAdapter);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = getIntent().getStringExtra("ID");
                if (!TextUtils.isEmpty(txtWritePost.getText().toString().trim()))
                    if (txtWritePost.getText().toString().trim().length() > 1) {
                        PostController pc  = new PostController(value , null , System.currentTimeMillis(),
                                null , new ArrayList<String>() ,0,null );
                        PostController.Comment c = pc.new Comment(null,txtWritePost.getText().toString()
                                , System.currentTimeMillis() ,
                                null ,value);
                        c.createComment();
                        // txtWritePost.setText(""
                    }
            }
        });
    }


}
