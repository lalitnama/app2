package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import model.LearningTrial;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;

public class EditLearningTrailActivity extends AppCompatActivity {


    private Button saveBtn, bAdd;
    private EditText trailName,editTrailDate;
    DatePickerDialog datePickerDialog;
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    String userId = null;
    String trailId = null;

    DatabaseReference db;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_learning_trail);

        if(getIntent() != null)
            trailId = getIntent().getStringExtra("LearningTrailId");
        //Get data to populate
        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference("LearningTrial");


        trailName= (EditText) findViewById(R.id.editTrailName);
        editTrailDate= (EditText) findViewById(R.id.editTrailDate);
        Button saveBtn= (Button) findViewById(R.id.edit_btn_save);
        // setListeners();

        //Now populate in fields added.
        //trailName.setText(lt.getTrailname());
        //and so on.

        editTrailDate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                editTrailDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(EditLearningTrailActivity.this, onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(EditLearningTrailActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    userId = mAuth.getCurrentUser().getUid();
                }

            }
        };

        //INITIALIZE FIREBASE DB
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLearningTrail();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTrailDate:
                datePickerDialog.show();
                break;
            case R.id.edit_btn_save:
                getLearningTrail();
                break;

        }
    }

    private void getLearningTrail()
    {
        try {
            String trailName = this.trailName.getText().toString();
            String g = editTrailDate.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(new Date(g));

            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();

            //SET DATA
            LearningTrial s = new LearningTrial(trailId, userId, formattedDate, trailName, ts, trailName);

            //SIMPLE VALIDATION
            if (trailName != null && trailName.length() > 0) {
                db.child(trailId).setValue(s);
                Toast.makeText(EditLearningTrailActivity.this, getString(R.string.update_successful),
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(EditLearningTrailActivity.this, LearningTrailActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(EditLearningTrailActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(EditLearningTrailActivity.this, "Date format should be valid.", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onTouch(View v, MotionEvent event) {
        final int actionPerformed = event.getAction();

        switch(actionPerformed) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        return false;
    }
}
