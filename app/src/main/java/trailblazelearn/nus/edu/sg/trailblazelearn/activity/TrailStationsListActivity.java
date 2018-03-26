package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import adapter.TrailStationAdapter;
import fao.ManageTrailStation;
import model.TrailStation;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.ItemClickListener;

public class TrailStationsListActivity extends AppCompatActivity implements ItemClickListener {

    private String trailId = null;
    private TrailStationAdapter mstationAdapter;
    DatabaseReference db;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView stationrecyclerView;
    ManageTrailStation trailstationhelper;
    private List<TrailStation> mTrailstationValues;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mstationAdapter=new TrailStationAdapter(TrailStationsListActivity.this,trailstationhelper.stationretrieve(), TrailStationsListActivity.this);
                mTrailstationValues = trailstationhelper.stationretrieve();
                if(mTrailstationValues.size() == 0){
                    Toast.makeText(TrailStationsListActivity.this, "Data Not Available For this ID.",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                }
                stationrecyclerView.setAdapter(mstationAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_stations_list);

        if(getIntent() !=null ){
            trailId = getIntent().getStringExtra("trail_id");
        }

        mAuth = FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(TrailStationsListActivity.this, MainActivity.class);
                    startActivity(i);
                }else{

                }

            }
        };


        stationrecyclerView = findViewById(R.id.trail_station_list);
        stationrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference("TrailStation").child(trailId);
        trailstationhelper=new ManageTrailStation(db);

        mstationAdapter=new TrailStationAdapter(this,trailstationhelper.stationretrieve(), this);
        stationrecyclerView.setAdapter(mstationAdapter);

    }

    @Override
    public void onItemClick(int pos) {
        Intent i = new Intent(this, TrailStationDetailActivity.class);

        startActivity(i);
    }
}
