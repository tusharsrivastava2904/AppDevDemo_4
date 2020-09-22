package com.example.demo4.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo4.HomePage;
import com.example.demo4.R;
import com.example.demo4.dao.MyLocalDatabase;
import com.example.demo4.model.User;

public class Profile extends Fragment {

    private MyLocalDatabase myLocalDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        myLocalDatabase = new MyLocalDatabase(getActivity());
        return view;
    }

    private EditText tname, temail, tpass, tmobile;
//    private Spinner tstate;
    private User user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tname = view.findViewById(R.id.pro_name);
        temail = view.findViewById(R.id.pro_email);
        tpass = view.findViewById(R.id.pro_pass);
        tmobile = view.findViewById(R.id.pro_mobile);
//        tstate = view.findViewById(R.id.pro_state);
        Button updateButton = view.findViewById(R.id.pro_update);

        HomePage homePage = (HomePage) getActivity();
        user = homePage.getUser();

        tname.setText(user.getName());
        temail.setText(user.getEmail());
        tpass.setText(user.getPass());
        tmobile.setText(String.valueOf(user.getMobile()));
//        tstate.setText(user.getName());                //Editing of state can't be performed for now.

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(tname.getText().toString());
                user.setPass(tpass.getText().toString());
                user.setMobile(Long.parseLong(tmobile.getText().toString()));
                user.setName(tname.getText().toString());
                int i = myLocalDatabase.updateUser(user);
                if(i>0){
                    Toast.makeText(getActivity(),"Successfully Updated!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Couldn't Update!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
