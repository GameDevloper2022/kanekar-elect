package com.example.kanekarelectronicsv1;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager instance;

    private ImageLoader imageLoader;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_NAME = "Name";
    private static final String KEY_USER_EMAIL = "Email";
    private static final String KEY_USER_ID = "userid";

    private SharedPrefManager(Context context) {
        ctx = context;

    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int id,String name,String  email)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_NAME,name);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL , null) != null)
        {
            return true;
        }
        return false;
    }

    public boolean logOut ()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

}
