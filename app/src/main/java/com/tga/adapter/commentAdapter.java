package com.tga.adapter;

/**
 * Created by yaser on 02/06/18.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
                .inflate(R.layout.item_comment, parent, false);

        return new MyViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(commentAdapter.MyViewHolder holder, int position) {
       final CommentModel comment = commentList.get(position);

        holder.content.setText(comment.content);
        holder.postTime.setText(String.valueOf(comment.date));


        Log.v("num" , commentList.size()+"")    ;

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtname, content, edittxt,deltxt,reporttxt,postTime;
        public ImageView ppimg;
        public MyViewHolder(View view) {
            super(view);

            txtname = (TextView) view.findViewById(R.id.txtName);
            edittxt = (TextView) view.findViewById(R.id.txtEditPost);
            content = (TextView) view.findViewById(R.id.txtPostContent);
            deltxt = (TextView) view.findViewById(R.id.txtDeletePost);
            reporttxt = (TextView) view.findViewById(R.id.txtReportPost);
            postTime = (TextView) view.findViewById(R.id.txtvPostTime);
            ppimg = (ImageView)view.findViewById(R.id.imgPP);


        }
    }
}
