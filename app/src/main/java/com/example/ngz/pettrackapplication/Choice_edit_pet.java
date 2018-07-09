package com.example.ngz.pettrackapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choice_edit_pet extends AppCompatActivity {

    private Button btn_lost,btn_sight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_edit_pet);

        btn_lost = (Button)findViewById(R.id.edit_pet_lost);
        btn_sight = (Button)findViewById(R.id.edit_pet_sight);

        btn_lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choice_edit_pet.this,Activity_admin_pet.class);
                startActivity(i);
            }
        });
        btn_sight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choice_edit_pet.this,Activity_admin_pet_sight.class);
                startActivity(i);
            }
        });
    }
}
