package com.tga.Activity;

/**
 * Created by yaser on 30/05/18.
 */
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.tga.Controller.PostController;
import com.tga.R;
import com.tga.adapter.PostAdapter;


public class Community extends AppCompatActivity  {
    private PostController posts;
    private PostAdapter pAdapter;
    private RecyclerView recyclerView;
    private EditText txtWritePost;
    private Button btnPost;
    private LinearLayout layoutPost;
    private long date;
    FirebaseUser user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        getSupportActionBar().setTitle("Community");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
        txtWritePost = (EditText) findViewById(R.id.txtWritePost);
        btnPost = (Button) findViewById(R.id.btnPost);
        layoutPost = (LinearLayout) findViewById(R.id.layoutPost);
        user = FirebaseAuth.getInstance().getCurrentUser();

        posts= new PostController(null,txtWritePost.getText().toString(),
                System.currentTimeMillis(),user.getUid(),null,0,null);
       pAdapter = new PostAdapter(getApplicationContext(),posts.listAll());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

       recyclerView.setAdapter(pAdapter);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(txtWritePost.getText().toString().trim()))
                    if (txtWritePost.getText().toString().trim().length() > 1) {
                        PostController s =new PostController(null,txtWritePost.getText().toString()

                                , System.currentTimeMillis(),user.getUid(),null,
                                0,null);
                        s.saveToDB();

                       // txtWritePost.setText("");
                    }
            }
        });


    }
    }
