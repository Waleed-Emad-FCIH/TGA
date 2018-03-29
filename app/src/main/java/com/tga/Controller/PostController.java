package com.tga.Controller;

import com.tga.models.CommentModel;
import com.tga.models.PostModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PostController {
    
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
    }

    public String getDate() {
        return postModel.date;
    }

    public String getUserId() {
        return postModel.userId;
    }

    public ArrayList<String> getComments() {
        return postModel.commentsID;
    }

    public void addComment(String commentID) {
        postModel.commentsID.add(commentID);
    }

    public void delComment(String comntID){
        postModel.commentsID.remove(comntID);
    }

    public void editComment(String comntID){ }

    public void delPost() { }

    public void editPost() { }
    
    private class Comment {
        private CommentModel commentModel;

        public Comment(String id, String content, String date, String userId){
            commentModel.id = id;
            commentModel.content = content;
            commentModel.date = date;
            commentModel.userId = userId;
        }

        public String getId() {
            return commentModel.id;
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

        public void delComment(){ }

        public void editComment() { }
    }
}
