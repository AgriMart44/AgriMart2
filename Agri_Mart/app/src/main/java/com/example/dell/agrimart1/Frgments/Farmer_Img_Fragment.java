package com.example.dell.agrimart1.Frgments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dell.agrimart1.Adapter.ImageAdapter;
import com.example.dell.agrimart1.LiveData.imageListModel;
import com.example.dell.agrimart1.Models.SessionManager;
import com.example.dell.agrimart1.Models.Upload;
import com.example.dell.agrimart1.R;
import com.example.dell.agrimart1.UI.Farmer_View_Bidd_Activity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Farmer_Img_Fragment extends Fragment implements ImageAdapter.OnItemClickListener  {

    private static final String TAG = "Img_Fragment";
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseFirestore mDatabaseRef;
    private List<Upload> mUploads;
    private View view;
    private SessionManager sessionManager;

    public Farmer_Img_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_img_, container, false);

        initilize();

        sessionManager = new SessionManager(getActivity());

        mAdapter = new ImageAdapter(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        Log.d(TAG,"entering to live data");
        imageListModel viewModel = ViewModelProviders.of(this).get(imageListModel.class);
        LiveData<List<Upload>> liveData = viewModel.getDataSnapshotLiveData();
        liveData.observe(this, new Observer<List<Upload>>() {
            @Override
            public void onChanged(@Nullable List<Upload> uploads) {

                Log.d(TAG,"Live data size"+uploads.size());
                mUploads.clear();
                Log.d(TAG,"contact"+sessionManager.getUserDetails().get("contact"));
                for(Upload upload : uploads)
                {
                    Log.d(TAG,"contacts"+upload.getmContact());
                    if(upload.getmContact()!=null && upload.getmContact().equals(sessionManager.getUserDetails().get("contact")))
                    mUploads.add(upload);
                    Log.d(TAG,"Relevent data size"+mUploads.size());
                }
               // mUploads.addAll(uploads);
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        return view;

    }
    public void initilize(){

        mRecyclerView = view.findViewById(R.id.recycler_view_Fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(getActivity(), mUploads);
        //mRecyclerView.setAdapter(mAdapter);
        mDatabaseRef = FirebaseFirestore.getInstance();
        mProgressCircle =view.findViewById(R.id.progress_circle_Fragment);
        Log.d(TAG,"Initialisation completed");
    }

    @Override
    public void onItemClick(int position) {

       // startActivity(new Intent(getActivity(),));

       // Toast.makeText(getActivity(), "the position is"+position, Toast.LENGTH_SHORT).show();

        String contact = mUploads.get(position).getmContact();
        String imageUrl =mUploads.get(position).getImageUrl();
        String price = mUploads.get(position).getmPrice();
        String name = mUploads.get(position).getName();
        String id = mUploads.get(position).getmKey();

        Log.d(TAG,"Information :"+contact+""+price+""+name);

        Intent intent= new  Intent(getActivity(), Farmer_View_Bidd_Activity.class);
        intent.putExtra("contact",contact);
        intent.putExtra("id",id);
        intent.putExtra("imageUrl",imageUrl);
        intent.putExtra("price",price);
        intent.putExtra("name",name);

        startActivity(intent);

    }
}
