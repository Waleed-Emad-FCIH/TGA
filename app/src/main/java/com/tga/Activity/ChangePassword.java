package com.tga.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tga.R;

public class ChangePassword extends AppCompatActivity {

    private TextView etxtUserEmail,etxtCurrentPassword,etxtNewPassword,txtSave;
    FirebaseUser user ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3646")));

        etxtUserEmail = (TextView)findViewById(R.id.etxtUserEmail);
        etxtNewPassword = (TextView)findViewById(R.id.etxtNewPassword);
        txtSave = (TextView)findViewById(R.id.txtSave);
        etxtCurrentPassword = (TextView)findViewById(R.id.etxtCurrentPassword);

        user = FirebaseAuth.getInstance().getCurrentUser();

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(etxtUserEmail.getText().toString(), etxtCurrentPassword.getText().toString());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(etxtNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Password Updated",
                                                            Toast.LENGTH_SHORT).show();
                                                    onBackPressed();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Error Password",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Error authentication failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
