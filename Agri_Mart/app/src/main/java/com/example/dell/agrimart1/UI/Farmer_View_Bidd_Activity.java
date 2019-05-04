package com.example.dell.agrimart1.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.agrimart1.Adapter.BiddingInfoAdapter;
import com.example.dell.agrimart1.LiveData.RegListModel;
import com.example.dell.agrimart1.Models.Bid;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Farmer_View_Bidd_Activity extends AppCompatActivity implements BiddingInfoAdapter.OnItemClickListener {


    private static final String TAG = "Farmer_Bidd_Activity" ;
    private String contact , imageUrl ,price ,email, name,id;
    private Button btnCall;
    private FirebaseFirestore db;
    private SessionManager sessionManager;

    private List<Bid> bidList;
    private RecyclerView recyclerView;
    private List<Bid> receiveList;
    private BiddingInfoAdapter.OnItemClickListener itemClickListener;
    private Context context;
    private BiddingInfoAdapter receiveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_view_bid);
        sessionManager=new SessionManager(this);
        bidList=new ArrayList<>();

        recyclerView= findViewById(R.id.recycler_bidView_farmer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        receiveList=new ArrayList<>();
        receiveAdapter=new BiddingInfoAdapter( receiveList, this);
        receiveAdapter.setOnItemClickListener(this);


        contact=getIntent().getStringExtra("contact");
        imageUrl=getIntent().getStringExtra("imageUrl");
        price = getIntent().getStringExtra("price");
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        email= sessionManager.getUserDetails().get("email");

        //Log.d(TAG, "id"+id+","+""+email) ;
         if(id==null){
             FirebaseAuth.getInstance().signOut();
         }
        Log.d(TAG,"Infos :"+contact+""+price+""+name+","+email+","+id);




        RegListModel viewModel = ViewModelProviders.of(this).get(RegListModel.class);
        LiveData<List<Bid>> liveData = viewModel.getDataSnapshotLiveData();
        liveData.observe(this, new Observer<List<Bid>>() {
            @Override
            public void onChanged(@Nullable List<Bid> bids) {
                receiveList.clear();
                receiveList.addAll(bids);
               // Log.d(TAG,"bid rate "+bid.getBid());
                receiveAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(receiveAdapter);
                 Log.d(TAG,"bid List Size "+receiveList.size());
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(Farmer_View_Bidd_Activity.this, "Contact Admin To fix the bid", Toast.LENGTH_SHORT).show();

    }
}

