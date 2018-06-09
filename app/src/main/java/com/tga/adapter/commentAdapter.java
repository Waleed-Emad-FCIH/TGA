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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.Activity.AddPost2;
import com.tga.Activity.Comments;
import com.tga.Activity.EditComment;
import com.tga.Controller.PostController;
import com.tga.R;
import com.tga.models.CommentModel;
import com.tga.models.PostModel;

import java.util.ArrayList;
import java.util.List;
public class commentAdapter  extends RecyclerView.Adapter<commentAdapter.MyViewHolder>{
    private List<CommentModel>commentList;
    Context context;
    FirebaseUser user ;



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
    public void onBindViewHolder(final commentAdapter.MyViewHolder holder, int position) {
       final CommentModel comment = commentList.get(position);

        holder.content.setText(comment.content);
        holder.postTime.setText(String.valueOf(comment.date));
        user = FirebaseAuth.getInstance().getCurrentUser();

        final PostController pc  = new PostController(null , null , System.currentTimeMillis(),
                null , new ArrayList<String>() ,0,null );
        final PostController.Comment c = pc.new Comment(comment.id, holder.content.getText().toString()
                , System.currentTimeMillis() ,
                user.getUid() ,null);

        Log.v("num" , commentList.size()+"")    ;

        holder.deltxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             c.delComment();

            }
        });
        holder.edittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.content.getText().toString().trim().isEmpty()){
                    Intent i = new Intent(context, EditComment.class);
                    i.putExtra("postID",pc.getId());
                    i.putExtra("ID",comment.id);
                    i.putExtra("content",comment.content);

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(i);
                }



            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid().equals(comment.userId)){
            holder.deltxt.setVisibility(View.VISIBLE);
            holder.edittxt.setVisibility(View.VISIBLE);
        } else {
            holder.deltxt.setVisibility(View.INVISIBLE);
            holder.edittxt.setVisibility(View.INVISIBLE);
        }

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
