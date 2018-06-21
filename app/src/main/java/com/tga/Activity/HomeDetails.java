package com.tga.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private ImageView imgBack,imgSendMsg, imgFav;
    private ImageView imgProgramEdit, imgProgramDel, imgAddDiscount, image;
    private LinearLayout llFavor, llDistance;
    private String Agent_id;
    GeoDataClient geoDataClient;

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
        imgFav = (ImageView)findViewById(R.id.imgFav);
        llFavor = (LinearLayout) findViewById(R.id.llFavor);
        llDistance = (LinearLayout) findViewById(R.id.llDistance);
        imgAddDiscount = (ImageView)findViewById(R.id.imgAddDiscount);
        image = (ImageView)findViewById(R.id.image);

        imgProgramEdit.setVisibility(View.GONE);
        imgProgramDel.setVisibility(View.GONE);
        bookNow.setVisibility(View.GONE);
        
        if(session.getUserRole() != SimpleSession.TOURIST_ROLE){
            imgSendMsg.setVisibility(View.GONE);
            llFavor.setVisibility(View.GONE);
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

                                imgProgramDel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (session.getUserRole() == SimpleSession.TOURIST_ROLE)
                                            ((TouristController) session.getUserObj()).delProgram(pc.getId());
                                        else
                                            ((AgentController) session.getUserObj()).delProgram(pc.getId());
                                        pc.delFromDB();
                                        Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                                Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                });

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

                                if (session.getUserRole() == SimpleSession.AGENT_ROLE &&
                                        ((AgentController)session.getUserObj()).getId().equals(pc.getOwnerID())){
                                    imgAddDiscount.setVisibility(View.VISIBLE);

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
                                }

                                if (session.getUserRole() == SimpleSession.TOURIST_ROLE){
                                    TouristController tc = (TouristController) session.getUserObj();
                                    if (tc.getMyFavouritPrograms().contains(pc.getId())){
                                        imgFav.setImageResource(R.drawable.heart);
                                        favor.setText("Remove from Favourites");
                                    }
                                    llFavor.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (favor.getText().equals("Remove from Favourites")){
                                                tc.unFavouriteProgram(pc.getId());
                                                favor.setText("Add to Favourites");
                                                imgFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                                            } else {
                                                tc.favouriteProgram(pc.getId());
                                                imgFav.setImageResource(R.drawable.heart);
                                                favor.setText("Remove from Favourites");
                                            }
                                        }
                                    });

                                    bookNow.setVisibility(View.VISIBLE); //only visible it after page load
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
                                }

                                // Load Map latLngs
                                ArrayList<LatLng> latLngs = new ArrayList<>();
                                for(int i = 0 ;  i <pc.getPlacesID().size() ; i++) {
                                    //////////map image//////////////////
                                    try {
                                        geoDataClient = Places.getGeoDataClient(getApplicationContext(), null);
                                        final int[] finalI2 = {i};
                                        geoDataClient.getPlaceById(pc.getPlacesID().get(i)).addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                PlaceBufferResponse places = task.getResult();
                                                Place myPlace = places.get(0);

                                                latLngs.add(myPlace.getLatLng());

                                                places.release();
                                            } else {
                                                Log.e("ProgDetails", "Place not found.");
                                            }
                                        });


                                    } catch (Exception e) {

                                    }
                                }

                                llDistance.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(getApplicationContext(), MapViewActivity.class);
                                        i.putExtra("latLngs" , latLngs);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                });

                                // Load photo slider
                                try {
                                    final int[] x = {0};
                                    Handler handler = new Handler();
                                    final Runnable[] refreshPhoto = new Runnable[1];
                                    refreshPhoto[0] = new Runnable() {
                                        @Override
                                        public void run() {
                                            geoDataClient = Places.getGeoDataClient(getApplicationContext(), null);
                                            geoDataClient.getPlacePhotos(pc.getPlacesID().get(x[0]++ % pc.getPlacesID().size())).addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                                                @Override
                                                public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                                                    // Get the list of photos.
                                                    PlacePhotoMetadataResponse photos = task.getResult();
                                                    // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                                                    PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                                                    // Get the first photo in the list.
                                                    try {

                                                        PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                                                        // Get the attribution text.
                                                        CharSequence attribution = photoMetadata.getAttributions();
                                                        // Get a full-size bitmap for the photo.
                                                        try {
                                                            Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                                                            photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                                                                    try {
                                                                        PlacePhotoResponse photo = task.getResult();
                                                                        Bitmap bitmap = photo.getBitmap();
                                                                        image.setImageBitmap(bitmap);
                                                                    }
                                                                    catch (Exception e)
                                                                    {
                                                                        System.out.println("bitmap");
                                                                    }
                                                                    // Log.v("helllo??/>" , photoi[0].toString());
                                                                }

                                                            });
                                                        } catch (Exception e) {
                                                            System.out.println("Task");
                                                        } finally {
                                                            photoMetadataBuffer.release();
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("PhotoMetaData");
                                                    }
                                                }
                                            });
                                            handler.postDelayed(refreshPhoto[0], 5000);
                                        }
                                    };
                                    if (pc.getPlacesID().size() > 0)
                                        handler.post(refreshPhoto[0]);
                                }
                                catch (Exception e)
                                {

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
