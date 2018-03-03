package trailblazelearn.nus.edu.sg.trailblazelearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Spinner training_mod, spinner2;
    private Button btnGoogleSubmit;
    private Button btnFacebookSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton();
    }

    public void LoginButton() {
        training_mod = (Spinner) findViewById(R.id.trainingmode);
        btnGoogleSubmit = (Button) findViewById(R.id.google_sign_in_button);
        btnFacebookSubmit = (Button) findViewById(R.id.facebook_sign_in_button);

        btnGoogleSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ModuleListActivity.class);
                startActivity(i);

            }
        });
    }

}
