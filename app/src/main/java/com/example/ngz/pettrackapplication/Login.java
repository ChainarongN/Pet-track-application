package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener AuthListener;
    private ProgressBar progressDialog;
    private DatabaseReference databaseUser;
    private FirebaseDatabase database;
    private String userID, status = "";
    private Usermanage usermanage;
    Context context;
    private static final String TAG = "test123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        usermanage = new Usermanage(context);
        database = FirebaseDatabase.getInstance();
        databaseUser = database.getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();

        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Boolean isTrue = usermanage.checkLoginValidate(user.getEmail());
                    if(isTrue){
                        databaseUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    userID = user.getUid();
                                    if (ds.getKey().equals(userID)) {
                                        status = String.valueOf(ds.child("_status").getValue());
                                    }
                                }
                                checkStatus();
                                Log.d(TAG,"data"+ dataSnapshot.getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG,"Failed to read value.", databaseError.toException());
                            }
                        });
                    }
                    else {
                        login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (TextUtils.isEmpty(email.getText().toString().trim())) {
                                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (TextUtils.isEmpty(password.getText().toString().trim())) {
                                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                final ProgressDialog progressDialog = ProgressDialog.show(Login.this,
                                        "Please wait...", "Processing...", true);

                                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                        .addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressDialog.dismiss();
                                                Boolean isTrue = usermanage.registerUser(email.getText().toString());
                                                if (isTrue) {
                                                    if (task.isSuccessful()) {
                                                        databaseUser.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                    userID = user.getUid();
                                                                    if (ds.getKey().equals(userID)) {
                                                                        status = String.valueOf(ds.child("_status").getValue());
                                                                        break;
                                                                    }
                                                                }
                                                                checkStatus();
                                                            }
                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                                Log.w("Failed to read value.", databaseError.toException());
                                                            }
                                                        });
                                                    } else {
                                                        Log.e("ERROR", task.getException().toString());
                                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }

                                                } else {
                                                    Toast.makeText(Login.this, "Login failed !!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        });
                    }
                }
            }
        };

    }

    private void checkStatus() {
        if (status.equals("User")) {
//            Toast.makeText(getApplicationContext(), "status " + status, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, Choice.class);
            startActivity(i);
            finish();
        } else if (status.equals("Admin")) {
//            Toast.makeText(getApplicationContext(), "status " + status, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, Choice_admin.class);
            startActivity(i);
            finish();
        }
        else if (status.equals("Block")) {
            Toast.makeText(getApplicationContext(), "You have been block", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(AuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListener != null) {
            firebaseAuth.removeAuthStateListener(AuthListener);
        }
    }
}
