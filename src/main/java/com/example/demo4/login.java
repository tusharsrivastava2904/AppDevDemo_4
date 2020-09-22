package com.example.demo4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo4.dao.MyLocalDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    private MyLocalDatabase md;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //protected class makes information within the organisation accessible to anyone
        //so in order to protect user data we call the private method inside this protected method to get user data.
        initViews();
    }

    //declaring variables globally
    private EditText temail, tpass;

    //initialising variables
    private void initViews(){
        //getting email and password from the user.
        //creating objects using findViewById()
        tpass = findViewById(R.id.loginpass);
        temail = findViewById(R.id.loginEmail);

        md = new MyLocalDatabase(this);
        //for code re-usability
        //returns the object of Application
        SingleTask singleTask = (SingleTask) getApplication();                  //downcast to get the object of SharedPreferences
        sp = singleTask.getSharedPreferences();                                                //from the child class (SingleTask)
        //SharedPreferences is used to achieve session management
    }

    //goToRegisterPage()
    public void goToRegisterPage(View view){
        Intent in = new Intent(this,signup.class);
        startActivity(in);
        finish();
    }

    //login()
    public void Login(View view){
        //validation executed

        if (valid()){

            boolean flag = md.login(email, pass);
            // md has a method login defined in MyLocalDatabase which query the database and returns a boolean
            if (flag){
                Toast.makeText(this,"Logged-in Successfully",Toast.LENGTH_SHORT).show();
                //first time login would change status to true
                //when started again splash file will find the value true and land the user to HomePage
                SharedPreferences.Editor e= sp.edit();                      //used to edit the status key
                e.putBoolean("status",true);
                e.putString("email",email);                              //for showing details on Profile Fragment
                e.commit();                                                 //mandatory to commit the change

                Intent in = new Intent(this,HomePage.class);
                startActivity(in);
                finish();
            }else{
                Toast.makeText(this,"Login Failed!",Toast.LENGTH_LONG).show();
            }
        }
    }

    private String email, pass;

    //validation if else ladder
    private boolean valid(){
        //validation code here
        //code to get text here because this segment executes first
        email = temail.getText().toString();
        pass = tpass.getText().toString();

        //missing field validation\
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter the email",Toast.LENGTH_SHORT).show();
            temail.requestFocus();
            return false;
        }else if(!isEmail(email)){
            Toast.makeText(this,"Invalid Email!",Toast.LENGTH_SHORT).show();
            temail.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter the Password",Toast.LENGTH_SHORT).show();
            tpass.requestFocus();
            return false;
        }//else if(!isPass(pass)){
         //  Toast.makeText(this,"Invalid Password!",Toast.LENGTH_SHORT).show();
         //   tpass.requestFocus();
         //   return false;}
        else{
            return true;
        }
    }

    //regex for email
    private String emailPattern="^[a-zA-Z0-9]{1,20}@[a-zA-Z]{1,20}.[a-zA-Z]{2,3}$";
    private boolean isEmail(String email){
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //regex for password
    //private String passwordPattern="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!?&()@$%#]).{6,15})";
    //private boolean isPass(String pass){
    //    Pattern pattern = Pattern.compile(passwordPattern);
    //    Matcher matcher = pattern.matcher(pass);
    //   return matcher.matches();
    //   }

    //forgotPassword()
    public void forgotPassword(View view){
        Intent in = new Intent(this,forgot.class);
        startActivity(in);
        finish();
    }

}