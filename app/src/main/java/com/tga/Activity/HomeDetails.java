package com.tga.Activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.Controller.AgentController;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.SimpleSession;
import com.tga.Controller.TouristController;
import com.tga.R;

import java.util.ArrayList;

public class HomeDetails extends AppCompatActivity  {

    private TextView agent, title, desc, reviews, bookNow, favor;
    private ImageView imgBack,imgSendMsg;
    private ImageView imgProgramEdit, imgProgramDel;
    private LinearLayout llFavor;
    private String Agent_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String prog_id = intent.getStringExtra("PROG_ID");
        Agent_id =intent.getStringExtra("user_id");
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (SimpleSession.isNull()) {
            Toast.makeText(getApplicationContext(), "Session has been expired", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }
        SimpleSession session = SimpleSession.getInstance();

        agent = (TextView) findViewById(R.id.txtCompany);
        title = (TextView) findViewById(R.id.txtTitle);
        desc = (TextView) findViewById(R.id.progDesc);
        reviews = (TextView) findViewById(R.id.reviews);
        bookNow = (TextView) findViewById(R.id.txtBookNow);
        favor = (TextView) findViewById(R.id.txtFavor);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgProgramEdit = (ImageView)findViewById(R.id.imgProgramEdit);
        imgProgramDel = (ImageView)findViewById(R.id.imgProgramDelete);
        imgSendMsg = (ImageView)findViewById(R.id.imgSendMsg);
        llFavor = (LinearLayout) findViewById(R.id.llFavor);

        imgProgramEdit.setVisibility(View.GONE);
        imgProgramDel.setVisibility(View.GONE);
        bookNow.setVisibility(View.GONE);
        
        if(session.getUserRole() != SimpleSession.TOURIST_ROLE){
            imgSendMsg.setVisibility(View.GONE);
        }

        //TODO: photos of places
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(final ProgramController pc) {
                if (pc != null) {
                    // TODO : Check for current user is the owner to activate imgs buttons
                    AgentController.getByID(new SimpleCallback<AgentController>() {
                        @Override
                        public void callback(final AgentController data) {
                            if (data != null) {
                                agent.setText(data.getName());
                                String t = pc.getTitle() + "  (" + String.valueOf(pc.getPrice()) + " L.E)";
                                title.setText(t);
                                desc.setText(pc.getDescription());
                                ArrayList<String> rev = pc.getReviews();
                                String revValue = "";
                                for (int i = 0; i < rev.size(); i++) {
                                    revValue += rev.get(i);
                                    if (i != (rev.size() - 1))
                                        revValue += "\n";
                                }
                                reviews.setText(revValue);

                                if (data.getId().equals(userID)){
                                    imgProgramEdit.setVisibility(View.VISIBLE);
                                    imgProgramDel.setVisibility(View.VISIBLE);
                                }

                                imgSendMsg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent chat_intent= new Intent(getApplicationContext(),ChatActivity.class);
                                        chat_intent.putExtra("user_id",Agent_id);
                                        startActivity(chat_intent);
                                    }
                                });

                                imgProgramEdit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getApplicationContext(), EditProgram.class);
                                        intent.putExtra("PROG_ID", pc.getId());
                                        startActivity(intent);
                                    }
                                });

                                imgBack.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                    }
                                });

                                bookNow.setVisibility(View.VISIBLE); //I visible it after page load
                                if (pc.getRegisteredList().contains(userID)) {
                                    bookNow.setText("Cancel Book");
                                }
                                bookNow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (bookNow.getText().equals("BOOK NOW")){
                                            pc.registeTourist(userID);
                                            bookNow.setText("Cancel Book");
                                        } else {
                                            pc.unRegisteTourist(userID);
                                            bookNow.setText("BOOK NOW");
                                        }
                                    }
                                });

                                if (session.getUserRole() == SimpleSession.TOURIST_ROLE){
                                    TouristController tc = (TouristController) session.getUserObj();
                                    if (tc.getMyFavouritPrograms().contains(pc.getId())){
                                        llFavor.setBackgroundResource(R.drawable.round);
                                        favor.setText("Remove from Favourites");
                                    }
                                    llFavor.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (favor.getText().equals("Remove from Favourites")){
                                                tc.unFavouriteProgram(pc.getId());
                                                favor.setText("Add to Favourites");
                                                llFavor.setBackgroundResource(R.color.cardview_light_background);
                                            } else {
                                                tc.favouriteProgram(pc.getId());
                                                llFavor.setBackgroundResource(R.drawable.round);
                                                favor.setText("Remove from Favourites");
                                            }
                                        }
                                    });
                                } else {
                                    llFavor.setVisibility(View.GONE);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Program has been deleted", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        }
                    }, pc.getOwnerID());
                } else {
                    Toast.makeText(getApplicationContext(), "Program has been deleted", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        }, prog_id);


    }


}
