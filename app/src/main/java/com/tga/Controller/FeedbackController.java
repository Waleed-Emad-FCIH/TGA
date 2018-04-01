package com.tga.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.models.FeedbackModel;

/**
 * Created by root on 3/9/18.
 */

public class FeedbackController {
    private FeedbackModel feedbackModel;

    public FeedbackController(String id, String subject, String content, String userId, boolean isComplain){
        this.feedbackModel = new FeedbackModel();
        feedbackModel.id = id;
        feedbackModel.subject = subject;
        feedbackModel.content = content;
        feedbackModel.userId = userId;
        feedbackModel.isComplain = isComplain;
    }

    public String getId() {
        return feedbackModel.id;
    }

    public String getSubject() {
        return feedbackModel.subject;
    }

    public String getContent() {
        return feedbackModel.content;
    }

    public boolean isComplain() {
        return feedbackModel.isComplain;
    }

    public String getUserId() {
        return feedbackModel.userId;
    }
    public void addFeedback(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference feedBack = database.getReference("FeedBacks");
        feedbackModel.id =feedBack.push().getKey();
        feedBack.child(feedbackModel.id).setValue(feedbackModel);

    /* no activity */
    }

}
