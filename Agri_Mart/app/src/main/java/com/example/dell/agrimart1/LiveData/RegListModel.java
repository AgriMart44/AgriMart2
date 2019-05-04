package com.example.dell.agrimart1.LiveData;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.dell.agrimart1.Models.Bid;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RegListModel extends ViewModel {

    private static final CollectionReference user_REF = FirebaseFirestore.getInstance().
            collection("biddings");
    private final RegLiveData liveData = new RegLiveData(user_REF);

    @NonNull
    public LiveData<List<Bid>> getDataSnapshotLiveData() {
        return liveData;
    }

}

