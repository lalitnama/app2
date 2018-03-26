package trailblazelearn.nus.edu.sg.trailblazelearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import model.Account;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;

public class MainActivity extends AppCompatActivity {

    private Spinner training_mod, spinner2;
    SignInButton Button;
    DatabaseReference db;
    FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    List<Account> accounts;
    List<String> emailval;


    private final static int RC_SIGN_IN=2;
     GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
      //  mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        training_mod = (Spinner) findViewById(R.id.trainingmode);
        SignInButton signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               signIn();

            }
        });
        db = FirebaseDatabase.getInstance().getReference("Account");
        mAuth = FirebaseAuth.getInstance();
        initializeFB();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null){
                   FirebaseUser user=firebaseAuth.getCurrentUser();
                           // addUser(splitStr[0], splitStr[1], email, accounttype);

                    //Toast.makeText(MainActivity.this,splitStr[1] , Toast.LENGTH_SHORT).show();


                        final String email = user.getEmail();

                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                FirebaseUser username=firebaseAuth.getCurrentUser();
                                //if(dataSnapshot.hasChild(email)) {
                                   // Intent i = new Intent(MainActivity.this, LearningTrailActivity.class);
                                  //  startActivity(i);
                                //}else{
                                    String str = username.getDisplayName();
                                    String[] splitStr = str.split("\\s+");
                                    String accounttype = "Google";
                                    //addUser(splitStr[0], splitStr[1], email, accounttype);
                                //}

                              //  boolean val = checkuserexist(email, dataSnapshot);
                                //if (val == false) {
                                //} else {
                                //}

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                }else{



                }

            }
        };




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void initializeFB() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed or account exist with same email ID but different provider.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // Configure Google Sign In
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                    GoogleSignInAccount account=result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
            }else{
                Toast.makeText(MainActivity.this, "Auth went wrong", Toast.LENGTH_SHORT).show();
            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                            //Remove after implementing check with db
                           Intent i = new Intent(MainActivity.this, LearningTrailActivity.class);
                            startActivity(i);


                            Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Auth went wrong", Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private boolean checkuserexist(String email, DataSnapshot dataSnapshot){

        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
            String name = (String) messageSnapshot.child("emailaddress").getValue();
            String message = (String) messageSnapshot.child("firstname").getValue();

            if(name==email){
                return true;
            }
            // emailval.add(name);
        }


        return  false;
    }


    private void  addUser(String firstname,String lastname,String email,String accounttype) {
        //checking if the value is provided
        if (!TextUtils.isEmpty(email)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = db.push().getKey();

            //creating an Artist Object
            Account account = new Account(id, firstname, lastname,email,accounttype,true);

            //Saving the Artist
            db.child(id).setValue(account);

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "User not able to register", Toast.LENGTH_LONG).show();
        }
    }



}
