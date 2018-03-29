package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.ContributeItemAdapter;
import adapter.LearningTrailAdapter;
import model.ContributedItem;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Constants;

public class TrailStationDetailActivity extends AppCompatActivity {

    private String userId, learnTrailId, trailStationId;
    private DatabaseReference db;
    private List<ContributedItem> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private int REQUEST_CODE = 1;
    private ContributeItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_station_detail);
        fab = findViewById(R.id.fab_Item);
        recyclerView = findViewById(R.id.rv_station_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        TextView tvStationName = findViewById(R.id.tv_station_name);
        TextView tvInstruction = findViewById(R.id.tv_instruction);


        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra(Constants.USER_ID);
            trailStationId = intent.getStringExtra(Constants.TRIAL_STATION_ID);
            learnTrailId = intent.getStringExtra(Constants.LEARN_TRAIL_ID);
            tvStationName.setText(intent.getStringExtra(Constants.STATION_NAME));
            tvInstruction.setText(intent.getStringExtra(Constants.INSTRUCTION));
        }

        mAdapter = new ContributeItemAdapter(TrailStationDetailActivity.this, itemList);
        recyclerView.setAdapter(mAdapter);

        db = FirebaseDatabase.getInstance().getReference("ContributedItem");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ContributedItem item = ds.getValue(ContributedItem.class);
                    if(item.getTrailStationId().equals(trailStationId))
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
        Intent i = new Intent(this, AddContributeItemActivity.class);
        i.putExtra(Constants.USER_ID, userId);
        i.putExtra(Constants.LEARN_TRAIL_ID, learnTrailId);
        i.putExtra(Constants.TRIAL_STATION_ID, trailStationId);
        startActivityForResult(i, REQUEST_CODE);
    }
}
