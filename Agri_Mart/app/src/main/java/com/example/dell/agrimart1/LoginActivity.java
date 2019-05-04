package com.example.dell.agrimart1;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.agrimart1.LiveData.listModel;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.Models.User;
import com.example.dell.agrimart1.UI.NotRegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup,textViewHead;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private String name,email,number,id,avatar,password,state,profile,contactNumber,rating;
    private String spMail;

    private User currentUser;
    private List<User> userList;
    private List<User> userListSign;
    private User userSignIn;


    private static final String TAG = "LoginActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar mProgressBar;
    String Designation;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

         sessionManager=new SessionManager(this);
          Designation= sessionManager.getUserDetails().get("designation");

        Log.d(TAG,"designation is "+Designation);
        userList=new ArrayList<>();
        userListSign=new ArrayList<>();
        userSignIn=new User();

       /* Intent intent = new Intent(LoginActivity.this, Admin_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();*/


         if (firebaseUser != null && Designation.equals("Farmer")){

            Toast.makeText(LoginActivity.this, "Authenticated with: " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, Farmer_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }else if (firebaseUser != null && Designation.equals("WholeSaler")){

            Toast.makeText(LoginActivity.this, "Authenticated with: " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, WholeSaler_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else if (Designation!=null && Designation.equals("NotRegistered") ) {

            Toast.makeText(LoginActivity.this, "Not Authenticated yet: " , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, NotRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            /* Intent intent = new Intent(LoginActivity.this, Admin_Activity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);*/
        }

       else {
            Log.d(TAG, "onAuthStateChanged:signed_out");

        }

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

        listModel viewModel = ViewModelProviders.of(this).get(listModel.class);
        LiveData<List<User>> liveData = viewModel.getDataSnapshotLiveData();
        liveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {


              userListSign.addAll(users);
                Log.d(TAG, "size of list"+users.size() );

            }
        });

    }



    private void userLogin(){
        //showDialog();
        validate();
        email = editTextEmail.getText().toString().trim();
        password  = editTextPassword.getText().toString().trim();

        for(User user:userListSign){

            if(user.getEmail().equals(email)){
                userSignIn=user;
                Log.d(TAG, "user found");

            }
        }

        validate();

        if(email.equals("admin@gmail.com")&password.equals("123456"))
        {

            Intent intent = new Intent(LoginActivity.this, Admin_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }else

            {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {

                                 if (userSignIn.getDesignation() .equals("Farmer")) {

                                     sessionManager.createLoginSession("Farmer",email,userSignIn.getContact());
                                    Intent intent = new Intent(LoginActivity.this, Farmer_Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                     sessionManager.createLoginSession("Wholesaler",email,userSignIn.getContact());
                                    Intent intent = new Intent(LoginActivity.this, WholeSaler_Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }


                            } else {
                                Toast.makeText(LoginActivity.this, "NetWork Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onComplete: failed to set the user client.");
                    Toast.makeText(LoginActivity.this, "The email you entered doesn't match any account.Sign up for an account", Toast.LENGTH_LONG).show();
                }
            });

        }

    }



    public void initialize()
    {


        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonSignIn = (Button)findViewById(R.id.buttonSignin);
        textViewSignup  = (TextView)findViewById(R.id.textViewSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


    }
    public void validate(){

        email = editTextEmail.getText().toString().trim();
        password  = editTextPassword.getText().toString().trim();

        if(isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


    }


    @Override
    public void onClick(View view) {
        if(view == buttonSignIn)
        {
            userLogin();
          //  startActivity(new Intent(LoginActivity.this,Admin_Activity.class));

        }
        if(view == textViewSignup){
            //finish();
            //startActivity(new Intent(Login_Activity.this, Registration_Activity.class));
            Intent intent = new Intent(LoginActivity.this, Prof_SelectActivity.class);
            startActivity(intent);
        }

    }



}


