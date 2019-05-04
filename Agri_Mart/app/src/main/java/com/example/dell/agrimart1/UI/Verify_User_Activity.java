package com.example.dell.agrimart1.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.agrimart1.Admin_Activity;
import com.example.dell.agrimart1.LiveData.UserListModel;
import com.example.dell.agrimart1.Models.User;
import com.example.dell.agrimart1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Verify_User_Activity extends AppCompatActivity {
    private static final String TAG = "Verify_User_Activity";
    private  User user;
    String uniqueID;
    private TextView textView1,textView2,textView3,textView4,textView5,textView6;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__user);
        user= new User();

        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5=(TextView)findViewById(R.id.textView5);
        textView6=(TextView)findViewById(R.id.textView6);
        uniqueID = getIntent().getStringExtra("uniqueID");

        Log.d(TAG,"Position :"+uniqueID);

        UserListModel viewModel = ViewModelProviders.of(this).get(UserListModel.class);
        LiveData<List<User>> liveData = viewModel.getDataSnapshotLiveData();
        liveData.observe(Verify_User_Activity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
              for (User user1:users){

                if(user1.getEmail().equals(uniqueID)) {
                    user = user1;
                    Log.d(TAG,"user is "+user.getName());
                }
              }
                Log.d(TAG,"users size : :"+users.size());

                Log.d(TAG,"Position :"+user.getName());
                textView1.setText(user.getName());
                textView2.setText(user.getState());
                textView3.setText(user.getDistrict());
                textView4.setText(user.getBlock());
                textView5.setText(user.getVillage());
                textView6.setText(user.getQualification());



            }
        });

    }

    public void onClick(View view) {


        Intent intent = new Intent(Verify_User_Activity.this, Admin_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
       // delete(user.getId());
        registerNewEmail();
    }

    public void delete(String id){

        db.collection("UserData").document(id).delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Verify_User_Activity.this, "Authentication Failed, try later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerNewEmail () {
        String email = user.getEmail();
        String password = user.getPassword();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful() ) {
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            //id = firebaseAuth.getCurrentUser().getUid();

                            Log.d(TAG, "Designation in register activity is  " );

                            db.collection("UserData").document(user.getId()).update("userName","Registered").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Verify_User_Activity.this, " Authenticated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });


                            finish();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed : AuthState: " + e.getMessage());
                Toast.makeText(Verify_User_Activity.this, "Network issues", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
