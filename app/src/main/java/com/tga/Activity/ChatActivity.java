package com.tga.Activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.R;
import com.tga.adapter.CustomAdapter;
import com.tga.models.ChatMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;




    public class ChatActivity extends AppCompatActivity {
        String SenderName;

        private RecyclerView mUsersList;
        private DatabaseReference mConversationReferences;
        DatabaseReference   mDatabaseRefernce= FirebaseDatabase.getInstance().getReference().child("tourists");
        ArrayList<ChatMessage> ChatMessages = new ArrayList<>();
        ArrayList<String> previousMessages= new ArrayList<>();
        private ProgressDialog mPrgress;
        ImageButton fab;
        EditText mInputMessage;
        private ListView listOfMessages;
        FirebaseUser mCurrentUser;
        String user_id;
        CustomAdapter adapter;
        CircleImageView mDisplayImage;

        Integer flag=1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            user_id = getIntent().getStringExtra("user_id");

            mConversationReferences = FirebaseDatabase.getInstance().getReference().child("conversation");
            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            mPrgress = new ProgressDialog(this);
            mInputMessage = (EditText) findViewById(R.id.input);
            fab = (ImageButton) findViewById(R.id.fab);
            listOfMessages = (ListView) findViewById(R.id.list_of_messages);


            adapter = new CustomAdapter(this,Retrieve(),mCurrentUser.getUid(),user_id);
            listOfMessages.setAdapter(adapter);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newMessage = mInputMessage.getText().toString();
                    if(!TextUtils.isEmpty(newMessage))
                    {
                        Date currentTime = Calendar.getInstance().getTime();
                        final   String currentDate = currentTime.toString();
                        HashMap<String ,String> messageMap= new HashMap<String, String>();


                        messageMap.put("message_sender",mCurrentUser.getUid());
                        messageMap.put("message_text",newMessage);
                        messageMap.put("message_time",currentDate);
                        if(flag==1) {

                            mConversationReferences.child(mCurrentUser.getUid()+user_id).push().setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        mInputMessage.setText("");
Toast.makeText(getApplicationContext(),"Message Sent successfully",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else if(flag==2)
                        {

                            mConversationReferences.child(user_id+mCurrentUser.getUid()).push().setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        mInputMessage.setText("");
                                    Toast.makeText(getApplicationContext(),"Message Sent successfully",Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please enter meassge ...",Toast.LENGTH_LONG).show();

                    }
                }
            });


        }

        @Override
        protected void onStart() {
            super.onStart();

        }
        void fetchData(DataSnapshot dataSnapshot)
        {
            for (DataSnapshot ds :dataSnapshot.getChildren())
            {
                ChatMessage c = ds.getValue(ChatMessage.class);

            }
        }

        ArrayList<ChatMessage>Retrieve(){

            mConversationReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(mCurrentUser.getUid()+user_id))
                    {
                        flag=1;
                        mConversationReferences.child(mCurrentUser.getUid()+user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ChatMessages.clear();
                                for(DataSnapshot myitem:dataSnapshot.getChildren()) {
                                    ChatMessage c = new ChatMessage();
                                    c.setMessageText(myitem.child("message_text").getValue().toString());
                                    c.setMessageTime(myitem.child("message_time").getValue().toString());
                                    c.setMessageUser(myitem.child("message_sender").getValue().toString());
                                    ChatMessages.add(c);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else if(dataSnapshot.hasChild(user_id+mCurrentUser.getUid()))
                    {flag=2;
                        mConversationReferences.child(user_id+mCurrentUser.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ChatMessages.clear();

                                for(DataSnapshot myitem:dataSnapshot.getChildren()) {
                                    ChatMessage c = new ChatMessage();
                                    c.setMessageText(myitem.child("message_text").getValue().toString());
                                    c.setMessageTime(myitem.child("message_time").getValue().toString());
                                    c.setMessageUser(myitem.child("message_sender").getValue().toString());
                                    ChatMessages.add(c);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return ChatMessages;
        }






    }


