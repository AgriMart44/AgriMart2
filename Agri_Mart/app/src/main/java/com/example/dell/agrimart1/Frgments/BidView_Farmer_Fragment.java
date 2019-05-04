package com.example.dell.agrimart1.Frgments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.agrimart1.Adapter.BiddingInfoAdapter;
import com.example.dell.agrimart1.Models.Bid;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidView_Farmer_Fragment extends Fragment implements BiddingInfoAdapter.OnItemClickListener {

    private static final String TAG = "BidView_Farmer";
    private FirebaseFirestore db;
    private View view;
    private SessionManager sessionManager;

    private List<Bid>bidList;
    private RecyclerView recyclerView;
    private List<Bid> receiveList;
    private BiddingInfoAdapter.OnItemClickListener itemClickListener;
    private Context context;
    private BiddingInfoAdapter receiveAdapter;

    public BidView_Farmer_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bid_view__farmer_, container, false);

        db = FirebaseFirestore.getInstance();
        sessionManager=new SessionManager(getActivity());
        bidList=new ArrayList<>();

        recyclerView=view.findViewById(R.id.recycler_bidView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        receiveList=new ArrayList<>();
        receiveAdapter=new BiddingInfoAdapter( receiveList, getActivity());
        receiveAdapter.setOnItemClickListener(this);

        if(sessionManager.getUserDetails().get("contact")!=null) {
          //  sessionManager.getUserDetails().get("contact")
          /* db.collection("uploads").whereEqualTo("mContact","12345678" )
                    .collection("biddings").
                    addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                   bidList.clear();
                  for(DocumentSnapshot querySnapshot:queryDocumentSnapshots.getDocuments()){

                      Bid bid = querySnapshot.toObject(Bid.class);
                      receiveList.add(bid);
                      Log.d(TAG,"bid rate "+bid.getBid());
                  }
                    receiveAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(receiveAdapter);
                  Log.d(TAG,"bid List Size "+bidList.size());
                }
            });*/

        }else {
            FirebaseAuth.getInstance().signOut();
        }

        return view;
    }

    @Override
    public void onItemClick(int position) {

    }
}
