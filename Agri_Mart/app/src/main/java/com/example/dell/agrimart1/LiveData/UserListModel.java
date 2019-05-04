package com.example.dell.agrimart1.LiveData;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.dell.agrimart1.Models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserListModel extends ViewModel {

    private static final CollectionReference user_REF = FirebaseFirestore.getInstance().
            collection("UserData");
    private final UserLiveData liveData = new UserLiveData(user_REF);

    @NonNull
    public LiveData<List<User>> getDataSnapshotLiveData() {
        return liveData;
    }

}

