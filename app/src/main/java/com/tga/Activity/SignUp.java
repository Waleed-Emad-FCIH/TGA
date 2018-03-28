package com.tga.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.R;
import com.tga.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();
    private User user;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Spinner country;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private TextView btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.etxtFullName);
        email = (EditText) findViewById(R.id.etxtEmail);
        password = (EditText) findViewById(R.id.etxtPassword);
        confirmPassword=(EditText)findViewById(R.id.etxtCPassword);

        btnSignUp=(TextView) findViewById(R.id.txtSignUp);

        btnSignUp.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (!validateForm()) {
                    return;
                }
                createNewAccount(email.getText().toString(), password.getText().toString());
                showProgressDialog();
            }
        });


        country = (Spinner)findViewById(R.id.spCountry);
        Resources res = getResources();
        String[] items=res.getStringArray(R.array.country_arrays);
        //String[] items = new String[]{"City","Cairo","Giza","Alexandria"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, items);
        country.setAdapter(adapter);

    }

    protected void setUpUser() {
        user = new User();
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setCountry(country.getSelectedItem().toString());
        user.setGender("");
        user.setPhoneNumber("");
        user.setAddress("");
        user.setbDate("");
        user.setProfilePic("");

    }

    private void createNewAccount(String email, String password) {

        setUpUser();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        hideProgressDialog();
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Wrong Email",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            onAuthenticationSucess(task.getResult().getUser());
                        }
                    }
                });

    }

    private void onAuthenticationSucess(final FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(),user.getCountry(),
                user.getGender(),user.getProfilePic(),user.getbDate(),user.getAddress());
        startActivity(new Intent(SignUp.this, MainActivity.class));
        finish();
    }

    private void saveNewUser(String userId, String name, String phone, String email, String password,String country
            , String gender, String profilePic,String bdate, String address) {
        User user = new User(userId, name, phone,  email,  password,  country,
                gender, profilePic, bdate, address);

//        mRef.push().setValue(profileModel);
        //mRef.child("users").child(userId).setValue(user);
        DatabaseReference users = mRef.getReference("users");
        users.child(userId).setValue(user);
    }

    private boolean validateForm() {
        boolean valid = true;

        String userName = name.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            name.setError("Required.");
            valid = false;
        } else {
            name.setError(null);
        }

        String userEmail = email.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userPassword)||userPassword.length()<8) {
            password.setError("Password at least 8 character");
            valid = false;
        } else {
            password.setError(null);
        }

        String userPasswordConfirm = confirmPassword.getText().toString();
        if (!userPasswordConfirm.equals(userPassword)){
            confirmPassword.setError("Wrong");
            valid = false;
        }else {
            confirmPassword.setError(null);
        }

        String city = country.getSelectedItem().toString();
        if(city.equals("City"))
        {
            Toast.makeText(SignUp.this, "Select your city",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}
