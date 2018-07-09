package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Share_lost extends AppCompatActivity {

//    private TextView email_share;
    private Button choose_image,upload;
    private EditText pet_name,pet_email,pet_phone,pet_Category;
    private ImageView img1;
    private ProgressDialog mProgressDialog;
    private Uri uri;
    private StorageReference mStorage;
    private int MY_READ_PERMISSION_REQUEST_CODE = 1;
    private final int GALLERY_INTENT = 2;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, databaseReference_user;
    private RadioGroup radioGroup;
    private RadioButton dog,cat;
    public static final String FB_DATABASE_PATH = "image_lost";
    String petCategory = "";
    private String email = "", tel = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_lost);

        mStorage = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        mProgressDialog = new ProgressDialog(this);

//        email_share = (TextView)findViewById(R.id.email_share);
//        email_share.setText(getIntent().getExtras().getString("Email"));

        choose_image =(Button)findViewById(R.id.choose_image);
        upload = (Button)findViewById(R.id.upload);
        img1 = (ImageView)findViewById(R.id.imageView);
        pet_name = (EditText)findViewById(R.id.pet_name);
        pet_email = (EditText)findViewById(R.id.pet_email);
        pet_phone = (EditText)findViewById(R.id.pet_phone);
        //pet_Category = (EditText)findViewById(R.id.pet_Category);
        radioGroup = findViewById(R.id.radioGroup);
//        dog = findViewById(R.id.radioDog);
//        cat = findViewById(R.id.radioCat);
        database = FirebaseDatabase.getInstance();
        databaseReference_user = database.getReference("User");
        databaseReference_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String userID = user.getUid();
                    if(ds.getKey().equals(userID)) {
                        email = String.valueOf(ds.child("_Email").getValue());
                        tel = String.valueOf(ds.child("_Telephone").getValue());
                        pet_email.setText(email);
                        pet_phone.setText(tel);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHECKGALLERYPERMISSION();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uri != null){
                    mProgressDialog.setMessage("Uploading ... ");
                    mProgressDialog.show();

//
                    StorageReference filepath = mStorage.child("Photos lost").child(uri.getLastPathSegment());

                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                            Upload data images pet in firebase
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String userID = user.getUid();
                            String petName = pet_name.getText().toString();
                            String petEmail= pet_email.getText().toString();
                            String petPhone = pet_phone.getText().toString();
//                            String petCategory = pet_Category.getText().toString();

                            int idRadio = radioGroup.getCheckedRadioButtonId();
                            dog = findViewById(idRadio);
                            cat = findViewById(idRadio);
                            petCategory = dog.getText().toString();
                            petCategory = cat.getText().toString();

                            String uploadId = databaseReference.push().getKey();
                            imageUpload imageUpload = new imageUpload(petName,petEmail,petPhone, petCategory,taskSnapshot.getDownloadUrl().toString(),uploadId);
                            databaseReference.child(uploadId).setValue(imageUpload);


                            Toast.makeText(Share_lost.this,"Upload Done.",Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                            finish();
                            Intent i = new Intent(Share_lost.this,View_lost.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(Share_lost.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else Toast.makeText(Share_lost.this,"Please select image",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CHECKGALLERYPERMISSION() {
        if (ContextCompat.checkSelfPermission(Share_lost.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_READ_PERMISSION_REQUEST_CODE);
            } else {
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        if (requestCode == MY_READ_PERMISSION_REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img1.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
