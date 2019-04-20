package com.example.priyanka.agrimart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Image extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseFirestore mDatabaseRef;
    private List<Upload> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(this, mUploads);
        //mRecyclerView.setAdapter(mAdapter);
        mDatabaseRef = FirebaseFirestore.getInstance();

        mAdapter = new ImageAdapter(this, mUploads);


        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {

    }
}
