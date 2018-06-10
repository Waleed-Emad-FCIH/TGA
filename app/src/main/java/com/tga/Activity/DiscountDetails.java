package com.tga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tga.Controller.AgentController;
import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.R;

public class DiscountDetails extends AppCompatActivity {


    private ImageView imgBack, imgDiscountsEdit, imgDiscountsDelete, img;
    private TextView txtTitle, txtCompany, txtDiscount, txtProgramDesc, txtDiscountEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgDiscountsEdit = (ImageView)findViewById(R.id.imgDiscountsEdit);
        img = (ImageView)findViewById(R.id.img);
        imgDiscountsDelete = (ImageView)findViewById(R.id.imgDiscountsDelete);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtCompany = (TextView) findViewById(R.id.txtCompany);
        txtDiscount = (TextView) findViewById(R.id.txtDiscount);
        txtProgramDesc = (TextView) findViewById(R.id.txtProgramDesc);
        txtDiscountEndDate = (TextView) findViewById(R.id.txtDiscountEndDate);

        String progID = getIntent().getStringExtra("PROG_ID");
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(final ProgramController pc) {
                txtTitle.setText(pc.getTitle());
                AgentController.getByID(new SimpleCallback<AgentController>() {
                    @Override
                    public void callback(AgentController ac) {
                        if (ac != null){
                            txtTitle.setText(pc.getTitle());
                            txtCompany.setText(ac.getName());
                            String s = String.valueOf(pc.getDiscountPercentage() * 100) + "%";
                            txtDiscount.setText(s);
                            txtProgramDesc.setText(pc.getDescription());
                            txtDiscountEndDate.setText(pc.getDiscountEndDate());

                            imgBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onBackPressed();
                                }
                            });

                            imgDiscountsEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(),EditDiscount.class);
                                    intent.putExtra("PROG_ID", pc.getId());
                                    startActivity(intent);
                                }
                            });

                            imgDiscountsDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pc.delDiscount();
                                    pc.updateToDB();
                                    Toast.makeText(getApplicationContext(), "Discount deleted successfully", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            });
                        }
                    }
                }, pc.getOwnerID());

            }
        }, progID);
    }
}
