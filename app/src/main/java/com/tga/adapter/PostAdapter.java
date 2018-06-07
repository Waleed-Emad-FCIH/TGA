package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.Activity.Comments;
import com.tga.Controller.PostController;
import com.tga.R;
import com.tga.models.PostModel;

import java.util.List;
/**
 * Created by yaser on 30/05/18.
 */

public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private List<PostModel> postList;
    Context context;
    FirebaseUser user ;


    public PostAdapter(Context mainActivityContacts,List<PostModel> postsModels) {
        this.context = mainActivityContacts;
        this.postList = postsModels;
    }


    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_community_ops, parent, false);

        return new MyViewHolder(itemView);

       }

    @Override
    public void onBindViewHolder(final PostAdapter.MyViewHolder holder, int position) {
        final PostModel post = postList.get(position);
        holder.uid.setText(post.userId);
        holder.content.setText(post.content);
        holder.pID.setText(post.id);
        holder.postTime.setText(String.valueOf(post.date));
        final PostController s =new PostController(holder.pID.getText().toString(),
                holder.content.getText().toString()
                , System.currentTimeMillis(),holder.uid.toString(),null,0,null);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btnEdit.getText().toString().equalsIgnoreCase("Save")){
                    if (!holder.content.getText().toString().trim().isEmpty()){
                        s.setId(holder.pID.getText().toString());
                        s.setContent(holder.content.getText().toString());
                        s.editPost();
                        holder.content.setEnabled(false);
                        holder.btnEdit.setText("Edit");
                    }
                } else {
                    holder.content.setEnabled(true);
                    holder.btnEdit.setText("Save");


                }
            }
        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            s.setId(holder.pID.getText().toString());
            s.delFromDB();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Comments.class);
                i.putExtra("ID",post.id);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid().equals(post.userId)){
            holder.btnDel.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.VISIBLE);
        } else {
                holder.btnDel.setVisibility(View.INVISIBLE);
                holder.btnEdit.setVisibility(View.INVISIBLE);
            }

        holder.btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostController pc=new PostController(post.id,null,0
                , null,null,0,null);
                pc.like(user.getUid());
            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public EditText uid, content, pID;
        public TextView postTime;
        public Button btnEdit, btnDel,btnlike;

        public MyViewHolder(View view){
            super(view);

            uid = (EditText) view.findViewById(R.id.txtPostUID);
            pID = (EditText) view.findViewById(R.id.txtPostPID);
            content = (EditText) view.findViewById(R.id.txtPostContent);
            btnEdit = (Button) view.findViewById(R.id.btnPostEdit);
            btnDel = (Button) view.findViewById(R.id.btnPostDel);
            btnlike = (Button) view.findViewById(R.id.btnLike);

            postTime = (TextView) view.findViewById(R.id.txtvPostTime);

        }
    }

}
