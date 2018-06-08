package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.Activity.AddPost2;
import com.tga.Activity.Comments;
import com.tga.Activity.PostDetails;
import com.tga.Controller.PostController;
import com.tga.Controller.UserController;
import com.tga.R;
import com.tga.model.User;
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
                .inflate(R.layout.item_post, parent, false);

        return new MyViewHolder(itemView);

       }

    @Override
    public void onBindViewHolder(final PostAdapter.MyViewHolder holder, int position) {
        final PostModel post = postList.get(position);
        holder.content.setText(post.content);
        holder.postTime.setText(String.valueOf(post.date));
     //   holder.txtname.setText(user.getDisplayName());
       // holder.ppimg.setImageURI(user.getPhotoUrl());
    //   holder.txtNumLikes.setText((post.likes));




        Log.v("num" , postList.size()+"")    ;

        final PostController object =new PostController(post.id,
                holder.content.getText().toString()
                , System.currentTimeMillis(),null,
                null,0,null);


        holder.edittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (!holder.content.getText().toString().trim().isEmpty()){
                        Intent i = new Intent(context, AddPost2.class);
                        i.putExtra("ID",post.id);
                        i.putExtra("content",post.content);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(i);
                        object.editPost();
                    }



            }
        });

        holder.deltxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            object.setId(post.id);
            object.delFromDB();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid().equals(post.userId)){
            holder.deltxt.setVisibility(View.VISIBLE);
            holder.edittxt.setVisibility(View.VISIBLE);
        } else {
            holder.deltxt.setVisibility(View.INVISIBLE);
            holder.edittxt.setVisibility(View.INVISIBLE);
        }

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PostDetails.class);
                i.putExtra("ID",post.id);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);

            }
        });
/*

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Comments.class);
                i.putExtra("ID",post.id);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });


        holder.btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostController pc=new PostController(post.id,null,0
                , null,null,0,null);
                pc.like(user.getUid());
            }
        });

*/
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtname,txtNumLikes, content, edittxt,deltxt,reporttxt,postTime;
        public ImageView ppimg ,img1,img2,img3,comment;
        public MyViewHolder(View view){
            super(view);
            txtname = (TextView) view.findViewById(R.id.txtName);
            edittxt = (TextView) view.findViewById(R.id.txtEditPost);
            content = (TextView) view.findViewById(R.id.txtPostContent);
            deltxt = (TextView) view.findViewById(R.id.txtDeletePost);
            reporttxt = (TextView) view.findViewById(R.id.txtReportPost);
            postTime = (TextView) view.findViewById(R.id.txtvPostTime);
            ppimg = (ImageView)view.findViewById(R.id.imgPP);
            img1 = (ImageView)view.findViewById(R.id.postImg1);
            img2 = (ImageView)view.findViewById(R.id.postImg2);
            img3 = (ImageView)view.findViewById(R.id.postImg3);
            txtNumLikes=(TextView)view.findViewById(R.id.txtNumLikes);
            comment=(ImageView)view.findViewById(R.id.comment);

        }
    }

}
