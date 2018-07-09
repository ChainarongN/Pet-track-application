package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {

    private EditText username_re,email_re,password_re,conpassword_re,tel_re;
    private Button register;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_re = (EditText)findViewById(R.id.Username);
        email_re = (EditText)findViewById(R.id.email);
        password_re = (EditText)findViewById(R.id.password);
        conpassword_re = (EditText)findViewById(R.id.con_password);
        tel_re = (EditText)findViewById(R.id.tel);
        register = (Button)findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(username_re.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email_re.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_re.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password_re.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog progressDialog = ProgressDialog.show(Register.this,
                        "Please wait...","Processing...",true);


                (firebaseAuth.createUserWithEmailAndPassword(email_re.getText().toString(),conpassword_re.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();


                                if(task.isSuccessful()){

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String userID = user.getUid();
                                    String email = user.getEmail();
                                    String name = username_re.getText().toString();
                                    String pass = password_re.getText().toString();
                                    String tel1 = tel_re.getText().toString();


                                    Database_Register database1 = new Database_Register(name,email,pass,tel1,userID,"User");
                                    databaseReference.child("User").child(userID).setValue(database1);

                                    Toast.makeText(Register.this,"Register Successful",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Register.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    Log.e("ERROR",task.getException().toString());
                                    Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

}
