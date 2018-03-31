package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adapter.TrailStationAdapter;
import fao.ManageTrailStation;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Session;

public class TrailStationActivity extends AppCompatActivity {


    private TrailStationAdapter mAdapter;
    DatabaseReference db;
    ManageTrailStation stationhelper;
    // Session Manager Class
    Session session;

    private Button bAdd;
    private EditText trailIdEt;
    String trailId = null;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_station);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trailIdEt= (EditText) findViewById(R.id.addSessionName);

        final Button getTrailBtn= (Button) findViewById(R.id.btn_enqiry);
        final String lsessionname=trailIdEt.getText().toString();
        db= FirebaseDatabase.getInstance().getReference("TrailStation");


        if(!TextUtils.isEmpty(trailIdEt.getText().toString())){
            trailId = trailIdEt.getText().toString();
            getTrailBtn.setEnabled(true);
        }

        trailIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(trailIdEt.getText().toString())){
                    trailId = trailIdEt.getText().toString();
                    getTrailBtn.setEnabled(true);
                }
            }
        });

        getTrailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(TrailStationActivity.this, TrailStationsListActivity.class);
                i.putExtra("trail_id", trailId);
                startActivity(i);

                //db.addListenerForSingleValueEvent(new ValueEventListener() {
                   // @Override
                  //  public void onDataChange(DataSnapshot dataSnapshot) {
                      //  for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        //    String lsessionname=learningsessionID.getText().toString();
                          //  String name = (String) messageSnapshot.getKey();
                            //Toast.makeText(TrailStationActivity.this, name, Toast.LENGTH_SHORT).show();
                        //}
                    //String dd=dataSnapshot.getKey();


                      //  if(dataSnapshot.getKey()==lsessionname){
                        //    Toast.makeText(TrailStationActivity.this, "user exist", Toast.LENGTH_SHORT).show();
                        //}else{
                          //  Toast.makeText(TrailStationActivity.this, "user doesn't exist", Toast.LENGTH_SHORT).show();
                        //}
                    //}

                   // @Override
                    //public void onCancelled(DatabaseError databaseError) {

                    //}
                //});





            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_participant).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(TrailStationActivity.this, "For Future use.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_trainer:
                Intent i = new Intent(TrailStationActivity.this, LearningTrailActivity.class);
                startActivity(i);
                return true;
            case R.id.action_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    // Intent i = new Intent(LearningTrailActivity.this, MainActivity.class);
                    //startActivity(i);
                    session.logoutUser();
                }else{

                }

            }
        };


    }

}
