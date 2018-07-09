package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_admin_pet_sight extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ListView listViewArtists_pet_sight;
    private List<imageUpload_sight> artistList_pet_sight;
    private adminListAdapter_pet_sight adminListAdapter_pet_sight;
    private ProgressDialog progressDialog;
    private imageUpload_sight regis_img_sight;
    private String uid = "";
    String petCategory_sight = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pet_sight);

        artistList_pet_sight = new ArrayList<>();
        listViewArtists_pet_sight = (ListView) findViewById(R.id.listViewArtists_pet_sight);
        regis_img_sight = new imageUpload_sight();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading ...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("image_sight");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    regis_img_sight = snapshot.getValue(imageUpload_sight.class);
                    artistList_pet_sight.add(regis_img_sight);

                }
                adminListAdapter_pet_sight = new adminListAdapter_pet_sight(Activity_admin_pet_sight.this, R.layout.list_layout_pet_sight, artistList_pet_sight);
                listViewArtists_pet_sight.setAdapter(adminListAdapter_pet_sight);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        listViewArtists_pet_sight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                regis_img_sight = artistList_pet_sight.get(position);
                uid = regis_img_sight.getPet_Uid_sight();
                showUpdateDialog(regis_img_sight.getPet_email_sight(), regis_img_sight.getPet_phone_sight(),regis_img_sight.getPet_Category_sight());

            }
        });
    }
    private void showUpdateDialog(String artistEmail_pet_sight, String artlistTel_pet_sight, String artlistCategory_pet_sight) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] pet = getResources().getStringArray(R.array.select_pet);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.update_dialog_pet_sight, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextTel_pet_sight = (EditText) dialogView.findViewById(R.id.editTextTel_pet_sight);
//        final EditText editTextCategory_pet_sight = (EditText) dialogView.findViewById(R.id.editTextCategory_pet_sight);
        dialogBuilder.setSingleChoiceItems(R.array.select_pet, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                petCategory_sight = pet[id];
            }
        });

        final Button buttonUpdate_pet = (Button) dialogView.findViewById(R.id.buttonUpdate_pet_sight);

        editTextTel_pet_sight.setText(artlistTel_pet_sight);
//        editTextCategory_pet_sight.setText(artlistCategory_pet_sight);

        buttonUpdate_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child(uid).child("pet_phone_sight").setValue(editTextTel_pet_sight.getText().toString());
                mDatabaseRef.child(uid).child("pet_Category_sight").setValue(petCategory_sight);
                finish();
                startActivity(getIntent());
            }
        });

        dialogBuilder.setTitle("Updating " + artistEmail_pet_sight);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
