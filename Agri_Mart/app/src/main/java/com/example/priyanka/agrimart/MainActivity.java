package com.example.priyanka.agrimart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class   MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText EditTextid, EditTextname,  EditTextstate, EditTextdistrict,  EditTextblock,EditTextvillage,  EditTextage,
            EditTextgender,  EditTextqualification,  EditTextlandHolding, EditTextlanguage,  EditTextcontact,  EditTextemail,  EditTextuserName,  EditTextpassword;

    private Button buttonSignup;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;


    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   buttonSignup = (Button) findViewById(R.id.buttonSignUp);
        initialize();
        buttonSignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

       // startActivity(new Intent(MainActivity.this,LoginActivity.class));
       registerNewEmail();
    }

    public class saveUserTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            saveUser();
            return null;
        }
    }

    public void saveUser() {

        String id = firebaseAuth.getUid();
        String name = EditTextname.getText().toString();
        String state = EditTextstate.getText().toString();
        String district = EditTextdistrict.getText().toString();
        String block = EditTextblock.getText().toString();
        String village = EditTextvillage.getText().toString();
        String age = EditTextage.getText().toString();
        String gender = EditTextgender.getText().toString();
        String qualification = EditTextqualification.getText().toString();
        String landHolding = EditTextlandHolding.getText().toString();
        String language = EditTextlanguage.getText().toString();
        String contact = EditTextcontact.getText().toString();
        String email = EditTextemail.getText().toString();
        String userName = EditTextuserName.getText().toString();
        String password = EditTextpassword.getText().toString();


        User userSave = new User(id, name, state, district, block, village, age, gender, qualification, landHolding, language, contact, email, userName, password);


        db.collection("Farmer").document(id).set(userSave).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User Saved.");

                    //correct location is found before the registration

                    Toast.makeText(MainActivity.this, "user saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Network failure", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

        public void registerNewEmail () {
           String email = EditTextemail.getText().toString();
            String password = EditTextpassword.getText().toString();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful() ) {
                                Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                                //id = firebaseAuth.getCurrentUser().getUid();
                                new saveUserTask().execute();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Failed : AuthState: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Network issues", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void initialize ()
        {
            db = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();


                   EditTextname= (EditText) findViewById(R.id.editTextName);
                    EditTextstate= (EditText) findViewById(R.id.editTextstate);
                    EditTextdistrict= (EditText) findViewById(R.id.editTextdistrict);
                    EditTextblock= (EditText) findViewById(R.id. editTextblock);
                    EditTextvillage= (EditText) findViewById(R.id.editTextvillage);
                    EditTextage= (EditText) findViewById(R.id.editTextage);
                    EditTextgender= (EditText) findViewById(R.id.editTextgender);
                    EditTextqualification= (EditText) findViewById(R.id.editTextqualification);
                    EditTextlandHolding= (EditText) findViewById(R.id.editTextHolding);
                    EditTextlanguage= (EditText) findViewById(R.id.editTextlanguage);
                    EditTextcontact= (EditText) findViewById(R.id.editTextcontact);
                    EditTextemail= (EditText) findViewById(R.id.editTextemail);
                    EditTextuserName= (EditText) findViewById(R.id.editTextuserName);
                    EditTextpassword= (EditText) findViewById(R.id.editTextpassword);


            buttonSignup = (Button) findViewById(R.id.buttonSignUp);


        }

    }


