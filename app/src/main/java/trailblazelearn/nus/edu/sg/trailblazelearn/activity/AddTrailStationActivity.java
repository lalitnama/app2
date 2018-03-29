package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import adapter.TrailStationAdapter;
import fao.ManageTrailStation;
import model.TrailStation;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;

public class AddTrailStationActivity extends AppCompatActivity {

    private Button saveBtn1;
    private EditText trailStationName,sequenceID,instruction,geolocation;
    private TrailStationAdapter mstationAdapter;
    DatabaseReference db;
    ManageTrailStation trailstationhelper;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView stationrecyclerView;

    private double longitude;
    private double latitude;
    private final int REQUEST_PERMISSION_FOR_LOCATION = 0x05;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail_station);
        getUserCurrentLocation();

        trailStationName= (EditText) findViewById(R.id.addTrailStationName);
        instruction= (EditText) findViewById(R.id.addInstruction);
        saveBtn1= (Button) findViewById(R.id.btn_tail_save);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(AddTrailStationActivity.this, MainActivity.class);
                    startActivity(i);
                }else{

                }

            }
        };
        Intent i= getIntent();

        db=  FirebaseDatabase.getInstance().getReference("TrailStation").child(i.getExtras().getString("LEARNING_TRAIL_ID"));
        trailstationhelper=new ManageTrailStation(db);

        saveBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLearningTrailStations();
            }
        });

    }



    private void getLearningTrailStations()
    {
        String trailStationName= this.trailStationName.getText().toString();
        String instruc=instruction.getText().toString();
        String stationId = db.push().getKey();
        Intent j= getIntent();
        String lTrailId=j.getExtras().getString("LEARNING_TRAIL_ID");
        String lTrailName=j.getExtras().getString("LEARNING_TRAIL_NAME");
        String userId=j.getExtras().getString("USER_ID");

        //SET DATA
        TrailStation s = new TrailStation(stationId,lTrailId,trailStationName,instruc,latitude, longitude);

        //SIMPLE VALIDATION
        if(trailStationName != null && trailStationName.length()>0)
        {
            db.child(stationId).setValue(s);
            Toast.makeText(AddTrailStationActivity.this, getString(R.string.trail_station_added_successful),
                    Toast.LENGTH_SHORT).show();

            Intent i = new Intent(AddTrailStationActivity.this, LearningTrailDetailActivity.class);
            i.putExtra("LEARNING_TRAIL_ID",lTrailId);
            i.putExtra("LEARNING_TRAIL_NAME",lTrailName);
            i.putExtra("USER_ID",userId);
            startActivity(i);
            finish();
        }else
        {
            Toast.makeText(AddTrailStationActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void getUserCurrentLocation() {
        if (ContextCompat.checkSelfPermission(AddTrailStationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(AddTrailStationActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermissionToAccessLocation();
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (lm != null) {
                LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
                List<String> providers = mLocationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    Location location = mLocationManager.getLastKnownLocation(provider);
                    if (location == null) {
                        continue;
                    }
                    if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        bestLocation = location;
                    }
                }
                Location location = bestLocation;
                if(location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_FOR_LOCATION:
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        askForPermissionToAccessLocation();
                        return;
                    }
                }
                // askForPermissionToAccessLocation();
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public void askForPermissionToAccessLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermission(permissions, REQUEST_PERMISSION_FOR_LOCATION);
        }
    }

    public void requestPermission(String[] permissions, final int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }
}
