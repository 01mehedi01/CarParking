package com.mehedi.user.ubarparkingapps.PostWrite_Read;

public class DrivierinformationPojoClass {

    private String DrivingLicense;
    private String DriverPhoneNumber;
    private String DriverUserID;

    public DrivierinformationPojoClass(String drivingLicense, String driverPhoneNumber, String driverUserID) {
        DrivingLicense = drivingLicense;
        DriverPhoneNumber = driverPhoneNumber;
        DriverUserID = driverUserID;
    }

    public DrivierinformationPojoClass() {

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
}
