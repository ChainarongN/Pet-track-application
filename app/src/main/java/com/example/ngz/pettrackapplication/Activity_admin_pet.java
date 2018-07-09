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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_admin_pet extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ListView listViewArtists_pet;
    private List<imageUpload> artistList_pet;
    private adminListAdapter_pet adminListAdapter_pet;
    private ProgressDialog progressDialog;
    private imageUpload regis_img;
    private String uid = "";
    String petCategory_lost = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pet);

        artistList_pet = new ArrayList<>();
        listViewArtists_pet = (ListView) findViewById(R.id.listViewArtists_pet);
        regis_img = new imageUpload();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading ...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("image_lost");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    regis_img = snapshot.getValue(imageUpload.class);
                    artistList_pet.add(regis_img);

                }
                adminListAdapter_pet = new adminListAdapter_pet(Activity_admin_pet.this, R.layout.list_layout_pet_lost, artistList_pet);
                listViewArtists_pet.setAdapter(adminListAdapter_pet);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        listViewArtists_pet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                regis_img = artistList_pet.get(position);
                uid = regis_img.getPet_Uid();
                showUpdateDialog(regis_img.getPet_email(), regis_img.getPet_name(), regis_img.getPet_phone(),regis_img.getPet_Category());
            }
        });
    }

    private void showUpdateDialog(String artistEmail_pet, String artlistName_pet, String artlistTel_pet , String artlistCategory_pet) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] pet = getResources().getStringArray(R.array.select_pet);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.update_dialog_pet_lost, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName_pet = (EditText) dialogView.findViewById(R.id.editTextName_pet_lost);
        final EditText editTextTel_pet = (EditText) dialogView.findViewById(R.id.editTextTel_pet_lost);

        dialogBuilder.setSingleChoiceItems(R.array.select_pet, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                petCategory_lost = pet[id];
            }
        });

        final Button buttonUpdate_pet = (Button) dialogView.findViewById(R.id.buttonUpdate_pet_lost);
        editTextName_pet.setText(artlistName_pet);
        editTextTel_pet.setText(artlistTel_pet);

        buttonUpdate_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child(uid).child("pet_name").setValue(editTextName_pet.getText().toString());
                mDatabaseRef.child(uid).child("pet_phone").setValue(editTextTel_pet.getText().toString());
                mDatabaseRef.child(uid).child("pet_Category").setValue(petCategory_lost);
                finish();
                startActivity(getIntent());
            }
        });

        dialogBuilder.setTitle("Updating " + artistEmail_pet);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
