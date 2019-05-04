package com.example.dell.agrimart1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.Models.User;
import com.example.dell.agrimart1.UI.NotRegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class   RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText EditTextid, EditTextname,   EditTextvillage,  EditTextage,
               EditTextlandHolding,  EditTextcontact,  EditTextemail, EditTextpassword;

    private Spinner  EditTextstate, EditTextblock,EditTextgender,EditTextqualification,
    EditTextlanguage,EditTextdistrict;
    private Button buttonSignup;
    private FirebaseFirestore db;
    private String Designation;
private SessionManager sessionManager;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonSignup = (Button) findViewById(R.id.buttonSignUp);
        sessionManager=new SessionManager(this);



        Designation = getIntent().getStringExtra("Designation");
        initialize();
        buttonSignup.setOnClickListener(this);
        Log.d(TAG,"designation is"+Designation);

    }

    @Override
    public void onClick(View view) {


        new saveUserTask().execute();

        startActivity(new Intent(RegisterActivity.this,NotRegisterActivity.class));


    }

    public class saveUserTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            saveUser();
            return null;
        }
    }

    public void saveUser() {

        Log.d(TAG, "entered to save user");
        String name = EditTextname.getText().toString();
        String state = EditTextstate.getSelectedItem().toString();
        //String state = "False";
        String district = EditTextdistrict.getSelectedItem().toString();
        String block = EditTextblock.getSelectedItem().toString();
        String village = EditTextvillage.getText().toString();
        String age = EditTextage.getText().toString();
        String gender = EditTextgender.getSelectedItem().toString();
        String qualification = EditTextqualification.getSelectedItem().toString();
        String landHolding = EditTextlandHolding.getText().toString();
        String language = EditTextlanguage.getSelectedItem().toString();
        final String contact = EditTextcontact.getText().toString();
        final String email = EditTextemail.getText().toString();
        String userName = "NotRegistered";
        String password = EditTextpassword.getText().toString();
        String id = email;


        final User userSave = new User(id, name, state, district, block, village, age, gender,
                qualification, landHolding, language, contact, email, userName, password,Designation);


        db.collection("UserData").document(id).set(userSave).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User Saved.");

                    //correct location is found before the registration
                    sessionManager.createLoginSession(Designation,email,contact);

                    Toast.makeText(RegisterActivity.this, "user saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Network failure", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void initialize ()
    {
        db = FirebaseFirestore.getInstance();

        EditTextname= (EditText) findViewById(R.id.editTextName);

        EditTextvillage= (EditText) findViewById(R.id.editTextvillage);
        EditTextage= (EditText) findViewById(R.id.editTextage);
        EditTextlandHolding= (EditText) findViewById(R.id.editTextHolding);
        EditTextcontact= (EditText) findViewById(R.id.editTextcontact);
        EditTextemail= (EditText) findViewById(R.id.editTextemail);
       // EditTextuserName= (EditText) findViewById(R.id.editTextuserName);
        EditTextpassword= (EditText) findViewById(R.id.editTextpassword);

        EditTextstate= (Spinner) findViewById(R.id.editTextstate);
        EditTextdistrict= (Spinner) findViewById(R.id.editTextdistrict);
        EditTextblock= (Spinner) findViewById(R.id. editTextblock);
        buttonSignup = (Button) findViewById(R.id.buttonSignUp);
        EditTextgender= (Spinner) findViewById(R.id.editTextgender);
        EditTextqualification= (Spinner) findViewById(R.id.editTextqualification);
        EditTextlanguage= (Spinner) findViewById(R.id.editTextlanguage);


    }

}




