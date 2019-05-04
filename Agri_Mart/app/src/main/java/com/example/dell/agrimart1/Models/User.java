package com.example.dell.agrimart1.Models;

public class User {

    private String id,name,state,district,block,village,age,gender,qualification,landHolding
            ,language,contact,email,userName,password,designation;

    public User() {
    }

    public User(String id, String name, String state, String district, String block, String village,
                String age, String gender, String qualification, String landHolding, String language,
                String contact, String email, String userName, String password, String designation) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.district = district;
        this.block = block;
        this.village = village;
        this.age = age;
        this.gender = gender;
        this.qualification = qualification;
        this.landHolding = landHolding;
        this.language = language;
        this.contact = contact;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.designation = designation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getBlock() {
        return block;
    }

    public String getVillage() {
        return village;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getQualification() {
        return qualification;
    }

    public String getLandHolding() {
        return landHolding;
    }

    public String getLanguage() {
        return language;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDesignation() {
        return designation;
    }
}
