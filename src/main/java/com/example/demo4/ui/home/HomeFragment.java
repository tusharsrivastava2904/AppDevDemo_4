package com.example.demo4.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo4.HomePage;
import com.example.demo4.R;
import com.example.demo4.SingleTask;
import com.example.demo4.adapter.Module_adapter;
import com.example.demo4.dao.MyLocalDatabase;
import com.example.demo4.login;
import com.example.demo4.model.Module;
import com.example.demo4.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SharedPreferences sp;
    private MyLocalDatabase myLocalDatabase;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //sp = getSharedPreferences("session",MODE_PRIVATE);
        //for code re-usability
        //returns the object of Application
        SingleTask singleTask = (SingleTask) getActivity().getApplication();                  //downcast to get the object of SharedPreferences
        sp = singleTask.getSharedPreferences();                                                //from the child class (SingleTask)
        //SharedPreferences is used to achieve session management

        myLocalDatabase = new MyLocalDatabase(getActivity());
        HomePage homePage = (HomePage) getActivity();
        user = homePage.getUser();

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.myrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        dummyModule();
        Module_adapter module_adapter = new Module_adapter(list);
        module_adapter.setOnDemoListener(new Module_adapter.DemoListener() {
            @Override
            public void clickOnItem(View view, int position) {
                String name = list.get(position).getName();
                NavController navController = Navigation.findNavController(view);
                if(name.equalsIgnoreCase("about us")){
                    navController.navigate(R.id.action_nav_home_to_nav_about);
                } else if (name.equalsIgnoreCase("contact us")) {
                    navController.navigate(R.id.action_nav_home_to_nav_contact);
                } else if (name.equalsIgnoreCase("delete account")) {
                    confirmation();
                }else if (name.equalsIgnoreCase("feedback")){
                    navController.navigate(R.id.action_nav_home_to_nav_feedback);
                } else if (name.equalsIgnoreCase("logout")) {
                    SharedPreferences.Editor e = sp.edit();
                    e.remove("status");
                    e.commit();                                          //removing login data from session
                    Intent in = new Intent(getActivity(), login.class);
                    startActivity(in);
                    getActivity().finish();
                } else if (name.equalsIgnoreCase("profile")){
                    navController.navigate(R.id.action_nav_home_to_nav_profile);
                }
            }
        });

        recyclerView.setAdapter(module_adapter);
    }

    private void confirmation() {
        AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
        a.setTitle("Delete Account!");
        a.setMessage("Are you sure you want to Delete your account?");
        a.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long t = myLocalDatabase.deleteAccount(user.getEmail());
                if (t>0){
                    Toast.makeText(getActivity(),"Account Deleted!",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor e = sp.edit();
                    e.remove("status");
                    e.commit();
                    Intent in = new Intent(getActivity(),login.class);
                    startActivity(in);
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity(),"Could not Delete!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        a.setCancelable(false);
        a.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        a.show();
    }

    //code from deepsingh44.blogspot.com
    //Module from there added in model
    List<Module> list;
    private void dummyModule() {
        list = new ArrayList<>();
        Module m1 = new Module();
        m1.setName("About Us");
        m1.setImage(R.drawable.ic_menu_share);

        Module m2 = new Module();
        m2.setName("Contact Us");
        m2.setImage(R.drawable.ic_menu_gallery);

        Module m3 = new Module();
        m3.setName("Profile");
        m3.setImage(R.drawable.ic_menu_camera);

        Module m6 = new Module();
        m6.setName("Feedback");
        m6.setImage(R.drawable.ic_menu_send);

        Module m5 = new Module();
        m5.setName("Delete Account");
        m5.setImage(R.drawable.ic_menu_slideshow);

        Module m4 = new Module();
        m4.setName("Logout");
        m4.setImage(R.drawable.ic_menu_manage);

        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m6);
        list.add(m5);
        list.add(m4);
    }
}