package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_admin extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ListView listViewArtists;
    private List<Database_Register> artistList;
    private adminListAdapter adminListAdapter;
    private ProgressDialog progressDialog;
    private Database_Register regis;
    private String uid = "";
    String petCategory_Admin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        artistList = new ArrayList<>();
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        regis = new Database_Register();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list ...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("User");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    regis = snapshot.getValue(Database_Register.class);
                    artistList.add(regis);

                }
                adminListAdapter = new adminListAdapter(Activity_admin.this, R.layout.list_layout, artistList);
                listViewArtists.setAdapter(adminListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                regis = artistList.get(position);
                uid = regis.get_Uid();
                showUpdateDialog(regis.get_Email(), regis.get_username(), regis.get_Telephone(), regis.get_status());

            }
        });
    }

    private void showUpdateDialog(String artistEmail, String artlistName, String artlistTel, String artlistStatus) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] pet = getResources().getStringArray(R.array.select_admin);
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextTel = (EditText) dialogView.findViewById(R.id.editTextTel);
//        final EditText editTextStatus = (EditText) dialogView.findViewById(R.id.editTextStatus);
        dialogBuilder.setSingleChoiceItems(R.array.select_admin, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                petCategory_Admin = pet[id];
            }
        });


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDel = (Button) dialogView.findViewById(R.id.buttonDel);
        editTextName.setText(artlistName);
        editTextTel.setText(artlistTel);
//        editTextStatus.setText(artlistStatus);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child(uid).child("_username").setValue(editTextName.getText().toString());
                mDatabaseRef.child(uid).child("_Telephone").setValue(editTextTel.getText().toString());
                mDatabaseRef.child(uid).child("_status").setValue(petCategory_Admin);
                finish();
                startActivity(getIntent());
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child(uid).removeValue();
                finish();
                startActivity(getIntent());
            }
        });

        dialogBuilder.setTitle("Updating " + artistEmail);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
