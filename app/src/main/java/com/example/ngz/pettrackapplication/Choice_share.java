package com.example.ngz.pettrackapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Choice_share extends AppCompatActivity {

    private Button lost, sight;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_share);

        lost = findViewById(R.id.pet_lost);
        sight = findViewById(R.id.pet_sight);

        firebaseAuth = FirebaseAuth.getInstance();

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice_share.this, Share_lost.class);
                intent.putExtra("Email",firebaseAuth.getCurrentUser().getEmail());
                startActivity(intent);
            }
        });

        sight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice_share.this, Share_sight.class);
                intent.putExtra("Email",firebaseAuth.getCurrentUser().getEmail());
                startActivity(intent);
            }
        });
    }
}
