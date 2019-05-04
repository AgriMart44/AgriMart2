package com.example.dell.agrimart1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.agrimart1.Models.Bid;
import com.example.dell.agrimart1.R;

import java.util.ArrayList;
import java.util.List;

public class BiddingInfoAdapter extends RecyclerView.Adapter <BiddingInfoAdapter.ViewHolder> {

    private static final String TAG ="RecyclerAdapterAccount" ;
    private List<Bid> arraylistItem=new ArrayList<>();
    private Context context;
    private OnItemClickListener newListener;



    public BiddingInfoAdapter(List<Bid> arraylistItem, Context context) {
        this.arraylistItem = arraylistItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.card2_bid,null);
        ViewHolder viewHolder = new ViewHolder(view, context, (ArrayList<Bid>) arraylistItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        Bid bid = arraylistItem.get(i);
        String price = bid.getBid();


        viewHolder.textViewProductName.setText(bid.getName());
        viewHolder.wholeSaler.setText(bid.getWholesaler());
        viewHolder.textViewWholeSalerPrice.setText("Bid :"+price+"/-");
        viewHolder.place.setText(bid.getPlace());

    }


    @Override
    public int getItemCount() {
        return arraylistItem.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView textViewProductName;
        TextView wholeSaler,place;
        TextView textViewWholeSalerPrice;
        ArrayList<Bid>arrayListItem = new ArrayList<Bid>();
        Context context;


        public ViewHolder(@NonNull View itemView, Context context, ArrayList<Bid>arrayListItem)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            this.arrayListItem = arrayListItem;
            this.context=context;

            textViewProductName=(TextView)itemView.findViewById(R.id.textViewNameBidder);
            textViewWholeSalerPrice=(TextView)itemView.findViewById(R.id.textViewPriceBidder);
            wholeSaler=(TextView)itemView.findViewById(R.id.textViewBidder);
            place=(TextView)itemView.findViewById(R.id.textViewPlaceBidder);
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
