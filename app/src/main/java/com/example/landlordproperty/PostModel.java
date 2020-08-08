package com.example.landlordproperty;

public class PostModel {

    String FullName;
    String PhoneNumber;
    String FlatNo;
    String Address;
    String City;
    String State;
    String Zipcode;
    String Description;
    String Image;


    //Default Constructor

    public PostModel() {
    }


    //Parametarised Constuctor

    public PostModel(String fullName, String phoneNumber, String flatNo, String address, String city, String state, String zipcode, String description, String image) {
        FullName = fullName;
        PhoneNumber = phoneNumber;
        FlatNo = flatNo;
        Address = address;
        City = city;
        State = state;
        Zipcode = zipcode;
        Description = description;
        Image = image;
    }

    //Getters and Setters

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
