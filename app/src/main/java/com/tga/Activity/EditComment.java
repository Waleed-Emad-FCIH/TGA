package com.tga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.Controller.PostController;
import com.tga.R;

import java.util.ArrayList;

public class EditComment extends AppCompatActivity {
    private TextView txtDone,btndel;
    private EditText txtPostContent;
    private FirebaseUser user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);
        txtPostContent=(EditText) findViewById(R.id.txtPostContent);
        user = FirebaseAuth.getInstance().getCurrentUser();
        txtDone= (TextView)findViewById(R.id.txtDone) ;
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postID,postContent;
                postID=getIntent().getStringExtra("ID");
                postContent=getIntent().getStringExtra("postID");
                if (!TextUtils.isEmpty(txtPostContent.getText().toString().trim()))
                    if (txtPostContent.getText().toString().trim().length() > 1) {
                        PostController pc  = new PostController(null ,null , System.currentTimeMillis(),
                                null , new ArrayList<String>() ,0,null );
                        PostController.Comment c = pc.new Comment(postID,txtPostContent.getText().toString()
                                , System.currentTimeMillis() , user.getUid() ,postContent);
                     c.editComment(txtPostContent.getText().toString());

                        // txtWritePost.setText("");
                    }
            }
        });

    }
}
