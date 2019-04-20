package com.example.priyanka.agrimart;

public class User {

    private String Id,Name,State,District,Block,Village,Age,Gender,Qualification,LandHolding
            ,Language,Contact,Email,UserName,Password;

    public User() {
    }

    public User(String id, String name, String state, String district, String block, String village, String age, String gender, String qualification, String landHolding, String language, String contact, String email, String userName, String password) {
        Id = id;
        Name = name;
        State = state;
        District = district;
        Block = block;
        Village = village;
        Age = age;
        Gender = gender;
        Qualification = qualification;
        LandHolding = landHolding;
        Language = language;
        Contact = contact;
        Email = email;
        UserName = userName;
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getState() {
        return State;
    }

    public String getDistrict() {
        return District;
    }

    public String getBlock() {
        return Block;
    }

    public String getVillage() {
        return Village;
    }

    public String getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public String getQualification() {
        return Qualification;
    }

    public String getLandHolding() {
        return LandHolding;
    }

    public String getLanguage() {
        return Language;
    }

    public String getContact() {
        return Contact;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }
}
