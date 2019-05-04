package com.example.dell.agrimart1.LiveData;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.dell.agrimart1.Models.Upload;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class imageListModel extends ViewModel {

    private static final CollectionReference img_REF = FirebaseFirestore.getInstance().
            collection("uploads");

    private final imageLiveData liveData = new imageLiveData(img_REF);

    @NonNull
    public LiveData<List<Upload>> getDataSnapshotLiveData() {
        return liveData;
    }

}


