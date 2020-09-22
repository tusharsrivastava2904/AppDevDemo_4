package com.example.demo4;

import android.app.Application;
import android.content.SharedPreferences;

//Application class contain all the tasks that are common in multiple activities
public class SingleTask extends Application {
    //here we have created SingleTask to use SharedPreferences with code re-usability
    private SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("session",MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sp;
    }
}
