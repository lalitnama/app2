package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import model.ContributedItem;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import util.Constants;

public class PostActivity extends AppCompatActivity {


    private String userId, learnTrailId, trailStationId;
    private String fileName;

    private ImageView newPostImage;
    private EditText newPostDesc,textTitle;
    private static final int GALLERY_REQUEST_CODE = 2;
    private Button newPostBtn;

    private Uri postImageUri = null;

    private ProgressBar newPostProgress;

    private StorageReference storage;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storage = FirebaseStorage.getInstance().getReference();
        databaseRef = database.getInstance().getReference("ContributedItem");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Account").child(mCurrentUser.getUid());


        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra(Constants.USER_ID);
            trailStationId = intent.getStringExtra(Constants.TRIAL_STATION_ID);
            learnTrailId = intent.getStringExtra(Constants.LEARN_TRAIL_ID);
        }
        newPostImage = findViewById(R.id.new_post_image);
        newPostDesc = findViewById(R.id.new_post_desc);
        textTitle = findViewById(R.id.textTitle);
        newPostProgress = findViewById(R.id.new_post_progress);
        newPostBtn= findViewById(R.id.post_save);

       // Button post_browseBtn = findViewById(R.id.btn_browseFile);
        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc = newPostDesc.getText().toString();

                if(!TextUtils.isEmpty(desc) && postImageUri != null) {

                    newPostProgress.setVisibility(View.VISIBLE);

                    final String randomName = UUID.randomUUID().toString();

                    // PHOTO UPLOAD

                    StorageReference filePath = storage.child("post_images").child(postImageUri.getLastPathSegment());

                    filePath.putFile(postImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            //getting the post image download url
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(getApplicationContext(), "Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                           // final DatabaseReference newPost = databaseRef.push();
                            //adding post contents to database reference
                            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final String randomName = UUID.randomUUID().toString();
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("ContributedItem");
                                    String itemId = db.push().getKey();
                                    ContributedItem item = new ContributedItem(trailStationId, learnTrailId, mCurrentUser.getUid(), downloadUrl.toString(),textTitle.getText().toString(), newPostDesc.getText().toString(), "", itemId);
                                    db.child(itemId).setValue(item)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                                                    if (task.isSuccessful()){


                                                        Intent intent = new Intent(PostActivity.this, PostDetailActivity.class);
                                                        intent.putExtra(Constants.USER_ID, userId);
                                                        intent.putExtra(Constants.LEARN_TRAIL_ID, learnTrailId);
                                                        intent.putExtra(Constants.TRIAL_STATION_ID, trailStationId);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                    //setResult(1);
                                    //finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });






                }
            }
        });






    }


    @Override
    // image from gallery result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            postImageUri = data.getData();
            newPostImage.setImageURI(postImageUri);
        }
    }
}
