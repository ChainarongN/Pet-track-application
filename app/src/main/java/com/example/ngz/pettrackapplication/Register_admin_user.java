package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_admin_user extends AppCompatActivity {

    private Button btn_Create;
    private EditText editTextName_user_admin,editTextEm_user_admin,editTextPass_user_admin,
            editTextConPass_user_admin,editTextTel_user_admin,editTextStatus_user_admin;
    private RadioGroup radioGroup_status;
    private RadioButton user_status,admin_status;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String status_user_admin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin_user);

        btn_Create = (Button) findViewById(R.id.Create);
        editTextName_user_admin = (EditText) findViewById(R.id.editTextName_user_admin);
        editTextEm_user_admin = (EditText)findViewById(R.id.editTextEm_user_admin);
        editTextPass_user_admin = (EditText)findViewById(R.id.editTextPass_user_admin);
        editTextConPass_user_admin = (EditText) findViewById(R.id.editTextConPass_user_admin);
        editTextTel_user_admin = (EditText) findViewById(R.id.editTextTel_user_admin);
//        editTextStatus_user_admin = (EditText) findViewById(R.id.editTextStatus_user_admin);
        radioGroup_status = findViewById(R.id.radioGroup_Status_user_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(editTextName_user_admin.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(editTextEm_user_admin.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(editTextPass_user_admin.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextPass_user_admin.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (TextUtils.isEmpty(editTextStatus_user_admin.getText().toString().trim())) {
//                    Toast.makeText(getApplicationContext(), "Enter Status!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                final ProgressDialog progressDialog = ProgressDialog.show(Register_admin_user.this,
                        "Please wait...","Processing...",true);

                (firebaseAuth.createUserWithEmailAndPassword(editTextEm_user_admin.getText().toString(),editTextPass_user_admin.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();


                                if(task.isSuccessful()){

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String userID = user.getUid();
                                    String email_user_admin = user.getEmail();
                                    String name_user_admin = editTextName_user_admin.getText().toString();
                                    String pass_user_admin = editTextPass_user_admin.getText().toString();
                                    String tel1_user_admin = editTextTel_user_admin.getText().toString();
//                                    String status_user_admin = editTextStatus_user_admin.getText().toString();
                                    int idRadio = radioGroup_status.getCheckedRadioButtonId();
                                    user_status = findViewById(idRadio);
                                    admin_status = findViewById(idRadio);
                                    status_user_admin = user_status.getText().toString();
                                    status_user_admin = admin_status.getText().toString();


                                    Database_Register database1 = new Database_Register(name_user_admin,email_user_admin,pass_user_admin,tel1_user_admin,userID,status_user_admin);
                                    databaseReference.child("User").child(userID).setValue(database1);

                                    Toast.makeText(Register_admin_user.this,"Create Successful",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Register_admin_user.this,Activity_admin.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    Log.e("ERROR",task.getException().toString());
                                    Toast.makeText(Register_admin_user.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });
    }
}
