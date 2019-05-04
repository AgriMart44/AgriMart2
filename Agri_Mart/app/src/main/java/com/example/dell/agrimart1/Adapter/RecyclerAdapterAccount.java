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

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterAccount extends RecyclerView.Adapter <RecyclerAdapterAccount.ViewHolder> {

    private static final String TAG ="RecyclerAdapterAccount" ;
    private List<User> arraylistItem=new ArrayList<>();
    private Context context;
    private OnItemClickListener newListener;



    public RecyclerAdapterAccount(List<User> arraylistItem, Context context) {
        this.arraylistItem = arraylistItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.card1,null);
        ViewHolder viewHolder = new ViewHolder(view, context, (ArrayList<User>) arraylistItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        User user = arraylistItem.get(i);
        String name = user.getName();

        viewHolder.textViewTransactionName.setText( name);


    }


    @Override
    public int getItemCount() {
        return arraylistItem.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView textViewTransactionName;
        ArrayList<User>arrayListItem = new ArrayList<User>();
        Context context;


        public ViewHolder(@NonNull View itemView, Context context, ArrayList<User>arrayListItem)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            this.arrayListItem = arrayListItem;
            this.context=context;

            textViewTransactionName=(TextView)itemView.findViewById(R.id.textViewName);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Log.d(TAG,"Position in recyclerAccount is"+position);
            if (newListener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    newListener.onItemClick(position);

                }
            }


        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        newListener = listener;
    }
}
