package com.tga.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.FeedbackModel;


import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class FeedbackController implements DB_Interface {
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();


    private FeedbackModel feedbackModel;


    public FeedbackController(String id, String subject, String content, String userId, boolean isComplain) {
        this.feedbackModel = new FeedbackModel();
        feedbackModel.id = id;
        feedbackModel.subject = subject;
        feedbackModel.content = content;
        feedbackModel.userId = userId;
        feedbackModel.isComplain = isComplain;
    }

    @Override
    public void saveToDB() {
        DatabaseReference reference = mRef.getReference().child("FeedBacks");
        reference.child(feedbackModel.id).setValue(feedbackModel);
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

    public void addFeedback() {

        saveToDB();

    /* no activity */
    }

    public void delFromDB() {
        DatabaseReference reference = mRef.getReference().child("FeedBacks");
        reference.child(feedbackModel.id).setValue(null);

    }

    @Override
    public void updateToDB() {
        DatabaseReference dRef = mRef.getReference("FeedBacks");
        feedbackModel.id = dRef.push().getKey();
        dRef.child(feedbackModel.id).setValue(feedbackModel);
    }

    public ArrayList<FeedbackModel> listAll() {
        final DatabaseReference fRef = mRef.getReference("FeedBacks");
        final ArrayList<FeedbackModel> feedbackModels = new ArrayList<>();

        fRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FeedbackModel feedbackModel = snapshot.getValue(FeedbackModel.class);
                    feedbackModels.add(feedbackModel);
                }

            }

            @Override
            public void onCancelled(DatabaseError DatabaseError) {

            }
        });
        return feedbackModels;

    }
}