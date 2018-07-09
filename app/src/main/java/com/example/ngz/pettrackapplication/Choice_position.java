package com.example.ngz.pettrackapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Choice_position extends AppCompatActivity {
    private final String TAG = "testIOT";

    Button btn1,btn2;
    private DatabaseReference databaseIot, databaseUser;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_position);

        btn1 = (Button)findViewById(R.id.pet_1);
        btn2 = (Button)findViewById(R.id.pet_2);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseIot = database.getReference("Iot");
        databaseUser = database.getReference("User");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Choice_position.this,MapsActivity.class);
                i.putExtra("1","one");
                startActivity(i);
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Choice_position.this,MapsActivity.class);
                i.putExtra("1","two");
                startActivity(i);
                finish();
            }
        });

    }
}
