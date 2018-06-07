package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.Controller.PostController;
import com.tga.R;

public class AddPost2 extends AppCompatActivity {

    private TextView txtWritePost;
    private Button btnPost;
    private ImageView img ;
    FirebaseUser user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Post");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));
        txtWritePost = (TextView) findViewById(R.id.etxtPlaceDesc);
       String postContent=getIntent().getStringExtra("content");

        txtWritePost.setText(postContent);

        user = FirebaseAuth.getInstance().getCurrentUser();
        img = (ImageView) findViewById(R.id.imgAddPlacePic);
        btnPost = (Button) findViewById(R.id.btnAddPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postID,postContent;
                postID=getIntent().getStringExtra("ID");
                if (!TextUtils.isEmpty(txtWritePost.getText().toString().trim()))
                    if (txtWritePost.getText().toString().trim().length() > 1) {
                        PostController s =new PostController(postID,txtWritePost.getText().toString()
                                , System.currentTimeMillis(),user.getUid(),null,
                                0,null);

                        if (s.getId() == null) {
                            s.saveToDB();


                        }
                        else  {

                            s.editPost();
                        }

                        // txtWritePost.setText("");
                    }
            }
        });


    }
}
