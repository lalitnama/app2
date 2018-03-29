package trailblazelearn.nus.edu.sg.trailblazelearn.activity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.TrailStationAdapter;
import fao.ManageTrailStation;
import model.TrailStation;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Constants;
import util.ItemClickListener;

public class LearningTrailDetailActivity extends AppCompatActivity implements ItemClickListener {

    private TrailStationAdapter mstationAdapter;
    DatabaseReference db;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView stationrecyclerView;
    TextView nameTxt,descTxt, propTxt;
    ArrayList<TrailStation> trailStationList = new ArrayList<>();
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

                mstationAdapter=new TrailStationAdapter(LearningTrailDetailActivity.this,trailStationList, LearningTrailDetailActivity.this);
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
        setContentView(R.layout.activity_learning_trail_layout);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
        descTxt= (TextView) findViewById(R.id.descDetailTxt);
        propTxt = (TextView) findViewById(R.id.propellantDetailTxt);

        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA
        String trailId=i.getExtras().getString("LEARNING_TRAIL_ID");
        String trailDesc=i.getExtras().getString("LEARNING_TRAIL_NAME");
        userId = i.getExtras().getString("USER_ID");

        //BIND DATA
        nameTxt.setText(trailId);
        descTxt.setText(trailDesc);
        propTxt.setText(userId);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(LearningTrailDetailActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                }

            }
        };


        stationrecyclerView = findViewById(R.id.trail_station_list);
        stationrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference("TrailStation").child(trailId);

        //ADAPTER

        mstationAdapter=new TrailStationAdapter(this,trailStationList, null);
        stationrecyclerView.setAdapter(mstationAdapter);

        Button saveBtn= (Button) findViewById(R.id.add_trail_station_click);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                Intent k = getIntent();
                String trailidval=k.getExtras().getString("LEARNING_TRAIL_ID");
                String trailname=k.getExtras().getString("LEARNING_TRAIL_NAME");
                String userid=k.getExtras().getString("USER_ID");

                Intent i = new Intent(LearningTrailDetailActivity.this, AddTrailStationActivity.class);


                i.putExtra("LEARNING_TRAIL_ID",trailidval);
                i.putExtra("LEARNING_TRAIL_NAME",trailname);
                i.putExtra("USER_ID",userid);

                startActivity(i);
                 //displayInputDialog();

                //Intent k= getIntent();
               // String trailidval=k.getExtras().getString("LEARNING_TRAIL_ID");
                //String useridval=k.getExtras().getString("USER_ID");
                //Toast.makeText(LearningTrailDetailActivity.this, "oclick", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onItemClick(int pos) {
        Intent i = new Intent(this, TrailStationDetailActivity.class);
        i.putExtra(Constants.USER_ID, userId);
        i.putExtra(Constants.LEARN_TRAIL_ID, trailStationList.get(pos).getTrailid());
        i.putExtra(Constants.TRIAL_STATION_ID,trailStationList.get(pos).getTrailstationid());
        i.putExtra(Constants.STATION_NAME, trailStationList.get(pos).getStationname());
        i.putExtra(Constants.INSTRUCTION, trailStationList.get(pos).getInstruction());
        startActivity(i);
    }
}
