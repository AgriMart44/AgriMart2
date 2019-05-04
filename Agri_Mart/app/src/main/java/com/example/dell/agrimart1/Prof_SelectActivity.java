package com.example.dell.agrimart1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Prof_SelectActivity extends AppCompatActivity implements View.OnClickListener {
    Button bnFarmer,bnWholsaler;
   // private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof__select);

        //sessionManager=new SessionManager(this);
        bnFarmer = findViewById(R.id.Bnprof1);
        bnWholsaler=findViewById(R.id.Bnprof3);


        bnFarmer.setOnClickListener(this);
        bnWholsaler.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==bnFarmer){

           // sessionManager.createLoginSession("Farmer","");
            Intent intent = new Intent(Prof_SelectActivity.this, RegisterActivity.class);
            intent.putExtra("Designation","Farmer");
            startActivity(intent);
            finish();
        }
        else if(view==bnWholsaler){
          //  sessionManager.createLoginSession("WholeSaler","");
            Intent intent = new Intent(Prof_SelectActivity.this, RegisterActivity.class);
            intent.putExtra("Designation","WholeSaler");
            startActivity(intent);
            finish();
        }

    }
}
