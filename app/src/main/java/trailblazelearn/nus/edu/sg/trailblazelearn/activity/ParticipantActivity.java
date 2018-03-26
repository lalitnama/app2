package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import trailblazelearn.nus.edu.sg.trailblazelearn.R;

public class ParticipantActivity extends AppCompatActivity {

    String trailId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        final EditText trailIdEt = findViewById(R.id.editText2);
        final Button getTrailBtn = findViewById(R.id.button2);

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

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParticipantActivity.this, TrailStationsListActivity.class);
                i.putExtra("trail_id", trailId);
                startActivity(i);
            }
        });
    }
}
