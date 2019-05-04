package com.example.dell.agrimart1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.agrimart1.Models.Upload;
import com.example.dell.agrimart1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private static final String TAG = "ImageAdapter";
    private  OnItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        Log.d(TAG,"imageAdapter initialised");
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        Log.d(TAG,"image item xml inflated ");
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        Upload uploadCurrent = mUploads.get(position);
        holder.textViewNameImage.setText(uploadCurrent.getName());
        holder.textViewPrice.setText(uploadCurrent.getmPrice()+"/-");
        Log.d(TAG,"image item name"+uploadCurrent.getName());
        Log.d(TAG,"image url inserted to picasso "+uploadCurrent.getImageUrl());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        Log.d(TAG,"image uploaded to picasso ");
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public TextView textViewNameImage;
        public TextView textViewPrice;
        public ImageView imageView;

        public ImageViewHolder(View itemView)
        {
            super(itemView);

            textViewNameImage = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);

                }
            }
        }


    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
