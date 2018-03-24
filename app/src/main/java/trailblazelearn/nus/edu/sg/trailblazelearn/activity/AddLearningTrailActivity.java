package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.text.InputType;
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

import adapter.LearningTrailAdapter;
import fao.ManageLearningTrail;
import model.LearningTrial;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;

public class AddLearningTrailActivity extends AppCompatActivity {

    private Button saveBtn, bAdd;
    private EditText trailName, trailID, userID,addTrailDate;
    DatePickerDialog datePickerDialog;
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());


    private RecyclerView recyclerView;
    private LearningTrailAdapter mAdapter;
    DatabaseReference db;
    ManageLearningTrail trailhelper;



    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_learning_trail);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);


      //  setSupportActionBar(toolbar);

      //  ActionBar actionBar = getSupportActionBar();
       // if (actionBar != null) {
        //    actionBar.setDisplayHomeAsUpEnabled(true);
       // }
        trailName= (EditText) findViewById(R.id.addTrailName);
        trailID= (EditText) findViewById(R.id.addTrailID);
        userID= (EditText) findViewById(R.id.addUserID);
        addTrailDate= (EditText) findViewById(R.id.addTrailDate);
        Button saveBtn= (Button) findViewById(R.id.btn_save);
       // setListeners();

        addTrailDate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                addTrailDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddLearningTrailActivity.this, onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(AddLearningTrailActivity.this, MainActivity.class);
                    startActivity(i);
                }else{

                }

            }
        };


        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference();
        trailhelper=new ManageLearningTrail(db);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLearningTrail();
            }
        });

    }








    private void getLearningTrail()
    {
        String tailname=trailName.getText().toString();
        String trailid=trailID.getText().toString();
        String userid=userID.getText().toString();
        String id = db.push().getKey();
        String username=userID.getText().toString();
        Date c = Calendar.getInstance().getTime();

       // String g=addTrailDate.getText().toString();

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

                Toast.makeText(AddLearningTrailActivity.this, getString(R.string.save_successful),
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddLearningTrailActivity.this, LearningTrailActivity.class);
                startActivity(i);
              //  mAdapter=new LearningTrailAdapter(this,trailhelper.retrieve());
               // recyclerView.setAdapter(mAdapter);


            }
        }else
        {
            Toast.makeText(AddLearningTrailActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
        }
    }


}
