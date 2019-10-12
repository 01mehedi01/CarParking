package com.mehedi.user.ubarparkingapps.PostWrite_Read;

/**
 * Created by User on 5/29/2019.
 */

public class Pojoclass {
    private String Name;
    private String Location;
    private String Address;
    private String Sloat;
    private String postcretoruserID;
    private String sloatType;
    private int perhourCost;
    private String HouseNo;
    private Boolean booked;
    private String settimeLimit;


    private String DrivingLicense;
    private String DriverPhoneNumber;
    private String DriverUserID;



    public Pojoclass(String drivingLicense, String driverPhoneNumber, String driverUserID) {
        DrivingLicense = drivingLicense;
        DriverPhoneNumber = driverPhoneNumber;
        DriverUserID = driverUserID;
    }
    public Pojoclass(String drivingLicense, String driverPhoneNumber, String driverUserID,int perhourCost) {
        DrivingLicense = drivingLicense;
        DriverPhoneNumber = driverPhoneNumber;
        DriverUserID = driverUserID;
        this.perhourCost = perhourCost;
    }

    public String getDrivingLicense() {
        return DrivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        DrivingLicense = drivingLicense;
    }

    public String getDriverPhoneNumber() {
        return DriverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        DriverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverUserID() {
        return DriverUserID;
    }

    public void setDriverUserID(String driverUserID) {
        DriverUserID = driverUserID;
    }

    public String getPostcretoruserID() {
        return postcretoruserID;
    }

    public void setPostcretoruserID(String postcretoruserID) {
        this.postcretoruserID = postcretoruserID;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getSloatType() {
        return sloatType;
    }

    public void setSloatType(String sloatType) {
        this.sloatType = sloatType;
    }

    public int getPerhourCost() {
        return perhourCost;
    }

    public void setPerhourCost(int perhourCost) {
        this.perhourCost = perhourCost;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public String getSettimeLimit() {
        return settimeLimit;
    }

    public void setSettimeLimit(String settimeLimit) {
        this.settimeLimit = settimeLimit;
    }

    public Pojoclass(String name, String location, String address, String sloat, String userID, String sloatType, int perhourCost, Boolean booked, String settimeLimit) {
        Name = name;
        Location = location;
        Address = address;
        Sloat = sloat;
        this.postcretoruserID = userID;
        this.sloatType = sloatType;
        this.perhourCost = perhourCost;
        this.booked = booked;
        this.settimeLimit = settimeLimit;
    }

    public Pojoclass(String name, String location, String address, String sloat, String postcretoruserID, String sloatType, int perhourCost, String houseNo, Boolean booked) {
        Name = name;
        Location = location;
        Address = address;
        Sloat = sloat;
        this.postcretoruserID = postcretoruserID;
        this.sloatType = sloatType;
        this.perhourCost = perhourCost;
        HouseNo = houseNo;
        this.booked = booked;
    }

    public Pojoclass(String name, String location, String address, String sloat, String userID, String sloatType, int perhourCost, Boolean booked) {
        Name = name;
        Location = location;
        Address = address;
        Sloat = sloat;
        this.postcretoruserID = userID;
        this.sloatType = sloatType;
        this.perhourCost = perhourCost;
        this.booked = booked;
    }

    public Pojoclass(String name, String location, String address, String sloat, String sloatType) {
        Name = name;
        Location = location;
        Address = address;
        Sloat = sloat;
        this.sloatType = sloatType;
    }

    public Pojoclass(String name, String location, String address, String sloat) {
        Name = name;
        Location = location;
        Address = address;
        Sloat = sloat;
    }

    public Pojoclass() {

    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSloat() {
        return Sloat;
    }

    public void setSloat(String sloat) {
        Sloat = sloat;
    }

    public String getUserID() {
        return postcretoruserID;
    }

    public void setUserID(String userID) {
        this.postcretoruserID = userID;
    }
}
