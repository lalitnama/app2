package fao;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import model.TrailStation;

/**
 * Created by Asif on 3/24/2018.
 */

public class ManageTrailStation {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<TrailStation> stationTrials=new ArrayList<>();

    public ManageTrailStation(DatabaseReference db) {
        this.db = db;
    }


    public Boolean trailstationsave(TrailStation trailstations)
    {
        if(stationTrials==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("TrailStation").push().setValue(trailstations);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void stationfetchData(DataSnapshot dataSnapshot)
    {
        stationTrials.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            TrailStation trail_station=ds.getValue(TrailStation.class);
            stationTrials.add(trail_station);
        }
    }




    //READ THEN RETURN ARRAYLIST
    public ArrayList<TrailStation> stationretrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                stationfetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                stationfetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return stationTrials;
    }




}
