package com.tga.Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.CommentModel;
import com.tga.models.PostModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PostController  implements DB_Interface{
    
    private PostModel postModel; 
    
    public PostController(String id, String content, String date, String userId,
                          ArrayList<String> commentsID){
        this.postModel = new PostModel();
        postModel.id = id;
        postModel.content = content;
        postModel.date = date;
        postModel.userId = userId;
        postModel.commentsID = commentsID;


    }

    @Override
    public void saveToDB() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");

        postModel.id = dRef.push().getKey();
        dRef.child(postModel.id).setValue(postModel);
    }
    @Override
    public void updateToDB() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(postModel.id).setValue(postModel);
        dRef.child((postModel.id)).child("commentsID").setValue(postModel.commentsID);

    }
    public String getId() {
        return postModel.id;
    }

    public void setId(String id) {
        postModel.id = id;
    }

    public String getContent() {
        return postModel.content;
    }

    public void setContent(String content) {

        postModel.content = content;
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).child("content").setValue(postModel.content);
    }

    public String getDate() {

        return postModel.date;
    }

    public String getUserId() {
        return postModel.userId;
    }

    public ArrayList<CommentModel> getComments()
    {

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final DatabaseReference tRef = fd.getReference("comments");
        Query query = tRef.orderByChild("postId").equalTo(this.getId());
        ArrayList<CommentModel> comments = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CommentModel commentModel = snapshot.getValue(CommentModel.class);
                        comments.add(commentModel);
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return comments;
    }

    public void addComment(String commentID) {
        postModel.commentsID.add(commentID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef=fd.getReference("posts");
        dRef.child(getId()).child("commentsID").setValue(postModel.commentsID);

    }

    public void delCommentId(String comntID){

        postModel.commentsID.remove(comntID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).child(getId()).setValue(postModel.commentsID);
    }
    //
    public void editComment(String comntID){ }

    @Override
    public void delFromDB() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).removeValue();
    }

    public void editPost() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("posts");
        dRef.child(getId()).child("content").setValue(postModel.content);
    }

    public ArrayList<PostModel> listAll() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final DatabaseReference tRef = fd.getReference("posts");
        final ArrayList<PostModel> posts = new ArrayList<>();

        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostModel post = snapshot.getValue(PostModel.class);
                    posts.add(post);
                }
            }
                @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return posts;
    }



    public class Comment {

        private CommentModel commentModel;
        public Comment(String id, String content, String date, String userId){
            this.commentModel = new CommentModel();

            this.commentModel.id = id;
            this.commentModel.content = content;
            this.commentModel.date = date;
            this.commentModel.userId = userId;

        }
        public void createComment()
        {

            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fd.getReference("comments");
            commentModel.id = dRef.push().getKey();
            dRef.child(commentModel.id).setValue(commentModel);
            //add comment Id to post comments Id
            addComment(commentModel.id);

        }


        public String getId() {

            return
                    commentModel.id;
        }

        public String getContent() {
            return commentModel.content;
        }

        public void setContent(String content) {
            commentModel.content = content;
        }

        public String getDate() {
            return commentModel.date;
        }

        public String getUserId() {
            return commentModel.userId;
        }

        public void delComment(){
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fd.getReference("comments");
            dRef.child(commentModel.id).removeValue();
            //delete comment id from post comments id
            delCommentId(commentModel.id);
        }

        public void editComment( String newCommentContent) {
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dRef = fd.getReference("comments");
            setContent(newCommentContent);
            dRef.child(commentModel.id).setValue(commentModel);

        }
    }
}
