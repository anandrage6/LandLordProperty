package com.example.landlordproperty;

public class TenantPostModel {
    String FullName;
    String Email;
    String Phone;
    String RentAmount;
    String SecurityDeposit;
    String StartDate;
    String EndDate;
    String PropertyName;
    String FlatNo;

    //Default Constructor
    public TenantPostModel() {
    }

    //Parameterised Constructor
    public TenantPostModel(String fullName, String email, String phone, String rentAmount, String securityDeposit, String startDate, String endDate, String propertyName, String flatNo) {
        FullName = fullName;
        Email = email;
        Phone = phone;
        RentAmount = rentAmount;
        SecurityDeposit = securityDeposit;
        StartDate = startDate;
        EndDate = endDate;
        PropertyName = propertyName;
        FlatNo = flatNo;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRentAmount() {
        return RentAmount;
    }

    public void setRentAmount(String rentAmount) {
        RentAmount = rentAmount;
    }

    public String getSecurityDeposit() {
        return SecurityDeposit;
    }

    public void setSecurityDeposit(String securityDeposit) {
        SecurityDeposit = securityDeposit;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

}
