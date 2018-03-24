package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.LearningTrailAdapter;
import fao.ManageLearningTrail;
import model.LearningTrial;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import trailblazelearn.nus.edu.sg.trailblazelearn.dummy.DummyContent;
import trailblazelearn.nus.edu.sg.trailblazelearn.fragment.LearningTrailDetailFragment;

public class LearningTrailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private LearningTrailAdapter mAdapter;
    DatabaseReference db;
    ManageLearningTrail trailhelper;



    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAdapter=new LearningTrailAdapter(LearningTrailActivity.this,trailhelper.retrieve());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_trail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent i = new Intent(LearningTrailActivity.this, AddLearningTrailActivity.class);
                startActivity(i);
               // displayInputDialog();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(LearningTrailActivity.this, MainActivity.class);
                    startActivity(i);
                }else{

                }

            }
        };
        if (findViewById(R.id.module_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        recyclerView = findViewById(R.id.module_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference();
        trailhelper=new ManageLearningTrail(db);

        //ADAPTER

        mAdapter=new LearningTrailAdapter(LearningTrailActivity.this,trailhelper.retrieve());
        recyclerView.setAdapter(mAdapter);


        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //recyclerView.setAdapter(mAdapter);



    }

    EditText trailName,trailID,userID;

    //DISPLAY INPUT DIALOG
    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.add_learningtrail_dailogue);

        trailName= (EditText) d.findViewById(R.id.addTrailName);
        trailID= (EditText) d.findViewById(R.id.addTrailID);
        userID= (EditText) d.findViewById(R.id.addUserID);
        Button saveBtn= (Button) d.findViewById(R.id.buttonAddtrail);
        Button deleteBtn= (Button) d.findViewById(R.id.buttonDeleteTrail);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String tailname=trailName.getText().toString();
                String trailid=trailID.getText().toString();
                String userid=userID.getText().toString();
                String id = db.push().getKey();
                String username=userID.getText().toString();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                //SET DATA
                LearningTrial s=new LearningTrial(trailid,userid,formattedDate,tailname,ts,tailname);



                //SIMPLE VALIDATION
                if(tailname != null && tailname.length()>0)
                {
                    //THEN SAVE
                    if(trailhelper.save(s))
                    {
                        //IF SAVED CLEAR EDITXT

                        mAdapter=new LearningTrailAdapter(LearningTrailActivity.this,trailhelper.retrieve());
                        recyclerView.setAdapter(mAdapter);


                    }
                }else
                {
                    Toast.makeText(LearningTrailActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.learning_trail, menu);
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
                        Intent i = new Intent(LearningTrailActivity.this, MainActivity.class);
                        startActivity(i);
                    }else{

                    }

                }
            };
        }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
