package com.tga.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.tga.R;
import com.tga.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login extends AppCompatActivity {

    private TextView txtCreate;
    private TextView txtLogin;
    private TextView txtForgetPassword;
    private ImageView imgGoogle;
    private User user;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;

    private static final int RC_SIGN_IN = 0 ;
    private GoogleApiClient mGoogleApiClient;
    //Add YOUR Firebase Reference URL instead of the following URL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCreate = (TextView)findViewById(R.id.create);
        txtLogin = (TextView) findViewById(R.id.txtlogin);
        txtForgetPassword = (TextView) findViewById(R.id.txtForgotPassword);
        imgGoogle = (ImageView)findViewById(R.id.imgGoogle);
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    // Make the call to GoogleApiClient
                    signIn();
                }
            }
        });

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null) {

            String uid = mAuth.getCurrentUser().getUid();
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("user_id", uid);
            startActivity(i);
            finish();

        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    // Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    // Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    // Make the call to GoogleApiClient
                    signIn();
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        email = (EditText) findViewById(R.id.etxtEmail);
        password = (EditText) findViewById(R.id.etxtPassword);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpUser();
                signIn(email.getText().toString(), password.getText().toString());
            }
        });

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount token) {
        //Log.d(TAG, "signInWithGoogle:" + token);

        showProgressDialog();


        AuthCredential credential = GoogleAuthProvider.getCredential(token.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            final String uid=task.getResult().getUser().getUid();
                            final String name=task.getResult().getUser().getDisplayName();
                            final String email=task.getResult().getUser().getEmail();
                            final String image=task.getResult().getUser().getPhotoUrl().toString();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            Query query = database.getReference("users");
                            query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                @Override
                                public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {

                                    try {
                                        HashMap<String, User> results = snapshot.getValue(new GenericTypeIndicator<HashMap<String, User>>() {});
                                        List<User> users = new ArrayList<>(results.values());
                                        boolean flag=false;
                                        for (User user:users){
                                            if (user.getId().equals(uid)){
                                                flag=true;
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                                hideProgressDialog();
                                            }
                                        }
                                        if (flag==false){
                                            User user = new User(uid, name, "",  email,  "",  "",
                                                    "",image,"","");
                                            FirebaseDatabase mRef1 = FirebaseDatabase.getInstance();
                                            DatabaseReference users1 = mRef1.getReference("users");
                                            users1.child(uid).setValue(user);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();

                                            hideProgressDialog();
                                        }
                                    }
                                    catch (Exception e){
                                        //Create a new User and Save it in Firebase database

                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
    }

    protected void setUpUser() {
        user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
    }

    private void signIn(String email, String password) {
        //Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Check you email or password",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            String uid = mAuth.getCurrentUser().getUid();
                            intent.putExtra("user_id", uid);
                            startActivity(intent);
                            finish();

//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            String uid = mAuth.getCurrentUser().getUid();
//                            intent.putExtra("user_id", uid);
//                            startActivity(intent);
//                            finish();
                        }

                        hideProgressDialog();
                    }
                });
        //
    }

    private boolean validateForm() {
        boolean valid = true;

        String userEmail = email.getText().toString();
        if (TextUtils
                .isEmpty(userEmail)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
