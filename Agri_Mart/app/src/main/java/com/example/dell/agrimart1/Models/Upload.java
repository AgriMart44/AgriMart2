package com.example.dell.agrimart1.Models;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private long timeStamp;
    private String mPrice;
    private String mContact;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl,long timeStamp,String price,String contact,String key) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        timeStamp=this.timeStamp;
        mPrice=price;
        mContact=contact;
        mKey = key;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmContact() {
        return mContact;
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

    public String getmKey() {
        return mKey;
    }


}

