package com.example.dell.agrimart1.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.agrimart1.Models.Bid;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.R;
import com.example.dell.agrimart1.WholeSaler_Activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class BuyActivity extends AppCompatActivity {

    private static final String TAG = "BuyActivity";
    private String contact, imageUrl, price, email, name, id;
    private TextView textViewName, textViewPrice, TextViewContact;
    private ImageView imageView;
    private Button btnCall;
    private EditText editTextBidding,editTextWholesaler,editTextPlace;
    private FirebaseFirestore db;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        sessionManager = new SessionManager(this);

        contact = getIntent().getStringExtra("contact");
        imageUrl = getIntent().getStringExtra("imageUrl");
        price = getIntent().getStringExtra("price");
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");

        email = sessionManager.getUserDetails().get("email");

        //Log.d(TAG, "id"+id+","+""+email) ;
        if (email == null) {
            FirebaseAuth.getInstance().signOut();
        }


        Log.d(TAG, "Infos :" + contact + "" + price + "" + name + "," + email + "," + id);
        editTextBidding = (EditText) findViewById(R.id.editTextBidding);
        editTextWholesaler = (EditText) findViewById(R.id.EditTextBidder);
        editTextPlace = (EditText) findViewById(R.id.EditTextPlace);


        textViewName = (TextView) findViewById(R.id.textView7);
        textViewPrice = (TextView) findViewById(R.id.textView8);
        imageView = (ImageView) findViewById(R.id.imageViewBuy);
        btnCall = (Button) findViewById(R.id.button);
        db = FirebaseFirestore.getInstance();


        textViewName.setText(name);
        textViewPrice.setText(price + "/-");

        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(imageView);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Bidding amoun is " + editTextBidding.getText().toString());

                Bid bid = new Bid(editTextBidding.getText().toString(), name, contact, id,editTextWholesaler.getText().toString(),
                        editTextPlace.getText().toString());

                db.collection("biddings").add(bid).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(BuyActivity.this, "Bid regiostered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(BuyActivity.this, WholeSaler_Activity.class));
                    }
                });


            }
        });
    }
}

