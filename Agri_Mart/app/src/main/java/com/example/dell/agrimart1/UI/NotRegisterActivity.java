package com.example.dell.agrimart1.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dell.agrimart1.LiveData.UserListModel;
import com.example.dell.agrimart1.LoginActivity;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.Models.User;
import com.example.dell.agrimart1.R;

import java.util.List;

public class NotRegisterActivity extends AppCompatActivity {

    private static final String TAG = "NotRegisterActivity";
    private SessionManager sessionManager;
    private User user;
    private String emailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_register);
         sessionManager=new SessionManager(this);
        emailId=sessionManager.getUserDetails().get("email");

        user= new User();

        UserListModel viewModel = ViewModelProviders.of(this).get(UserListModel.class);
        LiveData<List<User>> liveData = viewModel.getDataSnapshotLiveData();
        liveData.observe(NotRegisterActivity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                for (User user1:users){

                    if(user1.getEmail().equals(emailId)) {
                        user = user1;
                        Log.d(TAG,"user is "+user.getName());
                    }
                }
                Log.d(TAG,"users size : :"+users.size());

                if(user!=null && user.getUserName()==("Registered")){

                    sessionManager.createLoginSession("Registered",emailId,sessionManager.getUserDetails().get("contact"));

                    startActivity(new Intent(NotRegisterActivity.this, LoginActivity.class));
                }


            }
        });


    }
}
