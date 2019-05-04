package com.example.dell.agrimart1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.agrimart1.Models.User;
import com.example.dell.agrimart1.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ViewHolder> {

    private static final String TAG ="RecyclerAdapter" ;
    private List<User> arraylistItem;
    private Context context;

    public RecyclerAdapter(List<User> arraylistItem, Context context)
    {
        this.arraylistItem = arraylistItem;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        LayoutInflater inflater=LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.card1,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        User house=arraylistItem.get(i);
        String position = String.valueOf(i+1);
         Log.d(TAG,"Name is "+house.getName());
        viewHolder.textViewName.setText(house.getName());

    }

    @Override
    public int getItemCount() {
        return arraylistItem.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewName;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewName=(TextView)itemView.findViewById(R.id.textViewName);
        }
    }

}