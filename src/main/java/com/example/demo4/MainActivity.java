package com.example.demo4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sp = getSharedPreferences("session",MODE_PRIVATE);
        //creates a .xml file of the provide f_name and does not share the app's data with other apps when MODE_PRIVATE

        //for code re-usability
        //returns the object of Application
        SingleTask singleTask = (SingleTask) getApplication();                  //downcast to get the object of SharedPreferences
        sp = singleTask.getSharedPreferences();                                                //from the child class (SingleTask)
        //SharedPreferences is used to achieve session management


        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.title);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.textzoom);

        tv.setAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean status = sp.getBoolean("status",false);                 //default value of status set to false
                if(status){
                    Intent intent= new Intent(MainActivity.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent= new Intent(MainActivity.this,login.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}