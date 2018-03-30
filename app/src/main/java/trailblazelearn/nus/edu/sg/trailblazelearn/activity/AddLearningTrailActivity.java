package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
    private EditText trailName, trailID, userID, addTrailDate, trailCode;
    DatePickerDialog datePickerDialog;
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    String userId = null;
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
        addTrailDate= (EditText) findViewById(R.id.addTrailDate);
        trailCode = (EditText) findViewById(R.id.addTrailCode);
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
        userId = mAuth.getCurrentUser().getUid();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null){
                    Intent i = new Intent(AddLearningTrailActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    userId = firebaseAuth.getCurrentUser().getUid();
                }

            }
        };




        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference("LearningTrail");
        trailhelper=new ManageLearningTrail(db);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLearningTrail();
            }
        });

    }



    private void saveLearningTrail()
    {
        try {
            String trailname = trailName.getText().toString();
            String trailcode = trailCode.getText().toString();
            String trailDate =addTrailDate.getText().toString();

            //Validation
            if (trailname == null && trailname.length() == 0
                    && trailcode == null && trailcode.length() == 0
                    && trailDate == null && trailDate.length() == 0) {
                Toast.makeText(AddLearningTrailActivity.this, "Trail Name, Trail Code & Trail Date should not be empty", Toast.LENGTH_SHORT).show();
                return;
            } else {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(new Date(trailDate));

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                String trailid = formattedDate+ "-" +trailcode;

                //SET DATA
                LearningTrial s = new LearningTrial(trailid, userId, formattedDate, trailname, ts, trailname);

                db.child(s.getLearningtrailid()).setValue(s);
                Toast.makeText(AddLearningTrailActivity.this, getString(R.string.save_successful),
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddLearningTrailActivity.this, LearningTrailActivity.class);
                startActivity(i);
            }

        }catch (Exception e){
            Toast.makeText(AddLearningTrailActivity.this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
        }
    }


}
