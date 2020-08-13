package com.example.landlordproperty;

public class PostModel {

    String PropertyName;
    String OwnerName;
    String Address;
    String City;
    String State;
    String Zipcode;
    String Description;
    // Add flats
    String FlatNo;
    String Image;


    //Default Constructor

    public PostModel() {
    }


    //Parametarised Constuctor
    public PostModel(String propertyName, String ownerName, String address, String city, String state, String zipcode, String description, String image) {
        PropertyName = propertyName;
        OwnerName = ownerName;
        Address = address;
        City = city;
        State = state;
        Zipcode = zipcode;
        Description = description;
        Image = image;
    }

    //Getters and Setters

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
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

    //Parametarised Constuctor Add Flats


    public PostModel(String flatNo) {
        FlatNo = flatNo;
    }

    //getters and setters Addflats

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

}
