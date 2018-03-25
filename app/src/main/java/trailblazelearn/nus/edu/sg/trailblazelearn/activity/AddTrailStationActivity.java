package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail_station);


        trailStationName= (EditText) findViewById(R.id.addTrailStationName);
        sequenceID= (EditText) findViewById(R.id.addTrailSequenceID);
        instruction= (EditText) findViewById(R.id.addInstruction);
        geolocation= (EditText) findViewById(R.id.addGeoLocation);
        Button saveBtn1= (Button) findViewById(R.id.btn_tail_save);

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
        String tailstnname=trailStationName.getText().toString();
        String seqid=sequenceID.getText().toString();
        String instruc=instruction.getText().toString();
        String id = db.push().getKey();
        String geo=geolocation.getText().toString();
        int seq = Integer.parseInt(seqid);

        Intent j= getIntent();
        String trailidval=j.getExtras().getString("LEARNING_TRAIL_ID");
        String trailname=j.getExtras().getString("LEARNING_TRAIL_NAME");
        String userid=j.getExtras().getString("USER_ID");

        //SET DATA
        TrailStation s=new TrailStation(id,trailidval,geo,tailstnname,instruc,seq);




        //SIMPLE VALIDATION
        if(tailstnname != null && tailstnname.length()>0)
        {
            //THEN SAVE
            if(trailstationhelper.trailstationsave(s))
            {
                //IF SAVED CLEAR EDITXT

                Toast.makeText(AddTrailStationActivity.this, getString(R.string.save_successful),
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddTrailStationActivity.this, LearningTrailDetailActivity.class);
                i.putExtra("LEARNING_TRAIL_ID",trailidval);
                i.putExtra("LEARNING_TRAIL_NAME",trailname);
                i.putExtra("USER_ID",userid);
                startActivity(i);
                //  mAdapter=new LearningTrailAdapter(this,trailhelper.retrieve());
                // recyclerView.setAdapter(mAdapter);


            }
        }else
        {
            Toast.makeText(AddTrailStationActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
        }
    }
}
