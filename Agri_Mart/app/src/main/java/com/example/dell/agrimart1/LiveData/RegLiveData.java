package com.example.dell.agrimart1.LiveData;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.dell.agrimart1.Models.Bid;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegLiveData extends LiveData<List<Bid>> {
    public static final String TAG = "RegLiveData";

    private CollectionReference collectionReference;
    private List<Bid>list = new ArrayList<>();
    private final MyValueEventListener listener = new MyValueEventListener();
    private ListenerRegistration listenerRegistration;

    public RegLiveData(CollectionReference collectionReference)
    {
        this.collectionReference = collectionReference;
    }

    @Override
    protected void onActive() {
        super.onActive();

        Log.d(TAG, "onActive");
        if (listenerRegistration == null )
            listenerRegistration = collectionReference.addSnapshotListener(listener);
    }
    @Override
    protected void onInactive() {
        super.onInactive();

        Log.d(TAG, "onInactive: ");
        if (listenerRegistration != null)
            listenerRegistration.remove();
    }

    private class MyValueEventListener implements EventListener<QuerySnapshot> {

        @Override
        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
            list.clear();
            if (e != null)
            {
                Log.e(TAG, "Can't listen to doc snapshots: " + queryDocumentSnapshots + ":::" + e.getMessage());
                return;
            }
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments())
            {
                int i=200;
                Bid bid = doc.toObject(Bid.class);
                list.add(bid);
            }
            setValue(list);
        }
    }



}
