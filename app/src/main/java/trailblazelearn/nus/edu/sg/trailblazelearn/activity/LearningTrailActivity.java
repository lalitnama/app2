package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.LearningTrailAdapter;
import fao.ManageLearningTrail;
import model.LearningTrial;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Session;

public class LearningTrailActivity extends AppCompatActivity
{

    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private LearningTrailAdapter mAdapter;
    DatabaseReference db;
    ManageLearningTrail trailhelper;
    // Session Manager Class
    Session session;
    private ArrayList<LearningTrial> learningTrails = new ArrayList<>();
    private String userId;
    SwipeController swipeController = null;


    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                learningTrails.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LearningTrial learningTrial = ds.getValue(LearningTrial.class);
                    if (learningTrial.getUserid().equals(userId)) {
                        learningTrails.add(learningTrial);
                    }
                }
                mAdapter = new LearningTrailAdapter(LearningTrailActivity.this, learningTrails);
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




        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(LearningTrailActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    userId = mAuth.getCurrentUser().getUid();
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
        db= FirebaseDatabase.getInstance().getReference("LearningTrail");
        trailhelper=new ManageLearningTrail(db);

        //ADAPTER

        mAdapter=new LearningTrailAdapter(LearningTrailActivity.this,trailhelper.retrieve());
        recyclerView.setAdapter(mAdapter);

        //adding swipe function
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //mAdapter.players.remove(position);
                //learningTrails.remove(position);
                deleteTrail(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                //Redirect to Edit activity with data.
                Intent i = new Intent(LearningTrailActivity.this, EditLearningTrailActivity.class);
                i.putExtra("LearningTrailId", learningTrails.get(position).getLearningtrailid());
                startActivity(i);
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_trainer).setVisible(false);
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
            case R.id.action_participant:
                Intent i = new Intent(LearningTrailActivity.this, TrailStationActivity.class);
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


    public void deleteTrail(int position){
        LearningTrial lt = learningTrails.get(position);
        db.child(lt.getLearningtrailid()).setValue(null);
    }
}
