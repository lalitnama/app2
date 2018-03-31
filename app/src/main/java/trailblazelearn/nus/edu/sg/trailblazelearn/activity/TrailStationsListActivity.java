package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.TrailStationAdapter;
import model.TrailStation;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Constants;
import util.ItemClickListener;

public class TrailStationsListActivity extends AppCompatActivity implements ItemClickListener {

    private String learnTrailId = null;
    private TrailStationAdapter mstationAdapter;
    DatabaseReference db;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView stationrecyclerView;
    private List<TrailStation> trailStationList = new ArrayList<>();
    private String userId;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                trailStationList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    TrailStation trailStation = ds.getValue(TrailStation.class);
                    trailStationList.add(trailStation);
                }
                mstationAdapter=new TrailStationAdapter(TrailStationsListActivity.this, trailStationList, TrailStationsListActivity.this);
                if(trailStationList.size() == 0){
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
            learnTrailId = getIntent().getStringExtra("trail_id");
        }

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(TrailStationsListActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    userId = mAuth.getCurrentUser().getUid();
                }
            }
        };

        stationrecyclerView = findViewById(R.id.trail_station_list);
        stationrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference("TrailStation").child(learnTrailId);

        mstationAdapter=new TrailStationAdapter(this,trailStationList, this);
        stationrecyclerView.setAdapter(mstationAdapter);

    }

    @Override
    public void onItemClick(int pos) {
        Intent i = new Intent(this, PostDetailActivity.class);
        i.putExtra(Constants.USER_ID, userId);
        i.putExtra(Constants.LEARN_TRAIL_ID, learnTrailId);

        String stationid=trailStationList.get(pos).getTrailstationid();
        String stationname =trailStationList.get(pos).getStationname();
        String instruction=trailStationList.get(pos).getInstruction();
        i.putExtra(Constants.TRIAL_STATION_ID,stationid);
        i.putExtra(Constants.STATION_NAME, stationname);
        i.putExtra(Constants.INSTRUCTION, instruction);
        startActivity(i);
    }

}
