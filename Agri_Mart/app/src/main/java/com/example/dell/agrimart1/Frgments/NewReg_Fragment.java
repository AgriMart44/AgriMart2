package com.example.dell.agrimart1.Frgments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.example.dell.agrimart1.Adapter.RecyclerAdapterAccount;
import com.example.dell.agrimart1.LiveData.UserListModel;
import com.example.dell.agrimart1.Models.User;
import com.example.dell.agrimart1.R;
import com.example.dell.agrimart1.UI.Verify_User_Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewReg_Fragment extends Fragment implements RecyclerAdapterAccount.OnItemClickListener {
    private static final String TAG ="NewReg_Fragment" ;
    private  View view;
private RecyclerView recyclerView;
private List<User> receiveList;
private RecyclerAdapterAccount.OnItemClickListener itemClickListener;
private Context context;


private RecyclerAdapterAccount receiveAdapter;
    public NewReg_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_reg_, container, false);

        recyclerView=view.findViewById(R.id.recycler_acctView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        receiveList=new ArrayList<>();
        receiveAdapter=new RecyclerAdapterAccount( receiveList, getActivity());
        receiveAdapter.setOnItemClickListener(this);



         UserListModel viewModel = ViewModelProviders.of(getActivity()).get(UserListModel.class);

        LiveData<List<User>> liveData = viewModel.getDataSnapshotLiveData();
        liveData.observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {



                for(User user: users) {
                    if (user.getUserName().equals("NotRegistered")) {
                        receiveList.add(user);
                        receiveAdapter.notifyDataSetChanged();

                        recyclerView.setAdapter(receiveAdapter);
                        Log.d(TAG, "receiveList size " + receiveList.size());
                    }
                }
            }
        });



        return view;
    }

    private void setUserRegStatus(List<User>userslist)
    {
        Log.d(TAG, "userPosition called,the size of userList is"+userslist.size());
        int i=0;

        for (User user : userslist)
        {
            if (!user.getState().equals("True"))
            {
               receiveList.add(user);

            }else {
                i=i+1;
            }
        }
        Log.d(TAG,"receiveList size "+receiveList.size());

    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), Verify_User_Activity.class);
        intent.putExtra("uniqueID",receiveList.get(position).getEmail());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
