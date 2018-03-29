package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private EditText learningsessionID;


    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_station);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        learningsessionID= (EditText) findViewById(R.id.addSessionName);

        Button bAdd= (Button) findViewById(R.id.btn_enqiry);
       final String lsessionname=learningsessionID.getText().toString();
        db= FirebaseDatabase.getInstance().getReference("TrailStation").child(lsessionname);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      //  for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        //    String lsessionname=learningsessionID.getText().toString();
                          //  String name = (String) messageSnapshot.getKey();
                            //Toast.makeText(TrailStationActivity.this, name, Toast.LENGTH_SHORT).show();
                        //}
                    String dd=dataSnapshot.getKey();


                        if(dataSnapshot.getKey()==lsessionname){
                            Toast.makeText(TrailStationActivity.this, "user exist", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TrailStationActivity.this, "user doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                // try {
                //     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                // } catch (android.content.ActivityNotFoundException anfe) {
                //   startActivity(new Intent(Intent.ACTION_VIEW,
                //           Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                //  }
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
