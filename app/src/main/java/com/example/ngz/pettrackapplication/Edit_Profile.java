package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Profile extends AppCompatActivity {

    private Button update_profile;
    private EditText name_edit,tel_edit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    String name = "", tel = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        update_profile = (Button) findViewById(R.id.Update_profile);
        name_edit = (EditText) findViewById(R.id.name_edit);
        tel_edit = (EditText) findViewById(R.id.tel_edit);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String userID = user.getUid();
                    if(ds.getKey().equals(userID)) {
                        name = String.valueOf(ds.child("_username").getValue());
                        tel = String.valueOf(ds.child("_Telephone").getValue());
                        name_edit.setText(name);
                        tel_edit.setText(tel);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       update_profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   FirebaseUser user = firebaseAuth.getCurrentUser();
                   String userID = user.getUid();
                   String name = name_edit.getText().toString();
                   String tel1 = tel_edit.getText().toString();

                   databaseReference.child(userID).child("_username").setValue(name);
                   databaseReference.child(userID).child("_Telephone").setValue(tel1);

                   Toast.makeText(Edit_Profile.this,"Edit Successful",Toast.LENGTH_LONG).show();
                   Intent i = new Intent(Edit_Profile.this,Choice.class);
                   startActivity(i);
                   finish();

               }catch (Exception e){
                   Log.e("Some Tag", e.getMessage(), e);
               }
           }
       });
    }
}
