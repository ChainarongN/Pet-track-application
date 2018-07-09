package com.example.ngz.pettrackapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Choice extends AppCompatActivity {

    private TextView email;
    private Button btn1,btn2,btn3,edit,logout;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener AuthListener;
    private Usermanage usermanage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        email = (TextView)findViewById(R.id.email);
        btn1 = (Button)findViewById(R.id.button2);
        btn2 = (Button)findViewById(R.id.button3);
        btn3 = (Button)findViewById(R.id.button4);
        edit = (Button)findViewById(R.id.edit);
        logout = (Button)findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        context =this;
        usermanage = new Usermanage(context);
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Boolean isTrue = usermanage.checkLoginValidate(user.getEmail());
                    if(isTrue){
                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Choice.this,Choice_share.class);
                                startActivity(i);
                            }
                        });
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Choice.this,Choice_view.class);
                                startActivity(i);
                            }
                        });
                        btn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Choice.this,Choice_position.class);
                                startActivity(i);
                            }
                        });

                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Choice.this,Edit_Profile.class);
                                startActivity(i);
                            }
                        });

                        logout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent loginscreen = new Intent(Choice.this,MainActivity.class);
                                usermanage.removeKey();
//                loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(loginscreen);
                                finish();
                            }
                        });
                    }
                    else {
                        Intent intent = new Intent(context,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
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
