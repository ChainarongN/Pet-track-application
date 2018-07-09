package com.example.ngz.pettrackapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "TestGPS";
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private DatabaseReference databaseIot, databaseUser;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;
    private String name;
    private Float latitude, longtitude;
    private String get_ex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseIot = database.getReference("Iot");
        databaseUser = database.getReference("User");
        Intent i = getIntent();
        get_ex = i.getStringExtra("1");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String userID = user.getUid();
                    // String name = String.valueOf(ds.child(userID).child("username").getValue());

                    if(ds.getKey().equals(userID)){
                        name = String.valueOf(ds.child("_username").getValue());
//                        Toast.makeText(getApplicationContext(),"name " + name,Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        databaseIot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String username = String.valueOf(ds.child(get_ex).child("_username").getValue());
                    if (!username.equals(name)){
                        Intent i = new Intent(MapsActivity.this,Choice.class);
                        startActivity(i);
                        finish();
                    }
                    else if (username.equals(name)){
                        //getRef ไปหาอะไร
                        if(ds.getRef().child(name) != null){
                            latitude = Float.valueOf(String.valueOf(ds.child(get_ex).child("_lat").getValue()));
                            longtitude = Float.valueOf(String.valueOf(ds.child(get_ex).child("_long").getValue()));
                            mMap.clear();
                        }
                    }
                }
                // Add a marker in Sydney and move the camera
                LatLng _GPS = new LatLng(latitude, longtitude);
                mMap.addMarker(new MarkerOptions()
                        .position(_GPS)
                        .title("Your pet is here"))
                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dog));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(_GPS, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);//เพื่อ add permission ตำแหน่งปัจจุบันของเรา(GPS)
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"fail to read file",databaseError.toException());
            }
        });
    }
}
