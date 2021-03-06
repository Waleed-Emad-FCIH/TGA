package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.tga.Activity.AddPost2;
import com.tga.Activity.PostDetails;
import com.tga.Controller.PostController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.TouristController;
import com.tga.R;
import com.tga.models.PostModel;
import com.tga.util.CircleTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by yaser on 30/05/18.
 */

public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private ArrayList<PostModel> postList;

    Context context;
    FirebaseUser user ;



    public PostAdapter(Context mainActivityContacts,ArrayList<PostModel> postsModels) {
        this.context = mainActivityContacts;
        this.postList= new ArrayList<>();
        this.postList =postsModels;
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
        user = FirebaseAuth.getInstance().getCurrentUser();

        holder.postTime.setText(String.valueOf(post.date));
        holder.txtNumLikes.setText(String.valueOf(post.likes));

        try {

            Picasso.with(context)
                    .load(post.img)
                    .into(holder.img1);

        }catch (Exception e){

        }


     //   holder.txtname.setText(user.getDisplayName());
       // holder.ppimg.setImageURI(user.getPhotoUrl());
   //    holder.txtNumLikes.setText((post.likes));
        try {
            if(post.likesID.contains(user.getUid()))
            {
                holder.unliked.setVisibility(GONE);
                holder.liked.setVisibility(VISIBLE);
            }
            else {
                holder.unliked.setVisibility(VISIBLE);
            }
        }
        catch (Exception e)
        {
            holder.unliked.setVisibility(VISIBLE);
        }


        final ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(165);




        Log.v("num" , postList.size()+"")    ;

        String timeStamp = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());

        final PostController object =new PostController(post.id,
                holder.content.getText().toString()
                , timeStamp,null,
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


            TouristController.getByID(new SimpleCallback<TouristController>() {
                @Override
                public void callback(TouristController data) {
                    try {
                        holder.txtname.setText(data.getName());
                        Picasso.with(context)
                                .load(data.getPhoto())
                                .transform(new CircleTransform())
                                .into(holder.ppimg);



                    }
                    catch (Exception e)
                    {

                    }
                    }


                //@@
            },post.userId);







        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.liked.setVisibility(GONE);
                holder.unliked.startAnimation(animation);
                holder.liked.invalidate();
                holder.unliked.setVisibility(VISIBLE);
                Integer d = Integer.valueOf(holder.txtNumLikes.getText().toString()) -1;
                if (d==-1)
                {
                    d = 0 ;
                    holder.txtNumLikes.setText(d.toString());
                }
                else {
                    holder.txtNumLikes.setText(d.toString());
                }

                PostController pc=new PostController(post.id,null,""
                , user.getUid(),null,0,null);
                pc.unlike(user.getUid(), new SimpleCallback<Boolean>() {
                    @Override
                    public void callback(Boolean data) {

                    }
                });
            }
        });

        holder.unliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PostController pc=new PostController(post.id,null,""
                        , user.getUid(),null,0,null);
               pc.like(user.getUid(), new SimpleCallback() {
                   @Override
                   public void callback(Object data) {
                       holder.unliked.setVisibility(GONE);
                       holder.liked.startAnimation(animation);
                       holder.unliked.invalidate();
                       holder.liked.setVisibility(VISIBLE);


                   }
               });
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtname,txtNumLikes, content, edittxt,deltxt,reporttxt,postTime;
        public ImageView ppimg ,img1,comment , liked, unliked;
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
            txtNumLikes=(TextView)view.findViewById(R.id.txtNumLikes);
            comment=(ImageView)view.findViewById(R.id.comment);
            liked =(ImageView)view.findViewById(R.id.imgLiked);
            unliked =(ImageView)view.findViewById(R.id.imgNotLiked);
            unliked.setVisibility(VISIBLE);
            liked.setVisibility(GONE);
            unliked.invalidate();
            liked.invalidate();


        }
    }

}
