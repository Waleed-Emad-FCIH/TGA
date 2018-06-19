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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.Controller.PostController;
import com.tga.R;
import com.tga.adapter.commentAdapter;
import com.tga.models.CommentModel;
import com.tga.models.PostModel;

import java.util.ArrayList;

public class PostDetails extends AppCompatActivity {
    private PostController posts,obj;
    private RecyclerView recyclerView;
    private TextView content;
    private ImageView btnPost;
    private EditText txtWritePost;
    FirebaseUser user ;

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
        btnPost = (ImageView) findViewById(R.id.comment);
        txtWritePost=(EditText)findViewById(R.id.txtwriteComment);
        user = FirebaseAuth.getInstance().getCurrentUser();

        String value = getIntent().getStringExtra("ID");

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final DatabaseReference tRef = fd.getReference("posts").child(value);
        tRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PostModel postModel= dataSnapshot.getValue(PostModel.class);
                content.setText(postModel.content);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference cRef = fd.getReference("comments");
        Query query = cRef.orderByChild("postId").equalTo(value);
        final ArrayList<CommentModel> comments = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CommentModel commentModel = snapshot.getValue(CommentModel.class);
                    comments.add(commentModel);
                }
                cAdapter = new commentAdapter(getApplicationContext(),comments);
                cAdapter.commentList=comments;
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

                recyclerView.setAdapter(cAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                                user.getUid() ,value);
                        c.createComment();
                        finish();
                        startActivity(getIntent());
                    }
            }
        });

    }
}