package com.example.dell.agrimart1.Models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_DESIGNATION = "designation";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CONTACT = "contact";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void createLoginSession(String designation ,String email,String contact){

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_DESIGNATION, designation);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_CONTACT,contact );


        editor.commit();
    }


    public void checkLogin(){

        if(!this.isLoggedIn()){


        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_DESIGNATION, pref.getString(KEY_DESIGNATION, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));

        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
