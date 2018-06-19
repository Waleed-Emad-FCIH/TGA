package com.tga.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tga.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tga.models.ChatMessage;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<ChatMessage> chatMessages;
    String SenderName,ReceiverName;
    private DatabaseReference mDatabaseRefernce= FirebaseDatabase.getInstance().getReference().child("tourists");


    public CustomAdapter(Context c, ArrayList<ChatMessage> chatMessages,String Sender,String Reciever) {
        this.c = c;
        this.chatMessages = chatMessages;
        this.SenderName=Sender;
        this.ReceiverName=Reciever;
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;

    }
    String thumb_image,image;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.list_item,parent,false);
        }

        TextView messageTxt=(TextView)convertView.findViewById(R.id.message_text);
        TextView messageTime=(TextView)convertView.findViewById(R.id.message_time);
        final CircleImageView SenderImage =convertView.findViewById(R.id.sender_image);
        final   ChatMessage chatMessage = (ChatMessage) this.getItem(position);
        RelativeLayout ln = (RelativeLayout) convertView.findViewById(R.id.message_Layout);

        if(chatMessage.getMessageUser().equals(SenderName))
        {
            mDatabaseRefernce.child(SenderName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        else{
            ln.setBackgroundColor(Color.alpha(R.drawable.rounded));
            messageTxt.setTextColor(Color.BLACK);
            messageTime.setTextColor(Color.BLACK);
            mDatabaseRefernce.child(ReceiverName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        messageTime.setText(chatMessage.getMessageTime());
        messageTxt.setText(chatMessage.getMessageText());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,chatMessage.getMessageUser(),Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

}
