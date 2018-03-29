package util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import trailblazelearn.nus.edu.sg.trailblazelearn.activity.MainActivity;

/**
 * Created by Asif on 3/27/2018.
 */

public class Session {



        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "TrailBlaze";

        // All Shared Preferences Keys
        private static final String IS_LOGIN = "IsLoggedIn";

        // User name (make variable public to access from outside)
        public static final String USER_NAME = "name";

        // Email address (make variable public to access from outside)
        public static final String USER_ID = "userid";

        // Constructor
        public Session(Context context){
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        /**
         * Create login session
         * */
        public void createLoginSession(String name, String uid){
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGIN, true);

            // Storing name in pref
            editor.putString(USER_NAME, name);

            // Storing email in pref
            editor.putString(USER_ID, uid);

            // commit changes
            editor.commit();
        }





        /**
         * Get stored session data
         * */
        public HashMap<String, String> getUserDetails(){
            HashMap<String, String> user = new HashMap<String, String>();
            // user name
            user.put(USER_NAME, pref.getString(USER_NAME, null));

            // user user id
            user.put(USER_ID, pref.getString(USER_ID, null));

            // return user
            return user;
        }

        /**
         * Clear session details
         * */
        public void logoutUser(){
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();

            // After logout redirect user to Loing Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }


}