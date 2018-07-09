package com.example.ngz.pettrackapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_sight extends AppCompatActivity {


    private DatabaseReference mDatabaseRef;
    private List<imageUpload_sight> imgList_sight;
    private ListView lv;
    private ImaqeListAdapter_sight adapter_sight;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sight);

        imgList_sight = new ArrayList<>();
        lv = (ListView)findViewById(R.id.listViewImage_sight);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Share_sight.FB_DATABASE_PATH1);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    imageUpload_sight img_sight = snapshot.getValue(imageUpload_sight.class);
                    imgList_sight.add(img_sight);
                }
                adapter_sight = new ImaqeListAdapter_sight(View_sight.this,R.layout.image_item_sight,imgList_sight);
                lv.setAdapter(adapter_sight);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
