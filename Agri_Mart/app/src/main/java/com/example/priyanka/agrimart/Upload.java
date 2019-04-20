package com.example.priyanka.agrimart;

import com.google.firebase.firestore.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private long timeStamp;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl,long timeStamp) {
        if (name.trim().equals("")) {
            name = "No Name";

        }

        mName = name;
        mImageUrl = imageUrl;
        timeStamp=this.timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
