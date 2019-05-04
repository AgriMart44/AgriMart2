package com.example.dell.agrimart1.Models;

public class Bid {

    String bid;
    String name;
    String contact;
    String id,wholesaler,place;


    public Bid() {
    }

    public Bid(String bid, String name, String contact, String id, String wholesaler, String place) {
        this.bid = bid;
        this.name = name;
        this.contact = contact;
        this.id = id;
        this.wholesaler = wholesaler;
        this.place = place;
    }

    public String getBid() {
        return bid;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getId() {
        return id;
    }

    public String getWholesaler() {
        return wholesaler;
    }

    public String getPlace() {
        return place;
    }
}

