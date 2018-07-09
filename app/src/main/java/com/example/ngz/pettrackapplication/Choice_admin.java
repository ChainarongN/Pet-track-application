package com.example.ngz.pettrackapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choice_admin extends AppCompatActivity {

    private Button btn_edit,btn_createUser_Admin,btn_Edit_pet,btn_logout_admin_pet;
    private Usermanage usermanage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_admin);


        btn_edit = (Button)findViewById(R.id.Edit_del);
        btn_createUser_Admin = (Button) findViewById(R.id.Create_user_Admin);
        btn_Edit_pet = (Button)findViewById(R.id.Edit_pet);
        btn_logout_admin_pet = (Button) findViewById(R.id.logout_admin_pet);
        context = this;
        usermanage = new Usermanage(context);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choice_admin.this,Activity_admin.class);
                startActivity(i);
            }
        });

        btn_createUser_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choice_admin.this,Register_admin_user.class);
                startActivity(i);
            }
        });

        btn_Edit_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choice_admin.this,Choice_edit_pet.class);
                startActivity(i);
            }
        });

        btn_logout_admin_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginscreen = new Intent(Choice_admin.this,MainActivity.class);
                usermanage.removeKey();
                //loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginscreen);
                finish();
            }
        });
    }
}
