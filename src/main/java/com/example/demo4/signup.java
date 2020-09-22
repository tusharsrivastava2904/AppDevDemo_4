package com.example.demo4;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo4.dao.MyLocalDatabase;
import com.example.demo4.model.User;

public class signup extends AppCompatActivity {

    private EditText tname, temail, tpass, tmobile;
    private Spinner tstate;
    private  MyLocalDatabase md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        initViews();
    }
    //making objects of data
    private void  initViews(){
        tname = findViewById(R.id.sname);
        temail = findViewById(R.id.sEmail);
        tpass = findViewById(R.id.spass);
        tmobile = findViewById(R.id.smobile);
        tstate = findViewById(R.id.state);


    }

    //submit()
    public void submit(View view) {
        if (isValid()){
            //creating a new class object for model class User to set the values to declared variables
            User user= new User();
            user.setName(name);
            user.setEmail(email);
            user.setPass(pass);
            user.setMobile(Long.parseLong(mobile));
            user.setState(state);

            md = new MyLocalDatabase(this);
            long t = md.insert(user);

            if (t>0){
                Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
            }
         }

//    //trial code for networking-database here.
//    //call the insert servlet from here
//    //adding volley to gradle build for easy communication to network database.
//        callToServlet();
//    }
//
//    private void callToServlet(){
//        final StringRequest sr = new StringRequest(Request.Method.POST, "http://ip_address/MyFirstServer/insert", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(signup.this, response,Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(signup.this,""+ error,Toast.LENGTH_LONG).show();
//            }
//        });
//        Volley.newRequestQueue(this).add(sr);
   }

    //declaring variables globally to access from different methods
    private String name, email, pass, mobile, state;

    //validation if else ladder
    private boolean isValid(){
        name = tname.getText().toString();
        email = temail.getText().toString();
        pass = tpass.getText().toString();
        mobile = tmobile.getText().toString();
        state = tstate.getSelectedItem().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter your Name!",Toast.LENGTH_SHORT).show();
            tname.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter your Email!",Toast.LENGTH_SHORT).show();
            temail.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Enter your Password!",Toast.LENGTH_SHORT).show();
            tpass.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(mobile)){
            Toast.makeText(this,"Enter your Phone Number!",Toast.LENGTH_SHORT).show();
            tmobile.requestFocus();
            return false;
        }else if(state.equalsIgnoreCase("select state")){
            Toast.makeText(this,"Select your State!",Toast.LENGTH_SHORT).show();
            tstate.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    //goToLoginPage()
    public void goToLoginPage(View view){
        Intent in = new Intent(this,login.class);
        startActivity(in);
        finish();
    }

    //reset()
    public void reset(View view){
        clear();
    }

    // defining clear method
    private void clear(){
        tname.setText("");
        temail.setText("");
        tpass.setText("");
        tmobile.setText("");
    }
}