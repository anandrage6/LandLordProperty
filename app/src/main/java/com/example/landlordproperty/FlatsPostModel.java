package com.example.landlordproperty;

public class FlatsPostModel {

    String PropertyName;
    String Address;
    String City;
    String State;
    String Zipcode;
    String FlatNo;

    //Default Constructor
    public FlatsPostModel() {
    }


    //parameterised Constructor
    public FlatsPostModel(String propertyName, String address, String city, String state, String zipcode, String flatNo) {
        PropertyName = propertyName;
        Address = address;
        City = city;
        State = state;
        Zipcode = zipcode;
        FlatNo = flatNo;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
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

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }
}
