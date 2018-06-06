package com.tga.adapter;

/**
 * Created by yaser on 02/06/18.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tga.Activity.Comments;
import com.tga.R;
import com.tga.models.CommentModel;
import com.tga.models.PostModel;

import java.util.List;
public class commentAdapter  extends RecyclerView.Adapter<commentAdapter.MyViewHolder>{
    private List<CommentModel>commentList;
    Context context;


    public commentAdapter(Context mainActivityContacts,List<CommentModel> commentModels) {
        this.context = mainActivityContacts;
        this.commentList = commentModels;
    }

    @Override
    public commentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_comment_view, parent, false);

        return new MyViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(commentAdapter.MyViewHolder holder, int position) {
       final CommentModel comment = commentList.get(position);
       holder.content.setText(comment.content);
        holder.postTime.setText(String.valueOf(comment.date));



    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText uid, content, cID;
        public TextView postTime;
        public Button writePost;
        public MyViewHolder(View itemView) {
            super(itemView);

            content = (EditText) itemView.findViewById(R.id.txtPostContent);
            postTime = (TextView) itemView.findViewById(R.id.txtvPostTime);


        }
    }
}
