package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.PostDetailAdapter;
import model.ContributedItem;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Constants;

public class PostDetailActivity extends AppCompatActivity {


    private String userId, learnTrailId, trailStationId;
    private DatabaseReference db;
    private List<ContributedItem> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private int REQUEST_CODE = 1;
    private PostDetailAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        fab = findViewById(R.id.fab_Item);
        recyclerView = findViewById(R.id.post_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra(Constants.USER_ID);
            trailStationId = intent.getStringExtra(Constants.TRIAL_STATION_ID);
            learnTrailId = intent.getStringExtra(Constants.LEARN_TRAIL_ID);
        }

        mAdapter = new PostDetailAdapter(PostDetailActivity.this, itemList);
        recyclerView.setAdapter(mAdapter);


        db = FirebaseDatabase.getInstance().getReference("ContributedItem");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ContributedItem item = ds.getValue(ContributedItem.class);
                    Intent k=getIntent();
                    String trailid=k.getExtras().getString(Constants.TRIAL_STATION_ID);
                    if(item.getTrailStationId().equals(trailid))
                        itemList.add(item);
                }
                mAdapter.setList(itemList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    private void addItem() {
        Intent i = new Intent(this, PostActivity.class);
        i.putExtra(Constants.USER_ID, userId);
        i.putExtra(Constants.LEARN_TRAIL_ID, learnTrailId);
        i.putExtra(Constants.TRIAL_STATION_ID, trailStationId);
        startActivityForResult(i, REQUEST_CODE);
    }
}
