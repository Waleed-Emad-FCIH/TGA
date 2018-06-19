package com.tga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.Controller.AgentController;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.Controller.SimpleSession;
import com.tga.Controller.TouristController;
import com.tga.R;

import java.util.ArrayList;

public class ProgDetails extends AppCompatActivity {
    private TextView title, desc;
    private ImageView imgBack, imgProgramEdit, imgProgramDel, imgAddDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog_details);
        Intent intent = getIntent();

        if (SimpleSession.isNull()){
            Toast.makeText(getApplicationContext(), "Oops.. Session Expired", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        }

        String prog_id = intent.getStringExtra("PROG_ID");
        SimpleSession session = SimpleSession.getInstance();

        title = (TextView) findViewById(R.id.txtTitle);
        desc = (TextView) findViewById(R.id.progDesc);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgProgramEdit = (ImageView)findViewById(R.id.imgProgramEdit);
        imgProgramDel = (ImageView)findViewById(R.id.imgProgramDelete);
        imgAddDiscount = (ImageView)findViewById(R.id.imgAddDiscount);

        //TODO: photos of places
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(final ProgramController pc) {
                if (pc != null) {
                    String t = pc.getTitle();
                    if (pc.getPrice() != 0)
                        t.concat("  (" + String.valueOf(pc.getPrice()) + " L.E)");
                    title.setText(t);
                    desc.setText(pc.getDescription());

                    imgProgramEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), EditProgram.class);
                            intent.putExtra("PROG_ID", pc.getId());
                            startActivity(intent);
                        }
                    });

                    imgProgramDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TODO: Check current user is Agent or Tourist
                            TouristController.getByID(new SimpleCallback<TouristController>() {
                                @Override
                                public void callback(TouristController data) {
                                    if (data != null) {
                                        data.delProgram(pc.getId());
                                    }
                                    pc.delFromDB();
                                    Toast.makeText(getApplicationContext(), "Program successfully deleted", Toast.LENGTH_SHORT).show();
                                   onBackPressed();
                                }

                            }, pc.getOwnerID());
                        }
                    });

                    imgBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });

                    if (session.getUserRole() == SimpleSession.AGENT_ROLE &&
                            ((AgentController)session.getUserObj()).getId().equals(pc.getOwnerID())){
                        imgAddDiscount.setVisibility(View.VISIBLE);
                    }

                    imgAddDiscount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i;
                            if (pc.getDiscountID().isEmpty())
                                i = new Intent(view.getContext(), AddDiscounts.class);
                            else
                                i = new Intent(view.getContext(), EditDiscount.class);
                            i.putExtra("PROG_ID", prog_id);
                            startActivity(i);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Program has been deleted", Toast.LENGTH_LONG).show();
                   // onBackPressed();
                }
            }
        }, prog_id);
    }
}
