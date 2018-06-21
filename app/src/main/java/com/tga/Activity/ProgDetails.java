package com.tga.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ProgDetails extends AppCompatActivity {
    private TextView title, desc;
    private LinearLayout llDistance;
    private ImageView imgBack, imgProgramEdit, imgProgramDel, imgAddDiscount, image;
    GeoDataClient geoDataClient;

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
        image = (ImageView)findViewById(R.id.image);
        llDistance = (LinearLayout) findViewById(R.id.llDistance);

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
                   // onBackPressed();
                }
            }
        }, prog_id);
    }
}
