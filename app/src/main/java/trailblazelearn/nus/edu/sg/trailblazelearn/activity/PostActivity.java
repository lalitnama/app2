package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.ContributedItem;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Constants;

public class PostActivity extends AppCompatActivity {


    private String userId, learnTrailId, trailStationId;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra(Constants.USER_ID);
            trailStationId = intent.getStringExtra(Constants.TRIAL_STATION_ID);
            learnTrailId = intent.getStringExtra(Constants.LEARN_TRAIL_ID);
        }

        final EditText etDisc = findViewById(R.id.et_discussion);
        fileName = "c://demo.png";
        Button post_saveBtn = findViewById(R.id.post_save);

        Button post_browseBtn = findViewById(R.id.btn_browseFile);


        post_saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("ContributedItem");
                String itemId = db.push().getKey();
                ContributedItem item = new ContributedItem(trailStationId,learnTrailId,userId,fileName,etDisc.getText().toString(),"",itemId);
                db.child(itemId).setValue(item);
                setResult(1);
                finish();
            }
        });
        post_browseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






    }
}
