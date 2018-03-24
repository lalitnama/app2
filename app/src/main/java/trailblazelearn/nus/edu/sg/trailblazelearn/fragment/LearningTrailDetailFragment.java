package trailblazelearn.nus.edu.sg.trailblazelearn.fragment;

/**
 * Created by Asif on 3/18/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adapter.LearningTrailAdapter;
import fao.ManageLearningTrail;
import model.LearningTrial;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;

public class LearningTrailDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String LEARNING_TRAIL_ID = "learningtrailid";
    public static final String LEARNING_TRAIL_NAME = "learningtrailid";
    private LearningTrailAdapter mAdapter;
    DatabaseReference db;
    /**
     * The dummy content this fragment is presenting.
     */
    private LearningTrial mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public LearningTrailDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.module_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.module_detail)).setText(mItem.getTrailname());
        }

        return rootView;
    }

}
